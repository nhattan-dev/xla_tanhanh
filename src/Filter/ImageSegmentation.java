/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Welcome
 */
public class ImageSegmentation {

    private BufferedImage img;
    private final int maskPoint[][] = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
    private final int maskHoz[][] = {{-1, -1, -1}, {2, 2, 2}, {-1, -1, -1}};
    private final int mask45[][] = {{-1, -1, 2}, {-1, 2, -1}, {2, -1, -1}};
    private final int maskVer[][] = {{-1, 2, -1}, {-1, 2, -1}, {-1, 2, -1}};
    private final int mask_45[][] = {{2, -1, -1}, {-1, 2, -1}, {-1, -1, 2}};
    private final int[][] maskGx_Previtt = {{1, 0, -1}, {1, 0, -1}, {1, 0, -1}};
    private final int[][] maskGy_Previtt = {{1, 1, 1}, {0, 0, 0}, {-1, -1, -1}};
    private final int[][] maskGx_Robert = {{1, 0}, {0, -1}};
    private final int[][] maskGy_Robert = {{0, 1}, {-1, 0}};
    private final int[][] maskGy_Sobel = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    private final int[][] maskGx_Sobel = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    private final int maskLoG[][] = {{0, 0, 3, 2, 2, 2, 3, 0, 0},
                                     {0, 2, 3, 5, 5, 5, 3, 2, 0},
                                     {3, 3, 5, 3, 0, 3, 5, 3, 3},
                                     {2, 5, 3, -12, -23, -12, 3, 5, 2},
                                     {2, 5, 0, -23, -40, -23, 0, 5, 2},
                                     {2, 5, 3, -12, -23, -12, 3, 5, 2},
                                     {3, 3, 5, 3, 0, 3, 5, 3, 3},
                                     {0, 2, 3, 5, 5, 5, 3, 2, 0},
                                     {0, 0, 3, 2, 2, 2, 3, 0, 0},};
    private float T = 1f;

    public ImageSegmentation(BufferedImage img) {
        this.img = img;
        toBinary();
    }

    private void toBinary() {

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

    public BufferedImage LineDectection() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 1; i < img.getWidth() - 1; i++) {
            for (int j = 1; j < img.getHeight() - 1; j++) {
                int x = newColor(i, j, img, mask45) + newColor(i, j, img, maskHoz)
                        + newColor(i, j, img, maskVer) + newColor(i, j, img, mask_45);
                x = x > 255 ? 255 : x;
                x = x < 0 ? 0 : x;
                imgTemp.setRGB(i, j, new Color(x, x, x).getRGB());
            }
        }
        return img;
    }

    public BufferedImage PointDectection() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 1; i < img.getWidth() - 1; i++) {
            for (int j = 1; j < img.getHeight() - 1; j++) {
                int x = newColor(i, j, img, maskPoint);
                x = x > 255 ? 255 : x;
                x = x < 0 ? 0 : x;
                imgTemp.setRGB(i, j, new Color(x, x, x).getRGB());
            }
        }
        return imgTemp;
    }

    public BufferedImage Sobel() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 1; i < img.getWidth() - 1; i++) {
            for (int j = 1; j < img.getHeight() - 1; j++) {
                int Gx = newColor(i, j, img, maskGx_Sobel);
                int Gy = newColor(i, j, img, maskGy_Sobel);
                int x = (int) Math.sqrt(Math.pow(Gx, 2) + Math.pow(Gy, 2));
                x = x > 255 ? 255 : x;
                x = x < 0 ? 0 : x;
                imgTemp.setRGB(i, j, new Color(x, x, x).getRGB());
            }
        }
        return imgTemp;
    }

    public BufferedImage Previtt() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 1; i < img.getWidth() - 1; i++) {
            for (int j = 1; j < img.getHeight() - 1; j++) {
                int Gx = newColor(i, j, img, maskGx_Previtt);
                int Gy = newColor(i, j, img, maskGy_Previtt);
                int x = (int) Math.sqrt(Math.pow(Gx, 2) + Math.pow(Gy, 2));
                x = x > 255 ? 255 : x;
                x = x < 0 ? 0 : x;
                imgTemp.setRGB(i, j, new Color(x, x, x).getRGB());
            }
        }
        return imgTemp;
    }

    private int newColorRobert(int x, int y, BufferedImage image, int mask[][]) {
        int sum = 0, pixel;
        for (int i = x; i <= x + 1; i++) {
            for (int j = y; j <= y + 1; j++) {
                //Get total
                pixel = new Color(image.getRGB(i, j)).getBlue();
                sum += (pixel * mask[i - x][j - y]);
            }
        }
        return sum;
    }
    
    public BufferedImage Robert() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < img.getWidth() - 1; i++) {
            for (int j = 0; j < img.getHeight() - 1; j++) {
                int Gx = newColorRobert(i, j, img, maskGx_Robert);
                int Gy = newColorRobert(i, j, img, maskGy_Robert);
                int x = (int) Math.sqrt(Math.pow(Gx, 2) + Math.pow(Gy, 2));
                x = x > 255 ? 255 : x;
                x = x < 0 ? 0 : x;
                imgTemp.setRGB(i, j, new Color(x, x, x).getRGB());
            }
        }
        return imgTemp;
    }

    private int getAVGThreshold(BufferedImage image, Point start, Point end) {
        int sum = 0;
        int area = 0;
        for (int i = start.x; i < end.x; i++) {
            for (int j = start.y; j < end.y; j++) {
                try {
                    sum += new Color(image.getRGB(i, j)).getBlue();
                    area++;
                } catch (Exception e) {
                }
            }
        }
        return Math.round(sum / area);
    }

    private int threshold_Finder(int threshold, Point start, Point end) {
        int low = 0, hight = 0, sumLow = 0, sumHight = 0, pixel;
        for (int i = start.x; i < end.x; i++) {
            for (int j = start.y; j < end.y; j++) {
                try {
                    pixel = new Color(img.getRGB(i, j)).getBlue();
                    if (pixel > threshold) {
                        sumHight += pixel;
                        hight++;
                    } else {
                        sumLow += pixel;
                        low++;
                    }
                } catch (Exception e) {
                }
            }
        }
        int T1 = 0;
        try {
            T1 = sumHight / hight;
        } catch (Exception e) {
        }
        int T2 = 0;
        try {
            T2 = sumLow / low;
        } catch (Exception e) {
        }
        int new_Threshold = (T1 + T2) / 2;
        if (Math.abs(new_Threshold - threshold) < T) {
            return new_Threshold;
        } else {
            return threshold_Finder(new_Threshold, start, end);
        }
    }

    private void Adaptive(Point start, Point end, BufferedImage image) {
        int pixel;
        int threshold = threshold_Finder(ImageSegmentation.this.getAVGThreshold(img, start, end), start, end);
        for (int i = start.x; i < end.x; i++) {
            for (int j = start.y; j < end.y; j++) {
                try {
                    pixel = new Color(image.getRGB(i, j)).getBlue();
                    pixel = pixel > threshold ? 255 : 0;
                    Color color = new Color(pixel, pixel, pixel);
                    image.setRGB(i, j, color.getRGB());
                } catch (Exception e) {
                }
            }
        }
    }

    public BufferedImage AdaptiveThreshold(int ratio) {
        BufferedImage imgTemp = copyImage(img);
        Point start, end;
        for (int i = 0; i < img.getWidth(); i += ratio) {
            for (int j = 0; j < img.getHeight(); j += ratio) {
                start = new Point(i, j);
                end = new Point(i + (int) ratio, j + (int) ratio);
                Adaptive(start, end, imgTemp);
            }
        }
        return imgTemp;
    }

    private int getAVGThreshold(BufferedImage image) {
        int sum = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                sum += new Color(image.getRGB(i, j)).getBlue();
            }
        }
        return Math.round(sum / (image.getWidth() * image.getHeight()));
    }

    private int globalThreshold_Finder(BufferedImage image, int threshold) {
        int low = 0, hight = 0, sumLow = 0, sumHight = 0, pixel;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                pixel = new Color(image.getRGB(i, j)).getBlue();
                if (pixel > threshold) {
                    sumHight += pixel;
                    hight++;
                } else {
                    sumLow += pixel;
                    low++;
                }
            }
        }
        int T1 = 0;
        try {
            T1 = sumHight / hight;
        } catch (Exception e) {
        }
        int T2 = 0;
        try {
            T2 = sumLow / low;
        } catch (Exception e) {
        }
        int new_Threshold = (T1 + T2) / 2;
        if (Math.abs(new_Threshold - threshold) < T) {
            return new_Threshold;
        } else {
            return globalThreshold_Finder(image, new_Threshold);
        }
    }
    
    public BufferedImage GlobalThreshold() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int pixel, threshold = globalThreshold_Finder(img, getAVGThreshold(img));
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                pixel = new Color(img.getRGB(i, j)).getBlue();
                pixel = pixel > threshold ? 255 : 0;
                Color color = new Color(pixel, pixel, pixel);
                imgTemp.setRGB(i, j, color.getRGB());
            }
        }
        return imgTemp;
    }
    
    private int newColorLoG(int x, int y, BufferedImage image, int mask[][]) {
        int sum = 0, pixel;
        for (int i = x - 4; i <= x + 4; i++) {
            for (int j = y - 4; j <= y + 4; j++) {
                //Get total
                pixel = new Color(image.getRGB(i, j)).getBlue();
                sum += (pixel * mask[i - (x - 4)][j - (y - 4)]);
            }
        }
        return sum;
    }
    
    public BufferedImage LoG(){
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 4; i < img.getWidth() - 4; i++) {
            for (int j = 4; j < img.getHeight() - 4; j++) {
                int LoG = newColorLoG(i, j, img, maskLoG);
                LoG = LoG > 255 ? 255 : LoG;
                LoG = LoG < 0 ? 0 : LoG;
                imgTemp.setRGB(i, j, new Color(LoG, LoG, LoG).getRGB());
            }
        }
        return imgTemp;
    }

    private BufferedImage copyImage(BufferedImage image) {
        BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = temp.getGraphics();
        g.drawImage(image, 0, 0, null);
        return temp;
    }
}
