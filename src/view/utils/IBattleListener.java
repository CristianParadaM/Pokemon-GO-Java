package view.utils;

import java.util.EventListener;

/** Clase que maneja el objeto MyEvent.java
 * 	Listener personalizado
 *  @author CRISTIAN DAVID PARADA MARTINEZ
 *  @date 3/10/2021
 */
public interface IBattleListener extends EventListener{
	
	/**
	 * Metodo que alerta al componente que lo implemente sobre una batalla
	 * @param pokemonInfo informacion del pokemon
	 * @param CharacterInfo informacion del personaje
	 */
	public void alertBattle(String[] pokemonInfo, String[] CharacterInfo);
}
