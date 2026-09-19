package phaseTwo;

public class ServiceRequest {
    private int requestId;
    private int vehicleId;
    private int customerId;
    private String serviceType;
    private String date;
    private String status;

    public ServiceRequest(int requestId,int vehicleId,int customerId,String serviceType,String date,String status) {
        setRequestId(requestId);
        setVehicleId(vehicleId);
        setCustomerId(customerId);
        setServiceType(serviceType);
        setDate(date);
        setStatus(status);
    }

    private void setRequestId(int requestId) {
        if(requestId<=0)
            throw new IllegalArgumentException("Invalid request ID");

        this.requestId=requestId;
    }

    public int getRequestId() {
        return requestId;
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
            throw new IllegalArgumentException("Invalid request date");

        this.date=date.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status==null)
            throw new IllegalArgumentException("Invalid request status");

        status=status.trim().toUpperCase();

        if(status.equals("PROCESSING"))
            status="IN_PROGRESS";

        if(!status.equals("PENDING")&&!status.equals("IN_PROGRESS")&&!status.equals("COMPLETED") &&!status.equals("CANCELLED"))
            throw new IllegalArgumentException("Invalid request status");

        this.status=status;
    }

    @Override
    public String toString() {
        return requestId+","+vehicleId+","+customerId+","+serviceType+","+date+","+status;
    }
}