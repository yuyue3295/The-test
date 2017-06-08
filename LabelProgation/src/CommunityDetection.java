import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Jama.Matrix;

public class CommunityDetection {
	public static void main(String[] args) {
		List<Node> nodes = null;
		File gmlFile = new File("D:\\JavaPrograme\\facebookData\\facebook_combined.gml");// �����ļ���·��
		GMLParaser gml = new GMLParaser(gmlFile);
		Matrix adjacentMatrix = gml.generateAdjecentMatrix();// �����ڽӾ���
		nodes=gml.createNetWorkNodes();//�����ڵ������Ժ���������
		List<Node> listNodes = new ArrayList<Node>();
		Map<Node,Set<Node>> adjacentNodesMap = gml.createAdjacentNodeList();//����������ڽӱ�
		Matrix nodeWeightDistributeVector = null;//�����ߵ�λ�ø��ʷֲ�
		List<Node> communitySeeds = new ArrayList<Node>();//��������
		Node temNode=null;
		
		
		nodeWeightDistributeVector = new RandomWalk(adjacentMatrix)
				.startRandomWalking();// ��������ڵ��Ȩ�طֲ�
		
		

		/*
		 * ��ʼ���ڵ��Ȩ��ֵ
		 */
		for (int i = 0; i < nodeWeightDistributeVector.getColumnDimension(); i++) {
			double d[][] = nodeWeightDistributeVector.getArray();
			nodes.get(i).setWeight(
					new BigDecimal(d[0][i]).setScale(4,
							BigDecimal.ROUND_HALF_UP).doubleValue());// ����С�������λ
		}
		
		
		/*
		 * Collections.sort()����ʹ�õ���Tim����Tim������һ�ֻ������ʽ���ǶԹ鲢����Ͳ���������Ż����㷨��
		 * ʱ�临�Ӷ�Ҫ�����������㷨
		 */
		Collections.sort(nodes, new NodeComparator());
		
		
		listNodes.addAll(nodes);
		
		SelectCoreNodes select = new SelectCoreNodes(listNodes, adjacentNodesMap);
		select.cutoff();//�ضϣ����ֺ��Ľڵ�
		communitySeeds=select.createSubCommunity();//�������ӱ�ɸѡ�����������γ���������
		
		System.out.println("�ڵ㻮��ǰ");
		System.out.println(nodes);
		UpdateLabel updateLabel = new UpdateLabel(communitySeeds,adjacentNodesMap);
		
updateLabel.updating();//��ʼ��ǩ����;����ط�����˳���O mygard
		
		System.out.println("�ڵ㻮�ֺ�");
		System.out.println(nodes);
		
		GenerateGmlFile generateGmlFile = new GenerateGmlFile(nodes, adjacentMatrix);
		generateGmlFile.generate();//����gml�ļ�
		
		CalculateModularity cm=new CalculateModularity(nodes,adjacentNodesMap,adjacentMatrix);
		cm.getModularity();
	}

}
