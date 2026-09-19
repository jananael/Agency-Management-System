package phaseTwo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerWaitingManager {
    private Queue<Customer> waitingQueue=new Queue<Customer>();
    // Time Complexity: O(1)
    public int getSize() {
        return waitingQueue.size();
    }

 // Time Complexity: O(n)
    public boolean addCustomer(int customerId,CustomerManager customers) {
        Customer customer=customers.searchCustomer(customerId);

        if(customer==null)
            return false;

        if(hasCustomer(customerId))
            return false;

        waitingQueue.enqueue(customer);
        return true;
    }

    
    // Time Complexity: O(1)
    public Customer serveNextCustomer() {
        if(waitingQueue.isEmpty())
            return null;

        return waitingQueue.dequeue();
    }

    // Time Complexity: O(1)
    public Customer getNextCustomer() {
        if(waitingQueue.isEmpty())
            return null;

        return waitingQueue.peek();
    }
    
     // Time Complexity: O(n)
    public ObservableList<Customer> displayWaitingQueue() {
        ObservableList<Customer> data=FXCollections.observableArrayList();
        int size=waitingQueue.size();

        for(int i=0;i<size;i++) {
            Customer customer=waitingQueue.dequeue();
            data.add(customer);
            waitingQueue.enqueue(customer);
        }

        return data;
    }

 // Time Complexity: O(n)
    public boolean hasCustomer(int customerId) {
        boolean found=false;
        int size=waitingQueue.size();

        for(int i=0;i<size;i++) {
            Customer customer=waitingQueue.dequeue();

            if(customer.getCustomerId()==customerId)
                found=true;

            waitingQueue.enqueue(customer);
        }

        return found;
    }
    
 // Time Complexity: O(n)
    public Customer removeWaitingCustomer(int customerId){
        Customer removed=null;
        int size=waitingQueue.size();
        for(int i=0;i<size;i++){
            Customer customer=waitingQueue.dequeue();
            if(customer.getCustomerId()==customerId)
                removed=customer;
            else
                waitingQueue.enqueue(customer);
        }
        return removed;
    }
    
 // Time Complexity: O(n)
    public boolean returnCustomerToFront(Customer customer){
        if(customer==null)
            return false;
        if(hasCustomer(customer.getCustomerId()))
            return false;
        Queue<Customer> newQueue=new Queue<Customer>();
        newQueue.enqueue(customer);
        while(!waitingQueue.isEmpty())
            newQueue.enqueue(waitingQueue.dequeue());
        waitingQueue=newQueue;
        return true;
    }
    
}