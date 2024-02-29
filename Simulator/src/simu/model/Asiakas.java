package simu.model;

import simu.framework.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	//14/02 testaan toimiiko booleaneilla asiakkaiden käyttäytymisen määrittely
	private int aulaKäyty = 0;
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
	private static int satisfied;
	private static int dissatisfied;
	private final int numOfServices;
	private static int customerAmount;
	private static int usedOnlyAspa;
	//spending per service + all spending
	private int kauppaSpent;
	private int reseptiSpent;
	private int totalSpent;
	//spending by all the customers
	private static int totalSpentAllCustomers;
	private List<String> services = Arrays.asList("Asiakaspalvelu", "Hyllyt", "Resepti");


	public Asiakas(){
	    id = i++;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+ saapumisaika);
		//random services the customer wants, ensuring at least 1 service
		int numOfServices = 1 + (int) (Math.random() * (services.size()));
		this.numOfServices = numOfServices;
		//randomize the order
		Collections.shuffle(services);
		//trim the list to the desired number of services
		services = new ArrayList<>(services.subList(0, numOfServices));
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " aikoo käydä palveluissa: " + services);
	}
	public String nextService() {
		return services.get(0);
	}
	public String getNextService() {
		return services.remove(0);
	}
	public int getSpent() {
		return totalSpent;
	}
	public boolean onlyAspa() {
		return !kauppaKäyty && !reseptiKäyty && asiakaspalveluKäyty;
	}
	public void usedOnlyAspa() {
		if (onlyAspa()) {
			usedOnlyAspa++;
			System.out.println("Asiakas: " + id + " used aspa: " + getAspaKäyty() + ", used kauppa: " + getKauppaKäyty() + ", used resepti: " + getReseptiKäyty());
		}
	}
	public static int getUsedOnlyAspa() {
		return usedOnlyAspa;
	}

	public boolean hasMoreServices() {
		return !services.isEmpty();
	}
	public void setKokonaisPalveluaika(double palveluaika) {
		this.aikaPalveluissa += palveluaika;
	}
	public void setTyytyväisyys() {
		if (((poistumisaika-saapumisaika-aikaPalveluissa) / numOfServices) > 50) {
			dissatisfied++;
			customerAmount++;
		} else {
			satisfied++;
			customerAmount++;
		}
	}
	public static int getCustomerAmount() {
		return customerAmount;
	}
	public String getInfo() {
		return "jonotus: " + (poistumisaika-saapumisaika-aikaPalveluissa) + ", palvelujen määrä: " + numOfServices;
	}
	public static int getSatisfied() {
		return satisfied;
	}
	public static int getDissatisfied() {
		return dissatisfied;
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
	//save customer spending and total spending
	public void setKauppaSpent() {
		int save = ThreadLocalRandom.current().nextInt(10, 50);
		this.kauppaSpent = save;
		this.totalSpent += save;
		totalSpentAllCustomers += save;
	}
	public void setReseptiSpent() {
		int save = ThreadLocalRandom.current().nextInt(30,100);
		this.reseptiSpent = save;
		this.totalSpent += save;
		totalSpentAllCustomers += save;
	}
	public void setAulaState() {
		this.aulaKäyty += 1;
    }
	public int getAulaState() {
		return aulaKäyty;
	}

	public int getReseptiSpent() {
		return reseptiSpent;
	}

	public int getKauppaSpent() {
		return kauppaSpent;
	}
	public int getTotalSpent() {
		return totalSpent;
	}
	public static int getTotalSpentAllCustomers() {
		return totalSpentAllCustomers;
	}

	public void raportti(){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " +(poistumisaika-saapumisaika));
		//added few extra prints for display
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi jonottamassa: " +(poistumisaika-saapumisaika-aikaPalveluissa));
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi palveluissa: " + aikaPalveluissa);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi kulutti: " + getTotalSpent() + " €");
		Trace.out(Trace.Level.INFO,getInfo());
		Trace.out(Trace.Level.INFO,"Asiakas kävi vain aspassa: " + onlyAspa());


		//added display for every customer to see functionality of booleans
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " " + displayUsedServices());
		sum += (poistumisaika-saapumisaika);
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti "+ keskiarvo);
	}

}
