import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;

import Jama.Matrix;


abstract class Node {
	private int id = 0;
	private int community = 0;
	private double weight = 0;
	private String label=null;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}

	public int getCommunity() {
		return community;
	}

	public void setCommunity(int community) {
		this.community = community;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		if(this.label!=null)
			return "Node [id=" + id + ", community=" + community + ", weight="
					+ weight +", label="+label+"]";
		else return "Node [id=" + id + ", community=" + community + ", weight="
		+ weight +"]";
	}
	
	Node(){
		
	    }
	
	/*
	 * ����gml�ļ�ʱ��Ҫ��bufferedwriter����д������дʲô����node�Լ�����
	 */
	abstract void write(BufferedWriter bw,List<Node> nodeList,Matrix adjacentMatrix);
	
	/*
	 * �����ļ�ʱ��gml��ȡ�����ݣ���ô��������node�Լ�����
	 */
	abstract double[][] read(BufferedReader br,List<Node> nodeList);
}
