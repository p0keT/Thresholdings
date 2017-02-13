package sample;

/**
 * Created by Sergiy on 2/10/2017.
 */
public class AdaptiveThresholdings {

    /**
     * Adaptive algorithm for 1 dimension imageRGB array
     * ПРАЦЮЄ. Десь скоріш за все є помилка з х у. полоски вже нема але мені здається цей метод мене десь обманює
     * @param sourceImage Вхідне зображення. Одномірний масив.
     * @param threshold threshold for binarization (most often values are 0.7 - 0.8)
     * @param height  height of image (number of rows)
     * @param width width of image (number of columns)
     * @return binarization image array (1 dimension)
     */
    public static int[] Bradley_threshold(int[] sourceImage, int width, int height, float threshold) {
        int[] src = sourceImage;
        int[] res = new int[src.length];
        int []integral_image;
        int sum;
        int index;

        //рассчитываем интегральное изображение
        integral_image = (new ImageProcessing().toIntegralImage(src,height,width));

        //находим границы для локальных областей
        for (int i = 0; i < width; i++) {

            for (int j = 0; j < width; j++) {//тут замість height поставив width і полоска прибралася.
                                                // Тра розібратися шо за херня і чо тепер все ок
                                                //походу тут тра в циклах висоту і ширину поміняти і переробити алгоритм під ці зміни
                index = width*j + i;

                int a = (i - 2) * width + (j - 2);
                int b = (i - 2) * width + (j + 1);
                int c = (i + 1) * width + (j - 2);
                int d = (i + 1) * width + (j + 1);
                int sA;
                int sB;
                int sC;
                int sD;
                if ((i - 2) < 0 && (j - 2) < 0) {
                    sA = 0;
                    sB = 0;
                    sC = 0;
                    sD = integral_image[d];
                } else {
                    if ((i - 2) < 0) {
                        sA = 0;
                        sB = 0;
                        if(j==width-1)
                            sD = integral_image[(i + 1) * width + (j)];
                        else
                            sD = integral_image[d];
                        sC = integral_image[c];

                    } else {
                        if ((j - 2) < 0) {
                            sA = 0;
                            sC = 0;
                            if(i==height-1)
                                sD = integral_image[(i) * width + (j+1)];
                            else
                                sD = integral_image[d];
                            sB = integral_image[b];

                        } else {
                            if(j==width-1) {
                                b = (i - 2) * width + (j);
                                d = (i + 1) * width + (j);
                            }
                            if(i==height-1)
                            {
                                c = (i) * width + (j - 2);
                                d = (i) * width + (j+1);
                            }
                            sA = integral_image[a];
                            sC = integral_image[c];
                            sB = integral_image[b];
                            if(j==width-1&&i==height-1)
                                sD = integral_image[(i) * width + (j)];
                            else
                                sD = integral_image[d];
                        }
                    }
                }

                sum = sA + sD - sC - sB;
                int pixels = 9;
                if(sA==0)
                    pixels-=1;
                if(sB==0)
                    pixels-=1;
                if(sC==0)
                    pixels-=1;
                if(sD==0)
                    pixels-=1;

                if ( (src[index])<sum / pixels*threshold){
                    res[index] = 0x000000;
                }
                else{
                    res[index] = 0xFFFFFF;
                }
            }

        }

        return res;
    }
}
