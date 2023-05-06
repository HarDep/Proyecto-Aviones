package co.edu.uptc.view;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.pojos.AirplaneColor;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DialogModifications extends JDialog {
    boolean isReadyToEdit;
    AirplaneColor color;
    double speed;
    private JComboBox<String> colorType;
    private JTextField speedVal;

    public DialogModifications() {
        setModal(true);
        setSize(420, 280);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(false);
        setLayout(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        createComponents();
    }

    private void createComponents() {
        JLabel messages = new JLabel("Cambiar valores del avión");
        messages.setBounds(40,20,340,30);
        add(messages);
        JLabel labColor = new JLabel("Seleccione el color");
        labColor.setBounds(40,70,150,30);
        add(labColor);
        colorType = new JComboBox<>(new String[]{"Azul","Verde","Rojo","Amarillo","Negro"});
        colorType.setBounds(230,70,150,30);
        add(colorType);
        JLabel labSpeed = new JLabel("Ingrese la velocidad");
        labSpeed.setBounds(40,120,150,30);
        add(labSpeed);
        speedVal = new JTextField("1.0");
        speedVal.setBounds(230,120,150,30);
        add(speedVal);
        speedVal.addKeyListener(getCheckKey());
        JButton change = new JButton("Cambiar");
        change.setBounds(40,190,150,30);
        add(change);
        JButton cancel = new JButton("Cancelar");
        cancel.setBounds(230,190,150,30);
        add(cancel);
        change.addActionListener(e -> doChange());
        cancel.addActionListener(e -> this.setVisible(false));
    }

    private void doChange() {
        try {
            speed = Double.parseDouble(speedVal.getText());
            if (speed >= 6 || speed < 1){
                JOptionPane.showMessageDialog(null,"La velocidad debe estar entre 1 y 6," +
                        " y ser menor que 6");
                speedVal.setText("1.0");
            }else {
                switch (colorType.getSelectedIndex()) {
                    case 0 -> color = AirplaneColor.BLUE;
                    case 1 -> color = AirplaneColor.GREEN;
                    case 2 -> color = AirplaneColor.RED;
                    case 3 -> color = AirplaneColor.YELLOW;
                    case 4 -> color = AirplaneColor.BLACK;
                }
                this.setVisible(false);
                isReadyToEdit = true;
            }
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Entrada incorrecta");
            speedVal.setText("1.0");
        }
    }
    public KeyAdapter getCheckKey(){
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
