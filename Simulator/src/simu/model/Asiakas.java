package simu.model;

import simu.framework.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * The Asiakas class represents a customer entity.
 * <p>
 * It tracks various attributes and behaviors of customers during their interaction with the pharmacy system.
 * </p>
 */
public class Asiakas {

	/**
	 * The arrival time of the customer.
	 */
	private double saapumisaika;

	/**
	 * The departure time of the customer.
	 */
	private double poistumisaika;

	/**
	 * The unique identifier of the customer.
	 */
	private final int id;

	/**
	 * Static variable to keep track of customer identifiers.
	 */
	private static int i = 1;

	/**
	 * Static variable to keep track of the total time spent by all customers.
	 */
	private static long sum = 0;

	/**
	 * Indicates whether the customer has visited the store.
	 */
	private boolean kauppaKayty = false;

	/**
	 * Indicates whether the customer has visited the prescription.
	 */
	private boolean reseptiKayty = false;

	/**
	 * Indicates whether the customer has visited the customer service.
	 */
	private boolean asiakaspalveluKayty = false;

	/**
	 * Total time spent by the customer in all services.
	 */
	private double aikaPalveluissa;

	/**
	 * Time spent by the customer in the waiting area.
	 */
	private double aikaAulassa;

	/**
	 * Time spent by the customer in customer service.
	 */
	private double aikaAspassa;

	/**
	 * Time spent by the customer in the prescription service.
	 */
	private double aikaReseptissa;

	/**
	 * Time spent by the customer in the store.
	 */
	private double aikaKaupassa;

	/**
	 * Time spent by the customer at the cashier.
	 */
	private double aikaKassalla;

	/**
	 * The number of services the customer requires.
	 */
	private final int numOfServices;

	/**
	 * Static variable that counts the number of customers who visited only the customer service.
	 */
	private static int usedOnlyAspa;

	/**
	 * The amount spent by the customer at the store.
	 */
	private int kauppaSpent;

	/**
	 * The amount spent by the customer on prescriptions.
	 */
	private int reseptiSpent;

	/**
	 * The total amount spent by the customer.
	 */
	private int totalSpent;

	/**
	 * The total amount spent by all customers.
	 */
	private static int totalSpentAllCustomers;

	/**
	 * The list of services available in the pharmacy.
	 */
	private List<String> services = Arrays.asList("Asiakaspalvelu", "Hyllyt", "Resepti");

	/**
	 * The average service time of the customers.
	 */
	private double keskiarvo;


	/**
	 * Constructs a customer object with a unique identifier and randomly selected services.
	 */
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

	/**
	 * Retrieves the next service from the customer's service list and removes it.
	 *
	 * @return The next service the customer requires.
	 */
	public String getNextService() {
		return services.remove(0);
	}

	/**
	 * Retrieves the total amount spent by the customer.
	 *
	 * @return The total amount spent by the customer.
	 */
	public int getSpent() {
		return totalSpent;
	}

	/**
	 * Checks if the customer only visited the customer service.
	 *
	 * @return True if the customer only visited the customer service, otherwise false.
	 */
	public boolean onlyAspa() {
		return !kauppaKayty && !reseptiKayty && asiakaspalveluKayty;
	}

	/**
	 * Increments the count of customers who only visited the customer service.
	 */
	public void usedOnlyAspa() {
		if (onlyAspa()) {
			usedOnlyAspa++;
		}
	}

	/**
	 * Checks if the customer has more services to be served.
	 *
	 * @return True if the customer has more services, otherwise false.
	 */
	public boolean hasMoreServices() {
		return !services.isEmpty();
	}

	/**
	 * Sets the total time spent by the customer in all services.
	 *
	 * @param palveluaika The time spent by the customer in all services.
	 */
	public void setKokonaisPalveluaika(double palveluaika) {
		this.aikaPalveluissa += palveluaika;
	}

	/**
	 * Sets the customer's satisfaction based on the service time and the specified target waiting time.
	 *
	 * @param a The pharmacy to which the customer belongs.
	 */
	public void setTyytyvaisyys(Apteekki a) {
        int tavoiteJonotus = 50;
        if (((poistumisaika-saapumisaika-aikaPalveluissa) / numOfServices) > tavoiteJonotus) {
			a.addDissatisfiedCustomer();
		} else {
			a.addSatisfiedCustomer();
		}
	}

	/**
	 * Retrieves information about the customer's waiting time and the number of services.
	 *
	 * @return Information about the customer's waiting time and the number of services.
	 */
	public String getInfo() {
		return "jonotus: " + (poistumisaika-saapumisaika-aikaPalveluissa) + ", palvelujen määrä: " + numOfServices;
	}
	//adds service time for each service point

	/**
	 * Sets the service time for each service point.
	 *
	 * @param servicePoint The service point where the service was provided.
	 * @param palveluaika  The time spent by the customer at the specified service point.
	 */
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


	/**
	 * Sets the departure time of the customer.
	 *
	 * @param poistumisaika The departure time of the customer.
	 */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	/**
	 * Sets the average service time.
	 *
	 * @param keskiarvo The average service time.
	 */
	public void setKeskiarvo(double keskiarvo) {
		this.keskiarvo += keskiarvo;
	}

	/**
	 * Retrieves the average service time.
	 *
	 * @return The average service time.
	 */
	public double getKeskiarvo() {
		return keskiarvo;
	}

	/**
	 * Retrieves the unique identifier of the customer.
	 *
	 * @return The unique identifier of the customer.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Retrieves whether the customer visited the customer service.
	 *
	 * @return True if the customer visited the customer service, otherwise false.
	 */
	public boolean getAspaKayty() {
		return asiakaspalveluKayty;
	}

	/**
	 * Retrieves whether the customer visited the store.
	 *
	 * @return True if the customer visited the store, otherwise false.
	 */
	public boolean getKauppaKayty() {
		return kauppaKayty;
	}

	/**
	 * Retrieves whether the customer visited the prescription.
	 *
	 * @return True if the customer visited the prescription, otherwise false.
	 */
	public boolean getReseptiKayty() {
		return reseptiKayty;
	}

	/**
	 * Marks the customer as having visited the customer service.
	 */
	public void setAspaKayty() {
		this.asiakaspalveluKayty = true;
	}

	/**
	 * Marks the customer as having visited the store.
	 */
	public void setKauppaKayty() {
		this.kauppaKayty = true;
	}

	/**
	 * Marks the customer as having visited the prescription.
	 */
	public void setReseptiKayty() {
		this.reseptiKayty = true;
	}

	/**
	 * Displays the services used by the customer.
	 *
	 * @return Information about the services used by the customer.
	 */
	public String displayUsedServices() {
		return "Used aspa: " + getAspaKayty() + ", used kauppa: " + getKauppaKayty() + ", used resepti: " + getReseptiKayty();
	}

	/**
	 * Sets the amount spent by the customer at the store and adds it to overall spending.
	 */
	public void setKauppaSpent() {
		int save = ThreadLocalRandom.current().nextInt(10, 50);
		this.kauppaSpent = save;
		this.totalSpent += save;
		totalSpentAllCustomers += save;
	}

	/**
	 * Sets the amount spent by the customer at the prescription counter and adds it to overall spending.
	 */
	public void setReseptiSpent() {
		int save = ThreadLocalRandom.current().nextInt(30,100);
		this.reseptiSpent = save;
		this.totalSpent += save;
		totalSpentAllCustomers += save;
	}

	/**
	 * Retrieves the total amount spent by the customer.
	 *
	 * @return The total amount spent by the customer.
	 */
	public int getTotalSpent() {
		return totalSpent;
	}

	/**
	 * Retrieves the total amount spent by all customers.
	 *
	 * @return The total amount spent by all customers.
	 */
	public static int getTotalSpentAllCustomers() {
		return totalSpentAllCustomers;
	}

	/**
	 * Generates a report for the customer's visit.
	 *
	 * @param apteekki The pharmacy where the customer was served.
	 */
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
		setKeskiarvo(keskiarvo);
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
	}

	/**
	 * Resets the static counters and variables associated with the customer class.
	 */
	public static void reset(){
		i = 1;
		sum = 0;
		totalSpentAllCustomers = 0;
		usedOnlyAspa = 0;
	}

}
