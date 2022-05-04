import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AsciiArt {
    private String directory;
    private BufferedImage image;
    private final char[] bscale = {' ','.','°','*','o','O','#','@'};

    public AsciiArt(String directory) {
        this.directory = directory;
    }

    private void openImage() {
        try {
            this.image = ImageIO.read(new File(this.directory));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toGreyscale() {

        final double contrast = 255;

        double factor = (259 * (contrast + 255)) / (255 * (259 - contrast));

        for (int i=0; i<this.image.getHeight(); i++) {
           for (int j=0; j<this.image.getWidth(); j++) {

               Color color = new Color(this.image.getRGB(j, i));

               int red = (int)(factor * (color.getRed() - 128) + 128);
               red = red < 0 ? 0 : Math.min(red, 255);
               int green = (int)(factor * (color.getGreen() - 128) + 128);
               green = green < 0 ? 0 : Math.min(green, 255);
               int blue = (int)(factor * (color.getBlue() - 128) + 128);
               blue = blue < 0 ? 0 : Math.min(blue, 255);

               red = (int)(red * 0.2126);
               green = (int)(green * 0.7152);
               blue = (int)(blue * 0.0722);

               int grey = red + green + blue;

               this.image.setRGB(j, i, new Color(grey, grey, grey).getRGB());
           }
        }
    }

    public String render() {
        openImage();
        toGreyscale();

        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<this.image.getHeight(); i+=8) {
            for(int j=0; j<this.image.getWidth()-1; j+=8) {
                stringBuilder.append(this.bscale[(new Color(this.image.getRGB(j, i)).getRed()*(this.bscale.length-1))/255]);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void writeFile(String s) {
        try {
            FileWriter myWriter = new FileWriter("art.txt");
            myWriter.write(s);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        AsciiArt asciiArt = new AsciiArt(args[0]);
        AsciiArt.writeFile(asciiArt.render());
    }
}
