package controller;

public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:


    // Visualliset teksi osuudet
    public void naytaLoppuaika(double aika);
    void naytaPalveltu(int servedCustomers);

    void naytaMenetetty(int missedCustomers);

    void naytaTyytyvaisyys(double satisfaction);

    void naytaAverage(double average);




    //Näytöllä olevat osuudet
    public void visualisoiMenetettyAsiakas();

    public void visualisoiUusiAsiakas();


    void simulationDone();
}
