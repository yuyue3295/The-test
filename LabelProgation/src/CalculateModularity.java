import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Jama.Matrix;


class CalculateModularity {
	private List<Node> nodeList = new ArrayList<Node>();
	private double totalDegree=0.;
	private double modularitySum=0.;
	private double nullModelExpectedEdge=0.;
	private Map<Node,Set<Node>> adjacentNodeMap = new HashMap<Node,Set<Node>>();
	private Matrix adjacentMatrix=null;
	
	CalculateModularity(List<Node> nodeList,Map<Node,Set<Node>> adjacentNodeMap,Matrix adjacentMatrix){
		this.nodeList.addAll(nodeList);
		this.adjacentNodeMap=adjacentNodeMap;
		this.adjacentMatrix=adjacentMatrix;
		computeTotalDegree();
	}
	
	/*
	 * 计算网络中节点的度数和
	 */
	private void computeTotalDegree(){
		double degreeI=0.;
		System.out.println(nodeList.size());
		for(Node node:nodeList){
			Set<Node> temSets=new LinkedHashSet<Node>();
			temSets.addAll(adjacentNodeMap.get(node));
			degreeI=temSets.size()-1;//因为加了自环
			totalDegree+=degreeI;
		}
		
		System.out.println("网络中节点的度数是："+totalDegree);
	}
	
	/*public double getModularity(){
		double degreeI=0.,degreeJ=0.;//保存节点的度数
		for(Node node:nodeList){
			Set<Node> nodeSet=null;//保存node的邻居节点
			nodeSet=adjacentNodeMap.get(node);
			nodeSet.remove(node);//因为添加了自环
			degreeI=nodeSet.size();
			for(Node node1:nodeList){
				Set<Node> nodeSet1=null;//保存node1的邻居节点
				nodeSet1=adjacentNodeMap.get(node1);
				nodeSet1.remove(node1);//因为添加了自环
				degreeJ=nodeSet1.size();
				if(node.getCommunity()==node1.getCommunity()){
					if(nodeSet.contains(node1)){
						modularitySum+=1-degreeI*degreeJ/totalDegree;
					}
					
					else {modularitySum+=0-degreeI*degreeJ/totalDegree;}
				}
			}
		}
		
		System.out.println("modularity "+modularitySum/totalDegree);
		return 0.;
	}*/
	
	public double getModularity(){
		Map<Integer,Set<Node>> communityMap=new HashMap<Integer,Set<Node>>();//key为社区编号，value为对应社区中的节点集
		int community=0;
		for(Node node:nodeList){
			community=node.getCommunity();
			if(communityMap.get(community)==null){
				Set<Node> sets=new LinkedHashSet<Node>();
				sets.add(node);
				communityMap.put(community, sets);
				
				}
			
			else communityMap.get(community).add(node);
		}
		
		//计算null模型边的期望值
		for(Map.Entry<Integer, Set<Node>> entry:communityMap.entrySet()){
			Set<Node> temSet=entry.getValue();
			Iterator<Node> iterator=temSet.iterator();
			double communityDegreeI=0.;
			for(;iterator.hasNext();){
				double temDegree=0.;
				Node temNode=iterator.next();
				temDegree=adjacentNodeMap.get(temNode).size()-1;
				communityDegreeI+=temDegree;
				
				
			}
			
			nullModelExpectedEdge+=communityDegreeI*communityDegreeI/totalDegree/totalDegree;
		}
		
		System.out.println("nullModelExpectedEdge "+nullModelExpectedEdge);
		return 0.;
	}
}
