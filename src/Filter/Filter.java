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
public class Filter {
    private BufferedImage img;

    public Filter(BufferedImage img) {
        this.img = img;
    }
    
    public BufferedImage toGray(){
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(), 
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));
                int r = (int) (c.getRed() * 0.299);
                int g = (int) (c.getGreen() * 0.587);
                int b = (int) (c.getBlue() * 0.114);
                Color newColor = new Color(r + g + b, r + g + b, r + g + b);
                imgTemp.setRGB(j, i, newColor.getRGB());
            }
        }
        return imgTemp;
    }
}
