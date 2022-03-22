/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courier.management;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Wipro
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private BorderPane rootLayout;
    @FXML
    private ToggleButton setCourierSceneBtn;
    @FXML
    private ToggleGroup g1;
    @FXML
    private ToggleButton setStaffSceneBtn;
    @FXML
    private ToggleButton setSheduleSceneBtn;
    @FXML
    private ToggleButton setDeliverySceneBtn;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         changeScene("BookingScene.fxml");
    }    
 
   public  void changeScene(String scenePath){
        
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource(scenePath));
        AnchorPane pane = new AnchorPane();
    try{
            pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);
        }
        catch(Exception e){
        }
    }

   
    

    @FXML
    private void btnSetCourierScene(ActionEvent event) {
        changeScene("BookingScene.fxml");
    }

    @FXML
    private void btnSetStaffScene(ActionEvent event) {
        changeScene("StaffScene.fxml");
    
    }

    @FXML
    private void btnSetSheduleScene(ActionEvent event) {
        changeScene("Shedule.fxml");
    }

    @FXML
    private void btnSetDeliveryScene(ActionEvent event) {
        changeScene("DeliveryScene.fxml");
    }
    
}
 
     
    