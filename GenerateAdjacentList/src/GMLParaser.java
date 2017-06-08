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
	private int nodeNum = 0, edgeNum = 0;// �洢�ڵ�ĸ���������
	private int row = 0, colum = 0;// ������ɾ���ĳ�ʼ��
	private List<String> strings = new ArrayList<String>();// �洢���ļ��ж�ȡ��ÿһ���ַ���
	private Map<Node,Set<Node>> adjacentNodesMap = new LinkedHashMap<Node,Set<Node>>();//������ʾ�ڵ���ڽӱ�
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
		
		adjacentArray= new NodeDophins().read(br, nodes);//��nodes������Ŀ���ǽ�������node�ڵ���ӽ�����nodes�У��㶮��
		
		/*
		 * ������������������ʼ���ڵ��Ȩ��ֵ
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
	 * ����һ��hashmap��keyΪnode��valueΪһ��arraylist�����洢����node���ھӽڵ㣬����
	 * ��ʾ����ڵ���ڽ�������䵱�ڽӱ������
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
