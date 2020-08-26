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
    private JPasswordField contrase�a;
    private Escritura escritor;
    private Tetris tetris = new Tetris();

    public Registrarse() {

        escritor = new Escritura("userData");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setMinimumSize(new Dimension(450, 325));
		this.setTitle("Registro");
		
		JLabel name = new JLabel("Nombre");
		name.setBounds(34, 70, 46, 14);
		contentPane.add(name);
		
		JLabel pass = new JLabel("Contrase�a");
		pass.setBounds(34, 113, 46, 14);
		contentPane.add(pass);
		
		nombre = new JTextField();
		nombre.setBounds(126, 67, 240, 20);
		contentPane.add(nombre);
		nombre.setColumns(10);
		
		contrase�a = new JPasswordField();
		contrase�a.setBounds(126, 110, 240, 20);
		contentPane.add(contrase�a);
		contrase�a.setColumns(10);
		
		registrarse = new JButton("Registrarse");
		registrarse.setBounds(165, 183, 144, 50);
		contentPane.add(registrarse);

        registrarse.addActionListener(this);

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registrarse) {
            this.userName = nombre.getText();
            this.password = contrase�a.getText();

            escritor.addContent(userName);
            escritor.addContent(password);
            JOptionPane.showMessageDialog(null, "Se registro exitosamente");
            this.tetris.setVisible(true);
        }
    }
    
}