package sec13;

import java.util.Comparator;

public class BinTree<K,V> {
    //--- 노드 ---//
    static class Node<K,V> {
        private K key;              // 키값
        private V data;             // 데이터
        private Node<K,V> left;     // 왼쪽 포인터(왼쪽 자식노드에 대한 참조)
        private Node<K,V> right;    // 오른쪽 포인터(오른쪽 자식노드에 대한 참조)

        //--- 생성자(constructor) ---//
        Node(K key, V data, Node<K,V> left, Node<K,V> right) {
            this.key   = key;
            this.data  = data;
            this.left  = left;
            this.right = right;
        }

        //--- 키값을 그대로 반환 ---//
        K getKey() {
            return key;
        }

        //--- 데이터를 그대로 반환 ---//
        V getValue() {
            return data;
        }

        //--- 데이터를 출력하는 메서드 ---//
        void print() {
            System.out.println(data);
        }
    }
    
     // 루트
    private Node<K,V> root; 
    // 키값의 대소 관계를 판단하는 비교자(Comparator)
    // 이진검색트리를 생성하는 생성자에서 비교자를 명시적으로 설정하지 않으면
    // 자동으로 null이 되도록 초기자를 null로 주어 선언한다.
    private Comparator<? super K> comparator = null;    

    //--- 생성자(constructor) ---//
    //루트에 대한 참조인 root를 null로 하여 노드가 하나도 없는(비어있는)
    //이진검색트리를 생성하는 생성자
    public BinTree() {
        root = null;
    }

    //--- 생성자(constructor) ---//
    //
    public BinTree(Comparator<? super K> c) {
    	
    	//this()에 의해 인수를 전달받지 않은 생성자 BinTree()를 호출한다. 
    	//root가 null인(비어 있는)이진검색트리를 생성한다.
        this(); 
        //필드 comparator에 전달받은 c를 설정
        comparator = c;
    }

    //--- 두 키값을 비교 ---//
    
    private int comp(K key1, K key2) {
    	
    	// 이진검색트리에 비교자 comparator가 설정되어 있는지 않은지에 따라 2개의 키값을 비교하는 방법이 다르다.
    	// 비교자 comparator가 null인 경우에는 필드 comparator값은 null이되므로 비교자는 따로 설정되지 않는다.
    	// key1을 Comparable<K> 인터페이스형으로 형 변환하고 compareTo 메서드를 호출하여 key2와 비교한다.
    	// 비교자 comparator가 null이 아닌 경우에는 설정된 비교자 comparator의 compare 메서드를 호출하여 
    	// 두 키 값 key 1, key2의 대소 관계를 판단한다. 
    	// key1 > key2면 양수, key1<key2면 음수 , key1 == key2면 0
        return (comparator == null) ? ((Comparable<K>)key1).compareTo(key2)
                                    : comparator.compare(key1, key2);
    }

    //--- 키로 검색 ---//
    public V search(K key)    {
    	// 루트부터 선택하여 검색을 진행한다. 여기서 선택 노드를 p로 한다
        Node<K,V> p = root;                                    

        while (true) {
            if (p == null)                                     // p가 null이면 검색에 실패한다(종료).
                return null;                                   // …검색 실패
            int cond = comp(key, p.getKey());                  // 검색하는 값 key와 선택한 노드 p의 키값을 비교한다
            if (cond == 0)                                     // 값이 같으면 검색에 성공(검색 종료)한다
                return p.getValue();                           
            else if (cond < 0)                                 // key가 작으면 선택 노드를 왼쪽 자식 노드로 나아간다(왼쪽 검색).
                p = p.left;                                    // …왼쪽 서브트리에서 검색
            else                                               // key가 크면 선택 노드를 오른쪽 자식 노드로 나아간다(오른쪽 검색).
                p = p.right;                                   // …오른쪽 서브트리에서 검색
        }
    }

    //--- node를 뿌리로 하는 서브트리에 노드 <K,V>를 삽입 ---//
    // 삽입하는 노드의 키값은 key이고 데이터는 data이다. 
    private void addNode(Node<K,V> node, K key, V data) {
        int cond = comp(key, node.getKey());
        if (cond == 0)
            return;                                       // key가 이진검색트리에 이미 존재
        else if (cond < 0) { // key < node.getKey() 이면
            if (node.left == null) //왼쪽 자식 노드가 없으면 그 곳에 노드를 삽입한다.
                node.left = new Node<K,V>(key, data, null, null);
            else  //왼쪽 자식 노드가 있으면 선택 노드를 왼쪽 자식 노드로 나아간다.
                addNode(node.left, key, data);            // 왼쪽 서브트리에 주목
        } else { // key > node.getKey() 이면
            if (node.right == null) // 오른쪽 자식 노드가 없으면 그 곳에 노드를 삽입한다.
                node.right = new Node<K,V>(key, data, null, null);
            else  //오른쪽 자식 노드가 있으면 선택 노드를 오른쪽 자식 노드로 나아간다.
                addNode(node.right, key, data);           // 오른쪽 서브트리에 주목
        }
    }

    //--- 노드 삽입 ---//
    public void add(K key, V data) {
    	// 트리가 비어 있는 경우(root == null)에는 루트만으로 구성된 트리를 만들어야 한다. 
    	// 키값ㅇ key, 데이터가 data, 왼쪽 포인터와 오른쪽 포인터 둘다 null인 노드 생성
        if (root == null)
            root = new Node<K,V>(key, data, null, null);
        //트리가 비어 있지 않으므로 노드 삽입
        else
            addNode(root, key, data);
    }

    //--- 키값이 key인 노드를 삭제 --//
    public boolean remove(K key) {
        Node<K,V> p = root;                    // 스캔 중인 노드
        Node<K,V> parent = null;               // 스캔 중인 노드의 부모노드
        boolean isLeftChild = true;            // p는 parent의 왼쪽 자식노드인가?

        while (true) {
            if (p == null)                           // 더 이상 나아갈 수 없으면
                return false;                        // …그 키값은 존재하지 않음
            int cond = comp(key, p.getKey());        // key와 노드 p의 키값을 비교
            if (cond == 0)                           // 같으면
                break;                               // …검색 성공
            else {
                parent = p;                          // 가지로 내려가기 전에 부모를 설정
                if (cond < 0) {                      // key 쪽이 작으면
                    isLeftChild = true;              // …왼쪽의 자식으로 내려감
                    p = p.left;                      // …왼쪽 서브트리에서 검색
                } else {                             // key 쪽이 크면
                    isLeftChild = false;             // …오른쪽의 자식으로 내려감
                    p = p.right;                     // …오른쪽 서브트리에서 검색
                }
            }
        }

        if (p.left == null) {                         // p에 왼쪽의 자식이 없음
            if (p == root)                     
                root = p.right;
            else if (isLeftChild)
                parent.left  = p.right;                    // 부모의 왼쪽 포인터가 오른쪽 자식을 가리킴
            else
                parent.right = p.right;                    // 부모의 오른쪽 포인터가 오른쪽 자식을 가리킴
        } else if (p.right == null) {                      // p에 오른쪽 자식이 없음…
            if (p == root)
                root = p.left;
            else if (isLeftChild)
                parent.left  = p.left;                    // 부모의 왼쪽 포인터가 왼쪽 자식을 가리킴
            else
                parent.right = p.left;                    // 부모의 오른쪽 포인터가 왼쪽 자식을 가리킴
        } else {
            parent = p;
            Node<K,V> left = p.left;                     // 서브트리 가운데 최대 노드
            isLeftChild = true;
            while (left.right != null) {                 // 최대 노드의 left를 검색
                parent = left;
                left = left.right;
                isLeftChild = false;
            }
            p.key  = left.key;                           //  left의 키값을 p로 이동
            p.data = left.data;                          //  left의 데이터를 p로 이동
            if (isLeftChild)
                parent.left  = left.left;                // left를 삭제
            else
                parent.right = left.left;                // left를 삭제
        }
        return true;
    }

    //--- node를 루트로 하는 서브트리의 노드를 키값의 오름차순으로 표시 ---//
    private void printSubTree(Node node) {
        if (node != null) {
            printSubTree(node.left);                            // 왼쪽 서브트리를 키값의 오름차순으로 표시
            System.out.println(node.key + " " + node.data);     // node를 표시
            printSubTree(node.right);                           // 오른쪽 서브트리를 키값의 오름차순으로 표시
        }
    }

    //--- 전체 노드를 키값의 오름차순으로 표시 ---//
    public void print() {
        printSubTree(root);
    }
}
