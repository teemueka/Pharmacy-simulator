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
	private int aulaUsage = 0;
	private int kauppaUsage = 0;
	private int reseptiUsage = 0;
	private int aspaUsage = 0;
	private int kassaUsage = 0;
	private double activeTimeAspa;
	private double activeTimeKauppa;
	private double activeTimeResepti;
	private double activeTimeKassa;

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
				setActiveTimeAspa(palveluaika);
				break;
			case KAUPPA_P:
				servicePoint = "Kauppa";
				setActiveTimeKauppa(palveluaika);
				break;
			case RESEPTI_P:
				servicePoint = "Resepti";
				setActiveTimeResepti(palveluaika);
				break;
			case KASSA_P:
				servicePoint = "Kassa";
				setActiveTimeKassa(palveluaika);
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

	public void setActiveTimeAspa(double palveluaika) {
		activeTimeAspa += palveluaika;
	}
	public void setActiveTimeKauppa(double palveluaika) {
		activeTimeKauppa += palveluaika;
	}
	public void setActiveTimeResepti(double palveluaika) {
		activeTimeResepti += palveluaika;
	}
	public void setActiveTimeKassa(double palveluaika) {
		activeTimeKassa += palveluaika;
	}
	public double getAspaUtilization(int aspaTyontekijat) {
		return (activeTimeAspa / aspaTyontekijat) / Kello.getInstance().getAika() * 100;
	}
	public double getKauppaUtilization(int hyllyTyontekijat) {
		return (activeTimeKauppa / hyllyTyontekijat) / Kello.getInstance().getAika() * 100;
	}
	public double getReseptiUtilization(int reseptiTyontekijat) {
		return (activeTimeResepti / reseptiTyontekijat) / Kello.getInstance().getAika() * 100;
	}
	public double getKassaUtilization(int kassaTyontekijat) {
		return (activeTimeKassa / kassaTyontekijat) / Kello.getInstance().getAika() * 100;
	}
	public String displayUtilization(int a, int h, int r, int k) {
		return "aspa util: " + getAspaUtilization(a) + " %, kauppa util: " + getKauppaUtilization(h) * 100 + " %, resepti util: " + getReseptiUtilization(r) * 100 + " %, kassa util: " + getKassaUtilization(k) * 100 + " %";
	}

}
