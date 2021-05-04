
package scoolwithjavafx;


import Utils.DoubleTextField;
import Utils.LetterTextField;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.StringConverter;
import Entities.Assignment;
import Entities.Course;
import Entities.GradesForTables;
import Entities.Student;
import Entities.StudentGrade;
import Entities.dao.GenericDao;
import Utils.NumberTextField;
import Utils.YesOrNoWindow;

/**
 * FXML Controller class
 *
 * @author Tsepetzidis Nikos
 */
public class StudentPaneController implements Initializable {

    @FXML private TableView<Student> allStudents;
    @FXML private TableColumn<Student, String> firstName;
    @FXML private TableColumn<Student, String> lastName;
    @FXML private TableColumn<Student, LocalDate> birthDate;
    @FXML private TableColumn<Student, Double> tuitionFees;
    @FXML private Button addStudentButton;
    @FXML private Button deleteStudentButton;
    @FXML private LetterTextField firstNameText;
    @FXML private LetterTextField lastNameText;
    @FXML private DatePicker birthDatePicker;
    @FXML private DoubleTextField tuitionFeesText;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private Button editButton;
    @FXML private Label detailsErrorLabel;
    
    @FXML private TableView<Course> studentCourses;
    @FXML private TableColumn<Course, String> courseTitle;
    @FXML private TableColumn<Course, String> courseStream;
    @FXML private TableColumn<Course, String> courseType;
    @FXML private TableColumn<Course, LocalDate> courseStarts;
    @FXML private TableColumn<Course, LocalDate> courseEnds;
    
    @FXML private Button addCourse;
    @FXML private Button removeCourse;
    
    @FXML private TableView<GradesForTables> studentAssignments;
    @FXML private TableColumn<GradesForTables, String> assignmentTitle;
    @FXML private TableColumn<GradesForTables, String> assignmentDescription;
    @FXML private TableColumn<GradesForTables, LocalDate> assignmentDeadline;
    @FXML private TableColumn<GradesForTables, Integer> assignmentOralMark;
    @FXML private TableColumn<GradesForTables, Integer> assignmentTotalMark;
    @FXML private TableColumn<GradesForTables, String> assignmentDelivered;
    @FXML private VBox addCoursePane;
    @FXML private Button cancelAddCourseButton;
    @FXML private NumberTextField numberOfCoursesTextfield;
    @FXML private CheckBox searchCheckbox;
    @FXML private TableView<Course> avaliableCourses;
    @FXML private TableColumn<Course, String> avaliableCoursesTitle;
    @FXML private TableColumn<Course, String> avaliableCoursesStream;
    @FXML private TableColumn<Course, String> avaliableCoursesType;
    @FXML private TableColumn<Course, LocalDate> avaliableCoursesStarts;
    @FXML private TableColumn<Course, LocalDate> avaliableCoursesEnds;
    @FXML
    private VBox editStudentGrade;
    @FXML
    private Button goBackButton;
    @FXML
    private Button saveGrade;
    @FXML
    private Button editGrade;
    @FXML
    private NumberTextField oralMarkTextfield;
    @FXML
    private Label oralMarkLabel;
    @FXML
    private NumberTextField totalMarkTextfield;
    @FXML
    private Label totalMarkLabel;
    @FXML
    private Button cancelSaveGrade;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private DatePicker deadlinePicker;
    
    
    private Student student;
    private Course course;
    private String whatToSave;
    private Course selectedCourseToAdd;
    private Assignment assignment;
    private GradesForTables gradesForTables;
    private StudentGrade studentGrade;
    
    private final String pattern = GenericDao.daTiFormat;
    
    private MyNotification notification = new MyNotification();
    private YesOrNoWindow alert = new YesOrNoWindow();
    
    private MyTableColumn<Student, LocalDate> birthDayColumn = new MyTableColumn<>();
    private MyTableColumn<Course, LocalDate> courseDateColumn = new MyTableColumn<>();
    private MyTableColumn<GradesForTables, LocalDate> assignmentDateColumn = new MyTableColumn<>();
    
    GenericDao data = GenericDao.getInstance();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Everytime program initializes the Pane it sets to null the entities
        student = null;
        course = null;
        assignment = null;
        gradesForTables = null;
        studentGrade = null;
        
        // sets the propertyValueFactory in every table's column
        firstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        birthDate.setCellValueFactory(new PropertyValueFactory<Student, LocalDate>("dateOfBirth"));
        tuitionFees.setCellValueFactory(new PropertyValueFactory<Student, Double>("tuitionFees"));
        
        
        courseTitle.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
        courseStream.setCellValueFactory(new PropertyValueFactory<Course, String>("stream"));
        courseType.setCellValueFactory(new PropertyValueFactory<Course, String>("type"));
        courseStarts.setCellValueFactory(new PropertyValueFactory<Course, LocalDate>("start_date"));
        courseEnds.setCellValueFactory(new PropertyValueFactory<Course, LocalDate>("end_date"));
        
        assignmentTitle.setCellValueFactory(new PropertyValueFactory<GradesForTables, String>("title"));
        assignmentDescription.setCellValueFactory(new PropertyValueFactory<GradesForTables, String>("description"));
        assignmentDeadline.setCellValueFactory(new PropertyValueFactory<GradesForTables, LocalDate>("deadline"));
        assignmentOralMark.setCellValueFactory(new PropertyValueFactory<GradesForTables, Integer>("oralMark"));
        assignmentTotalMark.setCellValueFactory(new PropertyValueFactory<GradesForTables, Integer>("totalMark"));
        assignmentDelivered.setCellValueFactory(new PropertyValueFactory<GradesForTables, String>("delivered"));
        
        //Sets some allignment settings in some columns
        assignmentOralMark.setStyle( "-fx-alignment: CENTER-RIGHT;");
        assignmentTotalMark.setStyle( "-fx-alignment: CENTER-RIGHT;");
        assignmentDelivered.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
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
        
       
        descriptionTextArea.setWrapText(true);
        
        // sets the converter to datePicker
        birthDatePicker.setConverter(converter);
        birthDatePicker.setPromptText(pattern.toLowerCase());
        
        //sets the style in datePicker
        birthDatePicker.getStyleClass().add("non-editable-datepicker");
        deadlinePicker.getStyleClass().add("non-editable-datepicker");
        
        //sets the converter to the LodalDate type columns
        birthDate.setCellFactory(column -> birthDayColumn.tableConverter() );
        courseStarts.setCellFactory(column -> courseDateColumn.tableConverter() );
        courseEnds.setCellFactory(column -> courseDateColumn.tableConverter() );
        assignmentDeadline.setCellFactory(column -> assignmentDateColumn.tableConverter() );
        avaliableCoursesStarts.setCellFactory(column -> courseDateColumn.tableConverter() );
        avaliableCoursesEnds.setCellFactory(column -> courseDateColumn.tableConverter() );
        
        
        //if there is a connection to the database then update the TableViews with data, else
        // initialize them with error labes
        if (data.checkDbConnection()){
            if(data.checkTables() == "OK"){
                updateStudentTable(getStudents(),0);
                searchCheckbox.setDisable(false);
            }
        }
        else{
            searchCheckbox.setDisable(true);
            notification.warning("The program can't connect to the server.\n Please check if server is offline or\n go to change the Server Properties!");
            allStudents.setPlaceholder(new Label("There are no connection with the server. Please check the Server Properties!"));
            studentCourses.setPlaceholder(new Label("There are no connection with the server. Please check the Server Properties!"));
            studentAssignments.setPlaceholder(new Label("There are no connection with the server. Please check the Server Properties!"));
          
        }
    }    
    
            
    
    /**
     * Saves the selected student to the student variable and updates the student's details
     * and student's courses in coursesTable
     * @param event 
     */
    @FXML
    private void studentSelected(MouseEvent event) {
        student = (Student) allStudents.getSelectionModel().getSelectedItem();
        updateStudentDetails();
        updateStudentCoursesTable();
    }
    
    
    /**
     * Saves the selected course to the course variable and updates the course's assignments
     * in studentAssignments table
     * @param event 
     */
    @FXML
    private void courseSelected(MouseEvent event) {
        course = (Course) studentCourses.getSelectionModel().getSelectedItem();
        updateStudentAssignmentsTable();
    }
    
    

    /**
     * Sets the program to create mode
     * @param event 
     */
    @FXML
    private void addStudent(MouseEvent event) {
        whatToSave = "NEW";
        editMode();
        clearDetailFields();
    }

    
    /**
     * Deletes the selected student from the database
     * @param event 
     */
    @FXML
    private void removeStudent(MouseEvent event) {
        boolean result = alert.show("Confirmation!", "Do you want to remove "+ student.getFirstName()+" "+ student.getLastName()+ " ?");
        if(result){
            // Get selected row and delete
            int ix = allStudents.getSelectionModel().getSelectedIndex();
            student = (Student) allStudents.getSelectionModel().getSelectedItem();
            
            Student.delete(student);
            allStudents.getItems().remove(ix);
            notification.success("You have deleted the student successfully!");
            
            // Select a row
            if(allStudents.getItems().size()== 0){
                allStudents.getItems().clear();
                allStudents.setPlaceholder(new Label("There are no other students"));
                deleteStudentButton.setDisable(true);
            }
            if(searchCheckbox.isSelected()){
                updateStudentTable(getStudentsForCourseNumber(Integer.parseInt(numberOfCoursesTextfield.getText())),allStudents.getItems().size()-1);
            }
            else{
                updateStudentTable(getStudents(),allStudents.getItems().size()-1);
            }
        }
    }

    
    /**
     * Save the details of the new or edited student
     * @param event 
     */
    @FXML
    private void saveDetails(MouseEvent event) {
        int ix = allStudents.getSelectionModel().getSelectedIndex();
        boolean result;

        if(firstNameText.getText().equals("")){
            detailsErrorLabel.setText("Please write the student's first name and try again!");
            detailsErrorLabel.setVisible(true);
            notification.warning("Please write the student's first name and try again!");
        }
        else if(lastNameText.getText().equals("")){
            detailsErrorLabel.setText("Please write the student's last name and try again!");
            notification.warning("Please write the student's last name and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(tuitionFeesText.getText().equals("")){
            detailsErrorLabel.setText("Please write the student's tuition fees and try again!");
            notification.warning("Please write the student's tuition fees and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(birthDatePicker.getValue()==null){
            detailsErrorLabel.setText("Please give the student's birthday and try again!");
            notification.warning("Please give the student's birthday and try again!");
            detailsErrorLabel.setVisible(true);
        }
        else if(LocalDate.now().minusYears(15).isBefore(birthDatePicker.getValue())){
            notification.warning("The student must be at least 15 years old!");
            detailsErrorLabel.setText("The student must be at least 15 years old!");
            detailsErrorLabel.setVisible(true);
        }
        else{ 
            
            if(whatToSave == "NEW"){
                result = alert.show("Confirmation!", "Do you want to save the changes?");
                
                if(result){
                    Student newStudent = new Student(firstNameText.getText(),lastNameText.getText(),birthDatePicker.getValue(),Double.valueOf(tuitionFeesText.getText()));
                    
                    if(!Student.exists(newStudent)){
                        Student.saveStudent(newStudent);
                        student= newStudent;
                        detailsErrorLabel.setVisible(false);
                        notification.success("You have entered the student successfully!");
                        searchMode();
                        
                        if(searchCheckbox.isSelected()){
                            updateStudentTable(getStudentsForCourseNumber(Integer.parseInt(numberOfCoursesTextfield.getText())),0);
                        }
                        else{
                            updateStudentTable(getStudents(),ix-1);
                            allStudents.requestFocus();
                            allStudents.getSelectionModel().selectLast();
                            ix= Student.getAllStudents().size();
                            allStudents.getFocusModel().focus(ix-1);
                        }
                    }
                    else{
                        detailsErrorLabel.setText("The student allready exists. Please change some fields");
                        notification.warning("The student allready exists. Please change some fields!");
                    }
                }
            }
            else if(whatToSave == "EDIT"){
                result = alert.show("Confirmation!", "Do you want to update the changes?");
                
                if(result){
                    student.setFirstName(firstNameText.getText());
                    student.setLastName(lastNameText.getText());
                    student.setDateOfBirth(birthDatePicker.getValue());
                    student.setTuitionFees(Double.valueOf(tuitionFeesText.getText()));
                    student.update();
                    detailsErrorLabel.setVisible(false);
                    notification.success("You have updated the student successfully!");
                    searchMode();
                    if(searchCheckbox.isSelected()){
                        updateStudentTable(getStudentsForCourseNumber(Integer.parseInt(numberOfCoursesTextfield.getText())),allStudents.getSelectionModel().getSelectedIndex());
                    }
                    else{
                        updateStudentTable(getStudents(),allStudents.getSelectionModel().getSelectedIndex());
                    }
                }
            }
        }
    }

    
    /**
     * Sets the program in edit mode
     * @param event 
     */
    @FXML
    private void editDetails(MouseEvent event) {
        whatToSave = "EDIT";
        editMode();
    }
    
    
    /**
     * Returns the program back to search mode
     * @param event 
     */
    @FXML
    private void cancelTheSave(MouseEvent event) {
        firstNameText.setText(student.getFirstName());
        lastNameText.setText(student.getLastName());
        birthDatePicker.setValue(student.getDateOfBirth());
        tuitionFeesText.setText(String.valueOf(student.getTuitionFees()));
        deleteStudentButton.setDisable(false);
        if(whatToSave == "EDIT"){
            notification.info("You canceled the editing process!");
        }
        else if(whatToSave == "NEW"){
            notification.info("You canceled the entering process!");
        }
        searchMode();
    }

    

    /**
     * Adds the student into the selected course
     * @param event 
     */
    @FXML
    private void addCourseToStudent(MouseEvent event) {
        selectedCourseToAdd = (Course) avaliableCourses.getSelectionModel().getSelectedItem();
        boolean result = alert.show("Confirmation!", "Are you sure that you want to add the student to this course\n  "+selectedCourseToAdd.getTitle()+ "   "+selectedCourseToAdd.getStream()+" ?");
        if(result){
            selectedCourseToAdd.addStudent(student);
            updateAvaliableCoursesTable();
            notification.success(student.getFirstName() + " " + student.getLastName()+" has been added to the course!");
        }
    }

    
    /**
     * Removes the student from the selected course
     * @param event 
     */
    @FXML
    private void removeCourseFromStudent(MouseEvent event) {
        boolean result = alert.show("Confirmation!", "Are you sure that you want to remove the student from the course\n  "+course.getTitle()+ "   "+course.getStream()+" ?");
        if(result){
            int intex = allStudents.getSelectionModel().getSelectedIndex();
            course.removeStudent(student);
            notification.success("You have removed the student from the course successfully!");
            if(searchCheckbox.isSelected()){
                updateStudentTable(getStudentsForCourseNumber(Integer.parseInt(numberOfCoursesTextfield.getText())),intex);
            }
            else{
                updateStudentTable(getStudents(),intex);
            }
        }
    }
    
    /**
     * Shows the add course window
     * @param event 
     */
    @FXML
    private void showAddCoursePane(MouseEvent event) {
        updateAvaliableCoursesTable();
        prepareSlideMenuAnimation();
    }
    
    
    /**
     * Hides the add course window
     * @param event 
     */
    @FXML
    private void cancelAddCourse(MouseEvent event) {
        prepareSlideMenuAnimation();
    }


    /**
     * Checks if the checkbox is selected and activates or deactivates some program elements
     * @param event 
     */
    @FXML
    private void checkTheCheboxCondition(MouseEvent event) {
        if(searchCheckbox.isSelected()){
            searchCheckbox.setText("Search students which are in more courses than :");
            numberOfCoursesTextfield.setDisable(false);
            numberOfCoursesTextfield.setVisible(true);
            numberOfCoursesTextfield.clear();
        }
        else{
            searchCheckbox.setText("Search ");
            numberOfCoursesTextfield.setDisable(true);
            numberOfCoursesTextfield.setVisible(false);
            numberOfCoursesTextfield.clear();
            updateStudentTable(getStudents(),0);
        }    
    }

    
    /**
     * Update the student table with the students who have more courses than the given number.
     * @param event 
     */
    @FXML
    private void updateStudentsWithExactNumberOfCourses(KeyEvent event) {
        if(numberOfCoursesTextfield.getText().equalsIgnoreCase(null) || numberOfCoursesTextfield.getText().equalsIgnoreCase("")){
            updateStudentTable(getStudents(),0);
        }
        else{
            updateStudentTable(getStudentsForCourseNumber(Integer.parseInt(numberOfCoursesTextfield.getText())),0);
        }
        numberOfCoursesTextfield.requestFocus();
    }
    
    
    /**
     * Clears the detail's text fields
     */
    private void clearDetailFields(){
        firstNameText.setText("");
        lastNameText.setText("");
        birthDatePicker.setValue(null);
        tuitionFeesText.setText(null);
    }
    
    
    /**
     * Sets the program to the editing mode
     */
    private void editMode(){
        cancelButton.setVisible(true);
        cancelButton.setDisable(false);
        saveButton.setVisible(true);
        saveButton.setDisable(false);
        editButton.setVisible(false);
        editButton.setDisable(true);
        firstNameText.setEditable(true);
        lastNameText.setEditable(true);
        birthDatePicker.setEditable(true);
        tuitionFeesText.setEditable(true);
        
        birthDatePicker.getStyleClass().remove("non-editable-datepicker");
        
        allStudents.setDisable(true);
        allStudents.selectionModelProperty().isNull();
        addStudentButton.setDisable(true);
        deleteStudentButton.setDisable(true);
        
        studentCourses.setDisable(true);
        addCourse.setDisable(true);
        removeCourse.setDisable(true);
        
        studentAssignments.setDisable(true);
    }
    
    /**
     * Sets the program in searching mode
     */
    private void searchMode(){
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
        saveButton.setVisible(false);
        saveButton.setDisable(true);
        editButton.setVisible(true);
        editButton.setDisable(false);
        firstNameText.setEditable(false);
        lastNameText.setEditable(false);
        birthDatePicker.setEditable(false);
        tuitionFeesText.setEditable(false);
        birthDatePicker.getStyleClass().add("non-editable-datepicker");
        
        detailsErrorLabel.setVisible(false);
        detailsErrorLabel.setText("");
        
        allStudents.setDisable(false);
        allStudents.selectionModelProperty().isNull();
        addStudentButton.setDisable(false);
        deleteStudentButton.setDisable(false);
        
        studentCourses.setDisable(false);
        addCourse.setDisable(false);
        removeCourse.setDisable(false);
        
        studentAssignments.setDisable(false);
    }

  
    /**
     * Opens and closes the window for add course
     */
    private void prepareSlideMenuAnimation() {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), addCoursePane);
        openNav.setToX(400);
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), addCoursePane);
        int selectedStudent = allStudents.getSelectionModel().getSelectedIndex();
        if(addCoursePane.getTranslateX()!=400){
                openNav.play();
                allStudents.setDisable(true);
                addStudentButton.setDisable(true);
                deleteStudentButton.setDisable(true);
                studentCourses.setDisable(true);
                studentAssignments.setDisable(true);
                searchCheckbox.setDisable(true);
                numberOfCoursesTextfield.setDisable(true);
                
            }else{
                closeNav.setToX(+(addCoursePane.getWidth()+400));
                closeNav.play();
                allStudents.setDisable(false);
                addStudentButton.setDisable(false);
                studentCourses.setDisable(false);
                studentAssignments.setDisable(false);
                searchCheckbox.setDisable(false);
                numberOfCoursesTextfield.setDisable(false);
                if(searchCheckbox.isSelected()){
                        updateStudentTable(getStudentsForCourseNumber(Integer.parseInt(numberOfCoursesTextfield.getText())),selectedStudent);
                    }
                    else{
                        updateStudentTable(getStudents(),selectedStudent);
                    }
            }
        
    }
    
    /**
     * Opens and closes the window for student grades
     */
    private void prepareSlideMenuAnimationForGrades() {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), editStudentGrade);
        openNav.setToX(400);
        
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), editStudentGrade);
        int selectedStudent = allStudents.getSelectionModel().getSelectedIndex();
        if(editStudentGrade.getTranslateX()!=400){
                openNav.play();
                allStudents.setDisable(true);
                addStudentButton.setDisable(true);
                deleteStudentButton.setDisable(true);
                studentCourses.setDisable(true);
                studentAssignments.setDisable(true);
                searchCheckbox.setDisable(true);
                numberOfCoursesTextfield.setDisable(true);
                
            }else{
                closeNav.setToX(+(addCoursePane.getWidth()+400));
                closeNav.play();
                allStudents.setDisable(false);
                addStudentButton.setDisable(false);
                studentCourses.setDisable(false);
                studentAssignments.setDisable(false);
                searchCheckbox.setDisable(false);
                numberOfCoursesTextfield.setDisable(false);
                if(searchCheckbox.isSelected()){
                        updateStudentTable(getStudentsForCourseNumber(Integer.parseInt(numberOfCoursesTextfield.getText())),selectedStudent);
                    }
                    else{
                        updateStudentTable(getStudents(),selectedStudent);
                    }
            }
        
    }
    

    // ---------------  all methods that returns the data for tables -----------
    private ObservableList<Student> getStudents(){
        ObservableList<Student> studentsObList = FXCollections.observableArrayList();
        studentsObList.addAll(Student.getAllStudents());
        return studentsObList;
    }
      
    private ObservableList<Course> getCourses(){
        ObservableList<Course> coursesObList = FXCollections.observableArrayList();
        coursesObList.addAll(student.getCourses());
        return coursesObList;
    }

    private ObservableList<GradesForTables> getAssignments(){
        ObservableList<GradesForTables> assignmentsObList = FXCollections.observableArrayList();
        assignmentsObList.addAll(student.getRatedAssignmentsForFrontENd(course));
        return assignmentsObList;
    }
    
    private ObservableList<Student> getStudentsForCourseNumber(int number){
        ObservableList<Student> studentsObList = FXCollections.observableArrayList();
        studentsObList.addAll(Student.getStudentsWhenLessonsMoreThan(number));
        return studentsObList;
    }
    
    
    private ObservableList<Course> getAvailableCoursesForStudent(){
        ObservableList<Course> coursesObList = FXCollections.observableArrayList();
        coursesObList.addAll(student.getAvaliableCourses());
        return coursesObList;
    }

    
    // ----- all the methods that update the tables  ---------------------------
    private void updateStudentTable(ObservableList<Student> list, int selection){
            
        addStudentButton.setDisable(false);
        if(list.size()>0){

            allStudents.getItems().clear();
            allStudents.getItems().setAll(list);
            allStudents.requestFocus();
            allStudents.getSelectionModel().select(selection);

            deleteStudentButton.setDisable(false);
            student = (Student) allStudents.getSelectionModel().getSelectedItem();
            editButton.setDisable(false);
            updateStudentDetails();
            updateStudentCoursesTable();
        }
        else{
            editButton.setDisable(true);
            allStudents.getItems().clear();
            allStudents.setPlaceholder(new Label("The Student has no Courses"));
            student=null;
            updateStudentDetails();
            updateStudentCoursesTable();
        }

    }
    
    private void updateStudentCoursesTable(){
        
        
        if(student!= null ){
            addCourse.setDisable(false);
            if( student.getCourses().size()>0){
                studentCourses.getItems().setAll(getCourses());
                studentCourses.requestFocus();
                studentCourses.getSelectionModel().select(0);
                removeCourse.setDisable(false);
                course = (Course) studentCourses.getSelectionModel().getSelectedItem();
                updateStudentAssignmentsTable();
            }
            else{
                course=null;
                studentCourses.getItems().clear();
                removeCourse.setDisable(true);
                studentCourses.setPlaceholder(new Label("The Student has no Courses"));
                updateStudentAssignmentsTable();
            }
        }
        else{
            addCourse.setDisable(true);
            studentCourses.getItems().clear();
            removeCourse.setDisable(true);
            studentCourses.setPlaceholder(new Label("You have to select a Student first"));
            updateStudentAssignmentsTable();
        }
        updateStudentAssignmentsTable();
    }
    
    private void updateStudentAssignmentsTable(){
        if(student == null){
            studentAssignments.getItems().clear();
            studentAssignments.setPlaceholder(new Label("You have to select a Student first"));
        }
        else if( course == null){
            studentAssignments.getItems().clear();
            studentAssignments.setPlaceholder(new Label("The Student has to be in a course to have assignments"));
        }
        else if(getAssignments().isEmpty()){
            studentAssignments.getItems().clear();
            studentAssignments.setPlaceholder(new Label("There are no assignments in that course"));
        }
        else{
            studentAssignments.getItems().clear();
            studentAssignments.getItems().setAll(getAssignments());
        }
    }
    
    
    private void updateAvaliableCoursesTable(){
        if(student == null){
            avaliableCourses.getItems().clear();
            avaliableCourses.setPlaceholder(new Label("You have to select a Student first"));
        }
        else if(student.getAvaliableCourses().size()==0){
            avaliableCourses.getItems().clear();
            avaliableCourses.setPlaceholder(new Label("There are no avaliable courses for this student"));
        }
        else{
            avaliableCourses.getItems().clear();
            avaliableCourses.getItems().setAll(getAvailableCoursesForStudent());
        }
    }  
            
    
    private void updateStudentDetails(){
        if(student==null){
            clearDetailFields();
            deleteStudentButton.setDisable(true);
        }
        else{
            firstNameText.setText(student.getFirstName());
            lastNameText.setText(student.getLastName());
            birthDatePicker.setValue(student.getDateOfBirth());
            tuitionFeesText.setText(String.valueOf(student.getTuitionFees()));
            deleteStudentButton.setDisable(false);
        }
    }

    /**
     * Closes the StudentGrade window
     * @param event 
     */
    @FXML
    private void goBackFromStudentGrade(MouseEvent event) {
        prepareSlideMenuAnimationForGrades();
    }

    
    /**
     * Saves the edited ore new StudentGrade
     * @param event 
     */
    @FXML
    private void saveGradeChanges(MouseEvent event) {
        boolean result; 
        if(oralMarkTextfield.getText().equals("")){
            notification.warning("Please give the Oral Mark");
        }
        else if(Integer.parseInt(oralMarkTextfield.getText()) > assignment.getOralMark()){
            notification.warning("The oral mark can't be more than "+ assignment.getOralMark() + " !");
        }
        else if(Integer.parseInt(oralMarkTextfield.getText()) < 0){
            notification.warning("The oral mark can't be negative number!");
        }
        else if(totalMarkTextfield.getText().equals("")){
            notification.warning("Please give the Total Mark");
        }
        else if(Integer.parseInt(totalMarkTextfield.getText()) > assignment.getTotalMark()){
            notification.warning("The total mark can't be more than "+ assignment.getTotalMark() + " !");
        }
        else if(Integer.parseInt(totalMarkTextfield.getText()) < 0){
            notification.warning("The total mark can't be negative number!");
        }
        else{
            result = alert.show("Confirmation!", "Do you want to save the changes?");
            if(result){
                if(gradesForTables.getDelivered()=="YES"){
                    studentGrade = new StudentGrade(student,assignment,course,Integer.parseInt(oralMarkTextfield.getText()),Integer.parseInt(totalMarkTextfield.getText()));
                    studentGrade.update();
                    notification.success("The grade has updated successfully");
                    course=studentCourses.getSelectionModel().getSelectedItem();
                    
                    searchModeStudentGrades();
                }
                else if(gradesForTables.getDelivered()=="NO"){
                    studentGrade = new StudentGrade(student,assignment,course,Integer.parseInt(oralMarkTextfield.getText()),Integer.parseInt(totalMarkTextfield.getText()));
                    StudentGrade.saveStudentGrade(studentGrade);
                    notification.success("The grade has created successfully");
                    int intex= studentCourses.getSelectionModel().getSelectedIndex();
                    
                    searchModeStudentGrades();
                }
                int intex= studentAssignments.getSelectionModel().getSelectedIndex();
                updateStudentAssignmentsTable();
                studentAssignments.requestFocus();
                studentAssignments.getSelectionModel().select(intex);
            }
        }
    }
    
    /**
     * Sets the studentGrade window to editing mode
     */
    private void editModeStudentGrades(){
        editGrade.setDisable(true);
        editGrade.setVisible(false);
        saveGrade.setDisable(false);
        saveGrade.setVisible(true);
        cancelSaveGrade.setDisable(false);
        cancelSaveGrade.setVisible(true);
        oralMarkTextfield.setEditable(true);
        totalMarkTextfield.setEditable(true);
        goBackButton.setDisable(true);
        goBackButton.setVisible(false);
    }
    
    
    /**
     * Sets the studentGrade window to searching mode
     */
    private void searchModeStudentGrades(){
        editGrade.setDisable(false);
        editGrade.setVisible(true);
        saveGrade.setDisable(true);
        saveGrade.setVisible(false);
        cancelSaveGrade.setDisable(true);
        cancelSaveGrade.setVisible(false);
        oralMarkTextfield.setEditable(false);
        totalMarkTextfield.setEditable(false);
        goBackButton.setDisable(false);
        goBackButton.setVisible(true);
    }

    
    /**
     *  Cancels the editing mode and sets the studentGrade window to searching mode
     * @param event 
     */
    @FXML
    private void cancelSaveGradeChanges(MouseEvent event) {
        searchModeStudentGrades();
        if(gradesForTables.getDelivered()=="YES"){
            oralMarkTextfield.setText(String.valueOf(studentGrade.getOralMark()));
            totalMarkTextfield.setText(String.valueOf(studentGrade.getTotalMark()));
            notification.info("The editing progress has been canceled");
        }
        else if(gradesForTables.getDelivered()=="NO"){
            oralMarkTextfield.setText("");
            totalMarkTextfield.setText("");
            notification.info("The grade entering progress has been canceled");
        }
        
    }

    
    /**
     * Sets the StudentGrade window to editing mode
     * @param event 
     */
    @FXML
    private void editGrades(MouseEvent event) {
        editModeStudentGrades();
    }

    
    /**
     * Selects the StudentGrade and sets the TextFields values.
     * After it opens the StudentGrade's window.
     * @param event 
     */
    @FXML
    private void editTheAssignmentGrade(MouseEvent event) {
        gradesForTables = studentAssignments.getSelectionModel().getSelectedItem();
        assignment = Assignment.getAssignmentById(gradesForTables.getAssignmentId());
        if(gradesForTables.getDelivered()=="YES"){
            studentGrade = StudentGrade.getStudentGrade(student, course, assignment);
            oralMarkTextfield.setText(String.valueOf(studentGrade.getOralMark()));
            oralMarkLabel.setText("/"+assignment.getOralMark());
            totalMarkTextfield.setText(String.valueOf(studentGrade.getTotalMark()));
            totalMarkLabel.setText("/"+assignment.getTotalMark());
            titleTextField.setText(assignment.getTitle());
            descriptionTextArea.setText(assignment.getDescription());
            deadlinePicker.setValue(assignment.getSubDateTime());
            
        }
        else if(gradesForTables.getDelivered()=="NO"){
            studentGrade = StudentGrade.getStudentGrade(student, course, assignment);
            oralMarkTextfield.setText("");
            oralMarkLabel.setText("/"+assignment.getOralMark());
            totalMarkTextfield.setText("");
            totalMarkLabel.setText("/"+assignment.getTotalMark());
            titleTextField.setText(assignment.getTitle());
            descriptionTextArea.setText(assignment.getDescription());
            deadlinePicker.setValue(assignment.getSubDateTime());
        }
        prepareSlideMenuAnimationForGrades();
    }
}
