package co.edu.uptc.model;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.pojos.Airplane;

import java.util.List;

public class Pilot extends Thread {
    private final Airplane airplane;
    private int delay;
    private boolean isRunning = true;
    private boolean isTerminate = false;
    private boolean isPaused = false;
    private final ManagerAirplanes managerAirplanes;
    private boolean isToUp = true;

    public Pilot(Airplane airplane,ManagerAirplanes managerAirplanes) {
        this.airplane = airplane;
        this.managerAirplanes = managerAirplanes;
        List<Integer> xs= airplane.getxPositions();
        List<Integer> ys = airplane.getyPositions();
        airplane.setPosX(xs.get(0));
        airplane.setPosY(ys.get(0));
        xs.remove(0);
        ys.remove(0);
        putOrientation(xs,ys);
        updateDelay();
    }

    private void putOrientation(List<Integer> xs, List<Integer> ys) {
        int difX = airplane.getPosX() - xs.get(0);
        int difY = airplane.getPosY() - ys.get(0);
        airplane.setAngle(Math.atan2(difY,difX) + (Math.PI/2));
    }

    public void updateDelay(){
        delay = (int) (1000 / airplane.getSpeed());
    }

    public void run(){
        while (isRunning){
            try {
                while (isPaused){
                    sleep(300);
                    if (isTerminate) break;
                }
                move();
                checkCrash();
                checkFinish();
                sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void move(){
        synchronized (airplane){
            List<Integer> xs= airplane.getxPositions();
            List<Integer> ys = airplane.getyPositions();
            checkPositions(xs,ys);
            airplane.setPosX(xs.get(0));
            airplane.setPosY(ys.get(0));
            if (xs.size() != 1){
                xs.remove(0);
                ys.remove(0);
            } else
                regenerateRoute(xs, ys);
            putOrientation(xs,ys);
        }
    }

    private void regenerateRoute(List<Integer> xs, List<Integer> ys) {
        if (airplane.isEditedRoute())
            airplane.setEditedRoute(false);
        if (isToUp && airplane.getPosY() > 0 && airplane.getPosX() > 0){
            xs.set(0,airplane.getPosX()-1);
            ys.set(0, airplane.getPosY()-1);
        }else if (airplane.getPosY() < GlobalConfigs.realFrameHeight && airplane.getPosX() < GlobalConfigs.FRAME_WIDTH){
            xs.set(0,airplane.getPosX()+1);
            ys.set(0, airplane.getPosY()+1);
            isToUp = false;
        }else{
            isToUp = true;
            regenerateRoute(xs, ys);
        }
    }

    private void checkPositions(List<Integer> xs, List<Integer> ys) {
        if (airplane.isEditedRoute() && xs.size() != 1){
            while (airplane.getPosX() == xs.get(0) && airplane.getPosY() == ys.get(0)){
                xs.remove(0);
                ys.remove(0);
                if(xs.size() == 1){
                    break;
                }
            }
            if (xs.size() != 1){
                int difX = airplane.getPosX() - xs.get(0);
                int difY = airplane.getPosY() - ys.get(0);
                if (Math.abs(difY) > 1 || Math.abs(difX) > 1){
                    xs.add(0, difX > 0 ? (airplane.getPosX() - 1) :
                            (difX == 0 ? (airplane.getPosX()): (airplane.getPosX() + 1)) );
                    ys.add(0, difY > 0 ? (airplane.getPosY() - 1) :
                            (difY == 0 ? (airplane.getPosY()): (airplane.getPosY() + 1)) );
                }
            }
        }
    }

    private void checkFinish() {
        int yMin = (GlobalConfigs.realFrameHeight / 2) - (GlobalConfigs.AIRSTRIP_HEIGHT / 2);
        int xTotal = (GlobalConfigs.FRAME_WIDTH / 2) + (GlobalConfigs.AIRSTRIP_WIDTH / 2);
        int yMax = (GlobalConfigs.realFrameHeight / 2) + (GlobalConfigs.AIRSTRIP_HEIGHT / 2);
        int x = airplane.getPosX() + (GlobalConfigs.AIRPLANE_WIDTH / 2);
        if (x == xTotal && airplane.getPosY() > yMin && airplane.getPosY() < yMax && (!isTerminate)){
            synchronized (managerAirplanes.airplanes){
                managerAirplanes.airplanes.remove(airplane.getId(),airplane);
                managerAirplanes.pilots.remove(airplane.getId(),this);
            }
            terminateAll();
            managerAirplanes.addOneFinishedAirplanes();
        }
    }

    private void checkCrash() {
        for (Airplane airplane1: managerAirplanes.airplanes.values()) {//TODO verificar si se tocan realmente
            synchronized (airplane1){
                //como circulo tomando el ancho como el diametro
                /*int difX = airplane.getPosX() - airplane1.getPosX();
                int difY = airplane.getPosY() - airplane1.getPosY();
                if (Math.hypot(difX,difY) < GlobalConfigs.AIRPLANE_WIDTH
                        && airplane.getId() != airplane1.getId()){
                    managerAirplanes.terminateAll();
                    managerAirplanes.model.presenter.notifyEndGame();
                }*/
                //como circulo tomando como diametro la hipotenusa medios
                double newDistance = Math.hypot(GlobalConfigs.AIRPLANE_HEIGHT,GlobalConfigs.AIRPLANE_WIDTH) / 2;
                int difX = airplane.getPosX() - airplane1.getPosX();
                int difY = airplane.getPosY() - airplane1.getPosY();
                if (Math.hypot(difX,difY) < newDistance
                        && airplane.getId() != airplane1.getId()){
                    managerAirplanes.terminateAll();
                    managerAirplanes.model.presenter.notifyEndGame();
                }
            }
        }
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
}
