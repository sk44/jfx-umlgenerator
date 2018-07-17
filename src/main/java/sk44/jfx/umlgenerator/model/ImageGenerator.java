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
import org.mozilla.universalchardet.UniversalDetector;

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
            String encoding = getEncodingFrom(textFile);
            SourceFileReader reader = new SourceFileReader(textFile.toFile(), textFile.getParent().toFile(), encoding);
            List<GeneratedImage> list = reader.getGeneratedImages();
            File png = list.get(0).getPngFile();
            return Paths.get(png.getAbsolutePath());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getEncodingFrom(Path textFile) throws IOException {

        UniversalDetector detector = new UniversalDetector(null);
        byte[] data = Files.readAllBytes(textFile);
        detector.handleData(data, 0, data.length);
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        return encoding;
    }

}
