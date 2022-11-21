package view.JFrame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.Controller;
import view.Constants;
import view.utils.JButtonPokemon;
import view.utils.MyJDialog;
import view.utils.MyJTable;

/**
 * Clase que maneja el objeto JPanelInfo.java
 * 
 * @author CRISTIAN DAVID PARADA MARTINEZ
 * @date 30/09/2021
 */
public class JPanelInfo extends JPanel {
	private static final String[] COLUMN_NAMES_SCORES = new String[] { "Posicion", "Nombre", "Cantidad" };
	private static final long serialVersionUID = 1L;
	public static final int WIDTH_BUTTONS = (int) ((JFrameMain.WIDTH_SCREEN * 400) / 1920);
	public static final int HEITH_BUTTONS = (int) ((JFrameMain.WIDTH_SCREEN * 200) / 1920);
	private GridBagConstraints gbc;
	private JLabel jlabelLogo;
	private JButtonPokemon buttonPlay;
	private JButtonPokemon buttonSettigns;
	private JButtonPokemon buttonScores;
	private JButtonPokemon buttonExit;
	private JButtonPokemon buttonHelp;
	private JDialog jDialogScores;
	private MyJDialog jDialogSettings;
	private JPanelConfigs configs;

	/**
	 * Constructor de la clase JPanelMain
	 */
	public JPanelInfo(JPanelConfigs configs) {
		super();
		this.gbc = new GridBagConstraints();
		this.jlabelLogo = new JLabel(new ImageIcon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_LOGO)).getImage().getScaledInstance(
						JFrameMain.WIDTH_SCREEN / 3 * 2 + 30, JFrameMain.HEIGTH_SCREEN / 2, Image.SCALE_SMOOTH)));
		this.buttonPlay = new JButtonPokemon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_PLAY)),
				Constants.COMMAND_BUTTON_PLAY, new Dimension(WIDTH_BUTTONS, HEITH_BUTTONS));
		this.buttonSettigns = new JButtonPokemon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_SETTINGS)),
				Constants.COMMAND_BUTTON_SETTINGS, new Dimension(WIDTH_BUTTONS, HEITH_BUTTONS));
		this.buttonScores = new JButtonPokemon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_SCORES)),
				Constants.COMMAND_BUTTON_SCORES, new Dimension(WIDTH_BUTTONS, HEITH_BUTTONS));
		this.buttonExit = new JButtonPokemon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_EXIT)),
				Constants.COMMAND_BUTTON_EXIT, new Dimension(WIDTH_BUTTONS, HEITH_BUTTONS));
		this.buttonHelp = new JButtonPokemon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_HELP)),
				Constants.COMMAND_BUTTON_HELP,
				new Dimension(JFrameMain.WIDTH_SCREEN * 100 / 1920, JFrameMain.HEIGTH_SCREEN * 100 / 1080));
		this.jDialogScores = new JDialog(JFrameMain.getInstance());
		this.jDialogSettings = new MyJDialog(JFrameMain.getInstance(),
				new Dimension(JFrameMain.WIDTH_SCREEN * 500 / 1920, JFrameMain.HEIGTH_SCREEN * 500 / 1080));
		this.configs = configs;
		init();
	}

	/**
	 * Metodo que inicializa las propiedades de este panel
	 */
	private void init() {
		this.setOpaque(false);
		initDialogs();
		activateMainButtons();
	}

	/**
	 * Metodo que inicializa las propiedades de las subventanas
	 */
	private void initDialogs() {
		jDialogScores.setUndecorated(true);
		jDialogScores.setBounds(JFrameMain.WIDTH_SCREEN / 4, JFrameMain.HEIGTH_SCREEN / 4,
				JFrameMain.WIDTH_SCREEN / 3 * 2, JFrameMain.HEIGTH_SCREEN / 3 * 2);
		jDialogScores.setLocationRelativeTo(JFrameMain.getInstance());
		jDialogScores.getContentPane().setBackground(new Color(0, 0, 0, 0));
		jDialogScores.setShape(new RoundRectangle2D.Double(0, 0, JFrameMain.WIDTH_SCREEN / 3 * 2,
				JFrameMain.HEIGTH_SCREEN / 3 * 2, 60, 60));
		jDialogScores.setOpacity(0.90f);
		try {
			Image cursor = ImageIO.read(getClass().getResource(Constants.PATH_IMAGE_CURSOR_BLUE));
			Cursor myCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "my cursor");
			jDialogScores.setCursor(myCursor);
		} catch (IOException e) {
		}
	}

	/**
	 * Metodo que adiciona los componentes principales
	 */
	private void activateMainButtons() {
		this.setLayout(new GridBagLayout());
		gbc.weightx = 1;
		addLogo();
		addPlay();
		addSettings();
		addScores();
		addExit();
		addHelp();
	}

	/**
	 * Metodo que adiciona el boton de ayuda
	 */
	private void addHelp() {
		gbc.insets = new Insets(JFrameMain.HEIGTH_SCREEN * 50 / 1080, 0, 10, 10);
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		buttonHelp.addActionListener(JFrameMain.getInstance());
		this.add(buttonHelp, gbc);
	}

	/**
	 * Metodo que adiciona el boton de salir
	 */
	private void addExit() {
		gbc.gridx = 3;
		buttonExit.addActionListener(Controller.getInstance());
		this.add(buttonExit, gbc);
	}

	/**
	 * Metodo que adiciona el boton de scores
	 */
	private void addScores() {
		gbc.gridx = 2;
		buttonScores.addActionListener(Controller.getInstance());
		this.add(buttonScores, gbc);
	}

	/**
	 * Metodo que adiciona el boton de configuraciones
	 */
	private void addSettings() {
		gbc.gridx = 1;
		buttonSettigns.addActionListener(JFrameMain.getInstance());
		this.add(buttonSettigns, gbc);
	}

	/**
	 * Metodo que adiciona el boton de play
	 */
	private void addPlay() {
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		buttonPlay.addActionListener(JFrameMain.getInstance());
		this.add(buttonPlay, gbc);
	}

	/**
	 * Metodo que adiciona el logo
	 */
	private void addLogo() {
		gbc.gridwidth = 4;
		gbc.insets.bottom = 85;
		this.add(jlabelLogo, gbc);
	}
	
	/**
	 * Metodo que verifica si los score son visibles
	 * @return true o false
	 */
	public boolean isVisibleDialogScores() {
		return jDialogScores.isVisible();
	}

	/**
	 * Metodo que desactiva el dialog de scores
	 */
	public void desactivateDialogScores() {
		jDialogScores.setVisible(false);
		jDialogScores.dispose();
	}

	/**
	 * Metodo que verifica si las configuraciones son visibles
	 * @return true o false
	 */
	public boolean isVisibleDialogSettings() {
		return jDialogSettings.isVisible();
	}

	/**
	 * Metodo que desactiva las configuraciones
	 */
	public void desactivateDialogSettings() {
		jDialogSettings.setVisible(false);
		jDialogSettings.dispose();
	}

	/**
	 * Metodo que muestra las configuraciones
	 */
	public void showSettings() {
		jDialogSettings.getContentPane().add(configs);
		jDialogSettings.getContentPane().setBackground(new Color(254, 240, 225));
		jDialogSettings.setVisible(true);
	}

	/**
	 * Metodo que muestra la tabla de scores
	 * @param matrixScores informacion de los score
	 */
	public void showTable(Object[][] matrixScores) {
		jDialogScores.getContentPane()
				.add(new MyJTable(matrixScores, COLUMN_NAMES_SCORES, 40))
				.setBounds(0, 0, JFrameMain.WIDTH_SCREEN / 3 * 2, JFrameMain.HEIGTH_SCREEN / 3 * 2);
		jDialogScores.setVisible(true);
	}

	/**
	 * Metodo que cambia el cursor del dialog
	 * @param pathImageCursor path
	 */
	public void changeCursorD(String pathImageCursor) {
		try {
			Image cursor = ImageIO.read(getClass().getResource(pathImageCursor));
			Cursor myCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "my cursor");
			jDialogSettings.setCursor(myCursor);
		} catch (IOException e) {
		}
	}

}
