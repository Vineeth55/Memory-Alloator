// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right; // Children.
    private BSTree parent; // Parent pointer.

    public BSTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root!
        // and left child will always be null.
    }

    public BSTree(int address, int size, int key) {
        super(address, size, key);
    }


    public BSTree Insert(int address, int size, int key) {
        BSTree curr = this.goTop();
        BSTree Parent = null;
        boolean isLeftChild = false;
        if (curr == null) {
            BSTree newNode = new BSTree(address, size, key);
            this.right = newNode;
            newNode.parent = this;
            return newNode;
        }
        while (curr != null) {
            if (curr.key > key) {
                Parent = curr;
                curr = curr.left;
                isLeftChild = true;
            }else if(curr.key < key) {
                Parent = curr;
                curr = curr.right;
                isLeftChild = false;
            }
            else {
                if(curr.address < address) {
                    Parent = curr;
                    curr = curr.right;
                    isLeftChild = false;
                }
                else {
                    Parent = curr;
                    curr = curr.left;
                    isLeftChild = true;
                
                }
            }
        }
        curr = new BSTree(address, size, key);
        curr.parent = Parent;
        if (isLeftChild) {
            curr.parent.left = curr;
        } else {
            curr.parent.right = curr;
        }
        return curr;

        // return null;
    }

    public boolean Delete(Dictionary e) {
        BSTree curr = this.goTop();
        if (curr != null) {
            while (curr != null) {
                if (curr.address == e.address && curr.size == e.size && curr.key == e.key) {
                    if (curr.left == null && curr.right == null) {
                        if (curr.isLeftChild()) {
                            curr.parent.left = null;
                            curr.parent = null;
                        } else {
                            curr.parent.right = null;
                            curr.parent = null;
                        }
                    } else if (curr.left == null) {
                        if (curr.isLeftChild()) {
                            curr.right.parent = curr.parent;
                            curr.parent.left = curr.right;
                            curr.right = null;
                            curr.parent = null;
                        } else {
                            curr.right.parent = curr.parent;
                            curr.parent.right = curr.right;
                            curr.right = null;
                            curr.parent = null;
                        }
                    } else if (curr.right == null) {
                        if (curr.isLeftChild()) {
                            curr.left.parent = curr.parent;
                            curr.parent.left = curr.left;
                            curr.left = null;
                            curr.parent = null;
                        } else {
                            curr.left.parent = curr.parent;
                            curr.parent.right = curr.left;
                            curr.right = null;
                            curr.parent = null;
                        }
                    } else {
                        BSTree curr1 = curr.getNext();
                        if(curr.right != curr1) {
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
                        curr.left = null;
                        curr.right = null;
                        curr.parent = null;
                    }
                    return true;
                }

                else if (curr.key > e.key) {
                    curr = curr.left;
                }
                else if(curr.key < e.key) {
                    curr = curr.right;
                }
                else {
                    if(curr.address < e.address) {
                        curr = curr.right;
                    }
                    else if(curr.address > e.address) {
                        curr = curr.left;
                    }
                }
            }
        }
        return false;
    }

    public BSTree Find(int key, boolean exact) {
        BSTree curr = this.goTop();
        BSTree node = null;
        while(curr != null) {
            if(curr.key == key) {
               while(curr.key == key && curr.left != null) {
                   curr = curr.left;
                }
               if(curr.key == key) {
                   return curr;
                }
                else if(curr.key < key) {
                    BSTree curr2 = curr.parent;
                    int k1 = curr.key;
                    while(curr != null && curr.key == k1 ) {
                        BSTree curr1 = curr.right;
                        while(curr1 != null) {
                            if(curr1.key == key) {
                                curr2 = curr1;
                                curr1 = curr1.left;
                            }
                            else if(curr1.key > key) {
                                curr1 = curr1.left;
                            }
                            else if(curr1.key < key) {
                                curr1 = curr1.right;
                            }
                        }
                        curr = curr.left;
                    }
                   return curr2;
               }
               
            }
            else if(curr.key < key) {
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

    public BSTree getFirst() {
        BSTree curr = this;
        while (curr.parent != null) {
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

    public BSTree getNext() {
        BSTree curr = this;
        if (curr.right != null) {
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
    

    public boolean sanity() {

        BSTree first = this;
        BSTree second = this;
        boolean atStart = true;
        while(first != null && second != null && second.parent != null) {
            if(first.address == second.address && first.size == second.size && first.key == second.key && !atStart) {
                return false;
            }
            atStart = false;
            first = first.parent;
            second = second.parent.parent;
        }

        BSTree curr = this.goTop();
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
    private BSTree goTop() {
        BSTree curr = this;
        while (curr.parent != null) {
            curr = curr.parent;
        }
        if (curr.right != null) {
            return curr.right;
        }
        return null;
    }
    // This is a helper function
    private boolean isLeftChild() {
        if(this.parent != null) {
            return (this.parent.left == this);
        }
        return false;
    }
    // This is a helper function
    private boolean isRightChild() {
        if(this.parent != null) {
            return (this.parent.right == this);
        }
        return false;
    }
    //This is helper function to check sanity.
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
        if(this.left != null) {
            this.left.checkSanityRecursion();
        }
        if(this.right != null) {
            this.right.checkSanityRecursion();
        }
        return true;
    }

}
