import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Jama.Matrix;

class GMLParaser {
	private File gmlFile = null;
	private FileReader f = null;
	private BufferedReader br = null;
	private int nodeNum = 0, edgeNum = 0;// 存储节点的个数、变数
	private int row = 0, colum = 0;// 辅助完成矩阵的初始化
	private List<String> strings = new ArrayList<String>();// 存储从文件中读取的每一行字符串
	private Map<Node,Set<Node>> adjacentNodesMap = new LinkedHashMap<Node,Set<Node>>();//用来表示节点的邻接表
	private Matrix adjacentMatrix=null;
	private List<Node> nodes = new ArrayList<Node>();
	private List<Node> initialNodes = new ArrayList<Node>();
	private Node temNode=null;
	private double adjacentArray[][]=null;

	GMLParaser(File gmlFile) {
		this.gmlFile = gmlFile;
		this.parase();
	}

	
	private void parase(){
		try {
			f = new FileReader(gmlFile);
			br = new BufferedReader(f);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		adjacentArray= new NodeDophins().read(br, nodes);//穿nodes参数的目的是将创建的node节点添加进集合nodes中，你懂的
		
		/*
		 * 这里来做个遍历，初始化节点的权重值
		 */
		int numberOfNode = nodes.size();
		for(int i=0;i<numberOfNode;i++){
			nodes.get(i).setWeight(40./numberOfNode);
		}
		
	}
	
	double [][] generateArrays(){
		return adjacentArray;
	}
	Matrix generateAdjecentMatrix() {
		adjacentMatrix = new Matrix(generateArrays());
		return adjacentMatrix;
	}

	public List<Node> createNetWorkNodes(){
		return nodes;
	}
	
	/*
	 * 创建一个hashmap，key为node，value为一个arraylist链表，存储的是node的邻居节点，用来
	 * 表示网络节点的邻接情况，充当邻接表的作用
	 */
	public Map<Node,Set<Node>> createAdjacentNodeList(){
		double tem[][]=adjacentMatrix.getArray();
		Set<Node> temSet=null;
		Node temNode=null;
		for(int i=0;i<tem.length;i++){
			temSet=new LinkedHashSet<Node>();
			temNode=nodes.get(i);
			temSet.add(temNode);
			for(int j=0;j<tem[i].length;j++){
				if(tem[i][j]-1.==0)temSet.add(nodes.get(j));
			}
			
			adjacentNodesMap.put(temNode, temSet);
		}
		
		return adjacentNodesMap;
	}

}
