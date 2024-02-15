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
	//time tracking all services
	private double aikaPalveluissa;
	//time tracking for each service on their own
	private double aikaAulassa;
	private double aikaAspassa;
	private double aikaReseptissä;
	private double aikaKaupassa;
	private double aikaKassalla;

	public Asiakas(){
	    id = i++;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+ saapumisaika);
	}
	public void setKokonaisPalveluaika(double palveluaika) {
		this.aikaPalveluissa += palveluaika;
	}
	//adds service time for each service point
	public void setPalveluaika(String servicePoint, double palveluaika) {
		switch (servicePoint) {
			case "Aula":
				aikaAulassa = palveluaika;
				break;
			case "Aspa":
				aikaAspassa = palveluaika;
				break;
			case "Kauppa":
				aikaKaupassa = palveluaika;
				break;
			case "Resepti":
				aikaReseptissä = palveluaika;
				break;
			case "Kassa":
				aikaKassalla = palveluaika;
				break;
		}
	}

	public double getKaikkiPalveluAjat() {
		return aikaAulassa + aikaAspassa + aikaKaupassa + aikaReseptissä + aikaKassalla;
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
	public boolean getAspaKäyty() {
		return asiakaspalveluKäyty;
	}

	public boolean getKauppaKäyty() {
		return kauppaKäyty;
	}

	public boolean getReseptiKäyty() {
		return reseptiKäyty;
	}
	//setters for palvelupisteet, set käyty = true kun palvelu saatu
	public void setAspaKäyty() {
		this.asiakaspalveluKäyty = true;
	}
	public void setKauppaKäyty() {
		this.kauppaKäyty = true;
	}
	public void setReseptiKäyty() {
		this.reseptiKäyty = true;
	}
	//not essential method, just here for displaying results
	public String displayUsedServices() {
		return "Used aspa: " + getAspaKäyty() + ", used kauppa: " + getKauppaKäyty() + ", used resepti: " + getReseptiKäyty();
	}


	public void raportti(){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " +(poistumisaika-saapumisaika));
		//added few extra prints for display
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi jonottamassa: " +(poistumisaika-saapumisaika-aikaPalveluissa));
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi palveluissa: " + aikaPalveluissa);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi palveluissa (onko sama?): " + getKaikkiPalveluAjat());

		//added display for every customer to see functionality of booleans
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " " + displayUsedServices());
		sum += (poistumisaika-saapumisaika);
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti "+ keskiarvo);

	}

}
