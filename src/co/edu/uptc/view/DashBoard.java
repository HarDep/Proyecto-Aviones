package co.edu.uptc.view;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.presenter.AirplaneContract;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DashBoard extends JFrame implements AirplaneContract.View {
    AirplaneContract.Presenter presenter;
    private final PrincipalPanel principalPanel;

    public DashBoard() {
        putConfigs();
        principalPanel = new PrincipalPanel(this);
        add(principalPanel);
    }

    private void putConfigs(){
        setTitle(GlobalConfigs.TITLE);
        setSize(GlobalConfigs.TOTAL_DIMENSION);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addComponentListener(getComponentAdapter());
    }
    @Override
    public void setPresenter(AirplaneContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void paintAirplanes() {
        principalPanel.repaint();
    }

    @Override
    public void notifyEndGame() {
        principalPanel.isFirstPage = false;
        principalPanel.isStatisticsPage = true;
        principalPanel.clearActions();
        setResizable(true);
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

    private ComponentAdapter getComponentAdapter(){
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                GlobalConfigs.realFrameWidth = principalPanel.getWidth();
                GlobalConfigs.realFrameHeight = principalPanel.getHeight();
                repaint();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                terminateAll();
            }
        };
    }
}
