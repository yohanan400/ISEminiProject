package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import java.util.List;

/**
 * Testing ImageWriter Class
 *
 * @author Aviel buta and Yakir Yohanan
 */
public class ImageWriterTests {

    @Test
    public void writeImageTest() {
        ImageWriter image1 = new ImageWriter("Test_image", 800, 500);

        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                // nX/16 = 50; nY/10 = 50
                if ((i % 50 == 0) || (j % 50 == 0))
                    image1.writePixel(i, j, Color.BLACK);
                else
                    image1.writePixel(i, j, new Color(192, 192, 192));
            }
        }
        image1.writeToImage();
    }
}
