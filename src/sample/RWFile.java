package sample;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sergiy on 2/10/2017.
 */
public class RWFile {
    /**
     * Read buffered image from input location.
     * @param fName image location path
     */
    //TODO add format of fName
    public BufferedImage readBufferedImage(String fName){
        File imageFile = new File(fName);

        BufferedImage bImage = null;
        try {
            bImage = ImageIO.read(imageFile);
            return bImage;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("-- CAN'T READ FILE --");
        }
        return null;
    }

    /**
     *
     * @param bufImg BufferedImage to be stored
     * @param fName name of file
     * @param format file format without dot (.) before format name
     */
    public void writeBufferedImage(BufferedImage bufImg, String fName, String format){
        File outputfile = new File(fName);
        try {
            ImageIO.write(bufImg, format, outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





