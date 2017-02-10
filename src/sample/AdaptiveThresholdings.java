package sample;

/**
 * Created by Sergiy on 2/10/2017.
 */
public class AdaptiveThresholdings {

    /**
     * Adaptive algorithm for 1 dimension imageRGB array
     * ПРАЦЮЄ. Десь скоріш за все є помилка з х у. Тому є ота полоска.
     * @param src Вхідне зображення. Одномірний масив.
     * @param res Вихідне зображення. Одномірний масив.
     * @param width і так понятно
     * @param height і так понятно
     */
    public static void Bradley_threshold(int[] src, int[] res, int width, int height) {
        final int S = width/4;

        int s2 = 4;
        final float t = 0.15f;
        int []integral_image;
        int sum;
        int count;
        int index;
        int x1, y1, x2, y2;

        //рассчитываем интегральное изображение
        integral_image = new int[width*height];

        for (int i = 0; i < width; i++) {
            sum = 0;
            for (int j = 0; j < height; j++) {
                index = j * width + i;
                sum += src[index];
                if (i==0)
                    integral_image[index] = sum;
                else
                    integral_image[index] = integral_image[index-1] + sum;
            }
        }
        int temp=0;
       /* for (int i = 0; i <integral_image.length ; i++) {
            if(temp==16) {
                temp = 0;
                System.out.println();
            }
            temp++;
            System.out.print(integral_image[i]+"/");
        }*/

        //находим границы для локальных областей
        for (int i = 0; i < width; i++) {
            //System.out.println();
            for (int j = 0; j < height; j++) {
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

                //System.out.print("[" +src[index]+"/"+ sum/pixels+"]");
                /*if ((src[index]) < (sum / pixels))
                    res[index] = 0;
                else
                    res[index] = 255;*/
                if ( (src[index])<sum / pixels*0.7){
                    res[index] = 0x000000;
                }
                else{
                    res[index] = 0xFFFFFF;
                }
            }

        }
        // System.out.println();
        int temp2=0;
        /*for (int i = 0; i <res.length ; i++) {
            if(temp2==16) {
                temp2 = 0;
                System.out.println();
            }
            temp2++;
            System.out.print(res[i]+"/");
        }*/


    }
}
