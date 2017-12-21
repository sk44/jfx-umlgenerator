package sk44.jfx.umlgenerator.controller;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sk44.jfx.umlgenerator.model.GeneratorService;
import sk44.jfx.umlgenerator.model.ImageGenerator;

public class MainWindowController implements Initializable {

    @FXML
    private TextField pathBar;
    @FXML
    private Button generateButton;
    @FXML
    private ImageView preview;
    @FXML
    private ProgressBar progress;
    @FXML
    private Label messageLabel;

    private final GeneratorService service = new GeneratorService(new ImageGenerator());

    @FXML
    public void handleBrowseAction(ActionEvent e) {
        // TODO open file chooser
    }

    @FXML
    public void handleGenerateAction(ActionEvent e) {
        Path text = Paths.get(pathBar.getText());
        service.execute(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        generateButton.disableProperty().bind(service.runningProperty());
        progress.visibleProperty().bind(service.runningProperty());
        progress.setProgress(-1);
        service.setOnSucceeded(e -> {
            updateImage(service.getValue());
        });
        messageLabel.textProperty().bind(service.messageProperty());
    }

    private void updateImage(Path source) {
        try {
            Image image = new Image(Files.newInputStream(source));
            preview.setImage(image);
            preview.setFitWidth(image.getWidth());
            preview.setFitHeight(image.getHeight());
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

    }
}
