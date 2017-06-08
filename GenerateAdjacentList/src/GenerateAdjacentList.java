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


public class GenerateAdjacentList {
	public static void main(String[] args) {
		List<Node> nodes = null;
		File gmlFile = new File("D:\\JavaPrograme\\facebookData\\facebook_combined.gml");// 创建文件的路径
		GMLParaser gml = new GMLParaser(gmlFile);
		Matrix adjacentMatrix = gml.generateAdjecentMatrix();// 创建邻接矩阵
		nodes=gml.createNetWorkNodes();//创建节点链表，以后用于排序；
		List<Node> listNodes = new ArrayList<Node>();
		Map<Node,Set<Node>> adjacentNodesMap = gml.createAdjacentNodeList();//创建网络的邻接表
System.out.println(adjacentNodesMap);//打印输出
System.out.println(nodes);
		write(adjacentNodesMap,nodes);
		
		
	}
	
	private static void write(Map<Node,Set<Node>> adjacentNodes,List<Node> nodes){
		File path = new File("D:\\JavaPrograme\\facebookData\\facebook_combinedAdjacentList");
		BufferedWriter bw=null;
		try {
			bw = new BufferedWriter(new FileWriter(path));
			Iterator iteratorList = nodes.iterator();
			for(;iteratorList.hasNext();){
				Set<Node> tempSet =adjacentNodes.get(iteratorList.next());
				Iterator<Node> nodeIterator = tempSet.iterator();
				Node tempNode = nodeIterator.next();
				int id =tempNode.getId();
				bw.write(""+id);
				bw.write("\t");
				bw.write(""+tempNode.getWeight());
				
				
				while(nodeIterator.hasNext()){
					bw.write(" ");
					tempNode=nodeIterator.next();
					id=tempNode.getId();
					bw.write(""+id);
				}
				
				bw.newLine();
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
}