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

    public GeneratorTask(Path sourceText, ImageGenerator generator) {
        this.sourceText = sourceText;
        this.generator = generator;
    }

    @Override
    protected Path call() throws Exception {
        return generator.generateFrom(sourceText);
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessage("Error: " + getException().getMessage());
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        updateMessage("Ready.");
    }

    @Override
    protected void running() {
        super.running();
        updateMessage("Processing...");
    }

}
