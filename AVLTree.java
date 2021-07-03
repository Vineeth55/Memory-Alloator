// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    public AVLTree Insert(int address, int size, int key) 
    { 
        AVLTree curr = this.goTop();
        if(curr == null) {
            this.right = new AVLTree(address, size, key);
            this.right.parent = this;
            this.right.height = 1;
            return this.right;
        }
        else {
            AVLTree newNode = add(curr,address,size,key);
            AVLTree insertedNode = newNode.parent;
            AVLTree curr1 = insertedNode;
            while(curr1.parent != null) {
                if(curr1.right == null || curr1.left == null) {
                    curr1.height++;
                }
                else if(curr1.right != null && curr1.left != null) {
                    if(curr1.right.height < curr1.left.height) {
                        curr1.height = 1 + curr1.left.height;
                    }
                    else {
                        curr1.height = 1 + curr1.right.height;
                    }
                }
                curr1 = curr1.parent;
            }
            checkBalance(insertedNode);
            return newNode;

        }
        
    }

    public boolean Delete(Dictionary e)
    {
        AVLTree curr = this.goTop();
        if(curr != null) {
            AVLTree start = null;
            while(curr != null) {
                if(curr.address == e.address && curr.size == e.size && curr.key == e.key) {
                    if(curr.left == null && curr.right == null) {
                        if(curr.isLeftChild()) {
                            curr.parent.left = null;
                            start = curr.parent;
                            curr.parent = null;
                        }
                        else {
                            curr.parent.right = null;
                            start = curr.parent;
                            curr.parent = null;
                        }
                    }
                    else if(curr.left == null) {
                        if (curr.isLeftChild()) {
                            curr.right.parent = curr.parent;
                            curr.parent.left = curr.right;
                            curr.right = null;
                            start = curr.parent;
                            curr.parent = null;
                            }
                        else {
                            curr.right.parent = curr.parent;
                            curr.parent.right = curr.right;
                            curr.right = null;
                            start = curr.parent;
                            curr.parent = null;
                        }
                    }
                    else if(curr.right == null) {
                        if(curr.isLeftChild()) {
                            curr.left.parent = curr.parent;
                            curr.parent.left = curr.left;
                            curr.left = null;
                            start = curr.parent;
                            curr.parent = null;
                        }
                        else {
                            curr.left.parent = curr.parent;
                            curr.parent.right = curr.left;
                            curr.right = null;
                            start = curr.parent;
                            curr.parent = null;
                        }
                    }
                    else {
                        AVLTree curr1 = curr.getNext();
                        if(curr.right == curr1) {
                            start = curr1;
                        }
                        else {
                            start = curr1.parent;
                            curr1.parent.left = curr1.right;
                            if(curr1.right != null) {
                                curr1.right.parent = curr1.parent;
                            }
                            curr1.right = curr.right;
                            if(curr.right != null) {
                                curr1.right.parent = curr1;
                            }
                        }
                        curr1.left = curr.left;
                        curr.left.parent = curr1;
                        curr1.parent = curr.parent;
                        if(curr.isLeftChild()) {
                            curr1.parent.left = curr1;
                        }
                        else {
                            curr1.parent.right = curr1;
                        }
                    }
                    curr.left = null;
                    curr.right = null;
                    curr.parent = null;
                    AVLTree curr2 = start;
                    while(start.parent != null) {
                        if(start.right == null && start.left == null) {
                            start.height--;
                        }
                        else if(start.right == null) {
                            start.height = 1 + start.left.height;
                        }
                        else if(start.left == null) {
                            start.height = 1 + start.right.height;
                        }
                        else {
                            if(start.right.height < start.left.height) {
                                start.height = 1 + start.left.height;
                            }
                            else {
                                start.height = 1 + start.right.height;
                            }
                        }
                        start = start.parent;
                    }
                checkBalance(curr2);
                return true;
                }
                else if(curr.key > e.key) {
                    curr = curr.left;
                }
                else if(curr.key < e.key) {
                    curr = curr.right;
                }
                else {
                    if(curr.address < e.address) {
                        curr = curr.right;
                    }
                    else {
                        curr = curr.left;
                    }
                }
            }   
        }
        return false;
    }
        
    public AVLTree Find(int k, boolean exact)
    { 
        AVLTree curr = this.goTop();
        AVLTree node = null;
        while(curr != null) {
            if(curr.key == k) {
               while(curr.key == k && curr.left != null) {
                   curr = curr.left;
                }
               if(curr.key == k) {
                   return curr;
                }
                else if(curr.key < k) {
                    AVLTree curr2 = curr.parent;
                    int k1 = curr.key;
                    while(curr != null && curr.key == k1 ) {
                        AVLTree curr1 = curr.right;
                        while(curr1 != null) {
                            if(curr1.key == k) {
                                curr2 = curr1;
                                curr1 = curr1.left;
                            }
                            else if(curr1.key > k) {
                                curr1 = curr1.left;
                            }
                            else if(curr1.key < k) {
                                curr1 = curr1.right;
                            }
                        }
                        curr = curr.left;
                    }
                   return curr2;
               }
               
            }
            else if(curr.key < k) {
                curr = curr.right;
            }
            else {
                node = curr;
                curr = curr.left;
            }
        }
        if(!exact && node != null) {
            return node;
        }
        return null;
    }


    public AVLTree getFirst()
    { 
        AVLTree curr = this;
        while(curr.parent != null) {
            curr = curr.parent;
        }
        if(curr.right != null) {
            curr = curr.right;
        }
        while(curr.left != null) {
            curr = curr.left;
        }
        if(curr.parent != null) {
            return curr;
        }
        return null;
    }

    public AVLTree getNext()
    {
        AVLTree curr = this;
        if(curr.right != null) {
            curr = curr.right;
            while (curr.left != null) {
                curr = curr.left;
            }
            return curr;
        }
        else  {
            if(curr.isLeftChild()) {
                return curr.parent;
            }
            else if(curr.isRightChild()) {
                while(curr.parent.parent != null && curr.isRightChild()) {
                    curr = curr.parent;
                }
                if(curr != this.goTop()) {
                    return curr.parent;
                }
            }
        }
        return null;
    }

    public boolean sanity()
    { 
        AVLTree first = this;
        AVLTree second = this;
        boolean atStart = true;
        while(first != null && second != null && second.parent != null) {
            if(first.address == second.size && first.size == second.size && first.key == second.key && !atStart) {
                return false;
            }
            atStart = false;
            first = first.parent;
            second = second.parent.parent;
        }
        
        AVLTree curr = this.goTop();
        if(curr != null) {
            if(curr.parent.parent != null) {
                return false;
            }
            if(curr.parent.left != null) {
                return false;
            }
            if(curr.parent.right != curr) {
                return false;
            }
            if(curr.parent.address != -1 && curr.parent.size != -1 && curr.parent.key != -1) {
                return false;
            }
            if(!curr.checkSanityRecursion()) {
                return false;
            }
        }
        return true;
    }

     //This is a helper function
    private boolean isLeftChild() {
        if(this.parent != null) {
            return this.parent.left == this;
        }
        return false;
    }
    //This is a helper function
    private boolean isRightChild() {
        if(this.parent != null) {
            return this.parent.right == this;
        }
        return false;
    }
    //This is a helper function
    private AVLTree goTop() {
        AVLTree curr = this;
        while(curr.parent != null) {
            curr = curr.parent;
        }
        if(curr.right != null) {
            return curr.right;
        }
        return null;
    }
    //This is a helper function
    private AVLTree add(AVLTree node, int address, int size, int key) {
        if(node.key > key) {
            if(node.left == null) {
                AVLTree newNode = new AVLTree(address,size,key);
                node.left = newNode;
                newNode.parent = node;
                newNode.height = 1;
                return newNode;
            }
            else {
                return add(node.left, address, size, key);
            }

        }
        else if(node.key < key) {
            if(node.right == null) {
                AVLTree newNode = new AVLTree(address,size,key);
                node.right = newNode;
                newNode.parent = node;
                newNode.height = 1;
                return newNode;
            }
            else {
                return add(node.right, address, size, key);
            }
        }
        else {
            if(node.address >= address) {
                if(node.left == null) {
                    AVLTree newNode = new AVLTree(address,size,key);
                    node.left = newNode;
                    newNode.parent = node;
                    newNode.height = 1;
                    return newNode;
                }
                else {
                    return add(node.left, address, size, key);
                }
            }
            else {
                if(node.right == null) {
                    AVLTree newNode = new AVLTree(address,size,key);
                    node.right = newNode;
                    newNode.parent = node;
                    newNode.height = 1;
                    return newNode;
                }
                else {
                    return add(node.right, address, size, key);
                }
            }
        } 
    }
    // This is a helper function
    private void checkBalance(AVLTree node) {
        int rightHeight;
        int leftHeight;
        if(node.left == null) {
            leftHeight = 0;
        }
        else {
            leftHeight = node.left.height;
        }
        if(node.right == null) {
            rightHeight = 0;
        }
        else {
            rightHeight = node.right.height;
        }
        if(leftHeight - rightHeight < -1 || leftHeight - rightHeight > 1) {
            reBalance(node);
        }
        if(node.parent != null){
            node = node.parent;
        }
        if(node.parent != null) {
            checkBalance(node);
        }
    }

    //This is a helper function
    private AVLTree reBalance(AVLTree node) {
        int rightHeight;
        int leftHeight;
        int leftLeftHeight;
        int leftRightHeight;
        int rightRightHeight;
        int rightLeftHeight;
        if(node.left == null) {
            leftHeight = 0;
            leftLeftHeight = 0;
            leftRightHeight = 0;
        }
        else {
            leftHeight = node.left.height;
            if(node.left.left == null) {
                leftLeftHeight = 0;
            }
            else {
                leftLeftHeight = node.left.left.height;
            }
            if(node.left.right == null) {
                leftRightHeight = 0;
            }
            else {
                leftRightHeight = node.left.right.height;
            }
        }
        if(node.right == null) {
            rightHeight = 0;
            rightRightHeight = 0;
            rightLeftHeight = 0;
        }
        else {
            rightHeight = node.right.height;
            if(node.right.right == null) {
                rightRightHeight = 0;
            }
            else {
                rightRightHeight = node.right.right.height;
            }
            if(node.right.left == null) {
                rightLeftHeight = 0;
            }
            else {
                rightLeftHeight = node.right.left.height;
            }
        }
        if(leftHeight - rightHeight > 1) {
            if(leftLeftHeight >= leftRightHeight) {
                return rightRotate(node);
            }
            else {
                return leftRightRotate(node);
            }
        }
        else {
            if(rightRightHeight >= rightLeftHeight) {
                return leftRotate(node);
            }
            else {
                return rightLeftRotate(node);
            }
        }
    }

    //This is helper function
    private AVLTree leftRotate(AVLTree node) {
        AVLTree temp = node.right;
        node.right = temp.left;
        if(temp.left != null) {
            temp.left.parent = node;
        }
        node.right = temp.left;
        temp.parent = node.parent;
        if(node.isLeftChild()) {
            node.parent.left = temp;
        }
        else if(node.isRightChild()) {
            node.parent.right = temp;
        }
        temp.left = node;
        node.parent = temp;
        while(node.parent != null) {
            if(node.left == null && node.right == null) {
                node.height = 1;
            }
            else if(node.left == null && node.right != null) {
                node.height = 1 + node.right.height;
            }
            else if(node.left != null && node.right == null) {
                node.height = 1 + node.left.height;
            }
            else  {
                if(node.right.height < node.left.height) {
                    node.height = 1 + node.left.height;
                }
                else {
                    node.height = 1 + node.right.height;
                }
            }
            node = node.parent;
        }
        return temp;
    }

    private AVLTree rightRotate(AVLTree node) {
        AVLTree temp = node.left;
        node.left = temp.right;
        if(temp.right != null) {
            temp.right.parent = node;
        }
        node.left = temp.right;
        if(node.isLeftChild()) {
            node.parent.left = temp;
        }
        else if(node.isRightChild()) {
            node.parent.right = temp;
        }
        temp.right = node;
        temp.parent = node.parent;
        node.parent = temp;
        while(node.parent != null) {
            if(node.left == null && node.right == null) {
                node.height = 1;
            }
            else if(node.left == null && node.right != null) {
                node.height = 1 + node.right.height;
            }
            else if(node.left != null && node.right == null) {
                node.height = 1 + node.left.height;
            }
            else  {
                if(node.right.height < node.left.height) {
                    node.height = 1 + node.left.height;
                }
                else {
                    node.height = 1 + node.right.height;
                }
            }
            node = node.parent;
        }
        return temp;
    }
    //This is a helper function
    private AVLTree leftRightRotate(AVLTree node) {
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }
    //This is a helper function 
    private AVLTree rightLeftRotate(AVLTree node) {
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }
    //This is a helper function to check sanity
    private boolean checkSanityRecursion() {

        if(this.isLeftChild() && this.isRightChild() || !this.isLeftChild() && !this.isRightChild()) {
            return false;
        }
        if(this.isLeftChild() && this.key > this.parent.key || this.isRightChild() && this.key < this.parent.key) {
            return false;
        }
        
        if(this.left != null){
            if(this.key < this.left.key) {
                return false;
            }
            if(this.left.parent != this) {
                return false;
            }
        }
        if(this.right != null) {
            if(this.key > this.right.key) {
                return false;
            }
            if(this.right.parent != this) {
                return false;
            }
        }
        int leftHeight = 0;
        int rightHeight = 0;
        if(this.left != null) {
            leftHeight = this.left.height;
        }
        if(this.right != null) {
            rightHeight = this.right.height;
        }
        if(leftHeight - rightHeight > 1 || leftHeight - rightHeight < -1) {
            return false;
        }
        if(this.left != null) {
            this.left.checkSanityRecursion();
        }
        if(this.right != null) {
            this.right.checkSanityRecursion();
        }
        return true;
    } 

}



