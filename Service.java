package phaseTwo;

public class Service {
    private int serviceId;
    private int vehicleId;
    private int customerId;
    private String serviceType;
    private String date;
    private double cost;
    private String status;

    public Service(int serviceId,int vehicleId,int customerId,String serviceType,String date,double cost,String status) {
        setServiceId(serviceId);
        setVehicleId(vehicleId);
        setCustomerId(customerId);
        setServiceType(serviceType);
        setDate(date);
        setCost(cost);
        setStatus(status);
    }

    private void setServiceId(int serviceId) {
        if(serviceId<=0)
            throw new IllegalArgumentException("Invalid service ID");

        this.serviceId=serviceId;
    }

    public int getServiceId() {
        return serviceId;
    }

    private void setVehicleId(int vehicleId) {
        if(vehicleId<=0)
            throw new IllegalArgumentException("Invalid vehicle ID");

        this.vehicleId=vehicleId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    private void setCustomerId(int customerId) {
        if(customerId<=0)
            throw new IllegalArgumentException("Invalid customer ID");

        this.customerId=customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        if(serviceType==null||serviceType.trim().isEmpty())
            throw new IllegalArgumentException("Invalid service type");

        this.serviceType=serviceType.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if(date==null||date.trim().isEmpty())
            throw new IllegalArgumentException("Invalid service date");

        this.date=date.trim();
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        if(cost<0)
            throw new IllegalArgumentException("Service cost cannot be negative");

        this.cost=cost;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status){
        if(status==null||!status.trim().equalsIgnoreCase("COMPLETED"))
            throw new IllegalArgumentException("Invalid service status");

        this.status="COMPLETED";
    }

    @Override
    public String toString() {
        return serviceId+","+vehicleId+","+customerId+","+serviceType+","+date+","+cost+","+status;
    }
}