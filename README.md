# ImgToAsciiArt

Just another of the many programs that convert an image into an ascii image.

## Usage

First, load the image you want to convert into the path: 
```
.../resources/images
```
Second, edit the instruction in the main (in Asciiart.java) follow the instructions in the code below

```java
public static void main(String[] args) throws IOException 
{
    /*
     * Change the path with the desired image to convert
     * Change the values if you want a better result
     * If the values would be not valid the program
     * will still run with defaul values: 120 columns, 0.43 scale
     */
    Greyscale imgRGB = new Greyscale("/resources/images/Lenna.png");
    Asciiart imgGS = new Asciiart(imgRGB.render(), 256, 0.5);
    String imgASCII = imgGS.render();
    System.out.println(imgASCII);
    /*
     * In case you just want to admire the result from
     * the console just comment the following lines
     */
    File ascii = new File("ascii.txt");
    if(!ascii.exists()) ascii.createNewFile();
    FileWriter img = new FileWriter("ascii.txt");
    img.write(imgASCII);
    img.close();
}
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate and don't lame me, I'm not a senior developer with 10 years of experience with Java 

# Few examples
NOTE: All the ascii results were edited on Notepad++

# Source Image
![Lenna](https://user-images.githubusercontent.com/31989626/118847504-1630c200-b8ce-11eb-955a-4260bc221f80.png)

# Final Result
![ascii](https://user-images.githubusercontent.com/31989626/118869430-afb79e00-b8e5-11eb-93da-7fc7ae30bc31.PNG)

# Source Image
![Monnalisa](https://user-images.githubusercontent.com/31989626/118868626-b1cd2d00-b8e4-11eb-90ed-0aac29d70b42.jpg)

# Final Result
![ascii2](https://user-images.githubusercontent.com/31989626/118870073-73387200-b8e6-11eb-8ebd-880d3115a880.PNG)

# Source Image
![Spongebob](https://user-images.githubusercontent.com/31989626/118869525-c8c04f00-b8e5-11eb-88d5-9feeac4ff336.jpg)

# Final Result
![ascii3](https://user-images.githubusercontent.com/31989626/118869862-340a2100-b8e6-11eb-9e74-e2e5967bd83d.PNG)

