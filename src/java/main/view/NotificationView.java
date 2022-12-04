package main.view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationView {
    public static void showNotification(String title, String text, boolean error){
        Image img;
        if (!error) {
            img = new Image("/images/happy.png");
        } else {
            img = new Image("/images/sad.png");
        }
        ImageView iv = new ImageView(img);
        iv.setFitHeight(50);
        iv.setFitWidth(50);
        Notifications notification = Notifications.create()
                .title(title)
                .text(text)
                .graphic(iv)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT);
        notification.show();
    }
}
