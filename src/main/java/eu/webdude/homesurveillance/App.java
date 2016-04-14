package eu.webdude.homesurveillance;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import eu.webdude.homesurveillance.motiondetection.MotionDetectionAlgorithm;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    private static RPiCamera piCamera;

    public static void main(String[] args) {
        try {
            startSurveillance(new MotionDetectionAlgorithm(10, 20));
        } catch (FailedToRunRaspistillException e) {
            logger.log(Level.SEVERE, "Unable to find the raspberryPy camera!");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void startSurveillance(MotionDetectionAlgorithm motionDetectionAlgorithm) throws FailedToRunRaspistillException, IOException, InterruptedException {
        logger.log(Level.INFO, "Starting camera");
        piCamera = new RPiCamera("/home/pi/Pictures/");
        piCamera.takeStill("bafMamu");
        BufferedImage image = piCamera.takeBufferedStill();

        while (true) {
            Thread.sleep(1000);
            BufferedImage imageTwo = piCamera.takeBufferedStill();
            boolean detected = motionDetectionAlgorithm.detect(image, imageTwo);
            if (detected) {
                logger.log(Level.WARNING, "Motion has been detected");
            }
            image = imageTwo;
        }
    }
}