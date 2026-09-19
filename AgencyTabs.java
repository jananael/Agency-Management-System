package phaseTwo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


 class CustomerTab extends Tab{
    private CustomerManager customers;
    private BorderPane pane;
    private VBox topPane;
    private GridPane fieldsPane;
    private VBox tablePane;
    private VBox bottomPane;
    private HBox buttonsPane;
    private VBox mainPane;
    private TableView<Customer> customerTable;
    private TableColumn<Customer,Integer> customerIdColumn;
    private TableColumn<Customer,String> nameColumn;
    private TableColumn<Customer,String> phoneColumn;
    private TableColumn<Customer,String> addressColumn;
    private TextField tfCustomerId;
    private TextField tfName;
    private TextField tfPhone;
    private TextField tfAddress;
    private Button btAdd;
    private Button btRemove;
    private Button btUpdate;
    private Button btSearchId;
    private Button btSearchName;
    private Button btDisplayAll;
    private Button btClear;
    private Label title;
    private Label message;

    public CustomerTab(CustomerManager customers){
        this.customers=customers;
        setText("Customers");
        setClosable(false);

        pane=new BorderPane();
        topPane=new VBox(10);
        fieldsPane=new GridPane();
        tablePane=new VBox(10);
        bottomPane=new VBox(5);
        buttonsPane=new HBox(10);
        mainPane=new VBox(12);

        title=new Label("Customer Management");
        title.setStyle("-fx-font-weight: bold;");
        message=new Label();

        Label customerInformationLabel=new Label("Customer Information");
        Label instructionLabel=new Label("Select a customer from the table before editing it");
        Label mainOperationsLabel=new Label("Main Operations");
        Label searchLabel=new Label("Search");
        Label displayOptionsLabel=new Label("Display Options");

        customerInformationLabel.setStyle("-fx-font-weight: bold;");
        instructionLabel.setStyle("-fx-font-weight: bold;");
        mainOperationsLabel.setStyle("-fx-font-weight: bold;");
        searchLabel.setStyle("-fx-font-weight: bold;");
        displayOptionsLabel.setStyle("-fx-font-weight: bold;");

        tfCustomerId=new TextField();
        tfCustomerId.setText(String.valueOf(customers.generateCustomerId()));
        tfCustomerId.setEditable(true);

        tfName=new TextField();
        tfName.setPromptText("Enter Customer Name");

        tfPhone=new TextField();
        tfPhone.setPromptText("Enter Phone Number");

        tfAddress=new TextField();
        tfAddress.setPromptText("Enter Customer Address");

        tfCustomerId.setPrefWidth(210);
        tfName.setPrefWidth(210);
        tfPhone.setPrefWidth(210);
        tfAddress.setPrefWidth(210);

        btAdd=new Button("Add Customer");
        btRemove=new Button("Remove Customer");
        btUpdate=new Button("Update Customer");
        btSearchId=new Button("Search by ID");
        btSearchName=new Button("Search by Name");
        btDisplayAll=new Button("Display All");
        btClear=new Button("Clear");

        btAdd.setPrefSize(145,32);
        btRemove.setPrefSize(145,32);
        btUpdate.setPrefSize(145,32);
        btSearchId.setPrefSize(145,32);
        btSearchName.setPrefSize(145,32);
        btDisplayAll.setPrefSize(145,32);
        btClear.setPrefSize(145,32);

        customerTable=new TableView<Customer>();

        customerIdColumn=new TableColumn<Customer,Integer>("Customer ID");
        nameColumn=new TableColumn<Customer,String>("Name");
        phoneColumn=new TableColumn<Customer,String>("Phone");
        addressColumn=new TableColumn<Customer,String>("Address");

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("address"));

        customerTable.getColumns().addAll(customerIdColumn,nameColumn,phoneColumn,addressColumn);
        customerTable.setItems(customers.displayAllCustomers());
        customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        customerTable.setPrefHeight(560);

        customerTable.setOnMouseClicked(e->{
            Customer customer=customerTable.getSelectionModel().getSelectedItem();

            if(customer!=null){
                tfCustomerId.setText(String.valueOf(customer.getCustomerId()));
                tfName.setText(customer.getName());
                tfPhone.setText(customer.getPhone());
                tfAddress.setText(customer.getAddress());
            }
        });

        topPane.getChildren().add(title);
        topPane.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(8));

        fieldsPane.setHgap(10);
        fieldsPane.setVgap(10);
        fieldsPane.setAlignment(Pos.CENTER);

        fieldsPane.add(customerInformationLabel,0,0,2,1);
        fieldsPane.add(instructionLabel,0,1,2,1);
        fieldsPane.add(new Label("Customer ID:"),0,2);
        fieldsPane.add(tfCustomerId,1,2);
        fieldsPane.add(new Label("Name:"),0,3);
        fieldsPane.add(tfName,1,3);
        fieldsPane.add(new Label("Phone:"),0,4);
        fieldsPane.add(tfPhone,1,4);
        fieldsPane.add(new Label("Address:"),0,5);
        fieldsPane.add(tfAddress,1,5);

        buttonsPane.getChildren().addAll(btAdd,btUpdate,btRemove);
        buttonsPane.setAlignment(Pos.CENTER);

        tablePane.getChildren().addAll(customerTable,mainOperationsLabel,buttonsPane);
        tablePane.setAlignment(Pos.CENTER);
        tablePane.setPadding(new Insets(8));

        HBox searchButtonsPane=new HBox(10);
        searchButtonsPane.getChildren().addAll(btSearchId,btSearchName);
        searchButtonsPane.setAlignment(Pos.CENTER);

        HBox displayButtonsPane=new HBox(10);
        displayButtonsPane.getChildren().addAll(btDisplayAll,btClear);
        displayButtonsPane.setAlignment(Pos.CENTER);

        mainPane.getChildren().addAll(
            fieldsPane,
            searchLabel,
            searchButtonsPane,
            displayOptionsLabel,
            displayButtonsPane
        );

        mainPane.setAlignment(Pos.TOP_CENTER);
        mainPane.setPadding(new Insets(8));
        mainPane.setPrefWidth(430);

        bottomPane.getChildren().add(message);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(2));

        pane.setTop(topPane);
        pane.setCenter(tablePane);
        pane.setRight(mainPane);
        pane.setBottom(bottomPane);
        pane.setPadding(new Insets(8));

        setContent(pane);
    }

    public CustomerManager getCustomers(){
        return customers;
    }

    public void setCustomers(CustomerManager customers){
        this.customers=customers;
    }

    public BorderPane getPane(){
        return pane;
    }

    public void setPane(BorderPane pane){
        this.pane=pane;
    }

    public VBox getTopPane(){
        return topPane;
    }

    public void setTopPane(VBox topPane){
        this.topPane=topPane;
    }

    public GridPane getFieldsPane(){
        return fieldsPane;
    }

    public void setFieldsPane(GridPane fieldsPane){
        this.fieldsPane=fieldsPane;
    }

    public VBox getTablePane(){
        return tablePane;
    }

    public void setTablePane(VBox tablePane){
        this.tablePane=tablePane;
    }

    public VBox getBottomPane(){
        return bottomPane;
    }

    public void setBottomPane(VBox bottomPane){
        this.bottomPane=bottomPane;
    }

    public HBox getButtonsPane(){
        return buttonsPane;
    }

    public void setButtonsPane(HBox buttonsPane){
        this.buttonsPane=buttonsPane;
    }

    public VBox getMainPane(){
        return mainPane;
    }

    public void setMainPane(VBox mainPane){
        this.mainPane=mainPane;
    }

    public TableView<Customer> getCustomerTable(){
        return customerTable;
    }

    public void setCustomerTable(TableView<Customer> customerTable){
        this.customerTable=customerTable;
    }

    public TableColumn<Customer,Integer> getCustomerIdColumn(){
        return customerIdColumn;
    }

    public void setCustomerIdColumn(TableColumn<Customer,Integer> customerIdColumn){
        this.customerIdColumn=customerIdColumn;
    }

    public TableColumn<Customer,String> getNameColumn(){
        return nameColumn;
    }

    public void setNameColumn(TableColumn<Customer,String> nameColumn){
        this.nameColumn=nameColumn;
    }

    public TableColumn<Customer,String> getPhoneColumn(){
        return phoneColumn;
    }

    public void setPhoneColumn(TableColumn<Customer,String> phoneColumn){
        this.phoneColumn=phoneColumn;
    }

    public TableColumn<Customer,String> getAddressColumn(){
        return addressColumn;
    }

    public void setAddressColumn(TableColumn<Customer,String> addressColumn){
        this.addressColumn=addressColumn;
    }

    public TextField getTfCustomerId(){
        return tfCustomerId;
    }

    public void setTfCustomerId(TextField tfCustomerId){
        this.tfCustomerId=tfCustomerId;
    }

    public TextField getTfName(){
        return tfName;
    }

    public void setTfName(TextField tfName){
        this.tfName=tfName;
    }

    public TextField getTfPhone(){
        return tfPhone;
    }

    public void setTfPhone(TextField tfPhone){
        this.tfPhone=tfPhone;
    }

    public TextField getTfAddress(){
        return tfAddress;
    }

    public void setTfAddress(TextField tfAddress){
        this.tfAddress=tfAddress;
    }

    public Button getBtAdd(){
        return btAdd;
    }

    public void setBtAdd(Button btAdd){
        this.btAdd=btAdd;
    }

    public Button getBtRemove(){
        return btRemove;
    }

    public void setBtRemove(Button btRemove){
        this.btRemove=btRemove;
    }

    public Button getBtUpdate(){
        return btUpdate;
    }

    public void setBtUpdate(Button btUpdate){
        this.btUpdate=btUpdate;
    }

    public Button getBtSearchId(){
        return btSearchId;
    }

    public void setBtSearchId(Button btSearchId){
        this.btSearchId=btSearchId;
    }

    public Button getBtSearchName(){
        return btSearchName;
    }

    public void setBtSearchName(Button btSearchName){
        this.btSearchName=btSearchName;
    }

    public Button getBtDisplayAll(){
        return btDisplayAll;
    }

    public void setBtDisplayAll(Button btDisplayAll){
        this.btDisplayAll=btDisplayAll;
    }

    public Button getBtClear(){
        return btClear;
    }

    public void setBtClear(Button btClear){
        this.btClear=btClear;
    }

    public Label getTitle(){
        return title;
    }

    public void setTitle(Label title){
        this.title=title;
    }

    public Label getMessage(){
        return message;
    }

    public void setMessage(Label message){
        this.message=message;
    }
}
 
 
 
 
 class MaintenanceTab extends Tab{
	    private MaintenanceManager maintenance;
	    private BorderPane pane;
	    private VBox topPane;
	    private GridPane leftPane;
	    private VBox centerPane;
	    private VBox bottomPane;
	    private HBox buttonsPane;
	    private VBox mainPane;
	    private TableView<ServiceRequest> requestTable;
	    private TableColumn<ServiceRequest,Integer> requestIdColumn;
	    private TableColumn<ServiceRequest,Integer> requestVehicleIdColumn;
	    private TableColumn<ServiceRequest,Integer> requestCustomerIdColumn;
	    private TableColumn<ServiceRequest,String> requestTypeColumn;
	    private TableColumn<ServiceRequest,String> requestDateColumn;
	    private TableColumn<ServiceRequest,String> requestStatusColumn;
	    private TableView<Service> serviceTable;
	    private TableColumn<Service,Integer> serviceIdColumn;
	    private TableColumn<Service,Integer> serviceVehicleIdColumn;
	    private TableColumn<Service,Integer> serviceCustomerIdColumn;
	    private TableColumn<Service,String> serviceTypeColumn;
	    private TableColumn<Service,String> serviceDateColumn;
	    private TableColumn<Service,Double> serviceCostColumn;
	    private TableColumn<Service,String> serviceStatusColumn;
	    private TextField tfRequestId;
	    private TextField tfVehicleId;
	    private TextField tfCustomerId;
	    private TextField tfServiceType;
	    private DatePicker dpRequestDate;
	    private DatePicker dpServiceDate;
	    private TextField tfServiceId;
	    private TextField tfCost;
	    private Button btCreateRequest;
	    private Button btPending;
	    private Button btProcessing;
	    private Button btAllRequests;
	    private Button btProcessNext;
	    private Button btCompleteService;
	    private Button btCompletedServices;
	    private Button btServiceHistory;
	    private Button btVehicleHistory;
	    private Button btClear;
	    private Label title;
	    private Label message;

	    public MaintenanceTab(MaintenanceManager maintenance){
	        this.maintenance=maintenance;
	        setText("Service Requests / Services");
	        setClosable(false);

	        pane=new BorderPane();
	        topPane=new VBox(10);
	        leftPane=new GridPane();
	        centerPane=new VBox(8);
	        bottomPane=new VBox(6);
	        buttonsPane=new HBox(10);
	        mainPane=new VBox(8);

	        title=new Label("Maintenance");
	        title.setStyle("-fx-font-weight: bold;");
	        message=new Label();

	        Label requestInformationLabel=new Label("1. Service Request Information");
	        Label completedInformationLabel=new Label("2. Completed Service Information");
	        Label completeInstructionLabel=new Label("Select a PROCESSING request before completing it");
	        Label serviceRequestsLabel=new Label("Service Requests");
	        Label requestOperationsLabel=new Label("Service Request Operations");
	        Label completedOperationsLabel=new Label("Completed Service Operations");

	        requestInformationLabel.setStyle("-fx-font-weight: bold;");
	        completedInformationLabel.setStyle("-fx-font-weight: bold;");
	        completeInstructionLabel.setStyle("-fx-font-weight: bold;");
	        serviceRequestsLabel.setStyle("-fx-font-weight: bold;");
	        requestOperationsLabel.setStyle("-fx-font-weight: bold;");
	        completedOperationsLabel.setStyle("-fx-font-weight: bold;");

	        tfRequestId=new TextField();
	        tfRequestId.setText(String.valueOf(maintenance.generateRequestId()));
	        tfRequestId.setEditable(true);
	        tfRequestId.setPromptText("Enter Request ID");

	        tfVehicleId=new TextField();
	        tfVehicleId.setPromptText("Enter Vehicle ID");

	        tfCustomerId=new TextField();
	        tfCustomerId.setPromptText("Enter Customer ID");

	        tfServiceType=new TextField();
	        tfServiceType.setPromptText("Enter Service Type");

	        dpRequestDate=new DatePicker();
	        dpRequestDate.setPromptText("Select Request Date");

	        tfServiceId=new TextField();
	        tfServiceId.setPromptText("Enter Service ID");

	        dpServiceDate=new DatePicker();
	        dpServiceDate.setPromptText("Select Service Date");

	        tfCost=new TextField();
	        tfCost.setPromptText("Enter Service Cost");

	        tfRequestId.setPrefWidth(210);
	        tfVehicleId.setPrefWidth(210);
	        tfCustomerId.setPrefWidth(210);
	        tfServiceType.setPrefWidth(210);
	        dpRequestDate.setPrefWidth(210);
	        tfServiceId.setPrefWidth(210);
	        dpServiceDate.setPrefWidth(210);
	        tfCost.setPrefWidth(210);

	        btCreateRequest=new Button("Create Request");
	        btPending=new Button("Pending Requests");
	        btProcessing=new Button("Processing Requests");
	        btAllRequests=new Button("All Requests");
	        btProcessNext=new Button("Process Next Request");
	        btCompleteService=new Button("Complete Service");
	        btCompletedServices=new Button("Completed Services");
	        btServiceHistory=new Button("Service History");
	        btVehicleHistory=new Button("Vehicle History");
	        btClear=new Button("Clear");

	        btCreateRequest.setPrefSize(155,32);
	        btPending.setPrefSize(155,32);
	        btProcessing.setPrefSize(155,32);
	        btAllRequests.setPrefSize(155,32);
	        btProcessNext.setPrefSize(155,32);
	        btCompleteService.setPrefSize(155,32);
	        btCompletedServices.setPrefSize(155,32);
	        btServiceHistory.setPrefSize(155,32);
	        btVehicleHistory.setPrefSize(155,32);
	        btClear.setPrefSize(155,32);

	        requestTable=new TableView<ServiceRequest>();

	        requestIdColumn=new TableColumn<ServiceRequest,Integer>("Request ID");
	        requestVehicleIdColumn=new TableColumn<ServiceRequest,Integer>("Vehicle ID");
	        requestCustomerIdColumn=new TableColumn<ServiceRequest,Integer>("Customer ID");
	        requestTypeColumn=new TableColumn<ServiceRequest,String>("Service Type");
	        requestDateColumn=new TableColumn<ServiceRequest,String>("Date");
	        requestStatusColumn=new TableColumn<ServiceRequest,String>("Status");

	        requestIdColumn.setCellValueFactory(new PropertyValueFactory<ServiceRequest,Integer>("requestId"));
	        requestVehicleIdColumn.setCellValueFactory(new PropertyValueFactory<ServiceRequest,Integer>("vehicleId"));
	        requestCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<ServiceRequest,Integer>("customerId"));
	        requestTypeColumn.setCellValueFactory(new PropertyValueFactory<ServiceRequest,String>("serviceType"));
	        requestDateColumn.setCellValueFactory(new PropertyValueFactory<ServiceRequest,String>("date"));
	        requestStatusColumn.setCellValueFactory(new PropertyValueFactory<ServiceRequest,String>("status"));

	        requestTable.getColumns().addAll(requestIdColumn,requestVehicleIdColumn,requestCustomerIdColumn,requestTypeColumn,requestDateColumn,requestStatusColumn);
	        requestTable.setItems(maintenance.displayPendingRequests());
	        requestTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        requestTable.setPrefHeight(485);

	        serviceTable=new TableView<Service>();

	        serviceIdColumn=new TableColumn<Service,Integer>("Service ID");
	        serviceVehicleIdColumn=new TableColumn<Service,Integer>("Vehicle ID");
	        serviceCustomerIdColumn=new TableColumn<Service,Integer>("Customer ID");
	        serviceTypeColumn=new TableColumn<Service,String>("Service Type");
	        serviceDateColumn=new TableColumn<Service,String>("Date");
	        serviceCostColumn=new TableColumn<Service,Double>("Cost");
	        serviceStatusColumn=new TableColumn<Service,String>("Status");

	        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<Service,Integer>("serviceId"));
	        serviceVehicleIdColumn.setCellValueFactory(new PropertyValueFactory<Service,Integer>("vehicleId"));
	        serviceCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<Service,Integer>("customerId"));
	        serviceTypeColumn.setCellValueFactory(new PropertyValueFactory<Service,String>("serviceType"));
	        serviceDateColumn.setCellValueFactory(new PropertyValueFactory<Service,String>("date"));
	        serviceCostColumn.setCellValueFactory(new PropertyValueFactory<Service,Double>("cost"));
	        serviceStatusColumn.setCellValueFactory(new PropertyValueFactory<Service,String>("status"));

	        serviceTable.getColumns().addAll(serviceIdColumn,serviceVehicleIdColumn,serviceCustomerIdColumn,serviceTypeColumn,serviceDateColumn,serviceCostColumn,serviceStatusColumn);
	        serviceTable.setItems(maintenance.displayCompletedServices());
	        serviceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        serviceTable.setPrefHeight(485);

	        requestTable.setOnMouseClicked(e->{
	            ServiceRequest request=requestTable.getSelectionModel().getSelectedItem();

	            if(request!=null){
	                tfRequestId.setText(String.valueOf(request.getRequestId()));
	                tfVehicleId.setText(String.valueOf(request.getVehicleId()));
	                tfCustomerId.setText(String.valueOf(request.getCustomerId()));
	                tfServiceType.setText(request.getServiceType());
	                dpRequestDate.setValue(java.time.LocalDate.parse(request.getDate()));
	            }
	        });

	        serviceTable.setOnMouseClicked(e->{
	            Service service=serviceTable.getSelectionModel().getSelectedItem();

	            if(service!=null){
	                tfServiceId.setText(String.valueOf(service.getServiceId()));
	                tfVehicleId.setText(String.valueOf(service.getVehicleId()));
	                tfCustomerId.setText(String.valueOf(service.getCustomerId()));
	                tfServiceType.setText(service.getServiceType());
	                dpServiceDate.setValue(java.time.LocalDate.parse(service.getDate()));
	                tfCost.setText(String.valueOf(service.getCost()));
	            }
	        });

	        topPane.getChildren().add(title);
	        topPane.setAlignment(Pos.CENTER);
	        topPane.setPadding(new Insets(8));

	        leftPane.setHgap(10);
	        leftPane.setVgap(8);
	        leftPane.setAlignment(Pos.CENTER);

	        leftPane.add(requestInformationLabel,0,0,2,1);
	        leftPane.add(new Label("Request ID:"),0,1);
	        leftPane.add(tfRequestId,1,1);
	        leftPane.add(new Label("Vehicle ID:"),0,2);
	        leftPane.add(tfVehicleId,1,2);
	        leftPane.add(new Label("Customer ID:"),0,3);
	        leftPane.add(tfCustomerId,1,3);
	        leftPane.add(new Label("Service Type:"),0,4);
	        leftPane.add(tfServiceType,1,4);
	        leftPane.add(new Label("Request Date:"),0,5);
	        leftPane.add(dpRequestDate,1,5);

	        leftPane.add(completedInformationLabel,0,6,2,1);
	        leftPane.add(completeInstructionLabel,0,7,2,1);
	        leftPane.add(new Label("Service ID:"),0,8);
	        leftPane.add(tfServiceId,1,8);
	        leftPane.add(new Label("Service Date:"),0,9);
	        leftPane.add(dpServiceDate,1,9);
	        leftPane.add(new Label("Cost:"),0,10);
	        leftPane.add(tfCost,1,10);

	        centerPane.getChildren().addAll(serviceRequestsLabel,requestTable);
	        centerPane.setAlignment(Pos.CENTER);
	        centerPane.setPadding(new Insets(8));

	        buttonsPane.getChildren().addAll(btCreateRequest,btProcessNext,btPending,btProcessing,btAllRequests);
	        buttonsPane.setAlignment(Pos.CENTER);

	        HBox serviceButtonsPane=new HBox(10);
	        serviceButtonsPane.getChildren().addAll(btCompleteService,btCompletedServices,btServiceHistory,btVehicleHistory,btClear);
	        serviceButtonsPane.setAlignment(Pos.CENTER);

	        bottomPane.getChildren().addAll(
	            requestOperationsLabel,
	            buttonsPane,
	            completedOperationsLabel,
	            serviceButtonsPane,
	            message
	        );

	        bottomPane.setAlignment(Pos.CENTER);
	        bottomPane.setPadding(new Insets(4));

	        mainPane.getChildren().add(leftPane);
	        mainPane.setAlignment(Pos.TOP_CENTER);
	        mainPane.setPadding(new Insets(8));
	        mainPane.setPrefWidth(430);

	        pane.setTop(topPane);
	        pane.setCenter(centerPane);
	        pane.setRight(mainPane);
	        pane.setBottom(bottomPane);
	        pane.setPadding(new Insets(8));

	        setContent(pane);
	    }

	    public MaintenanceManager getMaintenance(){
	        return maintenance;
	    }

	    public void setMaintenance(MaintenanceManager maintenance){
	        this.maintenance=maintenance;
	    }

	    public BorderPane getPane(){
	        return pane;
	    }

	    public void setPane(BorderPane pane){
	        this.pane=pane;
	    }

	    public VBox getTopPane(){
	        return topPane;
	    }

	    public void setTopPane(VBox topPane){
	        this.topPane=topPane;
	    }

	    public GridPane getLeftPane(){
	        return leftPane;
	    }

	    public void setLeftPane(GridPane leftPane){
	        this.leftPane=leftPane;
	    }

	    public VBox getCenterPane(){
	        return centerPane;
	    }

	    public void setCenterPane(VBox centerPane){
	        this.centerPane=centerPane;
	    }

	    public VBox getBottomPane(){
	        return bottomPane;
	    }

	    public void setBottomPane(VBox bottomPane){
	        this.bottomPane=bottomPane;
	    }

	    public HBox getButtonsPane(){
	        return buttonsPane;
	    }

	    public void setButtonsPane(HBox buttonsPane){
	        this.buttonsPane=buttonsPane;
	    }

	    public VBox getMainPane(){
	        return mainPane;
	    }

	    public void setMainPane(VBox mainPane){
	        this.mainPane=mainPane;
	    }

	    public TableView<ServiceRequest> getRequestTable(){
	        return requestTable;
	    }

	    public void setRequestTable(TableView<ServiceRequest> requestTable){
	        this.requestTable=requestTable;
	    }

	    public TableView<Service> getServiceTable(){
	        return serviceTable;
	    }

	    public void setServiceTable(TableView<Service> serviceTable){
	        this.serviceTable=serviceTable;
	    }

	    public TextField getTfRequestId(){
	        return tfRequestId;
	    }

	    public void setTfRequestId(TextField tfRequestId){
	        this.tfRequestId=tfRequestId;
	    }

	    public TextField getTfVehicleId(){
	        return tfVehicleId;
	    }

	    public void setTfVehicleId(TextField tfVehicleId){
	        this.tfVehicleId=tfVehicleId;
	    }

	    public TextField getTfCustomerId(){
	        return tfCustomerId;
	    }

	    public void setTfCustomerId(TextField tfCustomerId){
	        this.tfCustomerId=tfCustomerId;
	    }

	    public TextField getTfServiceType(){
	        return tfServiceType;
	    }

	    public void setTfServiceType(TextField tfServiceType){
	        this.tfServiceType=tfServiceType;
	    }

	    public DatePicker getDpRequestDate(){
	        return dpRequestDate;
	    }

	    public void setDpRequestDate(DatePicker dpRequestDate){
	        this.dpRequestDate=dpRequestDate;
	    }

	    public TextField getTfServiceId(){
	        return tfServiceId;
	    }

	    public void setTfServiceId(TextField tfServiceId){
	        this.tfServiceId=tfServiceId;
	    }

	    public DatePicker getDpServiceDate(){
	        return dpServiceDate;
	    }

	    public void setDpServiceDate(DatePicker dpServiceDate){
	        this.dpServiceDate=dpServiceDate;
	    }

	    public TextField getTfCost(){
	        return tfCost;
	    }

	    public void setTfCost(TextField tfCost){
	        this.tfCost=tfCost;
	    }

	    public Button getBtCreateRequest(){
	        return btCreateRequest;
	    }

	    public void setBtCreateRequest(Button btCreateRequest){
	        this.btCreateRequest=btCreateRequest;
	    }

	    public Button getBtPending(){
	        return btPending;
	    }

	    public void setBtPending(Button btPending){
	        this.btPending=btPending;
	    }

	    public Button getBtProcessing(){
	        return btProcessing;
	    }

	    public void setBtProcessing(Button btProcessing){
	        this.btProcessing=btProcessing;
	    }

	    public Button getBtAllRequests(){
	        return btAllRequests;
	    }

	    public void setBtAllRequests(Button btAllRequests){
	        this.btAllRequests=btAllRequests;
	    }

	    public Button getBtProcessNext(){
	        return btProcessNext;
	    }

	    public void setBtProcessNext(Button btProcessNext){
	        this.btProcessNext=btProcessNext;
	    }

	    public Button getBtCompleteService(){
	        return btCompleteService;
	    }

	    public void setBtCompleteService(Button btCompleteService){
	        this.btCompleteService=btCompleteService;
	    }

	    public Button getBtCompletedServices(){
	        return btCompletedServices;
	    }

	    public void setBtCompletedServices(Button btCompletedServices){
	        this.btCompletedServices=btCompletedServices;
	    }

	    public Button getBtServiceHistory(){
	        return btServiceHistory;
	    }

	    public void setBtServiceHistory(Button btServiceHistory){
	        this.btServiceHistory=btServiceHistory;
	    }

	    public Button getBtVehicleHistory(){
	        return btVehicleHistory;
	    }

	    public void setBtVehicleHistory(Button btVehicleHistory){
	        this.btVehicleHistory=btVehicleHistory;
	    }

	    public Button getBtClear(){
	        return btClear;
	    }

	    public void setBtClear(Button btClear){
	        this.btClear=btClear;
	    }

	    public Label getTitle(){
	        return title;
	    }

	    public void setTitle(Label title){
	        this.title=title;
	    }

	    public Label getMessage(){
	        return message;
	    }

	    public void setMessage(Label message){
	        this.message=message;
	    }

	    public void showRequestTable(){
	        Label serviceRequestsLabel=new Label("Service Requests");
	        serviceRequestsLabel.setStyle("-fx-font-weight: bold;");

	        centerPane.getChildren().clear();
	        centerPane.getChildren().addAll(serviceRequestsLabel,requestTable);
	    }

	    public void showServiceTable(){
	        Label completedServicesLabel=new Label("Completed Services");
	        completedServicesLabel.setStyle("-fx-font-weight: bold;");

	        centerPane.getChildren().clear();
	        centerPane.getChildren().addAll(completedServicesLabel,serviceTable);
	    }
}
 
 
 class TransactionTab extends Tab{
	    private TransactionManager transactions;
	    private BorderPane pane;
	    private VBox topPane;
	    private GridPane leftPane;
	    private VBox centerPane;
	    private VBox bottomPane;
	    private HBox buttonsPane;
	    private VBox mainPane;
	    private TableView<Transaction> transactionTable;
	    private TableColumn<Transaction,Integer> transactionIdColumn;
	    private TableColumn<Transaction,Integer> customerIdColumn;
	    private TableColumn<Transaction,Integer> vehicleIdColumn;
	    private TableColumn<Transaction,Double> amountColumn;
	    private TableColumn<Transaction,String> transactionTypeColumn;
	    private TableColumn<Transaction,String> dateColumn;
	    private TextField tfTransactionId;
	    private TextField tfCustomerId;
	    private TextField tfVehicleId;
	    private TextField tfServiceId;
	    private TextField tfAmount;
	    private DatePicker dpDate;
	    private TextField tfDiscount;
	    private Button btSale;
	    private Button btServicePayment;
	    private Button btDeposit;
	    private Button btRefund;
	    private Button btApplyDiscount;
	    private Button btDisplayAll;
	    private Button btClear;
	    private Label title;
	    private Label message;

	    public TransactionTab(TransactionManager transactions){
	        this.transactions=transactions;
	        setText("Transactions");
	        setClosable(false);

	        pane=new BorderPane();
	        topPane=new VBox(10);
	        leftPane=new GridPane();
	        centerPane=new VBox(10);
	        bottomPane=new VBox(5);
	        buttonsPane=new HBox(10);
	        mainPane=new VBox(10);

	        title=new Label("Transaction Management");
	        title.setStyle("-fx-font-weight: bold;");
	        message=new Label();

	        Label commonInformationLabel=new Label("Common Transaction Information");
	        Label saleDepositRefundLabel=new Label("Sale / Deposit / Refund");
	        Label servicePaymentLabel=new Label("Service Payment");
	        Label discountLabel=new Label("Discount");
	        Label discountInstructionLabel=new Label("Select a SALE transaction from the table first");
	        Label createTransactionLabel=new Label("Create Transaction");
	        Label displayOptionsLabel=new Label("Display Options");

	        commonInformationLabel.setStyle("-fx-font-weight: bold;");
	        saleDepositRefundLabel.setStyle("-fx-font-weight: bold;");
	        servicePaymentLabel.setStyle("-fx-font-weight: bold;");
	        discountLabel.setStyle("-fx-font-weight: bold;");
	        discountInstructionLabel.setStyle("-fx-font-weight: bold;");
	        createTransactionLabel.setStyle("-fx-font-weight: bold;");
	        displayOptionsLabel.setStyle("-fx-font-weight: bold;");

	        tfTransactionId=new TextField();
	        tfTransactionId.setText(String.valueOf(transactions.generateTransactionId()));
	        tfTransactionId.setEditable(false);

	        tfCustomerId=new TextField();
	        tfCustomerId.setPromptText("Enter Customer ID");

	        tfVehicleId=new TextField();
	        tfVehicleId.setPromptText("Enter Vehicle ID");

	        tfServiceId=new TextField();
	        tfServiceId.setPromptText("Enter Service ID");

	        tfAmount=new TextField();
	        tfAmount.setPromptText("Enter Amount");

	        dpDate=new DatePicker();
	        dpDate.setPromptText("Select Transaction Date");

	        tfDiscount=new TextField();
	        tfDiscount.setPromptText("Enter Discount");

	        tfTransactionId.setPrefWidth(210);
	        tfCustomerId.setPrefWidth(210);
	        tfVehicleId.setPrefWidth(210);
	        tfServiceId.setPrefWidth(210);
	        tfAmount.setPrefWidth(210);
	        dpDate.setPrefWidth(210);
	        tfDiscount.setPrefWidth(210);

	        btSale=new Button("Create Sale");
	        btServicePayment=new Button("Service Payment");
	        btDeposit=new Button("Create Deposit");
	        btRefund=new Button("Create Refund");
	        btApplyDiscount=new Button("Apply Discount");
	        btDisplayAll=new Button("Display All");
	        btClear=new Button("Clear");

	        btSale.setPrefSize(160,32);
	        btServicePayment.setPrefSize(160,32);
	        btDeposit.setPrefSize(160,32);
	        btRefund.setPrefSize(160,32);
	        btApplyDiscount.setPrefSize(160,32);
	        btDisplayAll.setPrefSize(145,32);
	        btClear.setPrefSize(145,32);

	        transactionTable=new TableView<Transaction>();

	        transactionIdColumn=new TableColumn<Transaction,Integer>("Transaction ID");
	        customerIdColumn=new TableColumn<Transaction,Integer>("Customer ID");
	        vehicleIdColumn=new TableColumn<Transaction,Integer>("Vehicle ID");
	        amountColumn=new TableColumn<Transaction,Double>("Amount");
	        transactionTypeColumn=new TableColumn<Transaction,String>("Transaction Type");
	        dateColumn=new TableColumn<Transaction,String>("Date");

	        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<Transaction,Integer>("transactionId"));
	        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Transaction,Integer>("customerId"));
	        vehicleIdColumn.setCellValueFactory(new PropertyValueFactory<Transaction,Integer>("vehicleId"));
	        amountColumn.setCellValueFactory(new PropertyValueFactory<Transaction,Double>("amount"));
	        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("transactionType"));
	        dateColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("date"));

	        transactionTable.getColumns().addAll(transactionIdColumn,customerIdColumn,vehicleIdColumn,amountColumn,transactionTypeColumn,dateColumn);
	        transactionTable.setItems(transactions.displayTransactions());
	        transactionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        transactionTable.setPrefHeight(520);

	        transactionTable.setOnMouseClicked(e->{
	            Transaction transaction=transactionTable.getSelectionModel().getSelectedItem();

	            if(transaction!=null){
	                tfTransactionId.setText(String.valueOf(transaction.getTransactionId()));
	                tfCustomerId.setText(String.valueOf(transaction.getCustomerId()));
	                tfVehicleId.setText(String.valueOf(transaction.getVehicleId()));
	                tfAmount.setText(String.valueOf(transaction.getAmount()));
	                dpDate.setValue(java.time.LocalDate.parse(transaction.getDate()));
	            }
	        });

	        topPane.getChildren().add(title);
	        topPane.setAlignment(Pos.CENTER);
	        topPane.setPadding(new Insets(8));

	        leftPane.setHgap(10);
	        leftPane.setVgap(8);
	        leftPane.setAlignment(Pos.CENTER);

	        leftPane.add(commonInformationLabel,0,0,2,1);
	        leftPane.add(new Label("Transaction ID:"),0,1);
	        leftPane.add(tfTransactionId,1,1);
	        leftPane.add(new Label("Amount:"),0,2);
	        leftPane.add(tfAmount,1,2);
	        leftPane.add(new Label("Date:"),0,3);
	        leftPane.add(dpDate,1,3);

	        leftPane.add(saleDepositRefundLabel,0,4,2,1);
	        leftPane.add(new Label("Customer ID:"),0,5);
	        leftPane.add(tfCustomerId,1,5);
	        leftPane.add(new Label("Vehicle ID:"),0,6);
	        leftPane.add(tfVehicleId,1,6);

	        leftPane.add(servicePaymentLabel,0,7,2,1);
	        leftPane.add(new Label("Service ID:"),0,8);
	        leftPane.add(tfServiceId,1,8);

	        leftPane.add(discountLabel,0,9,2,1);
	        leftPane.add(discountInstructionLabel,0,10,2,1);
	        leftPane.add(new Label("Discount:"),0,11);
	        leftPane.add(tfDiscount,1,11);
	        leftPane.add(btApplyDiscount,1,12);

	        buttonsPane.getChildren().addAll(btSale,btServicePayment,btDeposit,btRefund);
	        buttonsPane.setAlignment(Pos.CENTER);

	        centerPane.getChildren().addAll(
	            transactionTable,
	            createTransactionLabel,
	            buttonsPane
	        );

	        centerPane.setAlignment(Pos.CENTER);
	        centerPane.setPadding(new Insets(8));

	        HBox displayButtonsPane=new HBox(10);
	        displayButtonsPane.getChildren().addAll(btDisplayAll,btClear);
	        displayButtonsPane.setAlignment(Pos.CENTER);

	        mainPane.getChildren().addAll(
	            leftPane,
	            displayOptionsLabel,
	            displayButtonsPane
	        );

	        mainPane.setAlignment(Pos.TOP_CENTER);
	        mainPane.setPadding(new Insets(8));
	        mainPane.setPrefWidth(430);

	        bottomPane.getChildren().add(message);
	        bottomPane.setAlignment(Pos.CENTER);
	        bottomPane.setPadding(new Insets(2));

	        pane.setTop(topPane);
	        pane.setCenter(centerPane);
	        pane.setRight(mainPane);
	        pane.setBottom(bottomPane);
	        pane.setPadding(new Insets(8));

	        setContent(pane);
	    }

	    public TransactionManager getTransactions(){
	        return transactions;
	    }

	    public void setTransactions(TransactionManager transactions){
	        this.transactions=transactions;
	    }

	    public BorderPane getPane(){
	        return pane;
	    }

	    public void setPane(BorderPane pane){
	        this.pane=pane;
	    }

	    public VBox getTopPane(){
	        return topPane;
	    }

	    public void setTopPane(VBox topPane){
	        this.topPane=topPane;
	    }

	    public GridPane getLeftPane(){
	        return leftPane;
	    }

	    public void setLeftPane(GridPane leftPane){
	        this.leftPane=leftPane;
	    }

	    public VBox getCenterPane(){
	        return centerPane;
	    }

	    public void setCenterPane(VBox centerPane){
	        this.centerPane=centerPane;
	    }

	    public VBox getBottomPane(){
	        return bottomPane;
	    }

	    public void setBottomPane(VBox bottomPane){
	        this.bottomPane=bottomPane;
	    }

	    public HBox getButtonsPane(){
	        return buttonsPane;
	    }

	    public void setButtonsPane(HBox buttonsPane){
	        this.buttonsPane=buttonsPane;
	    }

	    public VBox getMainPane(){
	        return mainPane;
	    }

	    public void setMainPane(VBox mainPane){
	        this.mainPane=mainPane;
	    }

	    public TableView<Transaction> getTransactionTable(){
	        return transactionTable;
	    }

	    public void setTransactionTable(TableView<Transaction> transactionTable){
	        this.transactionTable=transactionTable;
	    }

	    public TextField getTfTransactionId(){
	        return tfTransactionId;
	    }

	    public void setTfTransactionId(TextField tfTransactionId){
	        this.tfTransactionId=tfTransactionId;
	    }

	    public TextField getTfCustomerId(){
	        return tfCustomerId;
	    }

	    public void setTfCustomerId(TextField tfCustomerId){
	        this.tfCustomerId=tfCustomerId;
	    }

	    public TextField getTfVehicleId(){
	        return tfVehicleId;
	    }

	    public void setTfVehicleId(TextField tfVehicleId){
	        this.tfVehicleId=tfVehicleId;
	    }

	    public TextField getTfServiceId(){
	        return tfServiceId;
	    }

	    public void setTfServiceId(TextField tfServiceId){
	        this.tfServiceId=tfServiceId;
	    }

	    public TextField getTfAmount(){
	        return tfAmount;
	    }

	    public void setTfAmount(TextField tfAmount){
	        this.tfAmount=tfAmount;
	    }

	    public DatePicker getDpDate(){
	        return dpDate;
	    }

	    public void setDpDate(DatePicker dpDate){
	        this.dpDate=dpDate;
	    }

	    public TextField getTfDiscount(){
	        return tfDiscount;
	    }

	    public void setTfDiscount(TextField tfDiscount){
	        this.tfDiscount=tfDiscount;
	    }

	    public Button getBtSale(){
	        return btSale;
	    }

	    public void setBtSale(Button btSale){
	        this.btSale=btSale;
	    }

	    public Button getBtServicePayment(){
	        return btServicePayment;
	    }

	    public void setBtServicePayment(Button btServicePayment){
	        this.btServicePayment=btServicePayment;
	    }

	    public Button getBtDeposit(){
	        return btDeposit;
	    }

	    public void setBtDeposit(Button btDeposit){
	        this.btDeposit=btDeposit;
	    }

	    public Button getBtRefund(){
	        return btRefund;
	    }

	    public void setBtRefund(Button btRefund){
	        this.btRefund=btRefund;
	    }

	    public Button getBtApplyDiscount(){
	        return btApplyDiscount;
	    }

	    public void setBtApplyDiscount(Button btApplyDiscount){
	        this.btApplyDiscount=btApplyDiscount;
	    }

	    public Button getBtDisplayAll(){
	        return btDisplayAll;
	    }

	    public void setBtDisplayAll(Button btDisplayAll){
	        this.btDisplayAll=btDisplayAll;
	    }

	    public Button getBtClear(){
	        return btClear;
	    }

	    public void setBtClear(Button btClear){
	        this.btClear=btClear;
	    }

	    public Label getTitle(){
	        return title;
	    }

	    public void setTitle(Label title){
	        this.title=title;
	    }

	    public Label getMessage(){
	        return message;
	    }

	    public void setMessage(Label message){
	        this.message=message;
	    }
 }
 
 
 class ReservationTab extends Tab{
	    private VehicleReservationManager reservations;
	    private BorderPane pane;
	    private VBox topPane;
	    private GridPane leftPane;
	    private VBox centerPane;
	    private VBox bottomPane;
	    private HBox buttonsPane;
	    private VBox mainPane;
	    private TableView<Customer> customerTable;
	    private TableColumn<Customer,Integer> customerIdColumn;
	    private TableColumn<Customer,String> nameColumn;
	    private TableColumn<Customer,String> phoneColumn;
	    private TableColumn<Customer,String> addressColumn;
	    private TextField tfCustomerId;
	    private TextField tfVehicleId;
	    private Button btAdd;
	    private Button btDisplay;
	    private Button btProcessNext;
	    private Button btCount;
	    private Button btClear;
	    private Label title;
	    private Label message;
	    private Label countLabel;

	    public ReservationTab(VehicleReservationManager reservations){
	        this.reservations=reservations;
	        setText("Vehicle Reservations");
	        setClosable(false);

	        pane=new BorderPane();
	        topPane=new VBox(10);
	        leftPane=new GridPane();
	        centerPane=new VBox(10);
	        bottomPane=new VBox(5);
	        buttonsPane=new HBox(10);
	        mainPane=new VBox(12);

	        title=new Label("Vehicle Reservations");
	        title.setStyle("-fx-font-weight: bold;");
	        message=new Label();

	        countLabel=new Label("Reservations: 0");
	        countLabel.setStyle("-fx-font-weight: bold;");

	        Label reservationInformationLabel=new Label("Reservation Information");
	        Label availableVehicleLabel=new Label("Use an AVAILABLE vehicle to create the reservation queue");
	        Label mainOperationsLabel=new Label("Main Reservation Operations");
	        Label reservationStepsLabel=new Label("Reservation Steps");
	        Label firstStepLabel=new Label("1. Add customers to the vehicle reservation queue");
	        Label secondStepLabel=new Label("2. Process the first customer in the queue");
	        Label thirdStepLabel=new Label("3. The vehicle status becomes RESERVED");
	        Label queueOptionsLabel=new Label("Queue Options");

	        reservationInformationLabel.setStyle("-fx-font-weight: bold;");
	        availableVehicleLabel.setStyle("-fx-font-weight: bold;");
	        mainOperationsLabel.setStyle("-fx-font-weight: bold;");
	        reservationStepsLabel.setStyle("-fx-font-weight: bold;");
	        firstStepLabel.setStyle("-fx-font-weight: bold;");
	        secondStepLabel.setStyle("-fx-font-weight: bold;");
	        thirdStepLabel.setStyle("-fx-font-weight: bold;");
	        queueOptionsLabel.setStyle("-fx-font-weight: bold;");

	        tfCustomerId=new TextField();
	        tfCustomerId.setPromptText("Enter Customer ID");

	        tfVehicleId=new TextField();
	        tfVehicleId.setPromptText("Enter Vehicle ID");

	        tfCustomerId.setPrefWidth(210);
	        tfVehicleId.setPrefWidth(210);

	        btAdd=new Button("Add to Reservation Queue");
	        btDisplay=new Button("Display Queue");
	        btProcessNext=new Button("Reserve for Next Customer");
	        btCount=new Button("Update Count");
	        btClear=new Button("Clear");

	        btAdd.setPrefSize(190,32);
	        btProcessNext.setPrefSize(190,32);
	        btDisplay.setPrefSize(145,32);
	        btCount.setPrefSize(145,32);
	        btClear.setPrefSize(145,32);

	        customerTable=new TableView<Customer>();

	        customerIdColumn=new TableColumn<Customer,Integer>("Customer ID");
	        nameColumn=new TableColumn<Customer,String>("Name");
	        phoneColumn=new TableColumn<Customer,String>("Phone");
	        addressColumn=new TableColumn<Customer,String>("Address");

	        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("customerId"));
	        nameColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
	        phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("phone"));
	        addressColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("address"));

	        customerTable.getColumns().addAll(customerIdColumn,nameColumn,phoneColumn,addressColumn);
	        customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        customerTable.setPrefHeight(560);

	        customerTable.setOnMouseClicked(e->{
	            Customer customer=customerTable.getSelectionModel().getSelectedItem();

	            if(customer!=null)
	                tfCustomerId.setText(String.valueOf(customer.getCustomerId()));
	        });

	        topPane.getChildren().add(title);
	        topPane.setAlignment(Pos.CENTER);
	        topPane.setPadding(new Insets(8));

	        leftPane.setHgap(10);
	        leftPane.setVgap(10);
	        leftPane.setAlignment(Pos.CENTER);

	        leftPane.add(reservationInformationLabel,0,0,2,1);
	        leftPane.add(availableVehicleLabel,0,1,2,1);
	        leftPane.add(new Label("Customer ID:"),0,2);
	        leftPane.add(tfCustomerId,1,2);
	        leftPane.add(new Label("Vehicle ID:"),0,3);
	        leftPane.add(tfVehicleId,1,3);

	        buttonsPane.getChildren().addAll(btAdd,btProcessNext);
	        buttonsPane.setAlignment(Pos.CENTER);

	        centerPane.getChildren().addAll(
	            customerTable,
	            mainOperationsLabel,
	            buttonsPane
	        );

	        centerPane.setAlignment(Pos.CENTER);
	        centerPane.setPadding(new Insets(8));

	        HBox queueButtonsPane=new HBox(10);
	        queueButtonsPane.getChildren().addAll(btDisplay,btCount);
	        queueButtonsPane.setAlignment(Pos.CENTER);

	        HBox clearButtonPane=new HBox(10);
	        clearButtonPane.getChildren().add(btClear);
	        clearButtonPane.setAlignment(Pos.CENTER);

	        mainPane.getChildren().addAll(
	            leftPane,
	            countLabel,
	            reservationStepsLabel,
	            firstStepLabel,
	            secondStepLabel,
	            thirdStepLabel,
	            queueOptionsLabel,
	            queueButtonsPane,
	            clearButtonPane
	        );

	        mainPane.setAlignment(Pos.TOP_CENTER);
	        mainPane.setPadding(new Insets(8));
	        mainPane.setPrefWidth(430);

	        bottomPane.getChildren().add(message);
	        bottomPane.setAlignment(Pos.CENTER);
	        bottomPane.setPadding(new Insets(2));

	        pane.setTop(topPane);
	        pane.setCenter(centerPane);
	        pane.setRight(mainPane);
	        pane.setBottom(bottomPane);
	        pane.setPadding(new Insets(8));

	        setContent(pane);
	    }

	    public VehicleReservationManager getReservations(){
	        return reservations;
	    }

	    public void setReservations(VehicleReservationManager reservations){
	        this.reservations=reservations;
	    }

	    public BorderPane getPane(){
	        return pane;
	    }

	    public void setPane(BorderPane pane){
	        this.pane=pane;
	    }

	    public VBox getTopPane(){
	        return topPane;
	    }

	    public void setTopPane(VBox topPane){
	        this.topPane=topPane;
	    }

	    public GridPane getLeftPane(){
	        return leftPane;
	    }

	    public void setLeftPane(GridPane leftPane){
	        this.leftPane=leftPane;
	    }

	    public VBox getCenterPane(){
	        return centerPane;
	    }

	    public void setCenterPane(VBox centerPane){
	        this.centerPane=centerPane;
	    }

	    public VBox getBottomPane(){
	        return bottomPane;
	    }

	    public void setBottomPane(VBox bottomPane){
	        this.bottomPane=bottomPane;
	    }

	    public HBox getButtonsPane(){
	        return buttonsPane;
	    }

	    public void setButtonsPane(HBox buttonsPane){
	        this.buttonsPane=buttonsPane;
	    }

	    public VBox getMainPane(){
	        return mainPane;
	    }

	    public void setMainPane(VBox mainPane){
	        this.mainPane=mainPane;
	    }

	    public TableView<Customer> getCustomerTable(){
	        return customerTable;
	    }

	    public void setCustomerTable(TableView<Customer> customerTable){
	        this.customerTable=customerTable;
	    }

	    public TextField getTfCustomerId(){
	        return tfCustomerId;
	    }

	    public void setTfCustomerId(TextField tfCustomerId){
	        this.tfCustomerId=tfCustomerId;
	    }

	    public TextField getTfVehicleId(){
	        return tfVehicleId;
	    }

	    public void setTfVehicleId(TextField tfVehicleId){
	        this.tfVehicleId=tfVehicleId;
	    }

	    public Button getBtAdd(){
	        return btAdd;
	    }

	    public void setBtAdd(Button btAdd){
	        this.btAdd=btAdd;
	    }

	    public Button getBtDisplay(){
	        return btDisplay;
	    }

	    public void setBtDisplay(Button btDisplay){
	        this.btDisplay=btDisplay;
	    }

	    public Button getBtProcessNext(){
	        return btProcessNext;
	    }

	    public void setBtProcessNext(Button btProcessNext){
	        this.btProcessNext=btProcessNext;
	    }

	    public Button getBtCount(){
	        return btCount;
	    }

	    public void setBtCount(Button btCount){
	        this.btCount=btCount;
	    }

	    public Button getBtClear(){
	        return btClear;
	    }

	    public void setBtClear(Button btClear){
	        this.btClear=btClear;
	    }

	    public Label getTitle(){
	        return title;
	    }

	    public void setTitle(Label title){
	        this.title=title;
	    }

	    public Label getMessage(){
	        return message;
	    }

	    public void setMessage(Label message){
	        this.message=message;
	    }

	    public Label getCountLabel(){
	        return countLabel;
	    }

	    public void setCountLabel(Label countLabel){
	        this.countLabel=countLabel;
	    }
	}
 
 
 
 class UndoRedoTab extends Tab{
	    private UndoRedoManager undoRedoManager;
	    private BorderPane pane;
	    private VBox topPane;
	    private VBox centerPane;
	    private VBox mainPane;
	    private HBox buttonsPane;
	    private Label title;
	    private Label undoSizeLabel;
	    private Label redoSizeLabel;
	    private Label message;
	    private Button btUndo;
	    private Button btRedo;
	    private Button btClear;

	    public UndoRedoTab(UndoRedoManager undoRedoManager){
	        this.undoRedoManager=undoRedoManager;
	        setText("Undo / Redo");
	        setClosable(false);

	        pane=new BorderPane();
	        topPane=new VBox(10);
	        centerPane=new VBox(15);
	        mainPane=new VBox(20);
	        buttonsPane=new HBox(15);

	        title=new Label("History:");
	        undoSizeLabel=new Label("Next Undo: None (0)");
	        redoSizeLabel=new Label("Next Redo: None (0)");
	        message=new Label();

	        title.setStyle("-fx-font-weight: bold;");
	        undoSizeLabel.setStyle("-fx-font-weight: bold;");
	        redoSizeLabel.setStyle("-fx-font-weight: bold;");

	        btUndo=new Button("Undo");
	        btRedo=new Button("Redo");
	        btClear=new Button("Clear");

	        buttonsPane.setAlignment(Pos.CENTER);
	        buttonsPane.getChildren().addAll(title,undoSizeLabel,redoSizeLabel,btUndo,btRedo,btClear,message);

	        pane.setPadding(new Insets(8));
	        pane.setCenter(buttonsPane);

	        setContent(pane);
	    }

	    public void updateSizes(){
	        undoSizeLabel.setText("Next Undo: "+undoRedoManager.getNextUndoOperation()+" ("+undoRedoManager.getUndoSize()+")");
	        redoSizeLabel.setText("Next Redo: "+undoRedoManager.getNextRedoOperation()+" ("+undoRedoManager.getRedoSize()+")");
	    }

	    public UndoRedoManager getUndoRedoManager(){
	        return undoRedoManager;
	    }

	    public void setUndoRedoManager(UndoRedoManager undoRedoManager){
	        this.undoRedoManager=undoRedoManager;
	    }

	    public BorderPane getPane(){
	        return pane;
	    }

	    public void setPane(BorderPane pane){
	        this.pane=pane;
	    }

	    public VBox getTopPane(){
	        return topPane;
	    }

	    public void setTopPane(VBox topPane){
	        this.topPane=topPane;
	    }

	    public VBox getCenterPane(){
	        return centerPane;
	    }

	    public void setCenterPane(VBox centerPane){
	        this.centerPane=centerPane;
	    }

	    public VBox getMainPane(){
	        return mainPane;
	    }

	    public void setMainPane(VBox mainPane){
	        this.mainPane=mainPane;
	    }

	    public HBox getButtonsPane(){
	        return buttonsPane;
	    }

	    public void setButtonsPane(HBox buttonsPane){
	        this.buttonsPane=buttonsPane;
	    }

	    public Label getTitle(){
	        return title;
	    }

	    public void setTitle(Label title){
	        this.title=title;
	    }

	    public Label getUndoSizeLabel(){
	        return undoSizeLabel;
	    }

	    public void setUndoSizeLabel(Label undoSizeLabel){
	        this.undoSizeLabel=undoSizeLabel;
	    }

	    public Label getRedoSizeLabel(){
	        return redoSizeLabel;
	    }

	    public void setRedoSizeLabel(Label redoSizeLabel){
	        this.redoSizeLabel=redoSizeLabel;
	    }

	    public Label getMessage(){
	        return message;
	    }

	    public void setMessage(Label message){
	        this.message=message;
	    }

	    public Button getBtUndo(){
	        return btUndo;
	    }

	    public void setBtUndo(Button btUndo){
	        this.btUndo=btUndo;
	    }

	    public Button getBtRedo(){
	        return btRedo;
	    }

	    public void setBtRedo(Button btRedo){
	        this.btRedo=btRedo;
	    }

	    public Button getBtClear(){
	        return btClear;
	    }

	    public void setBtClear(Button btClear){
	        this.btClear=btClear;
	    }
	}
 
 class VehicleTab extends Tab{
	    private VehicleManager vehicles;
	    private BorderPane pane;
	    private VBox topPane;
	    private GridPane leftPane;
	    private VBox centerPane;
	    private VBox bottomPane;
	    private HBox buttonsPane;
	    private VBox mainPane;
	    private TableView<Vehicle> vehicleTable;
	    private TableColumn<Vehicle,Integer> vehicleIdColumn;
	    private TableColumn<Vehicle,String> makeColumn;
	    private TableColumn<Vehicle,String> modelColumn;
	    private TableColumn<Vehicle,Integer> yearColumn;
	    private TableColumn<Vehicle,Double> priceColumn;
	    private TableColumn<Vehicle,String> colorColumn;
	    private TableColumn<Vehicle,String> statusColumn;
	    private TextField tfVehicleId;
	    private TextField tfMake;
	    private TextField tfModel;
	    private TextField tfYear;
	    private TextField tfPrice;
	    private TextField tfColor;
	    private ComboBox<String> cbStatus;
	    private Button btAdd;
	    private Button btRemove;
	    private Button btUpdate;
	    private Button btSearchId;
	    private Button btSearchMake;
	    private Button btChangeStatus;
	    private Button btMarkSold;
	    private Button btAscending;
	    private Button btDescending;
	    private Button btClear;
	    private Label title;
	    private Label message;
	    private Button btHeight;

	    public VehicleTab(VehicleManager vehicles){
	        this.vehicles=vehicles;
	        setText("Vehicles");
	        setClosable(false);

	        pane=new BorderPane();
	        topPane=new VBox(10);
	        leftPane=new GridPane();
	        centerPane=new VBox(10);
	        bottomPane=new VBox(5);
	        buttonsPane=new HBox(10);
	        mainPane=new VBox(8);

	        title=new Label("Vehicle Management");
	        title.setStyle("-fx-font-weight: bold;");
	        message=new Label();

	        Label vehicleInformationLabel=new Label("Vehicle Information");
	        Label instructionLabel=new Label("Select a vehicle from the table before editing it");
	        Label mainOperationsLabel=new Label("Main Operations");
	        Label searchLabel=new Label("Search");
	        Label statusOperationsLabel=new Label("Status Operations");
	        Label displayOptionsLabel=new Label("Display Options");

	        vehicleInformationLabel.setStyle("-fx-font-weight: bold;");
	        instructionLabel.setStyle("-fx-font-weight: bold;");
	        mainOperationsLabel.setStyle("-fx-font-weight: bold;");
	        searchLabel.setStyle("-fx-font-weight: bold;");
	        statusOperationsLabel.setStyle("-fx-font-weight: bold;");
	        displayOptionsLabel.setStyle("-fx-font-weight: bold;");

	        tfVehicleId=new TextField();
	        tfVehicleId.setPromptText("Enter Vehicle ID");

	        tfMake=new TextField();
	        tfMake.setPromptText("Enter Vehicle Make");

	        tfModel=new TextField();
	        tfModel.setPromptText("Enter Vehicle Model");

	        tfYear=new TextField();
	        tfYear.setPromptText("Enter Vehicle Year");

	        tfPrice=new TextField();
	        tfPrice.setPromptText("Enter Vehicle Price");

	        tfColor=new TextField();
	        tfColor.setPromptText("Enter Vehicle Color");

	        cbStatus=new ComboBox<String>();
	        cbStatus.getItems().addAll("AVAILABLE","SOLD","IN_SERVICE","RESERVED");
	        cbStatus.setPromptText("Select Vehicle Status");

	        tfVehicleId.setPrefWidth(210);
	        tfMake.setPrefWidth(210);
	        tfModel.setPrefWidth(210);
	        tfYear.setPrefWidth(210);
	        tfPrice.setPrefWidth(210);
	        tfColor.setPrefWidth(210);
	        cbStatus.setPrefWidth(210);

	        btAdd=new Button("Add Vehicle");
	        btRemove=new Button("Remove Vehicle");
	        btUpdate=new Button("Update Vehicle");
	        btSearchId=new Button("Search by ID");
	        btSearchMake=new Button("Search by Make");
	        btChangeStatus=new Button("Change Status");
	        btMarkSold=new Button("Mark as Sold");
	        btAscending=new Button("Ascending");
	        btDescending=new Button("Descending");
	        btClear=new Button("Clear");
	        btHeight=new Button("Tree Height");

	        btAdd.setPrefSize(145,32);
	        btRemove.setPrefSize(145,32);
	        btUpdate.setPrefSize(145,32);
	        btSearchId.setPrefSize(145,32);
	        btSearchMake.setPrefSize(145,32);
	        btChangeStatus.setPrefSize(145,32);
	        btMarkSold.setPrefSize(145,32);
	        btAscending.setPrefSize(145,32);
	        btDescending.setPrefSize(145,32);
	        btClear.setPrefSize(145,32);
	        btHeight.setPrefSize(145,32);

	        vehicleTable=new TableView<Vehicle>();

	        vehicleIdColumn=new TableColumn<Vehicle,Integer>("Vehicle ID");
	        makeColumn=new TableColumn<Vehicle,String>("Make");
	        modelColumn=new TableColumn<Vehicle,String>("Model");
	        yearColumn=new TableColumn<Vehicle,Integer>("Year");
	        priceColumn=new TableColumn<Vehicle,Double>("Price");
	        colorColumn=new TableColumn<Vehicle,String>("Color");
	        statusColumn=new TableColumn<Vehicle,String>("Status");

	        vehicleIdColumn.setCellValueFactory(new PropertyValueFactory<Vehicle,Integer>("vehicleId"));
	        makeColumn.setCellValueFactory(new PropertyValueFactory<Vehicle,String>("make"));
	        modelColumn.setCellValueFactory(new PropertyValueFactory<Vehicle,String>("model"));
	        yearColumn.setCellValueFactory(new PropertyValueFactory<Vehicle,Integer>("year"));
	        priceColumn.setCellValueFactory(new PropertyValueFactory<Vehicle,Double>("price"));
	        colorColumn.setCellValueFactory(new PropertyValueFactory<Vehicle,String>("color"));
	        statusColumn.setCellValueFactory(new PropertyValueFactory<Vehicle,String>("status"));

	        vehicleTable.getColumns().addAll(vehicleIdColumn,makeColumn,modelColumn,yearColumn,priceColumn,colorColumn,statusColumn);
	        vehicleTable.setItems(vehicles.displayAllVehicles());
	        vehicleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        vehicleTable.setPrefHeight(520);

	        vehicleTable.setOnMouseClicked(e->{
	            Vehicle vehicle=vehicleTable.getSelectionModel().getSelectedItem();

	            if(vehicle!=null){
	                tfVehicleId.setText(String.valueOf(vehicle.getVehicleId()));
	                tfMake.setText(vehicle.getMake());
	                tfModel.setText(vehicle.getModel());
	                tfYear.setText(String.valueOf(vehicle.getYear()));
	                tfPrice.setText(String.valueOf(vehicle.getPrice()));
	                tfColor.setText(vehicle.getColor());
	                cbStatus.setValue(vehicle.getStatus());
	            }
	        });

	        topPane.getChildren().add(title);
	        topPane.setAlignment(Pos.CENTER);
	        topPane.setPadding(new Insets(8));

	        leftPane.setHgap(10);
	        leftPane.setVgap(8);
	        leftPane.setAlignment(Pos.CENTER);

	        leftPane.add(vehicleInformationLabel,0,0,2,1);
	        leftPane.add(instructionLabel,0,1,2,1);
	        leftPane.add(new Label("Vehicle ID:"),0,2);
	        leftPane.add(tfVehicleId,1,2);
	        leftPane.add(new Label("Make:"),0,3);
	        leftPane.add(tfMake,1,3);
	        leftPane.add(new Label("Model:"),0,4);
	        leftPane.add(tfModel,1,4);
	        leftPane.add(new Label("Year:"),0,5);
	        leftPane.add(tfYear,1,5);
	        leftPane.add(new Label("Price:"),0,6);
	        leftPane.add(tfPrice,1,6);
	        leftPane.add(new Label("Color:"),0,7);
	        leftPane.add(tfColor,1,7);
	        leftPane.add(new Label("Status:"),0,8);
	        leftPane.add(cbStatus,1,8);

	        buttonsPane.getChildren().addAll(btAdd,btUpdate,btRemove);
	        buttonsPane.setAlignment(Pos.CENTER);

	        centerPane.getChildren().addAll(vehicleTable,mainOperationsLabel,buttonsPane);
	        centerPane.setAlignment(Pos.CENTER);
	        centerPane.setPadding(new Insets(8));

	        HBox searchButtonsPane=new HBox(10);
	        searchButtonsPane.getChildren().addAll(btSearchId,btSearchMake);
	        searchButtonsPane.setAlignment(Pos.CENTER);

	        HBox statusButtonsPane=new HBox(10);
	        statusButtonsPane.getChildren().addAll(btChangeStatus,btMarkSold);
	        statusButtonsPane.setAlignment(Pos.CENTER);

	        HBox displayButtonsPane=new HBox(10);
	        displayButtonsPane.getChildren().addAll(btAscending,btDescending);
	        displayButtonsPane.setAlignment(Pos.CENTER);

	        HBox otherButtonsPane=new HBox(10);
	        otherButtonsPane.getChildren().addAll(btHeight,btClear);
	        otherButtonsPane.setAlignment(Pos.CENTER);

	        mainPane.getChildren().addAll(
	            leftPane,
	            searchLabel,
	            searchButtonsPane,
	            statusOperationsLabel,
	            statusButtonsPane,
	            displayOptionsLabel,
	            displayButtonsPane,
	            otherButtonsPane
	        );

	        mainPane.setAlignment(Pos.TOP_CENTER);
	        mainPane.setPadding(new Insets(8));
	        mainPane.setPrefWidth(430);

	        bottomPane.getChildren().add(message);
	        bottomPane.setAlignment(Pos.CENTER);
	        bottomPane.setPadding(new Insets(2));

	        pane.setTop(topPane);
	        pane.setCenter(centerPane);
	        pane.setRight(mainPane);
	        pane.setBottom(bottomPane);
	        pane.setPadding(new Insets(8));

	        setContent(pane);
	    }

	    public VehicleManager getVehicles(){
	        return vehicles;
	    }

	    public void setVehicles(VehicleManager vehicles){
	        this.vehicles=vehicles;
	    }

	    public BorderPane getPane(){
	        return pane;
	    }

	    public void setPane(BorderPane pane){
	        this.pane=pane;
	    }

	    public VBox getTopPane(){
	        return topPane;
	    }

	    public void setTopPane(VBox topPane){
	        this.topPane=topPane;
	    }

	    public GridPane getLeftPane(){
	        return leftPane;
	    }

	    public void setLeftPane(GridPane leftPane){
	        this.leftPane=leftPane;
	    }

	    public VBox getCenterPane(){
	        return centerPane;
	    }

	    public void setCenterPane(VBox centerPane){
	        this.centerPane=centerPane;
	    }

	    public VBox getBottomPane(){
	        return bottomPane;
	    }

	    public void setBottomPane(VBox bottomPane){
	        this.bottomPane=bottomPane;
	    }

	    public HBox getButtonsPane(){
	        return buttonsPane;
	    }

	    public void setButtonsPane(HBox buttonsPane){
	        this.buttonsPane=buttonsPane;
	    }

	    public VBox getMainPane(){
	        return mainPane;
	    }

	    public void setMainPane(VBox mainPane){
	        this.mainPane=mainPane;
	    }

	    public TableView<Vehicle> getVehicleTable(){
	        return vehicleTable;
	    }

	    public void setVehicleTable(TableView<Vehicle> vehicleTable){
	        this.vehicleTable=vehicleTable;
	    }

	    public TableColumn<Vehicle,Integer> getVehicleIdColumn(){
	        return vehicleIdColumn;
	    }

	    public void setVehicleIdColumn(TableColumn<Vehicle,Integer> vehicleIdColumn){
	        this.vehicleIdColumn=vehicleIdColumn;
	    }

	    public TableColumn<Vehicle,String> getMakeColumn(){
	        return makeColumn;
	    }

	    public void setMakeColumn(TableColumn<Vehicle,String> makeColumn){
	        this.makeColumn=makeColumn;
	    }

	    public TableColumn<Vehicle,String> getModelColumn(){
	        return modelColumn;
	    }

	    public void setModelColumn(TableColumn<Vehicle,String> modelColumn){
	        this.modelColumn=modelColumn;
	    }

	    public TableColumn<Vehicle,Integer> getYearColumn(){
	        return yearColumn;
	    }

	    public void setYearColumn(TableColumn<Vehicle,Integer> yearColumn){
	        this.yearColumn=yearColumn;
	    }

	    public TableColumn<Vehicle,Double> getPriceColumn(){
	        return priceColumn;
	    }

	    public void setPriceColumn(TableColumn<Vehicle,Double> priceColumn){
	        this.priceColumn=priceColumn;
	    }

	    public TableColumn<Vehicle,String> getColorColumn(){
	        return colorColumn;
	    }

	    public void setColorColumn(TableColumn<Vehicle,String> colorColumn){
	        this.colorColumn=colorColumn;
	    }

	    public TableColumn<Vehicle,String> getStatusColumn(){
	        return statusColumn;
	    }

	    public void setStatusColumn(TableColumn<Vehicle,String> statusColumn){
	        this.statusColumn=statusColumn;
	    }

	    public TextField getTfVehicleId(){
	        return tfVehicleId;
	    }

	    public void setTfVehicleId(TextField tfVehicleId){
	        this.tfVehicleId=tfVehicleId;
	    }

	    public TextField getTfMake(){
	        return tfMake;
	    }

	    public void setTfMake(TextField tfMake){
	        this.tfMake=tfMake;
	    }

	    public TextField getTfModel(){
	        return tfModel;
	    }

	    public void setTfModel(TextField tfModel){
	        this.tfModel=tfModel;
	    }

	    public TextField getTfYear(){
	        return tfYear;
	    }

	    public void setTfYear(TextField tfYear){
	        this.tfYear=tfYear;
	    }

	    public TextField getTfPrice(){
	        return tfPrice;
	    }

	    public void setTfPrice(TextField tfPrice){
	        this.tfPrice=tfPrice;
	    }

	    public TextField getTfColor(){
	        return tfColor;
	    }

	    public void setTfColor(TextField tfColor){
	        this.tfColor=tfColor;
	    }

	    public ComboBox<String> getCbStatus(){
	        return cbStatus;
	    }

	    public void setCbStatus(ComboBox<String> cbStatus){
	        this.cbStatus=cbStatus;
	    }

	    public Button getBtAdd(){
	        return btAdd;
	    }

	    public void setBtAdd(Button btAdd){
	        this.btAdd=btAdd;
	    }

	    public Button getBtRemove(){
	        return btRemove;
	    }

	    public void setBtRemove(Button btRemove){
	        this.btRemove=btRemove;
	    }

	    public Button getBtUpdate(){
	        return btUpdate;
	    }

	    public void setBtUpdate(Button btUpdate){
	        this.btUpdate=btUpdate;
	    }

	    public Button getBtSearchId(){
	        return btSearchId;
	    }

	    public void setBtSearchId(Button btSearchId){
	        this.btSearchId=btSearchId;
	    }

	    public Button getBtSearchMake(){
	        return btSearchMake;
	    }

	    public void setBtSearchMake(Button btSearchMake){
	        this.btSearchMake=btSearchMake;
	    }

	    public Button getBtChangeStatus(){
	        return btChangeStatus;
	    }

	    public void setBtChangeStatus(Button btChangeStatus){
	        this.btChangeStatus=btChangeStatus;
	    }

	    public Button getBtMarkSold(){
	        return btMarkSold;
	    }

	    public void setBtMarkSold(Button btMarkSold){
	        this.btMarkSold=btMarkSold;
	    }

	    public Button getBtAscending(){
	        return btAscending;
	    }

	    public void setBtAscending(Button btAscending){
	        this.btAscending=btAscending;
	    }

	    public Button getBtDescending(){
	        return btDescending;
	    }

	    public void setBtDescending(Button btDescending){
	        this.btDescending=btDescending;
	    }

	    public Button getBtClear(){
	        return btClear;
	    }

	    public void setBtClear(Button btClear){
	        this.btClear=btClear;
	    }

	    public Label getTitle(){
	        return title;
	    }

	    public void setTitle(Label title){
	        this.title=title;
	    }

	    public Label getMessage(){
	        return message;
	    }

	    public void setMessage(Label message){
	        this.message=message;
	    }

	    public Button getBtHeight(){
	        return btHeight;
	    }

	    public void setBtHeight(Button btHeight){
	        this.btHeight=btHeight;
	    }
	}
 
 
 
 class CustomerWaitingTab extends Tab{
	    private CustomerWaitingManager waiting;
	    private BorderPane pane;
	    private VBox topPane;
	    private GridPane leftPane;
	    private VBox centerPane;
	    private VBox bottomPane;
	    private HBox buttonsPane;
	    private VBox mainPane;
	    private TableView<Customer> customerTable;
	    private TableColumn<Customer,Integer> customerIdColumn;
	    private TableColumn<Customer,String> nameColumn;
	    private TableColumn<Customer,String> phoneColumn;
	    private TableColumn<Customer,String> addressColumn;
	    private TextField tfCustomerId;
	    private Button btAdd;
	    private Button btServeNext;
	    private Button btDisplay;
	    private Button btCount;
	    private Button btClear;
	    private Label title;
	    private Label message;
	    private Label countLabel;

	    public CustomerWaitingTab(CustomerWaitingManager waiting){
	        this.waiting=waiting;
	        setText("Customer Waiting");
	        setClosable(false);

	        pane=new BorderPane();
	        topPane=new VBox(10);
	        leftPane=new GridPane();
	        centerPane=new VBox(10);
	        bottomPane=new VBox(5);
	        buttonsPane=new HBox(10);
	        mainPane=new VBox(12);

	        title=new Label("Customer Waiting Queue");
	        title.setStyle("-fx-font-weight: bold;");
	        message=new Label();

	        countLabel=new Label("Waiting Customers: 0");
	        countLabel.setStyle("-fx-font-weight: bold;");

	        Label queueInformationLabel=new Label("Waiting Queue Information");
	        Label addInstructionLabel=new Label("Enter an existing customer ID to add it to the queue");
	        Label mainOperationsLabel=new Label("Main Operations");
	        Label serveNextLabel=new Label("Serve Next Customer");
	        Label serveInstructionLabel=new Label("Choose Maintenance, Sale, or Vehicle Reservation");
	        Label queueOptionsLabel=new Label("Queue Options");

	        queueInformationLabel.setStyle("-fx-font-weight: bold;");
	        addInstructionLabel.setStyle("-fx-font-weight: bold;");
	        mainOperationsLabel.setStyle("-fx-font-weight: bold;");
	        serveNextLabel.setStyle("-fx-font-weight: bold;");
	        serveInstructionLabel.setStyle("-fx-font-weight: bold;");
	        queueOptionsLabel.setStyle("-fx-font-weight: bold;");

	        tfCustomerId=new TextField();
	        tfCustomerId.setPromptText("Enter Customer ID");
	        tfCustomerId.setPrefWidth(210);

	        btAdd=new Button("Add to Queue");
	        btServeNext=new Button("Serve Next Customer");
	        btDisplay=new Button("Display Queue");
	        btCount=new Button("Update Count");
	        btClear=new Button("Clear");

	        btAdd.setPrefSize(160,32);
	        btServeNext.setPrefSize(160,32);
	        btDisplay.setPrefSize(145,32);
	        btCount.setPrefSize(145,32);
	        btClear.setPrefSize(145,32);

	        customerTable=new TableView<Customer>();

	        customerIdColumn=new TableColumn<Customer,Integer>("Customer ID");
	        nameColumn=new TableColumn<Customer,String>("Name");
	        phoneColumn=new TableColumn<Customer,String>("Phone");
	        addressColumn=new TableColumn<Customer,String>("Address");

	        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("customerId"));
	        nameColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
	        phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("phone"));
	        addressColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("address"));

	        customerTable.getColumns().addAll(customerIdColumn,nameColumn,phoneColumn,addressColumn);
	        customerTable.setItems(waiting.displayWaitingQueue());
	        customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        customerTable.setPrefHeight(560);

	        customerTable.setOnMouseClicked(e->{
	            Customer customer=customerTable.getSelectionModel().getSelectedItem();

	            if(customer!=null)
	                tfCustomerId.setText(String.valueOf(customer.getCustomerId()));
	        });

	        topPane.getChildren().add(title);
	        topPane.setAlignment(Pos.CENTER);
	        topPane.setPadding(new Insets(8));

	        leftPane.setHgap(10);
	        leftPane.setVgap(10);
	        leftPane.setAlignment(Pos.CENTER);

	        leftPane.add(queueInformationLabel,0,0,2,1);
	        leftPane.add(addInstructionLabel,0,1,2,1);
	        leftPane.add(new Label("Customer ID:"),0,2);
	        leftPane.add(tfCustomerId,1,2);

	        buttonsPane.getChildren().addAll(btAdd,btServeNext);
	        buttonsPane.setAlignment(Pos.CENTER);

	        centerPane.getChildren().addAll(customerTable,mainOperationsLabel,buttonsPane);
	        centerPane.setAlignment(Pos.CENTER);
	        centerPane.setPadding(new Insets(8));

	        HBox displayButtonsPane=new HBox(10);
	        displayButtonsPane.getChildren().addAll(btDisplay,btCount);
	        displayButtonsPane.setAlignment(Pos.CENTER);

	        HBox clearButtonPane=new HBox(10);
	        clearButtonPane.getChildren().add(btClear);
	        clearButtonPane.setAlignment(Pos.CENTER);

	        mainPane.getChildren().addAll(
	            leftPane,
	            countLabel,
	            serveNextLabel,
	            serveInstructionLabel,
	            queueOptionsLabel,
	            displayButtonsPane,
	            clearButtonPane
	        );

	        mainPane.setAlignment(Pos.TOP_CENTER);
	        mainPane.setPadding(new Insets(8));
	        mainPane.setPrefWidth(430);

	        bottomPane.getChildren().add(message);
	        bottomPane.setAlignment(Pos.CENTER);
	        bottomPane.setPadding(new Insets(2));

	        pane.setTop(topPane);
	        pane.setCenter(centerPane);
	        pane.setRight(mainPane);
	        pane.setBottom(bottomPane);
	        pane.setPadding(new Insets(8));

	        setContent(pane);
	    }

	    public CustomerWaitingManager getWaiting(){
	        return waiting;
	    }

	    public void setWaiting(CustomerWaitingManager waiting){
	        this.waiting=waiting;
	    }

	    public BorderPane getPane(){
	        return pane;
	    }

	    public void setPane(BorderPane pane){
	        this.pane=pane;
	    }

	    public VBox getTopPane(){
	        return topPane;
	    }

	    public void setTopPane(VBox topPane){
	        this.topPane=topPane;
	    }

	    public GridPane getLeftPane(){
	        return leftPane;
	    }

	    public void setLeftPane(GridPane leftPane){
	        this.leftPane=leftPane;
	    }

	    public VBox getCenterPane(){
	        return centerPane;
	    }

	    public void setCenterPane(VBox centerPane){
	        this.centerPane=centerPane;
	    }

	    public VBox getBottomPane(){
	        return bottomPane;
	    }

	    public void setBottomPane(VBox bottomPane){
	        this.bottomPane=bottomPane;
	    }

	    public HBox getButtonsPane(){
	        return buttonsPane;
	    }

	    public void setButtonsPane(HBox buttonsPane){
	        this.buttonsPane=buttonsPane;
	    }

	    public VBox getMainPane(){
	        return mainPane;
	    }

	    public void setMainPane(VBox mainPane){
	        this.mainPane=mainPane;
	    }

	    public TableView<Customer> getCustomerTable(){
	        return customerTable;
	    }

	    public void setCustomerTable(TableView<Customer> customerTable){
	        this.customerTable=customerTable;
	    }

	    public TextField getTfCustomerId(){
	        return tfCustomerId;
	    }

	    public void setTfCustomerId(TextField tfCustomerId){
	        this.tfCustomerId=tfCustomerId;
	    }

	    public Button getBtAdd(){
	        return btAdd;
	    }

	    public void setBtAdd(Button btAdd){
	        this.btAdd=btAdd;
	    }

	    public Button getBtServeNext(){
	        return btServeNext;
	    }

	    public void setBtServeNext(Button btServeNext){
	        this.btServeNext=btServeNext;
	    }

	    public Button getBtDisplay(){
	        return btDisplay;
	    }

	    public void setBtDisplay(Button btDisplay){
	        this.btDisplay=btDisplay;
	    }

	    public Button getBtCount(){
	        return btCount;
	    }

	    public void setBtCount(Button btCount){
	        this.btCount=btCount;
	    }

	    public Button getBtClear(){
	        return btClear;
	    }

	    public void setBtClear(Button btClear){
	        this.btClear=btClear;
	    }

	    public Label getTitle(){
	        return title;
	    }

	    public void setTitle(Label title){
	        this.title=title;
	    }

	    public Label getMessage(){
	        return message;
	    }

	    public void setMessage(Label message){
	        this.message=message;
	    }

	    public Label getCountLabel(){
	        return countLabel;
	    }

	    public void setCountLabel(Label countLabel){
	        this.countLabel=countLabel;
	    }
	}
 