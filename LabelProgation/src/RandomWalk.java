import java.util.Arrays;

import Jama.Matrix;

class RandomWalk {
	private Matrix adjacentMatrix;
	private Matrix initialPositionDistribution;
	private int nodeNum;

	RandomWalk(Matrix adjacentMatrix) {
		this.adjacentMatrix = adjacentMatrix;
	}

	/*
	 * 构建转移矩阵,执行结果将得到一个转移矩阵 设计思路是统计邻接矩阵中每个节点的度数，
	 * 然后是对应节点的每一行元素除以 该节点的度数则为转移矩阵
	 */
	Matrix createTansitionMatrix() {
		double tem[][] = adjacentMatrix.getArray();
		int k = 0;// k为节点的度数
		for (int i = 0; i < adjacentMatrix.getColumnDimension(); i++) {
			for (int j = 0; j < tem[i].length; j++) {
				if (1 - tem[i][j] == 0.)
					k++;
			}

			for (int j = 0; j < tem[i].length; j++) {
				tem[i][j] = tem[i][j] / k;// 计算节点的转移概率；
			}

			k = 0;

		}
		Matrix transitionMatrix = new Matrix(tem);
		return transitionMatrix;
	}

	/*
	 * 生成一个行向量，它是一个初始的位置概率分布，表示游走者从哪个节点出发进行 游走，这里设置的是游走者从节点1出发进行游走
	 */
	private void generateInitialPositionDistribution() {
		nodeNum=adjacentMatrix.getRowDimension();
		double b[][] = new double[nodeNum][nodeNum];
		Arrays.fill(b[0], 0, 1, 1);
		Arrays.fill(b[0], 2,b[0].length,0);
		for(int i=1;i<b.length;i++){
			Arrays.fill(b[i], 0);
		}
		initialPositionDistribution = new Matrix(b);

	}

	/*
	 * 开始进行随机游走
	 */
	public Matrix startRandomWalking() {
		Matrix transitionMatrix = createTansitionMatrix();
		generateInitialPositionDistribution();// 初始化位置概率分布
		Matrix finalPostionVector = initialPositionDistribution;
		double tem = initialPositionDistribution.get(0, 0);//用于临时存储位置前移步行走节点1的权重值
		while (true) {
			finalPostionVector = finalPostionVector.times(transitionMatrix);
			if (Math.abs(tem - finalPostionVector.get(0, 0)) < 0.00001)// 判断位置概率分布是否到达稳态
				break;
			tem = finalPostionVector.get(0, 0);
		}

		return finalPostionVector;//返回的是到达稳态以后的游走者的位置概率分布

	}
}
