import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


class UpdateLabel {
	private int labelChangeNum=0;
	private int iterationNum=0;
	private List<Node> communitySeeds=new ArrayList<Node>();
	private Map<Node,Set<Node>> adjacentNodesMap=null;
	UpdateLabel(List<Node> communitySeeds,Map<Node,Set<Node>> adjacentNodesMap){
		this.communitySeeds.addAll(communitySeeds);
		this.adjacentNodesMap=adjacentNodesMap;
	}
	
	public void updating(){
		Node node=null;//�洢����������������ȡ���Ľڵ�
		Map<Node,Set<Node>> coreNeighbor = new LinkedHashMap<Node,Set<Node>>();//�����������һ����Ҫ���±�ǩ�Ľڵ㼯
		Set<Node> updatedNodes = new HashSet<Node>();//�洢�Ѿ��������Ľڵ�
		for(int i=0;i<communitySeeds.size();i++){
			node=communitySeeds.get(i);
			Set<Node> communitySeedsNeighbor = new LinkedHashSet<Node> ();
			communitySeedsNeighbor.addAll(adjacentNodesMap.get(node));
			coreNeighbor.put(node, communitySeedsNeighbor);
		}
		
		while(true){
			
			//����ѭ����������û�нڵ�ı�ǩ�����䶯
			if(updatedNodes.size()==adjacentNodesMap.size()){
				iterationNum++;
				System.out.println("��"+iterationNum+"�ֱ����ı��ǩ�Ľڵ���Ŀ�ǣ�"+labelChangeNum);
				if(labelChangeNum!=0){
					updatedNodes.removeAll(updatedNodes);
					labelChangeNum=0;
					
					/*
					 * �ٴγ�ʼ�����������ھӽڵ�
					 */
					for(int i=0;i<communitySeeds.size();i++){
						node=communitySeeds.get(i);
						Set<Node> communitySeedsNeighbor = new LinkedHashSet<Node> ();
						communitySeedsNeighbor.addAll(adjacentNodesMap.get(node));
						coreNeighbor.put(node, communitySeedsNeighbor);
					}
				}
				
				else {
					System.out.println("�ܹ�������"+iterationNum+"�ֽڵ�");
					break;
					}
				}
			
			for(int i=0;i<communitySeeds.size();i++){
				Set<Node> nodes=new LinkedHashSet<Node>();//��һ�ּ������±�ǩ�Ľڵ�����
				Node node1=null;//�洢��nodes��ȡ���Ľڵ�
				Set<Node> nextExpanding=new LinkedHashSet<Node>();//��һ�ּ������±�ǩ�Ľڵ�
				node=communitySeeds.get(i);//�����������
				nodes=coreNeighbor.get(node);//��ȡ�������ӵ��ھӽڵ�
				nodes.removeAll(updatedNodes);
				//System.out.println("��"+node.getCommunity()+"����������ʼ���½ڵ��ǩ");
				
				
				/*
				 * ��ʼ����ÿ���������ӵ��ھӽڵ�ı�ǩ
				 */
				for(Iterator<Node> iterator=nodes.iterator();iterator.hasNext();){
					node1=iterator.next();
					nextExpanding.addAll(adjacentNodesMap.get(node1));//��ȡ�������½ڵ��node1�������ھӽڵ�
					chosingLabel(node1);
					updatedNodes.add(node1);
					
				}
			
				nextExpanding.removeAll(updatedNodes);//����Ѿ����±�ǩ�Ľڵ�,��Щ�Ѿ����¹���ǩ�Ľڵ�������������
				coreNeighbor.put(node, nextExpanding);
				
			}
		}
	}

	public void chosingLabel(Node node1) {
		Map<Integer,Double> map=new HashMap<Integer,Double>();
		Node node=null;
		int label=0;
		int maxLabel=0;
		double maxWeight=0.;
		double weight=0.;
		Set<Node> set=null;
		set=adjacentNodesMap.get(node1);
		
		//����ڵ�node1�ھӽڵ������б�ǩ�У���ͬ��ǩ��Ȩ�غͣ���Ӧ���ǣ�ĳ��ǩ--Ȩ��ֵ
		for(Iterator<Node> i=set.iterator();i.hasNext();){
			node=i.next();
			label=node.getCommunity();
			weight=node.getWeight();
			if(map.get(label)==null){
				map.put(label, weight);}
			
			else{
				map.put(label, node.getWeight()+map.get(label));}
		}
		
		//�ҳ��������Ȩ�ر�ǩ,����Ȩ��ֵ����ؼ�������map�ı�����
		Iterator mapIterator=map.entrySet().iterator();
		while(mapIterator.hasNext()){
			Map.Entry<Integer, Double> entry =(Map.Entry<Integer, Double>)mapIterator.next();
			label=entry.getKey();
			weight=entry.getValue();
			if(weight>maxWeight){
				maxWeight=weight;
				maxLabel=label;
			}
		}
		
		//�ж��Ƿ���±�ǩ
		if(map.get(node1.getCommunity())<maxWeight){
			node1.setCommunity(maxLabel);
			labelChangeNum++;
			
		}
		
		
	}
	
}
