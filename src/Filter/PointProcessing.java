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
public class PointProcessing {

    private BufferedImage img;

    public PointProcessing(BufferedImage img) {
        this.img = img;
        toGray();
    }

    //convert this picture to gray
    private void toGray() {

    }

    private int getAVG(int x, int y, BufferedImage image, int size) {
        int sum = 0, pixel;
        for (int i = x - (int) (size / 2); i <= x + (int) (size / 2); i++) {
            for (int j = y - (int) (size / 2); j <= y + (int) (size / 2); j++) {
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
        for (int i = (int) (size / 2); i < img.getWidth() - (int) (size / 2); i++) {
            for (int j = (int) (size / 2); j < img.getHeight() - (int) (size / 2); j++) {
                s = getAVG(i, j, img, size);
                imgTemp.setRGB(i, j, new Color(s, s, s).getRGB());
            }
        }
        return imgTemp;
    }

    public BufferedImage Negative() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int pixel;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                pixel = 255 - new Color(img.getRGB(i, j)).getBlue();
                Color color = new Color(pixel, pixel, pixel);
                imgTemp.setRGB(i, j, color.getRGB());
            }
        }
        return imgTemp;
    }

    private int getThreshold(BufferedImage image) {
        int sum = 0, pixel;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                pixel = new Color(image.getRGB(i, j)).getBlue();
                sum += pixel;
            }
        }
        return Math.round(sum / (image.getWidth() * image.getHeight()));
    }

    public BufferedImage ThresholdNegative() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int threshold = getThreshold(img), pixel;
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

    public BufferedImage Logarithm() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int s, pixel;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                pixel = new Color(img.getRGB(i, j)).getBlue();
                s = (int) (Math.log10(1 + pixel) * 255 / (Math.log10(256)));
                Color color = new Color(s, s, s);
                imgTemp.setRGB(i, j, color.getRGB());
            }
        }
        return imgTemp;
    }

    public BufferedImage PowerLow(float lambda) {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int pixel, s;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                pixel = new Color(img.getRGB(i, j)).getBlue();
                s = (int) (Math.pow(pixel / 255f, lambda) * 255);
                s = s > 255 ? 255 : s;
                imgTemp.setRGB(i, j, new Color(s, s, s).getRGB());
            }
        }
        return imgTemp;
    }

    //arr contains bit position used
    public BufferedImage BitPlaneSlicing(int bitplace) {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int pixel, s = 0;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                pixel = new Color(img.getRGB(i, j)).getBlue();
                s = pixel & bitplace;
                Color color = new Color(s, s, s);
                imgTemp.setRGB(i, j, color.getRGB());
            }
        }
        return imgTemp;
    }
}
