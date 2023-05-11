package co.edu.uptc.presenter;

import co.edu.uptc.pojos.Airplane;

import java.util.List;

public class PresenterAirplane implements AirplaneContract.Presenter{
    private AirplaneContract.View view;
    private AirplaneContract.Model model;
    @Override
    public void setModel(AirplaneContract.Model model) {
        this.model = model;
    }

    @Override
    public void setView(AirplaneContract.View view) {
        this.view = view;
    }

    @Override
    public void setAirplane(Airplane airplane) {
        model.setAirplane(airplane);
    }

    @Override
    public List<Airplane> getAirplanes() {
        return model.getAirplanes();
    }

    @Override
    public void pause() {
        model.pause();
    }

    @Override
    public void resume() {
        model.resume();
    }

    @Override
    public void terminate() {
        model.terminate();
    }

    @Override
    public void setAirplaneRoute(Airplane airplane) {
        model.setAirplaneRoute(airplane);
    }

    @Override
    public Airplane getAirplane(int x, int y) {
        return model.getAirplane(x, y);
    }

    @Override
    public void notifyEndGame() {
        view.notifyEndGame();
    }

    @Override
    public void paintAirplanes() {
        view.paintAirplanes();
    }

    @Override
    public void start() {
        model.start();
    }

    @Override
    public int[] parseToIntArray(List<Integer> array) {
        return model.parseToIntArray(array);
    }

    @Override
    public boolean isPaused() {
        return model.isPaused();
    }

    @Override
    public boolean isRunning() {
        return model.isRunning();
    }

    @Override
    public String getAirplanesLandedCount() {
        return model.getAirplanesLandedCount();
    }

    @Override
    public String getAirplanesFlying() {
        return model.getAirplanesFlying();
    }

    @Override
    public String getTimeInRunning() {
        return model.getTimeInRunning();
    }
}
