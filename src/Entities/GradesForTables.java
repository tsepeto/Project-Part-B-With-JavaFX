
package Entities;

import java.time.LocalDate;

/**
 *
 * @author Tsepetzidis Nikos
 * 
 * GradesForTables created to help us insert result into assignments table
 * in StudentPane
 */
public class GradesForTables {
    private int assignmentId;
    private String title;
    private String description;
    private LocalDate deadline;
    private int assOralMark;
    private int assTotalMark;
    private int userOralMark;
    private int userTotalMark;
    private String delivered;
    private String oralMark;
    private String totalMark;
    
    /**
     * If that constructor is called then we know that the assignment is delivered
     * @param assignmentId
     * @param title
     * @param description
     * @param deadline
     * @param assOralMark
     * @param assTotalMark
     * @param userOralMark
     * @param userTotalMark 
     */
    public GradesForTables(int assignmentId,String title, String description,LocalDate deadline, 
                            int assOralMark,int assTotalMark, int userOralMark, int userTotalMark){
        this.assignmentId =assignmentId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.assOralMark = assOralMark;
        this.assTotalMark = assTotalMark;
        this.userOralMark = userOralMark;
        this.userTotalMark = userTotalMark;
        this.delivered = "YES";
        this.oralMark = userOralMark+"/"+assOralMark;
        this.totalMark = userTotalMark+"/"+assTotalMark;
        
    }
    
    /**
     * if that constructor is called then we know that the assignment is not delivered
     * @param assignmentId
     * @param title
     * @param description
     * @param deadline
     * @param assOralMark
     * @param assTotalMark 
     */
    public GradesForTables(int assignmentId,String title, String description,LocalDate deadline, 
                            int assOralMark,int assTotalMark){
        this.assignmentId =assignmentId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.assOralMark = assOralMark;
        this.assTotalMark = assTotalMark;
        this.delivered = "NO";
        this.oralMark = "- /"+assOralMark;
        this.totalMark = "- /"+assTotalMark;
        
    }

//  Getters and setters
    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getAssOralMark() {
        return assOralMark;
    }

    public void setAssOralMark(int assOralMark) {
        this.assOralMark = assOralMark;
    }

    public int getAssTotalMark() {
        return assTotalMark;
    }

    public void setAssTotalMark(int assTotalMark) {
        this.assTotalMark = assTotalMark;
    }

    public int getUserOralMark() {
        return userOralMark;
    }

    public void setUserOralMark(int userOralMark) {
        this.userOralMark = userOralMark;
    }

    public int getUserTotalMark() {
        return userTotalMark;
    }

    public void setUserTotalMark(int userTotalMark) {
        this.userTotalMark = userTotalMark;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getOralMark() {
        return oralMark;
    }

    public void setOralMark(String oralMark) {
        this.oralMark = oralMark;
    }

    public String getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(String totalMark) {
        this.totalMark = totalMark;
    }
    
    
}
