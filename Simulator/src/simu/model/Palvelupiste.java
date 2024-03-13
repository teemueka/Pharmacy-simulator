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
	private final String palvelupisteenNimi;
	private double activeTime;
	private int usage;

	private boolean varattu = false;

	private final int staff;
	private int palveltavat = 0;

	private double jonoPituus;



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
		usage++;
		//Determine the service point based on the event type
        switch (skeduloitavanTapahtumanTyyppi) {
			case AULA_P:
				break;
			case ASPA_P:
				asiakas.setAspaKayty();
				break;
			case KAUPPA_P:
				asiakas.setKauppaKayty();
				asiakas.setKauppaSpent();
				break;
			case RESEPTI_P:
				asiakas.setReseptiKayty();
				asiakas.setReseptiSpent();
				break;
			case KASSA_P:
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
		setActiveTime(palveluaika);

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

	public int getUsage() {
		return usage;
	}
	public void setActiveTime(double palveluaika) {
		activeTime += palveluaika;
	}
	public double getUtilization() {
		return (activeTime / staff) / Kello.getInstance().getAika() * 100;
	}

	public double getJonoPituus() {
		return jono.size();
	}

}
