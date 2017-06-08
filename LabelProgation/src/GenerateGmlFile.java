import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Jama.Matrix;


class GenerateGmlFile {
	private String filePath="D:\\JavaPrograme\\facebookData\\facebook_combinedResult.gml";
	private File gmlFile = new File(filePath);
	private FileWriter fw = null;
	private BufferedWriter bw = null;
	private Matrix adjacentMatrix=null;
	private List<Node> nodeList = new ArrayList<Node>();
	private double a[][];//存储从邻接矩阵中生成的数组；
	GenerateGmlFile(List<Node> nodeList,Matrix adjacentMatrix) {
		this.nodeList.addAll(nodeList);
		this.adjacentMatrix=adjacentMatrix;
	}
	
	public void generate(){
		try {
			fw=new FileWriter(gmlFile);
		} catch (IOException e) {	
			e.printStackTrace();
		}
		bw=new BufferedWriter(fw);
		nodeList.get(0).write(bw, nodeList, adjacentMatrix);//调用Node中的write函数
		System.out.println(filePath+"文件生成");
	}
}
