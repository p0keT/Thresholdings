package sample;

import com.googlecode.javacv.FFmpegFrameGrabber;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML public ImageView IVimage;
    @FXML public Slider SThresh;
    @FXML public TextField TFThresh;
    private AnimationTimer timer=null;
    private float thresh = 0.943f;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //обробник подій на слайдер
        SThresh.valueProperty().addListener((observable, oldValue, newValue) -> {
            TFThresh.setText(String.valueOf( newValue.floatValue() ));
            thresh = newValue.floatValue();
        });

        SThresh.setValue(thresh);

        RWFile rw = new RWFile();
        ImageProcessing ip = new ImageProcessing();
        final BufferedImage[] bi = {rw.readBufferedImage("2.jpg")};

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("1.mp4");

        //int[][] rgbArr2D = ip.toRGBArray(bi[0]);
        //int[] rgbArr1D = ip.dimensionConverter(rgbArr2D);
        // rgbArr2D = ip.imageToGrey(rgbArr2D);
        //IVimage.setImage(SwingFXUtils.toFXImage(bi[0], null));

        try {
            grabber.start();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        final opencv_core.IplImage[] frame = new opencv_core.IplImage[1];
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    frame[0] = grabber.grab();
                    bi[0] = frame[0].getBufferedImage();
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                }

                int[][] rgbArr2D = ip.toRGBArray(bi[0]);
                //int[] rgbArr1D = ip.dimensionConverter(rgbArr2D);
                long start2 = System.currentTimeMillis();
                rgbArr2D = AdaptiveThresholdings.Bradley_threshold(rgbArr2D,  thresh);
                long finish2 = System.currentTimeMillis();
                System.out.println("Image thresh: " + (finish2 - start2) + " ms");
                //rgbArr2D = ip.dimensionConverter(rgbArr1D, rgbArr2D.length, rgbArr2D[0].length);
                rgbArr2D = ip.scaleSmaller(4,rgbArr2D);
                bi[0] = ip.toBufferedImage(rgbArr2D);
                IVimage.setImage(SwingFXUtils.toFXImage(bi[0], null));

            }
        };
        timer.start();

        /*
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
        System.out.println();

        int[] test = imPr.dimensionConverter(arr2D);
        for (int i = 0; i <test.length ; i++) {
            System.out.print(test[i]);
        }*/
    }
}
