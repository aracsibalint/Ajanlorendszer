package mainpackage;

public class Neu {

	private double inh = 0.0;
	private double outh = 0.0;
	private Suly[] elsosulyok = new Suly[15];
	private Suly[] masodiksulyok = new Suly[1];
	
	public Neu() {
		for (int i = 0; i < elsosulyok.length; i++) {
			Suly suly = new Suly();
			elsosulyok[i] = suly;
		}
		for (int j = 0; j < masodiksulyok.length; j++) {
			Suly suly = new Suly();
			masodiksulyok[j] = suly;
		}
	}
	
	public double getInh() {
		return inh;
	}
	
	public void setInh(double inh) {
		this.inh = inh;
	}
	
	public double getOuth() {
		return outh;
	}
	
	public void setOuth(double outh) {
		this.outh = outh;
	}
	
	public Suly[] getElsosulyok() {
		return elsosulyok;
	}
	
	public void setElsosulyok(Suly[] sulyok) {
		this.elsosulyok = sulyok;
	}

	public Suly[] getMasodiksulyok() {
		return masodiksulyok;
	}

	public void setMasodiksulyok(Suly[] masodiksulyok) {
		this.masodiksulyok = masodiksulyok;
	}		
}
