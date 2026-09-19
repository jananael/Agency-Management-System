package phaseTwo;

public class Queue<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data=data;
            next=null;
        }
    }

    private Node<T> front;
    private Node<T> rear;
    private int size;

    public Queue() {
        front=null;
        rear=null;
        size=0;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public int size() {
        return size;
    }

    public T peek() {
        if(isEmpty())
            return null;

        return front.data;
    }

    // Time Complexity:O(1)
    public boolean enqueue(T data) {
        Node<T> newNode=new Node<T>(data);

        if(isEmpty())
            front=rear=newNode;
        else {
            rear.next=newNode;
            rear=newNode;
        }

        size++;
        return true;
    }
    

    // Time Complexity:O(1)

    public T dequeue() {
        if(isEmpty())
            return null;

        T value=front.data;
        front=front.next;
        size--;

        if(isEmpty())
            rear=null;

        return value;
    }

    
    // Time Complexity:O(1)
    public void clear() {
        front=rear=null;
        size=0;
    }
    
    
    // Time Complexity:O(n)
    
    public void printQueue(Queue<T> q1) {
        Queue<T> q2=new Queue<T>();

        while(!q1.isEmpty()) {
            System.out.println(q1.peek());
            q2.enqueue(q1.dequeue());
        }

        while(!q2.isEmpty())
            q1.enqueue(q2.dequeue());
    }
}