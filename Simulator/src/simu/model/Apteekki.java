package simu.model;

import java.util.LinkedList;

/**
 * The Apteekki class represents a pharmacy entity.
 * <p>
 * It manages customer queue outside of the pharmacy, tracks customer satisfaction, pharmacy capacity, served customers, lost customers and handles customer interactions.
 * </p>
 */
public class Apteekki {
    //Apteekin kapasiteetti, tätä käytetään ehtona pääseekä asiakas sisään apteekkiin vai jonottaako ulkona

    /**
     * The capacity of the pharmacy, used as a condition for customers entering the pharmacy or waiting outside.
     */
    private final int capacity;
    /**
     * The current number of customers in the pharmacy.
     */
    private int current_customers = 0;

    /**
     * The number of customers who didn't want to wait outside the pharmacy.
     */
    private int missedCustomers = 0;

    /**
     * The number of customers who have been served by the pharmacy.
     */
    private int servedCustomers = 0;

    /**
     * The amount of revenue lost due to missed customers.
     */
    private int lostRevenue = 0;

    /**
     * The number of customers who are satisfied with the service provided by the pharmacy.
     */
    private int satisfiedCustomers;

    /**
     * The number of customers who are dissatisfied with the service provided by the pharmacy.
     */
    private int dissatisfiedCustomers;

    /**
     * The queue of customers waiting outside the pharmacy.
     */
    LinkedList<Asiakas> apteekkijono = new LinkedList<>();


    /**
     * Constructs a pharmacy with the given capacity.
     *
     * @param capacity The capacity of the pharmacy.
     */
    public Apteekki(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Retrieves the current number of customers in the pharmacy.
     *
     * @return The current number of customers in the pharmacy.
     */
    public int getCurrent_customers() {
        return current_customers;
    }

    /**
     * Retrieves the number of customers served by the pharmacy.
     *
     * @return The number of customers served by the pharmacy.
     */
    public int getServedCustomers() {
        return servedCustomers;
    }

    /**
     * Retrieves the number of customers who were unwilling to wait outside the pharmacy.
     *
     * @return The number of missed customers.
     */
    public int getMissedCustomers() {
        return missedCustomers;
    }

    /**
     * Increments the count of satisfied customers by one.
     */
    public void addSatisfiedCustomer() {
        satisfiedCustomers++;
    }

    /**
     * Increments the count of dissatisfied customers by one.
     */
    public void addDissatisfiedCustomer() {
        dissatisfiedCustomers++;
    }

    /**
     * Retrieves the number of satisfied customers.
     *
     * @return The number of satisfied customers.
     */
    public int getSatisfiedCustomers() {
        return satisfiedCustomers;
    }

    /**
     * Retrieves the number of dissatisfied customers.
     *
     * @return The number of dissatisfied customers.
     */
    public int getDissatisfiedCustomers() {
        return dissatisfiedCustomers;
    }

    /**
     * Calculates the overall satisfaction percentage based on the number of satisfied and served customers.
     *
     * @return The overall satisfaction percentage.
     */
    public double getOverallSatisfaction() {
        return (double) satisfiedCustomers / servedCustomers * 100;
    }

    /**
     * Retrieves the capacity of the pharmacy.
     *
     * @return The capacity of the pharmacy.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Increments the count of current customers when a new customer enters the pharmacy.
     */
    public void customerIn() {
        current_customers++;
    }

    /**
     * Decrements the count of current customers and increments the count of served customers when a customer leaves the pharmacy.
     */
    public void customerOut() {
        current_customers--;
        servedCustomers++;
    }

    /**
     * Retrieves the size of the customer queue waiting outside the pharmacy.
     *
     * @return The size of the customer queue.
     */
    public int displayApteekkijono() {
        return apteekkijono.size();
    }

    /**
     * Increments the count of missed customers and updates the lost revenue based on the average spending.
     */
    public void addMissedCustomer() {
        missedCustomers++;
        lostRevenue += 62; //62 is the average spending we got on a large sample size
    }

    /**
     * Retrieves the amount of lost revenue due to missed customers.
     *
     * @return The amount of lost revenue.
     */
    public int getLostRevenue() {
        return lostRevenue;
    }

    /**
     * Retrieves the number of missed customers.
     *
     * @return The number of missed customers.
     */
    public int displayMissedCustomers() {
        return missedCustomers;
    }
    /**
     * Displays served and missed customers to terminal after the simulation.
     */
    public void displayResults() {
        System.out.println("Asiakkaita palveltu: " + servedCustomers + ", Asiakkaita menetetty: " + missedCustomers);
    }
    /**
     * Returns a random number representing the chance of a missed customer.
     *
     * @return A random number representing the chance of a missed customer.
     */
    public double missedCustomerChance() {
        return Math.random();
    }
    //lisätään asiakkaita kaupan ulkopuolelle
    /**
     * Adds a customer to the pharmacy queue.
     *
     * @param asiakas The customer to be added to the queue.
     */
    public void addToPharmacyque(Asiakas asiakas) {
        apteekkijono.add(asiakas);
    }
    //otetaan jonosta ja tallennetaan nextCustomeriksi
    /**
     * Retrieves the next customer from the pharmacy queue.
     *
     * @return The next customer from the queue, or null if the queue is empty.
     */
    public Asiakas getFromPharmacyque() {
        if (!apteekkijono.isEmpty()) {
            Asiakas nextC = apteekkijono.poll();
            System.out.println("Asiakas " + nextC.getId() + " pääsi sisään jonosta");
            return nextC;
        }
        return null;
    }
}
