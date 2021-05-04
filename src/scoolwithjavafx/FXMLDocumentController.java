
package scoolwithjavafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 *
 * @author Tsepetzidis Nikos
 */
public class FXMLDocumentController implements Initializable{
    
    @FXML
    private Button exitButton;
    @FXML
    private Button studentButton;
    @FXML
    private Button trainerButton;
    @FXML
    private Button courseButton;
    @FXML
    private Button assignmentButton;
    @FXML
    private Pane mainPane;
    @FXML
    private Button propertiesButton;
    
    @FXML
    private void handleButtonActionExit(ActionEvent event) {
        choose(studentButton);
        Platform.exit();
    }
    
    
    /**
     * Selects the Student button and shows the Student Pane
     * @param event 
     */
    @FXML
    private void handleButtonActionStudent(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("StudentPane");
        mainPane.getChildren().addAll(view);
        choose(studentButton);
        
    }
    
    /**
     * Selects the Trainer button and shows the Trainer Pane
     * @param event 
     */
    @FXML
    private void handleButtonActionTrainer(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("TrainerPane");
        mainPane.getChildren().addAll(view);
        choose(trainerButton);
    }
    
    
    /**
     * Selects the Course button and shows the Course Pane
     * @param event 
     */
    @FXML
    private void handleButtonActionCourse(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("CoursePane");
        mainPane.getChildren().addAll(view);
        choose(courseButton);
        
    }
    
    
    /**
     * Selects the Assignment button and shows the Assignment Pane
     * @param event 
     */
    @FXML
    private void handleButtonActionAssignment(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("AssignmentPane");
        mainPane.getChildren().addAll(view);
        choose(assignmentButton);
        
    }
    
    
    /**
     * Selects the Properties button and shows the Properties Pane
     * @param event 
     */
    @FXML
    private void handleButtonActionProperties(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("PropertiesPane");
        mainPane.getChildren().addAll(view);
        choose(propertiesButton);
        
    }
    
    
    /**
     * Chooses as first window the StudentPane
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("StudentPane");
        mainPane.getChildren().addAll(view);
        
        choose(studentButton);
    }
    
    
    /**
     * Deselects all buttons and selects the given button
     * @param b 
     */
    public void choose(Button b){
        
        studentButton.getStyleClass().clear();
        studentButton.getStyleClass().add("button1");
        trainerButton.getStyleClass().clear();
        trainerButton.getStyleClass().add("button1");
        courseButton.getStyleClass().clear();
        courseButton.getStyleClass().add("button1");
        assignmentButton.getStyleClass().clear();
        assignmentButton.getStyleClass().add("button1");
        propertiesButton.getStyleClass().clear();
        propertiesButton.getStyleClass().add("button1");
        exitButton.getStyleClass().clear();
        exitButton.getStyleClass().add("button1");
        
        b.getStyleClass().clear();
        b.getStyleClass().add("button2");
        
    }

   
    
}
