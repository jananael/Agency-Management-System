package phaseTwo;

import java.util.NoSuchElementException;

public class Stack<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data=data;
        }
    }

    private Node<T> top;
    private int size;

    public Stack() {
        top=null;
        size=0;
    }

    // Time Complexity: O(1)
    public int size() {
        return size;
    }
    // Time Complexity: O(1)
    public boolean isEmpty() {
        return size==0;
    }
    // Time Complexity: O(1)
    public void clear() {
        top=null;
        size=0;
    }
    // Time Complexity: O(1)
    public void push(T data) {
        Node<T> newNode=new Node<T>(data);
        newNode.next=top;
        top=newNode;
        size++;
    }
    
    // Time Complexity: O(1)
    public T pop() {
        if(isEmpty())
            throw new NoSuchElementException("Stack is empty");

        T value=top.data;
        Node<T> temp=top;
        top=top.next;
        temp.next=null;
        size--;

        return value;
    }

    // Time Complexity: O(1)
    public T peek() {
        if(isEmpty())
            throw new NoSuchElementException("Stack is empty");

        return top.data;
    }

    // Time Complexity: O(n)
    @Override
    public String toString() {
        StringBuilder str=new StringBuilder();
        Node<T> current=top;

        str.append("[");

        while(current!=null) {
            str.append(current.data);

            if(current.next!=null)
                str.append(",");

            current=current.next;
        }

        str.append("]");
        return str.toString();
    }
}