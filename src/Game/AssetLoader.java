package Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Handles loading image assets from the resources folder.
 * If a texture fails to load, the caller gets null and should fall back to drawing plain shapes.
 */
public class AssetLoader {
    private static final String TexPath = "/";

    /**
     * Loads an image by filename from the classpath.
     *
     * @param filename  name of the image file, e.g. "player.png"
     * @return the loaded image, or null on failure
     */
    public static BufferedImage load(String filename) {
        String path = TexPath + filename;
        try {
            InputStream IS = AssetLoader.class.getResourceAsStream(path);

            if (IS == null) {
                System.out.println("Texture not found: " + path + " -> falling back to default colours.");
                return null;
            }
            return ImageIO.read(IS);

        } catch (Exception e) {
            System.out.println("Could not load texture: " + e.getMessage());
            return null;
        }
    }
}

