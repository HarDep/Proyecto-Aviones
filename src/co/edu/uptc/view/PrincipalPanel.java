package co.edu.uptc.view;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.pojos.Airplane;
import co.edu.uptc.pojos.AirplaneColor;
import co.edu.uptc.util.UtilImages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class PrincipalPanel extends JPanel {

    private int y1 = 150;
    private int y2 = 200;
    DashBoard dashBoard;
    boolean isFirstPage = true;
    Airplane auxAirplane1;
    Airplane auxAirplane2;
    boolean isSelectedAirplane = false;
    boolean isStatisticsPage = false;
    private boolean isInfoPage = false;
    List<Integer> posXs;
    List<Integer> posYs;
    DialogModifications dialogModifications;
    private JTextArea infoField;

    public PrincipalPanel(DashBoard dashBoard) {
        this.dashBoard = dashBoard;
        this.dialogModifications = new DialogModifications();
        posXs = new ArrayList<>();
        posYs = new ArrayList<>();
        setBackground(Color.WHITE);
        setLayout(null);
        setFocusable(true);
        addKeyListener(new KeyActions());
        putMouseConfigs();
        putInfoText();
    }

    private void putMouseConfigs(){
        MouseActions mouseActions = new MouseActions(this);
        addMouseListener(mouseActions);
        addMouseMotionListener(mouseActions);
    }

    private void putInfoText(){
        infoField = new JTextArea();
        infoField.setEditable(false);
        infoField.setVisible(false);
        add(infoField);
        infoField.setFont(new Font("TimesRoman", Font.BOLD, 15));
        infoField.setForeground(Color.black);
        infoField.setText(GlobalConfigs.INFO_TEXT);
        infoField.setBounds(50,125,850,400);
    }
    private void paintGame(Graphics2D graphics2D){
        graphics2D.setColor(Color.RED);
        graphics2D.drawImage(UtilImages.getInstanceAirstrip(),(GlobalConfigs.realFrameWidth / 2)
                - (GlobalConfigs.AIRSTRIP_WIDTH / 2), (GlobalConfigs.realFrameHeight / 2)
                - (GlobalConfigs.AIRSTRIP_HEIGHT / 2),null);
        if (posXs!=null){
            if (posXs.size()>0){
                int[] xs = dashBoard.presenter.parseToIntArray(new ArrayList<>(posXs));
                int[] ys = dashBoard.presenter.parseToIntArray(new ArrayList<>(posYs));
                graphics2D.drawPolyline(xs, ys, xs.length);
            }
        }
        int mid = GlobalConfigs.AIRPLANE_WIDTH / 2;
        AffineTransform or = graphics2D.getTransform();
        for (Airplane airplane: dashBoard.presenter.getAirplanes()) {
            graphics2D.setTransform(new AffineTransform());
            graphics2D.translate(airplane.getPosX(), airplane.getPosY());
            graphics2D.rotate(airplane.getAngle());
            graphics2D.drawImage(getImage(airplane.getAirplaneColor()), -mid,
                    -mid, null);
            graphics2D.setTransform(or);
            if (airplane.isEditedRoute()){
                int[] xs = dashBoard.presenter.parseToIntArray(new ArrayList<>(airplane.getxPositions()));
                int[] ys = dashBoard.presenter.parseToIntArray(new ArrayList<>(airplane.getyPositions()));
                graphics2D.drawPolyline(xs, ys, xs.length);
            }
        }
        graphics2D.setColor(Color.MAGENTA);
        graphics2D.drawString("Numero de aviones en vuelo: " + dashBoard.presenter.getAirplanesFlying(),
                20, 553);
        graphics2D.drawString("Numero de aviones aterrizados: " + dashBoard.presenter.getAirplanesLandedCount(),
                20,608);
    }

    private void paintFirstPage(Graphics2D graphics2D){
        graphics2D.drawImage(UtilImages.getInstanceInnitImage(GlobalConfigs.realFrameWidth,
                GlobalConfigs.realFrameHeight),0,0,null);
        graphics2D.setColor(Color.red);
        graphics2D.drawPolygon(new Polygon(new int[]{155,130,180},new int[]{y1,y2,y2},3));
        graphics2D.setColor(Color.white);
        graphics2D.setFont(new Font("TimesRoman", Font.BOLD, 50));
        graphics2D.drawString("Iniciar juego",200,200);
        graphics2D.drawString("Acerca del juego",200,300);
        graphics2D.drawString("Salir del juego",200,400);
    }
    private void paintStatisticsPage(Graphics2D graphics2D){
        graphics2D.drawImage(UtilImages.getInstanceChrashImage(GlobalConfigs.realFrameWidth,
                GlobalConfigs.realFrameHeight),0,0,null);
        graphics2D.setColor(Color.black);
        graphics2D.setFont(new Font("TimesRoman", Font.BOLD, 22));
        graphics2D.drawString("Se ha terminado el juego, algunos aviones se han estrellado", 100, 100);
        graphics2D.drawString("Volver al inicio",400,500);
        graphics2D.setColor(Color.GRAY);
        graphics2D.setFont(new Font("TimesRoman", Font.BOLD, 40));
        graphics2D.drawString("Numero de aviones aterrizados: " + dashBoard.presenter.getAirplanesLandedCount(),
                100,200);
        graphics2D.drawString("Tiempo obtenido: " + dashBoard.presenter.getTimeInRunning() + " segundos",
                100,300);
        graphics2D.drawString("Numero de aviones en vuelo: " + dashBoard.presenter.getAirplanesFlying(),
                100,400);
        graphics2D.setColor(Color.RED);
        graphics2D.drawPolygon(new Polygon(new int[]{369,358,380},new int[]{478,500,500},3));
    }

    private void paintInfoPage(Graphics2D graphics2D){
        graphics2D.setColor(Color.black);
        graphics2D.setFont(new Font("TimesRoman", Font.BOLD, 50));
        graphics2D.drawString("Juego Aviones Version 1",50,50);
        graphics2D.setFont(new Font("TimesRoman", Font.BOLD, 30));
        graphics2D.drawString("Autor: Harold Ricardo Alvarado Leandro",50,100);
        graphics2D.setFont(new Font("TimesRoman", Font.BOLD, 50));
        graphics2D.drawString("Volver atrás",200,575);
        graphics2D.setColor(Color.RED);
        graphics2D.drawPolygon(new Polygon(new int[]{155,130,180},new int[]{525,575,575},3));
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        if (isFirstPage)
            paintFirstPage(graphics2D);
        if (dashBoard.presenter.isRunning())
            paintGame(graphics2D);
        if (isStatisticsPage)
            paintStatisticsPage(graphics2D);
        if (isInfoPage)
            paintInfoPage(graphics2D);
    }

    private Image getImage(AirplaneColor color){
        Image image;
        switch (color) {
            case RED -> image = UtilImages.getInstanceAirplaneRed();
            case BLUE -> image = UtilImages.getInstanceAirplaneBlue();
            case GREEN -> image = UtilImages.getInstanceAirplaneGreen();
            case YELLOW -> image = UtilImages.getInstanceAirplaneYellow();
            default -> image = UtilImages.getInstanceAirplaneBlack();
        }
        return image;
    }

    public void changeParameters(){
        if (auxAirplane2!= null && dashBoard.presenter.isRunning()){
            dialogModifications.isReadyToEdit = false;
            dialogModifications.putAirplaneParameters(auxAirplane2);
            dialogModifications.setVisible(true);
            if (dialogModifications.isReadyToEdit){
                auxAirplane2.setAirplaneColor(dialogModifications.color);
                auxAirplane2.setSpeed(dialogModifications.speed);
                dashBoard.presenter.setAirplane(auxAirplane2);
            }
        }
    }

    public void terminateLeadAirplane(){
        if (posXs.size() > 0){
            auxAirplane1.setxPositions(new ArrayList<>(posXs));
            auxAirplane1.setyPositions(new ArrayList<>(posYs));
            dashBoard.presenter.setAirplaneRoute(auxAirplane1);
            posXs.clear();
            posYs.clear();
            isSelectedAirplane = false;
            auxAirplane1 = null;
        }
    }

    public void showFirstPage(){
        clearActions();
        isFirstPage = true;
        repaint();
    }
    private void moveTriangule(int value){
        y1+=value;
        y2+=value;
    }

    public void clearActions(){
        posXs.clear();
        posYs.clear();
        auxAirplane2 = null;
        isSelectedAirplane = false;
        auxAirplane1 = null;
    }

    class KeyActions extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (isFirstPage){
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    if (y1 != 350){
                        moveTriangule(100);
                        repaint();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    if (y1 != 150){
                        moveTriangule(-100);
                        repaint();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    if (y1 == 150){
                        isFirstPage = false;
                        clearActions();
                        dashBoard.presenter.start();
                        dashBoard.setResizable(false);
                        repaint();
                    }
                    if (y1 == 250){
                        isFirstPage = false;
                        isInfoPage = true;
                        infoField.setVisible(true);
                        repaint();
                    }
                    if (y1 == 350)
                        dashBoard.terminateAll();
                }
            }else if (isStatisticsPage){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    isFirstPage = true;
                    isStatisticsPage = false;
                    repaint();
                }
            }else if (isInfoPage){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    isFirstPage = true;
                    infoField.setVisible(false);
                    isInfoPage = false;
                    repaint();
                }
            }
        }

    }
}
