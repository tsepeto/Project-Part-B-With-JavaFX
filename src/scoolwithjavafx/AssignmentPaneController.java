
package scoolwithjavafx;

import Utils.MyNotification;
import Utils.MyTableColumn;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.StringConverter;
import Entities.Assignment;
import Entities.Course;
import Entities.Student;
import Entities.dao.GenericDao;
import Utils.NumberTextField;
import Utils.YesOrNoWindow;

/**
 * FXML Controller class
 *
 * @author Tsepetzidis Nikos
 */
public class AssignmentPaneController implements Initializable {

    @FXML private Button cancelTheSaveButton;
    @FXML private Button saveDetailsButton;
    @FXML private Button editDetailsButton;
    @FXML private Button addAssignmentButton;
    @FXML private Button deleteAssignmentButton;
    @FXML private TableView<Course> coursesTable;
    @FXML private TableColumn<Course, String> coursesTitle;
    @FXML private TableColumn<Course, String> coursesStream;
    @FXML private TableColumn<Course, String> coursesType;
    @FXML private TableColumn<Course, LocalDate> coursesStarts;
    @FXML private TableColumn<Course, LocalDate> coursesEnds;
    @FXML private Button addCourseButton;
    @FXML private Button deleteCourseButton;
    @FXML private TableView<Assignment> assignmentsTable;
    @FXML private TableColumn<Assignment, String> assignmentsTitle;
    @FXML private TableColumn<Assignment, String> assignmentsDescription;
    @FXML private TableColumn<Assignment, LocalDate> assignmentsDeadline;
    @FXML private DatePicker deadlinePicker;
    @FXML private NumberTextField oralMarkText;
    @FXML private NumberTextField totalMarkText;
    @FXML private TextArea descriptionTextfield;
    @FXML private TextField titleText;
    @FXML private Label detailsErrorLabel;
    @FXML private VBox addCoursePane;
    @FXML private TableView<Course> avaliableCourses;
    @FXML private TableColumn<Course, String> avaliableCoursesTitle;
    @FXML private TableColumn<Course, String> avaliableCoursesStream;
    @FXML private TableColumn<Course, String> avaliableCoursesType;
    @FXML private TableColumn<Course, LocalDate> avaliableCoursesStarts;
    @FXML private TableColumn<Course, LocalDate> avaliableCoursesEnds;
    
    
    private Assignment assignment;
    private Course course;
    private String whatToSave;
    private Course selectedCourseToAdd;
    private MyNotification notification = new MyNotification();
    private YesOrNoWindow alert = new YesOrNoWindow();                          
    
    private final String pattern = GenericDao.daTiFormat;
    
    private MyTableColumn<Assignment, LocalDate> assignmentsDateColumn = new MyTableColumn<>();
    private MyTableColumn<Course, LocalDate> coursesDateColumn = new MyTableColumn<>();
    
    GenericDao data = GenericDao.getInstance();
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Everytime program initializes the Pane it sets to null the assignment and the course
        assignment = null;
        course = null;
        
        // sets the propertyValueFactory in every table's column
        assignmentsTitle.setCellValueFactory(new PropertyValueFactory<Assignment, String>("title"));
        assignmentsDescription.setCellValueFactory(new PropertyValueFactory<Assignment, String>("description"));
        assignmentsDeadline.setCellValueFactory(new PropertyValueFactory<Assignment, LocalDate>("subDateTime"));
        
        
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
        
        
        //creates a new converter that helps to print the date in our DateTimeFormat, in every LocalDate type column
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        
        // sets the converter to deadlinePiceker
        deadlinePicker.setConverter(converter);
        deadlinePicker.setPromptText(pattern.toLowerCase());
        
        //sets the style in deadlinePicker
        deadlinePicker.getStyleClass().add("non-editable-datepicker");
        
        //sets the converter to the LodalDate type columns
        assignmentsDeadline.setCellFactory(column -> assignmentsDateColumn.tableConverter() );
        coursesStarts.setCellFactory(column -> coursesDateColumn.tableConverter() );
        coursesEnds.setCellFactory(column -> coursesDateColumn.tableConverter() );
        avaliableCoursesStarts.setCellFactory(column -> coursesDateColumn.tableConverter() );
        avaliableCoursesEnds.setCellFactory(column -> coursesDateColumn.tableConverter() );
        
        
        descriptionTextfield.setWrapText(true);     // New line of the text exceeds the text area
        
        //if there is a connection to the database then update the TableViews with data, else
        // initialize them with error labes
        if (data.checkDbConnection()){
            if(data.checkTables() == "OK"){
                updateAssignmentTable(0);
            }
        }
        else{
            
            notification.warning("The program can't connect to the server.\n Please check if server is offline or\n go to change the Server Properties!");
            assignmentsTable.setPlaceholder(new Label("There are no connection with the server. Please check the Server Properties!"));
            coursesTable.setPlaceholder(new Label("There are no connection with the server. Please check the Server Properties!"));
            
        }
    }    

    /**
     * Clears all the detail textFields, and makes them editable, to let the user to 
     * give the details for the new Assignment.
     * Also says to program that is going to add a NEW assignment if the user press the save button.
     * @param event 
     */
    @FXML
    private void addAssignment(MouseEvent event) {
        whatToSave = "NEW";
        editMode();
        clearDetailFields();
    }

    /**
     * It deletes the chosen assignment
     * @param event 
     */
    @FXML
    private void deleteAssignment(MouseEvent event) {
        boolean result = alert.show("Confirmation!", "Do you want to remove "+ assignment.getTitle()+ " ?");
        if(result){
            // Get selected assignment (table's row) and delete it
            int ix = assignmentsTable.getSelectionModel().getSelectedIndex();
            assignment = (Assignment) assignmentsTable.getSelectionModel().getSelectedItem();
            
            Assignment.delete(assignment);
            assignmentsTable.getItems().remove(ix);
            notification.success("The assignment has been deleted successfully!");
            
            // if there are no other assignments, then set a label in table
            if(assignmentsTable.getItems().size()== 0){
                assignmentsTable.getItems().clear();
                assignmentsTable.setPlaceholder(new Label("There are no other assignments"));
                deleteAssignmentButton.setDisable(true);
            }
            
            // after the deletion the program updates the trainers table, and chooses the last trainer
            updateAssignmentTable(assignmentsTable.getItems().size()-1);
        }
    }

    
    /**
     * Cancel the editing/save new  progress and returns the program to search mode
     * @param event 
     */
    @FXML
    private void cancelTheSave(MouseEvent event) {
        titleText.setText(assignment.getTitle());
        deadlinePicker.setValue(assignment.getSubDateTime());
        oralMarkText.setText(String.valueOf(assignment.getOralMark()));
        totalMarkText.setText(String.valueOf(assignment.getTotalMark()));
        descriptionTextfield.setText(assignment.getDescription());
        deleteAssignmentButton.setDisable(false);
        notification.info("You canceled the editing process!");

        searchMode();
    }
    
    
    /**
     * If the user press save, the program checks if the user saves an edited assignment, or a new one
     * and saves the changes after it checks if all the fields are not empty.
     * @param event 
     */
    @FXML
    private void saveDetails(MouseEvent event) {
        int ix = assignmentsTable.getSelectionModel().getSelectedIndex();
        boolean result;

        if(titleText.getText().equals("")){
            detailsErrorLabel.setText("Please write the assignment's title and try again!");
            notification.warning("Please write the assignment's title and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(oralMarkText.getText().equals("")){
            detailsErrorLabel.setText("Please write the assignment's oral mark and try again!");
            notification.warning("Please write the assignment's oral mark and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(Integer.parseInt(oralMarkText.getText())<0){
            detailsErrorLabel.setText("The assignment's oral mark can't be negative!");
            notification.warning("The assignment's oral mark can't be negative!");
            detailsErrorLabel.setVisible(true);
        }
        else if(totalMarkText.getText().equals("")){
            detailsErrorLabel.setText("Please write the assignment's total mark and try again!");
            notification.warning("Please write the assignment's total mark and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(Integer.parseInt(totalMarkText.getText())<0){
            detailsErrorLabel.setText("The assignment's total mark can't be negative!");
            notification.warning("The assignment's total mark can't be negative!");
            detailsErrorLabel.setVisible(true);
        }
        else if(deadlinePicker.getValue()==null){
            detailsErrorLabel.setText("Please give the assignment's deadline and try again!");
            notification.warning("Please write the assignment's deadline and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(descriptionTextfield.getText().equals("")){
            detailsErrorLabel.setText("Please write the assignment's description and try again!");
            notification.warning("Please write the assignment's description and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else{ 
            
            if(whatToSave == "NEW"){
                result = alert.show("Confirmation!", "Do you want to save the changes?");
                
                if(result){
                    Assignment newAssignment = new Assignment(titleText.getText(),descriptionTextfield.getText(),deadlinePicker.getValue(),Integer.parseInt(oralMarkText.getText()),Integer.parseInt(totalMarkText.getText()));
                    
                    if(!Assignment.exists(newAssignment)){
                        Assignment.saveAssignment(newAssignment);
                        assignment= newAssignment;
                        detailsErrorLabel.setVisible(false);
                        notification.success("The assignment has been saved successfully!");
                        assignmentsTable.requestFocus();
                        assignmentsTable.getSelectionModel().selectLast();
                        searchMode();
                        ix= Student.getAllStudents().size();
                        assignmentsTable.getFocusModel().focus(ix-1);
                        updateAssignmentTable(ix-1);
                    }
                    else{
                        detailsErrorLabel.setText("The student allready exists. Please change some fields");
                        notification.warning("The student allready exists. Please change some fields");
                    }
                    
                }
            }
            else if(whatToSave == "EDIT"){
                result = alert.show("Confirmation!", "Do you want to update the changes?");
                
                if(result){
                    
                    assignment.setTitle(titleText.getText());
                    assignment.setDescription(descriptionTextfield.getText());
                    assignment.setSubDateTime(assignment.getSubDateTime());
                    assignment.setOralMark(Integer.parseInt(oralMarkText.getText())); 
                    assignment.setTotalMark(Integer.parseInt(totalMarkText.getText())); 
                    assignment.update();
                    notification.success("The assignment has been updated successfully!");
                    
                    detailsErrorLabel.setVisible(false);
                    searchMode();
                    updateAssignmentTable(assignmentsTable.getSelectionModel().getSelectedIndex());
            
                }
            }
        }
    }

    
    /**
     * Enables the user to edit the TextFields, and tells to program that is an editing 
     * procedure
     * @param event 
     */
    @FXML
    private void editDetails(MouseEvent event) {
        whatToSave = "EDIT";
        editMode();
    }

    /**
     * Brings the window in front, and lets the user to choose a course to add to the assignment.
     * @param event 
     */
    @FXML
    private void addCourse(MouseEvent event) {
        updateAvaliableCoursesTable();
        prepareSlideMenuAnimation();
    }
    
    
    /**
     * Removes the course from the chosen assignment
     * @param event 
     */
    @FXML
    private void deleteCourse(MouseEvent event) {
        
        boolean result = alert.show("Confirmation!", "Are you sure that you want to remove the course\n"+course.getTitle()+ " "+course.getStream()+" from the assignment?");
        if(result){
            int intex = assignmentsTable.getSelectionModel().getSelectedIndex();
            assignment.removeCourse(course);
            notification.success("You have removed the assignment successfully from the course!");
            updateAssignmentTable(intex);
        }
    }

    
    /**
     * saves the selected assignment from the user to assignment variable
     * @param event 
     */
    @FXML
    private void selectAssignments(MouseEvent event) {
        assignment = (Assignment) assignmentsTable.getSelectionModel().getSelectedItem();
        updateAssignmentDetails();
        updateCoursesTable();
    }

    
    /**
     * saves the selected course from the user to course variable
     * @param event 
     */
    @FXML
    private void selectCourses(MouseEvent event) {
        course = (Course) coursesTable.getSelectionModel().getSelectedItem();
    }
    
    
    /**
     * Returns the assignments list as ObservableList for the table
     * @return assignmentsObList
     */
    private ObservableList<Assignment> getAssignments(){
        ObservableList<Assignment> assignmentsObList = FXCollections.observableArrayList();
        assignmentsObList.addAll(Assignment.getAllAssignments());
        return assignmentsObList;
    }
    
    
    /**
     * Returns the course list as ObservableList for the table
     * @return assignmentsObList
     */
    private ObservableList<Course> getCourses(){
        ObservableList<Course> coursesObList = FXCollections.observableArrayList();
        coursesObList.addAll(assignment.getCourses());
        return coursesObList;
    }
    
    /**
     * Returns the course list as ObservableList for the table
     * @return assignmentsObList
     */
    private ObservableList<Course> getAvailableCoursesForAssignment(){
        ObservableList<Course> coursesObList = FXCollections.observableArrayList();
        coursesObList.addAll(assignment.getUnregisteredCourses());
        return coursesObList;
    }
    
    
    /**
     * Update the assignment table and calls updateCoursesTable()
     * after it selects the row with the selection number
     * @param selection 
     */
    private void updateAssignmentTable(int selection){
        
        addAssignmentButton.setDisable(false);
        if(Assignment.getAllAssignments().size()>0){
            
            assignmentsTable.getItems().clear();
            assignmentsTable.getItems().setAll(getAssignments());
            assignmentsTable.requestFocus();
            assignmentsTable.getSelectionModel().select(selection);
            
            deleteAssignmentButton.setDisable(false);
            assignment = (Assignment) assignmentsTable.getSelectionModel().getSelectedItem();
            updateAssignmentDetails();
            updateCoursesTable();
            addCourseButton.setDisable(false);
            editDetailsButton.setDisable(false);
        }
        else{
            assignmentsTable.getItems().clear();
            assignmentsTable.setPlaceholder(new Label("The assignment has no Courses"));
            assignment=null;
            updateAssignmentDetails();
            updateCoursesTable();
            addCourseButton.setDisable(true);
            editDetailsButton.setDisable(true);
        }

        
    }
    
    /**
     * Updates the coursesTable for the selected assignment
     */
    private void updateCoursesTable(){
        
        
        if(assignment!= null ){
            if( assignment.getCourses().size()>0){
                coursesTable.getItems().setAll(getCourses());
                coursesTable.requestFocus();
                coursesTable.getSelectionModel().select(0);
                deleteCourseButton.setDisable(false);
                course = (Course) coursesTable.getSelectionModel().getSelectedItem();
                
            }
            else{
                course=null;
                coursesTable.getItems().clear();
                deleteCourseButton.setDisable(true);
                coursesTable.setPlaceholder(new Label("The assignment has no Courses"));
                
            }
        }
        else{
            coursesTable.getItems().clear();
            deleteCourseButton.setDisable(true);
            coursesTable.setPlaceholder(new Label("You have to select an assignment first"));
            
        }
    }
    
    
    /**
     * Updates the Avaliable courses table for the selected assignment
     */
    private void updateAvaliableCoursesTable(){
        if(assignment == null){
            avaliableCourses.getItems().clear();
            avaliableCourses.setPlaceholder(new Label("You have to select an assignment first"));
        }
        else if(assignment.getUnregisteredCourses().size()==0){
            avaliableCourses.getItems().clear();
            avaliableCourses.setPlaceholder(new Label("There are no avaliable courses for this assignment"));
        }
        else{
            avaliableCourses.getItems().clear();
            avaliableCourses.getItems().setAll(getAvailableCoursesForAssignment());
        }
    }
    
    
    /**
     * Updates the asssignment details
     */
    private void updateAssignmentDetails(){
        if(assignment==null){
            clearDetailFields();
            deleteAssignmentButton.setDisable(true);
        }
        else{
            titleText.setText(assignment.getTitle());
            deadlinePicker.setValue(assignment.getSubDateTime());
            oralMarkText.setText(String.valueOf(assignment.getOralMark()));
            totalMarkText.setText(String.valueOf(assignment.getTotalMark()));
            descriptionTextfield.setText(String.valueOf(assignment.getDescription()));
            
            deleteAssignmentButton.setDisable(false);
        }
    }
    
    
    /**
     * Clears all the detail TextFields
     */
     private void clearDetailFields(){
        titleText.setText("");
        deadlinePicker.setValue(null);
        oralMarkText.setText("");
        totalMarkText.setText("");
        descriptionTextfield.setText("");
    }
     
     /**
      * Sets the pane in edit mode
      */
     private void editMode(){
        cancelTheSaveButton.setVisible(true);
        cancelTheSaveButton.setDisable(false);
        saveDetailsButton.setVisible(true);
        saveDetailsButton.setDisable(false);
        editDetailsButton.setVisible(false);
        editDetailsButton.setDisable(true);
        titleText.setEditable(true);
        deadlinePicker.setEditable(true);
        oralMarkText.setEditable(true);
        totalMarkText.setEditable(true);
        descriptionTextfield.setEditable(true);
        
        deadlinePicker.getStyleClass().remove("non-editable-datepicker");
        
        assignmentsTable.setDisable(true);
        assignmentsTable.selectionModelProperty().isNull();
        addAssignmentButton.setDisable(true);
        deleteAssignmentButton.setDisable(true);
        
        coursesTable.setDisable(true);
        addCourseButton.setDisable(true);
        deleteCourseButton.setDisable(true);
        
    }
     
     
     /**
      * Sets the pane in search mode
      */
     private void searchMode(){
         
        cancelTheSaveButton.setVisible(false);
        cancelTheSaveButton.setDisable(true);
        saveDetailsButton.setVisible(false);
        saveDetailsButton.setDisable(true);
        editDetailsButton.setVisible(true);
        editDetailsButton.setDisable(false);
        titleText.setEditable(false);
        deadlinePicker.setEditable(false);
        oralMarkText.setEditable(false);
        totalMarkText.setEditable(false);
        descriptionTextfield.setEditable(false);
        
        deadlinePicker.getStyleClass().add("non-editable-datepicker");
        
        detailsErrorLabel.setVisible(false);
        detailsErrorLabel.setText("");
        
        assignmentsTable.setDisable(false);
        assignmentsTable.selectionModelProperty().isNull();
        addAssignmentButton.setDisable(false);
        deleteAssignmentButton.setDisable(false);
        
        coursesTable.setDisable(false);
        addCourseButton.setDisable(false);
        deleteCourseButton.setDisable(false);
    }
     
    
     /**
      * Closes the addCourse window
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
        int selectedAssignment = assignmentsTable.getSelectionModel().getSelectedIndex();
        if(addCoursePane.getTranslateX()!=400){
                openNav.play();
                assignmentsTable.setDisable(true);
                addAssignmentButton.setDisable(true);
                deleteAssignmentButton.setDisable(true);
                
            }else{
                closeNav.setToX(+(addCoursePane.getWidth()+400));
                closeNav.play();
                assignmentsTable.setDisable(false);
                addAssignmentButton.setDisable(false);
                deleteAssignmentButton.setDisable(false);
                updateAssignmentTable(selectedAssignment);
            }
        
    }

    /**
     * Adds the selected course to the assignment
     * @param event 
     */
    @FXML
    private void addCourseToAssignment(MouseEvent event) {
        selectedCourseToAdd = (Course) avaliableCourses.getSelectionModel().getSelectedItem();
        boolean result = alert.show("Confirmation!", "Are you sure that you want to add the assignment to this course\n  "+selectedCourseToAdd.getTitle()+ "   "+selectedCourseToAdd.getStream()+" ?");
        if(result){
            assignment.addCourse(selectedCourseToAdd);
            updateAvaliableCoursesTable();
            notification.success("Assignment has been added to the course!");
        }
    }

   
    
        
    
     
    
}
