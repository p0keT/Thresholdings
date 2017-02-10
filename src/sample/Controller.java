package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML public ImageView IVimage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        RWFile rw = new RWFile();
        ImageProcessing ip = new ImageProcessing();
        BufferedImage bi = rw.readBufferedImage("subtrect.jpg");

        int[][] rgbArr2D = ip.toRGBArray(bi);
        int[] rgbArr1D = ip.dimensionConverter(rgbArr2D);

        rgbArr1D = AdaptiveThresholdings.Bradley_threshold(rgbArr1D,rgbArr2D[0].length,rgbArr2D.length, 0.75f);

        rgbArr2D = ip.dimensionConverter(rgbArr1D,rgbArr2D.length,rgbArr2D[0].length);
        bi = ip.toBufferedImage(rgbArr2D);
        IVimage.setImage(SwingFXUtils.toFXImage(bi, null));



        //перевіряю роботу деяких методів
        int[] arr1D = {1,2,3,4,5,6};
        ImageProcessing imPr = new ImageProcessing();
        int[][] arr2D = imPr.dimensionConverter(arr1D,3,2);
        for (int i = 0; i < arr2D.length; i++) {
            for (int j = 0; j < arr2D[0].length; j++) {
                System.out.print(arr2D[i][j]);
            }
            System.out.println();
        }

        int[] test = imPr.dimensionConverter(arr2D);
        for (int i = 0; i <test.length ; i++) {
            System.out.print(test[i]);
        }
    }
}
