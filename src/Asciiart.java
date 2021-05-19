import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Asciiart
{
    private String gscale = "`~!^()-_+=;:'\",.\\/|<>[]{";
    private BufferedImage img;
    private int tileHeight;
    private int tileWidth;
    private int height;
    private int width;
    private int collumns;
    private int rows;

    public Asciiart(BufferedImage img, int collumns, double scale)
    {
        this.img = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
        try
        {
            if(collumns > this.width || collumns <= 0)
            {
                throw new SizeException("Collumns value not valid");
            }
            this.tileWidth = this.width/collumns;
            this.collumns = collumns;
            if(scale > 1 || scale <= 0)
            {
                throw new SizeException("Scale value not valid");
            }
            this.tileHeight = (int)(this.tileWidth/scale);
            this.rows = this.height/this.tileHeight;
        }
        catch(SizeException e)
        {
            System.out.println(e.getMessage());
            if(e.getMessage().equals("Collumns value not valid"))
            {
                System.out.println("Conversion will still be executed with the standard values: 120 cols and 0.43 scale");
                this.tileWidth = this.width/120;
                this.collumns = collumns;
            }
            if(e.getMessage().equals("Scale value not valid"))
            {
                System.out.println("Conversion will still be executed with the standard values: 120 cols and 0.43 scale");
                this.tileHeight = (int)(this.tileWidth/0.43);
                this.rows = this.height/this.tileHeight;
            }
        }
    }

    public String render() {
        int avg;
        Color temp;
        String img = "";
        int y0, y1, x0, x1 = 0;
        LinkedList<Integer> computingAvg;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.collumns; j++) {
                avg = 0;
                computingAvg = new LinkedList<>();
                y0 = i * this.tileHeight;
                y1 = (i + 1) * this.tileHeight;
                /**
                 * Last collumn correction
                 */
                if (i == this.rows - 1) {
                    y1 = this.height;
                }
                x0 = j * this.tileWidth;
                x1 = (j + 1) * this.tileWidth;
                /**
                 * Last row correction
                 */
                if (i == this.collumns - 1) {
                    x1 = this.width;
                }
                /**
                 * Colletting grey intesity from
                 * surroundings cells of the img
                 */
                for (int k = y0; k < y1; k++) {
                    for (int x = x0; x < x1; x++) {
                        temp = new Color(this.img.getRGB(x, k));
                        computingAvg.add(temp.getRed());
                    }
                }
                for (int k = 0; k < computingAvg.size(); k++) {
                    avg += computingAvg.get(k);
                }
                avg = avg / computingAvg.size();
                img += this.gscale.charAt((avg*(this.gscale.length()-1))/255);
                if (j == this.collumns - 1) {
                    img += "\n";
                }
            }
        }
        return img;
    }

    public static void main(String[] args) throws IOException {
        Greyscale imgRGB = new Greyscale("/resources/images/sb.jpg");
        Asciiart imgGS = new Asciiart(imgRGB.render(), 200, 0.5);
        String imgASCII = imgGS.render();
        System.out.println(imgASCII);
        File ascii = new File("ascii.txt");
        ascii.createNewFile();
        FileWriter img = new FileWriter("ascii.txt");
        img.write(imgASCII);
        img.close();
    }
}
