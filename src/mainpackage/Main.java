package mainpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		NeuronNetwork netw = new NeuronNetwork();
		
		int bemenetszam = 15;
		
		double minimum = 100000.0;
		double maximum = -100000.0;
		
		/*
		double[][] ip = new double [12][4];
		double[][] up = new double[12][2];
				
		double alfa = 0.1;
		
		for (int j = 0; j < 12; j++) {
			
			Random r = new Random();			
			double x2 = r.nextDouble();
			double x3 = r.nextDouble();
			//ip[j][0] = x1;
			//netw.getInputs()[j][0] = x1;
			
			ip[j][0] = x2;			
			ip[j][1] = x3;	
			ip[j][2] = Math.pow(x2, 2);
			ip[j][3] = Math.pow(x3, 2);
			up[j][0] = (Math.pow(x2, 2)+Math.pow(x3, 2))/2.0;
			up[j][1] = -1.0 * up[j][0];
		}
		*/
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		double[][] bemenetek = new double[19][bemenetszam];
		double[][] kimenetek = new double[19][1];
		
		int sgd1 = 0;
		int hatar1 = 19;
		while (sgd1 != hatar1) {
			String line = br.readLine();
			String[] reszek = line.split("\t");
			double[] parts = new double[bemenetszam];
			for (int i = 0; i < reszek.length; i++) {
				if (Double.parseDouble(reszek[i]) < minimum) {
					minimum = Double.parseDouble(reszek[i]);
				}
				if (Double.parseDouble(reszek[i]) > maximum) {
					maximum = Double.parseDouble(reszek[i]);
				}
				parts[i] = (Double.parseDouble(reszek[i]))/20.0;
				parts[i+5] = Math.pow(parts[i], 2);
				parts[i+10] = Math.pow(parts[i], 3);
			}
			bemenetek[sgd1] = parts;
			sgd1++;
		}				
		
		netw.min = minimum;
		netw.max = maximum;
		
		//netw.NormALL();
		
		System.out.println("Tanitobemenet beolvasva");
		
		int sgd2 = 0;
		while (sgd2 != hatar1) {
			String line = br.readLine();
			String[] reszek = line.split(" ");
			double[] parts = new double[3];
			for (int i = 0; i < reszek.length; i++) {
				parts[i] = (Double.parseDouble(reszek[i]));
			}
			kimenetek[sgd2] = parts;
			sgd2++;
		}
		
		System.out.println("Tanitokimenet beolvasva");
		
		double[][] teszt = new double[19][bemenetszam];
		
		/*
		double[] teszt1 = {0.4, 0.6, 0.16, 0.36};
		double[] teszt2 = {0.2, 0.3, 0.04, 0.09};
		
		teszt[0] = teszt1;
		teszt[1] = teszt2;
		*/
		
		int sgd3 = 0;
		int hatar2 = 19;
		
		while (sgd3 != hatar2) {
			String line = br.readLine();
			String[] reszek = line.split("\t");
			double[] parts = new double[bemenetszam];
			for (int i = 0; i < reszek.length; i++) {
				parts[i] = (Double.parseDouble(reszek[i]))/20.0;
				parts[i+5] = Math.pow(parts[i], 2);
				parts[i+10] = Math.pow(parts[i], 3);
			}
			teszt[sgd3] = parts;
			sgd3++;
		}
		
		System.out.println("Tesztbemenet beolvasva");
		
		netw.setInputs(bemenetek);
		netw.setOutputs(kimenetek);								
						
		int iter = 10000;
		double alfa = 0.1;
		
		for (int zs = 0; zs < iter ; zs++) {
			
			int ert = 0;
			
			for (int i = 0; i < netw.getInputs().length; i++) {								
				
				for (int j = 0; j < netw.getNeurons().length; j++) {				
					for (int k = 0; k < netw.getInputs()[i].length; k++) {					
						netw.getNeurons()[j].setInh(netw.getNeurons()[j].getInh() + 
								netw.getNeurons()[j].getElsosulyok()[k].getW() * netw.getInputs()[i][k]);					
					}			
															
					netw.getNeurons()[j].setOuth((netw.getNeurons()[j].getInh()));	
										
				}
										
				for (int j = 0; j < netw.getOutputneuron().length; j++) {
					for (int k = 0; k < netw.getNeurons().length; k++) {
						netw.getOutputneuron()[j].setInh(netw.getOutputneuron()[j].getInh() +
								netw.getNeurons()[k].getMasodiksulyok()[j].getW()*netw.getNeurons()[k].getOuth());
					}
					
					netw.getOutputneuron()[j].setOuth(netw.ReLu(netw.getOutputneuron()[j].getInh()));
				}
				
				for (int j = 0; j < netw.getNeurons().length; j++) {
					
					double tmp = 0.0;
					
					for (int k = 0; k < netw.getOutputneuron().length; k++) {
						tmp = tmp + (netw.getOutputs()[i][k] - netw.getOutputneuron()[k].getOuth()) * 
								//netw.getOutputneuron()[k].getOuth() * (1.0 - netw.getOutputneuron()[k].getOuth()) *
								netw.getNeurons()[j].getMasodiksulyok()[k].getW();
					}
					
					
					for (int k = 0; k < netw.getInputs()[i].length; k++) {
						
						netw.getNeurons()[j].getElsosulyok()[k].getGradiens()[ert] = 
								1.0*
								tmp *
								netw.getInputs()[i][k];																																																						
					}
					
					for (int k = 0; k < netw.getOutputneuron().length; k++) {
						netw.getNeurons()[j].getMasodiksulyok()[k].getGradiens()[ert] = 
								(netw.getOutputs()[i][k] - netw.getOutputneuron()[k].getOuth()) *
								//netw.getOutputneuron()[k].getOuth() * (1.0 - netw.getOutputneuron()[k].getOuth()) *
								netw.getNeurons()[j].getOuth();
					}
						
				}		
				
				
				if (i%1 == 0) {
					ert = -1;
					for (int i1 = 0; i1 < netw.getNeurons().length; i1++) {
						for (int j = 0; j < netw.getNeurons()[i1].getElsosulyok().length; j++) {
							netw.getNeurons()[i1].getElsosulyok()[j].setW(netw.getNeurons()[i1].getElsosulyok()[j].getW() + alfa*netw.getNeurons()[i1].getElsosulyok()[j].AtlagGradiens());
							netw.getNeurons()[i1].getElsosulyok()[j].NullazGradiens();
						}
					}
					for (int i2 = 0; i2 < netw.getNeurons().length; i2++) {
						for (int j = 0; j < netw.getNeurons()[i2].getMasodiksulyok().length; j++) {
							netw.getNeurons()[i2].getMasodiksulyok()[j].setW(netw.getNeurons()[i2].getMasodiksulyok()[j].getW() + alfa*netw.getNeurons()[i2].getMasodiksulyok()[j].AtlagGradiens());
							netw.getNeurons()[i2].getMasodiksulyok()[j].NullazGradiens();
						}												
					}
				}
				
				netw.Nullaz();
				ert++;				
			}
			
		}
		
		/*
		for (int i = 0; i < netw.getNeurons().length; i++) {
			System.out.println("Belsõ súlyok: ");
			for (int j = 0; j < netw.getNeurons()[i].getElsosulyok().length; j++) {
				System.out.println(i + "-edik neuron" + j + "-edik sulya: " + netw.getNeurons()[i].getElsosulyok()[j].getW());
			}
			System.out.println("Külsõ súlyok: ");
			for (int j = 0; j < netw.getNeurons()[i].getMasodiksulyok().length; j++) {
				System.out.println(i + "-edik neuron" + j + "-edik sulya: " + netw.getNeurons()[i].getMasodiksulyok()[j].getW());
			}
		}
		*/
		
		
		
		for (int o = 0; o < teszt.length; o++) {
			double[] resz = teszt[o];
			for (int j = 0; j < netw.getNeurons().length; j++) {				
				for (int k = 0; k < resz.length; k++) {					
					netw.getNeurons()[j].setInh(netw.getNeurons()[j].getInh() + 
							netw.getNeurons()[j].getElsosulyok()[k].getW() * resz[k]);					
				}			
														
				netw.getNeurons()[j].setOuth((netw.getNeurons()[j].getInh()));	
								
			}
									
			for (int j = 0; j < netw.getOutputneuron().length; j++) {
				for (int k = 0; k < netw.getNeurons().length; k++) {
					netw.getOutputneuron()[j].setInh(netw.getOutputneuron()[j].getInh() +
							netw.getNeurons()[k].getMasodiksulyok()[j].getW()*netw.getNeurons()[k].getOuth());
				}
				
				netw.getOutputneuron()[j].setOuth(netw.ReLu(netw.getOutputneuron()[j].getInh()));
			}
			
			System.out.println(netw.getOutputneuron()[0].getOuth() + " " 
					//+ netw.getOutputneuron()[1].getOuth() + " " + 
					//netw.getOutputneuron()[2].getOuth()
					);
			
			netw.Nullaz();
			
		}
		
		/*
		int sgd3 = 0;
		int hatar2 = 4252;
		
		while (sgd3 != hatar2) {
			String line = br.readLine();
			double max = 0.0;
			double[] reszek = new double[81];
			String[] reszek2 = line.split("\t");
			for (int i = 0; i < reszek2.length; i++) {
				double x = Double.parseDouble(reszek2[i]);
				if (Math.abs(x) > max) {
					max = x;
				}
				reszek[i] = x;
			}
			
			for (int j = 0; j < reszek.length; j++) {
				reszek[j] = (reszek[j])/(max);
			}			
			
			for (int j = 0; j < netw.getNeurons().length; j++) {				
				for (int k = 0; k < reszek.length; k++) {					
					netw.getNeurons()[j].setInh(netw.getNeurons()[j].getInh() + 
							netw.getNeurons()[j].getSulyok()[k].getW() * reszek[k]);					
				}				
				netw.getNeurons()[j].setOuth(netw.Sigmoid(netw.getNeurons()[j].getInh()));	
				netw.getOutputneuron().setInh(netw.getOutputneuron().getInh() + 
						netw.getNeurons()[j].getOuth() * netw.getOutputneuron().getSulyok()[j].getW());
			}
											
			netw.getOutputneuron().setOuth(netw.getOutputneuron().getInh());
			
			System.out.println(netw.getOutputneuron().getOuth());
			
			netw.Nullaz();
			
			sgd3++;
		}	*/			
	}
	
	public static double Sigmoid(double data) {
		return 1.0/(1.0 + Math.exp(data*(-1)));
	}
	
	public static double Normalize(double data, double min, double max) {
		return (data - min)/(max - min);
	}
	
	public static double ReNormalize(double data, double min, double max) {
		return data*(max-min) + min;
	}
}
