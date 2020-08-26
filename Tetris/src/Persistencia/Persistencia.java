package Persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Persistencia {

	ArrayList<Player> players = new ArrayList<Player>();
	private Player player;
	private boolean isWrite = false;
	private boolean checked;
	private boolean repeat;

	// Write.
	public Persistencia(Player player, boolean isWrite) {
		this.player = player;
		players.clear();
		// Recupera.
		try {
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream("database.data"));
			players = (ArrayList<Player>) reader.readObject();
			reader.close();
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getNombre().equals(player.getNombre())) {
					System.out.println(players.get(i).getNombre());
					if (players.get(i).getPassword().equals(player.getPassword())) {
						System.out.println(players.get(i).getPassword());
						this.repeat = true;
					}
				}
			}
		} catch (FileNotFoundException exception) {
			System.out.println(exception.getMessage());
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}
		
		if (isWrite && !this.repeat) {
			players.add(this.player);
			try {
				ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("database.data"));
				writer.writeObject(players);
				writer.close();
			} catch (FileNotFoundException except) {
				JOptionPane.showMessageDialog(null, except.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
			} catch (IOException except) {
				JOptionPane.showMessageDialog(null, except.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	// Read.
	public Persistencia(String nombre, String password) {
		super();
		players.clear();
		try {
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream("database.data"));
			players = (ArrayList<Player>) reader.readObject();
			reader.close();
			// Check.
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getNombre().equals(nombre)) {
					System.out.println(players.get(i).getNombre());
					if (players.get(i).getPassword().equals(password)) {
						System.out.println(players.get(i).getPassword());
						this.checked = true;
					}
				}
			}
		} catch (FileNotFoundException exception) {
			System.out.println(exception.getMessage());
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}
	}

	public boolean isChecked() {
		return checked;
	}

	public boolean isRepeat() {
		return repeat;
	}
	
}