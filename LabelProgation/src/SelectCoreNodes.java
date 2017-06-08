import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class SelectCoreNodes {
	private List<Node> nodes;
	private double threshold;// ���Ľڵ����ֵ
	private List<Node> coreNodes = new ArrayList<Node>();
	Map<Node, Set<Node>> adjacentNodesList = null;
	List<Node> communitySeeds = new ArrayList<Node>();

	SelectCoreNodes(List<Node> nodes, Map<Node, Set<Node>> adjacentNodesList) {
		this.nodes = nodes;
		this.adjacentNodesList = adjacentNodesList;
		this.setThreshold();
	}

	private void setThreshold() {
		threshold = nodes.get(nodes.size() / 3).getWeight();// ������ֵ���õ��ǵ�n/2���ڵ��Ȩ��ֵ
		System.out.println("the value of threshold is: "+threshold);
	}

	/*
	 * ɸѡȨ��ֵ������ֵ�Ľڵ�
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
		Iterator<Node> iCoreNodes = coreNodes.iterator();// ����ѡ�����ĺ��Ľڵ�
		Iterator<Node> iCommunitySeeds = null;// �����������ӽڵ�
		
		Node node = null;// �ݴ�˧ѡ�����ļ������д���Ƚϵĺ��Ľڵ�
		Node seed = null;// �ݴ��������ӽڵ�
		int flag = 0;// ��Ϊ�жϺ��Ľڵ����������ӽڵ��Ƿ��б����ӵı�־
		double tem = 0.;
		while (iCoreNodes.hasNext()) {
			flag = 0;
			node = iCoreNodes.next();// ��ü�������ĺ��Ľڵ�

			/*
			 * �����������������Ϊ�գ���ֱ�Ӽ�����Ľڵ㲢��Ϊһ������������
			 */
			if (communitySeeds.isEmpty()) {
				communitySeeds.add(node);
				continue;
			}

			iCommunitySeeds = communitySeeds.iterator();

			/*
			 * ������Ľڵ����������������еĺ��Ľڵ��б��������� �����Ľڵ���뵽�������ӽڵ����ڵ�����
			 */
			while (iCommunitySeeds.hasNext()) {
				seed = iCommunitySeeds.next();
				/*if (adjacentNodesList.get(seed).contains(node)) {
					node.setCommunity(seed.getCommunity());
					flag = 1;
					break;
				}*/
				
				//�ж��������������Ľڵ�������ھӽڵ㣨���Ľڵ㣩��Ȩ���Ƿ���ڽڵ�����
				if(hasEnoughCommonNeighborsWeight(node, seed)){node.setCommunity(seed.getCommunity());
				flag = 1;
				break;
				}
			}

			if (flag == 0)
				communitySeeds.add(node);// ���Ľڵ����������������е����ӽڵ��ޱ�����������Ľڵ���Ϊһ�������������ӽڵ�
		}
		
		System.out.println("�������ӣ�");
		System.out.println(communitySeeds);
		System.out.println("���Ľڵ㣺");
		System.out.println(coreNodes);
		return communitySeeds;
	}
	
	/*
	 * �������Ľڵ����������ӹ����ڵ��Ȩ�غ��Ƿ������ֵ
	 */
	private boolean hasEnoughCommonNeighborsWeight(Node node,Node seed){
		double commonNeighborsWeight=0.;
		Set<Node> intersectionSet=new HashSet<Node>();//�洢���Ľڵ�Ľ���
		intersectionSet.addAll(adjacentNodesList.get(seed));
		intersectionSet.retainAll(adjacentNodesList.get(node));//�������������Ľڵ�Ĺ�ͬ�ھ�
		intersectionSet.retainAll(coreNodes);//��ͬ�ھ��г�ȥ�����Ľڵ�
		
		
		for(Iterator<Node> iterator=intersectionSet.iterator();iterator.hasNext();){
			commonNeighborsWeight+=iterator.next().getWeight();
		}
		System.out.print (node+"��"+seed+"��ͬ�ھ�Ȩ�غ�Ϊ   ");
		System.out.println("commonNeighborsWeight "+commonNeighborsWeight);
		if(commonNeighborsWeight>node.getWeight())
			return true;
		else return false;
	}
}
