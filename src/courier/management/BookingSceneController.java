/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courier.management;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class BookingSceneController implements Initializable {

    @FXML
    private TableView<String> bookTable;
    ObservableList<String> bookingList = FXCollections.observableArrayList();
    @FXML
    private TextField courAmount;
    @FXML
    private TextField toName;
    @FXML
    private TextField toAdrs;
    @FXML
    private TextField toPin;
    @FXML
    private TextField courWeight;
    @FXML
    private TextField courType;
    @FXML
    private TextField fromName;
    @FXML
    private TextField fromAdrs;
    @FXML
    private TextField fromContact;
    @FXML
    private DatePicker bDate;
    @FXML
    private Button submitBtn;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    DisplayDatabase bookTableData =new  DisplayDatabase();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         bookTableData.buildData(bookTable, "Select * from bookingtable;");
        // TODO
    }    
String nameTo =("");
String adrsTo =("");
String pinTo =("");
String CourWeight =("");
String CourType =("");
String nameFrom =("");
String adrsFrom =("");
String fromCntc = ("");
double courAmnt = 0;

LocalDateTime dateB;
 
    


    private void getBookingFields() {
        nameTo = toName.getText();
        adrsTo = toAdrs.getText();
        pinTo = toPin.getText();
        CourWeight = courWeight.getText();
        CourType = courType.getText();
        courAmnt = Double.parseDouble(courAmount.getText());
        nameFrom = fromName.getText();
        adrsFrom = fromAdrs.getText();
        fromCntc = fromContact.getText();
        dateB = LocalDateTime.of( bDate.getValue(),LocalTime.now());
        
        

    }

   
    @FXML
    private void subBooking(ActionEvent event) {
         try {
             getBookingFields();
         Connection c;
         if(!update){
            c = DBConnection.connect();
            String query = "INSERT INTO courier.bookingtable (ToName,ToAddress,ToPin,CourWeight,CourType,Amount,FromName,FromAddress,FromContact,date)VALUES("+
 "'"+nameTo+"',\n" +
 "'"+adrsTo+"',\n" +
"'"+pinTo+"',\n" +
"'"+CourWeight+"',\n" +
"'"+CourType+"',\n" +
"'"+courAmnt+"',\n" +
"'"+nameFrom+"',\n" +
"'"+adrsFrom+"',\n" +
"'"+fromCntc+"',\n" +
"'"+dateB+"');";            
                   
                   
            
        
            c.createStatement().execute(query);
             }else{
              c = DBConnection.connect();
           String query = "Update bookingtable set ToName='"+nameTo+"',ToAddress='"+adrsTo+"',ToPin='"+pinTo+"',"
                   + "CourWeight='"+CourWeight+"',CourType='"+CourType+"',Amount='"+courAmnt+"',FromName='"+nameFrom+"',FromAddress='"+adrsFrom+"',FromContact='"+fromCntc+"',date='"+dateB+"' Where BId='"+id+"';";
                  System.out.println(query);
           c.createStatement().executeUpdate(query);
          } 
            c.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(BookingSceneController .class.getName()).log(Level.SEVERE, null, ex);
        }clearFields();
            
   
         bookTableData.buildData(bookTable, "Select * from bookingtable;");
    }
 private void clearFields() {
        toName.clear();
        toAdrs.clear();
        toPin.clear();        
        courWeight.clear();        
         courType.clear();      
         courAmount.clear();  
        fromName.clear();        
        fromAdrs.clear();
        fromContact .clear(); 
        
    update = false;
      submitBtn.setText("Submit");
    }
     boolean update = false;
    int id;
     DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void updateBooking(ActionEvent event) {
          int index = bookTable.getSelectionModel().getFocusedIndex();
      ObservableList<ObservableList> data = bookTableData.getData();
      ObservableList<String> itemData = data.get(index);
      
      
      id = Integer.parseInt(itemData.get(0));
      toName.setText(itemData.get(1));
        toAdrs.setText(itemData.get(2));
        toPin.setText(itemData.get(3));
        courWeight.setText(itemData.get(4));
        courType.setText(itemData.get(5));
        courAmount.setText(itemData.get(6));
        fromName.setText(itemData.get(7));
        fromAdrs.setText(itemData.get(8));
        fromContact.setText(itemData.get(9));
//        bDate.setValue(LocalDateTime.parse(itemData.get(10),format).toLocalDate());
       String[] sdate = itemData.get(10).split(" ");  // get only date from DateTime
               bDate.setValue(LocalDate.parse(sdate[0],dateformat));
            
        update=true;
        submitBtn.setText("Update");
    }

    @FXML
    private void deleteBooking(ActionEvent event) {
      int index = bookTable.getSelectionModel().getSelectedIndex();
         ObservableList data = bookTableData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "bookingtable","BId");
         bookTableData.buildData(bookTable, "Select * from bookingtable;");
    }
    
    
    
    }
    

    

