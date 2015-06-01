package Modelo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author toshiba
 */
public class Node {

    //cantidad objetos como nodo propio
    public int mNumKeys = 0;
    //cada unos delos identificadores del objeto que se encuentre en el nodo
    public int[] mKeys;
    // cada objeto presente en el nodo
    public Object[] mObjects;
    // cada uno de los hijos que tiene el nodo
    public Node[] mChildNodes;
    //es hoja
    public boolean mIsLeafNode;

    //constructor con el parametro que dara la pauta de cuatos objetos tendra por nodo y cuantos nodos hijos puede llegar a tener
    public Node(int T) {
        mKeys = new int[2 * T - 1];
        mObjects = new Object[2 * T - 1];
        mChildNodes = new Node[2 * T];
    }
//ubicacion del identificador para posteriormente ser agregado como hijo de de otro nodo
    public int binarySearch(int key) {
        int leftIndex = 0;
        int rightIndex = mNumKeys - 1;

        while (leftIndex <= rightIndex) {
            final int middleIndex = leftIndex + ((rightIndex - leftIndex) / 2);
            if (mKeys[middleIndex] < key) {
                leftIndex = middleIndex + 1;
            } else if (mKeys[middleIndex] > key) {
                rightIndex = middleIndex - 1;
            } else {
                return middleIndex;
            }
        }

        return -1;
    }

    public boolean contains(int key) {
        return binarySearch(key) != -1;
    }

    // Remove an element from a node and also the left (0) or right (+1) child.
    public void remove(int index, int leftOrRightChild) {
        if (index >= 0) {
            int i;
            for (i = index; i < mNumKeys - 1; i++) {
                mKeys[i] = mKeys[i + 1];
                mObjects[i] = mObjects[i + 1];
                if (!mIsLeafNode) {
                    if (i >= index + leftOrRightChild) {
                        mChildNodes[i] = mChildNodes[i + 1];
                    }
                }
            }
            mKeys[i] = 0;
            mObjects[i] = null;
            if (!mIsLeafNode) {
                if (i >= index + leftOrRightChild) {
                    mChildNodes[i] = mChildNodes[i + 1];
                }
                mChildNodes[i + 1] = null;
            }
            mNumKeys--;
        }
    }

    public void shiftRightByOne() {
        if (!mIsLeafNode) {
            mChildNodes[mNumKeys + 1] = mChildNodes[mNumKeys];
        }
        for (int i = mNumKeys - 1; i >= 0; i--) {
            mKeys[i + 1] = mKeys[i];
            mObjects[i + 1] = mObjects[i];
            if (!mIsLeafNode) {
                mChildNodes[i + 1] = mChildNodes[i];
            }
        }
    }

    public int subtreeRootNodeIndex(int key) {
        for (int i = 0; i < mNumKeys; i++) {
            if (key < mKeys[i]) {
                return i;
            }
        }
        return mNumKeys;
    }
    public boolean isLleaf(){
     int cont=0;
        for (int i = 0; i < mChildNodes.length; i++) {
            if (mChildNodes[i]==null) {
                cont++;
            }
        }
        return mChildNodes.length==cont;
    }
}
