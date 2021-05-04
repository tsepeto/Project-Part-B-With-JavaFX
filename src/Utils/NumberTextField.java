
package Utils;

import javafx.scene.control.TextField;

/**
 * @author Tsepetzidis Nikos
 * A TextField class that takes only integers
 */
public class NumberTextField extends TextField{
     @Override
    public void replaceText(int i, int il, String string){
        if(string.matches("[0-9]") || string.isEmpty()){
            super.replaceText(i, il, string);
        }
    }
    
    @Override
    public void replaceSelection(String string){
        super.replaceSelection(string);
    }
}
