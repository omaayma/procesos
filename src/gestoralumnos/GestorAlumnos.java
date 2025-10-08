package gestoralumnos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestorAlumnos extends JFrame implements ActionListener {
    private JLabel etiqueta;
    private JTextField campo;
    private JButton boton;

    public GestorAlumnos(){
        super();
        configurarGUI();
        inicializarComponentes();
    }

    private void configurarGUI(){
        this.setTitle("Gestor de alumnos");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false); // no puede hacerse más pequeña la ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void inicializarComponentes() {
        etiqueta = new JLabel();
        campo = new JTextField();
        boton = new JButton();

        etiqueta.setText("<html><B><I>Añadir Alumno</I></B></html>");
        etiqueta.setBounds(50, 100, 200, 20);

        campo.setBounds(50, 200, 200, 20);

        boton.setText("PULSAR");
        boton.setBounds(100, 300, 100, 20);
        boton.addActionListener(this);
        boton.setActionCommand("BUSCAR");

        this.add(etiqueta);
        this.add(campo);
        this.add(boton);

        JPanel panel = (JPanel) this.getContentPane();
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent evento){
        String comando = evento.getActionCommand();

        if(comando.equals("BUSCAR")){
            String texto = campo.getText();
            JOptionPane.showMessageDialog(this, "Botón pulsado. Texto: " + texto);
            JOptionPane.showConfirmDialog(this, "Selecciona");
        }
    }

    public static void main(String[] args) {
        new GestorAlumnos();
    }
}
