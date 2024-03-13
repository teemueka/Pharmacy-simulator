package controller;

public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:


    // Visualliset teksi osuudet
    public void naytaLoppuaika(double aika);
    void naytaPalveltu(int servedCustomers);

    void naytaMenetetty(int missedCustomers);





    //Näytöllä olevat osuudet
    public void visualisoiMenetettyAsiakas();

    public void visualisoiUusiAsiakas();


    void simulationDone();
}
