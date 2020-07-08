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
public class ImageProcessing {

    private BufferedImage img;

    public ImageProcessing(BufferedImage img) {
        this.img = img;
    }

    private int[] countPixel(BufferedImage image) {
        int gray, arr[] = new int[256];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                gray = new Color(image.getRGB(j, i)).getBlue();
                ++arr[gray];
            }
        }
        return arr;
    }

    private int[] sumHistogram(int array[]) {
        int arr[] = new int[256];
        for (int i = 1; i < array.length; i++) {
            arr[i] = array[i] + arr[i - 1];
        }
        return arr;
    }

    public BufferedImage equalize() {
        BufferedImage imgTemp = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int cp[] = countPixel(img);
        int sh[] = sumHistogram(cp);
        int gray = 0;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                gray = new Color(img.getRGB(j, i)).getBlue();
                int k = (sh[gray] * 256) / (img.getWidth() * img.getHeight());
                k = k > 255 ? 255 : k;
                Color color = new Color(k, k, k);
                imgTemp.setRGB(j, i, color.getRGB());
            }
        }
        return imgTemp;
    }

//    private JFreeChart createLineChart(String name) {
//        JFreeChart lineChart = ChartFactory.createLineChart(name, "Gray Level",
//                "Frequences", createDataset(), PlotOrientation.VERTICAL, true,
//                true, true);
//        return lineChart;
//    }

//    private CategoryDataset createDataset() {
//        int arr[] = countPixel(img);
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        for (int i = 0; i < arr.length; i++) {
//            dataset.setValue(arr[i], "0-255", i + "");
//        }
//        return dataset;
//    }

//    public ChartPanel drawHistogram(String name) {
////        gray, gray after equalize, rgb, rgb after qualize
//        ChartPanel chart = new ChartPanel(createLineChart(name));
//        chart.setPreferredSize(new java.awt.Dimension(500, 400));
//        return chart;
//    }
}
