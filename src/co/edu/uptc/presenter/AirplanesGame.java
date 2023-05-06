package co.edu.uptc.presenter;

import co.edu.uptc.model.ModelAirplane;
import co.edu.uptc.view.DashBoard;

public class AirplanesGame {
    private AirplaneContract.Model model;
    private AirplaneContract.Presenter presenter;
    private AirplaneContract.View view;

    public AirplanesGame() {
        model = new ModelAirplane();
        presenter = new PresenterAirplane();
        view = new DashBoard();
        createMVP();
    }

    private void createMVP() {
        model.setPresenter(presenter);
        presenter.setModel(model);
        presenter.setView(view);
        view.setPresenter(presenter);
    }
    public void start(){
        view.start();
    }
}
