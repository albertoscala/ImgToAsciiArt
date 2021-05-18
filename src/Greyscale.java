import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Greyscale
{
    private BufferedImage img;
    private int height;
    private int width;

    public Greyscale(String path) throws IOException {
        this.img = ImageIO.read(getClass().getResource(path));
        this.height = img.getHeight();
        this.width = img.getWidth();
    }

    public BufferedImage render()
    {
        Color color;
        for(int i=0; i<this.width; i++)
        {
            for(int j=0; j<this.height; j++)
            {
                color = new Color(this.img.getRGB(i, j));
                /**
                 * Credits: https://entropymine.com/imageworsener/grayscale/
                 *
                 * Now we can choose between three ways to get the grey (from the worst to the best approximation):
                 * 1-Add red, blue and green and divide byu three
                 * 2&3-Add the three colors but multiplying them for 3 constants less or better approximated
                 * 4-Too long to describe but the concept is better approximation better result (but also less performances)
                 */
                int red = (int)(color.getRed() * 0.299);
                int blue = (int)(color.getBlue() * 0.587);
                int green = (int)(color.getGreen() * 0.114);
                color = new Color(red+green+blue, red+green+blue, red+green+blue);
                this.img.setRGB(i, j, color.getRGB());
            }
        }
        return this.img;
    }
}
