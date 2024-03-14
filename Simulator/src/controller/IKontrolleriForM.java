package controller;

public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:


    // Visualliset teksi osuudet
    public void naytaLoppuaika(double aika);
    void naytaPalveltu(int servedCustomers);

    void naytaMenetetty(int missedCustomers);

    void naytaTyytyvaisyys(double satisfaction);

    void naytaAverage(double average);






    public void updateTyytyvaisyys(double v);

    public void updateSuuJokaLiikkuu(double v);

    public void updateAulaJonoPituus(double aulaJonoPit);

    public void updateKassaJonoPituus(double kassaJonoPit);
    public void updateReseptiJonoPituus(double reseptiJonoPit);

    public void updateHyllyJonoPituus(double hyllyJonoPit);

    public void updateInfoJonoPituus(double infoJonoPit);


    void simulationDone();

    void updateUI(int aspaTyontekijat, int hyllyTyontekijat, int reseptiTyontekijat, int kassaTyontekijat, int servedCustomers, int missedCustomers, int aspaUsage, int kauppaUsage, int reseptiUsage, int kassaUsage, int satisfiedCustomers, int dissatisfiedCustomers, double overallSatisfaction, double aspaUtilization, double kauppaUtilization, double reseptiUtilization, double kassaUtilization, double keskiarvo);
}
