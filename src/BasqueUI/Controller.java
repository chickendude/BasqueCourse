package BasqueUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label label;
    public Label sentenceLabel;

    public void displayAbout(ActionEvent actionEvent) {
        System.out.print("about!");
        sentenceLabel.setText("About!");
    }
}
