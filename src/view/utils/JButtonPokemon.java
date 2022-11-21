package view.utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import view.JFrame.JFrameMain;

/**
 * Clase que maneja el objeto JButtonPokemon.java
 * Boton personalizado usando iconos
 * @author CRISTIAN DAVID PARADA MARTINEZ
 * @date 29/09/2021
 */
public class JButtonPokemon extends JButton {

	private static final long serialVersionUID = 1L;
	private String actionCommand;
	private Dimension dimension;
	private ImageIcon icon;
	private ImageIcon iconScaledOver;

	/**
	 * Constructor de la clase JButtonPokemon
	 */
	public JButtonPokemon(ImageIcon icon, String actionCommand, Dimension dimension) {
		super(new ImageIcon(icon.getImage().getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(),
				Image.SCALE_SMOOTH)));
		this.icon = new ImageIcon(icon.getImage().getScaledInstance((int) dimension.getWidth(),
				(int) dimension.getHeight(), Image.SCALE_SMOOTH));
		this.iconScaledOver = new ImageIcon(
				icon.getImage().getScaledInstance((int) dimension.getWidth() + (JFrameMain.WIDTH_SCREEN * 50 / 1920),
						(int) dimension.getHeight() + (JFrameMain.WIDTH_SCREEN * 50 / 1920), Image.SCALE_SMOOTH));
		this.actionCommand = actionCommand;
		this.dimension = dimension;
		init();
	}

	/**
	 * Metodo que cambia el icono del boton
	 * 
	 * @param iconButton
	 */
	public void setIconButton(ImageIcon iconButton) {
		this.icon = new ImageIcon(iconButton.getImage().getScaledInstance((int) dimension.getWidth(),
				(int) dimension.getHeight(), Image.SCALE_SMOOTH));
		this.iconScaledOver = new ImageIcon(iconButton.getImage().getScaledInstance(
				(int) dimension.getWidth() + (JFrameMain.WIDTH_SCREEN * 50 / 1920),
				(int) dimension.getHeight() + (JFrameMain.WIDTH_SCREEN * 50 / 1920), Image.SCALE_SMOOTH));
		this.setIcon(icon);
	}

	/**
	 * Metodo que inicia las propiedades del boton
	 */
	private void init() {
		this.setSize(dimension);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setIcon(iconScaledOver);
				setSize((int) dimension.getWidth() + (JFrameMain.WIDTH_SCREEN * 50 / 1920),
						(int) dimension.getHeight() + (JFrameMain.WIDTH_SCREEN * 50 / 1920));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setIcon(icon);
				setSize(dimension);
			}

		});
		this.setContentAreaFilled(false);
		this.setActionCommand(actionCommand);
		this.setRolloverEnabled(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
	}

}
