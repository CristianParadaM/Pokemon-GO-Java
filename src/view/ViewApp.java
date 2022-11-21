package view;

import java.util.ArrayList;
import view.JFrame.JFrameMain;

/** Clase que maneja el objeto ViewApp.java
 *  @author CRISTIAN DAVID PARADA MARTINEZ
 *  @date 27/09/2021
 */
public class ViewApp {
	private JFrameMain jFrameMain;
	
	/**
	 * Constructor de la clase ViewApp
	 * @param splash
	 * @param jFrameMain
	 */
	public ViewApp() {
		this.jFrameMain = JFrameMain.getInstance();
	}

	/**
	 * Metodo que crea el splash
	 */
	public void showFrame() {
		jFrameMain.initFrame();
	}


	/**
	 * Metodo que obtiene el personaje elejido
	 * @return Nombre del personaje elejido
	 */
	public String getSelectedChar() {
		return jFrameMain.getSelectedChar();
	}

	/**
	 * Metodo que obtiene el nickname digitado en la vista
	 * @return nickname digitado
	 */
	public String getNickName() {
		return jFrameMain.getNickName();
	}

	/**
	 * Metodo que cambia la foto del jugador en la vista segun el nombre del jugador
	 * @param name nombre del jugador (Ash, Brock, Serena o Goh)
	 */
	public void setPhoto(String name) {
		this.jFrameMain.setPhoto(name);
	}

	/**
	 * Metodo que muestra el mapa y pone los pokemones y el jugador en el mapa
	 */
	public void showMap(String name, ArrayList<String[]> dataPokemons) {
		this.jFrameMain.activatePlay(name, dataPokemons);
	}

	/**
	 * Metodo que muestra el menu principal
	 */
	public void showMainMenu() {
		this.jFrameMain.showMainMenu();
	}

	/**
	 * Metodo que inicializa el frame
	 */
	public void initFrame() {
		this.jFrameMain.init();
	}

	/**
	 * Metodo que quita la seleccion de personajes
	 */
	public void deselectChars() {
		jFrameMain.deselectChars();
	}

	/**
	 * Metodo que muestra la tabla de scores
	 * @param matrixScores matriz contenedora de la informacion
	 */
	public void showTable(Object[][] matrixScores) {
		jFrameMain.showTable(matrixScores);
	}

	/**
	 * Metodo que restaura el mapa
	 */
	public void restoreMap() {
		jFrameMain.restoreMap();
	}

	/**
	 * Metodo que cambia la musica a triste
	 */
	public void changeMusicSad() {
		jFrameMain.changeMusicSad();
	}

	/**
	 * Metodo que cierra la batalla
	 */
	public void closeBattle() {
		jFrameMain.closeBattle();
	}

	/**
	 * Metodo que remueve un pokemon de la vista
	 * @param string id del pokemon
	 */
	public void removePokemon(String x, String y) {
		jFrameMain.removePokemon(x, y);
	}

	/**
	 * Metodo que cambia la musica del mapa
	 */
	public void changeMapMusic() {
		jFrameMain.changeMapMusic();
	}

	/**
	 * Metodo que muestra la lista de los pokemons capturados en el juego
	 * @param objects matriz contenedora de la informacion
	 */
	public void showListPokemons(Object[][] list) {
		jFrameMain.showListPokemons(list);
	}

	/**
	 * Metodo que cambia el nivel del jugador
	 * @param level nuevo nivel
	 */
	public void updateLevel(int level) {
		jFrameMain.updateLevel(level);
	}

	/**
	 * Metodo que cambia la vida del jugador
	 * @param life vida nueva
	 */
	public void updateLife(int life) {
		jFrameMain.updateLife(life);
	}

	/**
	 * Metodo que resetea las propiedades del mapa
	 */
	public void resetProp() {
		jFrameMain.resetProp();
	}

	/**
	 * Metodo que actualiza el mapa
	 * @param pokemonsData lista de informacion de los pokemones
	 */
	public void actualizeMap(ArrayList<String[]> pokemonsData) {
		this.jFrameMain.actualizeMap(pokemonsData);
	}

}
