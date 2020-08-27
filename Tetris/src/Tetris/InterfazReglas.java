package Tetris;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfazReglas extends JFrame {

	private JPanel contentPane;

	public InterfazReglas() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setMinimumSize(new Dimension(450, 325));
		this.setTitle("Reglas");
		
		JButton regresar = new JButton("Regresar");
		regresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hide();
			}
		});
		regresar.setBounds(300, 212, 110, 38);
		contentPane.add(regresar);
		
		JLabel reglas1 = new JLabel();
		reglas1.setText("- Oprima P para pausar el juego.");
		reglas1.setBounds(22, 11, 243, 38);
		contentPane.add(reglas1);
		
		JLabel reglas2 = new JLabel("<html>- Oprima las teclas de direccion izquierda y derecha para mover las figura en el tablero.<html>");
		reglas2.setBounds(21, 47, 389, 31);
		contentPane.add(reglas2);
		
		JLabel reglas3 = new JLabel("<html>- Oprima las teclas de direccion arriba y abajo para girar la figura hacia la derecha o hacia la izquierda respectivamente.<html>");
		reglas3.setBounds(21, 89, 389, 31);
		contentPane.add(reglas3);
		
		JLabel reglas4 = new JLabel("<html>- Oprima la barra espaciadora para que la figura baje completamente.<html>");
		reglas4.setBounds(21, 131, 389, 33);
		contentPane.add(reglas4);
		
		JLabel lblNewLabel = new JLabel("<html>- Oprima D para que la figura baje mas r\u00E1pido.<html>");
		lblNewLabel.setBounds(21, 175, 389, 14);
		contentPane.add(lblNewLabel);
		pack();
		setVisible(false);
	}
}
