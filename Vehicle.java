package phaseTwo;

public class Vehicle implements Comparable<Vehicle> {
    private int vehicleId;
    private String make;
    private String model;
    private int year;
    private double price;
    private String color;
    private String status;
    private Queue<Customer> reservationQueue=new Queue<Customer>();
    private Stack<Service> serviceHistory=new Stack<Service>();
    private int reservedCustomerId;

    public Vehicle(int vehicleId,String make,String model,int year,double price,String color,String status) {
        setVehicleId(vehicleId);
        setMake(make);
        setModel(model);
        setYear(year);
        setPrice(price);
        setColor(color);
        setStatus(status);
    }

    public Vehicle(int vehicleId) {
        setVehicleId(vehicleId);
    }

    private void setVehicleId(int vehicleId) {
        if(vehicleId<=0)
            throw new IllegalArgumentException("Invalid vehicle ID");

        this.vehicleId=vehicleId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        if(make==null||make.trim().isEmpty())
            throw new IllegalArgumentException("Invalid vehicle make");

        this.make=make.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if(model==null||model.trim().isEmpty())
            throw new IllegalArgumentException("Invalid vehicle model");

        this.model=model.trim();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if(year<=0)
            throw new IllegalArgumentException("Invalid vehicle year");

        this.year=year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price<0)
            throw new IllegalArgumentException("Vehicle price cannot be negative");

        this.price=price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if(color==null||color.trim().isEmpty())
            throw new IllegalArgumentException("Invalid vehicle color");

        this.color=color.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status==null)
            throw new IllegalArgumentException("Invalid vehicle status");

        status=status.trim().toUpperCase();

        if(!status.equals("AVAILABLE")&&!status.equals("SOLD")&&!status.equals("IN_SERVICE")&&!status.equals("RESERVED"))
            throw new IllegalArgumentException("Invalid vehicle status");

        this.status=status;
    }

    public Queue<Customer> getReservationQueue() {
        return reservationQueue;
    }

    public Stack<Service> getServiceHistory() {
        return serviceHistory;
    }

    public int getReservedCustomerId(){
        return reservedCustomerId;
    }

    public void setReservedCustomerId(int reservedCustomerId){
        if(reservedCustomerId<0)
            throw new IllegalArgumentException("Invalid reserved customer ID");
        this.reservedCustomerId=reservedCustomerId;
    }
    
    @Override
    public int compareTo(Vehicle other) {
        if(vehicleId<other.vehicleId)
            return -1;
        if(vehicleId>other.vehicleId)
            return 1;

        return 0;
    }

    @Override
    public String toString() {
        return vehicleId+","+make+","+model+","+year+","+price+","+color+","+status;
    }
}