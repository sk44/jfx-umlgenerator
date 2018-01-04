/*
 *
 *
 *
 */
package sk44.jfx.umlgenerator.model;

import java.nio.file.Path;
import javafx.concurrent.Task;

/**
 *
 * @author sk
 */
class GeneratorTask extends Task<Path> {

    private final Path sourceText;
    private final ImageGenerator generator;
    private Path generatedImage;

    public GeneratorTask(Path sourceText, ImageGenerator generator) {
        this.sourceText = sourceText;
        this.generator = generator;
    }

    @Override
    protected Path call() throws Exception {
        generatedImage = generator.generateFrom(sourceText);
        return generatedImage;
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessage("Error: " + getException().getMessage());
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        // ないはず
        if (generatedImage == null) {
            updateMessage("No image found.");
            return;
        }
        updateMessage("Generated: " + generatedImage.toString());
    }

    @Override
    protected void running() {
        super.running();
        updateMessage("Processing...");
    }

}
