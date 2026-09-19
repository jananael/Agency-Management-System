package phaseTwo;

public class AVL<T extends Comparable<T>> {
    private class AVLNode<T> {
        T data;
        AVLNode<T> left;
        AVLNode<T> right;
        int height;

        AVLNode(T data) {
            this.data=data;
            height=0;
            left=right=null;
        }
    }

    private AVLNode<T> root;

    // Time Complexity:O(1)
    public boolean isEmpty() {
        return root==null;
    }

    // Time Complexity:O(1)
    public void clear() {
        root=null;
    }

    
    // Time Complexity: O(log n)
    public void insert(T data) {
        root=insertRec(root,data);
    }

    // Time Complexity: O(log n)
    private AVLNode<T> insertRec(AVLNode<T> node,T data) {
        if(node==null)
            return new AVLNode<T>(data);

        int cmp=data.compareTo(node.data);

        if(cmp<0)
            node.left=insertRec(node.left,data);
        else if(cmp>0)
            node.right=insertRec(node.right,data);
        else
            return node;

        updateHeight(node);
        return rebalance(node);
    }

    // Time Complexity:O(1)
    private void updateHeight(AVLNode<T> node) {
        node.height=1+Math.max(height(node.left),height(node.right));
    }

    // Time Complexity:O(1)
    private int height(AVLNode<T> node) {
        return node==null?-1:node.height;
    }

    // Time Complexity:O(1)
    private int getBalanceFactor(AVLNode<T> node) {
        return node==null?0:height(node.left)-height(node.right);
    }

    
    // Time Complexity:O(1)
    private AVLNode<T> rebalance(AVLNode<T> node) {
        int bf=getBalanceFactor(node);

        if(bf>1&&getBalanceFactor(node.left)>=0)
            return rightRotate(node);

        if(bf>1&&getBalanceFactor(node.left)<0) {
            node.left=leftRotate(node.left);
            return rightRotate(node);
        }

        if(bf<-1&&getBalanceFactor(node.right)<=0)
            return leftRotate(node);

        if(bf<-1&&getBalanceFactor(node.right)>0) {
            node.right=rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    
    // Time Complexity:O(1)
    private AVLNode<T> rightRotate(AVLNode<T> node) {
        AVLNode<T> y=node;
        AVLNode<T> x=y.left;

        y.left=x.right;
        x.right=y;

        updateHeight(y);
        updateHeight(x);

        return x;
    }
    

    // Time Complexity:O(1)
    private AVLNode<T> leftRotate(AVLNode<T> node) {
        AVLNode<T> x=node;
        AVLNode<T> y=x.right;

        x.right=y.left;
        y.left=x;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Time Complexity:O(log n)
    public void delete(T data) {
        root=delete(root,data);
    }
   // Time Complexity:O(log n)
    private AVLNode<T> delete(AVLNode<T> node,T data) {
        if(node==null)
            return null;

        int cmp=data.compareTo(node.data);

        if(cmp<0)
            node.left=delete(node.left,data);
        else if(cmp>0)
            node.right=delete(node.right,data);
        else {
            if(node.left==null&&node.right==null)
                return null;
            else if(node.left==null)
                return node.right;
            else if(node.right==null)
                return node.left;
            else {
                AVLNode<T> successor=findMin(node.right);
                node.data=successor.data;
                node.right=delete(node.right,successor.data);
            }
        }

        updateHeight(node);
        return rebalance(node);
    }

    // Time Complexity:O(log n)
    private AVLNode<T> findMin(AVLNode<T> node) {
        while(node.left!=null)
            node=node.left;

        return node;
    }
    
    // Time Complexity:O(log n)
    public boolean contains(T data) {
        return containsRec(root,data);
    }

  // Time Complexity:O(log n)
    private boolean containsRec(AVLNode<T> node,T data) {
        if(node==null)
            return false;

        int cmp=data.compareTo(node.data);

        if(cmp<0)
            return containsRec(node.left,data);
        else if(cmp>0)
            return containsRec(node.right,data);
        else
            return true;
    }

    
    // Time Complexity:O(log n)
    public T find(T data) {
        return findRec(root,data);
    }
    
    // Time Complexity:O(log n)
    private T findRec(AVLNode<T> node,T data) {
        if(node==null)
            return null;

        int cmp=data.compareTo(node.data);

        if(cmp<0)
            return findRec(node.left,data);
        else if(cmp>0)
            return findRec(node.right,data);
        else
            return node.data;
    }

    // Time Complexity O(n)
    public int getSize() {
        return getSizeRec(root);
    }

    // Time Complexity O(n)
    private int getSizeRec(AVLNode<T> node) {
        if(node==null)
            return 0;

        return 1+getSizeRec(node.left)+getSizeRec(node.right);
    }

    // Time Complexity O(n)
    public void inOrder() {
        inOrderRec(root);
    }
    // Time Complexity O(n)
    private void inOrderRec(AVLNode<T> node) {
        if(node!=null) {
            inOrderRec(node.left);
            System.out.println(node.data);
            inOrderRec(node.right);
        }
    }

    // Time Complexity O(n)
    public Queue<T> inOrderQueue() {
        Queue<T> queue=new Queue<T>();
        inOrderQueueRec(root,queue);
        return queue;
    }

    // Time Complexity O(n)
    private void inOrderQueueRec(AVLNode<T> node,Queue<T> queue) {
        if(node!=null) {
            inOrderQueueRec(node.left,queue);
            queue.enqueue(node.data);
            inOrderQueueRec(node.right,queue);
        }
    }

    // Time Complexity O(n)
    public Queue<T> reverseInOrderQueue() {
        Queue<T> queue=new Queue<T>();
        reverseInOrderQueueRec(root,queue);
        return queue;
    }

    // Time Complexity O(n)
    private void reverseInOrderQueueRec(AVLNode<T> node,Queue<T> queue) {
        if(node!=null) {
            reverseInOrderQueueRec(node.right,queue);
            queue.enqueue(node.data);
            reverseInOrderQueueRec(node.left,queue);
        }
    }

    // Time Complexity O(n)
    public boolean isAVL() {
        return checkAVL(root)!=-1;
    }
    // Time Complexity O(n)
    private int checkAVL(AVLNode<T> node) {
        if(node==null)
            return 0;

        int left=checkAVL(node.left);

        if(left==-1)
            return -1;

        int right=checkAVL(node.right);

        if(right==-1)
            return -1;

        if(Math.abs(left-right)>1)
            return -1;

        return 1+Math.max(left,right);
    }

    // Time Complexity O(1)
    public int getHeight() {
        return height(root);
    }
}