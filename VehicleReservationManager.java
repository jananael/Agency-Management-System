package phaseTwo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VehicleReservationManager{
	
	 
	
	  // Time Complexity: O(n)
	public boolean requestVehicle(int customerId,int vehicleId,CustomerManager customers,VehicleManager vehicles){
	    Customer customer=customers.searchCustomer(customerId);
	    if(customer==null)
	        return false;

	    Vehicle vehicle=vehicles.searchVehicle(vehicleId);
	    if(vehicle==null)
	        return false;

	    if(!vehicle.getStatus().equalsIgnoreCase("AVAILABLE"))
	        return false;

	    if(vehicle.getReservedCustomerId()==customerId)
	        return false;

	    if(hasCustomer(vehicle,customerId))
	        return false;

	    vehicle.getReservationQueue().enqueue(customer);
	    return true;
	}

	  // Time Complexity: O(log n)
    public Customer processNextReservation(int vehicleId,VehicleManager vehicles){
        Vehicle vehicle=vehicles.searchVehicle(vehicleId);

        if(vehicle==null)
            return null;

        if(!vehicle.getStatus().equalsIgnoreCase("AVAILABLE"))
            return null;

        if(vehicle.getReservationQueue().isEmpty())
            return null;

        Customer customer=vehicle.getReservationQueue().dequeue();

        vehicle.setReservedCustomerId(customer.getCustomerId());
        vehicles.changeVehicleStatus(vehicleId,"RESERVED");

        return customer;
    }

    // Time Complexity: O(n)
    public ObservableList<Customer> displayReservations(int vehicleId,VehicleManager vehicles){
        ObservableList<Customer> data=FXCollections.observableArrayList();
        Vehicle vehicle=vehicles.searchVehicle(vehicleId);

        if(vehicle==null)
            return data;

        Queue<Customer> queue=vehicle.getReservationQueue();
        int size=queue.size();

        for(int i=0;i<size;i++){
            Customer customer=queue.dequeue();
            data.add(customer);
            queue.enqueue(customer);
        }

        return data;
    }
    
    

    // Time Complexity: O(log n)
    public int getReservationCount(int vehicleId,VehicleManager vehicles){
        Vehicle vehicle=vehicles.searchVehicle(vehicleId);

        if(vehicle==null)
            return 0;

        return vehicle.getReservationQueue().size();
    }

    // Time Complexity: O(n)
    private boolean hasCustomer(Vehicle vehicle,int customerId){
        Queue<Customer> queue=vehicle.getReservationQueue();
        boolean found=false;
        int size=queue.size();

        for(int i=0;i<size;i++){
            Customer customer=queue.dequeue();

            if(customer.getCustomerId()==customerId)
                found=true;

            queue.enqueue(customer);
        }

        return found;
    }

    // Time Complexity: O(log n)
    public boolean hasVehicle(int vehicleId,VehicleManager vehicles){
        Vehicle vehicle=vehicles.searchVehicle(vehicleId);

        if(vehicle==null)
            return false;

        return !vehicle.getReservationQueue().isEmpty()||vehicle.getReservedCustomerId()!=0;
    }
    
    // Time Complexity: O(n)
    public Customer removeReservation(int customerId,int vehicleId,VehicleManager vehicles){
        Vehicle vehicle=vehicles.searchVehicle(vehicleId);
        if(vehicle==null)
            return null;
        Customer removed=null;
        Queue<Customer> queue=vehicle.getReservationQueue();
        int size=queue.size();
        for(int i=0;i<size;i++){
            Customer customer=queue.dequeue();
            if(customer.getCustomerId()==customerId)
                removed=customer;
            else
                queue.enqueue(customer);
        }
        return removed;
    }
    
    // Time Complexity: O(n)
    public boolean returnReservationToFront(Customer customer,int vehicleId,VehicleManager vehicles){
        if(customer==null)
            return false;
        Vehicle vehicle=vehicles.searchVehicle(vehicleId);
        if(vehicle==null)
            return false;
        if(hasCustomer(vehicle,customer.getCustomerId()))
            return false;
        Queue<Customer> queue=vehicle.getReservationQueue();
        Queue<Customer> temp=new Queue<Customer>();
        while(!queue.isEmpty())
            temp.enqueue(queue.dequeue());
        queue.enqueue(customer);
        while(!temp.isEmpty())
            queue.enqueue(temp.dequeue());
        return true;
    }
}