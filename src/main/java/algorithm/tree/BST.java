package algorithm.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link BST} BinarySortTree
 * <pre/>
 * 二叉查找树/二叉排序树/二叉搜索树
 */
public class BST<T extends Comparable<T>> {
    private static final Logger LOG = LoggerFactory.getLogger(BST.class);

    /**
     * 插入操作：递归方法插入节点
     *
     * @param pNode 父节点
     * @param value 要插入的数据
     *
     * @return 父节点
     */
    public Node<T> insert(Node<T> pNode, T value) {
        if (null == pNode) {
            //找到位置新建节点插入
            pNode = new Node<T>(value, null, null);
        } else if (value.compareTo(pNode.getValue()) < 0) {
            pNode.setLNode(insert(pNode.getLNode(), value));
        } else {
            pNode.setRNode(insert(pNode.getRNode(), value));
        }
        return pNode;
    }

    /**
     * 插入操作：非递归方法插入节点
     *
     * @param rNode 父节点
     * @param value 要插入的数据
     *
     * @return 根节点
     */
    public Node<T> insertBST(Node<T> rNode, T value) {
        Node<T> iNode = new Node<T>(value, null, null);

        Node<T> root;
        if (null == rNode) {
            root = iNode;
        } else {
            root = rNode;
            while (iNode != rNode.getLNode()
                    && iNode != rNode.getRNode()) { //终止条件
                if (value.compareTo(rNode.getValue()) < 0) {
                    if (null == rNode.getLNode()) {
                        rNode.setLNode(iNode);
                    } else {
                        rNode = rNode.getLNode(); //rootNode向左下方走
                    }
                } else {
                    if (null == rNode.getRNode()) {
                        rNode.setRNode(iNode);
                    } else {
                        rNode = rNode.getRNode(); //rootNode向右下方走
                    }
                }
            }
        }
        return root;
    }

    /**
     * 查找操作
     *
     * @param pNode 父节点
     * @param value 要找的数据
     *
     * @return null or {@link Node}
     */
    public Node<T> searchBST(Node<T> pNode, T value) {
        boolean isFound = false;
        while (null != pNode && !isFound) {
            if (value.compareTo(pNode.getValue()) == 0) {
                isFound = true;
            } else if (value.compareTo(pNode.getValue()) < 0) {
                pNode = pNode.getLNode();
            } else {
                pNode = pNode.getRNode();
            }
        }
        if (null == pNode) {
            LOG.info("没有找到:{}", value);
        }
        return pNode;
    }

    /**
     * 删除操作
     *
     * @param p    根节点
     * @param data 要删除的数据
     *
     * @return 返回一个标志，表示是否找到被删元素
     */
    public boolean deleteBST(Node<T> p, T data) {
        Node<T> root = p;
        Node<T> q = null;
        boolean isFound = false;

        while (null != p && !isFound) {                    //查找被删元素
            if (data.compareTo(p.getValue()) == 0) {       //找到被删元素
                isFound = true;
            } else if (data.compareTo(p.getValue()) < 0) { //沿左子树找
                q = p;
                p = p.getLNode();
            } else {                                       //沿右子树找
                q = p;
                p = p.getRNode();
            }
        }

        if (p == null) {   //没找到
            LOG.info("没有找到:{}", data);
        }

        if (p.getLNode() == null && p.getRNode() == null) {  //p为叶子节点
            if (p == root) {  //p为根节点
                p = null;
            } else if (null != q && q.getLNode() == p) {
                q.setLNode(null);
            } else {
                q.setRNode(null);
            }
            p = null;  //释放节点p
        } else if (p.getLNode() == null || p.getRNode() == null) { //p为单支子树
            if (p == root) {  //p为根节点
                if (p.getLNode() == null) {
                    p = p.getRNode();
                } else {
                    p = p.getLNode();
                }
            } else {
                if (q.getLNode() == p && null != p.getLNode()) { //p是q的左子树且p有左子树
                    q.setLNode(p.getLNode());                    //将p的左子树链接到q的左指针上
                } else if (q.getLNode() == p && null != p.getRNode()) {
                    q.setLNode(p.getRNode());
                } else if (q.getRNode() == p && null != p.getLNode()) {
                    q.setRNode(p.getLNode());
                } else {
                    q.setRNode(p.getRNode());
                }
            }
            p = null;
        } else { //p的左右子树均不为空
            Node<T> t = p;
            Node<T> s = p.getLNode();       //从p的左子节点开始
            while (null != s.getRNode()) { //找到p的前驱，即p左子树中值最大的节点
                t = s;
                s = s.getRNode();
            }
            p.setValue(s.getValue());   //把节点s的值赋给p
            if (t == p) {
                p.setLNode(s.getLNode());
            } else {
                t.setRNode(s.getLNode());
            }
            s = null;
        }
        return isFound;
    }

}
