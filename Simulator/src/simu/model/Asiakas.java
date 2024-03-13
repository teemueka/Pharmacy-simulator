package simu.model;

import simu.framework.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private final int id;
	private static int i = 1;
	private static long sum = 0;
	private boolean kauppaKayty = false;
	private boolean reseptiKayty = false;
	private boolean asiakaspalveluKayty = false;
	//time tracking all services
	private double aikaPalveluissa;
	//time tracking for each service on their own
	private double aikaAulassa;
	private double aikaAspassa;
	private double aikaReseptissa;
	private double aikaKaupassa;
	private double aikaKassalla;
	private final int numOfServices;
	private static int usedOnlyAspa;
	//spending per service + all spending
	private int kauppaSpent;
	private int reseptiSpent;
	private int totalSpent;
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
		System.out.println(getServices());
	}

	public List<String> getServices() {
		return services;
	}
	public String getNextService() {
		return services.remove(0);
	}
	public int getSpent() {
		return totalSpent;
	}
	public boolean onlyAspa() {
		return !kauppaKayty && !reseptiKayty && asiakaspalveluKayty;
	}
	public void usedOnlyAspa() {
		if (onlyAspa()) {
			usedOnlyAspa++;
			System.out.println("Asiakas: " + id + " used aspa: " + getAspaKayty() + ", used kauppa: " + getKauppaKayty() + ", used resepti: " + getReseptiKayty());
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
	public void setTyytyvaisyys(Apteekki a) {
        //this is a value that determines the satisfaction of the customers
        //VERY IMPORTANT TO SET THIS TO REASONABLE VALUE
        //this is in straight correlation with palvelupiste servicetime distributions
        int tavoiteJonotus = 50;
        if (((poistumisaika-saapumisaika-aikaPalveluissa) / numOfServices) > tavoiteJonotus) {
			a.addDissatisfiedCustomer();
		} else {
			a.addSatisfiedCustomer();
		}
	}
	public String getInfo() {
		return "jonotus: " + (poistumisaika-saapumisaika-aikaPalveluissa) + ", palvelujen määrä: " + numOfServices;
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
				aikaReseptissa = palveluaika;
				break;
			case "Kassa":
				aikaKassalla = palveluaika;
				break;
		}
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
	public boolean getAspaKayty() {
		return asiakaspalveluKayty;
	}

	public boolean getKauppaKayty() {
		return kauppaKayty;
	}

	public boolean getReseptiKayty() {
		return reseptiKayty;
	}
	//setters for palvelupisteet, set käyty = true kun palvelu saatu
	public void setAspaKayty() {
		this.asiakaspalveluKayty = true;
	}
	public void setKauppaKayty() {
		this.kauppaKayty = true;
	}
	public void setReseptiKayty() {
		this.reseptiKayty = true;
	}
	public String displayUsedServices() {
		return "Used aspa: " + getAspaKayty() + ", used kauppa: " + getKauppaKayty() + ", used resepti: " + getReseptiKayty();
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
	public int getTotalSpent() {
		return totalSpent;
	}
	public static int getTotalSpentAllCustomers() {
		return totalSpentAllCustomers;
	}

	public void raportti(Apteekki apteekki){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " + saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " + poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " + (poistumisaika-saapumisaika));
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi jonottamassa: " + (poistumisaika-saapumisaika-aikaPalveluissa));
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi palveluissa: " + aikaPalveluissa);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi kulutti: " + getTotalSpent() + " €");
		Trace.out(Trace.Level.INFO,getInfo());
		Trace.out(Trace.Level.INFO,"Asiakas kävi vain aspassa: " + onlyAspa());
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " " + displayUsedServices());
		sum += (long) (poistumisaika-saapumisaika);
		double keskiarvo = (double) sum / apteekki.getServedCustomers();
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
	}

	public static void reset(){
		i = 1;
		sum = 0;
		totalSpentAllCustomers = 0;
		usedOnlyAspa = 0;
	}

}
