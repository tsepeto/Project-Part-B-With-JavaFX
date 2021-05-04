
package Utils;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Tsepetzidis Nikos
 * 
 * A Notification class that pops up messages in program
 */
public class MyNotification {
    
    private String title = "sCOOL!!!";
    private TrayNotification tray = new TrayNotification();
    private AnimationType popUpType = AnimationType.POPUP;

    public MyNotification() {
    }

    /**
     * Creates a warning notification with the given message
     * @param message 
     */
    public void warning(String message){
        this.tray.setAnimationType(popUpType);
        this.tray.setTitle(title);
        this.tray.setMessage(message);
        this.tray.setNotificationType(NotificationType.ERROR);
        this.tray.showAndDismiss(Duration.millis(3000));
        
    }
    
    /**
     * Creates a success notification with the given message
     * @param message 
     */
    public void success(String message){
        this.tray.setAnimationType(popUpType);
        this.tray.setTitle(title);
        this.tray.setMessage(message);
        this.tray.setNotificationType(NotificationType.SUCCESS);
        this.tray.showAndDismiss(Duration.millis(3000));
    }
    
    
    /**
     * Creates an information notification with the given message
     * @param message 
     */
    public void info(String message){
        this.tray.setAnimationType(popUpType);
        this.tray.setTitle(title);
        this.tray.setMessage(message);
        this.tray.setNotificationType(NotificationType.INFORMATION);
        this.tray.showAndDismiss(Duration.millis(3000));
    }
    
    
    /**
     * Getters and Setters
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TrayNotification getTray() {
        return tray;
    }

    public void setTray(TrayNotification tray) {
        this.tray = tray;
    }

    public AnimationType getPopUpType() {
        return popUpType;
    }

    public void setPopUpType(AnimationType type) {
        this.popUpType = type;
    }
    
    
}
