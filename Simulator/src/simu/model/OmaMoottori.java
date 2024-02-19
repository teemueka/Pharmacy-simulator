package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import controller.IKontrolleriForM;

public class OmaMoottori extends Moottori{
	
	private Saapumisprosessi saapumisprosessi;
	private Palvelupiste[] palvelupisteet;
	Apteekki apteekki = new Apteekki();


	public OmaMoottori(IKontrolleriForM kontrolleri){

		super(kontrolleri);

		palvelupisteet = new Palvelupiste[5];

		palvelupisteet[0]=new Palvelupiste("Sisäänkäynti", new Normal (0,1), tapahtumalista, TapahtumanTyyppi.AULA_P);
		palvelupisteet[1]=new Palvelupiste("Asiakaspalvelu" , new Normal(10,10), tapahtumalista, TapahtumanTyyppi.ASPA_P);
		palvelupisteet[2]=new Palvelupiste("Hyllyt" , new Normal(5,3), tapahtumalista, TapahtumanTyyppi.KAUPPA_P);
		palvelupisteet[3]=new Palvelupiste("Resepti", new Normal(10, 5), tapahtumalista, TapahtumanTyyppi.RESEPTI_P);
		palvelupisteet[4]=new Palvelupiste("Kassa", new Normal(10, 5), tapahtumalista, TapahtumanTyyppi.KASSA_P);


		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.AULA_S);


	}


	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		Asiakas a;
		switch ((TapahtumanTyyppi)t.getTyyppi()){


			//asiakkaan saapuminen, generoi uuden saapumisen, katsoo onko tilaa apteekissa
			case AULA_S:
				a = new Asiakas();
				saapumisprosessi.generoiSeuraava();
				apteekki.addToPharmacyque(a);
				System.out.println("Asiakas " + a.getId() + " lisätty apteekkijonoon. Jonon pituus: " + apteekki.displayApteekkijono());
				//jos on tilaa, jatka normaalisti
				if (apteekki.getCurrent_customers() < apteekki.getCapacity()) {

					a = apteekki.getFromPharmacyque();
					apteekki.customerIn();
					System.out.println("Asiakas pääsi sisään, asiakkaita sisällä: " + apteekki.getCurrent_customers());
					palvelupisteet[0].lisaaJonoon(a);

					//jos ei, mahdollisuus poistua
				} else {
					if (apteekki.missedCustomerChance() > 0.5) {
						apteekki.addMissedCustomer();
						System.out.println("Asiakasta kiukutti jonotus liikaa, menetettyjä asiakkaita: " + apteekki.displayMissedCustomers());
					} else {
						//todistan että asiakas jää jonoon ja hänet palvellaan tilanteessa jossa if ehto ei toteudu
						System.out.println("Asiakas, " + a.getId() + " päätti pysyä jonossa");
					}
				}
				break;


			case AULA_P:
				a = (Asiakas)palvelupisteet[0].otaJonosta();
				if (a.hasMoreServices()) {
					String nextService = a.getNextService();
					switch (nextService) {
						case "Asiakaspalvelu":
							palvelupisteet[1].lisaaJonoon(a);
							break;
						case "Hyllyt":
							palvelupisteet[2].lisaaJonoon(a);
								break;
						case "Resepti":
							palvelupisteet[3].lisaaJonoon(a);
							break;
					}
				} else {
					palvelupisteet[4].lisaaJonoon(a);
				}
				break;

			case ASPA_P:
			case KAUPPA_P:
			case RESEPTI_P:
				int index = 0;
				switch ((TapahtumanTyyppi)t.getTyyppi()) {
					case ASPA_P:
						index = 1;
						break;
					case KAUPPA_P:
						index = 2;
						break;
					case RESEPTI_P:
						index = 3;
						break;
				}
				a = (Asiakas)palvelupisteet[index].otaJonosta();
				if (a.hasMoreServices()) {
					String nextService = a.getNextService();
					switch (nextService) {
						case "Asiakaspalvelu":
							palvelupisteet[1].lisaaJonoon(a);
							break;
						case "Hyllyt":
							palvelupisteet[2].lisaaJonoon(a);
							break;
						case "Resepti":
							palvelupisteet[3].lisaaJonoon(a);
							break;
					}
				} else {
					palvelupisteet[4].lisaaJonoon(a);
				}
				break;

			case KASSA_P:
				a = (Asiakas)palvelupisteet[4].otaJonosta();

				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				System.out.println("Asiakas poistuu... asiakkaita sisällä: " + apteekki.getCurrent_customers());
				apteekki.customerOut();

		}
	}

	@Override
	protected void yritaCTapahtumat(){
		for (Palvelupiste p: palvelupisteet){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
		apteekki.displayResults();
		System.out.println(palvelupisteet[0].displayServiceUsage());

		// UUTTA graafista
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
	}

	
}
