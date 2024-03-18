package simu.model;

import simu.framework.*;
import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;

/**
 * Palvelupiste-class is used to create service points for the simulation
 */
public class Palvelupiste {


	/**
	 * The queue of customers waiting for service
	 */
	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus

	/**
	 * Array of customers being served
	 */
	private final LinkedList<Asiakas> palvelussa = new LinkedList<>();

	/**
	 * The generator for the service time
	 */
	private final ContinuousGenerator generator;

	/**
	 * The event list
	 */
	private final Tapahtumalista tapahtumalista;

	/**
	 * The type of event to be scheduled
	 */
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	/**
	 * The name of the service point
	 */
	private final String palvelupisteenNimi;

	/**
	 * The time the service point has been active
	 */
	private double activeTime;

	/**
	 * The number of customers served
	 */
	private int usage;

	/**
	 * The service point is full or not
	 */
	private boolean varattu = false;

	/**
	 * The number of employees at the service point
	 */
	private final int staff;

	/**
	 * The number of customers being served
	 */
	private int palveltavat = 0;


	/**
	 * Constructor for the service point
	 * @param palvelupisteenNimi the name of the service point
	 * @param generator the generator for the service time
	 * @param staff the number of employees at the service point
	 * @param tapahtumalista the event list
	 * @param tyyppi the type of event to be scheduled
	 */
	public Palvelupiste(String palvelupisteenNimi, ContinuousGenerator generator, int staff, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
        this.palvelupisteenNimi = palvelupisteenNimi;
		this.staff = staff;
    }

	/**
	 * Add a customer to the queue
	 * @param a	the customer to be added
	 */
	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		
	}

	/**
	 * Remove a customer from the queue
	 * updates the number of customers being served
	 * updates the service point status
	 * @return the customer removed from the queue
	 */
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


	/**
	 * Start a new service
	 * updates the number of customers being served
	 * updates the service point status
	 */
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

	/**
	 * Check if the service point is full
	 * @return true if the service point is full, false otherwise
	 */
	public boolean onVarattu(){
		return varattu;
	}

	/**
	 * Check if the queue is empty
	 * @return true if the queue is empty, false otherwise
	 */
	public boolean onJonossa(){
		return !jono.isEmpty();
	}

	/**
	 * Get the number of customers being served
	 * @return the number of customers being served
	 */
	public int getPalveltavat() {
		return palveltavat;
	}

	/**
	 * Get the number of employees at the service point
	 * @return the number of employees at the service point
	 */
	public int getStaff() {
		return staff;
	}

	/**
	 * Get the name of the service point
	 * @return the name of the service point
	 */
	public String getPalvelupisteenNimi() {
		return palvelupisteenNimi;
	}

	/**
	 * Get the time the service point has been active
	 * @return the time the service point has been active
	 */
	public int getUsage() {
		return usage;
	}

	/**
	 * Get the utilization of the service point
	 * @return the utilization of the service point
	 */
	public void setActiveTime(double palveluaika) {
		activeTime += palveluaika;
	}

	/**
	 * Get the utilization of the service point
	 * @return the utilization of the service point
	 */
	public double getUtilization() {
		double utilization = (activeTime / staff) / Kello.getInstance().getAika() * 100;
		return Double.parseDouble(String.format("%.1f", utilization).replace(",", "."));
	}

	/**
	 * Get the length of the queue
	 * @return the length of the queue
	 */
	public double getJonoPituus() {
		return jono.size();
	}

}
