/*
 *
 *
 *
 */
package sk44.jfx.umlgenerator.model;

import java.nio.file.Path;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author sk
 */
public class GeneratorService extends Service<Path> {

    private final ImageGenerator generator;
    private Path sourceText;

    public GeneratorService(ImageGenerator generator) {
        this.generator = generator;
    }

    public void execute(Path sourceText) {
        // TODO パラメーターの渡し方はこれでいいのか
        this.sourceText = sourceText;
        restart();
    }

    @Override
    protected Task<Path> createTask() {
        GeneratorTask task = new GeneratorTask(sourceText, generator);
        return task;
    }

}
