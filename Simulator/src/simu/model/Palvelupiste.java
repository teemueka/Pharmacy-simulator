package simu.model;

import simu.framework.*;
import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus
	private final LinkedList<Asiakas> palvelussa = new LinkedList<>();
	private final ContinuousGenerator generator;
	private final Tapahtumalista tapahtumalista;
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	//this is not yet used for anything other than naming the services
	private final String palvelupisteenNimi;

	//added counters for every service, not sure if we use them yet for anything
	//currently just incrementing every time customer enters service
	private static int aulaUsage = 0;
	private static int kauppaUsage = 0;
	private static int reseptiUsage = 0;
	private static int aspaUsage = 0;
	private static int kassaUsage = 0;

	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false;

	private final int staff;
	private int palveltavat = 0;



	public Palvelupiste(String palvelupisteenNimi, ContinuousGenerator generator, int staff, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
        this.palvelupisteenNimi = palvelupisteenNimi;
		this.staff = staff;
    }


	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		
	}


	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		palveltavat--;
		varattu = false;
		Asiakas asiakas = palvelussa.poll();
		//Determine the service point based on the event type
        switch (skeduloitavanTapahtumanTyyppi) {
			case AULA_P:
                aulaCounter();
				break;
			case ASPA_P:
                aspaCounter();
				asiakas.setAspaKayty();
				break;
			case KAUPPA_P:
                kauppaCounter();
				asiakas.setKauppaKayty();
				asiakas.setKauppaSpent();
				break;
			case RESEPTI_P:
                reseptiCounter();
				asiakas.setReseptiKayty();
				asiakas.setReseptiSpent();
				break;
			case KASSA_P:
                kassaCounter();
				break;
		}
		return asiakas;
	}


	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "Aloitetaan uusi " + getPalvelupisteenNimi() + " palvelu asiakkaalle " + jono.peek().getId());

		palveltavat++;
		if (palveltavat < staff){
			varattu = false;
		} else {
			varattu = true;
		}

		Trace.out(Trace.Level.INFO, getPalvelupisteenNimi() + ", henkilökunnan määrä: " + getStaff() + " palvelussa tällä hetkellä: " + getPalveltavat());
		double palveluaika = generator.sample();
		//get the time the customer has been served
		palvelussa.add(jono.peek());
		Asiakas asiakas = jono.peek();
		jono.poll();
		asiakas.setKokonaisPalveluaika(palveluaika);

		//Set service time for the specific service point
		String servicePoint = "";
		switch (skeduloitavanTapahtumanTyyppi) {
			case AULA_P:
				servicePoint = "Aula";
				break;
			case ASPA_P:
				servicePoint = "Aspa";
				break;
			case KAUPPA_P:
				servicePoint = "Kauppa";
				break;
			case RESEPTI_P:
				servicePoint = "Resepti";
				break;
			case KASSA_P:
				servicePoint = "Kassa";
				break;
		}
		//Set service time for the specific service point
		asiakas.setPalveluaika(servicePoint, palveluaika);

		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
	}

	public boolean onVarattu(){
		return varattu;
	}



	public boolean onJonossa(){
		return !jono.isEmpty();
	}
	public int getPalveltavat() {
		return palveltavat;
	}
	public int getStaff() {
		return staff;
	}
	public String getPalvelupisteenNimi() {
		return palvelupisteenNimi;
	}
	//counters for all the services
	public void aulaCounter() {
		aulaUsage++;
	}
	public void aspaCounter() {
		aspaUsage++;
	}
	public void kauppaCounter() {
		kauppaUsage++;
	}
	public void reseptiCounter() {
		reseptiUsage++;
	}
	public void kassaCounter() {
		kassaUsage++;
	}
	public int getKassaUsage() {
		return kassaUsage;
	}
	public int getAspaUsage() {
		return aspaUsage;
	}

	public int getKauppaUsage() {
		return kauppaUsage;
	}

	public int getReseptiUsage() {
		return reseptiUsage;
	}
	public int getAulaUsage() {
		return aulaUsage;
	}
	//this is just here to help us better understand the simulation during the run
	public String displayServiceUsage() {
		return "served customers at aula: " + getAulaUsage() + ", served customers at aspa: " + getAspaUsage() + ", served customers at kauppa: " + getKauppaUsage() + ", served customers at resepti: " + getReseptiUsage() + ", served customers at kassa: " + getKassaUsage();
	}

}
