
package Utils;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import javafx.scene.control.TableCell;

import Entities.dao.GenericDao;

/**
 *
 * @author Tsepetzidis Nikos
 * 
 * A TableColumn that formats the LocalDate with the costume DateTimeFormatter
 */
public class MyTableColumn<Obj , LocalDate>{
    
    DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern(GenericDao.daTiFormat);
    
    public TableCell<Obj, LocalDate> tableConverter(){
       return new TableCell<Obj, LocalDate>() {
        @Override
        protected void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
            } else {
                // Format date.
                setText(myDateFormatter.format((TemporalAccessor) item));
            }
        }
    };

    }
    
    
}
