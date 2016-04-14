package eu.webdude.homesurveillance.motiondetection.filters;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;


public class JHGrayFilter extends JHFilter {

    protected boolean canFilterIndexColorModel = true;

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dst) {

        int width = src.getWidth();
        int height = src.getHeight();
        int type = src.getType();

        WritableRaster srcRaster = src.getRaster();

        if (dst == null) {
            dst = createCompatibleDestImage(src, null);
        }

        WritableRaster dstRaster = dst.getRaster();

        int[] inPixels = new int[width];
        for (int y = 0; y < height; y++) {
            if (type == BufferedImage.TYPE_INT_ARGB) {
                srcRaster.getDataElements(0, y, width, 1, inPixels);
                for (int x = 0; x < width; x++) {
                    inPixels[x] = filterRGB(inPixels[x]);
                }
                dstRaster.setDataElements(0, y, width, 1, inPixels);
            } else {
                src.getRGB(0, y, width, 1, inPixels, 0, width);
                for (int x = 0; x < width; x++) {
                    inPixels[x] = filterRGB(inPixels[x]);
                }
                dst.setRGB(0, y, width, 1, inPixels, 0, width);
            }
        }

        return dst;
    }

    private int filterRGB(int rgb) {
        int a = rgb & 0xff000000;
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        rgb = (r * 77 + g * 151 + b * 28) >> 8; // NTSC luma
        return a | (rgb << 16) | (rgb << 8) | rgb;
    }
}