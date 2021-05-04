
package scoolwithjavafx;



import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Tseptzidis Nikos
 */
public class ScoolWithJavaFX extends Application {
    
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
        
    }
    
    @Override
    public void start(Stage stage){
        
        FxmlLoader loader = new FxmlLoader();
        
        Parent root = loader.getPane("FXMLDocument");
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("Style.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.show();
    }
}
