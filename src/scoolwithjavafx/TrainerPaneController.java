
package scoolwithjavafx;

import Utils.MyNotification;
import Utils.MyTableColumn;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import Entities.Course;
import Entities.Trainer;
import Entities.dao.GenericDao;
import Utils.LetterTextField;
import Utils.YesOrNoWindow;

/**
 * FXML Controller class
 *
 * @author Tsepetzidis Nikos
 */
public class TrainerPaneController implements Initializable {

    @FXML
    private TableView<Trainer> trainersTable;
    @FXML
    private TableColumn<Trainer, String> trainersFName;
    @FXML
    private TableColumn<Trainer, String> trainersLName;
    @FXML
    private Button addTrainerButton;
    @FXML
    private Button deleteTrainerButton;
    @FXML
    private Button cancelTheSaveButton;
    @FXML
    private Button saveDetailsButton;
    @FXML
    private Button editDetailsButton;
    @FXML
    private LetterTextField firstNameText;
    @FXML
    private LetterTextField lastNameText;
    @FXML
    private TextArea subjectTextfield;
    @FXML
    private TableView<Course> coursesTable;
    @FXML
    private TableColumn<Course, String> coursesTitle;
    @FXML
    private TableColumn<Course, String> coursesStream;
    @FXML
    private TableColumn<Course, String> coursesType;
    @FXML
    private TableColumn<Course, LocalDate> coursesStarts;
    @FXML
    private TableColumn<Course, LocalDate> coursesEnds;
    @FXML
    private Button addCourseButton;
    
    private MyTableColumn<Course, LocalDate> courseDateColumn = new MyTableColumn<>();
    
    private final String pattern = GenericDao.daTiFormat;
    
    private Trainer trainer;
    private Course course;
    private String whatToSave;
    private Course selectedCourseToAdd;
    private MyNotification notification = new MyNotification();
    private YesOrNoWindow alert = new YesOrNoWindow();
    
    
    @FXML
    private Button removeCourseButton;
    @FXML
    private Label detailsErrorLabel;
    @FXML
    private Button cancelAddCourseButton;
    @FXML
    private VBox addCoursePane;
    @FXML
    private TableView<Course> avaliableCourses;
    @FXML
    private TableColumn<Course, String> avaliableCoursesTitle;
    @FXML
    private TableColumn<Course, String> avaliableCoursesStream;
    @FXML
    private TableColumn<Course, String> avaliableCoursesType;
    @FXML
    private TableColumn<Course, LocalDate> avaliableCoursesStarts;
    @FXML
    private TableColumn<Course, LocalDate> avaliableCoursesEnds;
    
    GenericDao data = GenericDao.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Everytime program initializes the Pane it sets to null the trainer and the course
        trainer = null;
        course = null;
        
        // sets the propertyValueFactory in every table's column
        trainersFName.setCellValueFactory(new PropertyValueFactory<Trainer, String>("firstName"));
        trainersLName.setCellValueFactory(new PropertyValueFactory<Trainer, String>("lastName"));
        
        coursesTitle.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
        coursesStream.setCellValueFactory(new PropertyValueFactory<Course, String>("stream"));
        coursesType.setCellValueFactory(new PropertyValueFactory<Course, String>("type"));
        coursesStarts.setCellValueFactory(new PropertyValueFactory<Course, LocalDate>("start_date"));
        coursesEnds.setCellValueFactory(new PropertyValueFactory<Course, LocalDate>("end_date"));
        
        avaliableCoursesTitle.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
        avaliableCoursesStream.setCellValueFactory(new PropertyValueFactory<Course, String>("stream"));
        avaliableCoursesType.setCellValueFactory(new PropertyValueFactory<Course, String>("type"));
        avaliableCoursesStarts.setCellValueFactory(new PropertyValueFactory<Course, LocalDate>("start_date"));
        avaliableCoursesEnds.setCellValueFactory(new PropertyValueFactory<Course, LocalDate>("end_date"));
        
        
        //sets the converter to the LodalDate type columns
        coursesStarts.setCellFactory(column -> courseDateColumn.tableConverter() );
        coursesEnds.setCellFactory(column -> courseDateColumn.tableConverter() );
        avaliableCoursesStarts.setCellFactory(column -> courseDateColumn.tableConverter() );
        avaliableCoursesEnds.setCellFactory(column -> courseDateColumn.tableConverter() );
        
        
        subjectTextfield.setWrapText(true);
        
        //if there is a connection to the database then update the TableViews with data, else
        // initialize them with error labes
        if (data.checkDbConnection()){
            if(data.checkTables() == "OK"){
                updateTrainersTable(0);
            }
            
        }else{
            notification.warning("The program can't connect to the server.\n Please check if server is offline or\n go to change the Server Properties!");
            trainersTable.setPlaceholder(new Label("There are no connection with the server. Please check the Server Properties!"));
            coursesTable.setPlaceholder(new Label("There are no connection with the server. Please check the Server Properties!"));
            
        }
    }    

    /**
     * Turns the program in creating mode
     * @param event 
     */
    @FXML
    private void addTrainer(MouseEvent event) {
        whatToSave = "NEW";
        editMode();
        clearDetailFields();
    }

    
    /**
     * Deletes the selected trainer
     * @param event 
     */
    @FXML
    private void deleteTrainer(MouseEvent event) {
        boolean result = alert.show("Confirmation!", "Do you want to remove "+ trainer.getFirstName()+" "+ trainer.getLastName()+ " ?");
        if(result){
            // Get selected row and delete
            int ix = trainersTable.getSelectionModel().getSelectedIndex();
            trainer = (Trainer) trainersTable.getSelectionModel().getSelectedItem();
            
            Trainer.delete(trainer);
            trainersTable.getItems().remove(ix);
            notification.success("The trainer has deleted successfully!");
            
            // Select a row
            if(trainersTable.getItems().size()== 0){
                trainersTable.getItems().clear();
                trainersTable.setPlaceholder(new Label("There are no other trainers"));
                deleteTrainerButton.setDisable(true);
            }
            updateTrainersTable(trainersTable.getItems().size()-1);
        }
    }

    /**
     * Cancel the editing/creating procedure and turns the program into searching mode
     * @param event 
     */
    @FXML
    private void cancelTheSave(MouseEvent event) {
        notification.info("You canceled the editing proccess!");
        firstNameText.setText(trainer.getFirstName());
        lastNameText.setText(trainer.getLastName());
        subjectTextfield.setText(trainer.getSubject());
        
        deleteTrainerButton.setDisable(false);

        searchMode();
    }

    
    /**
     * Saves the edited/new trainer
     * @param event 
     */
    @FXML
    private void saveDetails(MouseEvent event) {
        int ix = trainersTable.getSelectionModel().getSelectedIndex();
        boolean result;

        if(firstNameText.getText().equals("")){
            detailsErrorLabel.setText("Please write the trainer's first name and try again!");
            notification.warning("Please write the trainer's first name and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(lastNameText.getText().equals("")){
            detailsErrorLabel.setText("Please write the trainer's last name and try again!");
            notification.warning("Please write the trainer's last name and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(subjectTextfield.getText().equals("")){
            detailsErrorLabel.setText("Please write the trainer's subject and try again!");
            notification.warning("Please write the trainer's subject and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else{ 
            if(whatToSave == "NEW"){
                result = alert.show("Confirmation!", "Do you want to save the changes?");
                
                if(result){
                    Trainer newTrainer = new Trainer(firstNameText.getText(),lastNameText.getText(),subjectTextfield.getText());
                    
                    if(!Trainer.exists(newTrainer)){
                        Trainer.saveTrainer(newTrainer);
                        trainer= newTrainer;
                        detailsErrorLabel.setVisible(false);
                        notification.success("You have added "+ trainer.getFirstName()+" "+trainer.getLastName()+" succefully!" );
                        trainersTable.requestFocus();
                        trainersTable.getSelectionModel().selectLast();
                        searchMode();
                        ix= Trainer.getAllTrainers().size();
                        coursesTable.getFocusModel().focus(ix-1);
                        updateTrainersTable(ix-1);
                    }
                    else{
                        detailsErrorLabel.setText("The trainer allready exists. Please change some fields");
                        notification.warning("The trainer allready exists. Please change some fields");
                    }
                }
            }
            else if(whatToSave == "EDIT"){
                result = alert.show("Confirmation!", "Do you want to update the changes?");
                if(result){
                    trainer.setFirstName(firstNameText.getText());
                    trainer.setLastName(lastNameText.getText());
                    trainer.setSubject(subjectTextfield.getText());
                    trainer.update();
                    notification.success("You have edited "+ trainer.getFirstName()+" "+trainer.getLastName()+" succefully!" );
                    detailsErrorLabel.setVisible(false);
                    searchMode();
                    updateTrainersTable(trainersTable.getSelectionModel().getSelectedIndex());
                }
            }
            
        }
    }

    
    /**
     * Turns the window in editing mode
     * @param event 
     */
    @FXML
    private void editDetails(MouseEvent event) {
        whatToSave = "EDIT";
        editMode();
    }
    
    

    /**
     * Opens the add avaliable courses window
     * @param event 
     */
    @FXML
    private void addCourse(MouseEvent event) {
        updateAvaliableCoursesTable();
        prepareSlideMenuAnimation();
    }
    
    
    /**
     * Removes the trainer from the selected course
     * @param event 
     */
    @FXML
    private void removeCourse(MouseEvent event) {
        boolean result = alert.show("Confirmation!", "Are you sure that you want to remove the trainer from the course\n  "+course.getTitle()+ "   "+course.getStream()+" ?");
        if(result){
            int intex = trainersTable.getSelectionModel().getSelectedIndex();
            course.removeTrainer(trainer);
            notification.success("You have removed successfully the trainer from the course!");
            updateTrainersTable(intex);
        }
    }
    
    
    /**
     * Saves the trainer into trainer variable and updates the trainer details and courses table
     * @param event 
     */
    @FXML
    private void trainerSelected(MouseEvent event) {
        trainer = (Trainer) trainersTable.getSelectionModel().getSelectedItem();
        updateDetails();
        updateCoursesTable();
    }

    
    /**
     * Saves the selected course into the course variable
     * @param event 
     */
    @FXML
    private void courseSelected(MouseEvent event) {
        course = (Course) coursesTable.getSelectionModel().getSelectedItem();
    }
    
    
//  --------------  Returns all the data for tables  ---------------------------
    private ObservableList<Trainer> getStudents(){
        ObservableList<Trainer> trainersObList = FXCollections.observableArrayList();
        trainersObList.addAll(Trainer.getAllTrainers());
        return trainersObList;
    }
    
    
    private ObservableList<Course> getCourses(){
        ObservableList<Course> coursesObList = FXCollections.observableArrayList();
        coursesObList.addAll(trainer.getCourses());
        return coursesObList;
    }
    
    
    private ObservableList<Course> getAvailableCoursesForTrainer(){
        ObservableList<Course> coursesObList = FXCollections.observableArrayList();
        coursesObList.addAll(trainer.getAvaliableCourses());
        return coursesObList;
    }
    
    
//  --------  all the methods that update the tables  --------------------------
    private void updateTrainersTable(int selection){
        
        addTrainerButton.setDisable(false);
        if(Trainer.getAllTrainers().size()>0){
            
            trainersTable.getItems().clear();
            trainersTable.getItems().setAll(getStudents());
            trainersTable.requestFocus();
            trainersTable.getSelectionModel().select(selection);
            
            deleteTrainerButton.setDisable(false);
            trainer = (Trainer) trainersTable.getSelectionModel().getSelectedItem();
            editDetailsButton.setDisable(false);
            updateDetails();
            updateCoursesTable();
        }
        else{
            trainersTable.getItems().clear();
            trainersTable.setPlaceholder(new Label("The Student has no Courses"));
            trainer=null;
            editDetailsButton.setDisable(true);
            updateDetails();
            updateCoursesTable();
        }

        
    }
    
    
    private void updateCoursesTable(){
        if(trainer!= null ){
            addCourseButton.setDisable(false);
            if( trainer.getCourses().size()>0){
                coursesTable.getItems().setAll(getCourses());
                coursesTable.requestFocus();
                coursesTable.getSelectionModel().select(0);
                removeCourseButton.setDisable(false);
                course = (Course) coursesTable.getSelectionModel().getSelectedItem();
            }
            else{
                course=null;
                coursesTable.getItems().clear();
                removeCourseButton.setDisable(true);
                coursesTable.setPlaceholder(new Label("The Trainer has no Courses"));
            }
        }
        else{
            addCourseButton.setDisable(false);
            coursesTable.getItems().clear();
            removeCourseButton.setDisable(true);
            coursesTable.setPlaceholder(new Label("You have to select a Trainer first"));
        }
    }
    
    /**
     * Updates the trainer details
     */
    private void updateDetails(){
        if(trainer==null){
            clearDetailFields();
            deleteTrainerButton.setDisable(true);
        }
        else{
            firstNameText.setText(trainer.getFirstName());
            lastNameText.setText(trainer.getLastName());
            subjectTextfield.setText(trainer.getSubject());
            deleteTrainerButton.setDisable(false);
        }
    }
    
    
    /**
     * Clears all the detail's fields
     */
    private void clearDetailFields(){
        firstNameText.setText("");
        lastNameText.setText("");
        subjectTextfield.setText("");
    }
    
    /**
     * Turns the program into the editing mode
     */
    private void editMode(){
        cancelTheSaveButton.setVisible(true);
        cancelTheSaveButton.setDisable(false);
        saveDetailsButton.setVisible(true);
        saveDetailsButton.setDisable(false);
        editDetailsButton.setVisible(false);
        editDetailsButton.setDisable(true);
        firstNameText.setEditable(true);
        lastNameText.setEditable(true);
        subjectTextfield.setEditable(true);
        
        trainersTable.setDisable(true);
        trainersTable.selectionModelProperty().isNull();
        addTrainerButton.setDisable(true);
        deleteTrainerButton.setDisable(true);
        
        coursesTable.setDisable(true);
        addCourseButton.setDisable(true);
        removeCourseButton.setDisable(true);
    }
    
    
    /**
     * Turns the program into searching mode
     */
    private void searchMode(){
        cancelTheSaveButton.setVisible(false);
        cancelTheSaveButton.setDisable(true);
        saveDetailsButton.setVisible(false);
        saveDetailsButton.setDisable(true);
        editDetailsButton.setVisible(true);
        editDetailsButton.setDisable(false);
        firstNameText.setEditable(false);
        lastNameText.setEditable(false);
        subjectTextfield.setEditable(false);
        
        trainersTable.setDisable(false);
        trainersTable.selectionModelProperty().isNull();
        addTrainerButton.setDisable(false);
        deleteTrainerButton.setDisable(false);
        
        coursesTable.setDisable(false);
        addCourseButton.setDisable(false);
        removeCourseButton.setDisable(false);
    }

    
    /**
     * Closes the add course window
     * @param event 
     */
    @FXML
    private void cancelAddCourse(MouseEvent event) {
        prepareSlideMenuAnimation();
    }


    

    /**
     * Opens and closes the add course window
     */
    private void prepareSlideMenuAnimation() {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), addCoursePane);
        openNav.setToX(400);
        
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), addCoursePane);
        int selectedTrainer = trainersTable.getSelectionModel().getSelectedIndex();
        if(addCoursePane.getTranslateX()!=400){
                openNav.play();
                trainersTable.setDisable(true);
                addTrainerButton.setDisable(true);
                deleteTrainerButton.setDisable(true);
                coursesTable.setDisable(true);
                
            }else{
                closeNav.setToX(+(addCoursePane.getWidth()+400));
                closeNav.play();
                trainersTable.setDisable(false);
                addTrainerButton.setDisable(false);
                deleteTrainerButton.setDisable(false);
                coursesTable.setDisable(false);
                updateTrainersTable(selectedTrainer);
            }
        
    }

    
    /**
     * Adds the trainer in the selected course
     * @param event 
     */
    @FXML
    private void addCourseToTrainer(MouseEvent event) {
        selectedCourseToAdd = (Course) avaliableCourses.getSelectionModel().getSelectedItem();
        boolean result = alert.show("Confirmation!", "Are you sure that you want to add the trainer to this course\n  "+selectedCourseToAdd.getTitle()+ "   "+selectedCourseToAdd.getStream()+" ?");
        if(result){
            selectedCourseToAdd.addTrainer(trainer);
            updateAvaliableCoursesTable();
            notification.success(trainer.getFirstName() + " " + trainer.getLastName()+" has been added to the course!");
        }
    }
    
    
    

    /**
     * Updates the avaliableCourses table
     */
    private void updateAvaliableCoursesTable(){
        if(trainer == null){
            avaliableCourses.getItems().clear();
            avaliableCourses.setPlaceholder(new Label("You have to select a Student first"));
        }
        else if(trainer.getAvaliableCourses().size()==0){
            avaliableCourses.getItems().clear();
            avaliableCourses.setPlaceholder(new Label("There are no avaliable courses for this student"));
        }
        else{
            avaliableCourses.getItems().clear();
            avaliableCourses.getItems().setAll(getAvailableCoursesForTrainer());
        }
    }  
}
