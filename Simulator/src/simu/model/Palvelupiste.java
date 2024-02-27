package simu.model;

import simu.framework.*;
import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus
	private final LinkedList<Asiakas> aulaJono = new LinkedList<>(); // Tietorakennetoteutus
	private final LinkedList<Asiakas> aspaJono = new LinkedList<>(); // Tietorakennetoteutus
	private final LinkedList<Asiakas> hyllyJono = new LinkedList<>(); // Tietorakennetoteutus
	private final LinkedList<Asiakas> reseptiJono = new LinkedList<>(); // Tietorakennetoteutus
	private final LinkedList<Asiakas> kassaJono = new LinkedList<>(); // Tietorakennetoteutus

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



	public Palvelupiste(String palvelupisteenNimi, ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.palvelupisteenNimi = palvelupisteenNimi;
	}


	public void lisaaJonoon(Asiakas a){// Jonon 1. asiakas aina palvelussa
		a.setAulaState();
		if (a.getAulaState() == 1) {
			aulaJono.add(a);
			System.out.println("CUSTOMER ADDED TO AULAJONO");
		}
		else {
			String nextService = a.nextService();
			switch (nextService) {
				//PRINTS FOR DEBUGGING
				case "Asiakaspalvelu":
					aspaJono.add(a);
					System.out.println("CUSTOMER ADDED TO ASPAJONO");
					break;
				case "Hyllyt":
					hyllyJono.add(a);
					System.out.println("CUSTOMER ADDED TO HYLLYJONO");
					break;
				case "Resepti":
					reseptiJono.add(a);
					System.out.println("CUSTOMER ADDED TO RESEPTIJONO");
					break;

			}
		}
		}



	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		Asiakas asiakas = null;
		//Determine the service point based on the event type
		switch (skeduloitavanTapahtumanTyyppi) {
			case AULA_P:
				aulaCounter();
				//asiakas = aulaJono.poll();
				break;
			case ASPA_P:
				aspaCounter();
				asiakas.setAspaKäyty();
				//asiakas = aspaJono.poll();
				break;
			case KAUPPA_P:
				kauppaCounter();
				asiakas.setKauppaKäyty();
				asiakas.setKauppaSpent();
				//asiakas = hyllyJono.poll();
				break;
			case RESEPTI_P:
				reseptiCounter();
				asiakas.setReseptiKäyty();
				asiakas.setReseptiSpent();
				//asiakas = reseptiJono.poll();
				break;
			case KASSA_P:
				kassaCounter();
				//asiakas = kassaJono.poll();
				break;
		}
		return asiakas;
	}


	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		Asiakas asiakas = null;
		//Determine the service point based on the event type
		switch (skeduloitavanTapahtumanTyyppi) {
			case AULA_P:
				if(!aulaJono.isEmpty()){
					asiakas = aulaJono.peek();
				}
				break;
			case ASPA_P:
				if(!aspaJono.isEmpty()){
					asiakas = aspaJono.peek();
				}
				break;
			case KAUPPA_P:
				if(!hyllyJono.isEmpty()){
					asiakas = hyllyJono.peek();
				}
				break;
			case RESEPTI_P:
				if(!reseptiJono.isEmpty()){
					asiakas = reseptiJono.peek();
				}
				break;
			case KASSA_P:
				if(!kassaJono.isEmpty()){
					asiakas = kassaJono.peek();
				}
				break;
		}

		if(asiakas != null){
			Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + asiakas.getId());

			varattu = true;
			double palveluaika = generator.sample();
			//get the time the customer has been served
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
			asiakas.setPalveluaika(servicePoint, palveluaika);

			tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
		}
	}


	public boolean onVarattu(){
		return varattu;
	}



	public boolean onJonossa(){
		return !jono.isEmpty();
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