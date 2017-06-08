import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Jama.Matrix;


class NodeDophins extends Node{
	NodeDophins(){
		super();
	}






	@Override
	void write(BufferedWriter bw, List<Node> nodeList,Matrix adjacentMatrix) {
		// TODO Auto-generated method stub
		//����gml�ļ�
		double a[][]=null;
		Node node=null;
		try {
			bw.write("graph");
			bw.newLine();
			bw.write("[");
			bw.newLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Iterator<Node> iterator=nodeList.iterator();
		
		//����ڵ���Ϣ
		while(iterator.hasNext()){
			node=iterator.next();
			try {
				bw.write("node");
				bw.newLine();
				bw.write("[");
				bw.newLine();
				bw.write("id "+node.getId());
				bw.newLine();
				bw.write("community "+"\""+node.getCommunity()+"\"");
				bw.newLine();
				bw.write("label "+ node.getLabel());
				bw.newLine();
				bw.write("weight "+"\""+node.getWeight()+"\"");
				bw.newLine();
				bw.write("]");
				bw.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//�������Ϣ
		a=adjacentMatrix.getArray();
		for(int i=0;i<a.length;i++){
			for(int j=0;j<i;j++){
				if(a[i][j]!=0.){
					try {
						bw.write("edge [ source "+(i)+" target "+(j)+" ]");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		try {
			bw.write(" ]");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

	@Override
	double[][] read(BufferedReader br, List<Node> nodeList) {
		String s = null;
		List<String> strings = new ArrayList<String>();
		NodeDophins node=null;
		int nodeNum=0,edgeNum=0;
		int colum=0,row=0;
		try {
			s = br.readLine();
			while (s != null) {
				strings.add(s);// ����ȡ���ַ��������ַ���������
				s = br.readLine();
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		/*
		 * ͳ�ƶ�ȡ�ļ��нڵ�ĸ����Լ�����
		 */
		for (int i = 0; i < strings.size(); i++) {
			String ss[] = strings.get(i).split(" ");
			for (int j = 0; j < ss.length; j++) {
				if (ss[j].equals("node")
						&& strings.get(i + 1).trim().equals("[")) {// �ڵ���"node["Ϊ���
					node=new NodeDophins();
					nodeList.add(node);
					nodeNum++;
				}
				
				if(ss[j].equals("id")){
					nodeList.get(nodeList.size()-1).setId(Integer.parseInt(ss[j+1]));
					nodeList.get(nodeList.size()-1).setCommunity(Integer.parseInt(ss[j+1]));
					}
				
				if(ss[j].equals("label")){
					nodeList.get(nodeList.size()-1).setLabel(ss[j+1]);;
					}

				if (ss[j].equals("edge")
						&& strings.get(i + 1).trim().equals("[")) {// ����"egde["Ϊ���
					edgeNum++;
				}
			}
		}
		

		double[][] adjacentArray = new double[nodeNum][nodeNum];// ���ݽڵ�ĸ���������ά����

		/*
		 * �����ļ���source��target��ֵ��ʼ����ά����
		 */
		for (int i = 0; i < strings.size(); i++) {
			String ss[] = strings.get(i).split(" ");
			for (int j = 0; j < ss.length; j++) {
				if (ss[j].equals("source")) {
					row = Integer.parseInt(ss[j + 1]);
				}

				if (ss[j].equals("target")) {
					colum = Integer.parseInt(ss[j + 1]);
					adjacentArray[row][colum] = 1.;
					adjacentArray[colum][row] = 1.;
				}
			}
		}
		
		return adjacentArray;

	}
}
