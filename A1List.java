// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        A1List curr = new A1List(address, size, key);
        try {
            A1List curr1 = this;
            while(curr1.prev != null) {
                curr1 = curr.prev;
            }
            curr.next = curr1.next;
            curr1.next = curr;
            curr.prev = curr1;
            curr.next.prev = curr;
            return curr;
        } catch (NullPointerException e) {
            //TODO: handle exception
            return null;
        }
        
    }

    public boolean Delete(Dictionary d) 
    {

        if(this.prev != null) {
            if(this.prev.prev != null) {
                A1List curr = this.prev;
                while(curr.prev != null) {
                    if(curr.address == d.address && curr.size == d.size && curr.key == d.key) {
                        A1List previous = curr.prev;
                        previous.next = curr.next;
                        curr.next.prev = previous;
                        return true;
                    }
                    curr = curr.prev;
                }
            }
        }

        if(this.next != null) {
            if(this.next.next != null) {
                A1List curr1 = this.next;
                while(curr1.next != null) {
                    if(curr1.address == d.address && curr1.size == d.size && curr1.key == d.key) {
                        A1List previous = curr1.prev;
                        previous.next = curr1.next;
                        curr1.next.prev = previous;
                        return true;
                    }
                    curr1 = curr1.next;
                }
            }
        }

        if(this.prev != null && this.next != null) {
            if(this.address == d.address && this.size == d.size && this.key == d.key) {
                A1List curr2 = this;
                A1List previous = this.prev;
                previous.next = curr2.next;
                curr2.next.prev = previous;
                return true;
            }
        }
        
        return false;
    }

    public A1List Find(int k, boolean exact)
    {   
        A1List curr = this.getFirst();
        if(curr != null) {
            while(curr.next != null) {
                if(exact && curr.key == k || !exact && curr.key >= k) {
                    return curr;
                }
                curr = curr.next;
            }
        }

        return null;
    }

    public A1List getFirst()
    {   
        A1List curr = this;
        try {
            if(curr.prev == null) {
                curr = curr.next;
            }
            else {
                while(curr.prev.prev != null) {
                    curr = curr.prev;
                }
            }
        } catch (NullPointerException e) {          
            //TODO: handle exception
            return null;
        }
        if(curr.next != null) {
            return curr;
        }
        return null;
        
    }
    
    public A1List getNext() 
    {   
        try {
            if(this.next.next == null) {
                return null;
            }
            else {
                return this.next;
            }
        } catch (NullPointerException e) {
            //TODO: handle exception
            return null;
        }
    }

    

    public boolean sanity()
    {   

        A1List first = this;
        A1List second = this;
        boolean atStart = true;
        while(first != null && second != null && second.next != null) {
            if(first.address == second.address && first.size == second.size && first.key == second.key && !atStart) {
                return false;
            }
            atStart = false;
            first = first.next;
            second = second.next.next;
        }

        A1List first1 = this;
        A1List second1 = this;
        boolean atStart1 = true;
        while(first1 != null && second1 != null && second1.prev != null) {
            if(first1.address == second1.address && first1.size == second1.size && first1.key == second1.key && !atStart1) {
                return false;
            }
            atStart1 = false;
            first1 = first1.prev;
            second1 = second1.prev.prev;
        }

        if(this.prev != null) {
            if(this.prev.next != this) {
                return false;
            }
            if(this.prev.prev != null) {
                A1List curr1 = this.prev;
                while(curr1.prev != null) {
                    if(curr1.prev.next != curr1) {
                        return false;
                    }
                    if(curr1.next.prev != curr1) {
                        return false;
                    }
                    curr1 = curr1.prev;
                }
                if(curr1.address != -1 || curr1.size != -1 || curr1.key != -1) {
                    return false;
                }
            }
        }
        else {
            if(this.address != -1 || this.size != -1 || this.key != -1) {
                return false;
            }
            if(this.next == null) {
                return false;
            }

        }

        if(this.next != null) {
            if(this.next.prev != this) {
                return false;
            }
            if(this.next.next != null) {
                A1List curr2 = this.next;
                while(curr2.next != null) {
                    if(curr2.prev.next != curr2) {
                        return false;
                    }
                    if(curr2.next.prev != curr2) {
                        return false;
                    }
                    curr2 = curr2.next;
                }
                if(curr2.address != -1 || curr2.size != -1 || curr2.key != -1) {
                    return false;
                }
            }
        }
        else {
            if(this.address != -1 || this.size != -1 || this.key != -1) {
                return false;
            }
        }
        return true;
    }
}
