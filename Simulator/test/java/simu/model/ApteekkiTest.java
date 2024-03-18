package simu.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ApteekkiTest is used to test the Apteekki-class
 */

class ApteekkiTest {

    /**
     * setUp is used to set up the tests
     */
    @BeforeAll
    static void setUp() {
        System.out.println("Performing tests.");
    }
    /**
     * tearDown is used to tear down the tests
     */
    @AfterAll
    static void tearDown() {
        System.out.println("All tests done.");
    }

    /**
     * addDissatisfiedCustomer is used to test the method addDissatisfiedCustomer
     * It adds two dissatisfied customers and checks if the number of dissatisfied customers is 2
     */
    @Test
    void addDissatisfiedCustomer() {
        Apteekki apteekki = new Apteekki(10);
        apteekki.addDissatisfiedCustomer();
        apteekki.addDissatisfiedCustomer();
        assertEquals(2, apteekki.getDissatisfiedCustomers());
    }

    /**
     * addMissedCustomer is used to test the method addMissedCustomer
     * It adds two missed customers and checks if the number of missed customers is 2
     */
    @Test
    void addMissedCustomer() {
        Apteekki apteekki = new Apteekki(10);
        apteekki.addMissedCustomer();
        apteekki.addMissedCustomer();
        assertEquals(2, apteekki.getMissedCustomers());
    }

    /**
     * addSatisfiedCustomer is used to test the method addSatisfiedCustomer
     * It adds two satisfied customers and checks if the number of satisfied customers is 2
     */
    @Test
    void addToPharmacyque() {
        Asiakas asiakas = new Asiakas();
        Asiakas asiakas2 = new Asiakas();
        Apteekki apteekki = new Apteekki(10);
        apteekki.addToPharmacyque(asiakas);
        apteekki.addToPharmacyque(asiakas2);
        assertEquals(2, apteekki.displayApteekkijono());
    }

    /**
     * customerIn is used to test the method customerIn
     * It adds two customers and removes one and checks if the number of customers is 1
     */
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

    /**
     * customerIn is used to test the method customerIn
     * It adds two customers in pharmacy and removes one and checks if the number of customers is 1 and the number of served customers is 1
     */
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