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
		Node node=null;//存储从社区种子链表中取出的节点
		Map<Node,Set<Node>> coreNeighbor = new LinkedHashMap<Node,Set<Node>>();//这个子社区下一次需要更新标签的节点集
		Set<Node> updatedNodes = new HashSet<Node>();//存储已经遍历过的节点
		for(int i=0;i<communitySeeds.size();i++){
			node=communitySeeds.get(i);
			Set<Node> communitySeedsNeighbor = new LinkedHashSet<Node> ();
			communitySeedsNeighbor.addAll(adjacentNodesMap.get(node));
			coreNeighbor.put(node, communitySeedsNeighbor);
		}
		
		while(true){
			
			//跳出循环的条件：没有节点的标签发生变动
			if(updatedNodes.size()==adjacentNodesMap.size()){
				iterationNum++;
				System.out.println("这"+iterationNum+"轮遍历改变便签的节点数目是："+labelChangeNum);
				if(labelChangeNum!=0){
					updatedNodes.removeAll(updatedNodes);
					labelChangeNum=0;
					
					/*
					 * 再次初始化社区种子邻居节点
					 */
					for(int i=0;i<communitySeeds.size();i++){
						node=communitySeeds.get(i);
						Set<Node> communitySeedsNeighbor = new LinkedHashSet<Node> ();
						communitySeedsNeighbor.addAll(adjacentNodesMap.get(node));
						coreNeighbor.put(node, communitySeedsNeighbor);
					}
				}
				
				else {
					System.out.println("总共遍历了"+iterationNum+"轮节点");
					break;
					}
				}
			
			for(int i=0;i<communitySeeds.size();i++){
				Set<Node> nodes=new LinkedHashSet<Node>();//这一轮即将更新标签的节点链表
				Node node1=null;//存储从nodes中取出的节点
				Set<Node> nextExpanding=new LinkedHashSet<Node>();//下一轮即将更新标签的节点
				node=communitySeeds.get(i);//获得社区种子
				nodes=coreNeighbor.get(node);//获取社区种子的邻居节点
				nodes.removeAll(updatedNodes);
				//System.out.println("第"+node.getCommunity()+"个子社区开始更新节点标签");
				
				
				/*
				 * 开始更新每个社区种子的邻居节点的标签
				 */
				for(Iterator<Node> iterator=nodes.iterator();iterator.hasNext();){
					node1=iterator.next();
					nextExpanding.addAll(adjacentNodesMap.get(node1));//获取即将更新节点的node1的所有邻居节点
					chosingLabel(node1);
					updatedNodes.add(node1);
					
				}
			
				nextExpanding.removeAll(updatedNodes);//清除已经更新标签的节点,有些已经更新过标签的节点如何清除。。。
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
		
		//计算节点node1邻居节点所持有标签中，相同标签的权重和，对应的是：某标签--权重值
		for(Iterator<Node> i=set.iterator();i.hasNext();){
			node=i.next();
			label=node.getCommunity();
			weight=node.getWeight();
			if(map.get(label)==null){
				map.put(label, weight);}
			
			else{
				map.put(label, node.getWeight()+map.get(label));}
		}
		
		//找出具有最大权重标签,及其权重值，其关键点在于map的遍历；
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
		
		//判断是否更新标签
		if(map.get(node1.getCommunity())<maxWeight){
			node1.setCommunity(maxLabel);
			labelChangeNum++;
			
		}
		
		
	}
	
}
