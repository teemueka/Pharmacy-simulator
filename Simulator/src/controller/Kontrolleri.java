package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import simu.view.ISimulaattorinUI;

public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV{   // UUSI
	
	private IMoottori moottori; 
	private ISimulaattorinUI ui;
	
	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
		
	}

	
	// Moottorin ohjausta:
		
	@Override
	public void kaynnistaSimulointi() {
		int a = (int) ui.getA_staff();
		int h = (int) ui.getH_staff();
		int r = (int) ui.getR_staff();
		int k = (int) ui.getK_staff();

		moottori = new OmaMoottori(this, a, h, r, k, 150,10); // luodaan uusi moottorisäie jokaista simulointia varten
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		ui.getVisualisointi().tyhjennaNaytto();
		((Thread)moottori).start();
		//((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?		
	}
	
	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*1.10));
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*0.9));
	}
	
	
	
	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:
		
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(()->ui.setLoppuaika(aika)); 
	}

	@Override
	public void naytaPalveltu(int asiakas) {
		Platform.runLater(()->ui.setPalveltu(asiakas));
	}

	@Override
	public void naytaMenetetty(int asiakas) {
		Platform.runLater(()->ui.setMenetetty(asiakas));
	}


	@Override
	public void visualisoiMenetettyAsiakas() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualisointi().menetettyAsiakas();
			}
		});
	}

	@Override
	public void visualisoiUusiAsiakas() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualisointi().uusiAsiakas();
			}
		});
	}

	@Override
	public void simulationDone() {

	}


}
