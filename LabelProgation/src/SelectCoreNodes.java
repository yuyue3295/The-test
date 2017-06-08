import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class SelectCoreNodes {
	private List<Node> nodes;
	private double threshold;// 核心节点的阈值
	private List<Node> coreNodes = new ArrayList<Node>();
	Map<Node, Set<Node>> adjacentNodesList = null;
	List<Node> communitySeeds = new ArrayList<Node>();

	SelectCoreNodes(List<Node> nodes, Map<Node, Set<Node>> adjacentNodesList) {
		this.nodes = nodes;
		this.adjacentNodesList = adjacentNodesList;
		this.setThreshold();
	}

	private void setThreshold() {
		threshold = nodes.get(nodes.size() / 3).getWeight();// 这里阈值设置的是第n/2个节点的权重值
		System.out.println("the value of threshold is: "+threshold);
	}

	/*
	 * 筛选权重值大于阈值的节点
	 */
	public List<Node> cutoff() {
		Node temNode = null;
		for (int i = 0; i < nodes.size(); i++) {
			temNode = nodes.get(i);
			if (Math.abs(temNode.getWeight() - threshold) >= 0.00001)
				coreNodes.add(temNode);
			else
				break;
		}

		return coreNodes;
	}

	public List<Node> createSubCommunity() {
		Iterator<Node> iCoreNodes = coreNodes.iterator();// 遍历选出来的核心节点
		Iterator<Node> iCommunitySeeds = null;// 用来遍历种子节点
		
		Node node = null;// 暂存帅选出来的即将进行处理比较的核心节点
		Node seed = null;// 暂存社区种子节点
		int flag = 0;// 作为判断核心节点与社区种子节点是否有边连接的标志
		double tem = 0.;
		while (iCoreNodes.hasNext()) {
			flag = 0;
			node = iCoreNodes.next();// 获得即将处理的核心节点

			/*
			 * 如果社区种子链表中为空，则直接加入核心节点并作为一个社区的种子
			 */
			if (communitySeeds.isEmpty()) {
				communitySeeds.add(node);
				continue;
			}

			iCommunitySeeds = communitySeeds.iterator();

			/*
			 * 如果核心节点与社区种子链表中的核心节点有边相连，则 将核心节点加入到社区种子节点所在的社区
			 */
			while (iCommunitySeeds.hasNext()) {
				seed = iCommunitySeeds.next();
				/*if (adjacentNodesList.get(seed).contains(node)) {
					node.setCommunity(seed.getCommunity());
					flag = 1;
					break;
				}*/
				
				//判断社区种子与中心节点的特殊邻居节点（中心节点）的权重是否大于节点自身
				if(hasEnoughCommonNeighborsWeight(node, seed)){node.setCommunity(seed.getCommunity());
				flag = 1;
				break;
				}
			}

			if (flag == 0)
				communitySeeds.add(node);// 核心节点与社区种子链表中的种子节点无边相连，则核心节点作为一个新社区的种子节点
		}
		
		System.out.println("社区种子：");
		System.out.println(communitySeeds);
		System.out.println("中心节点：");
		System.out.println(coreNodes);
		return communitySeeds;
	}
	
	/*
	 * 计算中心节点与社区种子公共节点的权重和是否大于阈值
	 */
	private boolean hasEnoughCommonNeighborsWeight(Node node,Node seed){
		double commonNeighborsWeight=0.;
		Set<Node> intersectionSet=new HashSet<Node>();//存储核心节点的交集
		intersectionSet.addAll(adjacentNodesList.get(seed));
		intersectionSet.retainAll(adjacentNodesList.get(node));//社区种子与中心节点的共同邻居
		intersectionSet.retainAll(coreNodes);//共同邻居中除去非中心节点
		
		
		for(Iterator<Node> iterator=intersectionSet.iterator();iterator.hasNext();){
			commonNeighborsWeight+=iterator.next().getWeight();
		}
		System.out.print (node+"与"+seed+"共同邻居权重和为   ");
		System.out.println("commonNeighborsWeight "+commonNeighborsWeight);
		if(commonNeighborsWeight>node.getWeight())
			return true;
		else return false;
	}
}
