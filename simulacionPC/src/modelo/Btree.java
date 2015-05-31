package Modelo;


import java.util.ArrayList;
import java.util.LinkedList;
import javax.sound.sampled.FloatControl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author toshiba
 */
public class Btree {

    /**
     * @return the T
     */
    public static int getT() {
        return T;
    }

    /**
     * @return the LEFT_CHILD_NODE
     */
    public static int getLEFT_CHILD_NODE() {
        return LEFT_CHILD_NODE;
    }

    /**
     * @param aLEFT_CHILD_NODE the LEFT_CHILD_NODE to set
     */
    public static void setLEFT_CHILD_NODE(int aLEFT_CHILD_NODE) {
        LEFT_CHILD_NODE = aLEFT_CHILD_NODE;
    }

    /**
     * @return the RIGHT_CHILD_NODE
     */
    public static int getRIGHT_CHILD_NODE() {
        return RIGHT_CHILD_NODE;
    }

    /**
     * @param aRIGHT_CHILD_NODE the RIGHT_CHILD_NODE to set
     */
    public static void setRIGHT_CHILD_NODE(int aRIGHT_CHILD_NODE) {
        RIGHT_CHILD_NODE = aRIGHT_CHILD_NODE;
    }
    private int altura;
    private int niveles;
    private LinkedList<Node>listAnchura;
    private LinkedList<Node>listProfundidad;
    private static final int T = 2;
    private Node mRootNode;
    private static int LEFT_CHILD_NODE = 0;
    private static int RIGHT_CHILD_NODE = 1;
    
     public Btree() {
        mRootNode = new Node(getT());
        mRootNode.mIsLeafNode = true;
        listAnchura= new LinkedList<>();
    }

    public void add(int key, Object object) {
        Node rootNode = getmRootNode();
        if (!update(mRootNode, key, object)) {
            if (rootNode.mNumKeys == (2 * getT() - 1)) {
                Node newRootNode = new Node(getT());
                setmRootNode(newRootNode);
                newRootNode.mIsLeafNode = false;
                mRootNode.mChildNodes[0] = rootNode;
                splitChildNode(newRootNode, 0, rootNode); // Split rootNode and move its median (middle) key up into newRootNode.
                insertIntoNonFullNode(newRootNode, key, object); // Insert the key into the B-Tree with root newRootNode.
            } else {
                insertIntoNonFullNode(rootNode, key, object); // Insert the key into the B-Tree with root rootNode.
            }
        }
    }

        // Split the node, node, of a B-Tree into two nodes that both contain T-1 elements and move node's median key up to the parentNode.
    // This method will only be called if node is full; node is the i-th child of parentNode.
    void splitChildNode(Node parentNode, int i, Node node) {
        Node newNode = new Node(getT());
        newNode.mIsLeafNode = node.mIsLeafNode;
        newNode.mNumKeys = getT() - 1;
        for (int j = 0; j < getT() - 1; j++) { // Copy the last T-1 elements of node into newNode.
            newNode.mKeys[j] = node.mKeys[j + getT()];
            newNode.mObjects[j] = node.mObjects[j + getT()];
        }
        if (!newNode.mIsLeafNode) {
            for (int j = 0; j < getT(); j++) { // Copy the last T pointers of node into newNode.
                newNode.mChildNodes[j] = node.mChildNodes[j + getT()];
            }
            for (int j = getT(); j <= node.mNumKeys; j++) {
                node.mChildNodes[j] = null;
            }
        }
        for (int j = getT(); j < node.mNumKeys; j++) {
            node.mKeys[j] = 0;
            node.mObjects[j] = null;
        }
        node.mNumKeys = getT() - 1;

        // Insert a (child) pointer to node newNode into the parentNode, moving other keys and pointers as necessary.
        for (int j = parentNode.mNumKeys; j >= i + 1; j--) {
            parentNode.mChildNodes[j + 1] = parentNode.mChildNodes[j];
        }
        parentNode.mChildNodes[i + 1] = newNode;
        for (int j = parentNode.mNumKeys - 1; j >= i; j--) {
            parentNode.mKeys[j + 1] = parentNode.mKeys[j];
            parentNode.mObjects[j + 1] = parentNode.mObjects[j];
        }
        parentNode.mKeys[i] = node.mKeys[getT() - 1];
        parentNode.mObjects[i] = node.mObjects[getT() - 1];
        node.mKeys[getT() - 1] = 0;
        node.mObjects[getT() - 1] = null;
        parentNode.mNumKeys++;
    }

    // Insert an element into a B-Tree. (The element will ultimately be inserted into a leaf node). 
    void insertIntoNonFullNode(Node node, int key, Object object) {
        int i = node.mNumKeys - 1;
        if (node.mIsLeafNode) {
            // Since node is not a full node insert the new element into its proper place within node.
            while (i >= 0 && key < node.mKeys[i]) {
                node.mKeys[i + 1] = node.mKeys[i];
                node.mObjects[i + 1] = node.mObjects[i];
                i--;
            }
            i++;
            node.mKeys[i] = key;
            node.mObjects[i] = object;
            node.mNumKeys++;
        } else {
                        // Move back from the last key of node until we find the child pointer to the node
            // that is the root node of the subtree where the new element should be placed.
            while (i >= 0 && key < node.mKeys[i]) {
                i--;
            }
            i++;
            if (node.mChildNodes[i].mNumKeys == (2 * getT() - 1)) {
                splitChildNode(node, i, node.mChildNodes[i]);
                if (key > node.mKeys[i]) {
                    i++;
                }
            }
            insertIntoNonFullNode(node.mChildNodes[i], key, object);
        }
    }

    public void delete(int key) {
        delete(getmRootNode(), key);
    }

    public void delete(Node node, int key) {
        if (node.mIsLeafNode) { // 1. If the key is in node and node is a leaf node, then delete the key from node.
            int i;
            if ((i = node.binarySearch(key)) != -1) { // key is i-th key of node if node contains key.
                node.remove(i, getLEFT_CHILD_NODE());
            }
        } else {
            int i;
            if ((i = node.binarySearch(key)) != -1) { // 2. If node is an internal node and it contains the key... (key is i-th key of node if node contains key)                   
                Node leftChildNode = node.mChildNodes[i];
                Node rightChildNode = node.mChildNodes[i + 1];
                if (leftChildNode.mNumKeys >= getT()) { // 2a. If the predecessor child node has at least T keys...
                    Node predecessorNode = leftChildNode;
                    Node erasureNode = predecessorNode; // Make sure not to delete a key from a node with only T - 1 elements.
                    while (!predecessorNode.mIsLeafNode) { // Therefore only descend to the previous node (erasureNode) of the predecessor node and delete the key using 3.
                        erasureNode = predecessorNode;
                        predecessorNode = predecessorNode.mChildNodes[node.mNumKeys - 1];
                    }
                    node.mKeys[i] = predecessorNode.mKeys[predecessorNode.mNumKeys - 1];
                    node.mObjects[i] = predecessorNode.mObjects[predecessorNode.mNumKeys - 1];
                    delete(erasureNode, node.mKeys[i]);
                } else if (rightChildNode.mNumKeys >= getT()) { // 2b. If the successor child node has at least T keys...
                    Node successorNode = rightChildNode;
                    Node erasureNode = successorNode; // Make sure not to delete a key from a node with only T - 1 elements.
                    while (!successorNode.mIsLeafNode) { // Therefore only descend to the previous node (erasureNode) of the predecessor node and delete the key using 3.
                        erasureNode = successorNode;
                        successorNode = successorNode.mChildNodes[0];
                    }
                    node.mKeys[i] = successorNode.mKeys[0];
                    node.mObjects[i] = successorNode.mObjects[0];
                    delete(erasureNode, node.mKeys[i]);
                } else { // 2c. If both the predecessor and the successor child node have only T - 1 keys...
                    // If both of the two child nodes to the left and right of the deleted element have the minimum number of elements,
                    // namely T - 1, they can then be joined into a single node with 2 * T - 2 elements.
                    int medianKeyIndex = mergeNodes(leftChildNode, rightChildNode);
                    moveKey(node, i, getRIGHT_CHILD_NODE(), leftChildNode, medianKeyIndex); // Delete i's right child pointer from node.
                    delete(leftChildNode, key);
                }
            } else { // 3. If the key is not resent in node, descent to the root of the appropriate subtree that must contain key...
                // The method is structured to guarantee that whenever delete is called recursively on node "node", the number of keys
                // in node is at least the minimum degree T. Note that this condition requires one more key than the minimum required
                // by usual B-tree conditions. This strengthened condition allows us to delete a key from the tree in one downward pass
                // without having to "back up".
                i = node.subtreeRootNodeIndex(key);
                Node childNode = node.mChildNodes[i]; // childNode is i-th child of node.                               
                if (childNode.mNumKeys == getT() - 1) {
                    Node leftChildSibling = (i - 1 >= 0) ? node.mChildNodes[i - 1] : null;
                    Node rightChildSibling = (i + 1 <= node.mNumKeys) ? node.mChildNodes[i + 1] : null;
                    if (leftChildSibling != null && leftChildSibling.mNumKeys >= getT()) { // 3a. The left sibling has >= T keys...                                              
                        // Move a key from the subtree's root node down into childNode along with the appropriate child pointer.
                        // Therefore, first shift all elements and children of childNode right by 1.
                        childNode.shiftRightByOne();
                        childNode.mKeys[0] = node.mKeys[i - 1]; // i - 1 is the key index in node that is smaller than childNode's smallest key.
                        childNode.mObjects[0] = node.mObjects[i - 1];
                        if (!childNode.mIsLeafNode) {
                            childNode.mChildNodes[0] = leftChildSibling.mChildNodes[leftChildSibling.mNumKeys];
                        }
                        childNode.mNumKeys++;

                        // Move a key from the left sibling into the subtree's root node. 
                        node.mKeys[i - 1] = leftChildSibling.mKeys[leftChildSibling.mNumKeys - 1];
                        node.mObjects[i - 1] = leftChildSibling.mObjects[leftChildSibling.mNumKeys - 1];

                        // Remove the key from the left sibling along with its right child node.
                        leftChildSibling.remove(leftChildSibling.mNumKeys - 1, getRIGHT_CHILD_NODE());
                    } else if (rightChildSibling != null && rightChildSibling.mNumKeys >= getT()) { // 3a. The right sibling has >= T keys...                                    
                        // Move a key from the subtree's root node down into childNode along with the appropriate child pointer.
                        childNode.mKeys[childNode.mNumKeys] = node.mKeys[i]; // i is the key index in node that is bigger than childNode's biggest key.
                        childNode.mObjects[childNode.mNumKeys] = node.mObjects[i];
                        if (!childNode.mIsLeafNode) {
                            childNode.mChildNodes[childNode.mNumKeys + 1] = rightChildSibling.mChildNodes[0];
                        }
                        childNode.mNumKeys++;

                        // Move a key from the right sibling into the subtree's root node. 
                        node.mKeys[i] = rightChildSibling.mKeys[0];
                        node.mObjects[i] = rightChildSibling.mObjects[0];

                        // Remove the key from the right sibling along with its left child node.                                                
                        rightChildSibling.remove(0, getLEFT_CHILD_NODE());
                    } else { // 3b. Both of childNode's siblings have only T - 1 keys each...
                        if (leftChildSibling != null) {
                            int medianKeyIndex = mergeNodes(childNode, leftChildSibling);
                            moveKey(node, i - 1, getLEFT_CHILD_NODE(), childNode, medianKeyIndex); // i - 1 is the median key index in node when merging with the left sibling.                          
                        } else if (rightChildSibling != null) {
                            int medianKeyIndex = mergeNodes(childNode, rightChildSibling);
                            moveKey(node, i, getRIGHT_CHILD_NODE(), childNode, medianKeyIndex); // i is the median key index in node when merging with the right sibling.
                        }
                    }
                }
                delete(childNode, key);
            }
        }
    }

    // Merge two nodes and keep the median key (element) empty.
    int mergeNodes(Node dstNode, Node srcNode) {
        int medianKeyIndex;
        if (srcNode.mKeys[0] < dstNode.mKeys[dstNode.mNumKeys - 1]) {
            int i;
            // Shift all elements of dstNode right by srcNode.mNumKeys + 1 to make place for the srcNode and the median key.
            if (!dstNode.mIsLeafNode) {
                dstNode.mChildNodes[srcNode.mNumKeys + dstNode.mNumKeys + 1] = dstNode.mChildNodes[dstNode.mNumKeys];
            }
            for (i = dstNode.mNumKeys; i > 0; i--) {
                dstNode.mKeys[srcNode.mNumKeys + i] = dstNode.mKeys[i - 1];
                dstNode.mObjects[srcNode.mNumKeys + i] = dstNode.mObjects[i - 1];
                if (!dstNode.mIsLeafNode) {
                    dstNode.mChildNodes[srcNode.mNumKeys + i] = dstNode.mChildNodes[i - 1];
                }
            }

            // Clear the median key (element).
            medianKeyIndex = srcNode.mNumKeys;
            dstNode.mKeys[medianKeyIndex] = 0;
            dstNode.mObjects[medianKeyIndex] = null;

            // Copy the srcNode's elements into dstNode.
            for (i = 0; i < srcNode.mNumKeys; i++) {
                dstNode.mKeys[i] = srcNode.mKeys[i];
                dstNode.mObjects[i] = srcNode.mObjects[i];
                if (!srcNode.mIsLeafNode) {
                    dstNode.mChildNodes[i] = srcNode.mChildNodes[i];
                }
            }
            if (!srcNode.mIsLeafNode) {
                dstNode.mChildNodes[i] = srcNode.mChildNodes[i];
            }
        } else {
            // Clear the median key (element).
            medianKeyIndex = dstNode.mNumKeys;
            dstNode.mKeys[medianKeyIndex] = 0;
            dstNode.mObjects[medianKeyIndex] = null;

            // Copy the srcNode's elements into dstNode.
            int offset = medianKeyIndex + 1;
            int i;
            for (i = 0; i < srcNode.mNumKeys; i++) {
                dstNode.mKeys[offset + i] = srcNode.mKeys[i];
                dstNode.mObjects[offset + i] = srcNode.mObjects[i];
                if (!srcNode.mIsLeafNode) {
                    dstNode.mChildNodes[offset + i] = srcNode.mChildNodes[i];
                }
            }
            if (!srcNode.mIsLeafNode) {
                dstNode.mChildNodes[offset + i] = srcNode.mChildNodes[i];
            }
        }
        dstNode.mNumKeys += srcNode.mNumKeys;
        return medianKeyIndex;
    }

    // Move the key from srcNode at index into dstNode at medianKeyIndex. Note that the element at index is already empty.
    void moveKey(Node srcNode, int srcKeyIndex, int childIndex, Node dstNode, int medianKeyIndex) {
        dstNode.mKeys[medianKeyIndex] = srcNode.mKeys[srcKeyIndex];
        dstNode.mObjects[medianKeyIndex] = srcNode.mObjects[srcKeyIndex];
        dstNode.mNumKeys++;

        srcNode.remove(srcKeyIndex, childIndex);

        if (srcNode == getmRootNode() && srcNode.mNumKeys == 0) {
            setmRootNode(dstNode);
        }
    }

    public Object search(int key) {
        return search(getmRootNode(), key);
    }

    // Recursive search method.
    public Object search(Node node, int key) {
        int i = 0;
        while (i < node.mNumKeys && key > node.mKeys[i]) {
            i++;
        }
       
        if (i < node.mNumKeys && key == node.mKeys[i]) {
            System.out.println(""+node.binarySearch(key));
            return node.mObjects[i];
        }
        if (node.mIsLeafNode) {
            return null;
        } else {
            return search(node.mChildNodes[i], key);
        }
    }

    public Object search2(int key) {
        return search2(getmRootNode(), key);
    }

    // Iterative search method.
    public Object search2(Node node, int key) {
        while (node != null) {
            int i = 0;
            while (i < node.mNumKeys && key > node.mKeys[i]) {
                i++;
            }
            if (i < node.mNumKeys && key == node.mKeys[i]) {
                return node.mObjects[i];
            }
            if (node.mIsLeafNode) {
                return null;
            } else {
                node = node.mChildNodes[i];
            }
        }
        return null;
    }

    private boolean update(Node node, int key, Object object) {
        while (node != null) {
            int i = 0;
            while (i < node.mNumKeys && key > node.mKeys[i]) {
                i++;
            }
            // tener cuidado actualiza inmediatamente sin comprobaciones
            if (i < node.mNumKeys && key == node.mKeys[i]) {
                node.mObjects[i] = object;
                return true;
            }
            if (node.mIsLeafNode) {
                return false;
            } else {
                node = node.mChildNodes[i];
            }
        }
        return false;
    }

    // Inorder walk over the tree.
    String printBTree(Node node) {
        String string = "";
        if (node != null) {
            if (node.mIsLeafNode) {
                for (int i = 0; i < node.mNumKeys; i++) {
                    string += node.mObjects[i] + ", ";
                }
            } else {
                int i;
                for (i = 0; i < node.mNumKeys; i++) {
                    string += printBTree(node.mChildNodes[i]);
                    string += node.mObjects[i] + ", ";
                }
                string += printBTree(node.mChildNodes[i]);
            }
        }
        return string;
    }

    public String toString() {
        return printBTree(getmRootNode());
    }

    void validate() throws Exception {
        ArrayList<Integer> array = getKeys(getmRootNode());
        for (int i = 0; i < array.size() - 1; i++) {
            if (array.get(i) >= array.get(i + 1)) {
                throw new Exception("B-Tree invalid: " + array.get(i) + " greater than " + array.get(i + 1));
            }
        }
    }

    // Inorder walk over the tree.
    ArrayList<Integer> getKeys(Node node) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        if (node != null) {
            if (node.mIsLeafNode) {
                for (int i = 0; i < node.mNumKeys; i++) {
                    array.add(node.mKeys[i]);
                }
            } else {
                int i;
                for (i = 0; i < node.mNumKeys; i++) {
                    array.addAll(getKeys(node.mChildNodes[i]));
                    array.add(node.mKeys[i]);
                }
                array.addAll(getKeys(node.mChildNodes[i]));
            }
        }
        return array;
    }

    public Node getmRootNode() {
        return mRootNode;
    }
    public void recorrido(){
        
       LinkedList<Node>cola= new LinkedList<>();
        if (getmRootNode()!=null) {
            cola.add(getmRootNode());
           
        }
       
        setListAnchura(recorridoAnchura(cola, getmRootNode()));
        LinkedList<Node>auxProfundidad=new LinkedList<>();
        //el campo 0 es para indicar la altura q empieza en cero
        setListProfundidad(recorridoProfundidad(getmRootNode(), listAnchura,0));
        
    }
    private LinkedList<Node> recorridoAnchura(LinkedList<Node>cola,Node nodo){
       LinkedList<Node>recorrido= new LinkedList<>();
        while (!cola.isEmpty()) {
            Node aux=new Node(getT());
            aux=cola.remove();
            for (int i = 0; i < aux.mChildNodes.length; i++) {
                cola.add(aux.mChildNodes[i]);
            }
            
            
        }
        return recorrido;
    }
    //posorden
    private LinkedList<Node> recorridoProfundidad(Node nodo,LinkedList<Node>listAux,int auxAltura) {
        auxAltura=altura+1;
        if (nodo.mIsLeafNode) {
            listAux.add(nodo);
            if (getAltura()<auxAltura) {
                setAltura(auxAltura);
            }
            return listAux;
        }
        else{
            for (int i = 0; i < nodo.mChildNodes.length; i++) {
                listAux=recorridoProfundidad(nodo.mChildNodes[i], listAux,auxAltura);
            }
            listAux.add(nodo);
            
        }
        return listAux;
    }
    // realiza los recorridos y obtiene altura y niveles del arbol
    public void obtenerInformacion(){
        recorrido();
        setNiveles(getAltura()-1);
        
    }
    

    /**
     * @return the altura
     */
    public int getAltura() {
        return altura;
    }

    /**
     * @param altura the altura to set
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     * @return the niveles
     */
    public int getNiveles() {
        return niveles;
    }

    /**
     * @param niveles the niveles to set
     */
    public void setNiveles(int niveles) {
        this.niveles = niveles;
    }

    /**
     * @return the listAnchura
     */
    public LinkedList<Node> getListAnchura() {
        return listAnchura;
    }

    /**
     * @param listAnchura the listAnchura to set
     */
    public void setListAnchura(LinkedList<Node> listAnchura) {
        this.listAnchura = listAnchura;
    }

    /**
     * @param mRootNode the mRootNode to set
     */
    public void setmRootNode(Node mRootNode) {
        this.mRootNode = mRootNode;
    }

    /**
     * @return the listProfundidad
     */
    public LinkedList<Node> getListProfundidad() {
        return listProfundidad;
    }

    /**
     * @param listProfundidad the listProfundidad to set
     */
    public void setListProfundidad(LinkedList<Node> listProfundidad) {
        this.listProfundidad = listProfundidad;
    }

    
    
    
}
   

