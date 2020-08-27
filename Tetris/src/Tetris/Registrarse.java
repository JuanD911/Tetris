package Tetris;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Persistencia.*;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

public class Registrarse extends JFrame implements ActionListener {

    private String userName, password;
    private JButton registrarse;
    private JPanel contentPane;
    private JTextField nombre;
    private JPasswordField contraseña;
    private JButton regresar;
    private Tetris tetris = new Tetris();

    public Registrarse() {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setMinimumSize(new Dimension(450, 325));
		this.setTitle("Registro");
		
		JLabel name = new JLabel("Nombre");
		name.setBounds(10, 70, 100, 14);
		contentPane.add(name);
		
		JLabel pass = new JLabel("Contraseña");
		pass.setBounds(10, 113, 100, 14);
		contentPane.add(pass);
		
		nombre = new JTextField();
		nombre.setBounds(126, 67, 240, 20);
		contentPane.add(nombre);
		nombre.setColumns(10);
		
		contraseña = new JPasswordField();
		contraseña.setBounds(126, 110, 240, 20);
		contentPane.add(contraseña);
		contraseña.setColumns(10);
		
		registrarse = new JButton("Registrarse");
		registrarse.setBounds(250, 183, 144, 50);
		contentPane.add(registrarse);
		
		regresar = new JButton("Regresar");
		regresar.setBounds(90, 183, 144, 50);
		contentPane.add(regresar);

        registrarse.addActionListener(this);
        regresar.addActionListener(this);

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registrarse) {
        	Player player = new Player(nombre.getText(), contraseña.getText());
        	Persistencia guardar = new Persistencia(player, true);
        	if (guardar.isRepeat()) {
        		JOptionPane.showMessageDialog(null, "Este usuario ya existe!");
			}else {
				JOptionPane.showMessageDialog(null, "Se registro exitosamente :)");
	            this.tetris.setVisible(true);
	            this.hide();
			}
        }
        if(e.getSource() == regresar) {
        	this.hide();
        }
    }
    
}