package phaseTwo;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class FileMenu extends MenuBar{
    private Menu fileMenu;
    private Menu readMenu;
    private Menu saveMenu;
    private MenuItem readCustomers;
    private MenuItem readVehicles;
    private MenuItem readRequests;
    private MenuItem readServices;
    private MenuItem readTransactions;
    private MenuItem saveCustomers;
    private MenuItem saveVehicles;
    private MenuItem saveRequests;
    private MenuItem saveServices;
    private MenuItem saveTransactions;
    private MenuItem exit;

    public FileMenu(){
        fileMenu=new Menu("File");
        readMenu=new Menu("Read");
        saveMenu=new Menu("Save");

        readCustomers=new MenuItem("Customers");
        readVehicles=new MenuItem("Vehicles");
        readRequests=new MenuItem("Service Requests");
        readServices=new MenuItem("Services");
        readTransactions=new MenuItem("Transactions");

        saveCustomers=new MenuItem("Customers");
        saveVehicles=new MenuItem("Vehicles");
        saveRequests=new MenuItem("Service Requests");
        saveServices=new MenuItem("Services");
        saveTransactions=new MenuItem("Transactions");

        exit=new MenuItem("Exit");

        readMenu.getItems().addAll(readCustomers,readVehicles,readRequests,readServices,readTransactions);
        saveMenu.getItems().addAll(saveCustomers,saveVehicles,saveRequests,saveServices,saveTransactions);
        fileMenu.getItems().addAll(readMenu,saveMenu,exit);
        getMenus().add(fileMenu);
    }

    public Menu getFileMenu(){
        return fileMenu;
    }

    public void setFileMenu(Menu fileMenu){
        this.fileMenu=fileMenu;
    }

    public Menu getReadMenu(){
        return readMenu;
    }

    public void setReadMenu(Menu readMenu){
        this.readMenu=readMenu;
    }

    public Menu getSaveMenu(){
        return saveMenu;
    }

    public void setSaveMenu(Menu saveMenu){
        this.saveMenu=saveMenu;
    }

    public MenuItem getReadCustomers(){
        return readCustomers;
    }

    public void setReadCustomers(MenuItem readCustomers){
        this.readCustomers=readCustomers;
    }

    public MenuItem getReadVehicles(){
        return readVehicles;
    }

    public void setReadVehicles(MenuItem readVehicles){
        this.readVehicles=readVehicles;
    }

    public MenuItem getReadRequests(){
        return readRequests;
    }

    public void setReadRequests(MenuItem readRequests){
        this.readRequests=readRequests;
    }

    public MenuItem getReadServices(){
        return readServices;
    }

    public void setReadServices(MenuItem readServices){
        this.readServices=readServices;
    }

    public MenuItem getReadTransactions(){
        return readTransactions;
    }

    public void setReadTransactions(MenuItem readTransactions){
        this.readTransactions=readTransactions;
    }

    public MenuItem getSaveCustomers(){
        return saveCustomers;
    }

    public void setSaveCustomers(MenuItem saveCustomers){
        this.saveCustomers=saveCustomers;
    }

    public MenuItem getSaveVehicles(){
        return saveVehicles;
    }

    public void setSaveVehicles(MenuItem saveVehicles){
        this.saveVehicles=saveVehicles;
    }

    public MenuItem getSaveRequests(){
        return saveRequests;
    }

    public void setSaveRequests(MenuItem saveRequests){
        this.saveRequests=saveRequests;
    }

    public MenuItem getSaveServices(){
        return saveServices;
    }

    public void setSaveServices(MenuItem saveServices){
        this.saveServices=saveServices;
    }

    public MenuItem getSaveTransactions(){
        return saveTransactions;
    }

    public void setSaveTransactions(MenuItem saveTransactions){
        this.saveTransactions=saveTransactions;
    }

    public MenuItem getExit(){
        return exit;
    }

    public void setExit(MenuItem exit){
        this.exit=exit;
    }
}