package sk44.jfx.umlgenerator.controller;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import sk44.jfx.umlgenerator.model.GeneratorService;
import sk44.jfx.umlgenerator.model.ImageGenerator;

public class MainWindowController implements Initializable {

    @FXML
    private TextField pathBar;
    @FXML
    private Button generateButton;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Pane imageHolder;
    @FXML
    private ImageView preview;
    @FXML
    private ProgressBar progress;
    @FXML
    private Label messageLabel;

    private final BooleanProperty runningProperty = new SimpleBooleanProperty();

    private FileChooser fileChooser;
    private final GeneratorService service = new GeneratorService(new ImageGenerator());

    @FXML
    public void handleBrowseAction(ActionEvent e) {
        Path initialDir = Paths.get(this.pathBar.getText()).getParent();
        if (initialDir != null && Files.exists(initialDir)) {
            fileChooser.setInitialDirectory(initialDir.toFile());
        }

        File selected = fileChooser.showOpenDialog(this.pathBar.getScene().getWindow());
        if (selected != null) {
            pathBar.setText(selected.getAbsolutePath());
        }
    }

    @FXML
    public void handleGenerateAction(ActionEvent e) {
        generate();
    }

    @FXML
    public void handleEnterOnPathBar(ActionEvent e) {
        generate();
    }

    @FXML
    public void handleKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case R:
            case F5:
                generate();
                break;
            default:
                // no-op
                break;
        }
    }

    private void generate() {
        if (runningProperty.get()) {
            return;
        }
        Path text = Paths.get(pathBar.getText());
        service.execute(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        runningProperty.bind(service.runningProperty());
        generateButton.disableProperty().bind(service.runningProperty());
        progress.visibleProperty().bind(service.runningProperty());
        progress.setProgress(-1);
        service.setOnSucceeded(e -> {
            updateImage(service.getValue());
        });
        messageLabel.textProperty().bind(service.messageProperty());

        imageHolder.minWidthProperty().bind(Bindings.createDoubleBinding(() -> scroll.getViewportBounds().getWidth(), scroll.viewportBoundsProperty()));
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("plantuml file", "*.puml"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("all file", "*.*"));
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
