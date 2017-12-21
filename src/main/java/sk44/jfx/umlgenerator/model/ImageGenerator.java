/*
 *
 *
 *
 */
package sk44.jfx.umlgenerator.model;

import java.io.File;
import java.io.IOException;
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
        try {
            SourceFileReader reader = new SourceFileReader(textFile.toFile());
            List<GeneratedImage> list = reader.getGeneratedImages();
            File png = list.get(0).getPngFile();
            return Paths.get(png.getAbsolutePath());
        } catch (InterruptedException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}