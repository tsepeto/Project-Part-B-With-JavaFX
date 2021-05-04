
package Utils;

import javafx.scene.control.TextField;

/**
 *
 * @author Tsepetzidis Nikos
 * 
 * A TextField that takes only integer or double number and saves it as a double
 */
public class DoubleTextField extends TextField{
    
    @Override
    public void replaceText(int i, int il, String string){
        if(string.matches("\\d*(\\.\\d*)?") || string.isEmpty() ){
            super.replaceText(i, il, string);
        }
    }
    
    @Override
    public void replaceSelection(String string){
        super.replaceSelection(string);
    }
    
}
