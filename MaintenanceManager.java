package phaseTwo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MaintenanceManager {
    private Queue<ServiceRequest> maintenanceQueue=new Queue<ServiceRequest>();
    private Queue<ServiceRequest> allRequests=new Queue<ServiceRequest>();
    private Queue<ServiceRequest> processingRequests=new Queue<ServiceRequest>();
    private Queue<Service> completedServices=new Queue<Service>();
    private Stack<Service> serviceHistory=new Stack<Service>();

    // Time Complexity: O(1)
    public int getPendingSize() {
        return maintenanceQueue.size();
    }

    // Time Complexity:O(n)
    public boolean createServiceRequest(ServiceRequest request,CustomerManager customers,VehicleManager vehicles){
        if(request==null)
            return false;
        if(customers.searchCustomer(request.getCustomerId())==null)
            return false;
        Vehicle vehicle=vehicles.searchVehicle(request.getVehicleId());
        if(vehicle==null)
            return false;
        if(!vehicle.getStatus().equalsIgnoreCase("AVAILABLE"))
            return false;
        if(searchRequest(request.getRequestId())!=null)
            return false;
        request.setStatus("PENDING");
        maintenanceQueue.enqueue(request);
        allRequests.enqueue(request);
        return true;
    }
    
    
    // Time Complexity:O(n)
    public ServiceRequest searchRequest(int requestId) {
        ServiceRequest found=null;
        int size=allRequests.size();

        for(int i=0;i<size;i++) {
            ServiceRequest request=allRequests.dequeue();

            if(request.getRequestId()==requestId)
                found=request;

            allRequests.enqueue(request);
        }

        return found;
    }
    // Time Complexity:O(log n)
    public ServiceRequest processNextRequest(VehicleManager vehicles) {
        if(maintenanceQueue.isEmpty())
            return null;

        ServiceRequest request=maintenanceQueue.peek();
        Vehicle vehicle=vehicles.searchVehicle(request.getVehicleId());

        if(vehicle==null)
            return null;
        if(!vehicle.getStatus().equalsIgnoreCase("AVAILABLE"))
            return null;

        request=maintenanceQueue.dequeue();
        request.setStatus("IN_PROGRESS");
        processingRequests.enqueue(request);
        vehicles.markAsInService(request.getVehicleId());

        return request;
    }
    
    // Time Complexity:O(n)
    public Service searchService(int serviceId) {
        Service found=null;
        int size=completedServices.size();

        for(int i=0;i<size;i++) {
            Service service=completedServices.dequeue();

            if(service.getServiceId()==serviceId)
                found=service;

            completedServices.enqueue(service);
        }

        return found;
    }

    // Time Complexity:O(n)
    private ServiceRequest searchProcessingRequest(int requestId) {
        ServiceRequest found=null;
        int size=processingRequests.size();

        for(int i=0;i<size;i++) {
            ServiceRequest request=processingRequests.dequeue();

            if(request.getRequestId()==requestId)
                found=request;

            processingRequests.enqueue(request);
        }

        return found;
    }

    // Time Complexity:O(n)
    private void removeProcessingRequest(int requestId) {
        int size=processingRequests.size();

        for(int i=0;i<size;i++) {
            ServiceRequest request=processingRequests.dequeue();

            if(request.getRequestId()!=requestId)
                processingRequests.enqueue(request);
        }
    }

    // Time Complexity:O(n)
    public Service completeService(int requestId,int serviceId,String date,double cost,VehicleManager vehicles) {
        if(searchService(serviceId)!=null)
            return null;

        ServiceRequest request=searchProcessingRequest(requestId);

        if(request==null)
            return null;

        Vehicle vehicle=vehicles.searchVehicle(request.getVehicleId());

        if(vehicle==null)
            return null;
        if(!vehicle.getStatus().equalsIgnoreCase("IN_SERVICE"))
            return null;

        try {
            Service service=new Service(serviceId,request.getVehicleId(),request.getCustomerId(), request.getServiceType(), date, cost,"COMPLETED" );

            removeProcessingRequest(requestId);
            request.setStatus("COMPLETED");
            completedServices.enqueue(service);
            serviceHistory.push(service);
            vehicle.getServiceHistory().push(service);
            vehicles.markAsAvailable(request.getVehicleId());

            return service;
        }
        catch(Exception ex) {
            return null;
        }
    }

    // Time Complexity:O(n)
    public ObservableList<ServiceRequest> displayPendingRequests() {
        ObservableList<ServiceRequest> data=FXCollections.observableArrayList();
        int size=maintenanceQueue.size();

        for(int i=0;i<size;i++) {
            ServiceRequest request=maintenanceQueue.dequeue();
            data.add(request);
            maintenanceQueue.enqueue(request);
        }

        return data;
    }

    // Time Complexity:O(n)
    public ObservableList<ServiceRequest> displayProcessingRequests() {
        ObservableList<ServiceRequest> data=FXCollections.observableArrayList();
        int size=processingRequests.size();

        for(int i=0;i<size;i++) {
            ServiceRequest request=processingRequests.dequeue();
            data.add(request);
            processingRequests.enqueue(request);
        }

        return data;
    }

    // Time Complexity:O(n)
    public ObservableList<ServiceRequest> displayAllRequests() {
        ObservableList<ServiceRequest> data=FXCollections.observableArrayList();
        int size=allRequests.size();

        for(int i=0;i<size;i++) {
            ServiceRequest request=allRequests.dequeue();
            data.add(request);
            allRequests.enqueue(request);
        }

        return data;
    }

    
    // Time Complexity:O(n)
    public ObservableList<Service> displayCompletedServices() {
        ObservableList<Service> data=FXCollections.observableArrayList();
        int size=completedServices.size();

        for(int i=0;i<size;i++) {
            Service service=completedServices.dequeue();
            data.add(service);
            completedServices.enqueue(service);
        }

        return data;
    }

    // Time Complexity:O(n)
    public ObservableList<Service> displayServiceHistory() {
        ObservableList<Service> data=FXCollections.observableArrayList();
        Stack<Service> temporary=new Stack<Service>();

        while(!serviceHistory.isEmpty()) {
            Service service=serviceHistory.pop();
            data.add(service);
            temporary.push(service);
        }

        while(!temporary.isEmpty())
            serviceHistory.push(temporary.pop());

        return data;
    }

    // Time Complexity:O(n)
    public ObservableList<Service> displayVehicleServiceHistory(int vehicleId,VehicleManager vehicles) {
        ObservableList<Service> data=FXCollections.observableArrayList();
        Vehicle vehicle=vehicles.searchVehicle(vehicleId);

        if(vehicle==null)
            return data;

        Stack<Service> history=vehicle.getServiceHistory();
        Stack<Service> temporary=new Stack<Service>();

        while(!history.isEmpty()) {
            Service service=history.pop();
            data.add(service);
            temporary.push(service);
        }

        while(!temporary.isEmpty())
            history.push(temporary.pop());

        return data;
    }
    
    // Time Complexity:O(1)
    public Service getLastCompletedService() {
        if(serviceHistory.isEmpty())
            return null;

        return serviceHistory.peek();
    }

    // Time Complexity:O(log n)
    public Service getLastVehicleService(int vehicleId,VehicleManager vehicles) {
        Vehicle vehicle=vehicles.searchVehicle(vehicleId);

        if(vehicle==null)
            return null;
        if(vehicle.getServiceHistory().isEmpty())
            return null;

        return vehicle.getServiceHistory().peek();
    }

    // Time Complexity:O(n)
    public boolean hasVehicle(int vehicleId) {
        boolean found=false;
        int requestSize=allRequests.size();

        for(int i=0;i<requestSize;i++) {
            ServiceRequest request=allRequests.dequeue();

            if(request.getVehicleId()==vehicleId)
                found=true;

            allRequests.enqueue(request);
        }

        int serviceSize=completedServices.size();

        for(int i=0;i<serviceSize;i++) {
            Service service=completedServices.dequeue();

            if(service.getVehicleId()==vehicleId)
                found=true;

            completedServices.enqueue(service);
        }

        return found;
    }

    // Time Complexity:O(n)
    public boolean hasCustomer(int customerId) {
        boolean found=false;
        int requestSize=allRequests.size();

        for(int i=0;i<requestSize;i++) {
            ServiceRequest request=allRequests.dequeue();

            if(request.getCustomerId()==customerId)
                found=true;

            allRequests.enqueue(request);
        }

        int serviceSize=completedServices.size();

        for(int i=0;i<serviceSize;i++) {
            Service service=completedServices.dequeue();

            if(service.getCustomerId()==customerId)
                found=true;

            completedServices.enqueue(service);
        }

        return found;
    }

    // Time Complexity:O(n^2)
    public void readServiceRequests(String fileName,CustomerManager customers,VehicleManager vehicles)throws Exception {
        java.io.File file=new java.io.File(fileName);

        try(java.util.Scanner input=new java.util.Scanner(file)) {
            while(input.hasNextLine()) {
                String line=input.nextLine();
                java.util.Scanner lineScanner=new java.util.Scanner(line);
                lineScanner.useDelimiter(",");

                int requestId=lineScanner.nextInt();
                int vehicleId=lineScanner.nextInt();
                int customerId=lineScanner.nextInt();
                String serviceType=lineScanner.next();
                String date=lineScanner.next();
                String status=lineScanner.next();

                ServiceRequest request=new ServiceRequest( requestId, vehicleId,customerId, serviceType,date, status );

                if(searchRequest(requestId)==null&&customers.searchCustomer(customerId)!=null&&vehicles.searchVehicle(vehicleId)!=null) {
                    allRequests.enqueue(request);

                    if(request.getStatus().equals("PENDING"))
                        maintenanceQueue.enqueue(request);
                    else if(request.getStatus().equals("IN_PROGRESS")) {
                        processingRequests.enqueue(request);
                        vehicles.markAsInService(vehicleId);
                    }
                }

                lineScanner.close();
            }
        }
    }
    // Time Complexity:O(n)
    public void writeServiceRequests(String fileName)throws Exception {
        java.io.File file=new java.io.File(fileName);

        try(java.io.PrintWriter output=new java.io.PrintWriter(file)) {
            int size=allRequests.size();

            for(int i=0;i<size;i++) {
                ServiceRequest request=allRequests.dequeue();
                output.println(request);
                allRequests.enqueue(request);
            }
        }
    }

    // Time Complexity:O(n^2)
    public void readServices(String fileName,VehicleManager vehicles)throws Exception{
        java.io.File file=new java.io.File(fileName);

        try(java.util.Scanner input=new java.util.Scanner(file)){
            while(input.hasNextLine()){
                String line=input.nextLine();
                java.util.Scanner lineScanner=new java.util.Scanner(line);
                lineScanner.useDelimiter(",");

                int serviceId=lineScanner.nextInt();
                int vehicleId=lineScanner.nextInt();
                int customerId=lineScanner.nextInt();
                String serviceType=lineScanner.next();
                String date=lineScanner.next();
                double cost=lineScanner.nextDouble();
                String status=lineScanner.next();

                Service service=new Service(serviceId,vehicleId,customerId,serviceType,date,cost,status);
                Vehicle vehicle=vehicles.searchVehicle(vehicleId);

                if(searchService(serviceId)==null&&vehicle!=null){
                    completedServices.enqueue(service);
                    serviceHistory.push(service);
                    vehicle.getServiceHistory().push(service);
                }

                lineScanner.close();
            }
        }
    }
    // Time Complexity:O(n)
    public void writeServices(String fileName)throws Exception {
        java.io.File file=new java.io.File(fileName);

        try(java.io.PrintWriter output=new java.io.PrintWriter(file)) {
            int size=completedServices.size();

            for(int i=0;i<size;i++) {
                Service service=completedServices.dequeue();
                output.println(service);
                completedServices.enqueue(service);
            }
        }
    }
    
    // Time Complexity:O(n)
    public boolean hasActiveRequest(int vehicleId){
        boolean found=false;
        int size=allRequests.size();
        for(int i=0;i<size;i++){
            ServiceRequest request=allRequests.dequeue();
            if(request.getVehicleId()==vehicleId&&(request.getStatus().equalsIgnoreCase("PENDING")||request.getStatus().equalsIgnoreCase("IN_PROGRESS")))
                found=true;
            allRequests.enqueue(request);
        }
        return found;
    }
    
    // Time Complexity:O(n)
    public ServiceRequest removeServiceRequest(int requestId){
        ServiceRequest removed=null;
        int pendingSize=maintenanceQueue.size();
        for(int i=0;i<pendingSize;i++){
            ServiceRequest request=maintenanceQueue.dequeue();
            if(request.getRequestId()==requestId)
                removed=request;
            else
                maintenanceQueue.enqueue(request);
        }
        int allSize=allRequests.size();
        for(int i=0;i<allSize;i++){
            ServiceRequest request=allRequests.dequeue();
            if(request.getRequestId()!=requestId)
                allRequests.enqueue(request);
        }
        return removed;
    }
    
    // Time Complexity:O(n)
    public boolean returnRequestToPendingFront(ServiceRequest request){
        if(request==null)
            return false;
        if(searchProcessingRequest(request.getRequestId())==null)
            return false;
        removeProcessingRequest(request.getRequestId());
        request.setStatus("PENDING");
        Queue<ServiceRequest> temp=new Queue<ServiceRequest>();
        while(!maintenanceQueue.isEmpty())
            temp.enqueue(maintenanceQueue.dequeue());
        maintenanceQueue.enqueue(request);
        while(!temp.isEmpty())
            maintenanceQueue.enqueue(temp.dequeue());
        return true;
    }
    
    // Time Complexity:O(n)
    public boolean undoCompleteService(Service service,ServiceRequest request,VehicleManager vehicles){
        if(service==null||request==null)
            return false;
        Vehicle vehicle=vehicles.searchVehicle(service.getVehicleId());
        if(vehicle==null)
            return false;
        if(serviceHistory.isEmpty()||vehicle.getServiceHistory().isEmpty())
            return false;
        if(serviceHistory.peek().getServiceId()!=service.getServiceId())
            return false;
        if(vehicle.getServiceHistory().peek().getServiceId()!=service.getServiceId())
            return false;
        boolean found=false;
        int size=completedServices.size();
        for(int i=0;i<size;i++){
            Service current=completedServices.dequeue();
            if(current.getServiceId()==service.getServiceId())
                found=true;
            else
                completedServices.enqueue(current);
        }
        if(!found)
            return false;
        serviceHistory.pop();
        vehicle.getServiceHistory().pop();
        request.setStatus("IN_PROGRESS");
        processingRequests.enqueue(request);
        vehicles.markAsInService(vehicle.getVehicleId());
        return true;
    }
    
    // Time Complexity:O(n)
    public int generateRequestId(){
        int maxId=0;
        int size=allRequests.size();
        for(int i=0;i<size;i++){
            ServiceRequest request=allRequests.dequeue();
            if(request.getRequestId()>maxId)
                maxId=request.getRequestId();
            allRequests.enqueue(request);
        }
        return maxId+1;
    }
    
    
 // Time Complexity: O(n)
    public int generateServiceId(){
        int maxId=0;
        int size=completedServices.size();

        for(int i=0;i<size;i++){
            Service service=completedServices.dequeue();

            if(service.getServiceId()>maxId)
                maxId=service.getServiceId();

            completedServices.enqueue(service);
        }

        return maxId+1;
    }
}