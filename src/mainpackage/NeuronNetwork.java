package mainpackage;

public class NeuronNetwork {
	
	private double[][] inputs = new double[19][15];
	private double[][] outputs = new double[19][1];
	private double[] maximum = new double [17011];
	public double min;
	public double max;
	private Neu[] neurons = new Neu[5];		
	private Neu[] outputneurons = new Neu[1];	
	
	public NeuronNetwork() {		
		for (int i = 0; i < neurons.length; i++) {
			neurons[i] = new Neu();
		}		
		for (int i = 0; i < outputneurons.length; i++) {
			outputneurons[i] = new Neu();
		}
	}		
	
	public double ReLu(double data) {	
		if (data < -1.0/3.0) {
			return -1.0;
		}
		if (data >= -1.0/3.0 && data < 1.0/3.0) {
			return 0;
		}
		else {
			return 1.0;
		}		
	}
	
	public double Sigmoid(double data) {
		return 1.0/(1.0 + Math.exp((-1)*data));
	}
	
	public double[][] getInputs() {
		return inputs;
	}
	
	public void setInputs(double[][] inputs) {
		this.inputs = inputs;
	}
	
	public Neu[] getNeurons() {
		return neurons;
	}
	
	public void setNeurons(Neu[] neurons) {
		this.neurons = neurons;
	}		
	
	public Neu[] getOutputneuron() {
		return outputneurons;
	}
	
	public void setOutputneuron(Neu[] outputneuron) {
		this.outputneurons = outputneuron;
	}	
	
	public void Nullaz() {
		for (int i = 0; i < neurons.length; i++) {
			neurons[i].setInh(0.0);
			neurons[i].setOuth(0.0);
		}
		for (int i = 0; i < outputneurons.length; i++) {
			outputneurons[i].setInh(0.0);
			outputneurons[i].setOuth(0.0);
		}		
	}
	
	public double N(double data) {
		return 2.0*((data - min)/(max - min) - 0.5);
	}
	
	public void NormALL() {
		for (int i = 0; i < inputs.length; i++) {
			for (int j = 0; j < inputs[i].length; j++) {
				inputs[i][j] = this.N(inputs[i][j]);
			}
		}			
	}		

	public double[][] getOutputs() {
		return outputs;
	}

	public void setOutputs(double[][] outputs) {
		this.outputs = outputs;
	}
	
	public double[] getMaximum() {
		return maximum;
	}

	public void setMaximum(double[] maximum) {
		this.maximum = maximum;
	}
}
