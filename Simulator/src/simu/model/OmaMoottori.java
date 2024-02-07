package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

public class OmaMoottori extends Moottori{
	
	private Saapumisprosessi saapumisprosessi;

	private Palvelupiste[] palvelupisteet;

	public OmaMoottori(){

		palvelupisteet = new Palvelupiste[4];

		palvelupisteet[0]=new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.AULA_P);
		palvelupisteet[1]=new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.RESEPTI_P);
		palvelupisteet[2]=new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.KAUPPA_P);
		palvelupisteet[3]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.KASSA_P);

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

			case AULA_S: palvelupisteet[0].lisaaJonoon(new Asiakas());
				    saapumisprosessi.generoiSeuraava();
				break;
			case AULA_P: a = (Asiakas)palvelupisteet[0].otaJonosta();
				   	palvelupisteet[1].lisaaJonoon(a);
				break;
			case RESEPTI_P: a = (Asiakas)palvelupisteet[1].otaJonosta();
				palvelupisteet[2].lisaaJonoon(a);
				break;

			case KAUPPA_P: a = (Asiakas)palvelupisteet[2].otaJonosta();
					palvelupisteet[3].lisaaJonoon(a);
				break;

			case KASSA_P:
				       a = (Asiakas)palvelupisteet[3].otaJonosta();
					   a.setPoistumisaika(Kello.getInstance().getAika());
			           a.raportti();
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
	}

	
}
