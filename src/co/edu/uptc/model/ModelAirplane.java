package co.edu.uptc.model;

import co.edu.uptc.pojos.Airplane;
import co.edu.uptc.presenter.AirplaneContract;

import java.util.List;

public class ModelAirplane implements AirplaneContract.Model {
    AirplaneContract.Presenter presenter;
    private ManagerAirplanes managerAirplanes;
    private boolean isFirstStart = true;

    public ModelAirplane() {
        this.managerAirplanes = new ManagerAirplanes(this);
    }

    @Override
    public void setPresenter(AirplaneContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setAirplane(Airplane airplane) {
        managerAirplanes.setAirplane(airplane);
    }

    @Override
    public List<Airplane> getAirplanes() {
        return managerAirplanes.getAirplanes();
    }

    @Override
    public void pause() {
        managerAirplanes.pause();
    }

    @Override
    public void resume() {
        managerAirplanes.resuming();
    }

    @Override
    public void terminate() {
        managerAirplanes.terminateAll();
    }

    @Override
    public void setAirplaneRoute(Airplane airplane) {
        managerAirplanes.setAirplaneRoute(airplane);
    }

    @Override
    public Airplane getAirplane(int x, int y) {
        return managerAirplanes.getAirplane(x, y);
    }

    @Override
    public void start() {
        if (!isFirstStart)
            this.managerAirplanes = new ManagerAirplanes(this);
        managerAirplanes.isRunning = true;
        managerAirplanes.start();
        isFirstStart = false;
    }

    @Override
    public int[] parseToIntArray(List<Integer> array) {
        int[] arr = new int[array.size()];
        int count = 0;
        synchronized (array){ // porque concurrencia?
            //podria intentar hacer dos metodos uno con avion y este para ver en cual es que ocurre la excepcion
            for (Integer num:array) {
                if (num!=null)
                    arr[count] = num;
                count++;
            }
        }
        return arr;
    }

    @Override
    public boolean isPaused() {
        return managerAirplanes.isPaused;
    }

    @Override
    public boolean isRunning() {
        return managerAirplanes.isRunning;
    }

    @Override
    public String getAirplanesLandedCount() {
        return managerAirplanes.getAirplanesLandedCount();
    }

    @Override
    public String getAirplanesFlying() {
        return managerAirplanes.getAirplanesFlying();
    }

    @Override
    public String getTimeInRunning() {
        return managerAirplanes.getTimeInRunning();
    }
}
