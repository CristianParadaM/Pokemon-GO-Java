package view.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import controller.Controller;
import javax.swing.JDialog;
import view.Constants;
import view.Battle.JPanelBattle;
import view.JFrame.Play.JPanelMap;
import view.JFrame.help.JPanelHelp;
import view.utils.JButtonPokemon;
import view.utils.IBattleListener;

/**
 * Clase que maneja el objeto JFrameMain.java
 * @author CRISTIAN DAVID PARADA MARTINEZ
 * @date 27/09/2021
 */
public class JFrameMain extends JFrame implements ActionListener, MouseListener, MouseWheelListener, ItemListener, IBattleListener {
	
	public static final int WIDTH_SCREEN = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int HEIGTH_SCREEN = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private static final long serialVersionUID = 1L;
	private static final int TOTAL_LIFE = 1000;
	private boolean isPlaying;
	private static JFrameMain frameMain;
	private JDialog jdialogBattle;
	private JPanelBackground background;
	private JPanelInfo jPanelInfo;
	private JPanelMap jPanelMap;
	private JPanelConfigs configs;
	private JPanelCharacterSel jPanelCharacterSel;
	private JPanelHelp jpanelHelp;
	private JScrollPane jScrollPane;

	/**
	 * Metodo que retorna una instancia de JFrameMain donde contiene ActionListener, MouseListener, MouseWheelListener, ItemListener
	 * IBattle Listener
	 * @return instancia del componente
	 */
	public static JFrameMain getInstance() {
		if (frameMain == null) {
			frameMain = new JFrameMain();
		}
		return frameMain;
	}

	/**
	 * Constructor de la clase JFrameMain
	 */
	private JFrameMain() {
		super("POKEMON GO");
		this.isPlaying = false;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (jPanelInfo != null && jPanelMap != null) {
			switch ((String) (e.getItem())) {
			case Constants.DIALGA_S_FIGHT_TO_THE_FINISH -> switchMusic(Constants.DIALGA_S_FIGHT_TO_THE_FINISH);
			case Constants.MEWMORE_EMOTION -> switchMusic(Constants.MEWMORE_EMOTION);
			case Constants.MYTHICAL_POKEMON -> switchMusic(Constants.MYTHICAL_POKEMON);
			case Constants.TREACHERY -> switchMusic(Constants.TREACHERY);
			case Constants.VICTORY -> switchMusic(Constants.VICTORY);
			case Constants.CURSOR_BLUE -> switchCursor(0);
			case Constants.CURSOR_RED -> switchCursor(1);
			case Constants.CURSOR_PINK -> switchCursor(2);
			case Constants.CURSOR_YELLOW -> switchCursor(3);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case Constants.COMMAND_BUTTON_PLAY -> selectCharacter();
		case Constants.COMMAND_BUTTON_LENSMAS -> jPanelMap.zoom(-1, 10);
		case Constants.COMMAND_BUTTON_LENSMENOS -> jPanelMap.zoom(1, 10);
		case Constants.COMMAND_BUTTON_MUTEUNMUTE -> mutePlay();
		case Constants.COMMAND_BUTTON_ASHPR -> jPanelCharacterSel.select(((JButtonPokemon) e.getSource()).getName());
		case Constants.COMMAND_BUTTON_SERPR -> jPanelCharacterSel.select(((JButtonPokemon) e.getSource()).getName());
		case Constants.COMMAND_BUTTON_BROCKPR -> jPanelCharacterSel.select(((JButtonPokemon) e.getSource()).getName());
		case Constants.COMMAND_BUTTON_GOHPR -> jPanelCharacterSel.select(((JButtonPokemon) e.getSource()).getName());
		case Constants.COMMAND_BUTTON_SETTINGS -> showSettings();
		case Constants.COMMAND_BUTTON_EXIT_SEL -> exitSel();
		case Constants.COMMAND_BUTTON_CONFIG -> showConfigMap();
		case Constants.COMMAND_BUTTON_HELP -> showHelp();
		case Constants.COMMAND_BUTTON_PLAY_TWO -> deselectHelp();
		}
	}

	/**
	 * Metodo que oculta el panel de ayuda
	 */
	private void deselectHelp() {
		this.setLayout(new BorderLayout());
		jScrollPane.setVisible(false);
		jScrollPane.getVerticalScrollBar().setValue(0);
		this.jPanelInfo.setVisible(true);
	}

	/**
	 * Metodo que muestra el panel de ayuda
	 */
	private void showHelp() {
		if (jPanelInfo.isVisibleDialogScores()) {
			jPanelInfo.desactivateDialogScores();
		}
		if (jPanelInfo.isVisibleDialogSettings()) {
			jPanelInfo.desactivateDialogSettings();
		}
		this.setLayout(new BorderLayout());
		this.jPanelInfo.setVisible(false);
		jScrollPane = addJScrollToHelp();
		this.add(jScrollPane, BorderLayout.CENTER);
	}

	/**
	 * Metodo que añade un scroll al panel de ayuda
	 * @return scroll
	 */
	private JScrollPane addJScrollToHelp() {
		JScrollPane jScrollPane = new JScrollPane(jpanelHelp);
		jScrollPane.getViewport().setBackground(new Color(0, 135, 255));
		jScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
		jScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(20,0));
		jScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(31, 97, 141);
			}
			
		});
		jScrollPane.getVerticalScrollBar().setBackground(new Color(133, 193, 233));
		jScrollPane.getHorizontalScrollBar().setOpaque(false);
		return jScrollPane;
	}

	/**
	 * Metodo que muestra las configuraciones
	 */
	private void showConfigMap() {
		this.jPanelMap.showSettings();
	}

	/**
	 * Metodo que oculta el panel de seleccion de personajes
	 */
	private void exitSel() {
		this.jPanelCharacterSel.setVisible(false);
		this.jPanelInfo.setVisible(true);
	}

	/**
	 * Metodo que muestra la ventana de configuraciones
	 */
	private void showSettings() {
		jPanelInfo.showSettings();
	}

	/**
	 * Metodo que cambia el boton de mute si la musica esta muteada
	 */
	private void mutePlay() {
		if (background.isMuted()) {
			jPanelMap.setIconMuted(getClass().getResource(Constants.PATH_IMAGE_BUTTON_MUTE));
		} else {
			jPanelMap.setIconMuted(getClass().getResource(Constants.PATH_IMAGE_BUTTON_UNMUTE));
		}
	}

	/**
	 * Metodo que inicializa los componentes de este panel
	 */
	public void init() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setUndecorated(true);
		this.addMouseListener(this);
		this.setLayout(new BorderLayout());
		this.background = new JPanelBackground();
		this.setContentPane(background);
	}

	/**
	 * Metodo que crea el video de introduccion usando componentes JAVA FX 
	 */
	public void initFrame() {
			this.configs = new JPanelConfigs();
			this.jPanelCharacterSel = new JPanelCharacterSel();
			this.jPanelCharacterSel.setVisible(false);
			this.jPanelMap = new JPanelMap(configs);
			this.jPanelMap.addMyEventListener(this);
			this.jpanelHelp = new JPanelHelp();
			createComponents();
	}

	/**
	 * Metodo que crea los componentes de la pantalla inicial
	 */
	public void createComponents() {
		this.isPlaying = false;
		this.jPanelInfo = new JPanelInfo(configs);
		this.setLayout(new BorderLayout());
		this.add(jPanelInfo, BorderLayout.CENTER);
		this.setVisible(true);
		background.startMusic();
	}

	/**
	 * Metodo que activa el panel de seleccion de personaje
	 */
	private void selectCharacter() {
		if (jPanelInfo.isVisibleDialogScores()) {
			jPanelInfo.desactivateDialogScores();
		}
		if (jPanelInfo.isVisibleDialogSettings()) {
			jPanelInfo.desactivateDialogSettings();
		}
		this.isPlaying = false;
		this.setLayout(new BorderLayout());
		this.jPanelInfo.setVisible(false);
		this.jPanelCharacterSel.setVisible(true);
		this.add(jPanelCharacterSel, BorderLayout.CENTER);
	}

	/**
	 * Metodo que activa el juego 
	 * @param name nombre del personaje
	 * @param pokemons datos de los pokemones
	 */
	public void activatePlay(String name, ArrayList<String[]> pokemons) {
		this.setLayout(new BorderLayout());
		this.jPanelCharacterSel.setVisible(false);
		this.jPanelMap.setVisible(true);
		switch (name) {
		case "Ash" -> putPropPlayer(Constants.PATH_IMAGE_PLAYER_ASH, pokemons);
		case "Brock"-> putPropPlayer(Constants.PATH_IMAGE_PLAYER_BRO, pokemons);
		case "Serena"-> putPropPlayer(Constants.PATH_IMAGE_PLAYER_SER, pokemons);
		case "Goh"-> putPropPlayer(Constants.PATH_IMAGE_PLAYER_GOH, pokemons);
		}
		switchMusic(Constants.MYTHICAL_POKEMON);
		this.add(jPanelMap);
		this.isPlaying = true;
	}

	/**
	 * Metodo que pone las propiedades iniciales del jugador
	 * @param pokemons datos de los pokemones
	 */
	private void putPropPlayer(String pathImage, ArrayList<String[]> pokemons) {
		this.jPanelMap.putList(pokemons);
		this.jPanelMap.setPlayerImage(pathImage);
		this.jPanelMap.movePlayer(new Point(2500, 2500), false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (jPanelInfo != null && jPanelInfo.isVisibleDialogScores()) {
			jPanelInfo.desactivateDialogScores();
		} else if (jPanelInfo != null && jPanelInfo.isVisibleDialogSettings()) {
			jPanelInfo.desactivateDialogSettings();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (jPanelMap != null && jPanelMap.isVisible()) {
			if (e.getComponent() instanceof JPanel && isPlaying) {
				switch (e.getComponent().getName()) {
				case "bottomPanel":
					jPanelMap.setDown(true);
					jPanelMap.moveDown();
					break;
				case "topPanel":
					jPanelMap.setUp(true);
					jPanelMap.moveUP();
					break;
				case "rigthPanel":
					jPanelMap.setRigth(true);
					jPanelMap.moveRigth();
					break;
				case "leftPanel":
					jPanelMap.setLeft(true);
					jPanelMap.moveLeft();
					break;
				}
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (jPanelMap != null && jPanelMap.isVisible()) {
			if (e.getComponent() instanceof JPanel && isPlaying) {
				switch (e.getComponent().getName()) {
				case "bottomPanel":
					jPanelMap.setDown(false);
					break;
				case "topPanel":
					jPanelMap.setUp(false);
					break;
				case "rigthPanel":
					jPanelMap.setRigth(false);
					break;
				case "leftPanel":
					jPanelMap.setLeft(false);
					break;
				}
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getComponent() instanceof JPanelMap && isPlaying) {
			jPanelMap.zoom(e.getPreciseWheelRotation(), e.getScrollAmount());
		}
	}

	/**
	 * Metodo que obtiene el personaje seleccionado
	 * @return personaje seleccionado
	 */
	public String getSelectedChar() {
		return jPanelCharacterSel.getSelectedChar();
	}

	/**
	 * Metodo que obtiene el nickname digitado
	 * @return nickname
	 */
	public String getNickName() {
		return jPanelCharacterSel.getNickName();
	}

	/**
	 * Metodo que cambia la foto del jugador en el mapa
	 * @param name nombre del personaje
	 */
	public void setPhoto(String name) {
		this.jPanelMap.setPhoto(name);
	}

	/**
	 * Metodo que muestra el menu principal
	 */
	public void showMainMenu() {
		this.isPlaying = false;
		this.setLayout(new BorderLayout());
		this.jPanelMap.setVisible(false);
		this.jPanelCharacterSel.setVisible(false);
		this.jPanelInfo.setVisible(true);
		switchMusic(Constants.DIALGA_S_FIGHT_TO_THE_FINISH);
	}

	/**
	 * Metodo que deselecciona los personajes
	 */
	public void deselectChars() {
		jPanelCharacterSel.deselectAll();
	}

	/**
	 * Metodo que muestra la tabla de puntuaciones
	 * @param matrixScores matriz de datos del modelo
	 */
	public void showTable(Object[][] matrixScores) {
		this.isPlaying = false;
		if (jPanelInfo != null && jPanelInfo.isVisibleDialogSettings()) {
			jPanelInfo.desactivateDialogSettings();
		}
		jPanelInfo.showTable(matrixScores);
	}

	/**
	 * Metodo que cambia el cursor
	 * @param type tipo de cursor
	 */
	private void switchCursor(int type) {
		try {
			switch (type) {
			case 0:
				Constants.ACTUAL_CURSOR_INIT = Constants.PATH_IMAGE_CURSOR_BLUE;
				background.changeCursor(Constants.PATH_IMAGE_CURSOR_BLUE);
				jPanelInfo.changeCursorD(Constants.PATH_IMAGE_CURSOR_BLUE);
				jPanelMap.changeCursorD(Constants.PATH_IMAGE_CURSOR_BLUE);
				break;
			case 1:
				Constants.ACTUAL_CURSOR_INIT = Constants.PATH_IMAGE_CURSOR_RED;
				background.changeCursor(Constants.PATH_IMAGE_CURSOR_RED);
				jPanelInfo.changeCursorD(Constants.PATH_IMAGE_CURSOR_RED);
				jPanelMap.changeCursorD(Constants.PATH_IMAGE_CURSOR_RED);
				break;
			case 2:
				Constants.ACTUAL_CURSOR_INIT = Constants.PATH_IMAGE_CURSOR_PINK;
				background.changeCursor(Constants.PATH_IMAGE_CURSOR_PINK);
				jPanelInfo.changeCursorD(Constants.PATH_IMAGE_CURSOR_PINK);
				jPanelMap.changeCursorD(Constants.PATH_IMAGE_CURSOR_PINK);
				break;
			case 3:
				Constants.ACTUAL_CURSOR_INIT = Constants.PATH_IMAGE_CURSOR_YELLOW;
				background.changeCursor(Constants.PATH_IMAGE_CURSOR_YELLOW);
				jPanelInfo.changeCursorD(Constants.PATH_IMAGE_CURSOR_YELLOW);
				jPanelMap.changeCursorD(Constants.PATH_IMAGE_CURSOR_YELLOW);
			}
		} catch (IOException | URISyntaxException e) {
		}
	}

	/**
	 * Metodo que cambia el cursor a un componente
	 * @param component componente
	 */
	private void changeCursor(Component component) {
		try {
			BufferedImage cursor = ImageIO.read(getClass().getResource(Constants.ACTUAL_CURSOR_INIT));
			Cursor myCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "my cursor");
			component.setCursor(myCursor);
		} catch (IOException e) {
		}
	}

	/**
	 * Metodo que cambia la musica
	 * @param nameMusic nombre de la cancion
	 */
	private void switchMusic(String nameMusic) {
		if (!Constants.ACTUAL_SONG_INIT.equals(nameMusic)) {
			switch (nameMusic) {
				case Constants.DIALGA_S_FIGHT_TO_THE_FINISH -> background.switchMusic(Constants.PATH_AUDIO_SOUND_DAL);
				case Constants.MEWMORE_EMOTION -> background.switchMusic(Constants.PATH_AUDIO_SOUND_MEW);
				case Constants.MYTHICAL_POKEMON -> background.switchMusic(Constants.PATH_AUDIO_SOUND_MYT);
				case Constants.TREACHERY -> background.switchMusic(Constants.PATH_AUDIO_SOUND_TRE);
				case Constants.VICTORY -> background.switchMusic(Constants.PATH_AUDIO_SOUND_VIC);
			}
			Constants.ACTUAL_SONG_INIT = nameMusic;
		}
	}

	/**
	 * Metodo que restaura el mapa
	 */
	public void restoreMap() {
		this.jPanelMap.restoreMap();
	}

	@Override
	public void alertBattle(String[] pokemonInfo, String[] characterInfo) {
		this.jdialogBattle = new JDialog(this);
		jdialogBattle.setUndecorated(true);
		jdialogBattle.setSize(WIDTH_SCREEN, HEIGTH_SCREEN);
		jdialogBattle.setLocationRelativeTo(this);
		JPanelBattle battle = new JPanelBattle(pokemonInfo, characterInfo, jPanelMap.isMuted());
		battle.addIGame(Controller.getInstance());
		changeCursor(battle);
		jdialogBattle.getContentPane().add(battle);
		jdialogBattle.setVisible(true);
		isMutedToBattle(characterInfo);
	}

	/**
	 * Metodo que verifica si esta muteada la musica para poner la de batalla
	 * @param characterInfo datos del jugador
	 */
	private void isMutedToBattle(String[] characterInfo) {
		if (!jPanelMap.isMuted()) {
			if (Integer.parseInt(characterInfo[1])<TOTAL_LIFE*0.6) {
				switchMusic(Constants.TREACHERY);
			}else {
				switchMusic(Constants.VICTORY);
			}
		}
	}

	/**
	 * Metodo que cambia a musica triste
	 */
	public void changeMusicSad() {
		switchMusic(Constants.MEWMORE_EMOTION);
	}

	/**
	 * Metodo que cierra la batalla
	 */
	public void closeBattle() {
		jdialogBattle.setVisible(false);
		jdialogBattle.dispose();
	}

	/**
	 * Metodo que remueve un pokemon del mapa
	 * @param idPokemon
	 */
	public void removePokemon(String x, String y) {
		jPanelMap.removePokemon(x, y);
	}

	/**
	 * Metodo que cambia la musica a la musica del mapa
	 */
	public void changeMapMusic() {
		if (!jPanelMap.isMuted()) {
			switchMusic(Constants.MYTHICAL_POKEMON);
		}
	}

	/**
	 * Metodo que muestra la lista de pokemones
	 * @param list
	 */
	public void showListPokemons(Object[][] list) {
		jPanelMap.showList(list);
	}

	/**
	 * Metodo que cambia el nivel del jugador
	 * @param level nivel
	 */
	public void updateLevel(int level) {
		jPanelMap.updateLevel(level);
	}

	/**
	 * Metodo que cambia la vida del jugador
	 * @param life nueva vida
	 */
	public void updateLife(int life) {
		jPanelMap.updateLife(life);
	}
	
	/**
	 * Metodo que cambia las propiedades iniciales del mapa
	 */
	public void resetProp() {
		jPanelMap.resetProp();
	}

	/**
	 * Metodo que actualiza el mapa con los nuevos pokemones
	 * @param pokemonsData datos de los pokemones
	 */
	public void actualizeMap(ArrayList<String[]> pokemonsData) {
		this.jPanelMap.actualizeMap(pokemonsData);
	}

}
