package simu.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Visualisointi implements IVisualisointi{

	private final GraphicsContext gc;
	
	double i = 0;
	double j = 10;

	private Canvas cnv;

	int asiakasLkm = 0;


	public Visualisointi(Canvas cnv) {
		this.cnv = cnv;
		this.gc = cnv.getGraphicsContext2D();
		tyhjennaNaytto();
	}
	

	public void tyhjennaNaytto() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, cnv.getWidth(), cnv.getHeight());
	}
	
	public void uusiAsiakas() {
		gc.setFill(Color.BLUE);
		gc.fillOval(i,j,10,10);

		i = i + 10;
		if (i >= cnv.getWidth()) {i = 0; j+=10;}

	}
	public void menetettyAsiakas() {
		gc.setFill(Color.RED);
		gc.fillOval(i,j,10,10);

		i = (i + 10) % cnv.getWidth();
		//j = (j + 12) % this.getHeight();
		if (i==0) j+=10;
	}

	public void reset() {
		i = 0;
		j = 10;
	}

	
}
