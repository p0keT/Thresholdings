package sample;

import java.awt.image.BufferedImage;

/**
 * Created by Sergiy on 2/10/2017.
 */
public class ImageProcessing {

    public int[][] toIntegralImage(int[][] srcArr){

        int [][]integral_image;
        int sum;

        integral_image = new int[srcArr.length][srcArr[0].length];

        for (int i = 0; i < srcArr.length; i++) {
            sum = 0;
            for (int j = 0; j < srcArr[0].length; j++) {
                sum += srcArr[i][j];
                if (i==0)
                    integral_image[i][j] = sum;
                else
                    integral_image[i][j] = integral_image[i-1][j] + sum;
            }
        }
        return integral_image;
    }

    /**
     * Converts grayscale image to integral image
     * @param src gray scale (1 dimension) image array
     * @param h height of image (number of rows)
     * @param w width of image (number of columns)
     * @return integral image (1 dimension)
     */
    public int[] toIntegralImage(int[] src, int h, int w){

        int []integral_image;
        int sum;
        int index;

        integral_image = new int[w*h];

        for (int i = 0; i < w; i++) {
            sum = 0;
            for (int j = 0; j < h; j++) {
                index = j * w + i;
                sum += src[index];
                if (i==0)
                    integral_image[index] = sum;
                else
                    integral_image[index] = integral_image[index-1] + sum;
            }
        }
        return integral_image;
    }

    /**
     * Converts 2 dimension image array to 1 dimension
     * @param srcImgArr 2 dimension image array
     * @return 1 dimension
     */
    public int[]  dimensionConverter(int[][] srcImgArr){
        int[] result = new int[srcImgArr.length*srcImgArr[0].length];
        for (int i = 0; i < srcImgArr.length; i++) {
            for (int j = 0; j <srcImgArr[i].length ; j++) {
                result[srcImgArr[0].length*i+j]=srcImgArr[i][j];
            }
        }
        return result;
    }

    /**
     * Converts 1 dimension image array to 2 dimension
     * @param srcImgArr 1 dimension image array
     * @param h height of image (number of rows)
     * @param w width of image (number of columns)
     * @return 2 dimension
     */
    public int[][] dimensionConverter(int[] srcImgArr, int h, int w){
        int[][] result = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j <w ; j++) {
                result[i][j] = srcImgArr[w*i+j];
            }
        }
        return result;
    }

    /**
     * Converts image to grey color scheme
     * @param rgbArray color image to convert
     * @return image in grey
     */
    public int[][] imageToGrey(int[][] rgbArray){

        int width = rgbArray.length;
        int height = rgbArray[0].length;

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++){

                int pixel = rgbArray[i][j];
                int pixelRED = (int)( 0.2125 * ((pixel>>16) & 0xFF) );
                int pixelGREEN = (int)( 0.7152 * ((pixel>>8) & 0xFF) );
                int pixelBLUE = (int)( 0.0722 * ((pixel>>0) & 0xFF) );

                rgbArray[i][j] = (pixelRED + pixelGREEN + pixelBLUE) << 16;
                rgbArray[i][j] |= (pixelRED + pixelGREEN + pixelBLUE) << 8;
                rgbArray[i][j] |= (pixelRED + pixelGREEN + pixelBLUE) << 0;
            }

        return rgbArray;
    }

    /**
     * Converts Buffered image to RGB array
     * @param image image to convert
     * @return RGB array
     */
    public int[][] toRGBArray(BufferedImage image){

        int rgbArray[][] = new int[image.getWidth()][image.getHeight()];

        for (int i=0; i < image.getWidth(); i++)
            for (int j=0; j < image.getHeight(); j++)
            {
                rgbArray[i][j] = image.getRGB(i,j);
            }
        return rgbArray;
    }

    /**
     * Converts RGB array to Buffered image
     * @param rgbArray RGB array
     * @return BufferedImage
     */
    public BufferedImage toBufferedImage(int[][] rgbArray){

        int width = rgbArray.length;
        int height = rgbArray[0].length;

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                image.setRGB(i,j,rgbArray[i][j]);
        return image;
    }
}
