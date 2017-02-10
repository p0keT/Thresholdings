package sample;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int[] arr = {1,2,3,4,5,6};
        ImageProcessing imPr = new ImageProcessing();
        int[][] arr2 = imPr.dimensionConverter(arr,3,2);
        for (int i = 0; i < arr2.length; i++) {
            for (int j = 0; j < arr2[0].length; j++) {
                System.out.print(arr2[i][j]);
            }
            System.out.println();
        }
    }
}
