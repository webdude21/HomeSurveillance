package eu.webdude.homesurveillance;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;

public class App {

    public static void main(String[] args) {
        try {
            RPiCamera piCamera = new RPiCamera("/home/pi/Pictures");
        } catch (FailedToRunRaspistillException e) {
            e.printStackTrace();
        }
        System.out.println("Testing Raspberry camera module");
    }
}