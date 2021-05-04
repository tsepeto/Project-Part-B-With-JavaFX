
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.StringConverter;
import Entities.Assignment;
import Entities.Course;
import Entities.Student;
import Entities.Trainer;
import Entities.dao.GenericDao;
import Utils.YesOrNoWindow;

/**
 * FXML Controller class
 *
 * @author Tseptzidis Nikos
 */
public class CoursePaneController implements Initializable {

    @FXML
    private TableView<Course> coursesTable;
    @FXML
    private TableColumn<Course, String> coursesTitle;
    @FXML
    private TableColumn<Course, String> coursesStream;
    @FXML
    private TableColumn<Course, String> coursesType;
    @FXML
    private Button addCourseButton;
    @FXML
    private Button deleteCourseButton;
    @FXML
    private Button cancelTheSaveButton;
    @FXML
    private Button saveDetailsButton;
    @FXML
    private Button editDetailsButton;
    @FXML
    private TextField titleText;
    @FXML
    private TextField streamText;
    @FXML
    private TextField typeText;
    @FXML
    private DatePicker startsDatepicker;
    @FXML
    private DatePicker endsDatepicker;
    @FXML
    private Label detailsErrorLabel;
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> studentFirstName;
    @FXML
    private TableColumn<Student, String> studentLastName;
    @FXML
    private Button removeStudentButton;
    @FXML
    private Button addStudentButton;
    @FXML
    private TableView<Trainer> trainerTable;
    @FXML
    private TableColumn<Trainer, String> trainerFirstName;
    @FXML
    private TableColumn<Trainer, String> trainerLastName;
    @FXML
    private Button removeTrainerButton;
    @FXML
    private Button addTrainerButton;
    @FXML
    private TableView<Assignment> assignmentTable;
    @FXML
    private TableColumn<Assignment, String> assignmentTitle;
    @FXML
    private TableColumn<Assignment, String> assignmentDescription;
    @FXML
    private TableColumn<Assignment, LocalDate> assignmentDeadline;
    @FXML
    private Button removeAssignmentButton;
    @FXML
    private Button addAssignmentButton;
    @FXML
    private VBox addStudentPane;
    @FXML
    private TableView<Student> avaliableStudents;
    @FXML
    private TableColumn<Student, String> avaliableStudentsFirstName;
    @FXML
    private TableColumn<Student, String> avaliableStudentsLastName;
    @FXML
    private TableColumn<Student, LocalDate> avaliableStudentsBirthday;
    @FXML
    private TableColumn<Student, Double> avaliableStudentsTuitionFees;
    @FXML
    private Button cancelAddStudentButton;
    @FXML
    private VBox addTrainerPane;
    @FXML
    private Button cancelAddTrainerButton;
    @FXML
    private TableView<Trainer> avaliableTrainers;
    @FXML
    private TableColumn<Trainer, String> avaliableTrainersFirstName;
    @FXML
    private TableColumn<Trainer, String> avaliableTrainersLastName;
    @FXML
    private TableColumn<Trainer, String> avaliableTrainersSubject;
    @FXML
    private VBox addAssignmentPane;
    @FXML
    private Button cancelAddAssignmentsButton;
    @FXML
    private TableView<Assignment> avaliableAssignments;
    @FXML
    private TableColumn<Assignment, String> avaliableAssignmentsTitle;
    @FXML
    private TableColumn<Assignment, String> avaliableAssignmentsDescription;
    @FXML
    private TableColumn<Assignment, LocalDate> avaliableAssignmentsDeadline;
    @FXML
    private TableColumn<Assignment, Integer> avaliableAssignmentsOralMark;
    @FXML
    private TableColumn<Assignment, Integer> avaliableAssignmentsTotalMark;
    
    
    private MyTableColumn<Student, LocalDate> studentDateColumn = new MyTableColumn<>();
    private MyTableColumn<Assignment, LocalDate> assignmentDateColumn = new MyTableColumn<>();
    
    private final String pattern = GenericDao.daTiFormat;
    private MyNotification notification = new MyNotification();
    private YesOrNoWindow alert = new YesOrNoWindow();
    
    private Course course;
    private Student student;
    private Trainer trainer;
    private Assignment assignment;
    private Student avaliableStudent;
    private Trainer avaliableTrainer;
    private Assignment avaliableAssignment;
    private String whatToSave;
    
    GenericDao data = GenericDao.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Everytime we initialize the Pane we set to null the entities
        course = null;
        student = null;
        trainer = null;
        assignment = null;
        
        // sets the propertyValueFactory in every table's column
        coursesTitle.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
        coursesStream.setCellValueFactory(new PropertyValueFactory<Course, String>("stream"));
        coursesType.setCellValueFactory(new PropertyValueFactory<Course, String>("type"));
        
        studentFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        studentLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        
        trainerFirstName.setCellValueFactory(new PropertyValueFactory<Trainer, String>("firstName"));
        trainerLastName.setCellValueFactory(new PropertyValueFactory<Trainer, String>("lastName"));
        
        assignmentTitle.setCellValueFactory(new PropertyValueFactory<Assignment, String>("title"));
        assignmentDescription.setCellValueFactory(new PropertyValueFactory<Assignment, String>("description"));
        assignmentDeadline.setCellValueFactory(new PropertyValueFactory<Assignment, LocalDate>("subDateTime"));
        
        avaliableStudentsFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        avaliableStudentsLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        avaliableStudentsBirthday.setCellValueFactory(new PropertyValueFactory<Student, LocalDate>("dateOfBirth"));
        avaliableStudentsTuitionFees.setCellValueFactory(new PropertyValueFactory<Student, Double>("tuitionFees"));
        
        avaliableTrainersFirstName.setCellValueFactory(new PropertyValueFactory<Trainer, String>("firstName"));
        avaliableTrainersLastName.setCellValueFactory(new PropertyValueFactory<Trainer, String>("lastName"));
        avaliableTrainersSubject.setCellValueFactory(new PropertyValueFactory<Trainer, String>("subject"));
        
        avaliableAssignmentsTitle.setCellValueFactory(new PropertyValueFactory<Assignment, String>("title"));
        avaliableAssignmentsDescription.setCellValueFactory(new PropertyValueFactory<Assignment, String>("description"));
        avaliableAssignmentsDeadline.setCellValueFactory(new PropertyValueFactory<Assignment, LocalDate>("subDateTime"));
        avaliableAssignmentsOralMark.setCellValueFactory(new PropertyValueFactory<Assignment, Integer>("oralMark"));
        avaliableAssignmentsTotalMark.setCellValueFactory(new PropertyValueFactory<Assignment, Integer>("totalMark"));
        
        
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
        
        // sets the converter to datePicekrs
        startsDatepicker.setConverter(converter);
        startsDatepicker.setPromptText(pattern.toLowerCase());
        endsDatepicker.setConverter(converter);
        endsDatepicker.setPromptText(pattern.toLowerCase());
        
        //sets the style in datePickers
        startsDatepicker.getStyleClass().add("non-editable-datepicker");
        endsDatepicker.getStyleClass().add("non-editable-datepicker");
        
        //sets the converter to the LodalDate type columns
        assignmentDeadline.setCellFactory(column -> assignmentDateColumn.tableConverter() );
        avaliableStudentsBirthday.setCellFactory(column -> studentDateColumn.tableConverter() );
        avaliableAssignmentsDeadline.setCellFactory(column -> assignmentDateColumn.tableConverter() );
        
        //if there is a connection to the server then update the TableViews with data, else
        // initialize them with error labes
        if (data.checkDbConnection()){
            if(data.checkTables() == "OK"){
                updateCoursesTable(0);
            }
        }
        else{
            
            notification.warning("The program can't connect to the server.\n Please check if server is offline or\n go to change the Server Properties!");
            coursesTable.setPlaceholder(new Label("There are no connection with the server. Please check the Server Properties!"));
            studentTable.setPlaceholder(new Label("There are no connection with the server. Please check the Server Properties!"));
            trainerTable.setPlaceholder(new Label("There are no connection with the server. Please check the Server Properties!"));
            assignmentTable.setPlaceholder(new Label("There are no connection with the server. Please check the Server Properties!"));
          
        }
        
    }    

    /**
     * Saves the selected course to course variable and updates the details and the tables
     * @param event 
     */
    @FXML
    private void selectCourse(MouseEvent event) {
        course = (Course) coursesTable.getSelectionModel().getSelectedItem();
        updateCourseDetails();
        updateStudentsTable();
        updateTrainersTable();
        updateAssignmentsTable();
    }

    /**
     * Lets the user to create a new course
     * @param event 
     */
    @FXML
    private void addCourse(MouseEvent event) {
        whatToSave = "NEW";
        editMode();
        clearDetailFields();
    }

    /**
     * Deletes the chosen course
     * @param event 
     */
    @FXML
    private void deleteCourse(MouseEvent event) {
        boolean result = alert.show("Confirmation!", "Do you want to remove "+ course.getTitle()+" "+ course.getType()+ " ?");
        if(result){
            // Get selected row and delete
            int ix = coursesTable.getSelectionModel().getSelectedIndex();
            course = (Course) coursesTable.getSelectionModel().getSelectedItem();
            Course.delete(course);
            coursesTable.getItems().remove(ix);
            notification.success("You have deleted the course successfully!");
            
            // Select a row
            if(coursesTable.getItems().size()== 0){
                coursesTable.getItems().clear();
                coursesTable.setPlaceholder(new Label("There are no other students"));
                deleteCourseButton.setDisable(true);
            }
            updateCoursesTable(coursesTable.getItems().size()-1);
        }
    }

    /**
     * Cancels the creating/editing procedure and returns the program to search mode
     * @param event 
     */
    @FXML
    private void cancelTheSave(MouseEvent event) {
        typeText.setText(course.getType());
        titleText.setText(course.getTitle());
        streamText.setText(course.getStream());
        startsDatepicker.setValue(course.getStart_date());
        endsDatepicker.setValue(course.getEnd_date());
        deleteCourseButton.setDisable(false);
        if(whatToSave == "EDIT"){
            notification.info("You canceled the editing process!");
        }
        else if(whatToSave == "NEW"){
            notification.info("You canceled the entering process!");
        }
        searchMode();
    }

    /**
     * saves the new or edited course
     * @param event 
     */
    @FXML
    private void saveDetails(MouseEvent event) {
        int ix = coursesTable.getSelectionModel().getSelectedIndex();
        boolean result;
        if(titleText.getText().equals("")){
            detailsErrorLabel.setText("Please write the course's title and try again!");
            detailsErrorLabel.setVisible(true);
            notification.warning("Please write the course's title and try again!");
        }
        else if(typeText.getText().equals("")){
            detailsErrorLabel.setText("Please write the course's type and try again!");
            notification.warning("Please write the course's type and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(streamText.getText().equals("")){
            detailsErrorLabel.setText("Please write the course's stream and try again!");
            notification.warning("Please write the course's stream and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(startsDatepicker.getValue()==null){
            detailsErrorLabel.setText("Please give the course's starting date and try again!");
            notification.warning("Please give the course's starting date and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(endsDatepicker.getValue()==null){
            detailsErrorLabel.setText("Please give the course's ending date and try again!");
            notification.warning("Please give the course's ending date and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(startsDatepicker.getValue().isAfter(endsDatepicker.getValue())){
            notification.warning("The ending date must be after starting date");
            detailsErrorLabel.setText("The ending date must be after starting date");
            detailsErrorLabel.setVisible(true);
        }
        else{
            if(whatToSave == "NEW"){
                result = alert.show("Confirmation!", "Do you want to save the changes?");
                if(result){
                    Course newCourse = new Course(titleText.getText(),streamText.getText(),typeText.getText(),startsDatepicker.getValue(),endsDatepicker.getValue());
                    if(!Course.exists(newCourse)){
                        Course.saveCourse(newCourse);
                        course= newCourse;
                        detailsErrorLabel.setVisible(false);
                        notification.success("You have entered the course successfully!");
                        
                        coursesTable.requestFocus();
                        coursesTable.getSelectionModel().selectLast();
                        searchMode();
                        ix= Course.getAllCourses().size();
                        coursesTable.getFocusModel().focus(ix-1);
                        updateCoursesTable(ix-1);
                    }
                    else{
                        detailsErrorLabel.setText("The course allready exists. Please change some fields");
                        notification.warning("The course allready exists. Please change some fields!");
                    }
                }
            }
            else if(whatToSave == "EDIT"){
                result = alert.show("Confirmation!", "Do you want to update the changes?");
                if(result){
                    course.setTitle(titleText.getText());
                    course.setStream(streamText.getText());
                    course.setType(typeText.getText());
                    course.setStart_date(startsDatepicker.getValue());
                    course.setEnd_date(endsDatepicker.getValue());
                    course.update();
                    notification.success("You have updated the course successfully!");
                    detailsErrorLabel.setVisible(false);
                    searchMode();
                    updateCoursesTable(coursesTable.getSelectionModel().getSelectedIndex());
                }
            }
        }
    }

    /**
     * Sets the program to editing mode
     * @param event 
     */
    @FXML
    private void editDetails(MouseEvent event) {
        whatToSave = "EDIT";
        editMode();
    }

    
    /**
     * Saves the selected student to the student variable
     * @param event 
     */
    @FXML
    private void studentSelected(MouseEvent event) {
        student = (Student) studentTable.getSelectionModel().getSelectedItem();
    }
    
    /**
     * Remove the selected student from the course
     * @param event 
     */
    @FXML
    private void removestudent(MouseEvent event) {
        boolean result = alert.show("Confirmation!", "Are you sure that you want to remove the student from the course\n  "+course.getTitle()+ "   "+course.getStream()+" ?");
        if(result){
            int intex = coursesTable.getSelectionModel().getSelectedIndex();
            course.removeStudent(student);
            notification.success("You have removed the student from the course successfully!");
            updateCoursesTable(intex);
        }
    }

    /**
     * Shows the window to let the user choose a student to add in the course
     * @param event 
     */
    @FXML
    private void addStudent(MouseEvent event) {
        updateAvaliableStudentsTable();
        prepareSlideMenuAnimation(addStudentPane);
    }
    
    /**
     * Shows the window to let the user choose a trainer to add in the course
     * @param event 
     */
    @FXML
    private void addTrainer(MouseEvent event) {
        updateAvaliableTrainersTable();
        prepareSlideMenuAnimation(addTrainerPane);
    }

    /**
     * Shows the window to let the user choose an assignment to add in the course
     * @param event 
     */
    @FXML
    private void addAssignment(MouseEvent event) {
        updateAvaliableAssignmentsTable();
        prepareSlideMenuAnimation(addAssignmentPane);
    }

    /**
     * Saves the selected trainer in the trainer variable
     * @param event 
     */
    @FXML
    private void trainerSelected(MouseEvent event) {
        trainer = (Trainer) trainerTable.getSelectionModel().getSelectedItem();
    }

    /**
     * Removes the chosen trainer from the course.
     * @param event 
     */
    @FXML
    private void removeTrainer(MouseEvent event) {
        boolean result = alert.show("Confirmation!", "Are you sure that you want to remove the trainer from the course\n  "+course.getTitle()+ "   "+course.getStream()+" ?");
        if(result){
            int intex = coursesTable.getSelectionModel().getSelectedIndex();
            course.removeTrainer(trainer);
            notification.success("You have removed the trainer from the course successfully!");
            updateCoursesTable(intex);
        }
    }

    /**
     * Saves the selected assignment in the assignment variable
     * @param event 
     */
    @FXML
    private void assignmentSelected(MouseEvent event) {
        assignment = (Assignment) assignmentTable.getSelectionModel().getSelectedItem();
    }
    
    /**
     * Removes the chosen assignment from the course.
     * @param event 
     */
     @FXML
    private void removeAssignment(MouseEvent event) {
        boolean result = alert.show("Confirmation!", "Are you sure that you want to remove the assignment from the course\n  "+course.getTitle()+ "   "+course.getStream()+" ?");
        if(result){
            int intex = coursesTable.getSelectionModel().getSelectedIndex();
            assignment.removeCourse(course);
            notification.success("You have removed the assignment from the course successfully!");
            updateCoursesTable(intex);
        }
    }

    
    /**
     * Closes the add student window
     * @param event 
     */
    @FXML
    private void cancelAddStudent(MouseEvent event) {
        prepareSlideMenuAnimation(addStudentPane);
    }

    /**
     * Adds the selected student to the course
     * @param event 
     */
    @FXML
    private void addStudentToCourse(MouseEvent event) {
        avaliableStudent = (Student) avaliableStudents.getSelectionModel().getSelectedItem();
        boolean result = alert.show("Confirmation!", "Are you sure that you want to add the studebt to this course ?");
        if(result){
            course.addStudent(avaliableStudent);
            updateAvaliableStudentsTable();
            notification.success("The student "+avaliableStudent.getFirstName()+" "+avaliableStudent.getLastName() + " has been added to the course!");
        }
    }

    /**
     * Closes the add Trainer window
     * @param event 
     */
    @FXML
    private void cancelAddTrainer(MouseEvent event) {
        prepareSlideMenuAnimation(addTrainerPane);
    }

    /**
     * Adds the selected trainer to course
     * @param event 
     */
    @FXML
    private void addTrainerToCourse(MouseEvent event) {
        avaliableTrainer = (Trainer) avaliableTrainers.getSelectionModel().getSelectedItem();
        boolean result = alert.show("Confirmation!", "Are you sure that you want to add the trainer to this course ?");
        if(result){
            course.addTrainer(avaliableTrainer);
            updateAvaliableTrainersTable();
            notification.success("The trainer "+avaliableTrainer.getFirstName()+" "+avaliableTrainer.getLastName() + " has been added to the course!");
        }
    }

    
    /**
     * Closes the add assignemt window
     * @param event 
     */
    @FXML
    private void cancelAddAssignment(MouseEvent event) {
        prepareSlideMenuAnimation(addAssignmentPane);
    }

    
    /**
     * Adds the selected assignment to the course
     * @param event 
     */
    @FXML
    private void addAssignmentToCourse(MouseEvent event) {
        avaliableAssignment = (Assignment) avaliableAssignments.getSelectionModel().getSelectedItem();
        boolean result = alert.show("Confirmation!", "Are you sure that you want to add the assignment to this course ?");
        if(result){
            avaliableAssignment.addCourse(course);
            updateAvaliableAssignmentsTable();
            notification.success("The assignment has been added to the course!");
        }
        
    }
    
    /**
     * Opens and closes the given pane (window)
     * @param extraPane 
     */
    private void prepareSlideMenuAnimation(VBox extraPane) {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), extraPane);
        openNav.setToX(400);
        
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), extraPane);
        if(extraPane.getTranslateX()!=400){
                openNav.play();
                deactivateForExtraPane();
                
                
            }else{
                closeNav.setToX(+(extraPane.getWidth()+400));
                closeNav.play();
                activateForExtraPane();
                
                updateCoursesTable(coursesTable.getSelectionModel().getSelectedIndex());

            }
        
    }
    
    /**
     * Deactivates some tables and buttons when the extra windows opened
     */
    private void deactivateForExtraPane(){
        coursesTable.setDisable(true);
        addCourseButton.setDisable(true);
        deleteCourseButton.setDisable(true);
        studentTable.setDisable(true);
        
    }
    
    /**
     * Activates back the tables and buttons when the extra window closed
     */
    private void activateForExtraPane(){
        coursesTable.setDisable(false);
        addCourseButton.setDisable(false);
        deleteCourseButton.setDisable(false);
        studentTable.setDisable(false);
    }

    
    //  ---------- Fill Tables ---------------
    
    private ObservableList<Course> getCourses(){
        ObservableList<Course> coursesObList = FXCollections.observableArrayList();
        coursesObList.addAll(Course.getAllCourses());
        return coursesObList;
    }
    
    
    private ObservableList<Student> getStudents(){
        ObservableList<Student> studentsObList = FXCollections.observableArrayList();
        studentsObList.addAll(course.getStudents());
        return studentsObList;
    }
    
    
    private ObservableList<Trainer> getTrainers(){
        ObservableList<Trainer> trainersObList = FXCollections.observableArrayList();
        trainersObList.addAll(course.getTrainers());
        return trainersObList;
    }
    
    
    private ObservableList<Assignment> getAssignments(){
        ObservableList<Assignment> assignmentsObList = FXCollections.observableArrayList();
        assignmentsObList.addAll(course.getAssignments());
        return assignmentsObList;
    }
    
    private ObservableList<Student> getAvaliableStudents(){
        ObservableList<Student> studentsObList = FXCollections.observableArrayList();
        studentsObList.addAll(course.getUnregisteredStudents());
        return studentsObList;
    }
    
    private ObservableList<Trainer> getAvaliableTrainers(){
        ObservableList<Trainer> trainersObList = FXCollections.observableArrayList();
        trainersObList.addAll(course.getUnregisteredTrainers());
        return trainersObList;
    }
    
    
    private ObservableList<Assignment> getAvaliableAssignments(){
        ObservableList<Assignment> assignmentsObList = FXCollections.observableArrayList();
        assignmentsObList.addAll(course.getUnregisteredAssignments());
        return assignmentsObList;
    }
    
    // -----------------  All methods that update the tables  ------------------
    private void updateCoursesTable( int selection){
        addCourseButton.setDisable(false);
        if(Course.getAllCourses().size()>0){
            
            coursesTable.getItems().clear();
            coursesTable.getItems().setAll(getCourses());
            coursesTable.requestFocus();
            coursesTable.getSelectionModel().select(selection);
            
            editDetailsButton.setDisable(false);
            deleteCourseButton.setDisable(false);
            addStudentButton.setDisable(false);
            addTrainerButton.setDisable(false);
            addAssignmentButton.setDisable(false);
            course = (Course) coursesTable.getSelectionModel().getSelectedItem();
            
        }
        else{
            editDetailsButton.setDisable(true);
            coursesTable.getItems().clear();
            coursesTable.setPlaceholder(new Label("There are no Courses"));
            student=null;
        }
        updateCourseDetails();
        updateStudentsTable();
        updateTrainersTable();
        updateAssignmentsTable();
    }
    
    private void updateStudentsTable(){
        if(course!= null ){
            if( course.getStudents().size()>0){
                studentTable.getItems().setAll(getStudents());
                studentTable.requestFocus();
                studentTable.getSelectionModel().select(0);
                removeStudentButton.setDisable(false);
                student = (Student) studentTable.getSelectionModel().getSelectedItem();
            }
            else{
                student=null;
                studentTable.getItems().clear();
                removeStudentButton.setDisable(true);
                studentTable.setPlaceholder(new Label("The Course has no Students"));
            }
        }
        else{
            studentTable.getItems().clear();
            removeStudentButton.setDisable(true);
            studentTable.setPlaceholder(new Label("You have to select a Course first"));
        }
    }
    
    
    private void updateTrainersTable(){
        if(course!= null ){
            if( course.getTrainers().size()>0){
                trainerTable.getItems().setAll(getTrainers());
                trainerTable.requestFocus();
                trainerTable.getSelectionModel().select(0);
                removeTrainerButton.setDisable(false);
                trainer = (Trainer) trainerTable.getSelectionModel().getSelectedItem();
            }
            else{
                trainer=null;
                trainerTable.getItems().clear();
                removeTrainerButton.setDisable(true);
                trainerTable.setPlaceholder(new Label("The Course has no Trainers"));
            }
        }
        else{
            trainerTable.getItems().clear();
            removeTrainerButton.setDisable(true);
            trainerTable.setPlaceholder(new Label("You have to select a Course first"));
        }
    }
    
    private void updateAssignmentsTable(){
        if(course!= null ){
            if( course.getAssignments().size()>0){
                assignmentTable.getItems().setAll(getAssignments());
                assignmentTable.requestFocus();
                assignmentTable.getSelectionModel().select(0);
                removeAssignmentButton.setDisable(false);
                assignment = (Assignment) assignmentTable.getSelectionModel().getSelectedItem();
            }
            else{
                assignment=null;
                assignmentTable.getItems().clear();
                removeAssignmentButton.setDisable(true);
                assignmentTable.setPlaceholder(new Label("The Course has no Assignments"));
            }
        }
        else{
            assignmentTable.getItems().clear();
            removeAssignmentButton.setDisable(true);
            assignmentTable.setPlaceholder(new Label("You have to select a Course first"));
        }
    }
    
    
    private void updateAvaliableStudentsTable(){
        if( course.getUnregisteredStudents().size()>0){
            avaliableStudents.getItems().setAll(getAvaliableStudents());
        }
        else{
            avaliableStudent=null;
            
            avaliableStudents.getItems().clear();
            
            avaliableStudents.setPlaceholder(new Label("There are no other students that you can add to that course!"));
        }
    }
    
    
    private void updateAvaliableTrainersTable(){
        if( course.getUnregisteredTrainers().size()>0){
            avaliableTrainers.getItems().setAll(getAvaliableTrainers());
        }
        else{
            avaliableTrainer=null;
            avaliableTrainers.getItems().clear();
            avaliableTrainers.setPlaceholder(new Label("There are no other trainers that you can add to that course!"));
        }
    }
    
    
    private void updateAvaliableAssignmentsTable(){
        if( course.getUnregisteredAssignments().size()>0){
            avaliableAssignments.getItems().setAll(getAvaliableAssignments());
        }
        else{
            avaliableAssignment=null;
            avaliableAssignments.getItems().clear();
            avaliableAssignments.setPlaceholder(new Label("There are no other assignments that you can add to that course!"));
        }
    }
    
    
    private void updateCourseDetails(){
        if(course==null){
            clearDetailFields();
            deleteCourseButton.setDisable(true);
        }
        else{
            titleText.setText(course.getTitle());
            streamText.setText(course.getStream());
            typeText.setText(course.getType());
            startsDatepicker.setValue(course.getStart_date());
            endsDatepicker.setValue(course.getEnd_date());
        }
    }
    
    
    /**
     * Clears the detail fields
     */
    private void clearDetailFields(){
        titleText.setText("");
        streamText.setText("");
        typeText.setText("");
        startsDatepicker.setValue(null);
        endsDatepicker.setValue(null);
    }
    
    /**
     * Sets the program in edit mode
     */
    private void editMode(){
        cancelTheSaveButton.setDisable(false);
        cancelTheSaveButton.setVisible(true);
        saveDetailsButton.setDisable(false);
        saveDetailsButton.setVisible(true);
        editDetailsButton.setDisable(true);
        editDetailsButton.setVisible(false);
        titleText.setEditable(true);
        typeText.setEditable(true);
        streamText.setEditable(true);
        startsDatepicker.setEditable(true);
        endsDatepicker.setEditable(true);
        startsDatepicker.getStyleClass().remove("non-editable-datepicker");
        endsDatepicker.getStyleClass().remove("non-editable-datepicker");
        
        coursesTable.setDisable(true);
        addCourseButton.setDisable(true);
        deleteCourseButton.setDisable(true);
        
        studentTable.setDisable(true);
        addStudentButton.setDisable(true);
        removeStudentButton.setDisable(true);
        
        trainerTable.setDisable(true);
        addTrainerButton.setDisable(true);
        removeTrainerButton.setDisable(true);
        
        assignmentTable.setDisable(true);
        addAssignmentButton.setDisable(true);
        removeAssignmentButton.setDisable(true);
        
    }
    
    
    /**
     * Sets the program in search mode
     */
    private void searchMode(){
        cancelTheSaveButton.setDisable(true);
        cancelTheSaveButton.setVisible(false);
        saveDetailsButton.setDisable(true);
        saveDetailsButton.setVisible(false);
        editDetailsButton.setDisable(false);
        editDetailsButton.setVisible(true);
        titleText.setEditable(false);
        typeText.setEditable(false);
        streamText.setEditable(false);
        startsDatepicker.setEditable(false);
        endsDatepicker.setEditable(false);
        startsDatepicker.getStyleClass().add("non-editable-datepicker");
        endsDatepicker.getStyleClass().add("non-editable-datepicker");
        
        coursesTable.setDisable(false);
        addCourseButton.setDisable(false);
        deleteCourseButton.setDisable(false);
        
        studentTable.setDisable(false);
        addStudentButton.setDisable(false);
        removeStudentButton.setDisable(false);
        
        trainerTable.setDisable(false);
        addTrainerButton.setDisable(false);
        removeTrainerButton.setDisable(false);
        
        assignmentTable.setDisable(false);
        addAssignmentButton.setDisable(false);
        removeAssignmentButton.setDisable(false);
    }

   
    
}
