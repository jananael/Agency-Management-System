package phaseTwo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerManager {
    private Queue<Customer> customers=new Queue<Customer>();

    
 // Time Complexity: O(1)
    public int getSize() {
        return customers.size();
    }
 // Time Complexity: O(1)
    public boolean isEmpty() {
        return customers.isEmpty();
    }
 // Time Complexity:O(n)
    public boolean addCustomer(Customer data) {
        if(data==null)
            return false;
        if(searchCustomer(data.getCustomerId())!=null)
            return false;

        customers.enqueue(data);
        return true;
    }

    // Time Complexity:O(n)
    public Customer searchCustomer(int customerId) {
        Customer found=null;
        int size=customers.size();

        for(int i=0;i<size;i++) {
            Customer customer=customers.dequeue();

            if(customer.getCustomerId()==customerId)
                found=customer;

            customers.enqueue(customer);
        }

        return found;
    }

    // Time Complexity:O(n)
    public boolean updateCustomer(int customerId,String name,String phone,String address){
        Customer customer=searchCustomer(customerId);
        if(customer==null)
            return false;
        String newName=customer.getName();
        String newPhone=customer.getPhone();
        String newAddress=customer.getAddress();
        if(name!=null&&!name.trim().isEmpty())
            newName=name;
        if(phone!=null&&!phone.trim().isEmpty())
            newPhone=phone;
        if(address!=null&&!address.trim().isEmpty())
            newAddress=address;
        try{
            Customer updated=new Customer(customerId,newName,newPhone,newAddress);
            customer.setName(updated.getName());
            customer.setPhone(updated.getPhone());
            customer.setAddress(updated.getAddress());
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    
    
    // Time Complexity:O(n)
    public Customer removeCustomer(int customerId,CustomerWaitingManager waiting,MaintenanceManager maintenance,TransactionManager transactions,VehicleManager vehicles) {
        Customer customer=searchCustomer(customerId);

        if(customer==null)
            return null;
        if(waiting.hasCustomer(customerId))
            return null;
        if(maintenance.hasCustomer(customerId))
            return null;
        if(transactions.hasCustomer(customerId))
            return null;
        if(vehicles.hasCustomerReservation(customerId))
            return null;

        Customer removed=null;
        int size=customers.size();

        for(int i=0;i<size;i++) {
            Customer current=customers.dequeue();

            if(current.getCustomerId()==customerId)
                removed=current;
            else
                customers.enqueue(current);
        }

        return removed;
    }
    
    // Time Complexity:O(n)
    public ObservableList<Customer> displayAllCustomers() {
        ObservableList<Customer> data=FXCollections.observableArrayList();
        int size=customers.size();

        for(int i=0;i<size;i++) {
            Customer customer=customers.dequeue();
            data.add(customer);
            customers.enqueue(customer);
        }

        return data;
    }

    // Time Complexity:O(n)
    public ObservableList<Customer> searchCustomerName(String name){
        ObservableList<Customer> data=FXCollections.observableArrayList();
        if(name==null||name.trim().isEmpty())
            return data;
        int size=customers.size();
        for(int i=0;i<size;i++){
            Customer customer=customers.dequeue();
            if(customer.getName().toLowerCase().contains(name.trim().toLowerCase()))
                data.add(customer);
            customers.enqueue(customer);
        }
        return data;
    }
    
    // Time Complexity:O(n^2)
    public void readCustomers(String fileName)throws Exception{
        java.io.File file=new java.io.File(fileName);
        try(java.util.Scanner input=new java.util.Scanner(file)){
            while(input.hasNextLine()){
                String line=input.nextLine();
                java.util.Scanner lineScanner=new java.util.Scanner(line);
                lineScanner.useDelimiter(",");
                int customerId=Integer.parseInt(lineScanner.next().trim());
                String name=lineScanner.next().trim();
                String phone=lineScanner.next().trim();
                String address=lineScanner.next().trim();

                while(lineScanner.hasNext()){
                    address=address+", "+lineScanner.next().trim();
                }
                Customer customer=new Customer(customerId,name,phone,address);
                addCustomer(customer);
                lineScanner.close();
            }
        }
    }
    

    // Time Complexity:O(n)
    public void writeCustomers(String fileName)throws Exception {
        java.io.File file=new java.io.File(fileName);

        try(java.io.PrintWriter output=new java.io.PrintWriter(file)) {
            int size=customers.size();

            for(int i=0;i<size;i++) {
                Customer customer=customers.dequeue();
                output.println(customer);
                customers.enqueue(customer);
            }
        }
    }
    
    // Time Complexity:O(n)
    public int generateCustomerId(){
        int maxId=0;
        int size=customers.size();
        for(int i=0;i<size;i++){
            Customer customer=customers.dequeue();
            if(customer.getCustomerId()>maxId)
                maxId=customer.getCustomerId();
            customers.enqueue(customer);
        }
        return maxId+1;
    }
    
    
    
}