package co.edu.uptc.presenter;

import co.edu.uptc.pojos.Airplane;

import java.util.List;

public interface AirplaneContract {
    interface Model{
        void setPresenter(Presenter presenter);
        void setAirplane(Airplane airplane);
        List<Airplane> getAirplanes();
        void pause();
        void resume();
        void terminate();
        void setAirplaneRoute(Airplane airplane);
        Airplane getAirplane(int x,int y);
        void start();
        int[] parseToIntArray(List<Integer> array);
        boolean isPaused();
        boolean isRunning();
        String getAirplanesLandedCount();
        String getAirplanesFlighting();
        String getTimeInRunning();
    }
    interface Presenter{
        void setModel(Model model);
        void setView(View view);
        void setAirplane(Airplane airplane);
        List<Airplane> getAirplanes();
        void pause();
        void resume();
        void terminate();
        void setAirplaneRoute(Airplane airplane);
        Airplane getAirplane(int x,int y);
        void notifyEndGame();
        void repaint();
        void start();
        int[] parseToIntArray(List<Integer> array);
        boolean isPaused();
        boolean isRunning();
        String getAirplanesLandedCount();
        String getAirplanesFlighting();
        String getTimeInRunning();
    }
    interface View{
        void setPresenter(Presenter presenter);
        void repaint();
        void notifyEndGame();
        void start();
    }
}
