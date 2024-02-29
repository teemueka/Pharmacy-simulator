package simu.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Visualisointi2 extends Canvas implements IVisualisointi{
	
	private GraphicsContext gc;
	
	int asiakasLkm = 0;
	int menetettyLkm = 0;

	public Visualisointi2(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();
	}
	

	public void tyhjennaNaytto() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void uusiAsiakas() {
		
		asiakasLkm++;
		
		gc.setFill(Color.BLACK);
		gc.fillRect(100,80, 100, 20);
		gc.setFill(Color.BLUE);
		gc.setFont(new Font(20));
		gc.fillText("Asiakas " + asiakasLkm, 100, 100);
		
	}

	public void menetettyAsiakas() {

		menetettyLkm++;

		gc.setFill(Color.BLACK);
		gc.fillRect(200,160, 200, 40);
		gc.setFill(Color.RED);
		gc.setFont(new Font(20));
		gc.fillText("Menetetty asiakas " + menetettyLkm, 200, 200);
	}

	@Override
	public void reset() {

	}


}
