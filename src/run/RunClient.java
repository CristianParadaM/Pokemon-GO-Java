package run;

import controller.Controller;

/** Clase que maneja el objeto RunClient.java
 *  @author CRISTIAN DAVID PARADA MARTINEZ
 *  @date 10/10/2021
 */
public class RunClient {
	public static void main(String[] args) {
		Controller.getInstance().runApp("localhost", "12345");
	}
}