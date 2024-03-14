package simu.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApteekkiTest {
    @BeforeAll
    static void setUp() {
        System.out.println("Performing tests.");
    }
    @Test
    void addDissatisfiedCustomer() {
        Apteekki apteekki = new Apteekki(10);
        apteekki.addDissatisfiedCustomer();
        apteekki.addDissatisfiedCustomer();
        assertEquals(2, apteekki.getDissatisfiedCustomers());
    }

    @Test
    void addMissedCustomer() {
        Apteekki apteekki = new Apteekki(10);
        apteekki.addMissedCustomer();
        apteekki.addMissedCustomer();
        assertEquals(2, apteekki.getMissedCustomers());
    }

    @Test
    void addToPharmacyque() {
        Asiakas asiakas = new Asiakas();
        Asiakas asiakas2 = new Asiakas();
        Apteekki apteekki = new Apteekki(10);
        apteekki.addToPharmacyque(asiakas);
        apteekki.addToPharmacyque(asiakas2);
        assertEquals(2, apteekki.displayApteekkijono());
    }

    @Test
    void getFromPharmacyque() {
        Asiakas asiakas = new Asiakas();
        Asiakas asiakas2 = new Asiakas();
        Apteekki apteekki = new Apteekki(20);
        apteekki.addToPharmacyque(asiakas);
        apteekki.addToPharmacyque(asiakas2);
        apteekki.getFromPharmacyque();
        assertEquals(1, apteekki.displayApteekkijono());
    }
    @Test
    void customerOut(){
        Apteekki apteekki = new Apteekki(10);
        apteekki.addDissatisfiedCustomer();
        apteekki.customerIn();
        apteekki.customerIn();
        apteekki.customerOut();
        assertEquals(1,apteekki.getCurrent_customers());
        assertEquals(1, apteekki.getServedCustomers());
    }
}