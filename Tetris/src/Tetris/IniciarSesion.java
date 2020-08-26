package Tetris;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Persistencia.*;

public class IniciarSesion extends JFrame implements ActionListener {

    private String userName, password;
    private JButton iniciarsesion;
    private JPanel contentPane;
    private JTextField nombre; 
    private JPasswordField contraseņa;
    private Tetris tetris = new Tetris();
    private Persistencia datos;

    public IniciarSesion() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setMinimumSize(new Dimension(450, 325));
		this.setTitle("Iniciar Sesion");
		
		JLabel name = new JLabel("Nombre");
		name.setBounds(10, 70, 100, 14);
		contentPane.add(name);
		
		JLabel pass = new JLabel("Contraseņa");
		pass.setBounds(10, 113, 100, 14);
		contentPane.add(pass);
		
		nombre = new JTextField();
		nombre.setBounds(126, 67, 240, 20);
		contentPane.add(nombre);
		nombre.setColumns(10);
		
		contraseņa = new JPasswordField();
		contraseņa.setBounds(126, 110, 240, 20);
		contentPane.add(contraseņa);
		contraseņa.setColumns(10);
		
		iniciarsesion = new JButton("Iniciar Sesion");
		iniciarsesion.setBounds(165, 183, 144, 50);
		contentPane.add(iniciarsesion);

        iniciarsesion.addActionListener(this);

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == iniciarsesion) {
    		datos = new Persistencia (nombre.getText(), contraseņa.getText());
    		if (!datos.isChecked()) {
    			JOptionPane.showMessageDialog(null, "Te quedo mal gran hijueputa");
    		} else {
    			JOptionPane.showMessageDialog(null, "Bienvenido "+nombre.getText());
    			tetris.setVisible(true);
    		}
    	}
    }
}