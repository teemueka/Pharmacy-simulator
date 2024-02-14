package simu.model;

import simu.framework.*;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	//14/02 testaan toimiiko booleaneilla asiakkaiden käyttäytymisen määrittely
	private boolean kauppaKäyty = false;
	private boolean reseptiKäyty = false;
	private boolean asiakaspalveluKäyty = false;

	public Asiakas(){
	    id = i++;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+ saapumisaika);
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	public int getId() {
		return id;
	}
	//getters for palvelupisteet, idea is to use this as a condition later on
	public boolean isAsiakaspalveluKäyty() {
		return asiakaspalveluKäyty;
	}

	public boolean isKauppaKäyty() {
		return kauppaKäyty;
	}

	public boolean isReseptiKäyty() {
		return reseptiKäyty;
	}
	//setters for palvelupisteet, set käyty = true kun palvelu saatu
	public void setAspaKäyty() {
		this.asiakaspalveluKäyty = true;
	}
	public void setKauppatKäyty() {
		this.kauppaKäyty = true;
	}
	public void setReseptiKäyty() {
		this.reseptiKäyty = true;
	}
	//not essential method, just here for displaying results
	public String displayUsedServices() {
		return "Used aspa: " + isAsiakaspalveluKäyty() + ", used kauppa: " + isKauppaKäyty() + ", used resepti: " + isReseptiKäyty();
	}


	public void raportti(){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " +(poistumisaika-saapumisaika));
		//added display for every customer to see functionality of booleans
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " " + displayUsedServices());
		sum += (poistumisaika-saapumisaika);
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti "+ keskiarvo);

	}

}
