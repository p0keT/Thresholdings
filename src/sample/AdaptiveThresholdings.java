package sample;

/**
 * Created by Sergiy on 2/10/2017.
 */
public class AdaptiveThresholdings {

    /**
     * Adaptive algorithm for 1 dimension imageRGB array
     * ПРАЦЮЄ. Десь скоріш за все є помилка з х у. полоски вже нема але мені здається цей метод мене десь обманює
     * @param sourceImage Вхідне зображення. Одномірний масив.
     * @param threshold threshold for binarization (most often values are 0.9 - 1.1)
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
        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {
                index = width*i + j;

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

    /**
     * Adaptive algorithm for 2 dimension imageRGB array
     * @param sourceImage Source image. 2 dimension array.
     * @param threshold threshold for binarization (most often values are 0.9 - 1.1)
     * @return binarization image array (2 dimension)
     */
    public static int[][] Bradley_threshold(int[][] sourceImage, float threshold) {
        int[][] src = sourceImage;
        int[][] res = new int[src.length][src[0].length];
        int [][]integral_image;
        int sum;

        //рассчитываем интегральное изображение
        integral_image = (new ImageProcessing().toIntegralImage(src));

        //находим границы для локальных областей
        for (int i = 0; i < src.length; i++) {

            for (int j = 0; j < src[0].length; j++) {

                int sA = -1;
                int sB = -1;
                int sC = -1;
                int sD = -1;

                int ai = (i - 2);
                if(ai<0)
                   sA=0;

                int aj = (j - 2);
                if(aj<0)
                    sA=0;

                int bi = (i - 2);
                if(bi<0)
                    sB=0;

                int bj = (j + 1);
                if(bj>src[0].length-1)
                    bj = src[0].length-1;

                int ci = (i + 1);
                if(ci>src.length-1)
                    ci = src.length-1;

                int cj = (j - 2);
                if(cj<0)
                    sC=0;

                int di = (i + 1);
                if(di>src.length-1)
                    di = src.length-1;

                int dj = (j + 1);
                if(dj>src[0].length-1)
                    dj = src[0].length-1;

                if(sA!=0){
                    sA = integral_image[ai][aj];
                }
                if(sB!=0){
                    sB = integral_image[bi][bj];
                }
                if(sC!=0){
                    sC = integral_image[ci][cj];
                }
                if(sD!=0){
                    sD = integral_image[di][dj];
                }

                sum = sA + sD - sC - sB;
                int pixels = 9; //можна розрахувати з ai,...,dj
                if(sA==0)
                    pixels-=1;
                if(sB==0)
                    pixels-=1;
                if(sC==0)
                    pixels-=1;
                if(sD==0)
                    pixels-=1;

                if ( (src[i][j])<sum / pixels*threshold){
                    res[i][j] = 0x000000;
                }
                else{
                    res[i][j] = 0xFFFFFF;
                }
            }

        }

        return res;
    }

    //TODO: не працює, хоча може і працює, а я просто не січу фішку
    public static int[][] Bradley_threshold2(int[][] sourceImage, float threshold) {
        int[][] src = sourceImage;
        int[][] res = new int[src.length][src[0].length];
        int [][]integral_image;
        int sum;
        int index;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print("["+src[i][j]+"]");

            }
            System.out.println();
        }
        System.out.println("==================");
        //рассчитываем интегральное изображение
        integral_image = (new ImageProcessing().toIntegralImage(src));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print("["+integral_image[i][j]+"]");
            }
            System.out.println();
        }
        int f1=src.length/16;

        int z=0;
        //находим границы для локальных областей
        for(int l = 0;l<16;l++) {
            int f2=src[0].length/16;
            for (int k = 0; k < 16; k++) {

                for (int i = f1-src.length/16; i < f1; i++) {

                    for (int j = f2-src[0].length/16; j < f2; j++) {

                        int sA = -1;
                        int sB = -1;
                        int sC = -1;
                        int sD = -1;

                        int ai = f1-(src.length/16)-1;
                        if (ai < 0)
                            sA = 0;

                        int aj = f2-(src[0].length/16)-1;
                        if (aj < 0)
                            sA = 0;

                        int bi = f1-(src.length/16)-1;
                        if (bi < 0)
                            sB = 0;

                        int bj = f2-1;


                        int ci = f1-1;


                        int cj = f2-(src[0].length/16)-1;
                        if (cj < 0)
                            sC = 0;

                        int di = f1-1;


                        int dj = f2-1;


                        if (sA != 0) {
                            sA = integral_image[ai][aj];
                        }
                        if (sB != 0) {
                            sB = integral_image[bi][bj];
                        }
                        if (sC != 0) {
                            sC = integral_image[ci][cj];
                        }
                        if (sD != 0) {
                            sD = integral_image[di][dj];
                        }

                        sum = sA + sD - sC - sB;
                        int pixels = src.length/16 * src[0].length/16;

                        if ((src[i][j]*pixels) < sum *(1.0- threshold)) {
                            res[i][j] = 0x000000;
                        } else {
                            res[i][j] = 0xFFFFFF;
                        }
                    }

                }
                System.out.println("rect: "+z+"/"+l+" "+k);
                z++;
                f2 = f2 + src[0].length / 16;
            }
            f1 = f1 + src.length / 16;
        }
        return res;
    }
}

