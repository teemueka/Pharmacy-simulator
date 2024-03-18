package controller;

/**
 * Interface for the controller of the model
 */

public interface IKontrolleriForM {

    /**
     * Updates the visual element of the satisfaction
     * @param v satisfaction
     */
    public void updateTyytyvaisyys(double v);

    /**
     * Updates the moving mouth of the smiley face and color of the face
     * @param v satisfaction value
     */
    public void updateSuuJokaLiikkuu(double v);

    /**
     * Updates the length of the queue bar of the aula
     * @param aulaJonoPit length of the queue
     */
    public void updateAulaJonoPituus(double aulaJonoPit);

    /**
     * Updates the length of the queue bar of the kassa
     * @param kassaJonoPit length of the queue
     */
    public void updateKassaJonoPituus(double kassaJonoPit);

    /**
     * Updates the length of the queue bar of the resepti
     * @param reseptiJonoPit length of the queue
     */
    public void updateReseptiJonoPituus(double reseptiJonoPit);

    /**
     * Updates the length of the queue bar of the hylly
     * @param hyllyJonoPit length of the queue
     */
    public void updateHyllyJonoPituus(double hyllyJonoPit);

    /**
     * Updates the length of the queue bar of the info
     * @param infoJonoPit length of the queue
     */
    public void updateInfoJonoPituus(double infoJonoPit);

    /**
     * simulationDone() is called when the simulation is done.
     */
    void simulationDone();

    /**
     * updateUI() is called to update the UI with the simulation data.
     * @param aspaTyontekijat is the number of employees in the customer service.
     * @param hyllyTyontekijat	is the number of employees in the shelves.
     * @param reseptiTyontekijat	is the number of employees in the prescription.
     * @param kassaTyontekijat	is the number of employees in the checkout.
     * @param servedCustomers	is the number of customers served.
     * @param missedCustomers	is the number of customers missed.
     * @param aspaUsage	is the usage of the customer service.
     * @param kauppaUsage	is the usage of the shelves.
     * @param reseptiUsage	is the usage of the prescription.
     * @param kassaUsage	is the usage of the checkout.
     * @param satisfiedCustomers	is the number of satisfied customers.
     * @param dissatisfiedCustomers	is the number of dissatisfied customers.
     * @param overallSatisfaction	is the overall satisfaction of the customers.
     * @param aspaUtilization	is the utilization of the customer service.
     * @param kauppaUtilization	is the utilization of the shelves.
     * @param reseptiUtilization	is the utilization of the prescription.
     * @param kassaUtilization	is the utilization of the checkout.
     * @param keskiarvo	is the average time of the customers.
     */
    void updateUI(int aspaTyontekijat, int hyllyTyontekijat, int reseptiTyontekijat, int kassaTyontekijat, int servedCustomers, int missedCustomers, int aspaUsage, int kauppaUsage, int reseptiUsage, int kassaUsage, int satisfiedCustomers, int dissatisfiedCustomers, double overallSatisfaction, double aspaUtilization, double kauppaUtilization, double reseptiUtilization, double kassaUtilization, double keskiarvo);
}
