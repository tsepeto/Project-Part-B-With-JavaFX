
package Utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

/**
 *
 * @author Tsepetzidis Nikos
 * 
 * A window that ask the user to confirm on change. Returns true or false.
 */
public class YesOrNoWindow {
    
    public  boolean show(String title, String message){
        boolean returnResult = false;
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.WARNING, message, yes, no);
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialogPane = alert.getDialogPane();
        
        dialogPane.getStylesheets().add(
        getClass().getResource("Style.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
                
        

        alert.setTitle("string");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            returnResult = true;
        }
        return returnResult;
    }
}
