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
		File gmlFile = new File("D:\\JavaPrograme\\facebookData\\facebook_combined.gml");// 创建文件的路径
		GMLParaser gml = new GMLParaser(gmlFile);
		Matrix adjacentMatrix = gml.generateAdjecentMatrix();// 创建邻接矩阵
		nodes=gml.createNetWorkNodes();//创建节点链表，以后用于排序；
		List<Node> listNodes = new ArrayList<Node>();
		Map<Node,Set<Node>> adjacentNodesMap = gml.createAdjacentNodeList();//创建网络的邻接表
		Matrix nodeWeightDistributeVector = null;//游走者的位置概率分布
		List<Node> communitySeeds = new ArrayList<Node>();//社区种子
		Node temNode=null;
		
		
		nodeWeightDistributeVector = new RandomWalk(adjacentMatrix)
				.startRandomWalking();// 计算网络节点的权重分布
		
		

		/*
		 * 初始化节点的权重值
		 */
		for (int i = 0; i < nodeWeightDistributeVector.getColumnDimension(); i++) {
			double d[][] = nodeWeightDistributeVector.getArray();
			nodes.get(i).setWeight(
					new BigDecimal(d[0][i]).setScale(4,
							BigDecimal.ROUND_HALF_UP).doubleValue());// 保留小数点后四位
		}
		
		
		/*
		 * Collections.sort()方法使用的是Tim排序，Tim排序是一种混合排序方式，是对归并排序和插入排序的优化，算法的
		 * 时间复杂度要优于这两种算法
		 */
		Collections.sort(nodes, new NodeComparator());
		
		
		listNodes.addAll(nodes);
		
		SelectCoreNodes select = new SelectCoreNodes(listNodes, adjacentNodesMap);
		select.cutoff();//截断，划分核心节点
		communitySeeds=select.createSubCommunity();//社区种子被筛选出来，初步形成子社区；
		
		System.out.println("节点划分前");
		System.out.println(nodes);
		UpdateLabel updateLabel = new UpdateLabel(communitySeeds,adjacentNodesMap);
		
updateLabel.updating();//开始标签传播;这个地方造成了出错，O mygard
		
		System.out.println("节点划分后");
		System.out.println(nodes);
		
		GenerateGmlFile generateGmlFile = new GenerateGmlFile(nodes, adjacentMatrix);
		generateGmlFile.generate();//生成gml文件
		
		CalculateModularity cm=new CalculateModularity(nodes,adjacentNodesMap,adjacentMatrix);
		cm.getModularity();
	}

}
