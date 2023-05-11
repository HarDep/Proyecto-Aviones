package co.edu.uptc.model;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.pojos.Airplane;
import co.edu.uptc.pojos.AirplaneColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerAirplanes extends Thread{
    private int finishedAirplanesCount = 0;
    final Map<Integer,Airplane> airplanes;
    Map<Integer,Pilot> pilots;
    ModelAirplane model;
    boolean isRunning = false;
    boolean isTerminate = false;
    boolean isPaused = false;
    private int count = 0;
    private long innitTime = 0;
    private int idCount = 0;

    public ManagerAirplanes(ModelAirplane model) {
        this.model = model;
        this.airplanes = new HashMap<>();
        this.pilots = new HashMap<>();
    }
    public void addOneFinishedAirplanes(){
        finishedAirplanesCount++;
    }

    public void run(){
        innitTime = System.currentTimeMillis();
        while (isRunning){
            try {
                while (isPaused){
                    sleep(300);
                    if (isTerminate) break;
                }
                count++;
                if ((count == 70 && airplanes.size() < 15) || airplanes.size() == 0)
                    createAirplane();
                if (count == 70)
                    count = 0;
                repaint();
                sleep(150);
                if (isTerminate) break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void repaint(){
        model.presenter.paintAirplanes();
    }

    public List<Airplane> getAirplanes() {
        return getCloneAirplanes();
    }

    private List<Airplane> getCloneAirplanes(){
        List<Airplane> clone = new ArrayList<>();
        synchronized (airplanes){
            for (Airplane airplane:airplanes.values()) {
                Pilot pilot = pilots.get(airplane.getId());
                synchronized (pilot.airplane){
                    clone.add(airplane.clone());
                }
            }
        }
        return clone;
    }

    public void createAirplane(){
        Integer[] innitPosition = generateInnitPosition();
        List<Integer> xs = generateDefaultPositions(innitPosition[0], GlobalConfigs.FRAME_WIDTH);
        List<Integer> ys = generateDefaultPositions(innitPosition[1], GlobalConfigs.realFrameHeight);
        equalizeSize(xs,ys);
        AirplaneColor color = generateColor();
        double speed = (Math.random() * GlobalConfigs.SPEED_LIMIT) + 1;
        Airplane airplane = new Airplane(idCount, ys,xs, speed, color);
        if (checkAirplaneOverlap(airplane)){
            Pilot pilot = new Pilot(airplane, this);
            airplanes.put(airplane.getId(), airplane);
            pilots.put(airplane.getId(), pilot);
            pilot.start();
            idCount++;
        }else
            createAirplane();
    }

    private boolean checkAirplaneOverlap(Airplane airplane1){
        for (int i = 0; i < airplanes.size(); i++) {
            if (airplanes.containsKey(i)){
                Airplane airplane = airplanes.get(i);
                int difX = airplane.getPosX() - airplane1.getPosX();
                int difY = airplane.getPosY() - airplane1.getPosY();
                if (Math.hypot(difX,difY) < (1.5 * GlobalConfigs.AIRPLANE_WIDTH))
                    return false;
            }
        }
        return true;
    }

    private void equalizeSize(List<Integer> xs, List<Integer> ys) {
        if (xs.size() < ys.size()){
            int toAdd = xs.get(xs.size()-1);
            int dif = ys.size() - xs.size();
            for (int i = dif; i > 0; i--) {
                xs.add(toAdd);
            }
        }else {
            int toAdd = ys.get(ys.size()-1);
            int dif = xs.size() - ys.size();
            for (int i = dif; i > 0; i--) {
                ys.add(toAdd);
            }
        }
    }

    private Integer[] generateInnitPosition() {
        Integer[] pos;
        int yLimit = GlobalConfigs.realFrameHeight - GlobalConfigs.AIRPLANE_HEIGHT;
        int xLimit = GlobalConfigs.FRAME_WIDTH - GlobalConfigs.AIRPLANE_WIDTH;
        int mid = GlobalConfigs.AIRPLANE_WIDTH / 2;
        switch ((int) (Math.random()*4)){
            case 0 -> pos = new Integer[]{mid,(int) (Math.random()*yLimit) + mid};
            case 1 -> pos = new Integer[]{(int) (Math.random()*xLimit) + mid, mid};
            case 2 -> pos = new Integer[]{(int) (Math.random()*xLimit) + mid,yLimit + mid};
            default -> pos = new Integer[]{xLimit + mid,(int) (Math.random()*yLimit) + mid};
        }
        return pos;
    }

    public List<Integer> generateDefaultPositions(int innitPos, int axisSize){
        List<Integer> positions = new ArrayList<>();
        positions.add(innitPos);
        int middlePosition = axisSize / 2;
        if (innitPos < middlePosition){
            for (int i = innitPos + 1; i < middlePosition ; i++) {
                positions.add(i);
            }
        }else {
            for (int i = innitPos - 1; i > middlePosition ; i--) {
                positions.add(i);
            }
        }
        if (innitPos!=middlePosition)
            positions.add(middlePosition);
        return positions;
    }

    private AirplaneColor generateColor() {
        AirplaneColor color;
        switch ((int) (Math.random()*5)) {
            case 0 -> color = AirplaneColor.RED;
            case 1 -> color = AirplaneColor.BLUE;
            case 2 -> color = AirplaneColor.GREEN;
            case 3 -> color = AirplaneColor.YELLOW;
            default -> color = AirplaneColor.BLACK;
        }
        return color;
    }
    public void setAirplane(Airplane airplane){
        Airplane toSet = airplanes.get(airplane.getId());
        if (toSet != null){
            toSet.setAirplaneColor(airplane.getAirplaneColor());
            toSet.setSpeed(airplane.getSpeed());
            pilots.get(toSet.getId()).updateDelay();
        }
    }
    public void setAirplaneRoute(Airplane airplane){
        Pilot pilot = pilots.get(airplane.getId());
        Airplane toSet = airplanes.get(airplane.getId());
        if (toSet != null){
            synchronized (pilot.airplane){
                toSet.setxPositions(new ArrayList<>(airplane.getxPositions()));
                toSet.setyPositions(new ArrayList<>(airplane.getyPositions()));
                toSet.setEditedRoute(true);
            }
        }
    }
    public Airplane getAirplane(int x, int y){
        int mid = GlobalConfigs.AIRPLANE_WIDTH / 2;
        for (Airplane airplane: airplanes.values()) {
            int difX = x - (airplane.getPosX() - mid);
            int difY = y - (airplane.getPosY() - mid);
            if (difX < GlobalConfigs.AIRPLANE_WIDTH && difX > -1 && difY < GlobalConfigs.AIRPLANE_HEIGHT && difY > -1)
                return airplane.clone();
        }
        return null;
    }

    public void terminateAll(){
        isTerminate = true;
        isRunning = false;
    }
    public void pause(){
        isPaused = true;
    }
    public void resuming(){
        isPaused = false;
    }
    public int getAirplanesLandedCount(){
        return finishedAirplanesCount;
    }

    public int getAirplanesFlying(){
        return airplanes.size();
    }

    public int getTimeInRunning(){
        return (int) ((System.currentTimeMillis() - innitTime) / 1000);
    }

}
