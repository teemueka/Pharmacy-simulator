package controller;

public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:


    // Visualliset teksi osuudet
    public void naytaLoppuaika(double aika);
    void naytaPalveltu(int servedCustomers);

    void naytaMenetetty(int missedCustomers);







    public void updateTyytyvaisyys(double v);

    public void updateSuuJokaLiikkuu(double v);

    public void updateAulaJonoPituus(double aulaJonoPit);

    public void updateKassaJonoPituus(double kassaJonoPit);
    public void updateReseptiJonoPituus(double reseptiJonoPit);

    public void updateHyllyJonoPituus(double hyllyJonoPit);

    public void updateInfoJonoPituus(double infoJonoPit);


    void simulationDone();
}
