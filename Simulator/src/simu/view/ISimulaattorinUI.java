package simu.view;

public interface ISimulaattorinUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getAika();
	public long getViive();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public void setLoppuaika(double aika);

	public void setPalveltu(int asiakas);
	public void setMenetetty(int asiakas);
	public void setTyytyvaisyys(double tyytyvaisyys);
	public void setAverage(double aika);

	public int getA_staff();
	public int getH_staff();
	public int getR_staff();
	public int getK_staff();
	
	// Kontrolleri tarvitsee  
	public IVisualisointi getVisualisointi();

}
