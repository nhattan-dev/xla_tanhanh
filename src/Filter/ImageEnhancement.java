/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Welcome
 */
public class ImageEnhancement {

    private BufferedImage img;
    private final int maskGy[][] = {{-1,0,1},{-2,0,2},{-1,0,1}};
    private final int maskGx[][] = {{-1,-2,-1},{0,0,0},{1,2,1}};

    public ImageEnhancement(BufferedImage img) {
        this.img = img;
        toGray();
    }

    private void toGray() {

    }

    private int getAVG(int x, int y, BufferedImage image, int size) {
        int sum = 0, pixel;
        for (int i = x - (int) (size / 2); i <= x + (int) (size / 2); i++) {
            for (int j = y - (int) (size / 2); j <= y + (int) (size / 2); j++) {
                //Get total
                pixel = new Color(image.getRGB(i, j)).getBlue();
                sum += pixel;
            }
        }
        return sum / (size * size);
    }

    public BufferedImage AVG(int size) {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int s = 0;
        for (int i = 1; i < img.getWidth() - 1; i++) {
            for (int j = 1; j < img.getHeight() - 1; j++) {
                s = getAVG(i, j, img, size);
                imgTemp.setRGB(i, j, new Color(s, s, s).getRGB());
            }
        }
        return imgTemp;
    }

    private int getMedian(int x, int y, BufferedImage image, int size) {
        int arr[] = new int[size * size], number = 0;
        //Get the set of number
        for (int i = x - (int) (size / 2); i <= x + (int) (size / 2); i++) {
            for (int j = y - (int) (size / 2); j <= y + (int) (size / 2); j++) {
                arr[number++] = new Color(image.getRGB(i, j)).getBlue();
            }
        }

        //Bubble Sort
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j+1] and arr[i] 
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr[(int) (size * size / 2)];
    }

    public BufferedImage Mean(int size) {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int s = 0;
        for (int i = (int) (size / 2); i < img.getWidth() - (int) (size / 2); i++) {
            for (int j = (int) (size / 2); j < img.getHeight() - (int) (size / 2); j++) {
                s = getMedian(i, j, img, size);
                imgTemp.setRGB(i, j, new Color(s, s, s).getRGB());
            }
        }
        return imgTemp;
    }

    private int getMax(int x, int y, BufferedImage image, int size) {
        int max = 0, pixel;
        for (int i = x - (int) (size / 2); i <= x + (int) (size / 2); i++) {
            for (int j = y - (int) (size / 2); j <= y + (int) (size / 2); j++) {
                //Don't use central value
                if (i == j) {
                    continue;
                }
                pixel = new Color(image.getRGB(i, j)).getBlue();
                max = max > pixel ? max : pixel;
            }
        }
        return max;
    }

    public BufferedImage Max(int size) {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int s = 0;
        for (int i = 1; i < img.getWidth() - 1; i++) {
            for (int j = 1; j < img.getHeight() - 1; j++) {
                s = getMax(i, j, img, size);
                imgTemp.setRGB(i, j, new Color(s, s, s).getRGB());
            }
        }
        return imgTemp;
    }

    private int getMin(int x, int y, BufferedImage image, int size) {
        int min = 999, pixel;
        for (int i = x - (int) (size / 2); i <= x + (int) (size / 2); i++) {
            for (int j = y - (int) (size / 2); j <= y + (int) (size / 2); j++) {
                //Don't use central value
                if (i == j) {
                    continue;
                }
                pixel = new Color(image.getRGB(i, j)).getBlue();
                min = min < pixel ? min : pixel;
            }
        }
        return min;
    }

    public BufferedImage Min(int size) {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int s = 0;
        for (int i = (int) (size / 2); i < img.getWidth() - (int) (size / 2); i++) {
            for (int j = (int) (size / 2); j < img.getHeight() - (int) (size / 2); j++) {
                s = getMin(i, j, img, size);
                imgTemp.setRGB(i, j, new Color(s, s, s).getRGB());
            }
        }
        return imgTemp;
    }

    private int getAVG(int x, int y, BufferedImage image) {
        int sum = 0, pixel;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                pixel = new Color(image.getRGB(i, j)).getBlue();
                //Don't use central value
                if (i == x && j == y) {
                    sum += (4 * pixel);
                } else if (i == x || j == y) {
                    sum += (2 * pixel);
                } else {
                    sum += pixel;
                }
            }
        }
        return sum / 16;
    }

    public BufferedImage WeightedAVG() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int s = 0;
        for (int i = 1; i < img.getWidth() - 1; i++) {
            for (int j = 1; j < img.getHeight() - 1; j++) {
                s = getAVG(i, j, img);
                imgTemp.setRGB(i, j, new Color(s, s, s).getRGB());
            }
        }
        return imgTemp;
    }

    public BufferedImage Laplacian() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        return imgTemp;
    }

    private int newColor(int x, int y, BufferedImage image, int mask[][]) {
        int sum = 0, pixel;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                //Get total
                pixel = new Color(image.getRGB(i, j)).getBlue();
                sum += (pixel * mask[i - (x - 1)][j - (y - 1)]);
            }
        }
        return sum;
    }
    
    public BufferedImage Sobel() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 1; i < img.getWidth() - 1; i++) {
            for (int j = 1; j < img.getHeight() - 1; j++) {
                int Gx = newColor(i, j, img, maskGx);
                int Gy = newColor(i, j, img, maskGy);
                int x = (int) Math.sqrt(Math.pow(Gx, 2) + Math.pow(Gy, 2));
                x = x > 255 ? 255 : x;
                x = x < 0 ? 0 : x;
                imgTemp.setRGB(i, j, new Color(x, x, x).getRGB());
            }
        }
        return imgTemp;
    }

    public BufferedImage BoneScan() {
        return img;
    }
}
