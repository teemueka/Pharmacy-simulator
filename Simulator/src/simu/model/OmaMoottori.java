package simu.model;

import simu.dao.SimulationDao;
import controller.PaneelitController;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import controller.IKontrolleriForM;

public class OmaMoottori extends Moottori{
	
	private final Saapumisprosessi saapumisprosessi;
	private final Palvelupiste[] palvelupisteet;
	SimulationDao simulationDao = new SimulationDao();

	Apteekki apteekki;


	public OmaMoottori(IKontrolleriForM kontrolleri, int a_staff, int h_staff, int r_staff, int k_staff, int intensity, int capacity) {

		super(kontrolleri);

		apteekki = new Apteekki(capacity);

		System.out.println(a_staff + " " + h_staff + " " + r_staff + " " + k_staff);

		palvelupisteet = new Palvelupiste[5];

		palvelupisteet[0]=new Palvelupiste("Sisäänkäynti", new Normal (0,1),1, tapahtumalista, TapahtumanTyyppi.AULA_P);


		palvelupisteet[1]=new Palvelupiste("Asiakaspalvelu" , new Normal(100,10), a_staff, tapahtumalista, TapahtumanTyyppi.ASPA_P);
		palvelupisteet[2]=new Palvelupiste("Hyllyt" , new Normal(50,30),	h_staff , tapahtumalista, TapahtumanTyyppi.KAUPPA_P);
		palvelupisteet[3]=new Palvelupiste("Resepti", new Normal(100, 50),	r_staff ,tapahtumalista, TapahtumanTyyppi.RESEPTI_P);
		palvelupisteet[4]=new Palvelupiste("Kassa", new Normal(100, 50),	k_staff ,tapahtumalista, TapahtumanTyyppi.KASSA_P);


		saapumisprosessi = new Saapumisprosessi(new Negexp((intensity),intensity/10), tapahtumalista, TapahtumanTyyppi.AULA_S);


	}



	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		Asiakas a;
		switch (t.getTyyppi()){


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


						kontrolleri.naytaMenetetty(apteekki.getMissedCustomers());//Tämä päivittää menetettyjen asiakkaiden määrän
					} else {
						//todistan että asiakas jää jonoon ja hänet palvellaan tilanteessa jossa if ehto ei toteudu
						System.out.println("Asiakas, " + a.getId() + " päätti pysyä jonossa");
					}
				}
				break;


			case AULA_P:
				a = palvelupisteet[0].otaJonosta();
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
				switch (t.getTyyppi()) {
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
				a = palvelupisteet[index].otaJonosta();
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
					if (a.getSpent() != 0 && !a.onlyAspa()) {
						palvelupisteet[4].lisaaJonoon(a);
					}
					else {
						a.usedOnlyAspa();
						a.setPoistumisaika(Kello.getInstance().getAika());
						a.setTyytyvaisyys(apteekki);
						a.raportti(apteekki);
						System.out.println("Asiakas poistuu... asiakkaita sisällä: " + apteekki.getCurrent_customers());
						apteekki.customerOut();
					}
				}
				break;

			case KASSA_P:
				a = palvelupisteet[4].otaJonosta();

				a.setPoistumisaika(Kello.getInstance().getAika());
				a.setTyytyvaisyys(apteekki);
				a.raportti(apteekki);
				System.out.println("Asiakas poistuu... asiakkaita sisällä: " + apteekki.getCurrent_customers());
				apteekki.customerOut();

					   //Päivittää palveltun asiakkaan määrän
					   kontrolleri.naytaPalveltu(apteekki.getServedCustomers());

                       //TODO: FIX THE CUTOMERS!
				kontrolleri.updateTyytyvaisyys(((double)  apteekki.getSatisfiedCustomers() / apteekki.getServedCustomers()) * 100);
				kontrolleri.updateSuuJokaLiikkuu(((double) apteekki.getSatisfiedCustomers() / apteekki.getServedCustomers()) * 100);
				kontrolleri.updateAulaJonoPituus((double)apteekki.displayApteekkijono());
				kontrolleri.updateKassaJonoPituus((double)palvelupisteet[4].getJonoPituus());
				kontrolleri.updateInfoJonoPituus((double)palvelupisteet[1].getJonoPituus());
				kontrolleri.updateHyllyJonoPituus((double)palvelupisteet[2].getJonoPituus());
				kontrolleri.updateReseptiJonoPituus((double)palvelupisteet[3].getJonoPituus());
				kontrolleri.updateUI(palvelupisteet[1].getStaff(), palvelupisteet[2].getStaff(), palvelupisteet[3].getStaff(), palvelupisteet[4].getStaff(), apteekki.getServedCustomers(), apteekki.getMissedCustomers(), palvelupisteet[1].getUsage(), palvelupisteet[2].getUsage(), palvelupisteet[3].getUsage(), palvelupisteet[4].getUsage(), apteekki.getSatisfiedCustomers(), apteekki.getDissatisfiedCustomers(), apteekki.getOverallSatisfaction(), palvelupisteet[1].getUtilization(), palvelupisteet[2].getUtilization(), palvelupisteet[3].getUtilization(), palvelupisteet[4].getUtilization(), a.getKeskiarvo());
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
		System.out.println("Simuloinnissa käytetty henkilökunnan määrä");
		System.out.println("Aspa henkilökunta: " + palvelupisteet[1].getStaff() + ", Kauppa henkilökunta: " + palvelupisteet[2].getStaff() + ", Resepti henkilökunta: " + palvelupisteet[3].getStaff() + ", Kassa henkilökunta: " + palvelupisteet[4].getStaff());
		apteekki.displayResults();
		System.out.println("served customers at aula: " + palvelupisteet[0].getUsage() + ", served at aspa: " + palvelupisteet[1].getUsage() + ", served at kauppa: " + palvelupisteet[2].getUsage() + ", served at resepti: " + palvelupisteet[3].getUsage() + ", served at kassa: " + palvelupisteet[4].getUsage());
		System.out.printf("aspa util: %.1f%%, kauppa util: %.1f%%, resepti util: %.1f%%, kassa util: %.1f%%",
				palvelupisteet[1].getUtilization(),
				palvelupisteet[2].getUtilization(),
				palvelupisteet[3].getUtilization(),
				palvelupisteet[4].getUtilization());
		System.out.println();
		System.out.println(Asiakas.getUsedOnlyAspa() + " asiakasta kävi vain asiakaspalvelussa.");
		System.out.println("dissatisfied customers: " + apteekki.getDissatisfiedCustomers() + ", satisfied customers: " + apteekki.getSatisfiedCustomers());
		System.out.printf("Asiakastyytyväisyys: %.1f%%", apteekki.getOverallSatisfaction());
		System.out.println();
		System.out.println("Asiakkaat kuluttivat: " + Asiakas.getTotalSpentAllCustomers() + " €");
		System.out.println("Asiakkaiden keskiarvo kulutus: " + Asiakas.getTotalSpentAllCustomers() / apteekki.getServedCustomers() + " €");
		System.out.println("Menetetty tuotto: " + apteekki.getLostRevenue() + " €");
		simulationDao.saveResultsInDatabase(palvelupisteet[1].getStaff(), palvelupisteet[2].getStaff(), palvelupisteet[3].getStaff(), palvelupisteet[4].getStaff(), apteekki.getServedCustomers(), apteekki.getMissedCustomers(), palvelupisteet[1].getUsage(), palvelupisteet[2].getUsage(), palvelupisteet[3].getUsage(), palvelupisteet[4].getUsage(), apteekki.getSatisfiedCustomers(), apteekki.getDissatisfiedCustomers(), apteekki.getOverallSatisfaction(), palvelupisteet[1].getUtilization(), palvelupisteet[2].getUtilization(), palvelupisteet[3].getUtilization(), palvelupisteet[4].getUtilization());

		// UUTTA graafista
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		kontrolleri.simulationDone();



	}


}
