package Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Handles loading image assets from the resources folder.
 * If a texture fails to load, the caller gets null and should fall back to drawing plain shapes.
 *
 * @author Patrik Říha
 */
public class AssetLoader {
    private static final String TEX_PATH = "/";

    /**
     * Loads an image by filename from the classpath.
     *
     * @param filename  name of the image file, e.g. "player.png"
     * @return the loaded image, or null on failure
     */
    public static BufferedImage load(String filename) {
        String path = TEX_PATH + filename;
        try {
            InputStream inputStream = AssetLoader.class.getResourceAsStream(path);

            if (inputStream == null) {
                System.out.println("Texture not found: " + path + " -> falling back to default colours.");
                return null;
            }
            return ImageIO.read(inputStream);

        } catch (Exception e) {
            System.out.println("Could not load texture: " + e.getMessage());
            return null;
        }
    }

    /**
     * Loads a horizontal sprite sheet and slices it into individual frames.
     * Assumes all frames are equal width and the sheet is a single row.
     * Returns an empty array if the image fails to load.
     *
     * @param filename name of the sprite sheet file, e.g. "Run.png"
     * @param frameCount number of frames in the strip
     * @return array of individual frame images, or an empty array on failure
     */
    public static BufferedImage[] loadSpriteSheet(String filename, int frameCount) {
        BufferedImage sheet = load(filename);
        if (sheet == null) return new BufferedImage[0];

        int frameWidth = sheet.getWidth() / frameCount;
        int frameHeight = sheet.getHeight();
        BufferedImage[] frames = new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            frames[i] = sheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }

        return frames;
    }
}

