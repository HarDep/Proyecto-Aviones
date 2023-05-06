package co.edu.uptc.view;


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseActions extends MouseAdapter {//verificar cuando se termina terminar todos los procesos
    private JPopupMenu popupSelected;
    private JPopupMenu popupNotSelected;
    private JMenuItem pause;
    private JMenuItem resume;
    private boolean isReadyEdit=false;
    private PrincipalPanel principalPanel;

    public MouseActions(PrincipalPanel principalPanel) {
        this.principalPanel = principalPanel;
        createPopupNotSelected();
        createPopupSelected();
    }

    private void createPopupSelected() {
        popupSelected = new JPopupMenu();
        addActionChangeAirplaneParameters();
    }

    private void addActionChangeAirplaneParameters() {
        JMenuItem changeParameters = new JMenuItem("Cambiar caracteristicas del avión");
        popupSelected.add(changeParameters);
        changeParameters.addActionListener(e -> principalPanel.changeParameters());
    }

    private void createPopupNotSelected() {
        popupNotSelected = new JPopupMenu();
        addActionPauseGame();
        addActionResumeGame();
        addActionTerminateGame();
    }

    private void addActionTerminateGame() {
        JMenuItem terminate = new JMenuItem("Terminar el juego");
        popupNotSelected.add(terminate);
        terminate.addActionListener(e -> {
            if (principalPanel.dashBoard.presenter.isRunning()){
                principalPanel.dashBoard.presenter.terminate();
                principalPanel.showFirstPage();
            }
        });
    }

    private void addActionResumeGame() {
        resume = new JMenuItem("Continuar el juego");
        popupNotSelected.add(resume);
        resume.addActionListener(e -> {
            if (principalPanel.dashBoard.presenter.isRunning()){
                principalPanel.dashBoard.presenter.resume();
                resume.setEnabled(false);
                pause.setEnabled(true);
            }
        });
        resume.setEnabled(false);
    }

    private void addActionPauseGame() {
        pause = new JMenuItem("Pausar el juego");
        popupNotSelected.add(pause);
        pause.addActionListener(e -> {
            if (principalPanel.dashBoard.presenter.isRunning()){
                principalPanel.dashBoard.presenter.pause();
                resume.setEnabled(true);
                pause.setEnabled(false);
                principalPanel.clearActions();
            }
        });
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (principalPanel.isSelectedAirplane && (!principalPanel.dashBoard.presenter.isPaused())){
            principalPanel.posXs.add(e.getX());
            principalPanel.posYs.add(e.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (principalPanel.dashBoard.presenter.isRunning()){
            if (e.getButton() == MouseEvent.BUTTON3){
                isReadyEdit = false;
                principalPanel.auxAirplane2 = principalPanel.dashBoard.presenter.getAirplane(e.getX(),e.getY());
                if (principalPanel.auxAirplane2 != null)
                    isReadyEdit = true;
                if (isReadyEdit && (!principalPanel.dashBoard.presenter.isPaused()))
                    popupSelected.show(principalPanel, e.getX(), e.getY());
                else
                    popupNotSelected.show(principalPanel, e.getX(), e.getY());
            }else {
                if (!principalPanel.dashBoard.presenter.isPaused()){
                    if (principalPanel.isSelectedAirplane)
                        principalPanel.terminateLeadAirplane();
                    principalPanel.auxAirplane1 = principalPanel.dashBoard.presenter.getAirplane(e.getX(),e.getY());
                    if (principalPanel.auxAirplane1 != null)
                        principalPanel.isSelectedAirplane = true;
                }
            }
        }
    }

}
