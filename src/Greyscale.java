import java.awt.Color;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Greyscale {
    private final int contrast = 255;
    private BufferedImage img;
    private final int height;
    private final int width;

    public Greyscale(String path) throws IOException {
        this.img = ImageIO.read(getClass().getResource(path));
        this.height = img.getHeight();
        this.width = img.getWidth();
    }

    public BufferedImage render() {
        Color color;
        double factor = (259 * (this.contrast + 255)) / (255 * (259 - this.contrast)); //TODO: Make a slider on this in the UI
        for(int i=0; i<this.width; i++)
        {
            for(int j=0; j<this.height; j++)
            {
                color = new Color(this.img.getRGB(i, j));

                /**
                 * Credits: https://www.dfstudios.co.uk/articles/programming/image-programming-algorithms/image-processing-algorithms-part-5-contrast-adjustment/
                 *
                 * The fist step in changing the contrast of an image is to calculate the contrast correction factor which
                 * is given by the following formula: F = (259 * (C + 255)) / (255 * (259 - C)) // C stands for contrast
                 * In second place we have to do the actual adjustment by following next formula: R' = F * (R - 128) + 128
                 */
                int red = (int)(factor * (color.getRed() - 128) + 128);
                red = red < 0 ? 0 : red > 255 ? 255 : red;
                int green = (int)(factor * (color.getGreen() - 128) + 128);
                green = green < 0 ? 0 : green > 255 ? 255 : green;
                int blue = (int)(factor * (color.getBlue() - 128) + 128);
                blue = blue < 0 ? 0 : blue > 255 ? 255 : blue;

                /**
                 * Credits: https://entropymine.com/imageworsener/grayscale/
                 *
                 * Now we can choose between three ways to get the grey (from the worst to the best approximation):
                 * 1-Add red, blue and green and divide byu three
                 * 2&3-Add the three colors but multiplying them for 3 constants less or better approximated
                 * 4-Too long to describe but the concept is better approximation better result (but also less performances)
                 */
                red = (int)(red * 0.2126);
                green = (int)(green * 0.7152);
                blue = (int)(blue * 0.0722);
                color = new Color(red+green+blue, red+green+blue, red+green+blue);
                this.img.setRGB(i, j, color.getRGB());
            }
        }
        return this.img;
    }
}
