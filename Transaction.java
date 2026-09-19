package phaseTwo;

public class Transaction{
    private int transactionId;
    private int customerId;
    private int vehicleId;
    private double amount;
    private String transactionType;
    private String date;

    public Transaction(int transactionId,int customerId,int vehicleId,double amount,String transactionType,String date){
        setTransactionId(transactionId);
        setCustomerId(customerId);
        setVehicleId(vehicleId);
        setTransactionType(transactionType);
        setAmount(amount);
        setDate(date);
    }

    private void setTransactionId(int transactionId){
        if(transactionId<=0)
            throw new IllegalArgumentException("Invalid transaction ID");
        this.transactionId=transactionId;
    }

    public int getTransactionId(){
        return transactionId;
    }

    private void setCustomerId(int customerId){
        if(customerId<=0)
            throw new IllegalArgumentException("Invalid customer ID");
        this.customerId=customerId;
    }

    public int getCustomerId(){
        return customerId;
    }

    private void setVehicleId(int vehicleId){
        if(vehicleId<=0)
            throw new IllegalArgumentException("Invalid vehicle ID");
        this.vehicleId=vehicleId;
    }

    public int getVehicleId(){
        return vehicleId;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        if(amount<0&&!transactionType.equals("REFUND")&&!transactionType.equals("ADJUSTMENT"))
            throw new IllegalArgumentException("Amount cannot be negative");
        this.amount=amount;
    }

    public String getTransactionType(){
        return transactionType;
    }

    public void setTransactionType(String transactionType){
        if(transactionType==null)
            throw new IllegalArgumentException("Invalid transaction type");
        transactionType=transactionType.trim().toUpperCase();
        if(!transactionType.equals("SALE")&&!transactionType.equals("SERVICE_PAYMENT")&&!transactionType.equals("DEPOSIT")&&!transactionType.equals("REFUND")&&!transactionType.equals("ADJUSTMENT"))
            throw new IllegalArgumentException("Invalid transaction type");
        this.transactionType=transactionType;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        if(date==null||date.trim().isEmpty())
            throw new IllegalArgumentException("Invalid date");
        this.date=date.trim();
    }

    @Override
    public String toString(){
        return transactionId+","+customerId+","+vehicleId+","+amount+","+transactionType+","+date;
    }
}