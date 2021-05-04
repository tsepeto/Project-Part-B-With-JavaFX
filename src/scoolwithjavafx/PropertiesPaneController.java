
package scoolwithjavafx;

import Entities.dao.GenericDao;
import Utils.MyNotification;
import Utils.NumberTextField;
import Utils.YesOrNoWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Tsepetzidis Nikos
 */
public class PropertiesPaneController implements Initializable {

    @FXML
    private CheckBox serverCheckButton;
    @FXML
    private CheckBox databaseCheckButton;
    @FXML
    private CheckBox readyCheckButton;
    @FXML
    private Button cancelSaveDetailsButton;
    @FXML
    private Button saveDetailsButton;
    @FXML
    private Button editDetailsButton;
    @FXML
    private TextField urlDomainTextField;
    @FXML
    private NumberTextField serverPortTextField;
    @FXML
    private TextField databaseNameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button restoreToDefaultButton;
    @FXML
    private TextArea attentionMessage;
    
    GenericDao data = GenericDao.getInstance();
    MyNotification notification = new MyNotification();
    private YesOrNoWindow alert = new YesOrNoWindow();
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        serverCheckButton.setDisable(true);
        databaseCheckButton.setDisable(true);
        readyCheckButton.setDisable(true);
        
        attentionMessage.setWrapText(true);
        
        setAttentionMessage();
        showDBProperties();
        getConnectionStatus();
        
    }    

    
    /**
     * Returns the program to search mode
     * @param event 
     */
    @FXML
    private void cancelSaveDetails(MouseEvent event) {
        norlamMode();
        notification.info("You canceled the editing procedure!");
        showDBProperties();
    }

    
    /**
     *Save the server details and updates the connection status 
     * @param event 
     */
    @FXML
    private void saveDetails(MouseEvent event) {
        
        if(urlDomainTextField.getText().equals("")){
            notification.warning("Please don't let blank the Server's Url");
        }
        else if(serverPortTextField.getText().equals("")){
            notification.warning("Please don't let blank the Server's Port");
        }
        else if(databaseNameTextField.getText().equals("")){
            notification.warning("Please don't let blank the Server's Database Name");
        }
        else if(usernameTextField.getText().equals("")){
            notification.warning("Please don't let blank the Username");
        }
        else{
            boolean result = alert.show("Confirmation!", "Do you want to save the changes?");
            
            if(result){
                data.setServer(urlDomainTextField.getText());
                data.setPort(serverPortTextField.getText());
                data.setDataBase(databaseNameTextField.getText());
                data.setUsername(usernameTextField.getText());
                data.setPassword(passwordTextField.getText());
                data.setProperties();
            }
        }
        norlamMode();
        showDBProperties();
        getConnectionStatus();
        
       
    }

    
    /**
     * Sets the program in edit mode
     * @param event 
     */
    @FXML
    private void editDetails(MouseEvent event) {
        restoreToDefaultButton.setDisable(true);
        restoreToDefaultButton.setVisible(false);
        
        cancelSaveDetailsButton.setDisable(false);
        cancelSaveDetailsButton.setVisible(true);
        saveDetailsButton.setDisable(false);
        saveDetailsButton.setVisible(true);
        editDetailsButton.setDisable(true);
        editDetailsButton.setVisible(false);
        
        
        urlDomainTextField.setEditable(true);
        serverPortTextField.setEditable(true);
        databaseNameTextField.setEditable(true);
        usernameTextField.setEditable(true);
        passwordTextField.setEditable(true);
        
        
    }

    
    /**
     * Restores the database properties in to default 
     * @param event 
     */
    @FXML
    private void restoreToDefault(MouseEvent event) {
        
        boolean result = alert.show("Confirmation!", "Do you want to set the connection to default?");
            
        if(result){
            data.setDefaultProperties();
            showDBProperties();
            getConnectionStatus();
            notification.success("You set the connetion properties to default!");
        }
        
    }
    
    
    /**
     * Check the connection status
     */
    private void getConnectionStatus(){
        serverCheckButton.setSelected(false);
        serverCheckButton.setText("Server Disconnected");
        databaseCheckButton.setSelected(false);
        databaseCheckButton.setText("Database Disconnected");
        readyCheckButton.setSelected(false);
        readyCheckButton.setText("Can't Check Database");
        
        boolean serverConnection = data.checkServerConnection();
        
        
        if (serverConnection == true){
            serverCheckButton.setSelected(true);
            serverCheckButton.setText("Server Connected");
            boolean databaseConnection = data.checkDbConnection();
            if(databaseConnection == true){
                databaseCheckButton.setSelected(true);
                databaseCheckButton.setText("Database Connected");
                String tablesConnection = data.checkTables();
                
                if(tablesConnection.equals("EMPTY DB")){
                    notification.warning("The database is empty. Please choose another database, or create the necessary tables!");
                    readyCheckButton.setText("The Database is empty");
                }
                else if (tablesConnection.equals("WRONG DATABASE")){
                    notification.warning("The database is has not the right form. Please choose another database!");
                    readyCheckButton.setText("The Database is wrong!!!");
                }
                else if(tablesConnection.equals("OK")){
                    notification.success("The program is ready for use!");
                    readyCheckButton.setText("The Database is READY!!!");
                    readyCheckButton.setSelected(true);
                }
            }
        }
        
        
    }
    
    /**
     * Restores the program to the search mode
     */
    private void norlamMode(){
        restoreToDefaultButton.setDisable(false);
        restoreToDefaultButton.setVisible(true);
        
        cancelSaveDetailsButton.setDisable(true);
        cancelSaveDetailsButton.setVisible(false);
        saveDetailsButton.setDisable(true);
        saveDetailsButton.setVisible(false);
        editDetailsButton.setDisable(false);
        editDetailsButton.setVisible(true);
        
        
        urlDomainTextField.setEditable(false);
        serverPortTextField.setEditable(false);
        databaseNameTextField.setEditable(false);
        usernameTextField.setEditable(false);
        passwordTextField.setEditable(false);
    }
    
    
    /**
     * Sets the values in details TextFields
     */
    private void showDBProperties(){
        urlDomainTextField.setText(data.getServer());
        serverPortTextField.setText(data.getPort());
        databaseNameTextField.setText(data.getDataBase());
        usernameTextField.setText(data.getUsername());
        passwordTextField.setText(data.getPassword());
    }
    
    
    /**
     * Sets the attention message to TextArea
     */
    private void setAttentionMessage(){
        String message = "Be careful when changing server settings.\n" +
"  You can not use the program unless you first connect to the database.\n" +
"  If you changed the settings and want to reset the program to the default properties please click the \"Restore to Default Properties\" button!";
        attentionMessage.setText(message);
    }
    
}
