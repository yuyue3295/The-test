import java.util.Comparator;
public class NodeComparator implements Comparator<Node> {
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 * Ϊʲôo1>o2Ҫ����-1������1�أ���ΪCollections.sort()����Ĭ�ϵ���������������Ϊ�˵õ�Ĭ�ϵĽ��������������
	 */
	public int compare(Node o1, Node o2) {
		if (o1.getWeight() - o2.getWeight() > 0.0001)
			return -1;
		else if (Math.abs(o1.getWeight() - o2.getWeight()) <= 0.0001)
			return 0;
		else
			return 1;
	}
	
	
}
