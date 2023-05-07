package co.edu.uptc.view;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.presenter.AirplaneContract;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DashBoard extends JFrame implements AirplaneContract.View {
    AirplaneContract.Presenter presenter;
    private PrincipalPanel principalPanel;

    public DashBoard() {
        putConfigs();
        principalPanel = new PrincipalPanel(this);
        add(principalPanel);
    }

    private void putConfigs(){
        setTitle(GlobalConfigs.TITLE);
        setSize(GlobalConfigs.TOTAL_DIMENSION);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                terminateAll();
            }
        });
    }
    @Override
    public void setPresenter(AirplaneContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void notifyEndGame() {
        principalPanel.isFirstPage = false;
        principalPanel.isStatisticsPage = true;
        principalPanel.clearActions();
        principalPanel.repaint();
    }

    public void terminateAll(){
        if (presenter.isRunning()){
            presenter.terminate();
        }
        System.exit(0);
    }

    @Override
    public void start() {
        this.setVisible(true);
        GlobalConfigs.realFrameHeight = principalPanel.getHeight();
    }
}
