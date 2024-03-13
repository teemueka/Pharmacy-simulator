package simu.model;

import java.util.LinkedList;

public class Apteekki {
    //Apteekin kapasiteetti, tätä käytetään ehtona pääseekä asiakas sisään apteekkiin vai jonottaako ulkona
    private final int capacity;
    private int current_customers = 0;
    private int missedCustomers = 0;
    private int servedCustomers = 0;
    private int lostRevenue = 0;
    private int satisfiedCustomers;
    private int dissatisfiedCustomers;
    LinkedList<Asiakas> apteekkijono = new LinkedList<>();

    public Apteekki(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrent_customers() {
        return current_customers;
    }

    public int getServedCustomers() {
        return servedCustomers;
    }

    public int getMissedCustomers() {
        return missedCustomers;
    }
    public void addSatisfiedCustomer() {
        satisfiedCustomers++;
    }
    public void addDissatisfiedCustomer() {
        dissatisfiedCustomers++;
    }
    public int getSatisfiedCustomers() {
        return satisfiedCustomers;
    }
    public int getDissatisfiedCustomers() {
        return dissatisfiedCustomers;
    }
    public double getOverallSatisfaction() {
        return (double) satisfiedCustomers / servedCustomers * 100;
    }

    public int getCapacity() {
        return capacity;
    }
    public void customerIn() {
        current_customers++;
    }
    public void customerOut() {
        current_customers--;
        servedCustomers++;
    }
    public int displayApteekkijono() {
        return apteekkijono.size();
    }
    public void addMissedCustomer() {
        missedCustomers++;
        lostRevenue += 62; //62 is the average spending we got on a large sample size
    }
    public int getLostRevenue() {
        return lostRevenue;
    }
    public int displayMissedCustomers() {
        return missedCustomers;
    }
    public void displayResults() {
        System.out.println("Asiakkaita palveltu: " + servedCustomers + ", Asiakkaita menetetty: " + missedCustomers);
    }
    public double missedCustomerChance() {
        return Math.random();
    }
    //lisätään asiakkaita kaupan ulkopuolelle
    public void addToPharmacyque(Asiakas asiakas) {
        apteekkijono.add(asiakas);
    }
    //otetaan jonosta ja tallennetaan nextCustomeriksi
    public Asiakas getFromPharmacyque() {
        if (!apteekkijono.isEmpty()) {
            Asiakas nextC = apteekkijono.poll();
            System.out.println("Asiakas " + nextC.getId() + " pääsi sisään jonosta");
            return nextC;
        }
        return null;
    }
}
