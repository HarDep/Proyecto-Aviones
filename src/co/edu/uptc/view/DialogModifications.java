package co.edu.uptc.view;

import co.edu.uptc.pojos.Airplane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DialogModifications extends JDialog {
    boolean isReadyToEdit;
    int colorNumber;
    double speed;
    private JComboBox<String> colorType;
    private JTextField speedVal;

    public DialogModifications() {
        putConfigs();
        createComponents();
    }
    private void putConfigs(){
        setModal(true);
        setSize(420, 180);
        setResizable(false);
        setTitle("Cambiar características de avión");
        setLocationRelativeTo(null);
        setVisible(false);
        setLayout(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void createComponents() {
        addLabel(new JLabel("Seleccione el color"),0);
        colorType = new JComboBox<>(new String[]{"Rojo","Azul","Verde","Amarillo","Negro"});
        putBounds(colorType,0,1);
        add(colorType);
        addLabel(new JLabel("Ingrese la velocidad"),1);
        speedVal = new JTextField("1.0");
        putBounds(speedVal,1,1);
        add(speedVal);
        speedVal.addKeyListener(getCheckKey());
        JButton change = new JButton("Cambiar");
        putBounds(change,2,0);
        add(change);
        JButton cancel = new JButton("Cancelar");
        putBounds(cancel,2,1);
        add(cancel);
        change.addActionListener(e -> doChange());
        cancel.addActionListener(e -> this.setVisible(false));
    }

    private void putBounds(Component component, int row, int column){
        component.setBounds(40 + column * 190, 20 + row * 40,150,30);
    }
    private void addLabel(JLabel component, int row){
        component.setBounds(40, 20 + row * 40,150,30);
        add(component);
    }

    public void putAirplaneParameters(Airplane airplane){
        colorType.setSelectedIndex(airplane.getColorNumber());
        speedVal.setText(String.valueOf(airplane.getSpeed()));
    }

    private void doChange() {
        try {
            speed = Double.parseDouble(speedVal.getText());
            if (speed >= 6 || speed < 1){
                JOptionPane.showMessageDialog(null,"La velocidad debe estar entre 1 y 6," +
                        " y ser menor que 6");
                speedVal.setText("1.0");
            }else {
                colorNumber = colorType.getSelectedIndex();
                this.setVisible(false);
                isReadyToEdit = true;
            }
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Entrada incorrecta");
            speedVal.setText("1.0");
        }
    }
    private KeyAdapter getCheckKey(){
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())){
                    e.consume();
                }
            }
        };
    }
}
