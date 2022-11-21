package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/** Clase que maneja el objeto Client.java
 *  @author CRISTIAN DAVID PARADA MARTINEZ
 *  @date 2/10/2021
 */
public class Client {
	
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	/**
	 * Constructor de la clase Client
	 * @param socket
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Client(String ip, int port) throws UnknownHostException, IOException {
		this.socket = new Socket(ip, port);
		this.in = new DataInputStream(socket.getInputStream());
		this.out = new DataOutputStream(socket.getOutputStream());
	}
	
	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}
	/**
	 * @return the out
	 */
	public DataOutputStream getOut() {
		return out;
	}
	/**
	 * @return the in
	 */
	public DataInputStream getIn() {
		return in;
	}
	
}
