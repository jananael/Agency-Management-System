package phaseTwo;

public class UndoRedoManager{
    private Stack<UndoRedoOperation> undoStack=new Stack<UndoRedoOperation>();
    private Stack<UndoRedoOperation> redoStack=new Stack<UndoRedoOperation>();

    // Time Complexity: O(1)
    public int getUndoSize(){
        return undoStack.size();
    }

    // Time Complexity: O(1)
    public int getRedoSize(){
        return redoStack.size();
    }
    // Time Complexity: O(1)
    public boolean canUndo(){
        return !undoStack.isEmpty();
    }
    // Time Complexity: O(1)
    public boolean canRedo(){
        return !redoStack.isEmpty();
    }
    // Time Complexity: O(1)
    public String getNextUndoOperation(){
        if(undoStack.isEmpty())
            return "None";
        return getOperationName(undoStack.peek().getOperationType());
    }
    // Time Complexity: O(1)
    public String getNextRedoOperation(){
        if(redoStack.isEmpty())
            return "None";
        return getOperationName(redoStack.peek().getOperationType());
    }
    // Time Complexity: O(1)
    private String getOperationName(String type){
        if("ADD_CUSTOMER".equals(type))
            return "Add Customer";
        if("REMOVE_CUSTOMER".equals(type))
            return "Remove Customer";
        if("UPDATE_CUSTOMER".equals(type))
            return "Update Customer";
        if("ADD_VEHICLE".equals(type))
            return "Add Vehicle";
        if("REMOVE_VEHICLE".equals(type))
            return "Remove Vehicle";
        if("APPLY_DISCOUNT".equals(type))
            return "Apply Discount";
        if("UPDATE_VEHICLE".equals(type))
            return "Update Vehicle";
        if("CHANGE_STATUS".equals(type))
            return "Change Vehicle Status";
        if("ADD_WAITING_CUSTOMER".equals(type))
            return "Add Waiting Customer";
        if("SERVE_WAITING_CUSTOMER".equals(type))
            return "Serve Waiting Customer";
        if("ADD_RESERVATION".equals(type))
            return "Add Reservation";
        if("PROCESS_RESERVATION".equals(type))
            return "Process Reservation";
        if("CREATE_SERVICE_REQUEST".equals(type))
            return "Create Service Request";
        if("PROCESS_SERVICE_REQUEST".equals(type))
            return "Process Service Request";
        if("COMPLETE_SERVICE".equals(type))
            return "Complete Service";
        if("CREATE_TRANSACTION".equals(type))
            return "Create Transaction";
        return "None";
    }
    // Time Complexity: O(1)
    public boolean recordOperation(UndoRedoOperation operation){
        if(operation==null)
            return false;
        String type=operation.getOperationType();
        if(type==null)
            return false;
        if(!type.equalsIgnoreCase("ADD_CUSTOMER")&&!type.equalsIgnoreCase("REMOVE_CUSTOMER")&&!type.equalsIgnoreCase("UPDATE_CUSTOMER")
                &&!type.equalsIgnoreCase("ADD_VEHICLE")&&!type.equalsIgnoreCase("REMOVE_VEHICLE")
                &&!type.equalsIgnoreCase("APPLY_DISCOUNT")&&!type.equalsIgnoreCase("UPDATE_VEHICLE")&&!type.equalsIgnoreCase("CHANGE_STATUS")&&!type.equals("ADD_WAITING_CUSTOMER")
                &&!type.equals("SERVE_WAITING_CUSTOMER")&&!type.equals("ADD_RESERVATION")
                &&!type.equals("PROCESS_RESERVATION")&&!type.equals("CREATE_SERVICE_REQUEST")
                &&!type.equals("PROCESS_SERVICE_REQUEST")&&!type.equals("COMPLETE_SERVICE")&&!type.equals("CREATE_TRANSACTION"))
        return false;
        undoStack.push(operation);
        redoStack.clear();
        return true;
    }

    // Time Complexity: O(n)
    public boolean undo(CustomerManager customers,VehicleManager vehicles,CustomerWaitingManager waiting,VehicleReservationManager reservations,MaintenanceManager maintenance,TransactionManager transactions){
        if(undoStack.isEmpty())
            return false;
        UndoRedoOperation operation=undoStack.pop();
        String type=operation.getOperationType();
        boolean done=false;

        if("ADD_CUSTOMER".equals(type)){
            Customer customer=operation.getCustomer();
            if(customer!=null)
                done=customers.removeCustomer(customer.getCustomerId(),waiting,maintenance,transactions,vehicles)!=null;
        }
        else if("REMOVE_CUSTOMER".equals(type)){
            Customer customer=operation.getCustomer();
            if(customer!=null)
                done=customers.addCustomer(customer);
        }
        else if("UPDATE_CUSTOMER".equals(type)){
            Customer customer=operation.getCustomer();
            if(customer!=null)
                done=customers.updateCustomer(customer.getCustomerId(),operation.getOldName(),operation.getOldPhone(),operation.getOldAddress());
        }
        else if("ADD_VEHICLE".equals(type)){
            Vehicle vehicle=operation.getVehicle();
            if(vehicle!=null)
                done=vehicles.removeVehicle(vehicle.getVehicleId(),maintenance,transactions)!=null;
        }
        else if("REMOVE_VEHICLE".equals(type)){
            Vehicle vehicle=operation.getVehicle();
            if(vehicle!=null)
                done=vehicles.addVehicle(vehicle);
        }
        else if("UPDATE_VEHICLE".equals(type)){
            Vehicle vehicle=operation.getVehicle();
            Vehicle oldVehicle=operation.getOldVehicle();
            if(vehicle!=null&&oldVehicle!=null)
                done=vehicles.updateVehicle(
                    vehicle.getVehicleId(),
                    oldVehicle.getMake(),
                    oldVehicle.getModel(),
                    String.valueOf(oldVehicle.getYear()),
                    String.valueOf(oldVehicle.getPrice()),
                    oldVehicle.getColor()
                );
        }
        else if("CHANGE_STATUS".equals(type)){
            Vehicle vehicle=operation.getVehicle();
            if(vehicle!=null){
                done=vehicles.changeVehicleStatus(vehicle.getVehicleId(),operation.getOldStatus());
                if(done)
                    vehicle.setReservedCustomerId(operation.getOldReservedCustomerId());
            }
        }
        else if("APPLY_DISCOUNT".equals(type)){
            Transaction transaction=operation.getTransaction();
            if(transaction!=null){
                try{
                    transaction.setAmount(operation.getOldAmount());
                    done=true;
                }
                catch(Exception ex){
                    done=false;
                }
            }
        }
        else if("ADD_WAITING_CUSTOMER".equals(type)){
            Customer customer=operation.getCustomer();
            if(customer!=null)
                done=waiting.removeWaitingCustomer(customer.getCustomerId())!=null;
        }
        else if("SERVE_WAITING_CUSTOMER".equals(type)){
            Customer customer=operation.getCustomer();
            if(customer!=null)
                done=waiting.returnCustomerToFront(customer);
        }
        else if("ADD_RESERVATION".equals(type)){
            Customer customer=operation.getCustomer();
            Vehicle vehicle=operation.getVehicle();
            if(customer!=null&&vehicle!=null)
                done=reservations.removeReservation(customer.getCustomerId(),vehicle.getVehicleId(),vehicles)!=null;
        }
        else if("PROCESS_RESERVATION".equals(type)){
            Customer customer=operation.getCustomer();
            Vehicle vehicle=operation.getVehicle();
            if(customer!=null&&vehicle!=null){
                done=vehicles.changeVehicleStatus(vehicle.getVehicleId(),operation.getOldStatus());
                if(done){
                    vehicle.setReservedCustomerId(operation.getOldReservedCustomerId());
                    done=reservations.returnReservationToFront(customer,vehicle.getVehicleId(),vehicles);
                }
            }
        }
        else if("CREATE_SERVICE_REQUEST".equals(type)){
            ServiceRequest request=operation.getServiceRequest();
            if(request!=null)
                done=maintenance.removeServiceRequest(request.getRequestId())!=null;
        }
        else if("PROCESS_SERVICE_REQUEST".equals(type)){
            ServiceRequest request=operation.getServiceRequest();
            Vehicle vehicle=operation.getVehicle();
            if(request!=null&&vehicle!=null){
                done=maintenance.returnRequestToPendingFront(request);
                if(done)
                    done=vehicles.changeVehicleStatus(vehicle.getVehicleId(),operation.getOldStatus());
            }
        }
        else if("COMPLETE_SERVICE".equals(type)){
            ServiceRequest request=operation.getServiceRequest();
            Service service=operation.getService();
            if(request!=null&&service!=null)
                done=maintenance.undoCompleteService(service,request,vehicles);
        }
        else if("CREATE_TRANSACTION".equals(type)){
            Transaction transaction=operation.getTransaction();
            if(transaction!=null){
                done=transactions.removeTransaction(transaction.getTransactionId())!=null;
                if(done&&transaction.getTransactionType().equals("SALE")){
                    Vehicle vehicle=operation.getVehicle();
                    Queue<Customer> oldReservationQueue=operation.getOldReservationQueue();
                    if(vehicle!=null){
                        vehicles.changeVehicleStatus(vehicle.getVehicleId(),operation.getOldStatus());
                        vehicle.setReservedCustomerId(operation.getOldReservedCustomerId());
                        vehicle.getReservationQueue().clear();
                        if(oldReservationQueue!=null){
                            int size=oldReservationQueue.size();
                            for(int i=0;i<size;i++){
                                Customer customer=oldReservationQueue.dequeue();
                                vehicle.getReservationQueue().enqueue(customer);
                                oldReservationQueue.enqueue(customer);
                            }
                        }
                    }
                }
            }
        }

        if(done)
            redoStack.push(operation);
        else
            undoStack.push(operation);
        return done;
    }

    // Time Complexity: O(n)
    public boolean redo(CustomerManager customers,VehicleManager vehicles,CustomerWaitingManager waiting,VehicleReservationManager reservations,MaintenanceManager maintenance,TransactionManager transactions){
        if(redoStack.isEmpty())
            return false;
        UndoRedoOperation operation=redoStack.pop();
        String type=operation.getOperationType();
        boolean done=false;

        if("ADD_CUSTOMER".equals(type)){
            Customer customer=operation.getCustomer();
            if(customer!=null)
                done=customers.addCustomer(customer);
        }
        else if("REMOVE_CUSTOMER".equals(type)){
            Customer customer=operation.getCustomer();
            if(customer!=null)
                done=customers.removeCustomer(customer.getCustomerId(),waiting,maintenance,transactions,vehicles)!=null;
        }
        else if("UPDATE_CUSTOMER".equals(type)){
            Customer customer=operation.getCustomer();
            if(customer!=null)
                done=customers.updateCustomer(customer.getCustomerId(),operation.getNewName(),operation.getNewPhone(),operation.getNewAddress());
        }
        else if("ADD_VEHICLE".equals(type)){
            Vehicle vehicle=operation.getVehicle();
            if(vehicle!=null)
                done=vehicles.addVehicle(vehicle);
        }
        else if("REMOVE_VEHICLE".equals(type)){
            Vehicle vehicle=operation.getVehicle();
            if(vehicle!=null)
                done=vehicles.removeVehicle(vehicle.getVehicleId(),maintenance,transactions)!=null;
        }
        else if("UPDATE_VEHICLE".equals(type)){
            Vehicle vehicle=operation.getVehicle();
            Vehicle newVehicle=operation.getNewVehicle();
            if(vehicle!=null&&newVehicle!=null)
                done=vehicles.updateVehicle(
                    vehicle.getVehicleId(),
                    newVehicle.getMake(),
                    newVehicle.getModel(),
                    String.valueOf(newVehicle.getYear()),
                    String.valueOf(newVehicle.getPrice()),
                    newVehicle.getColor()
                );
        }
        else if("CHANGE_STATUS".equals(type)){
            Vehicle vehicle=operation.getVehicle();
            if(vehicle!=null){
                done=vehicles.changeVehicleStatus(vehicle.getVehicleId(),operation.getNewStatus());
                if(done)
                    vehicle.setReservedCustomerId(operation.getNewReservedCustomerId());
            }
        }
        else if("APPLY_DISCOUNT".equals(type)){
            Transaction transaction=operation.getTransaction();
            if(transaction!=null){
                try{
                    transaction.setAmount(operation.getNewAmount());
                    done=true;
                }
                catch(Exception ex){
                    done=false;
                }
            }
        }
        else if("ADD_WAITING_CUSTOMER".equals(type)){
            Customer customer=operation.getCustomer();
            if(customer!=null)
                done=waiting.addCustomer(customer.getCustomerId(),customers);
        }
        else if("SERVE_WAITING_CUSTOMER".equals(type)){
            Customer customer=operation.getCustomer();
            if(customer!=null){
                Customer served=waiting.serveNextCustomer();
                done=served!=null&&served.getCustomerId()==customer.getCustomerId();
            }
        }
        else if("ADD_RESERVATION".equals(type)){
            Customer customer=operation.getCustomer();
            Vehicle vehicle=operation.getVehicle();
            if(customer!=null&&vehicle!=null)
                done=reservations.requestVehicle(customer.getCustomerId(),vehicle.getVehicleId(),customers,vehicles);
        }
        else if("PROCESS_RESERVATION".equals(type)){
            Customer customer=operation.getCustomer();
            Vehicle vehicle=operation.getVehicle();
            if(customer!=null&&vehicle!=null){
                Customer processed=reservations.processNextReservation(vehicle.getVehicleId(),vehicles);
                done=processed!=null&&processed.getCustomerId()==customer.getCustomerId();
            }
        }
        else if("CREATE_SERVICE_REQUEST".equals(type)){
            ServiceRequest request=operation.getServiceRequest();
            if(request!=null)
                done=maintenance.createServiceRequest(request,customers,vehicles);
        }
        else if("PROCESS_SERVICE_REQUEST".equals(type)){
            ServiceRequest request=operation.getServiceRequest();
            if(request!=null){
                ServiceRequest processed=maintenance.processNextRequest(vehicles);
                done=processed!=null&&processed.getRequestId()==request.getRequestId();
            }
        }
        else if("COMPLETE_SERVICE".equals(type)){
            ServiceRequest request=operation.getServiceRequest();
            Service service=operation.getService();
            if(request!=null&&service!=null)
                done=maintenance.completeService(request.getRequestId(),service.getServiceId(),service.getDate(),service.getCost(),vehicles)!=null;
        }
        else if("CREATE_TRANSACTION".equals(type)){
            Transaction transaction=operation.getTransaction();
            if(transaction!=null){
                done=transactions.addTransaction(transaction);
                if(done&&transaction.getTransactionType().equals("SALE")){
                    Vehicle vehicle=operation.getVehicle();
                    if(vehicle!=null){
                        vehicle.getReservationQueue().clear();
                        done=vehicles.changeVehicleStatus(vehicle.getVehicleId(),"SOLD");
                    }
                }
            }
        }

        if(done)
            undoStack.push(operation);
        else
            redoStack.push(operation);
        return done;
    }

    // Time Complexity: O(1)
    public void clear(){
        undoStack.clear();
        redoStack.clear();
    }
}