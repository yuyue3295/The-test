import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Jama.Matrix;


public class Parse {
	public static void main(String[]args){
		File gmlFile = new File("D:\\JavaPrograme\\haha1.gml");
		FileReader f=null;
		BufferedReader bf=null;

		int nodeNum = 0,edgeNum=0;
		int row = 0,colum=0;
		String s = null;
		List<Node> nodes = new LinkedList<Node>();
		List<String> strings = new ArrayList<String>();//将从文件中读取的每一行
		
		//从karate文件中读取数据
		try {
			f = new FileReader(gmlFile);
			 bf = new BufferedReader(f);
			s = bf.readLine();
			while(s!=null){
				System.out.println(s);
				strings.add(s);
				s = bf.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				f.close();
				bf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*
		 * 统计读取文件中节点的个数以及边数
		 */
		for(int i=0;i<strings.size();i++){
			String ss[] = strings.get(i).split(" ");
			for(int j=0;j<ss.length;j++){
				if(ss[j].equals("node")&&strings.get(i+1).trim().equals("[")){//节点以"node["为标记
					nodeNum++;
				}
				
				if(ss[j].equals("edge")&&strings.get(i+1).trim().equals("[")){//边以"egde["为标记
					edgeNum++;
				}
			}
		}
		
		
		
		double a[][]=new double[nodeNum][nodeNum];//根据节点的个数简历一个空的邻接矩阵
		
		/*
		 * 根据文件中source和target的值初始化邻接矩阵
		 */
		for(int i=0;i<strings.size();i++){
			String ss[] = strings.get(i).split(" ");
			for(int j=0;j<ss.length;j++){
				if(ss[j].equals("source")){
					row=Integer.parseInt(ss[j+1]);
				}
				
				if(ss[j].equals("target")){
					colum=Integer.parseInt(ss[j+1]);
					a[row-1][colum-1]=1.;
					a[colum-1][row-1]=1.;
				}
			}
		}
		
		Matrix m = new Matrix(a);
		m.print(nodeNum, nodeNum);//打印矩阵的制定行和列
		
		
		
		
		System.out.println(nodes);
		System.out.println("the num of node = "+nodeNum);
		System.out.println("the num of edge = "+edgeNum);
		System.out.println(strings);
		
		/*
		 * 打印矩阵
		 */
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[i].length;j++)System.out.println("a["+i+"]["+j+"]= "+a[i][j]);
		}

	
		
		
	}
}

class Node{
	private int id = -1;
	Node(int id){
		this.id=id;
	}
	@Override
	public String toString() {
		return "Node [id=" + id + "]";
	}
	
	
	
}