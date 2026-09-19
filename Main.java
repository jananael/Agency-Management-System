package phaseTwo;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application{
    private CustomerManager customerManager=new CustomerManager();
    private VehicleManager vehicleManager=new VehicleManager();
    private CustomerWaitingManager waitingManager=new CustomerWaitingManager();
    private MaintenanceManager maintenanceManager=new MaintenanceManager();
    private VehicleReservationManager reservationManager=new VehicleReservationManager();
    private TransactionManager transactionManager=new TransactionManager();
    private UndoRedoManager undoRedoManager=new UndoRedoManager();
    private CustomerTab customerTab;
    private VehicleTab vehicleTab;
    private MaintenanceTab maintenanceTab;
    private CustomerWaitingTab waitingTab;
    private ReservationTab reservationTab;
    private TransactionTab transactionTab;
    private UndoRedoTab undoRedoTab;
    private TabPane tabPane;
    private BorderPane mainPane;
    private FileMenu fileMenu;
    
    @Override
    public void start(Stage stage){
        customerTab=new CustomerTab(customerManager);
        vehicleTab=new VehicleTab(vehicleManager);
        maintenanceTab=new MaintenanceTab(maintenanceManager);
        waitingTab=new CustomerWaitingTab(waitingManager);
        reservationTab=new ReservationTab(reservationManager);
        transactionTab=new TransactionTab(transactionManager);
        undoRedoTab=new UndoRedoTab(undoRedoManager);

        tabPane=new TabPane();
        tabPane.getTabs().addAll(customerTab,vehicleTab,waitingTab,maintenanceTab,reservationTab,transactionTab);        fileMenu=new FileMenu();
        mainPane=new BorderPane();
        mainPane.setTop(fileMenu);
        mainPane.setCenter(tabPane);
        mainPane.setBottom(undoRedoTab.getContent());
        
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files","*.txt"));
        
        //CustomerTab Events

        customerTab.getBtAdd().setOnAction(e->{
            try{
                int customerId=Integer.parseInt(customerTab.getTfCustomerId().getText());
                String name=customerTab.getTfName().getText();
                String phone=customerTab.getTfPhone().getText();
                String address=customerTab.getTfAddress().getText();
                Customer customer=new Customer(customerId,name,phone,address);
                if(customerManager.addCustomer(customer)){
                    undoRedoManager.recordOperation(new UndoRedoOperation("ADD_CUSTOMER",customer));
                    customerTab.getCustomerTable().setItems(customerManager.displayAllCustomers());
                    customerTab.getCustomerTable().refresh();
                    customerTab.getTfCustomerId().setText(String.valueOf(customerManager.generateCustomerId()));
                    undoRedoTab.updateSizes();
                    customerTab.getMessage().setText("Customer added");
                }
                else{
                    customerTab.getMessage().setText("Customer already exists");
                }
            }
            catch(Exception ex){
                customerTab.getMessage().setText("Invalid input");
            }
        });

        customerTab.getBtUpdate().setOnAction(e->{
            try{
                int customerId=Integer.parseInt(customerTab.getTfCustomerId().getText());
                Customer customer=customerManager.searchCustomer(customerId);
                if(customer==null){
                    customerTab.getMessage().setText("Customer not found");
                    return;
                }
                String oldName=customer.getName();
                String oldPhone=customer.getPhone();
                String oldAddress=customer.getAddress();
                String name=customerTab.getTfName().getText();
                String phone=customerTab.getTfPhone().getText();
                String address=customerTab.getTfAddress().getText();
                if(customerManager.updateCustomer(customerId,name,phone,address)){
                	undoRedoManager.recordOperation(new UndoRedoOperation( "UPDATE_CUSTOMER", customer, oldName,oldPhone,oldAddress,customer.getName(),customer.getPhone(),customer.getAddress()));
                    customerTab.getCustomerTable().setItems(customerManager.displayAllCustomers());
                    customerTab.getTfCustomerId().setText(String.valueOf(customerManager.generateCustomerId()));
                    undoRedoTab.updateSizes();
                    customerTab.getMessage().setText("Customer updated");
                }
                else{
                    customerTab.getMessage().setText("Customer cannot be updated");
                }
            }
            catch(Exception ex){
                customerTab.getMessage().setText("Invalid input");
            }
        });
        

        customerTab.getBtRemove().setOnAction(e->{
            try{
                int customerId=Integer.parseInt(customerTab.getTfCustomerId().getText());
                Customer customer=customerManager.removeCustomer(customerId,waitingManager,maintenanceManager,transactionManager,vehicleManager);

                if(customer!=null){
                    undoRedoManager.recordOperation(new UndoRedoOperation("REMOVE_CUSTOMER",customer));
                    customerTab.getCustomerTable().setItems(customerManager.displayAllCustomers());
                    customerTab.getTfCustomerId().setText(String.valueOf(customerManager.generateCustomerId()));
                    undoRedoTab.updateSizes();
                    customerTab.getMessage().setText("Customer removed");
                }
                
                else{
                    customerTab.getMessage().setText("Customer cannot be removed");
                }
            }
            catch(Exception ex){
                customerTab.getMessage().setText("Invalid input");
            }
        });

        customerTab.getBtSearchId().setOnAction(e->{
            try{
                int customerId=Integer.parseInt(customerTab.getTfCustomerId().getText());
                Customer customer=customerManager.searchCustomer(customerId);
                customerTab.getCustomerTable().getItems().clear();

                if(customer!=null){
                    customerTab.getCustomerTable().getItems().add(customer);
                    customerTab.getMessage().setText("Customer found");
                }
                else{
                    customerTab.getMessage().setText("Customer not found");
                }
            }
            catch(Exception ex){
                customerTab.getMessage().setText("Invalid input");
            }
        });

        customerTab.getBtSearchName().setOnAction(e->{
            String name=customerTab.getTfName().getText();

            if(name==null||name.trim().isEmpty()){
                customerTab.getMessage().setText("Enter customer name");
            }
            else{
                customerTab.getCustomerTable().setItems(customerManager.searchCustomerName(name));

                if(customerTab.getCustomerTable().getItems().isEmpty())
                    customerTab.getMessage().setText("Customer not found");
                else
                    customerTab.getMessage().setText("Customer found");
            }
        });

        customerTab.getBtDisplayAll().setOnAction(e->{
            customerTab.getCustomerTable().setItems(customerManager.displayAllCustomers());
            customerTab.getMessage().setText("All customers");
        });

        customerTab.getBtClear().setOnAction(e->{
        	customerTab.getTfCustomerId().setText(String.valueOf(customerManager.generateCustomerId()));
        	customerTab.getTfName().clear();
            customerTab.getTfPhone().clear();
            customerTab.getTfAddress().clear();
            customerTab.getMessage().setText("");
        });


      //VehicleTab Events
        
        
        vehicleTab.getBtHeight().setOnAction(e->{
            vehicleTab.getMessage().setText("Tree Height: "+vehicleManager.getTreeHeight());
        });
        
        vehicleTab.getBtAdd().setOnAction(e->{
            try{
                int vehicleId=Integer.parseInt(vehicleTab.getTfVehicleId().getText());
                String make=vehicleTab.getTfMake().getText();
                String model=vehicleTab.getTfModel().getText();
                int year=Integer.parseInt(vehicleTab.getTfYear().getText());
                double price=Double.parseDouble(vehicleTab.getTfPrice().getText());
                String color=vehicleTab.getTfColor().getText();
                String status=vehicleTab.getCbStatus().getValue();
                Vehicle vehicle=new Vehicle(vehicleId,make,model,year,price,color,status);
                if(vehicleManager.addVehicle(vehicle)){
                    undoRedoManager.recordOperation(new UndoRedoOperation("ADD_VEHICLE",vehicle));
                    vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                    vehicleTab.getVehicleTable().refresh();
                    undoRedoTab.updateSizes();
                    vehicleTab.getMessage().setText("Vehicle added");
                }
                else{
                    vehicleTab.getMessage().setText("Vehicle already exists");
                }
            }
            catch(Exception ex){
                vehicleTab.getMessage().setText("Invalid input");
            }
        });
        
        vehicleTab.getBtUpdate().setOnAction(e->{
            try{
                int vehicleId=Integer.parseInt(vehicleTab.getTfVehicleId().getText());
                Vehicle vehicle=vehicleManager.searchVehicle(vehicleId);
                if(vehicle==null){
                    vehicleTab.getMessage().setText("Vehicle not found");
                    return;
                }
                Vehicle oldVehicle=new Vehicle(vehicle.getVehicleId(),vehicle.getMake(),vehicle.getModel(),vehicle.getYear(),vehicle.getPrice(),vehicle.getColor(),vehicle.getStatus());
                boolean updated=vehicleManager.updateVehicle(vehicleId,vehicleTab.getTfMake().getText(),vehicleTab.getTfModel().getText(),vehicleTab.getTfYear().getText(),vehicleTab.getTfPrice().getText(),vehicleTab.getTfColor().getText());
                if(updated){
                    Vehicle newVehicle=new Vehicle(vehicle.getVehicleId(),vehicle.getMake(),vehicle.getModel(),vehicle.getYear(),vehicle.getPrice(),vehicle.getColor(),vehicle.getStatus());
                    undoRedoManager.recordOperation(new UndoRedoOperation("UPDATE_VEHICLE",vehicle,oldVehicle,newVehicle));
                    vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                    vehicleTab.getVehicleTable().refresh();
                    undoRedoTab.updateSizes();
                    vehicleTab.getMessage().setText("Vehicle updated");
                }
                else{
                    vehicleTab.getMessage().setText("Vehicle cannot be updated");
                }
            }
            catch(Exception ex){
                vehicleTab.getMessage().setText("Invalid input");
            }
        });
        
        vehicleTab.getBtRemove().setOnAction(e->{
            try{
                int vehicleId=Integer.parseInt(vehicleTab.getTfVehicleId().getText());
                Vehicle vehicle=vehicleManager.removeVehicle(vehicleId,maintenanceManager,transactionManager);
                if(vehicle!=null){
                    undoRedoManager.recordOperation(new UndoRedoOperation("REMOVE_VEHICLE",vehicle));
                    vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                    undoRedoTab.updateSizes();
                    vehicleTab.getMessage().setText("Vehicle removed");
                }
                else{
                    vehicleTab.getMessage().setText("Vehicle cannot be removed");
                }
            }
            catch(Exception ex){
                vehicleTab.getMessage().setText("Invalid input");
            }
        });
        vehicleTab.getBtSearchId().setOnAction(e->{
            try{
                int vehicleId=Integer.parseInt(vehicleTab.getTfVehicleId().getText());
                Vehicle vehicle=vehicleManager.searchVehicle(vehicleId);
                vehicleTab.getVehicleTable().getItems().clear();
                if(vehicle!=null){
                    vehicleTab.getVehicleTable().getItems().add(vehicle);
                    vehicleTab.getMessage().setText("Vehicle found");
                }
                else{
                    vehicleTab.getMessage().setText("Vehicle not found");
                }
            }
            catch(Exception ex){
                vehicleTab.getMessage().setText("Invalid input");
            }
        });
        vehicleTab.getBtSearchMake().setOnAction(e->{
            String make=vehicleTab.getTfMake().getText();
            if(make==null||make.trim().isEmpty()){
                vehicleTab.getMessage().setText("Enter vehicle make");
            }
            else{
                vehicleTab.getVehicleTable().setItems(vehicleManager.searchVehicleMake(make));
                if(vehicleTab.getVehicleTable().getItems().isEmpty())
                    vehicleTab.getMessage().setText("No vehicles found");
                else
                    vehicleTab.getMessage().setText("Vehicle found");
            }
        });
        
        vehicleTab.getBtChangeStatus().setOnAction(e->{
            try{
                int vehicleId=Integer.parseInt(vehicleTab.getTfVehicleId().getText());
                Vehicle vehicle=vehicleManager.searchVehicle(vehicleId);
                if(vehicle==null){
                    vehicleTab.getMessage().setText("Vehicle not found");
                    return;
                }
                String oldStatus=vehicle.getStatus();
                int oldReservedCustomerId=vehicle.getReservedCustomerId();
                String newStatus=vehicleTab.getCbStatus().getValue();
                if("SOLD".equals(newStatus)){
                    vehicleTab.getMessage().setText("Use Mark Sold button");
                    return;
                }
                if(vehicleManager.changeVehicleStatus(vehicleId,newStatus)){
                    int newReservedCustomerId=vehicle.getReservedCustomerId();
                    undoRedoManager.recordOperation(new UndoRedoOperation("CHANGE_STATUS",vehicle,oldStatus,vehicle.getStatus(),oldReservedCustomerId,newReservedCustomerId));
                    vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                    undoRedoTab.updateSizes();
                    vehicleTab.getMessage().setText("Status changed");
                }
                else{
                    vehicleTab.getMessage().setText("Invalid status");
                }
            }
            catch(Exception ex){
                vehicleTab.getMessage().setText("Invalid input");
            }
        });
        
        
        vehicleTab.getBtMarkSold().setOnAction(e->{
            try{
                int vehicleId=Integer.parseInt(vehicleTab.getTfVehicleId().getText());
                Vehicle vehicle=vehicleManager.searchVehicle(vehicleId);
                if(vehicle==null){
                    vehicleTab.getMessage().setText("Vehicle not found");
                    return;
                }
                String oldStatus=vehicle.getStatus();
                int oldReservedCustomerId=vehicle.getReservedCustomerId();
                if(vehicleManager.markAsSold(vehicleId)){
                    int newReservedCustomerId=vehicle.getReservedCustomerId();
                    undoRedoManager.recordOperation(new UndoRedoOperation("CHANGE_STATUS",vehicle,oldStatus,vehicle.getStatus(),oldReservedCustomerId,newReservedCustomerId));
                    vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                    undoRedoTab.updateSizes();
                    vehicleTab.getMessage().setText("Vehicle marked as sold");
                }
                else{
                    vehicleTab.getMessage().setText("Vehicle cannot be marked as sold");
                }
            }
            catch(Exception ex){
                vehicleTab.getMessage().setText("Invalid input");
            }
        });
        
        
        vehicleTab.getBtAscending().setOnAction(e->{
            vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
            vehicleTab.getMessage().setText("Vehicles ascending");
        });
        vehicleTab.getBtDescending().setOnAction(e->{
            vehicleTab.getVehicleTable().setItems(vehicleManager.displayVehiclesDescending());
            vehicleTab.getMessage().setText("Vehicles descending");
        });
        vehicleTab.getBtClear().setOnAction(e->{
            vehicleTab.getTfVehicleId().clear();
            vehicleTab.getTfMake().clear();
            vehicleTab.getTfModel().clear();
            vehicleTab.getTfYear().clear();
            vehicleTab.getTfPrice().clear();
            vehicleTab.getTfColor().clear();
            vehicleTab.getCbStatus().setValue(null);
            vehicleTab.getMessage().setText("");
        });
        
        
      //MaintenanceTab Events
        
        maintenanceTab.getBtCreateRequest().setOnAction(e->{
            try{
                int requestId=Integer.parseInt(maintenanceTab.getTfRequestId().getText());
                int vehicleId=Integer.parseInt(maintenanceTab.getTfVehicleId().getText());
                int customerId=Integer.parseInt(maintenanceTab.getTfCustomerId().getText());
                String serviceType=maintenanceTab.getTfServiceType().getText();
                if(maintenanceTab.getDpRequestDate().getValue()==null){
                    maintenanceTab.getMessage().setText("Select request date");
                    return;
                }
                String date=maintenanceTab.getDpRequestDate().getValue().toString();
                ServiceRequest request=new ServiceRequest(requestId,vehicleId,customerId,serviceType,date,"PENDING");
                if(maintenanceManager.createServiceRequest(request,customerManager,vehicleManager)){
                    undoRedoManager.recordOperation(new UndoRedoOperation("CREATE_SERVICE_REQUEST",request));
                    maintenanceTab.showRequestTable();
                    maintenanceTab.getRequestTable().setItems(maintenanceManager.displayPendingRequests());
                    maintenanceTab.getTfRequestId().setText(String.valueOf(maintenanceManager.generateRequestId()));
                    undoRedoTab.updateSizes();
                    maintenanceTab.getMessage().setText("Request added");
                }
                else{
                    maintenanceTab.getMessage().setText("Request cannot be added");
                }
            }
            catch(Exception ex){
                maintenanceTab.getMessage().setText("Invalid input");
            }
        });

        maintenanceTab.getBtPending().setOnAction(e->{
            maintenanceTab.showRequestTable();
            maintenanceTab.getRequestTable().setItems(maintenanceManager.displayPendingRequests());
            maintenanceTab.getMessage().setText("Pending requests");
        });

        maintenanceTab.getBtProcessing().setOnAction(e->{
            maintenanceTab.showRequestTable();
            maintenanceTab.getRequestTable().setItems(maintenanceManager.displayProcessingRequests());
            maintenanceTab.getMessage().setText("Processing requests");
        });

        maintenanceTab.getBtAllRequests().setOnAction(e->{
            maintenanceTab.showRequestTable();
            maintenanceTab.getRequestTable().setItems(maintenanceManager.displayAllRequests());
            maintenanceTab.getMessage().setText("All requests");
        });

        maintenanceTab.getBtProcessNext().setOnAction(e->{
            ServiceRequest request=maintenanceManager.processNextRequest(vehicleManager);
            if(request!=null){
                Vehicle vehicle=vehicleManager.searchVehicle(request.getVehicleId());
                undoRedoManager.recordOperation(new UndoRedoOperation("PROCESS_SERVICE_REQUEST",request,vehicle,"AVAILABLE",vehicle.getStatus()));
                maintenanceTab.showRequestTable();
                maintenanceTab.getRequestTable().setItems(maintenanceManager.displayProcessingRequests());
                maintenanceTab.getRequestTable().refresh();
                vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                vehicleTab.getVehicleTable().refresh();
                undoRedoTab.updateSizes();
                maintenanceTab.getMessage().setText("Request is processing");
            }
            else{
                maintenanceTab.getMessage().setText("No request processed");
            }
        });

        maintenanceTab.getBtCompleteService().setOnAction(e->{
            try{
                int requestId=Integer.parseInt(maintenanceTab.getTfRequestId().getText());
                int serviceId=Integer.parseInt(maintenanceTab.getTfServiceId().getText());
                double cost=Double.parseDouble(maintenanceTab.getTfCost().getText());

                if(maintenanceTab.getDpServiceDate().getValue()==null){
                    maintenanceTab.getMessage().setText("Select service date");
                    return;
                }

                String date=maintenanceTab.getDpServiceDate().getValue().toString();
                ServiceRequest request=maintenanceManager.searchRequest(requestId);

                if(request==null){
                    maintenanceTab.getMessage().setText("Request not found");
                    return;
                }

                Vehicle vehicle=vehicleManager.searchVehicle(request.getVehicleId());
                Service service=maintenanceManager.completeService(requestId,serviceId,date,cost,vehicleManager);

                if(service!=null){
                    undoRedoManager.recordOperation(new UndoRedoOperation("COMPLETE_SERVICE",request,service,vehicle));
                    maintenanceTab.getRequestTable().setItems(maintenanceManager.displayProcessingRequests());
                    maintenanceTab.getServiceTable().setItems(maintenanceManager.displayCompletedServices());
                    maintenanceTab.showServiceTable();
                    vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                    vehicleTab.getVehicleTable().refresh();
                    undoRedoTab.updateSizes();
                    maintenanceTab.getMessage().setText("Service completed");

                    ButtonType goToPayment=new ButtonType("Go to Payment");
                    ButtonType later=new ButtonType("Later");

                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Service Payment");
                    alert.setHeaderText("Service completed successfully");
                    alert.setContentText("Do you want to go to the payment page?");
                    alert.getButtonTypes().setAll(goToPayment,later);
                    alert.showAndWait();

                    if(alert.getResult()==goToPayment){
                        transactionTab.getTfTransactionId().setText(String.valueOf(transactionManager.generateTransactionId()));
                        transactionTab.getTfServiceId().setText(String.valueOf(service.getServiceId()));
                        transactionTab.getTfCustomerId().setText(String.valueOf(service.getCustomerId()));
                        transactionTab.getTfVehicleId().setText(String.valueOf(service.getVehicleId()));
                        transactionTab.getTfAmount().clear();
                        transactionTab.getDpDate().setValue(null);
                        transactionTab.getMessage().setText("Enter the amount and date, then choose Service Payment");
                        tabPane.getSelectionModel().select(transactionTab);
                    }
                }
                else{
                    maintenanceTab.getMessage().setText("Service cannot be completed");
                }
            }
            catch(Exception ex){
                maintenanceTab.getMessage().setText("Invalid input");
            }
        });

        maintenanceTab.getBtCompletedServices().setOnAction(e->{
            maintenanceTab.showServiceTable();
            maintenanceTab.getServiceTable().setItems(maintenanceManager.displayCompletedServices());
            maintenanceTab.getMessage().setText("Completed services");
        });

        maintenanceTab.getBtServiceHistory().setOnAction(e->{
            maintenanceTab.showServiceTable();
            maintenanceTab.getServiceTable().setItems(maintenanceManager.displayServiceHistory());
            maintenanceTab.getMessage().setText("Service history");
        });

        maintenanceTab.getBtVehicleHistory().setOnAction(e->{
            try{
                int vehicleId=Integer.parseInt(maintenanceTab.getTfVehicleId().getText());
                maintenanceTab.showServiceTable();
                maintenanceTab.getServiceTable().setItems(maintenanceManager.displayVehicleServiceHistory(vehicleId,vehicleManager));
                if(maintenanceTab.getServiceTable().getItems().isEmpty())
                    maintenanceTab.getMessage().setText("No services found");
                else
                    maintenanceTab.getMessage().setText("Vehicle service history");
            }
            catch(Exception ex){
                maintenanceTab.getMessage().setText("Invalid input");
            }
        });

        maintenanceTab.getBtClear().setOnAction(e->{
        	maintenanceTab.getTfRequestId().setText(String.valueOf(maintenanceManager.generateRequestId())); 
        	maintenanceTab.getTfVehicleId().clear();
            maintenanceTab.getTfCustomerId().clear();
            maintenanceTab.getTfServiceType().clear();
            maintenanceTab.getDpRequestDate().setValue(null);
            maintenanceTab.getTfServiceId().clear();
            maintenanceTab.getDpServiceDate().setValue(null);
            maintenanceTab.getTfCost().clear();
            maintenanceTab.getMessage().setText("");
        });
        
        
        //CustomerWaitingTab Events
        
        waitingTab.getBtAdd().setOnAction(e->{
            try{
                int customerId=Integer.parseInt(waitingTab.getTfCustomerId().getText());
                Customer customer=customerManager.searchCustomer(customerId);
                if(waitingManager.addCustomer(customerId,customerManager)){
                    undoRedoManager.recordOperation(new UndoRedoOperation("ADD_WAITING_CUSTOMER",customer));
                    waitingTab.getCustomerTable().setItems(waitingManager.displayWaitingQueue());
                    waitingTab.getCountLabel().setText("Waiting Customers: "+waitingManager.getSize());
                    undoRedoTab.updateSizes();
                    waitingTab.getMessage().setText("Customer added to queue");
                }
                else{
                    waitingTab.getMessage().setText("Customer cannot be added");
                }
            }
            catch(Exception ex){
                waitingTab.getMessage().setText("Invalid input");
            }
        });
        
        waitingTab.getBtServeNext().setOnAction(e->{
            Customer nextCustomer=waitingManager.getNextCustomer();
            if(nextCustomer==null){
                waitingTab.getMessage().setText("Queue is empty");
                return;
            }

            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Customer Service");
            alert.setHeaderText("Serving customer: "+nextCustomer.getName());
            alert.setContentText("Select the requested service");

            ButtonType maintenanceButton=new ButtonType("Maintenance");
            ButtonType saleButton=new ButtonType("Sale");
            ButtonType reservationButton=new ButtonType("Vehicle Reservation");

            alert.getButtonTypes().setAll(maintenanceButton,saleButton,reservationButton,ButtonType.CANCEL);

            ButtonType selectedButton=alert.showAndWait().orElse(ButtonType.CANCEL);

            if(selectedButton==ButtonType.CANCEL)
                return;

            Customer customer=waitingManager.serveNextCustomer();

            if(customer!=null){
                undoRedoManager.recordOperation(new UndoRedoOperation("SERVE_WAITING_CUSTOMER",customer));
                waitingTab.getCustomerTable().setItems(waitingManager.displayWaitingQueue());
                waitingTab.getCountLabel().setText("Waiting Customers: "+waitingManager.getSize());
                undoRedoTab.updateSizes();
                waitingTab.getMessage().setText("Serving customer: "+customer.getName());

                if(selectedButton==maintenanceButton){
                    maintenanceTab.getTfCustomerId().setText(String.valueOf(customer.getCustomerId()));
                    tabPane.getSelectionModel().select(maintenanceTab);
                }
                else if(selectedButton==saleButton){
                    transactionTab.getTfCustomerId().setText(String.valueOf(customer.getCustomerId()));
                    tabPane.getSelectionModel().select(transactionTab);
                }
                else if(selectedButton==reservationButton){
                    reservationTab.getTfCustomerId().setText(String.valueOf(customer.getCustomerId()));
                    tabPane.getSelectionModel().select(reservationTab);
                }
            }
        });
        

        waitingTab.getBtCount().setOnAction(e->{
            waitingTab.getCountLabel().setText("Waiting Customers: "+waitingManager.getSize());
            waitingTab.getMessage().setText("Count updated");
        });

        waitingTab.getBtClear().setOnAction(e->{
            waitingTab.getTfCustomerId().clear();
            waitingTab.getMessage().setText("");
        });

        //ReservationTab Events

        reservationTab.getBtAdd().setOnAction(e->{
            try{
                int customerId=Integer.parseInt(reservationTab.getTfCustomerId().getText());
                int vehicleId=Integer.parseInt(reservationTab.getTfVehicleId().getText());
                Customer customer=customerManager.searchCustomer(customerId);
                Vehicle vehicle=vehicleManager.searchVehicle(vehicleId);
                if(reservationManager.requestVehicle(customerId,vehicleId,customerManager,vehicleManager)){
                    undoRedoManager.recordOperation(new UndoRedoOperation("ADD_RESERVATION",customer,vehicle));
                    reservationTab.getCustomerTable().setItems(reservationManager.displayReservations(vehicleId,vehicleManager));
                    reservationTab.getCountLabel().setText("Reservations: "+reservationManager.getReservationCount(vehicleId,vehicleManager));
                    undoRedoTab.updateSizes();
                    reservationTab.getMessage().setText("Reservation added");
                }
                else{
                    reservationTab.getMessage().setText("Reservation cannot be added");
                }
            }
            catch(Exception ex){
                reservationTab.getMessage().setText("Invalid input");
            }
        });
        
        reservationTab.getBtDisplay().setOnAction(e->{
            try{
                int vehicleId=Integer.parseInt(reservationTab.getTfVehicleId().getText());
                reservationTab.getCustomerTable().setItems(reservationManager.displayReservations(vehicleId,vehicleManager));
                reservationTab.getCountLabel().setText("Reservations: "+reservationManager.getReservationCount(vehicleId,vehicleManager));

                if(reservationTab.getCustomerTable().getItems().isEmpty())
                    reservationTab.getMessage().setText("No reservations");
                else
                    reservationTab.getMessage().setText("Reservation queue");
            }
            catch(Exception ex){
                reservationTab.getMessage().setText("Invalid input");
            }
        });

        reservationTab.getBtProcessNext().setOnAction(e->{
            try{
                int vehicleId=Integer.parseInt(reservationTab.getTfVehicleId().getText());
                Vehicle vehicle=vehicleManager.searchVehicle(vehicleId);

                if(vehicle==null){
                    reservationTab.getMessage().setText("Vehicle not found");
                    return;
                }

                String oldStatus=vehicle.getStatus();
                int oldReservedCustomerId=vehicle.getReservedCustomerId();
                Customer customer=reservationManager.processNextReservation(vehicleId,vehicleManager);

                if(customer!=null){
                    undoRedoManager.recordOperation(new UndoRedoOperation("PROCESS_RESERVATION",customer,vehicle,oldStatus,vehicle.getStatus(),oldReservedCustomerId,vehicle.getReservedCustomerId()));
                    reservationTab.getCustomerTable().setItems(reservationManager.displayReservations(vehicleId,vehicleManager));
                    reservationTab.getCountLabel().setText("Reservations: "+reservationManager.getReservationCount(vehicleId,vehicleManager));
                    vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                    vehicleTab.getVehicleTable().refresh();
                    undoRedoTab.updateSizes();
                    reservationTab.getMessage().setText("Reservation for: "+customer.getName());

                    ButtonType goToTransaction=new ButtonType("Go to Transaction");
                    ButtonType later=new ButtonType("Later");

                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Reservation Processed");
                    alert.setHeaderText("Vehicle reserved successfully");
                    alert.setContentText("Do you want to go to the transaction page?");
                    alert.getButtonTypes().setAll(goToTransaction,later);
                    alert.showAndWait();

                    if(alert.getResult()==goToTransaction){
                        transactionTab.getTfTransactionId().setText(String.valueOf(transactionManager.generateTransactionId()));
                        transactionTab.getTfCustomerId().setText(String.valueOf(customer.getCustomerId()));
                        transactionTab.getTfVehicleId().setText(String.valueOf(vehicleId));
                        transactionTab.getTfServiceId().clear();
                        transactionTab.getTfAmount().clear();
                        transactionTab.getDpDate().setValue(null);
                        transactionTab.getMessage().setText("Enter the amount and date, then choose Deposit or Sale");
                        tabPane.getSelectionModel().select(transactionTab);
                    }
                }
                else{
                    reservationTab.getMessage().setText("Reservation cannot be processed");
                }
            }
            catch(Exception ex){
                reservationTab.getMessage().setText("Invalid input");
            }
        });

        reservationTab.getBtCount().setOnAction(e->{
            try{
                int vehicleId=Integer.parseInt(reservationTab.getTfVehicleId().getText());
                int count=reservationManager.getReservationCount(vehicleId,vehicleManager);
                reservationTab.getCountLabel().setText("Reservations: "+count);
                reservationTab.getMessage().setText("Count updated");
            }
            catch(Exception ex){
                reservationTab.getMessage().setText("Invalid input");
            }
        });

        reservationTab.getBtClear().setOnAction(e->{
            reservationTab.getTfCustomerId().clear();
            reservationTab.getTfVehicleId().clear();
            reservationTab.getMessage().setText("");
        });

        //TransactionTab Events
        transactionTab.getBtSale().setOnAction(e->{
            try{
                int transactionId=Integer.parseInt(transactionTab.getTfTransactionId().getText());
                int customerId=Integer.parseInt(transactionTab.getTfCustomerId().getText());
                int vehicleId=Integer.parseInt(transactionTab.getTfVehicleId().getText());
                double amount=Double.parseDouble(transactionTab.getTfAmount().getText());

                if(transactionTab.getDpDate().getValue()==null){
                    transactionTab.getMessage().setText("Select date");
                    return;
                }

                String date=transactionTab.getDpDate().getValue().toString();
                Vehicle vehicle=vehicleManager.searchVehicle(vehicleId);
                String oldStatus=null;
                int oldReservedCustomerId=0;
                Queue<Customer> oldReservationQueue=new Queue<Customer>();

                if(vehicle!=null){
                    oldStatus=vehicle.getStatus();
                    oldReservedCustomerId=vehicle.getReservedCustomerId();
                    Queue<Customer> queue=vehicle.getReservationQueue();
                    int size=queue.size();

                    for(int i=0;i<size;i++){
                        Customer customer=queue.dequeue();
                        oldReservationQueue.enqueue(customer);
                        queue.enqueue(customer);
                    }
                }

                Transaction transaction=transactionManager.createSale(transactionId,customerId,vehicleId,amount,date,customerManager,vehicleManager,maintenanceManager);

                if(transaction!=null){
                    undoRedoManager.recordOperation(new UndoRedoOperation("CREATE_TRANSACTION",transaction,vehicle,oldStatus,oldReservedCustomerId,oldReservationQueue));
                    transactionTab.getTransactionTable().setItems(transactionManager.displayTransactions());
                    transactionTab.getTransactionTable().refresh();
                    vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                    vehicleTab.getVehicleTable().refresh();
                    transactionTab.getTfTransactionId().setText(String.valueOf(transactionManager.generateTransactionId()));
                    undoRedoTab.updateSizes();
                    transactionTab.getMessage().setText("Sale created");
                }
                else{
                    transactionTab.getMessage().setText("Sale cannot be created");
                }
            }
            catch(Exception ex){
                transactionTab.getMessage().setText("Invalid input");
            }
        });
        
        
        transactionTab.getBtServicePayment().setOnAction(e->{
            try{
                int transactionId=Integer.parseInt(transactionTab.getTfTransactionId().getText());
                int serviceId=Integer.parseInt(transactionTab.getTfServiceId().getText());
                double amount=Double.parseDouble(transactionTab.getTfAmount().getText());
                if(transactionTab.getDpDate().getValue()==null){
                    transactionTab.getMessage().setText("Select date");
                    return;
                }
                String date=transactionTab.getDpDate().getValue().toString();
                Transaction transaction=transactionManager.createServicePayment(transactionId,serviceId,amount,date,maintenanceManager);
                if(transaction!=null){
                    undoRedoManager.recordOperation(new UndoRedoOperation("CREATE_TRANSACTION",transaction));
                    transactionTab.getTransactionTable().setItems(transactionManager.displayTransactions());
                    transactionTab.getTfTransactionId().setText(String.valueOf(transactionManager.generateTransactionId()));
                    undoRedoTab.updateSizes();
                    transactionTab.getMessage().setText("Payment added");
                }
                else{
                    transactionTab.getMessage().setText("Payment cannot be added");
                }
            }
            catch(Exception ex){
                transactionTab.getMessage().setText("Invalid input");
            }
        });
        
        
        transactionTab.getBtDeposit().setOnAction(e->{
            try{
                int transactionId=Integer.parseInt(transactionTab.getTfTransactionId().getText());
                int customerId=Integer.parseInt(transactionTab.getTfCustomerId().getText());
                int vehicleId=Integer.parseInt(transactionTab.getTfVehicleId().getText());
                double amount=Double.parseDouble(transactionTab.getTfAmount().getText());

                if(transactionTab.getDpDate().getValue()==null){
                    transactionTab.getMessage().setText("Select date");
                    return;
                }

                String date=transactionTab.getDpDate().getValue().toString();
                Transaction transaction=transactionManager.createDeposit(transactionId,customerId,vehicleId,amount,date,customerManager,vehicleManager);

                if(transaction!=null){
                    undoRedoManager.recordOperation(new UndoRedoOperation("CREATE_TRANSACTION",transaction));
                    transactionTab.getTransactionTable().setItems(transactionManager.displayTransactions());
                    transactionTab.getTransactionTable().refresh();
                    transactionTab.getTfTransactionId().setText(String.valueOf(transactionManager.generateTransactionId()));
                    undoRedoTab.updateSizes();
                    transactionTab.getMessage().setText("Deposit added");
                }
                else{
                    transactionTab.getMessage().setText("Deposit cannot be added");
                }
            }
            catch(Exception ex){
                transactionTab.getMessage().setText("Invalid input");
            }
        });

        transactionTab.getBtRefund().setOnAction(e->{
            try{
                int transactionId=Integer.parseInt(transactionTab.getTfTransactionId().getText());
                int customerId=Integer.parseInt(transactionTab.getTfCustomerId().getText());
                int vehicleId=Integer.parseInt(transactionTab.getTfVehicleId().getText());
                double amount=Double.parseDouble(transactionTab.getTfAmount().getText());
                if(transactionTab.getDpDate().getValue()==null){
                    transactionTab.getMessage().setText("Select date");
                    return;
                }
                String date=transactionTab.getDpDate().getValue().toString();
                Transaction transaction=transactionManager.createRefund(transactionId,customerId,vehicleId,amount,date,customerManager,vehicleManager);
                if(transaction!=null){
                    undoRedoManager.recordOperation(new UndoRedoOperation("CREATE_TRANSACTION",transaction));
                    transactionTab.getTransactionTable().setItems(transactionManager.displayTransactions());
                    transactionTab.getTfTransactionId().setText(String.valueOf(transactionManager.generateTransactionId()));
                    undoRedoTab.updateSizes();
                    transactionTab.getMessage().setText("Refund added");
                }
                else{
                    transactionTab.getMessage().setText("Refund cannot be added");
                }
            }
            catch(Exception ex){
                transactionTab.getMessage().setText("Invalid input");
            }
        });

        transactionTab.getBtApplyDiscount().setOnAction(e->{
            try{
                int transactionId=Integer.parseInt(transactionTab.getTfTransactionId().getText());
                double discount=Double.parseDouble(transactionTab.getTfDiscount().getText());

                if(transactionManager.applyDiscount(transactionId,discount,undoRedoManager)){
                    transactionTab.getTransactionTable().setItems(transactionManager.displayTransactions());
                    undoRedoTab.updateSizes();
                    transactionTab.getMessage().setText("Discount applied");
                }
                else{
                    transactionTab.getMessage().setText("Discount cannot be applied");
                }
            }
            catch(Exception ex){
                transactionTab.getMessage().setText("Invalid input");
            }
        });

        transactionTab.getBtDisplayAll().setOnAction(e->{
            transactionTab.getTransactionTable().setItems(transactionManager.displayTransactions());
            transactionTab.getMessage().setText("All transactions");
        });

        transactionTab.getBtClear().setOnAction(e->{
        	transactionTab.getTfTransactionId().setText(String.valueOf(transactionManager.generateTransactionId()));
            transactionTab.getTfCustomerId().clear();
            transactionTab.getTfVehicleId().clear();
            transactionTab.getTfServiceId().clear();
            transactionTab.getTfAmount().clear();
            transactionTab.getDpDate().setValue(null);
            transactionTab.getTfDiscount().clear();
            transactionTab.getMessage().setText("");
        });

        //UndoRedoTab Events
        undoRedoTab.getBtUndo().setOnAction(e->{
            boolean done=undoRedoManager.undo(customerManager,vehicleManager,waitingManager,reservationManager,maintenanceManager,transactionManager);
            if(done){
                customerTab.getCustomerTable().setItems(customerManager.displayAllCustomers());
                customerTab.getCustomerTable().refresh();
                customerTab.getTfCustomerId().setText(String.valueOf(customerManager.generateCustomerId()));

                vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                vehicleTab.getVehicleTable().refresh();

                transactionTab.getTransactionTable().setItems(transactionManager.displayTransactions());
                transactionTab.getTransactionTable().refresh();
                transactionTab.getTfTransactionId().setText(String.valueOf(transactionManager.generateTransactionId()));

                waitingTab.getCustomerTable().setItems(waitingManager.displayWaitingQueue());
                waitingTab.getCustomerTable().refresh();
                waitingTab.getCountLabel().setText("Waiting Customers: "+waitingManager.getSize());

                maintenanceTab.getRequestTable().setItems(maintenanceManager.displayAllRequests());
                maintenanceTab.getRequestTable().refresh();
                maintenanceTab.getServiceTable().setItems(maintenanceManager.displayCompletedServices());
                maintenanceTab.getServiceTable().refresh();
                maintenanceTab.getTfRequestId().setText(String.valueOf(maintenanceManager.generateRequestId()));

                try{
                    int vehicleId=Integer.parseInt(reservationTab.getTfVehicleId().getText());
                    reservationTab.getCustomerTable().setItems(reservationManager.displayReservations(vehicleId,vehicleManager));
                    reservationTab.getCustomerTable().refresh();
                    reservationTab.getCountLabel().setText("Reservations: "+reservationManager.getReservationCount(vehicleId,vehicleManager));
                }
                catch(Exception ex){
                    reservationTab.getCustomerTable().getItems().clear();
                    reservationTab.getCountLabel().setText("Reservations: 0");
                }

                undoRedoTab.updateSizes();
                undoRedoTab.getMessage().setText("Undo done");
            }
            else{
                undoRedoTab.getMessage().setText("Cannot undo");
            }
        });

        undoRedoTab.getBtRedo().setOnAction(e->{
            boolean done=undoRedoManager.redo(customerManager,vehicleManager,waitingManager,reservationManager,maintenanceManager,transactionManager);
            if(done){
                customerTab.getCustomerTable().setItems(customerManager.displayAllCustomers());
                customerTab.getCustomerTable().refresh();
                customerTab.getTfCustomerId().setText(String.valueOf(customerManager.generateCustomerId()));

                vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                vehicleTab.getVehicleTable().refresh();

                transactionTab.getTransactionTable().setItems(transactionManager.displayTransactions());
                transactionTab.getTransactionTable().refresh();
                transactionTab.getTfTransactionId().setText(String.valueOf(transactionManager.generateTransactionId()));

                waitingTab.getCustomerTable().setItems(waitingManager.displayWaitingQueue());
                waitingTab.getCustomerTable().refresh();
                waitingTab.getCountLabel().setText("Waiting Customers: "+waitingManager.getSize());

                maintenanceTab.getRequestTable().setItems(maintenanceManager.displayAllRequests());
                maintenanceTab.getRequestTable().refresh();
                maintenanceTab.getServiceTable().setItems(maintenanceManager.displayCompletedServices());
                maintenanceTab.getServiceTable().refresh();
                maintenanceTab.getTfRequestId().setText(String.valueOf(maintenanceManager.generateRequestId()));

                try{
                    int vehicleId=Integer.parseInt(reservationTab.getTfVehicleId().getText());
                    reservationTab.getCustomerTable().setItems(reservationManager.displayReservations(vehicleId,vehicleManager));
                    reservationTab.getCustomerTable().refresh();                    reservationTab.getCustomerTable().refresh();
                    reservationTab.getCountLabel().setText("Reservations: "+reservationManager.getReservationCount(vehicleId,vehicleManager));
                }
                catch(Exception ex){
                    reservationTab.getCustomerTable().getItems().clear();
                    reservationTab.getCountLabel().setText("Reservations: 0");
                }

                undoRedoTab.updateSizes();
                undoRedoTab.getMessage().setText("Redo done");
            }
            else{
                undoRedoTab.getMessage().setText("Cannot redo");
            }
        });
        undoRedoTab.getBtClear().setOnAction(e->{
            undoRedoManager.clear();
            undoRedoTab.updateSizes();
            undoRedoTab.getMessage().setText("Stacks cleared");
        });
        
      //FileMenu Events
        fileMenu.getReadCustomers().setOnAction(e->{
            fileChooser.setTitle("Read Customers");
            File file=fileChooser.showOpenDialog(stage);
            if(file!=null){
                try{
                	customerManager.readCustomers(file.getPath());
                	customerTab.getCustomerTable().setItems(customerManager.displayAllCustomers());
                	customerTab.getTfCustomerId().setText(String.valueOf(customerManager.generateCustomerId()));
                	customerTab.getMessage().setText("File read");
                }
                catch(Exception ex){
                    customerTab.getMessage().setText("File error");
                }
            }
        });
        fileMenu.getReadVehicles().setOnAction(e->{
            fileChooser.setTitle("Read Vehicles");
            File file=fileChooser.showOpenDialog(stage);
            if(file!=null){
                try{
                    vehicleManager.readVehicles(file.getPath());
                    vehicleTab.getVehicleTable().setItems(vehicleManager.displayAllVehicles());
                    vehicleTab.getVehicleTable().refresh();                    vehicleTab.getMessage().setText("File read");
                }
                catch(Exception ex){
                    vehicleTab.getMessage().setText("File error");
                }
            }
        });
        fileMenu.getReadRequests().setOnAction(e->{
            fileChooser.setTitle("Read Service Requests");
            File file=fileChooser.showOpenDialog(stage);
            if(file!=null){
                try{
                	maintenanceManager.readServiceRequests(file.getPath(),customerManager,vehicleManager);
                	maintenanceTab.showRequestTable();
                	maintenanceTab.getRequestTable().setItems(maintenanceManager.displayAllRequests());
                	maintenanceTab.getTfRequestId().setText(String.valueOf(maintenanceManager.generateRequestId()));
                	maintenanceTab.getMessage().setText("File read");
                }
                catch(Exception ex){
                    maintenanceTab.getMessage().setText("File error");
                }
            }
        });
        fileMenu.getReadServices().setOnAction(e->{
            fileChooser.setTitle("Read Services");

            File file=fileChooser.showOpenDialog(stage);

            if(file!=null){
                try{
                    maintenanceManager.readServices( file.getPath(),vehicleManager);

                    maintenanceTab.showServiceTable();
                    maintenanceTab.getServiceTable().setItems( maintenanceManager.displayCompletedServices() );
                    maintenanceTab.getServiceTable().refresh();
                    maintenanceTab.getTfServiceId().setText(  String.valueOf(maintenanceManager.generateServiceId() ));
                    maintenanceTab.getMessage().setText("File read");
                }
                catch(Exception ex){
                    ex.printStackTrace();
                    maintenanceTab.getMessage().setText("File error: "+ex.getMessage()
                    );
                }
            }
        });
        
        fileMenu.getReadTransactions().setOnAction(e->{
            fileChooser.setTitle("Read Transactions");
            File file=fileChooser.showOpenDialog(stage);
            if(file!=null){
                try{
                    transactionManager.readTransactions(file.getPath());
                    transactionTab.getTfTransactionId().setText(String.valueOf(transactionManager.generateTransactionId()));
                    transactionTab.getTransactionTable().setItems(transactionManager.displayTransactions());
                    transactionTab.getTransactionTable().refresh();
                    transactionTab.getMessage().setText("File read");
                }
                catch(Exception ex){
                    transactionTab.getMessage().setText("File error");
                }
            }
        });
        fileMenu.getSaveCustomers().setOnAction(e->{
            fileChooser.setTitle("Save Customers");
            File file=fileChooser.showSaveDialog(stage);
            if(file!=null){
                try{
                    customerManager.writeCustomers(file.getPath());
                    customerTab.getMessage().setText("File saved");
                }
                catch(Exception ex){
                    customerTab.getMessage().setText("File error");
                }
            }
        });
        fileMenu.getSaveVehicles().setOnAction(e->{
            fileChooser.setTitle("Save Vehicles");
            File file=fileChooser.showSaveDialog(stage);
            if(file!=null){
                try{
                    vehicleManager.writeVehicles(file.getPath());
                    vehicleTab.getMessage().setText("File saved");
                }
                catch(Exception ex){
                    vehicleTab.getMessage().setText("File error");
                }
            }
        });
        fileMenu.getSaveRequests().setOnAction(e->{
            fileChooser.setTitle("Save Service Requests");
            File file=fileChooser.showSaveDialog(stage);
            if(file!=null){
                try{
                    maintenanceManager.writeServiceRequests(file.getPath());
                    maintenanceTab.getMessage().setText("File saved");
                }
                catch(Exception ex){
                    maintenanceTab.getMessage().setText("File error");
                }
            }
        });
        fileMenu.getSaveServices().setOnAction(e->{
            fileChooser.setTitle("Save Services");
            File file=fileChooser.showSaveDialog(stage);
            if(file!=null){
                try{
                    maintenanceManager.writeServices(file.getPath());
                    maintenanceTab.getMessage().setText("File saved");
                }
                catch(Exception ex){
                    maintenanceTab.getMessage().setText("File error");
                }
            }
        });
        fileMenu.getSaveTransactions().setOnAction(e->{
            fileChooser.setTitle("Save Transactions");
            File file=fileChooser.showSaveDialog(stage);
            if(file!=null){
                try{
                    transactionManager.writeTransactions(file.getPath());
                    transactionTab.getMessage().setText("File saved");
                }
                catch(Exception ex){
                    transactionTab.getMessage().setText("File error");
                }
            }
        });
        
        fileMenu.getExit().setOnAction(e->{
            stage.close();
        });

        Scene scene=new Scene(mainPane,1200,700);      
        stage.setTitle("Car Agency Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}