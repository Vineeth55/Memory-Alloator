// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 

    public void Defragment() {
        Dictionary newTree;
        if(this.type == 2) {
            newTree = new BSTree();
        }
        else {
            newTree = new AVLTree();
        }
        Dictionary node = this.freeBlk.getFirst();
        while (node != null) {
            newTree.Insert(node.key, node.size, node.address);
            node = node.getNext();
            }
        Dictionary node1 = newTree.getFirst();
        Dictionary node2;
        if(node1 != null) {
            node2 = node1.getNext();
        }
        else {
            node2 = null;
        }
            
        while(node1 != null && node2 != null) {
            if(node1.key + node1.size == node2.key) {
                this.freeBlk.Delete(new BSTree(node1.key, node1.size, node1.address));
                this.freeBlk.Delete(new BSTree(node2.key, node2.size, node2.address));
                newTree.Delete(node1);
                newTree.Delete(node2);
                this.freeBlk.Insert(node1.key, node1.size+node2.size, node1.size+node2.size);                    
                node1 = newTree.Insert(node1.size+node2.size,node1.size+node2.size,node1.key);
                node2 = node1.getNext();
            }
            else {
                node1 = node2;
                node2 = node2.getNext();
            }
        }    
        Dictionary curr = newTree.getFirst();
        while(curr != null) {
            newTree.Delete(curr);
            curr = newTree.getFirst();
        }
        
        return;
    }

    public static void main(String[] args) {
        A2DynamicMem obj = new A2DynamicMem(100, 3);
        obj.Allocate(10);
        obj.Free(0);
        obj.Free(1);
    }
}