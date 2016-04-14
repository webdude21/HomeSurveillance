package eu.webdude.homesurveillance.motiondetection.filters;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;


/**
 * A filter which flips images or rotates by multiples of 90 degrees.
 */
public class JHFlipFilter extends JHFilter {

    /**
     * Rotate the image 90 degrees clockwise.
     */
    public static final int FLIP_90CW = 4;

    /**
     * Rotate the image 90 degrees counter-clockwise.
     */
    public static final int FLIP_90CCW = 5;

    /**
     * Rotate the image 180 degrees.
     */
    public static final int FLIP_180 = 6;

    private int operation;

    /**
     * Construct a FlipFilter which flips horizontally and vertically.
     */
    public JHFlipFilter() {
        this(FLIP_90CW);
    }

    /**
     * Construct a FlipFilter.
     *
     * @param operation the filter operation
     */
    public JHFlipFilter(int operation) {
        this.operation = operation;
    }

    /**
     * Set the filter operation.
     *
     * @param operation the filter operation
     * @see #getOperation
     */
    public void setOperation(int operation) {
        this.operation = operation;
    }

    /**
     * Get the filter operation.
     *
     * @return the filter operation
     * @see #setOperation
     */
    public int getOperation() {
        return operation;
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        int width = src.getWidth();
        int height = src.getHeight();

        int[] inPixels = getRGB(src, 0, 0, width, height, null);

        int w = width;
        int h = height;

        int newW = w;
        int newH = h;
        switch (operation) {
            case FLIP_90CW:
                newW = h;
                newH = w;
                break;
            case FLIP_90CCW:
                newW = h;
                newH = w;
                break;
            case FLIP_180:
                break;
        }

        int[] newPixels = new int[newW * newH];

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                int index = row * width + col;
                int newRow = row;
                int newCol = col;
                switch (operation) {
                    case FLIP_90CW:
                        newRow = col;
                        newCol = h - row - 1;
                        ;
                        break;
                    case FLIP_90CCW:
                        newRow = w - col - 1;
                        newCol = row;
                        break;
                    case FLIP_180:
                        newRow = h - row - 1;
                        newCol = w - col - 1;
                        break;
                }
                int newIndex = newRow * newW + newCol;
                newPixels[newIndex] = inPixels[index];
            }
        }

        if (dst == null) {
            ColorModel dstCM = src.getColorModel();
            dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(newW, newH), dstCM.isAlphaPremultiplied(), null);
        }
        setRGB(dst, 0, 0, newW, newH, newPixels);

        return dst;
    }

    @Override
    public String toString() {
        switch (operation) {
            case FLIP_90CW:
                return "Rotate 90";
            case FLIP_90CCW:
                return "Rotate -90";
            case FLIP_180:
                return "Rotate 180";
        }
        return "Flip";
    }
}