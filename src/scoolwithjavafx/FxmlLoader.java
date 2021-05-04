
package scoolwithjavafx;





import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 *
 * @author Tsepetzidis Nikos
 * 
 * Helps the program to show another Pane
 */
public class FxmlLoader {
    private Pane view;
    
    
    public Pane getPane(String fileName){
    
        try{
            URL fileUrl = ScoolWithJavaFX.class.getResource("/scoolwithjavafx/"+fileName+".fxml");
            
            if(fileUrl == null){
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }
            view = new FXMLLoader().load(fileUrl);
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("No page " + fileName +" please check FxmlLoader.");
            URL fileUrl = ScoolWithJavaFX.class.getResource("/scoolwithjavafx/PropertiesPane.fxml");
            try {
                view = new FXMLLoader().load(fileUrl);
            } catch (IOException ex) {
                System.out.println("Something went wrong!");
            }
        }
        return view;
    }
}
