package sample;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
