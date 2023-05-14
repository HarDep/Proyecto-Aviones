package co.edu.uptc.configs;

import java.awt.*;

public class GlobalConfigs {
    public static final String BLUE_AIRPLANE_PATH = "resources/assets/imgBu.png";
    public static final String RED_AIRPLANE_PATH = "resources/assets/imgR.png";
    public static final String YELLOW_AIRPLANE_PATH = "resources/assets/imgY.png";
    public static final String GREEN_AIRPLANE_PATH = "resources/assets/imgG.png";
    public static final String BLACK_AIRPLANE_PATH = "resources/assets/imgBl.png";
    public static final Dimension TOTAL_DIMENSION = new Dimension(1000,700);
    public static int realFrameWidth = 1000;
    public static int realFrameHeight = 700;
    public static final int AIRPLANE_WIDTH = 40;
    public static final int AIRPLANE_HEIGHT = 40;
    public static final int AIRSTRIP_WIDTH = 450;
    public static final int AIRSTRIP_HEIGHT = 50;
    public static final double SPEED_LIMIT = 5;
    public static final String TITLE = "Airplanes";
    public static final String INFO_TEXT = """
                Lugar de llegada a la pista:\s
                Para poder aterrizar un avión en la pista se debe ingresar el avión en la zona derecha de la
                pista (zona que esta marcada con el numero 60), solo esta zona esta habilitada para el
                aterrizaje del avión, el aterrizaje se debe de hacer dentro del area que se remarca con un
                rectángulo rojo direccionando el movimiento únicamente desde el lado derecho del rectángulo
                rojo hacia el interior de la pista, si se hace de otra manera no se registrara el aterrizaje.
                
                Acciones del juego:
                ◉ Para cambiar la ruta del avión se debe seleccionar el avión con un click izquierdo, luego
                se describe la ruta deseada a través del movimiento del mouse, para terminar de dirigir
                el avión se da otro click izquierdo.
                ◉ Para modificar la velocidad o el color del avión se debe dar un click derecho sobre el
                correspondiente avión, en seguida saldrá un popup para poder seleccionar la opción de
                modificar el avión el cual al seleccionarlo desplegara un cuadro de dialogo en el cual
                se podrá modificar estos atributos del avión.
                ◉ Para pausar, continuar o terminar el juego se debe dar un click derecho fuera de cualquier
                avión, en seguida saldrá un popup con las anteriores opciones, cabe aclarar que se puede
                pausar si el juego esta corriendo, y continuar si el juego esta pausado, en el caso de
                seleccionar la opción de terminar el juego saldrá un cuadro de dialogo para confirmar la
                acción de terminar el juego.""";
}
