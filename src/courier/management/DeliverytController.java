/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courier.management;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import DB.QueryDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class DeliverytController implements Initializable {

    @FXML
    private AnchorPane deliveryScene;
    @FXML
    private DatePicker deliveredOn;
    @FXML
    private ComboBox<String> staffNameComboBtn;
    @FXML
    private TextField DeliveredTo;
    @FXML
    private Button submitDeliveryBTn;
    @FXML
    private TableView<?> deliveryTable;
    @FXML
    private TextField courNumber;
    @FXML
    private ComboBox<String> cBranch;
    ObservableList<String> cList = FXCollections.observableArrayList(); 
     ObservableList<String> bList = FXCollections.observableArrayList(); 
     ObservableList<String> sList = FXCollections.observableArrayList();
      DisplayDatabase DeliveryData =new  DisplayDatabase();
    @FXML
    private Label warnMsg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DeliveryData.buildData(deliveryTable, "Select * from DeliveryTable;");
        
         ResultSet rs = QueryDatabase.QueryDatabase("Select branchcode from branchTable;");
        if(rs!=null){
            try {
                while   (rs.next()){
                    bList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DeliverytController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
        cBranch.setItems(bList);
        
        
        // TODO
         rs = QueryDatabase.QueryDatabase("Select BId from BookingTable where BID NOT IN (Select CourNum from sheduleTable);");
        if(rs!=null){
            try {
                while(rs.next()){
                    cList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DeliverytController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
       AutoCompletionBinding<String> auto = TextFields.bindAutoCompletion(courNumber,cList); 
       
    }    
  LocalDateTime dDate;
String courierNumber = "";
String branchCode = "";
String staffName = "";
String deliveredTo = "";
        // TODO
       

    @FXML
    private void btnStaffNameCombo(ActionEvent event) {
    }

    @FXML
    private void btnSubmitDelivery(ActionEvent event) {
        try {
             getDeliveryFields();
         
             ResultSet rs = QueryDatabase.QueryDatabase("Select BId from BookingTable where BId='"+courierNumber+"';");
             if(rs!=null){
                 if(rs.next())
                 {
                      warnMsg.setText("");
                 }else{
                 warnMsg.setText("The Courier Number is Not Booked");
                     return;
                   }    
             }else{
                 warnMsg.setText("The Courier Number is Not Booked");
                     return;
                 
             }
               rs = QueryDatabase.QueryDatabase("Select CourNum from DeliveryTable where CourNum='"+courierNumber+"';");
             if(rs!=null){
                 if(rs.next()){
                     warnMsg.setText("The Courier Number is already Delivered.");
                     return;
                 }
             }
             
             Connection c;
            c = DBConnection.connect();
         
            
            
            
            String query = "INSERT INTO courier.DeliveryTable (courNumber,branchcode,staffname,deliveredTo,Ddate)VALUES("+
 
 "'"+courierNumber+"',\n" +
"'"+branchCode+"',\n" +
"'"+staffName+"',\n" +
"'"+deliveredTo+"',\n" +
"'"+dDate+"');";             
            
      
           c.createStatement().execute(query);
            
            c.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(SheduleSceneController .class.getName()).log(Level.SEVERE, null, ex);
        }clearFields();
            
   
      DeliveryData.buildData(deliveryTable, "Select * from DeliveryTable;");
        
    }

    @FXML
    private void setStaff(ActionEvent event) {
        sList.clear();
        String brCode = cBranch.getValue();
         ResultSet rs = QueryDatabase.QueryDatabase("Select StaffName from staffTable where bcode = '"+brCode+"';");
        if(rs!=null){
            try {
                while(rs.next()){
                    sList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SheduleSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
        staffNameComboBtn.setItems(sList);
        
    }

    private void clearFields() {
        DeliveredTo.clear();
       courNumber.clear();
       warnMsg.setText("");         
    }

    private void getDeliveryFields() {
         dDate = LocalDateTime.of( deliveredOn.getValue(),LocalTime.now());
 courierNumber = courNumber.getText();
 branchCode = cBranch.getValue();
 staffName = staffNameComboBtn.getValue();
deliveredTo = DeliveredTo.getText();
        
    }

    @FXML
    private void DeleteDelivery(ActionEvent event) {
        int index = deliveryTable.getSelectionModel().getSelectedIndex();
         ObservableList data = DeliveryData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "DeliveryTable", "DId");
         
         DeliveryData.buildData(deliveryTable, "Select * from DeliveryTable;");
    }
    
}
