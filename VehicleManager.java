package phaseTwo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VehicleManager {
    private AVL<Vehicle> vehicles=new AVL<Vehicle>();

    // Time Complexity: O(n)
    public int getSize() {
        return vehicles.getSize();
    }

 // Time Complexity: O(1)
    public boolean isEmpty() {
        return vehicles.isEmpty();
    }

 // Time Complexity: O(log n)
    public boolean addVehicle(Vehicle data) {
        if(data==null)
            return false;
        if(vehicles.contains(data))
            return false;

        vehicles.insert(data);
        return true;
    }

 // Time Complexity: O(log n)
    public Vehicle searchVehicle(int vehicleId) {
        if(vehicleId<=0)
            return null;

        return vehicles.find(new Vehicle(vehicleId));
    }

    // Time Complexity: O(log n)
    public boolean updateVehicle(int vehicleId,String make,String model,String year,String price,String color){
        Vehicle vehicle=searchVehicle(vehicleId);
        if(vehicle==null)
            return false;
        String newMake=vehicle.getMake();
        String newModel=vehicle.getModel();
        int newYear=vehicle.getYear();
        double newPrice=vehicle.getPrice();
        String newColor=vehicle.getColor();
        try{
            if(make!=null&&!make.trim().isEmpty())
                newMake=make;
            if(model!=null&&!model.trim().isEmpty())
                newModel=model;
            if(year!=null&&!year.trim().isEmpty())
                newYear=Integer.parseInt(year);
            if(price!=null&&!price.trim().isEmpty())
                newPrice=Double.parseDouble(price);
            if(color!=null&&!color.trim().isEmpty())
                newColor=color;
            Vehicle updatedVehicle=new Vehicle(vehicleId,newMake,newModel,newYear,newPrice,newColor,vehicle.getStatus());
            vehicle.setMake(updatedVehicle.getMake());
            vehicle.setModel(updatedVehicle.getModel());
            vehicle.setYear(updatedVehicle.getYear());
            vehicle.setPrice(updatedVehicle.getPrice());
            vehicle.setColor(updatedVehicle.getColor());
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    

    // Time Complexity: O(n)
    public Vehicle removeVehicle(int vehicleId,MaintenanceManager maintenance,TransactionManager transactions) {
        Vehicle vehicle=searchVehicle(vehicleId);

        if(vehicle==null)
            return null;
        if(!vehicle.getStatus().equals("AVAILABLE"))
            return null;
        if(!vehicle.getReservationQueue().isEmpty())
            return null;
        if(maintenance.hasVehicle(vehicleId))
            return null;
        if(transactions.hasVehicle(vehicleId))
            return null;

        vehicles.delete(vehicle);
        return vehicle;
    }
    

 // Time Complexity: O(n)
    public ObservableList<Vehicle> displayAllVehicles() {
        ObservableList<Vehicle> data=FXCollections.observableArrayList();
        Queue<Vehicle> queue=vehicles.inOrderQueue();

        while(!queue.isEmpty())
            data.add(queue.dequeue());

        return data;
    }

 // Time Complexity: O(n)
    public ObservableList<Vehicle> displayAvailableVehicles() {
        ObservableList<Vehicle> data=FXCollections.observableArrayList();
        Queue<Vehicle> queue=vehicles.inOrderQueue();

        while(!queue.isEmpty()) {
            Vehicle vehicle=queue.dequeue();

            if(vehicle.getStatus().equalsIgnoreCase("AVAILABLE"))
                data.add(vehicle);
        }

        return data;
    }

 // Time Complexity: O(n)
    public ObservableList<Vehicle> searchVehicleMake(String make) {
        ObservableList<Vehicle> data=FXCollections.observableArrayList();

        if(make==null)
            return data;

        Queue<Vehicle> queue=vehicles.inOrderQueue();

        while(!queue.isEmpty()) {
            Vehicle vehicle=queue.dequeue();

            if(vehicle.getMake().equalsIgnoreCase(make))
                data.add(vehicle);
        }

        return data;
    }

 // Time Complexity: O(n)
    public boolean isAVL() {
        return vehicles.isAVL();
    }

 // Time Complexity: O(n)
    public ObservableList<Vehicle> displayVehiclesDescending() {
        ObservableList<Vehicle> data=FXCollections.observableArrayList();
        Queue<Vehicle> queue=vehicles.reverseInOrderQueue();

        while(!queue.isEmpty())
            data.add(queue.dequeue());

        return data;
    }
    
 // Time Complexity: O(log n)
    public boolean changeVehicleStatus(int vehicleId,String status){
        Vehicle vehicle=searchVehicle(vehicleId);
        if(vehicle==null)
            return false;
        try{
            vehicle.setStatus(status);
            if(!vehicle.getStatus().equals("RESERVED"))
                vehicle.setReservedCustomerId(0);
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    
 // Time Complexity: O( log n)
    public boolean markAsSold(int vehicleId){
        Vehicle vehicle=searchVehicle(vehicleId);

        if(vehicle==null)
            return false;
        if(!vehicle.getStatus().equals("AVAILABLE"))
            return false;
        if(!vehicle.getReservationQueue().isEmpty())
            return false;

        return changeVehicleStatus(vehicleId,"SOLD");
    }
    
 // Time Complexity: O( log n)
    public boolean markAsInService(int vehicleId) {
        return changeVehicleStatus(vehicleId,"IN_SERVICE");
    }

 // Time Complexity: O( log n)
    public boolean markAsAvailable(int vehicleId) {
        return changeVehicleStatus(vehicleId,"AVAILABLE");
    }

 // Time Complexity: O(1)
    public int getTreeHeight() {
        return vehicles.getHeight();
    }
    
 // Time Complexity: O(n log n)
    public void readVehicles(String fileName)throws Exception {
        java.io.File file=new java.io.File(fileName);

        try(java.util.Scanner input=new java.util.Scanner(file)) {
            while(input.hasNextLine()) {
                String line=input.nextLine();
                java.util.Scanner lineScanner=new java.util.Scanner(line);
                lineScanner.useDelimiter(",");

                int vehicleId=lineScanner.nextInt();
                String make=lineScanner.next();
                String model=lineScanner.next();
                int year=lineScanner.nextInt();
                double price=lineScanner.nextDouble();
                String color=lineScanner.next();
                String status=lineScanner.next();

                Vehicle vehicle=new Vehicle(vehicleId,make,model,year,price,color,status);
                addVehicle(vehicle);
                lineScanner.close();
            }
        }
    }

 // Time Complexity: O(n)
    public void writeVehicles(String fileName)throws Exception {
        java.io.File file=new java.io.File(fileName);
        Queue<Vehicle> queue=vehicles.inOrderQueue();

        try(java.io.PrintWriter output=new java.io.PrintWriter(file)) {
            while(!queue.isEmpty())
                output.println(queue.dequeue());
        }
    }
    
 // Time Complexity: O(n)
    public boolean hasCustomerReservation(int customerId){
        boolean found=false;
        Queue<Vehicle> vehicleQueue=vehicles.inOrderQueue();
        while(!vehicleQueue.isEmpty()){
            Vehicle vehicle=vehicleQueue.dequeue();
            if(vehicle.getReservedCustomerId()==customerId)
                found=true;
            Queue<Customer> reservationQueue=vehicle.getReservationQueue();
            int size=reservationQueue.size();
            for(int i=0;i<size;i++){
                Customer customer=reservationQueue.dequeue();
                if(customer.getCustomerId()==customerId)
                    found=true;
                reservationQueue.enqueue(customer);
            }
        }
        return found;
    }
    
    }