package Tetris;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inicio extends JFrame{
	
	InterfazReglas r = new InterfazReglas();
	private JButton IniciarSesion;
	private JButton Registrarse;
	private JButton Reglas;
	private JLabel titulo;
  
	public Inicio(){
		JPanel botones = new JPanel();
		JPanel tableros = new JPanel();
		JPanel caja = new JPanel();
    
		IniciarSesion= new JButton();
		Registrarse = new JButton();
		Reglas= new JButton();
		titulo = new JLabel();
		caja.setLayout(new GridLayout(2,1));
		tableros.setLayout(new GridLayout(1,1));
//
//    titulo.setHorizontalAlignment(JLabel.CENTER);
//    titulo.setForeground(Color.white);
//    titulo.setFont(new Font ("Courier",Font.PLAIN,60));
		tableros.setBackground(Color.black);
    
		ImageIcon IconoTitulo=new ImageIcon("Iconos/tetris.png");
		titulo.setBackground(Color.BLACK);
		titulo.setBounds(500, 500, 550, 250);    
		titulo.setIcon(new ImageIcon(IconoTitulo.getImage().getScaledInstance(titulo.getWidth(),titulo.getHeight(),Image.SCALE_SMOOTH)));

		ImageIcon IconoIniciarSesion=new ImageIcon("Iconos/iniciar.png");
		IniciarSesion.setBackground(Color.BLACK);
		IniciarSesion.setBounds(400, 300, 100, 100);    
		IniciarSesion.setIcon(new ImageIcon(IconoIniciarSesion.getImage().getScaledInstance(IniciarSesion.getWidth(),IniciarSesion.getHeight(),Image.SCALE_SMOOTH)));

		ImageIcon IconoRegistrarse=new ImageIcon("Iconos/Registrarse.png");
		Registrarse.setBackground(Color.BLACK);
		Registrarse.setBounds(400, 300, 100, 100);    
		Registrarse.setIcon(new ImageIcon(IconoRegistrarse.getImage().getScaledInstance(Registrarse.getWidth(),Registrarse.getHeight(),Image.SCALE_SMOOTH)));

		ImageIcon IconoReglas=new ImageIcon("Iconos/Reglas.png");
		Reglas.setBackground(Color.BLACK);
		Reglas.setBounds(400, 300, 100, 100);    
		Reglas.setIcon(new ImageIcon(IconoReglas.getImage().getScaledInstance(Reglas.getWidth(),Reglas.getHeight(),Image.SCALE_SMOOTH)));
		botones.setBackground(Color.black);
		botones.add(IniciarSesion);
		botones.add(Registrarse);
		botones.add(Reglas);
		tableros.add(titulo);
		caja.add(tableros);
		caja.add(botones);

		getContentPane().add(caja);
		pack();
		this.setVisible(true);
		setTitle("TETRIS");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100,100,550,558);
    
		Reglas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				r.setVisible(true);
			}
		});
    }
}

