package phaseTwo;

public class UndoRedoOperation{
    private String operationType;
    private Customer customer;
    private Vehicle vehicle;
    private Transaction transaction;
    private String oldStatus;
    private String newStatus;
    private double oldAmount;
    private double newAmount;
    private String oldName;
    private String newName;
    private String oldPhone;
    private String newPhone;
    private String oldAddress;
    private String newAddress;
    private Vehicle oldVehicle;
    private Vehicle newVehicle;
    private int oldReservedCustomerId;
    private int newReservedCustomerId;
    private ServiceRequest serviceRequest;
    private Service service;
    private Queue<Customer> oldReservationQueue;
    
    public UndoRedoOperation(String operationType,Customer customer){
        this.operationType=operationType;
        this.customer=customer;
    }

    public UndoRedoOperation(String operationType,Vehicle vehicle){
        this.operationType=operationType;
        this.vehicle=vehicle;
    }

    public UndoRedoOperation(String operationType,Vehicle vehicle,String oldStatus,String newStatus){
        this.operationType=operationType;
        this.vehicle=vehicle;
        this.oldStatus=oldStatus;
        this.newStatus=newStatus;
    }

    public UndoRedoOperation(String operationType,Transaction transaction,double oldAmount,double newAmount){
        this.operationType=operationType;
        this.transaction=transaction;
        this.oldAmount=oldAmount;
        this.newAmount=newAmount;
    }

    public UndoRedoOperation(String operationType,Customer customer,String oldName,String oldPhone,String oldAddress,String newName,String newPhone,String newAddress){
        this.operationType=operationType;
        this.customer=customer;
        this.oldName=oldName;
        this.oldPhone=oldPhone;
        this.oldAddress=oldAddress;
        this.newName=newName;
        this.newPhone=newPhone;
        this.newAddress=newAddress;
    }

    public UndoRedoOperation(String operationType,Vehicle vehicle,Vehicle oldVehicle,Vehicle newVehicle){
        this.operationType=operationType;
        this.vehicle=vehicle;
        this.oldVehicle=oldVehicle;
        this.newVehicle=newVehicle;
    }
    public UndoRedoOperation(String operationType,Vehicle vehicle,String oldStatus,String newStatus,int oldReservedCustomerId,int newReservedCustomerId){
        this.operationType=operationType;
        this.vehicle=vehicle;
        this.oldStatus=oldStatus;
        this.newStatus=newStatus;
        this.oldReservedCustomerId=oldReservedCustomerId;
        this.newReservedCustomerId=newReservedCustomerId;
    }
    
    public UndoRedoOperation(String operationType,Customer customer,Vehicle vehicle){
        this.operationType=operationType;
        this.customer=customer;
        this.vehicle=vehicle;
    }
    
    public UndoRedoOperation(String operationType,Customer customer,Vehicle vehicle,String oldStatus,String newStatus,int oldReservedCustomerId,int newReservedCustomerId){
        this.operationType=operationType;
        this.customer=customer;
        this.vehicle=vehicle;
        this.oldStatus=oldStatus;
        this.newStatus=newStatus;
        this.oldReservedCustomerId=oldReservedCustomerId;
        this.newReservedCustomerId=newReservedCustomerId;
    }
    public UndoRedoOperation(String operationType,ServiceRequest serviceRequest){
        this.operationType=operationType;
        this.serviceRequest=serviceRequest;
    }
    public UndoRedoOperation(String operationType,ServiceRequest serviceRequest,Vehicle vehicle,String oldStatus,String newStatus){
        this.operationType=operationType;
        this.serviceRequest=serviceRequest;
        this.vehicle=vehicle;
        this.oldStatus=oldStatus;
        this.newStatus=newStatus;
    }
    
    public UndoRedoOperation(String operationType,ServiceRequest serviceRequest,Service service,Vehicle vehicle){
        this.operationType=operationType;
        this.serviceRequest=serviceRequest;
        this.service=service;
        this.vehicle=vehicle;
    }
    
    public UndoRedoOperation(String operationType,Transaction transaction,Vehicle vehicle,String oldStatus,int oldReservedCustomerId,Queue<Customer> oldReservationQueue){
        this.operationType=operationType;
        this.transaction=transaction;
        this.vehicle=vehicle;
        this.oldStatus=oldStatus;
        this.oldReservedCustomerId=oldReservedCustomerId;
        this.oldReservationQueue=oldReservationQueue;
    }
    
    public UndoRedoOperation(String operationType,Transaction transaction){
        this.operationType=operationType;
        this.transaction=transaction;
    }
    public String getOperationType(){
        return operationType;
    }

    public Customer getCustomer(){
        return customer;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public Transaction getTransaction(){
        return transaction;
    }

    public String getOldStatus(){
        return oldStatus;
    }

    public String getNewStatus(){
        return newStatus;
    }

    public double getOldAmount(){
        return oldAmount;
    }

    public double getNewAmount(){
        return newAmount;
    }

    public String getOldName(){
        return oldName;
    }

    public String getNewName(){
        return newName;
    }

    public String getOldPhone(){
        return oldPhone;
    }

    public String getNewPhone(){
        return newPhone;
    }

    public String getOldAddress(){
        return oldAddress;
    }

    public String getNewAddress(){
        return newAddress;
    }
    public Vehicle getOldVehicle(){
        return oldVehicle;
    }

    public Vehicle getNewVehicle(){
        return newVehicle;
    }
    public int getOldReservedCustomerId(){
        return oldReservedCustomerId;
    }

    public int getNewReservedCustomerId(){
        return newReservedCustomerId;
    }
    public ServiceRequest getServiceRequest(){
        return serviceRequest;
    }
    public Service getService(){
        return service;
    }
    public Queue<Customer> getOldReservationQueue(){
        return oldReservationQueue;
    }
    @Override
    public String toString(){
        return operationType;
    }
}