import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Asciiart {
    private final String gscale = "`~!^()-_+=;:'\",.\\/|<>[]{";
    private final BufferedImage img;
    private final int height;
    private final int width;

    public Asciiart(BufferedImage img) {
        this.img = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    /**
     * The general idea behind the render is iterating over
     * the image and calculating the luminosity of every pixel
     * and skipping half of the lines in the height to correct
     * the shaped of the image that is distorted by the height
     * of the characters
     *
     * @return Image in ASCII code
     */
    public String render() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<this.height-1; i+=2) {
            for(int j=0; j<this.width-1; j++) {
                stringBuilder.append(gscale.charAt((new Color(this.img.getRGB(j, i)).getRed()*(gscale.length()-1))/255));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws IOException {
        Greyscale imgGS = new Greyscale("/resources/images/Monnalisa.jpg");
        Asciiart imgASCII = new Asciiart(imgGS.render());
        String text = imgASCII.render();
        System.out.println(text);
        File ascii = new File("ascii.txt");
        if(!ascii.exists()) ascii.createNewFile();
        FileWriter img = new FileWriter("ascii.txt");
        img.write(text);
        img.close();
    }
}
