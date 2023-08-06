package renderer;

import primitives.*;
import org.junit.jupiter.api.Test;

class ImageWriterTest {
    /**
     * test method of imageWriter to write pixels to image.
     * {@link ImageWriter#writeToImage()}
     */
    @Test
    void writeToImage() {
        Color color1 = new Color(3, 4, 300);
        Color color2 = new Color(100, 500, 100);
        ImageWriter img = new ImageWriter("23", 800, 500);
        for (int i = 0; i < 500; i++)
            for (int j = 0; j < 800 - 1; j++)
                    img.writePixel(j, i, i % 50 == 0 || j % 50 == 0 ? color1 : color2);
        img.writeToImage();
    }

}