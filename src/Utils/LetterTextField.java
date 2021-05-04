
package Utils;

import javafx.scene.control.TextField;

/**
 *
 * @author Tsepetzidis Nikos
 * 
 * A TextField that takes only letters and spaces
 */
public class LetterTextField extends TextField{
    @Override
    public void replaceText(int i, int il, String string){
        if(string.matches("^[ A-Za-z]+$") || string.isEmpty()){
            super.replaceText(i, il, string);
        }
    }
    
    @Override
    public void replaceSelection(String string){
        super.replaceSelection(string);
    }
}
