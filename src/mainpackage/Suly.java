package mainpackage;

import java.util.Random;

public class Suly {
	private double w;
	private double[] gradiens = new double[1];
	
	public Suly() {
		Random r = new Random();
		w = r.nextDouble()-0.5;
		for (int i = 0; i < gradiens.length; i++) {
			gradiens[i] = 0.0;
		}
	}
	
	public double AtlagGradiens() {
		double atlag = 0.0;
		for (int i = 0; i < gradiens.length; i++) {
			atlag = atlag + gradiens[i];
		}
		double size = (double)(gradiens.length);
		return atlag/size;
	}
	
	public void NullazGradiens() {
		for (int i = 0; i < gradiens.length; i++) {
			gradiens[i] = 0.0;
		}
	}
	
	public double getW() {
		return w;
	}
	
	public void setW(double w) {
		this.w = w;
	}
	
	public double[] getGradiens() {
		return gradiens;
	}
	
	public void setGradiens(double[] gradiens) {
		this.gradiens = gradiens;
	}
}
