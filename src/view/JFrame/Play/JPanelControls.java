package view.JFrame.Play;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.URL;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import controller.Controller;
import view.Constants;
import view.JFrame.JFrameMain;
import view.utils.JButtonPokemon;

/**
 * Clase que maneja el objeto JPanelControls.java
 * 
 * @author CRISTIAN DAVID PARADA MARTINEZ
 * @date 30/09/2021
 */
public class JPanelControls extends JPanel {

	public static final int SIZE_BUTTONS = (int)((JFrameMain.WIDTH_SCREEN * 80 )/ 1920); ;
	public static final int SIZELENS_BUTTONS = (int)((JFrameMain.WIDTH_SCREEN * 100 )/ 1920); ;
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc;
	private JButtonPokemon buttonLensMas;
	private JButtonPokemon buttonLensMenos;
	private JButtonPokemon buttonConfig;
	private JButtonPokemon buttonMuteUnMute;
	private JButtonPokemon buttonList;
	private JButtonPokemon buttonExit;
	private String level;
	private String life;
	private JPanel jpanelLens;
	private JPanel jpanelConfigs;
	

	/**
	 * Constructor de la clase JPanelControls
	 */
	public JPanelControls() {
		super();
		this.gbc = new GridBagConstraints();
		this.jpanelLens = new JPanel();
		this.jpanelConfigs = new JPanel();
		this.level = "1";
		this.life = "100";
		init();
	}

	/**
	 * Metodo que inicializa las propiedades de este panel
	 */
	private void init() {
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		addLens();
		addListButton();
		addMuteUnmuteButton();
		addConfigButtonButton();
		addExitButton();
	}

	/**
	 * Metodo que adiciona el boton de salir
	 */
	private void addExitButton() {
		gbc.gridx = 3;
		gbc.weightx = 0;
		this.buttonExit = new JButtonPokemon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_EXIT_MAP)),
				Constants.COMMAND_BUTTON_EXIT_MAP, new Dimension(SIZE_BUTTONS, SIZE_BUTTONS));
		this.buttonExit.addActionListener(Controller.getInstance());
		jpanelConfigs.add(buttonExit, gbc);
	}

	/**
	 * Metodo que adiciona el boton de ajustes
	 */
	private void addConfigButtonButton() {
		gbc.gridx = 2;
		this.buttonConfig = new JButtonPokemon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_CONFIG)),
				Constants.COMMAND_BUTTON_CONFIG, new Dimension(SIZE_BUTTONS, SIZE_BUTTONS));
		this.buttonConfig.addActionListener(JFrameMain.getInstance());
		jpanelConfigs.add(buttonConfig, gbc);
		this.add(jpanelConfigs, BorderLayout.SOUTH);
	}

	/**
	 * Metodo que adiciona el boton de la lista de pokemones
	 */
	private void addListButton() {
		jpanelConfigs.setOpaque(false);
		jpanelConfigs.setLayout(new GridBagLayout());
		this.buttonList = new JButtonPokemon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_LIST)),
				Constants.COMMAND_BUTTON_LIST, new Dimension(SIZE_BUTTONS, SIZE_BUTTONS));
		this.buttonList.addActionListener(Controller.getInstance());
		gbc.insets.left = (int)((JFrameMain.WIDTH_SCREEN * 800 )/ 1920) ;
		jpanelConfigs.add(buttonList, gbc);
	}

	/**
	 * Metodo que adiciona el boton de mute
	 */
	private void addMuteUnmuteButton() {
		gbc.gridx = 1;
		gbc.insets.left = 0;
		this.buttonMuteUnMute = new JButtonPokemon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_UNMUTE)),
				Constants.COMMAND_BUTTON_MUTEUNMUTE, new Dimension(SIZE_BUTTONS, SIZE_BUTTONS));
		this.buttonMuteUnMute.addActionListener(JFrameMain.getInstance());
		jpanelConfigs.add(buttonMuteUnMute, gbc);
	}

	/**
	 * Metodo que adiciona los botones de zoom
	 */
	private void addLens() {
		GridBagConstraints gbc = new GridBagConstraints();
		this.jpanelLens.setOpaque(false);
		jpanelLens.setLayout(new GridBagLayout());
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = 1;
		gbc.gridwidth = 4;
		jpanelLens.add(Box.createRigidArea(new Dimension(10, JFrameMain.HEIGTH_SCREEN * 450 / 1080)), gbc);
		gbc.weighty = 0;
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 1;
		jpanelLens.add(Box.createRigidArea(new Dimension(JFrameMain.HEIGTH_SCREEN * 690 / 1080, 0)), gbc);
		gbc.weightx = 0;
		gbc.gridx = 4;
		gbc.gridy = 1;
		this.buttonLensMas = new JButtonPokemon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_LENSMAS)),
				Constants.COMMAND_BUTTON_LENSMAS, new Dimension(SIZELENS_BUTTONS, SIZELENS_BUTTONS));
		this.buttonLensMas.addActionListener(JFrameMain.getInstance());
		jpanelLens.add(buttonLensMas, gbc);
		gbc.gridy = 2;
		this.buttonLensMenos = new JButtonPokemon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_LENSMENOS)),
				Constants.COMMAND_BUTTON_LENSMENOS, new Dimension(SIZELENS_BUTTONS, SIZELENS_BUTTONS));
		this.buttonLensMenos.addActionListener(JFrameMain.getInstance());
		jpanelLens.add(buttonLensMenos, gbc);
		this.add(jpanelLens, BorderLayout.NORTH);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Ravie", Font.BOLD, JFrameMain.WIDTH_SCREEN*45/1920));
		g.drawString("Nivel "+level, 0, getHeight());
		g.drawString("Fuerza "+life+"%", JFrameMain.WIDTH_SCREEN*300/1920, getHeight()-5);
		super.paintComponent(g);
	}
	
	/**
	 * @param life the life to set
	 */
	public void setLife(int life) {
		this.life = new String(life+"");
		repaint();
	}
	
	/**
	 * @return the life
	 */
	public String getLife() {
		return (1000*Integer.parseInt(life)/100)+"";
	}
	
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = new String(level+"");
		repaint();
	}
	
	/**
	 * Metodo que cambia el icono de mute
	 * @param resource
	 */
	public void setIconMuted(URL resource) {
		this.buttonMuteUnMute.setIconButton(new ImageIcon(resource));
	}
}
