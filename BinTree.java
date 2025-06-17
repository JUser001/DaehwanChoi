package sec13;

import java.util.*;
import java.util.Comparator;

public class BinTree<K,V> {
    //--- ��� ---//
    static class Node<K,V> {
        private K key;              // Ű��
        private V data;             // ������
        private Node<K,V> left;     // ���� ������(���� �ڽĳ�忡 ���� ����)
        private Node<K,V> right;    // ������ ������(������ �ڽĳ�忡 ���� ����)

        //--- ������(constructor) ---//
        Node(K key, V data, Node<K,V> left, Node<K,V> right) {
            this.key   = key;
            this.data  = data;
            this.left  = left;
            this.right = right;
        }

        //--- Ű���� �״�� ��ȯ ---//
        K getKey() {
            return key;
        }

        //--- �����͸� �״�� ��ȯ ---//
        V getValue() {
            return data;
        }

        //--- �����͸� ����ϴ� �޼��� ---//
        void print() {
            System.out.println(data);
        }
    }
    
     // ��Ʈ
    private Node<K,V> root; 
    // Ű���� ��� ���踦 �Ǵ��ϴ� ����(Comparator)
    // �����˻�Ʈ���� �����ϴ� �����ڿ��� ���ڸ� ���������� �������� ������
    // �ڵ����� null�� �ǵ��� �ʱ��ڸ� null�� �־� �����Ѵ�.
    private Comparator<? super K> comparator = null;    

    //--- ������(constructor) ---//
    //��Ʈ�� ���� ������ root�� null�� �Ͽ� ��尡 �ϳ��� ����(����ִ�)
    //�����˻�Ʈ���� �����ϴ� ������
    public BinTree() {
        root = null;
    }

    //--- ������(constructor) ---//
    //
    public BinTree(Comparator<? super K> c) {
    	
    	//this()�� ���� �μ��� ���޹��� ���� ������ BinTree()�� ȣ���Ѵ�. 
    	//root�� null��(��� �ִ�)�����˻�Ʈ���� �����Ѵ�.
        this(); 
        //�ʵ� comparator�� ���޹��� c�� ����
        comparator = c;
    }

    //--- �� Ű���� �� ---//
    
    private int comp(K key1, K key2) {
    	
    	// �����˻�Ʈ���� ���� comparator�� �����Ǿ� �ִ��� �������� ���� 2���� Ű���� ���ϴ� ����� �ٸ���.
    	// ���� comparator�� null�� ��쿡�� �ʵ� comparator���� null�̵ǹǷ� ���ڴ� ���� �������� �ʴ´�.
    	// key1�� Comparable<K> �������̽������� �� ��ȯ�ϰ� compareTo �޼��带 ȣ���Ͽ� key2�� ���Ѵ�.
    	// ���� comparator�� null�� �ƴ� ��쿡�� ������ ���� comparator�� compare �޼��带 ȣ���Ͽ� 
    	// �� Ű �� key 1, key2�� ��� ���踦 �Ǵ��Ѵ�. 
    	// key1 > key2�� ���, key1<key2�� ���� , key1 == key2�� 0
        return (comparator == null) ? ((Comparable<K>)key1).compareTo(key2)
                                    : comparator.compare(key1, key2);
    }

    //--- Ű�� �˻� ---//
    public V search(K key)    {
    	// ��Ʈ���� �����Ͽ� �˻��� �����Ѵ�. ���⼭ ���� ��带 p�� �Ѵ�
        Node<K,V> p = root;                                    

        while (true) {
            if (p == null)                                     // p�� null�̸� �˻��� �����Ѵ�(����).
                return null;                                   // ���˻� ����
            int cond = comp(key, p.getKey());                  // �˻��ϴ� �� key�� ������ ��� p�� Ű���� ���Ѵ�
            if (cond == 0)                                     // ���� ������ �˻��� ����(�˻� ����)�Ѵ�
                return p.getValue();                           
            else if (cond < 0)                                 // key�� ������ ���� ��带 ���� �ڽ� ���� ���ư���(���� �˻�).
                p = p.left;                                    // ������ ����Ʈ������ �˻�
            else                                               // key�� ũ�� ���� ��带 ������ �ڽ� ���� ���ư���(������ �˻�).
                p = p.right;                                   // �������� ����Ʈ������ �˻�
        }
    }

    //--- node�� �Ѹ��� �ϴ� ����Ʈ���� ��� <K,V>�� ���� ---//
    // �����ϴ� ����� Ű���� key�̰� �����ʹ� data�̴�. 
    private void addNode(Node<K,V> node, K key, V data) {
        int cond = comp(key, node.getKey());
        if (cond == 0)
            return;                                       // key�� �����˻�Ʈ���� �̹� ����
        else if (cond < 0) { // key < node.getKey() �̸�
            if (node.left == null) //���� �ڽ� ��尡 ������ �� ���� ��带 �����Ѵ�.
                node.left = new Node<K,V>(key, data, null, null);
            else  //���� �ڽ� ��尡 ������ ���� ��带 ���� �ڽ� ���� ���ư���.
                addNode(node.left, key, data);            // ���� ����Ʈ���� �ָ�
        } else { // key > node.getKey() �̸�
            if (node.right == null) // ������ �ڽ� ��尡 ������ �� ���� ��带 �����Ѵ�.
                node.right = new Node<K,V>(key, data, null, null);
            else  //������ �ڽ� ��尡 ������ ���� ��带 ������ �ڽ� ���� ���ư���.
                addNode(node.right, key, data);           // ������ ����Ʈ���� �ָ�
        }
    }

    //--- ��� ���� ---//
    public void add(K key, V data) {
    	// Ʈ���� ��� �ִ� ���(root == null)���� ��Ʈ������ ������ Ʈ���� ������ �Ѵ�. 
    	// Ű���� key, �����Ͱ� data, ���� �����Ϳ� ������ ������ �Ѵ� null�� ��� ����
        if (root == null)
            root = new Node<K,V>(key, data, null, null);
        //Ʈ���� ��� ���� �����Ƿ� ��� ����
        else
            addNode(root, key, data);
    }

    //--- Ű���� key�� ��带 ���� --//
    public boolean remove(K key) {
        Node<K,V> p = root;                    // ��ĵ ���� ���
        Node<K,V> parent = null;               // ��ĵ ���� ����� �θ���
        boolean isLeftChild = true;            // p�� parent�� ���� �ڽĳ���ΰ�?

        while (true) {
            if (p == null)                           // �� �̻� ���ư� �� ������
                return false;                        // ���� Ű���� �������� ����
            int cond = comp(key, p.getKey());        // key�� ��� p�� Ű���� ��
            if (cond == 0)                           // ������
                break;                               // ���˻� ����
            else {
                parent = p;                          // ������ �������� ���� �θ� ����
                if (cond < 0) {                      // key ���� ������
                    isLeftChild = true;              // �������� �ڽ����� ������
                    p = p.left;                      // ������ ����Ʈ������ �˻�
                } else {                             // key ���� ũ��
                    isLeftChild = false;             // ���������� �ڽ����� ������
                    p = p.right;                     // �������� ����Ʈ������ �˻�
                }
            }
        }

        if (p.left == null) {                         // p�� ������ �ڽ��� ����
            if (p == root)                     
                root = p.right;
            else if (isLeftChild)
                parent.left  = p.right;                    // �θ��� ���� �����Ͱ� ������ �ڽ��� ����Ŵ
            else
                parent.right = p.right;                    // �θ��� ������ �����Ͱ� ������ �ڽ��� ����Ŵ
        } else if (p.right == null) {                      // p�� ������ �ڽ��� ������
            if (p == root)
                root = p.left;
            else if (isLeftChild)
                parent.left  = p.left;                    // �θ��� ���� �����Ͱ� ���� �ڽ��� ����Ŵ
            else
                parent.right = p.left;                    // �θ��� ������ �����Ͱ� ���� �ڽ��� ����Ŵ
        } else {
            parent = p;
            Node<K,V> left = p.left;                     // ����Ʈ�� ��� �ִ� ���
            isLeftChild = true;
            while (left.right != null) {                 // �ִ� ����� left�� �˻�
                parent = left;
                left = left.right;
                isLeftChild = false;
            }
            p.key  = left.key;                           //  left�� Ű���� p�� �̵�
            p.data = left.data;                          //  left�� �����͸� p�� �̵�
            if (isLeftChild)
                parent.left  = left.left;                // left�� ����
            else
                parent.right = left.left;                // left�� ����
        }
        return true;
    }

    //--- node�� ��Ʈ�� �ϴ� ����Ʈ���� ��带 Ű���� ������������ ǥ�� ---//
    private void printSubTree(Node node) {
        if (node != null) {
            printSubTree(node.left);                            // ���� ����Ʈ���� Ű���� ������������ ǥ��
            System.out.println(node.key + " " + node.data);     // node�� ǥ��
            printSubTree(node.right);                           // ������ ����Ʈ���� Ű���� ������������ ǥ��
        }
    }

    //--- ��ü ��带 Ű���� ������������ ǥ�� ---//
    public void print() {
        printSubTree(root);
    }
}
