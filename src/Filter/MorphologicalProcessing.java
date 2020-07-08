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
public class MorphologicalProcessing {
    private BufferedImage img;
    final private int maxGray = 255;

    public MorphologicalProcessing(BufferedImage img) {
        this.img = img;
        toBinary();
    }
    
    private void toBinary(){
        
    }
    
    private boolean isFit(int x, int y, BufferedImage image, int strEle[][]) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (strEle[i - (x - 1)][j - (y - 1)] == maxGray) {
                    if (new Color(image.getRGB(i, j)).getBlue() != maxGray) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    //Erosion
    private BufferedImage Erosion(BufferedImage image) {
        int strEle[][] = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
        BufferedImage imgFit = new BufferedImage(image.getWidth(), image.getHeight(), 4);
        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {
                if (isFit(i, j, image, strEle)) {
                    imgFit.setRGB(i, j, new Color(255, 255, 255).getRGB());
                }
            }
        }
        return imgFit;
    }
    
    private BufferedImage toBinary(BufferedImage image, int threshold){
        BufferedImage negImg = new BufferedImage(img.getWidth(), img.getHeight(), 4);
        int x;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                x = new Color(image.getRGB(i, j)).getBlue();
                x = x > threshold ? 255 : 0;
                negImg.setRGB(i, j, new Color(x,x,x).getRGB());
            }
        }
        return negImg;
    }
    
    public BufferedImage BoundaryExtraction(){
        //Convert to binary image
        int threshold = 150;
        img = toBinary(img, threshold);
        
        //Get Erosion image
        BufferedImage imgFit = Erosion(img);
        
        //Subtract image
        BufferedImage imgBoundary = new BufferedImage(img.getWidth(), img.getHeight(), 4);
        for (int i = 1; i < img.getWidth() - 1; i++) {
            for (int j = 1; j < img.getHeight()- 1; j++) {
                int fitPixel = new Color(imgFit.getRGB(i, j)).getBlue();
                int oldPixel = new Color(img.getRGB(i, j)).getBlue();
                int newPixel = oldPixel - fitPixel;
                imgBoundary.setRGB(i, j, new Color(newPixel,newPixel,newPixel).getRGB());
            }
        }
        return imgBoundary;
    }
    
    private boolean isHit(int x, int y, BufferedImage image, int strEle[][]) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (strEle[i - (x - 1)][j - (y - 1)] == maxGray) {
                    if (new Color(image.getRGB(i, j)).getBlue() == maxGray) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Gian  
    private BufferedImage Dilation(BufferedImage image) {
        int strEle[][] = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
        BufferedImage imgHit = new BufferedImage(image.getWidth(), image.getHeight(), 4);
        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {
                //is hit
                if (isHit(i, j, image, strEle)) {
                    imgHit.setRGB(i, j, new Color(255, 255, 255).getRGB());
                }
            }
        }
        return imgHit;
    }    
    
    private BufferedImage Opening(BufferedImage image) {
        return Dilation(Erosion(image));
    }

    private BufferedImage Closing(BufferedImage image) {
        return Erosion(Dilation(image));
    }
    
    public BufferedImage Enhancement(){        
        BufferedImage imgEnhancement = new BufferedImage(img.getWidth(), img.getHeight(), 4);
        int threshold = 150;
        imgEnhancement = toBinary(img, threshold);
        return Closing(Opening(imgEnhancement));
    }
    
    public BufferedImage RegionFill(){
        return img;
    }
}
