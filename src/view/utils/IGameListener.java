package view.utils;

import java.util.EventListener;

/** Clase que maneja el objeto IGame.java
 * 	Listener personalizado
 *  @author CRISTIAN DAVID PARADA MARTINEZ
 *  @date 4/10/2021
 */
public interface IGameListener extends EventListener {
	
	/**
	 * Metodo que alerta al componente que lo implemento sobre una victoria
	 * @param pokemonData datos del pokemon
	 * @param playerData datos del jugador
	 */
	public void win(String[] pokemonData, String[] playerData);
	
	/**
	 * Metodo que alerta al componente que lo implemento sobre una derrota
	 * @param pokemonData datos del pokemon
	 * @param playerData datos del jugador
	 */
	public void lose(String[] pokemonData, String[] playerData);
}
