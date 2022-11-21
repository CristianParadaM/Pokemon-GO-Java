package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import network.Client;
import network.ThreadNotify;
import view.Constants;
import view.ViewApp;
import view.utils.IGameListener;

/**
 * Clase que maneja el objeto Controller.java
 * 
 * @author CRISTIAN DAVID PARADA MARTINEZ
 * @date 27/09/2021
 */
public class Controller extends ThreadNotify implements ActionListener, IGameListener {
	private ViewApp view;
	private static Controller controller;
	private Gson gson;
	private Client client;
	private int port;
	private String ip;

	/**
	 * Metodo estatico que retorna una instancia de Controller donde contiene los
	 * listener ActionListener, KeyListener y IGame
	 * 
	 * @return instancia de Controller
	 */
	public static Controller getInstance() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}

	/**
	 * Constructor de la clase Controller
	 */
	private Controller() {
		super(50);
		this.view = new ViewApp();
		this.gson = new Gson();
		this.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			this.pause();
				switch (e.getActionCommand()) {
				case Constants.COMMAND_BUTTON_SEND -> connect();
				case Constants.COMMAND_BUTTON_EXIT_MAP -> disconnect();
				case Constants.COMMAND_BUTTON_EXIT -> exitApp();
				case Constants.COMMAND_BUTTON_SCORES -> connectUser("ScoreUser","");
				case Constants.COMMAND_BUTTON_LIST -> showList();
				}
			this.resume();
		} catch (IOException e1) {

		}
	}

	@Override @SuppressWarnings("unchecked")
	protected void executeTask() {
		try {
			if (client!=null) {
				if (client.getIn().available() > 0) {
					if (client.getIn().readByte() == -1) {
						ArrayList<String> pokemonsData = gson.fromJson(client.getIn().readUTF(), ArrayList.class);
						ArrayList<String[]> aux = toArray(pokemonsData);
						this.view.actualizeMap(aux);
					}
				}
			}
		} catch (IOException e) {
		}
	}

	/**
	 * Metodo que inicia la aplicacion
	 */
	public void runApp(String ip, String port) {
		this.ip = ip;
		this.port = Integer.parseInt(port);
		view.initFrame();
		view.showFrame();
	}


	/**
	 * Metodo que comunica del servidor la lista de pokemones del jugador y la manda
	 * a la vista
	 * 
	 * @throws IOException
	 * @throws JsonSyntaxException
	 */
	private void showList() throws JsonSyntaxException, IOException {
		client.getOut().writeByte(4);
		Object[][] m = gson.fromJson(client.getIn().readUTF(), Object[][].class);
		view.showListPokemons(m);
	}

	/**
	 * Metodo que envia una peticion de tipo ScoreUser al servidor y este le retorna
	 * la matriz de puntuaciones del juego
	 * 
	 * @throws IOException
	 */
	private void showScores() throws IOException {
		Object[][] matrixScores = gson.fromJson(client.getIn().readUTF(), Object[][].class);
		view.showTable(matrixScores);
	}

	/**
	 * Metodo que cierra el programa
	 */
	private void exitApp() {
		System.exit(1);
	}

	/**
	 * Metodo que desconecta a un jugador del servidor y restaura el mapa de la
	 * vista
	 */
	private void disconnect() {
		try {
			client.getOut().writeByte(3);
			client.getSocket().close();
			view.restoreMap();
			view.deselectChars();
			view.showMainMenu();
		} catch (IOException e) {
		}
	}

	/**
	 * Metodo que conecta al jugador al servidor y recibe los datos de la matriz de
	 * pokemons para ponerlos en la vista
	 * 
	 * @throws IOException
	 * @throws JsonSyntaxException
	 */
	@SuppressWarnings("unchecked")
	private void connect() throws JsonSyntaxException, IOException {
		String name = view.getSelectedChar();
		if (!name.equals("none")) {
			String nickName = view.getNickName();
			if (!nickName.isBlank()) {
				if (connectUser(name, nickName)) {
					view.setPhoto(name);
					client.getOut().writeByte(1);
					ArrayList<String> array = gson.fromJson(client.getIn().readUTF(), ArrayList.class);
					ArrayList<String[]> aux = toArray(array);
					view.showMap(name, aux);
				}
			}else {
				JOptionPane.showMessageDialog(null, "Digite un nombre", "error", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Selecciona un personaje", "error", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Metodo que convierte una lista de String a lista de String[] (por problemas de GSON)
	 * @param list lista a convertir
	 * @return lista convertida
	 */
	private ArrayList<String[]> toArray(ArrayList<String> list) {
		ArrayList<String[]> aux = new ArrayList<>();
		for (String data : list) {
			aux.add(data.split(","));
		}
		return aux;
	}

	/**
	 * Metodo que conecta el usuario al servidor
	 * @param name nombre del jugador 
	 * @param nickname apodo del jugador
	 */
	private boolean connectUser(String name, String nickname) {
		try {
			if (name.equals("ScoreUser")) {
				client = new Client(ip, port);
				client.getOut().writeUTF(name);
				client.getOut().writeByte(0);
				showScores();
			} else {
				client = new Client(ip, port);
				client.getOut().writeUTF(name+"_"+nickname);
			}
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se puede conectar al servidor", "error de conexion",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}


	@Override
	public void win(String[] pokemonData, String[] playerData) {
		try {
			this.pause();
			client.getOut().writeByte(2);
			client.getOut().writeUTF(gson.toJson(pokemonData));
			client.getOut().writeUTF(gson.toJson(playerData));
			client.getOut().writeUTF(playerData[1]);
			
			Thread.sleep(3000);
			view.closeBattle();
			view.changeMapMusic();
			view.removePokemon(pokemonData[1], pokemonData[2]);
			view.updateLevel(Integer.parseInt(client.getIn().readUTF()));
			view.updateLife(client.getIn().readInt());
			this.resume();
		} catch (InterruptedException | IOException e) {
		}
	}

	@Override
	public void lose(String[] pokemonData, String[] playerData) {
		try {
			this.pause();
			Thread.sleep(3000);
			view.changeMusicSad();
			Thread.sleep(5000);
			view.closeBattle();
			view.resetProp();
			disconnect();
			this.resume();
		} catch (InterruptedException e) {
		}
	}

}