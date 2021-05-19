import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.image.BufferedImage;

public class Asciiart
{
    private final String gscale = "`~!^()-_+=;:'\",.\\/|<>[]{";
    private final BufferedImage img;
    private int tileHeight;
    private int tileWidth;
    private final int height;
    private final int width;
    private int columns;
    private int rows;

    public Asciiart(BufferedImage img, int columns, double scale)
    {
        this.img = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
        try
        {
            if(columns > this.width || columns <= 0)
            {
                throw new SizeException("Columns value not valid");
            }
            this.tileWidth = this.width/columns;
            this.columns = columns;
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
            if(e.getMessage().equals("Columns value not valid"))
            {
                System.out.println("Conversion will still be executed with the standard values: 120 cols and 0.43 scale");
                this.tileWidth = this.width/120;
                this.columns = columns;
            }
            if(e.getMessage().equals("Scale value not valid"))
            {
                System.out.println("Conversion will still be executed with the standard values: 120 cols and 0.43 scale");
                this.tileHeight = (int)(this.tileWidth/0.43);
                this.rows = this.height/this.tileHeight;
            }
        }
    }

    /**
     * The general idea behind the render is taking N tiles
     * to calculate the average luminosity of the elements
     *
     * @return Image in ASCII code
     */
    public String render() {
        int avg;
        Color temp;
        String img = "";
        int y0, y1, x0, x1 = 0;
        LinkedList<Integer> computingAvg;
        for (int i = 0; i < this.rows; i++)
        {
            for (int j = 0; j < this.columns; j++)
            {
                avg = 0;
                computingAvg = new LinkedList<>();
                y0 = i * this.tileHeight;
                y1 = (i + 1) * this.tileHeight;
                /**
                 * Last column correction
                 */
                if (i == this.rows - 1)
                {
                    y1 = this.height;
                }
                x0 = j * this.tileWidth;
                x1 = (j + 1) * this.tileWidth;
                /**
                 * Last row correction
                 */
                if (i == this.columns - 1)
                {
                    x1 = this.width;
                }
                /**
                 * Collecting grey intensity from
                 * surroundings cells of the img
                 */
                for (int k = y0; k < y1; k++)
                {
                    for (int x = x0; x < x1; x++)
                    {
                        temp = new Color(this.img.getRGB(x, k));
                        computingAvg.add(temp.getRed());
                    }
                }
                for(Integer integer : computingAvg)
                {
                    avg += integer;
                }
                avg = avg / computingAvg.size();
                img += gscale.charAt((avg*(gscale.length()-1))/255);
                if (j == this.columns - 1)
                {
                    img += "\n";
                }
            }
        }
        return img;
    }

    public static void main(String[] args) throws IOException {
        Greyscale imgRGB = new Greyscale("/resources/images/Spongebob.jpg");
        Asciiart imgGS = new Asciiart(imgRGB.render(), 330, 0.5);
        String imgASCII = imgGS.render();
        System.out.println(imgASCII);
        File ascii = new File("ascii.txt");
        if(!ascii.exists()) ascii.createNewFile();
        FileWriter img = new FileWriter("ascii.txt");
        img.write(imgASCII);
        img.close();
    }
}
