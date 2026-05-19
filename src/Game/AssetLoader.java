package Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class AssetLoader {
    private static final String TexPath = "/";

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

