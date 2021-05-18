import java.io.IOException;

public class Prova
{
    public static void main(String[] args) throws IOException {
        Greyscale imgRGB = new Greyscale("/resources/images/prova.jpg");
        Asciiart imgGS = new Asciiart(imgRGB.render(), 120, 0.43);
        String imgASCII = imgGS.render();
        System.out.println(imgASCII);
    }
}
