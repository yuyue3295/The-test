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
	 * ����ת�ƾ���,ִ�н�����õ�һ��ת�ƾ��� ���˼·��ͳ���ڽӾ�����ÿ���ڵ�Ķ�����
	 * Ȼ���Ƕ�Ӧ�ڵ��ÿһ��Ԫ�س��� �ýڵ�Ķ�����Ϊת�ƾ���
	 */
	Matrix createTansitionMatrix() {
		double tem[][] = adjacentMatrix.getArray();
		int k = 0;// kΪ�ڵ�Ķ���
		for (int i = 0; i < adjacentMatrix.getColumnDimension(); i++) {
			for (int j = 0; j < tem[i].length; j++) {
				if (1 - tem[i][j] == 0.)
					k++;
			}

			for (int j = 0; j < tem[i].length; j++) {
				tem[i][j] = tem[i][j] / k;// ����ڵ��ת�Ƹ��ʣ�
			}

			k = 0;

		}
		Matrix transitionMatrix = new Matrix(tem);
		return transitionMatrix;
	}

	/*
	 * ����һ��������������һ����ʼ��λ�ø��ʷֲ�����ʾ�����ߴ��ĸ��ڵ�������� ���ߣ��������õ��������ߴӽڵ�1������������
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
	 * ��ʼ�����������
	 */
	public Matrix startRandomWalking() {
		Matrix transitionMatrix = createTansitionMatrix();
		generateInitialPositionDistribution();// ��ʼ��λ�ø��ʷֲ�
		Matrix finalPostionVector = initialPositionDistribution;
		double tem = initialPositionDistribution.get(0, 0);//������ʱ�洢λ��ǰ�Ʋ����߽ڵ�1��Ȩ��ֵ
		while (true) {
			finalPostionVector = finalPostionVector.times(transitionMatrix);
			if (Math.abs(tem - finalPostionVector.get(0, 0)) < 0.00001)// �ж�λ�ø��ʷֲ��Ƿ񵽴���̬
				break;
			tem = finalPostionVector.get(0, 0);
		}

		return finalPostionVector;//���ص��ǵ�����̬�Ժ�������ߵ�λ�ø��ʷֲ�

	}
}
