package algorithm.tree;

/**
 * {@link Node} 节点
 */
public class Node<T extends Comparable<T>> {

    private T value;
    private Node<T> lNode;
    private Node<T> rNode;

    public Node() {
    }

    public Node(T value, Node<T> lNode, Node<T> rNode) {
        this.value = value;
        this.lNode = lNode;
        this.rNode = rNode;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLNode() {
        return lNode;
    }

    public void setLNode(Node<T> lNode) {
        this.lNode = lNode;
    }

    public Node<T> getRNode() {
        return rNode;
    }

    public void setRNode(Node<T> rNode) {
        this.rNode = rNode;
    }

}
