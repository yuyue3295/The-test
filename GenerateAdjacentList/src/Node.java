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
	 * 生成gml文件时需要向bufferedwriter里面写东西，写什么东西node自己决定
	 */
	abstract void write(BufferedWriter bw,List<Node> nodeList,Matrix adjacentMatrix);
	
	/*
	 * 解析文件时从gml读取的数据，怎么个读法由node自己决定
	 */
	abstract double[][] read(BufferedReader br,List<Node> nodeList);
}
