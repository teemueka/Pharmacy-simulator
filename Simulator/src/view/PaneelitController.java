package view;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

public class PaneelitController {
	
	@FXML
	private Canvas visu;	// Käyttöliittymäkomponentti
	private IVisualisointi visualisointi = null; // Työjuhta
	
	
	@FXML
	private Button startButton;
	@FXML
	private Button simuloiButton;
	
	MainApp mainApp;

	
	public void handleStart() {
		System.out.println("Start");
		if (visualisointi == null){
			visualisointi = new Visualisointi(visu);
			visualisointi.tyhjennaNaytto();
		}
		startButton.setDisable(true);
	}
	
	
	public void handleSimuloi() {
		
		new Thread() {
			public void run() {
				
				for (int i = 1; i <=100; i++) {
					Platform.runLater(()->visualisointi.uusiAsiakas());
					try {
						sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}.start();
		
		
	}
	
		
	 public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;

	 }
}