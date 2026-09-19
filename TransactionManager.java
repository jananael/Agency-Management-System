	package phaseTwo;
	
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	
	public class TransactionManager {
	    private Queue<Transaction> transactions=new Queue<Transaction>();
	
	    // Time Complexity: O(1)
	    public int getSize() {
	        return transactions.size();
	    }
	
	    // Time Complexity: O(n)
	    public Transaction searchTransaction(int transactionId) {
	        Transaction found=null;
	        int size=transactions.size();
	
	        for(int i=0;i<size;i++) {
	            Transaction transaction=transactions.dequeue();
	
	            if(transaction.getTransactionId()==transactionId)
	                found=transaction;
	
	            transactions.enqueue(transaction);
	        }
	
	        return found;
	    }
	 // Time Complexity: O(n)
	    public boolean addTransaction(Transaction transaction) {
	        if(transaction==null)
	            return false;
	        if(searchTransaction(transaction.getTransactionId())!=null)
	            return false;
	
	        transactions.enqueue(transaction);
	        return true;
	    }
	
	 // Time Complexity: O(n)
	  public Transaction createSale(int transactionId,int customerId,int vehicleId,double amount,String date,CustomerManager customers,VehicleManager vehicles,MaintenanceManager maintenance){
	    if(transactionId<=0)
	        return null;
	    if(searchTransaction(transactionId)!=null)
	        return null;
	    if(customers.searchCustomer(customerId)==null)
	        return null;
	    Vehicle vehicle=vehicles.searchVehicle(vehicleId);
	    if(vehicle==null)
	        return null;
	    if(maintenance.hasActiveRequest(vehicleId))
	        return null;
	    if(vehicle.getStatus().equalsIgnoreCase("AVAILABLE")&&!vehicle.getReservationQueue().isEmpty())
	        return null;
	    boolean available=vehicle.getStatus().equalsIgnoreCase("AVAILABLE");
	    boolean reservedForCustomer=vehicle.getStatus().equalsIgnoreCase("RESERVED")&&vehicle.getReservedCustomerId()==customerId;
	    if(!available&&!reservedForCustomer)
	        return null;
	    if(amount<0)
	        return null;
	    if(date==null||date.trim().isEmpty())
	        return null;
	    try{
	        Transaction transaction=new Transaction(transactionId,customerId,vehicleId,amount,"SALE",date);
	        if(!addTransaction(transaction))
	            return null;
	        vehicle.getReservationQueue().clear();
	        vehicles.changeVehicleStatus(vehicleId,"SOLD");
	        return transaction;
	    }
	    catch(Exception ex){
	        return null;
	    }
	}
	  
	// Time Complexity: O(n)
	    public Transaction createServicePayment(int transactionId,int serviceId,double amount,String date,MaintenanceManager maintenance) {
	        Service service=maintenance.searchService(serviceId);
	
	        if(service==null)
	            return null;
	
	        try {
	            Transaction transaction=new Transaction(transactionId,service.getCustomerId(),service.getVehicleId(),amount,"SERVICE_PAYMENT",date);
	
	            if(!addTransaction(transaction))
	                return null;
	
	            return transaction;
	        }
	        catch(Exception ex) {
	            return null;
	        }
	    }
	
	    
	 // Time Complexity: O(n)
	    public Transaction createDeposit(int transactionId,int customerId,int vehicleId,double amount,String date,CustomerManager customers,VehicleManager vehicles) {
	        if(customers.searchCustomer(customerId)==null)
	            return null;
	        if(vehicles.searchVehicle(vehicleId)==null)
	            return null;
	
	        try {
	            Transaction transaction=new Transaction(transactionId,customerId,vehicleId,amount,"DEPOSIT",date);
	
	            if(!addTransaction(transaction))
	                return null;
	
	            return transaction;
	        }
	        catch(Exception ex) {
	            return null;
	        }
	    }
	 // Time Complexity: O(n)
	    public Transaction createRefund(int transactionId,int customerId,int vehicleId,double amount,String date,CustomerManager customers,VehicleManager vehicles){
	        if(customers.searchCustomer(customerId)==null)
	            return null;
	        if(vehicles.searchVehicle(vehicleId)==null)
	            return null;
	        if(amount<=0)
	            return null;

	        double refundableAmount=0;
	        int size=transactions.size();

	        for(int i=0;i<size;i++){
	            Transaction transaction=transactions.dequeue();

	            if(transaction.getCustomerId()==customerId&&transaction.getVehicleId()==vehicleId)
	                refundableAmount+=transaction.getAmount();

	            transactions.enqueue(transaction);
	        }

	        if(refundableAmount<=0)
	            return null;
	        if(amount>refundableAmount)
	            return null;

	        try{
	            Transaction transaction=new Transaction(transactionId,customerId,vehicleId,-amount,"REFUND",date);

	            if(!addTransaction(transaction))
	                return null;

	            return transaction;
	        }
	        catch(Exception ex){
	            return null;
	        }
	    }
	    
	    
	 // Time Complexity: O(n)
	    public ObservableList<Transaction> displayTransactions() {
	        ObservableList<Transaction> data=FXCollections.observableArrayList();
	        int size=transactions.size();
	
	        for(int i=0;i<size;i++) {
	            Transaction transaction=transactions.dequeue();
	            data.add(transaction);
	            transactions.enqueue(transaction);
	        }
	
	        return data;
	    }
	 // Time Complexity: O(n)
	    public boolean hasCustomer(int customerId) {
	        boolean found=false;
	        int size=transactions.size();
	
	        for(int i=0;i<size;i++) {
	            Transaction transaction=transactions.dequeue();
	
	            if(transaction.getCustomerId()==customerId)
	                found=true;
	
	            transactions.enqueue(transaction);
	        }
	
	        return found;
	    }
	 // Time Complexity: O(n)
	    public boolean hasVehicle(int vehicleId) {
	        boolean found=false;
	        int size=transactions.size();
	
	        for(int i=0;i<size;i++) {
	            Transaction transaction=transactions.dequeue();
	
	            if(transaction.getVehicleId()==vehicleId)
	                found=true;
	
	            transactions.enqueue(transaction);
	        }
	
	        return found;
	    }
	 // Time Complexity: O(n^2)
	    public void readTransactions(String fileName)throws Exception {
	        java.io.File file=new java.io.File(fileName);
	
	        try(java.util.Scanner input=new java.util.Scanner(file)) {
	            while(input.hasNextLine()) {
	                String line=input.nextLine();
	                java.util.Scanner lineScanner=new java.util.Scanner(line);
	                lineScanner.useDelimiter(",");
	
	                int transactionId=lineScanner.nextInt();
	                int customerId=lineScanner.nextInt();
	                int vehicleId=lineScanner.nextInt();
	                double amount=lineScanner.nextDouble();
	                String transactionType=lineScanner.next();
	                String date=lineScanner.next();
	
	                Transaction transaction=new Transaction(transactionId,customerId,vehicleId,amount,transactionType,date);
	                addTransaction(transaction);
	                lineScanner.close();
	            }
	        }
	    }
	
	 // Time Complexity: O(n)
	    public void writeTransactions(String fileName)throws Exception {
	        java.io.File file=new java.io.File(fileName);
	
	        try(java.io.PrintWriter output=new java.io.PrintWriter(file)) {
	            int size=transactions.size();
	
	            for(int i=0;i<size;i++) {
	                Transaction transaction=transactions.dequeue();
	                output.println(transaction);
	                transactions.enqueue(transaction);
	            }
	        }
	        
	    }
	    
	 // Time Complexity: O(n)
	    public boolean applyDiscount(int transactionId,double discount,UndoRedoManager undoRedo) {
	        Transaction transaction=searchTransaction(transactionId);
	
	        if(transaction==null)
	            return false;
	        if(!transaction.getTransactionType().equals("SALE"))
	            return false;
	        if(discount<=0)
	            return false;
	        if(discount>transaction.getAmount())
	            return false;
	
	        double oldAmount=transaction.getAmount();
	        double newAmount=oldAmount-discount;
	
	        transaction.setAmount(newAmount);
	
	        UndoRedoOperation operation=new UndoRedoOperation( "APPLY_DISCOUNT",transaction,oldAmount, newAmount);
	
	        if(!undoRedo.recordOperation(operation)) {
	            transaction.setAmount(oldAmount);
	            return false;
	        }
	
	        return true;
	    }
	    
	 // Time Complexity: O(n)
	    public int generateTransactionId(){
	        int maxId=0;
	        int size=transactions.size();

	        for(int i=0;i<size;i++){
	            Transaction transaction=transactions.dequeue();

	            if(transaction.getTransactionId()>maxId)
	                maxId=transaction.getTransactionId();

	            transactions.enqueue(transaction);
	        }

	        return maxId+1;
	    }
	 // Time Complexity: O(n)
	    public Transaction removeTransaction(int transactionId){
	        Transaction removed=null;
	        int size=transactions.size();
	        for(int i=0;i<size;i++){
	            Transaction transaction=transactions.dequeue();
	            if(transaction.getTransactionId()==transactionId)
	                removed=transaction;
	            else
	                transactions.enqueue(transaction);
	        }
	        return removed;
	    }
	    
	}