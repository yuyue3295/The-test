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
		List<String> strings = new ArrayList<String>();//�����ļ��ж�ȡ��ÿһ��
		
		//��karate�ļ��ж�ȡ����
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
		 * ͳ�ƶ�ȡ�ļ��нڵ�ĸ����Լ�����
		 */
		for(int i=0;i<strings.size();i++){
			String ss[] = strings.get(i).split(" ");
			for(int j=0;j<ss.length;j++){
				if(ss[j].equals("node")&&strings.get(i+1).trim().equals("[")){//�ڵ���"node["Ϊ���
					nodeNum++;
				}
				
				if(ss[j].equals("edge")&&strings.get(i+1).trim().equals("[")){//����"egde["Ϊ���
					edgeNum++;
				}
			}
		}
		
		
		
		double a[][]=new double[nodeNum][nodeNum];//���ݽڵ�ĸ�������һ���յ��ڽӾ���
		
		/*
		 * �����ļ���source��target��ֵ��ʼ���ڽӾ���
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
		m.print(nodeNum, nodeNum);//��ӡ������ƶ��к���
		
		
		
		
		System.out.println(nodes);
		System.out.println("the num of node = "+nodeNum);
		System.out.println("the num of edge = "+edgeNum);
		System.out.println(strings);
		
		/*
		 * ��ӡ����
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