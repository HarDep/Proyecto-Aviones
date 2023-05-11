package co.edu.uptc.util;

import co.edu.uptc.configs.GlobalConfigs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class UtilImages {
    private static Image instanceAirplaneBlue;
    private static Image instanceAirplaneBlack;
    private static Image instanceAirplaneRed;
    private static Image instanceAirplaneGreen;
    private static Image instanceAirplaneYellow;
    private static Image instanceAirstrip;
    private static Image instanceInnitImage;
    private static Image instanceChrashImage;

    public static Image getInstanceChrashImage(int w, int h){
        try {
            instanceChrashImage = ImageIO.read(new File("resources/assets/choque.png")
            ).getScaledInstance(w, h,0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return instanceChrashImage;
    }

    public static Image getInstanceInnitImage(int w, int h){
        try {
            instanceInnitImage = ImageIO.read(new File("resources/assets/inicio.png")
            ).getScaledInstance(w, h,0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return instanceInnitImage;
    }

    public static Image getInstanceAirstrip(){
        if (instanceAirstrip == null){
            try {
                instanceAirstrip = ImageIO.read(new File("resources/assets/airstrip.png")
                ).getScaledInstance(GlobalConfigs.AIRSTRIP_WIDTH, GlobalConfigs.AIRSTRIP_HEIGHT,0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return instanceAirstrip;
    }
    public static Image getInstanceAirplaneBlue(){
        if (instanceAirplaneBlue == null){
            try {
                instanceAirplaneBlue = ImageIO.read(new File(GlobalConfigs.BLUE_AIRPLANE_PATH)
                ).getScaledInstance(GlobalConfigs.AIRPLANE_WIDTH,GlobalConfigs.AIRPLANE_HEIGHT,0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return instanceAirplaneBlue;
    }
    public static Image getInstanceAirplaneBlack(){
        if (instanceAirplaneBlack == null){
            try {
                instanceAirplaneBlack = ImageIO.read(new File(GlobalConfigs.BLACK_AIRPLANE_PATH)
                ).getScaledInstance(GlobalConfigs.AIRPLANE_WIDTH,GlobalConfigs.AIRPLANE_HEIGHT,0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return instanceAirplaneBlack;
    }
    public static Image getInstanceAirplaneRed(){
        if (instanceAirplaneRed == null){
            try {
                instanceAirplaneRed = ImageIO.read(new File(GlobalConfigs.RED_AIRPLANE_PATH)
                ).getScaledInstance(GlobalConfigs.AIRPLANE_WIDTH,GlobalConfigs.AIRPLANE_HEIGHT,0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return instanceAirplaneRed;
    }
    public static Image getInstanceAirplaneGreen(){
        if (instanceAirplaneGreen == null){
            try {
                instanceAirplaneGreen = ImageIO.read(new File(GlobalConfigs.GREEN_AIRPLANE_PATH)
                ).getScaledInstance(GlobalConfigs.AIRPLANE_WIDTH,GlobalConfigs.AIRPLANE_HEIGHT,0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return instanceAirplaneGreen;
    }
    public static Image getInstanceAirplaneYellow(){
        if (instanceAirplaneYellow == null){
            try {
                instanceAirplaneYellow = ImageIO.read(new File(GlobalConfigs.YELLOW_AIRPLANE_PATH)
                ).getScaledInstance(GlobalConfigs.AIRPLANE_WIDTH,GlobalConfigs.AIRPLANE_HEIGHT,0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return instanceAirplaneYellow;
    }
}
