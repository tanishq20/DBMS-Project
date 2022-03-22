/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courier.management;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import Model.Staff;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class StaffSceneController implements Initializable {

    @FXML
    private AnchorPane staffScene;
    @FXML
    private Button addStaffBtn;
    @FXML
    private TextField branchCode;
    @FXML
    private TextField branchName;
    @FXML
    private TextField branchAdrs;
    @FXML
    private TextField branchPin;
    @FXML
    private TextField staffName;
    @FXML
    private TextField staffCntc;
    @FXML
    private TextArea staffAdrs;
    @FXML
    private TableView<Staff> sTableView;
    @FXML
    private TextField srchBranchCode;
    @FXML
    private Button srchBranchBtn;
    @FXML
    private TableView<?> bTableView;
    @FXML
    private Button subBranchBTn;
    @FXML
    private Label warnMsg;

    /**
     * Initializes the controller class.
     */
    
    DisplayDatabase branchData = new DisplayDatabase();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        branchData.buildData(bTableView, "Select * from branchTable;");
        createStaffTable();
    }    
String bCode = "";
String bName = "";
String bAdd = "";
String bPin = "";
String sname = "";
String sCntc = "";
String sAdrs = "";  

ObservableList<Staff> staffList = FXCollections.observableArrayList();

    @FXML
    private void addStaff(ActionEvent event) {
        
        getStaffFields();
                
        staffList.add(new Staff(sname,sCntc,sAdrs));
        sTableView.setItems(staffList);
        
        clearFields();
            
   
     //bookTableData.buildData(bookTable, "Select * from bookingtable;");
    }

    private void getStaffFields() {
        sname = staffName.getText();
        sCntc = staffCntc.getText();
        sAdrs = staffAdrs.getText();
    }

    private void clearFields() {
        staffName.clear();
        staffCntc.clear();
        staffAdrs.clear();

    }
    
    
    
    
    


    private void createStaffTable() {
       
        TableColumn col_name = new TableColumn("Name");
        col_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Staff,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Staff, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getName());              
           }            
         });  
        sTableView.getColumns().addAll(col_name);
        
           TableColumn col_cont = new TableColumn("Contact");
        col_cont.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Staff,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Staff, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getContact());              
           }            
         });  
        sTableView.getColumns().addAll(col_cont);
        
           TableColumn col_add = new TableColumn("Address");
        col_add.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Staff,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Staff, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getAdd());              
           }            
         }); 
        
        sTableView.getColumns().addAll(col_add);
        
        
        
    }

    @FXML
    private void mDeleteStaff(ActionEvent event) {
        int index = sTableView.getSelectionModel().getSelectedIndex();
        staffList.remove(index);
        sTableView.refresh();
    }

    @FXML
    private void submitBranch(ActionEvent event) {
        
       boolean val =  getBranchFields();
       if(!val){
       return;
       }
       
       Connection c;
       try{
           c = DBConnection.connect();
            String query = "INSERT INTO BranchTable (branchcode,branchName,branchAddress,Bpin)VALUES("+
"'"+bCode+"',\n" +
"'"+bName+"',\n" +
"'"+bAdd+"',\n" +
"'"+bPin+"');";                    
         
            c.createStatement().execute(query);
            
            for(Staff i: staffList){
          
             query = "INSERT INTO staffTable (BCode,StaffName,StaffContact,StaffAddress)VALUES("+
                "'"+bCode+"',\n" +
                "'"+i.getName()+"',\n" +
                "'"+i.getContact()+"',\n" +
                "'"+i.getAdd()+"');";                    
         
            c.createStatement().execute(query);
            
            }
            
            
            
            c.close();
       
       } catch (SQLException ex) {
            Logger.getLogger(StaffSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clearAllFields();
         branchData.buildData(bTableView, "Select * from branchTable;");
    }

    @FXML
    private void searchBranch(ActionEvent event) {
    }

    private boolean getBranchFields() {
        
        bCode = branchCode.getText();
        bName = branchName.getText();
        bAdd = branchAdrs.getText();
        bPin = branchPin.getText();
        
        if(bCode==null || bCode.isEmpty()){
            branchCode.requestFocus();
            warnMsg.setText("Pls enter branch code.");
            return false;
        }
        
        if(bName==null || bName.isEmpty()){
            branchName.requestFocus();
            warnMsg.setText("Pls enter branch Name.");
            return false;
        }
        
        if(bAdd==null || bAdd.isEmpty()){
            branchAdrs.requestFocus();
            warnMsg.setText("Pls enter branch Address.");
            return false;
        }
        if(bPin==null || bPin.isEmpty()){
            branchPin.requestFocus();
            warnMsg.setText("Pls enter branch Pin.");
            return false;
        }
        
        return true;
    }

    private void clearAllFields() {
        branchName.clear();
        branchCode.clear();
        branchAdrs.clear();
        branchPin.clear();
        staffList.clear();
        sTableView.refresh();
        
    }

    @FXML
    private void mDeleteBranch(ActionEvent event) {
         int index = bTableView.getSelectionModel().getSelectedIndex();
         ObservableList data = branchData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "BranchTable","Id");
         
         branchData.buildData(bTableView, "Select * from branchTable;");
    }
    
}
