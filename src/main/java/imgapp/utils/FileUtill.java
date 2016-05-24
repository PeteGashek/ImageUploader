package imgapp.utils;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ghost on 3/14/16.
 */
public final class FileUtill {

    private FileUtill() {
    }

    private static final Logger logger = Logger.getLogger(FileUtill.class);

    public static BufferedImage resizeImage(BufferedImage originalImage, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();
        return resizedImage;
    }

    public static void writeImageToFileSystem(BufferedImage image, String directory, String extensionRemoved, String subfolder) {
        try {
            Path dirHierarchy = Paths.get(directory + '\\' + subfolder + '\\');

            if (Files.exists(dirHierarchy)) {
                ImageIO.write(resizeImage(image, 100, 100), "jpg", new File(directory + '\\' + subfolder + '\\' + extensionRemoved + "_small.jpg"));
                ImageIO.write(resizeImage(image, 200, 200), "jpg", new File(directory + '\\' + subfolder + '\\' + extensionRemoved + "_mid.jpg"));
                ImageIO.write(resizeImage(image, 300, 300), "jpg", new File(directory + '\\' + subfolder + '\\' + extensionRemoved + "_big.jpg"));
            } else {
                new File(directory + "/" + subfolder + "/").mkdirs();
                ImageIO.write(resizeImage(image, 100, 100), "jpg", new File(directory + '\\' + subfolder + '\\' + extensionRemoved + "_small.jpg"));
                ImageIO.write(resizeImage(image, 200, 200), "jpg", new File(directory + '\\' + subfolder + '\\' + extensionRemoved + "_mid.jpg"));
                ImageIO.write(resizeImage(image, 300, 300), "jpg", new File(directory + '\\' + subfolder + '\\' + extensionRemoved + "_big.jpg"));
            }
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
    }
}
