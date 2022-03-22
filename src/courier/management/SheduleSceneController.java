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
public class SheduleSceneController implements Initializable {

    @FXML
    private AnchorPane sheduleScene;
    @FXML
    private Button sumitBtn;
    @FXML
    private DatePicker dateTime;
    @FXML
    private ComboBox<String> cBranch;
    @FXML
    private ComboBox<String> cStaff;
    @FXML
    private TableView<?> submitTable;
   

    /**
     * Initializes the controller class.
     */
     ObservableList<String> cList = FXCollections.observableArrayList(); 
     ObservableList<String> bList = FXCollections.observableArrayList(); 
     ObservableList<String> sList = FXCollections.observableArrayList(); 
    
     @FXML
    private TextField cNumber;
     DisplayDatabase SheduleData =new  DisplayDatabase();
    @FXML
    private Label warnMsg;
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SheduleData.buildData(submitTable, "Select * from Sheduletable;");
        
         ResultSet rs = QueryDatabase.QueryDatabase("Select branchcode from branchTable;");
        if(rs!=null){
            try {
                while(rs.next()){
                    bList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SheduleSceneController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SheduleSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
       AutoCompletionBinding<String> auto = TextFields.bindAutoCompletion(cNumber,cList); 
       
    }    
  LocalDateTime sDate;
String courierNumber = "";
String branchCode = "";
String staffName = "";
    @FXML
    private void submit(ActionEvent event) {
        try {
             getSheduleFields();
         
             ResultSet rs = QueryDatabase.QueryDatabase("Select BId from BookingTable where BID='"+courierNumber+"';");
             if(rs==null){
                 
                     warnMsg.setText("The Courier Number is Not Booked");
                     return;
                 
             }
               rs = QueryDatabase.QueryDatabase("Select CourNum from sheduleTable where CourNum='"+courierNumber+"';");
             if(rs!=null){
                 if(rs.next()){
                     warnMsg.setText("The Courier Number is already scheduled.");
                     return;
                 }
             }
             
             Connection c;
            c = DBConnection.connect();
         
            
            
            
            String query = "INSERT INTO courier.sheduleTable (date,cournum,branchcode,staffname)VALUES("+
 "'"+sDate+"',\n" +
 "'"+courierNumber+"',\n" +
"'"+branchCode+"',\n" +
"'"+staffName+"');";            
            
      
           c.createStatement().execute(query);
            
            c.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(SheduleSceneController .class.getName()).log(Level.SEVERE, null, ex);
        }clearFields();
            
   
      SheduleData.buildData(submitTable, "Select * from Sheduletable;");
        
    }

    @FXML
    private void getStaff(ActionEvent event) {
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
      
        cStaff.setItems(sList);
        
    }

    private void getSheduleFields() {
       sDate = LocalDateTime.of( dateTime.getValue(),LocalTime.now());
        courierNumber = cNumber.getText();
        branchCode = cBranch.getValue();
        staffName = cStaff.getValue();

    }

    private void clearFields() {
        cNumber.clear();
        warnMsg.setText("");
    }

    @FXML
    private void DeleteShedule(ActionEvent event) {
        int index = submitTable.getSelectionModel().getSelectedIndex();
         ObservableList data = SheduleData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "Sheduletable","ShId");
         
         SheduleData.buildData(submitTable, "Select * from Sheduletable;");
    }
    
}
