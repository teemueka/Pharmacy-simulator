package simu.model;

import java.util.LinkedList;

public class Apteekki {
    //Apteekin kapasiteetti, tätä käytetään ehtona pääseekä asiakas sisään apteekkiin vai jonottaako ulkona
    private final int capacity;
    //tällä pidetään yllä ketkä ovat päässeet sisään, sisälle tultaessa ++ poistuessaan --
    private static int current_customers = 0;
    private static int missedCustomers = 0;
    private static int servedCustomers = 0;
    private static int lostRevenue = 0;
    LinkedList<Asiakas> apteekkijono = new LinkedList<>();

    public Apteekki(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrent_customers() {
        return current_customers;
    }

    public static int getServedCustomers() {
        return servedCustomers;
    }

    public static int getMissedCustomers() {
        return missedCustomers;
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
    public static int getLostRevenue() {
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
