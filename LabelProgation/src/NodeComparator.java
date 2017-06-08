import java.util.Comparator;
public class NodeComparator implements Comparator<Node> {
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 * 为什么o1>o2要返回-1而不是1呢？因为Collections.sort()方法默认的是升序排序，所以为了得到默认的降序排序而这样做
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
