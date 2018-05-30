/*
 *
 *
 *
 */
package sk44.jfx.umlgenerator.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;

/**
 *
 * @author sk
 */
public class ImageGenerator {

    public Path generateFrom(Path textFile) {
        if (Files.exists(textFile) == false) {
            throw new IllegalArgumentException(textFile.toString() + " does not exists.");
        }
        try {
            SourceFileReader reader = new SourceFileReader(textFile.toFile(), textFile.getParent().toFile(), "UTF-8");
            List<GeneratedImage> list = reader.getGeneratedImages();
            File png = list.get(0).getPngFile();
            return Paths.get(png.getAbsolutePath());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
