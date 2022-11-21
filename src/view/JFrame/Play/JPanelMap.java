package view.JFrame.Play;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import view.utils.IBattleListener;
import view.utils.MyJDialog;
import view.utils.MyJTable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import view.Constants;
import view.JFrame.JFrameMain;
import view.JFrame.JPanelConfigs;

/**
 * Clase que maneja el objeto JPanelMap.java
 * 
 * @author CRISTIAN DAVID PARADA MARTINEZ
 * @date 30/09/2021
 */
public class JPanelMap extends JPanel {

	private static final String[] COLUMN_NAMES_LIST_POKEMONS = new String[] { "Nombre", "Tipo", "Cantidad" };
	private static final String[] PATHS_IMAGES_POKEMONS = (Constants.PATH_IMAGE_POKEMON_PIKACHU + ","
			+ Constants.PATH_IMAGE_POKEMON_SQUIRTLE + "," + Constants.PATH_IMAGE_POKEMON_JIGGLYPUGG + ","
			+ Constants.PATH_IMAGE_POKEMON_CHARMANDER + "," + Constants.PATH_IMAGE_POKEMON_BULBASAUR + ","
			+ Constants.PATH_IMAGE_POKEMON_SNORLAX + "," + Constants.PATH_IMAGE_POKEMON_MEWTWO + ","
			+ Constants.PATH_IMAGE_POKEMON_EKANS).split(",");
	private static final long serialVersionUID = 1L;
	private static final int VELOCITY = 10;
	private BufferedImage map;
	private BufferedImage mapb;
	private BufferedImage mapView;
	private boolean isDown;
	private boolean isUp;
	private boolean isLeft;
	private boolean isRigth;
	private boolean isZoom;
	private boolean isMoving;
	private boolean isActive;
	private boolean isIntersected;
	private boolean isMute;
	private int xOnMap;
	private int yOnMap;
	private Image charPhoto;
	private Image framePhoto;
	private Image ray;
	private ImageIcon imagePlayer;
	private Dimension dimensionMap;
	private MyJDialog jDialogSettings;
	private JDialog jDialogList;
	private JPanelControls jPanelControls;
	private JPanelConfigs configs;
	private IBattleListener myListener;
	private Point playerPos;
	private ArrayList<String[]> listPokemons;

	/**
	 * Constructor de la clase JPanelMap
	 * @param panel de configuraciones
	 */
	public JPanelMap(JPanelConfigs configs) {
		super();
		this.mapView = new BufferedImage(JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN,
				BufferedImage.TYPE_4BYTE_ABGR_PRE);
		this.isDown = false;
		this.isUp = false;
		this.isLeft = false;
		this.isRigth = false;
		this.jPanelControls = new JPanelControls();
		this.jDialogSettings = new MyJDialog(JFrameMain.getInstance(),
				new Dimension(JFrameMain.WIDTH_SCREEN * 500 / 1920, JFrameMain.HEIGTH_SCREEN * 500 / 1080));
		this.configs = configs;
		this.isZoom = false;
		this.isMoving = false;
		this.isActive = true;
		this.isIntersected = false;
		this.isMute = false;
		init();
	}

	/**
	 * Metodo que inicia las propiedades de los componentes
	 */
	private void init() {
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.addMouseWheelListener(JFrameMain.getInstance());
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isVisibleDialogSettings()) {
					desactivateDialogSettings();
				}
				if (e.getButton() == 3 && !isZoom && !isMoving && !isIntersected) {
					Point point = new Point(xOnMap + e.getXOnScreen() - 50, yOnMap + e.getYOnScreen() - 100);
					movePlayer(point, true);
				}
				if (isVisibleDialogList()) {
					desactivateDialogList();
				}
			}
		});
		addPanelMovings();
		openImage();
		configDialogListPokemons();
		this.add(jPanelControls, BorderLayout.CENTER);
	}

	/**
	 * Metodo que inicializa las propiedades de la subventana de la lista de pokemones
	 */
	private void configDialogListPokemons() {
		this.jDialogList = new JDialog(JFrameMain.getInstance());
		this.jDialogList.setSize(JFrameMain.WIDTH_SCREEN / 2, JFrameMain.HEIGTH_SCREEN / 2);
		this.jDialogList.setUndecorated(true);
		this.jDialogList.setShape(
				new RoundRectangle2D.Double(0, 0, JFrameMain.WIDTH_SCREEN / 2, JFrameMain.HEIGTH_SCREEN / 2, 60, 60));
		this.jDialogList.setLocationRelativeTo(JFrameMain.getInstance());
		this.jDialogList.setOpacity(0.90f);
		try {
			Image cursor = ImageIO.read(getClass().getResource(Constants.ACTUAL_CURSOR_INIT));
			Cursor myCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "myCursor");
			this.jDialogList.setCursor(myCursor);
		} catch (IOException e) {
		}
	}

	/**
	 * Metodo que cambia la imagen del jugador
	 * @param path ruta de la imagen
	 */
	public void setPlayerImage(String path) {
		this.imagePlayer = new ImageIcon(getClass().getResource(path));
	}

	/**
	 * Metodo que añade los paneles invisibles de movimiento de la camara
	 */
	private void addPanelMovings() {
		JPanel jPanelLeft = new JPanel();
		jPanelLeft.setName("leftPanel");
		jPanelLeft.setOpaque(false);
		jPanelLeft.addMouseListener(JFrameMain.getInstance());
		JPanel jPanelRigth = new JPanel();
		jPanelRigth.setName("rigthPanel");
		jPanelRigth.setOpaque(false);
		jPanelRigth.addMouseListener(JFrameMain.getInstance());
		JPanel jPanelTop = new JPanel();
		jPanelTop.setName("topPanel");
		jPanelTop.setOpaque(false);
		jPanelTop.addMouseListener(JFrameMain.getInstance());
		JPanel jPanelBottom = new JPanel();
		jPanelBottom.setName("bottomPanel");
		jPanelBottom.setOpaque(false);
		jPanelBottom.addMouseListener(JFrameMain.getInstance());
		this.add(jPanelTop, BorderLayout.NORTH);
		this.add(jPanelLeft, BorderLayout.WEST);
		this.add(jPanelRigth, BorderLayout.EAST);
		this.add(jPanelBottom, BorderLayout.SOUTH);
	}

	/**
	 * Metodo que realiza un zoom al mapa
	 * @param type tipo de zoom
	 * @param amount cantidad de zoom
	 */
	public void zoom(double type, int amount) {
		if (type > 0) {
			if (dimensionMap.getHeight() == 5000) {
				zoomOut();
			}
		} else {
			if (dimensionMap.getWidth() == 4500) {
				zoomIn();
			}
		}
	}

	/**
	 * Metodo que hace un zoom in
	 */
	private void zoomIn() {
		BufferedImage after = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(1.1, 1.1);
		double size = dimensionMap.getWidth() * 0.1;
		dimensionMap = new Dimension((int) (dimensionMap.getWidth() + size), (int) (dimensionMap.getHeight() + size));
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(map, after);
		map = after;
		mapView = map.getSubimage(xOnMap, yOnMap, JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN);
		repaint();
		isZoom = false;
	}

	/**
	 * Metodo que hace un zoom out
	 */
	private void zoomOut() {
		isZoom = true;
		BufferedImage after = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(0.9, 0.9);
		double size = dimensionMap.getWidth() * 0.1;
		dimensionMap = new Dimension((int) (dimensionMap.getWidth() - size), (int) (dimensionMap.getHeight() - size));
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(map, after);
		map = after;
		if (yOnMap + JFrameMain.HEIGTH_SCREEN + size > dimensionMap.getHeight()) {
			yOnMap = 0;
			xOnMap = 0;
		} else if (xOnMap + JFrameMain.WIDTH_SCREEN + size > dimensionMap.getWidth()) {
			yOnMap = 0;
			xOnMap = 0;
		}
		mapView = map.getSubimage(xOnMap, yOnMap, JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN);
		repaint();
	}

	/**
	 * Metodo para moverse hacia abajo en el mapa
	 */
	public void moveDown() {
		new Thread(() -> {
			while (isDown) {
				try {
					if (yOnMap < dimensionMap.getHeight() - JFrameMain.HEIGTH_SCREEN - VELOCITY) {
						mapView = map.getSubimage(xOnMap, yOnMap += VELOCITY, JFrameMain.WIDTH_SCREEN,
								JFrameMain.HEIGTH_SCREEN);
						repaint();
					} else {
						break;
					}
					Thread.sleep(10);
				} catch (InterruptedException | RasterFormatException e) {
				}
			}
		}).start();
	}

	/**
	 * Metodo para moverse hacia arriba en el mapa
	 */
	public void moveUP() {
		new Thread(() -> {
			while (isUp) {
				try {
					if (yOnMap > (VELOCITY * 2)) {
						mapView = map.getSubimage(xOnMap, yOnMap -= VELOCITY, JFrameMain.WIDTH_SCREEN,
								JFrameMain.HEIGTH_SCREEN);
						repaint();
					} else {
						break;
					}
					Thread.sleep(10);
				} catch (InterruptedException | RasterFormatException e) {
				}
			}
		}).start();
	}

	/**
	 * Metodo para moverse hacia la izquierda en el mapa
	 */
	public void moveLeft() {
		new Thread(() -> {
			while (isLeft) {
				try {
					if (xOnMap > (VELOCITY * 2)) {
						mapView = map.getSubimage(xOnMap -= VELOCITY, yOnMap, JFrameMain.WIDTH_SCREEN,
								JFrameMain.HEIGTH_SCREEN);
						repaint();
					} else {
						break;
					}
					Thread.sleep(10);
				} catch (InterruptedException | RasterFormatException e) {
				}
			}
		}).start();
	}

	/**
	 * Metodo para moverse hacia la derecha en el mapa
	 */
	public void moveRigth() {
		new Thread(() -> {
			while (isRigth) {
				try {
					if (xOnMap < dimensionMap.getWidth() - JFrameMain.WIDTH_SCREEN - VELOCITY) {
						mapView = map.getSubimage(xOnMap += VELOCITY, yOnMap, JFrameMain.WIDTH_SCREEN,
								JFrameMain.HEIGTH_SCREEN);
						repaint();
					} else {
						break;
					}
					Thread.sleep(10);
				} catch (InterruptedException | RasterFormatException e) {
					break;
				}
			}
		}).start();
	}

	/**
	 * @param down the down to set
	 */
	public void setDown(boolean down) {
		this.isDown = down;
	}

	/**
	 * @param up the up to set
	 */
	public void setUp(boolean up) {
		this.isUp = up;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(boolean left) {
		this.isLeft = left;
	}

	/**
	 * @param rigth the rigth to set
	 */
	public void setRigth(boolean rigth) {
		this.isRigth = rigth;
	}

	/**
	 * Metodo que cambia el icono de mute
	 * 
	 * @param resource
	 */
	public void setIconMuted(URL resource) {
		isMute = isMute==true?false:true;
		jPanelControls.setIconMuted(resource);
	}
	
	/**
	 * Metodo que retorna si el audio esta muteado o no
	 * @return true o false
	 */
	public boolean isMuted() {
		return isMute;
	}

	/**
	 * Metodo que cambia la foto del jugador
	 * @param name nombre del personaje
	 */
	public void setPhoto(String name) {
		switch (name) {
		case "Ash":
			charPhoto = new ImageIcon(getClass().getResource(Constants.PATH_GIF_PHOTO_ASH)).getImage();
			break;
		case "Serena":
			charPhoto = new ImageIcon(getClass().getResource(Constants.PATH_GIF_PHOTO_SERENA)).getImage();
			break;
		case "Brock":
			charPhoto = new ImageIcon(getClass().getResource(Constants.PATH_GIF_PHOTO_BROCK)).getImage();
			break;
		case "Goh":
			charPhoto = new ImageIcon(getClass().getResource(Constants.PATH_GIF_PHOTO_GOH)).getImage();
			break;

		}
	}

	/**
	 * Metodo que retorna si esta visible la subVentana de la lista de pokemones
	 * @return true o false
	 */
	public boolean isVisibleDialogList() {
		return jDialogList.isVisible();
	}

	/**
	 * Metodo que desactiva la subVentana de la lista de pokemones
	 */
	public void desactivateDialogList() {
		jDialogList.setVisible(false);
		jDialogList.dispose();
	}

	/**
	 * Metodo que retorna si esta visible la subVentana de Configuraciones.
	 * @return true o false
	 */
	public boolean isVisibleDialogSettings() {
		return jDialogSettings.isVisible();
	}

	/**
	 * Metodo que desactiva la subVentana de Configuraciones.
	 */
	public void desactivateDialogSettings() {
		jDialogSettings.setVisible(false);
		jDialogSettings.dispose();
	}

	/**
	 * Metodo que muestra la subVentana de Configuraciones.
	 */
	public void showSettings() {
		if (isVisibleDialogList()) {
			desactivateDialogList();
		}
		jDialogSettings.getContentPane().add(configs);
		jDialogSettings.getContentPane().setBackground(new Color(254, 240, 225));
		jDialogSettings.setVisible(true);
	}

	/**
	 * Metodo que muestra la subVentana de la lista de pokemones.
	 */
	public void showList(Object[][] listPokemons) {
		new Thread(()->{
			if (isVisibleDialogSettings()) {
				desactivateDialogSettings();
			}
			if (!isVisibleDialogList()) {
				configDialogListPokemons();
			}
			jDialogList.getContentPane()
					.add(new MyJTable(listPokemons, COLUMN_NAMES_LIST_POKEMONS, 30));
			jDialogList.getContentPane().setBackground(new Color(254, 240, 225));
			jDialogList.setVisible(true);
		}).start();
	}

	/**
	 * Metodo que cambia el cursor del dialog settings y dialog list.
	 * @param pathImageCursorRed.
	 * @throws IOException.
	 */
	public void changeCursorD(String path) throws IOException {
		Image cursor = ImageIO.read(getClass().getResource(path));
		Cursor myCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "my cursor");
		jDialogSettings.setCursor(myCursor);
		jDialogList.setCursor(myCursor);
	}

	/**
	 * Metodo que mueve a un jugador por el mapa
	 * @param point nueva ubicacion
	 * @param anim si el movimiento es animado o no
	 */
	public void movePlayer(Point point, boolean anim) {
		isMoving = true;
		if (!anim) {
			BufferedImage scene = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_RGB);
			scene.getGraphics().drawImage(mapb.getSubimage(0, 0, mapb.getWidth(), mapb.getHeight()), 0, 0, this);
			scene.getGraphics().drawImage(imagePlayer.getImage(), point.x, point.y, 50, 100, this);
			scene.getGraphics().fillOval(point.x, point.y + 100, 50, 5);
			putPokemonsInMap(scene);
			map.getGraphics().drawImage(scene, 0, 0, this);
			mapView = map.getSubimage(xOnMap, yOnMap, JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN);
			playerPos = point;
			isMoving = false;
			repaint();
		} else {
			animMovement(point);
		}
	}

	/**
	 * Metodo que pone la lista de pokemones al mapa
	 * @param list lista de pokemones
	 */
	public void putList(ArrayList<String[]> list) {
		this.listPokemons = list;
		repaint();
	}

	/**
	 * Metodo que mira si hay una interseccion entre el jugador y los pokemones
	 * @return true o false
	 */
	public boolean isIntersect() {
		String[] intersected = searchIntesect();
		if (intersected != null) {
			isIntersected = true;
			myListener.alertBattle(intersected, new String[] { jPanelControls.getLevel(), jPanelControls.getLife() });
			return true;
		}
		return false;
	}

	/**
	 * Metodo que busca la interseccion entre los pokemones en el mapa
	 * @return datos del pokemon
	 */
	private synchronized String[] searchIntesect() {
		for (String[] strings : listPokemons) {
			int h = Integer.parseInt(strings[1]);
			int k = Integer.parseInt(strings[2]);
			int w = Integer.parseInt(strings[3]);
			if (Math.pow(playerPos.x - (h + (w)), 2) + Math.pow(playerPos.y - k, 2) <= 2000) {
				return strings;
			} else if (Math.pow((playerPos.x + 100) - (h + (w/2)), 2) + Math.pow((playerPos.y + 50) - k, 2) <= 2000) {
				return strings;
			} else if (Math.pow((playerPos.x + 50) - (h + w/2), 2) + Math.pow(playerPos.y - k, 2) <= 2000) {
				return strings;
			} else if (Math.pow((playerPos.x + 50) - (h + w/2), 2) + Math.pow(playerPos.y + 50 - k, 2) <= 2000) {
				return strings;
			} else if (Math.pow((playerPos.x + 50) - (h + w/2), 2) + Math.pow(playerPos.y + 25 - k, 2) <= 2000) {
				return strings;
			}
		}
		return null;
	}

	/**
	 * Metodo que pone la lista de pokemones en la escena
	 * @param scene escena general
	 */
	public synchronized void putPokemonsInMap(BufferedImage scene) {
		for (String[] strings : listPokemons) {
			Image pokemonImage = new ImageIcon(
					getClass().getResource(PATHS_IMAGES_POKEMONS[Integer.parseInt(strings[0])])).getImage();
			;
			int x = Integer.parseInt(strings[1]);
			int y = Integer.parseInt(strings[2]);
			int w = Integer.parseInt(strings[3]);
			int h = Integer.parseInt(strings[4]);
			scene.getGraphics().drawImage(pokemonImage, x, y, w, h, this);
			scene.getGraphics().drawOval(x - w / 2, y - h / 4, w * 2, h * 2);
		}
	}

	/**
	 * Metodo que anima el movimiento del jugador
	 * @param point nueva ubicacion
	 */
	private void animMovement(Point point) {
		if (playerPos.x < point.x) {
			if (playerPos.y < point.y) {
				moveRigthDown(point);
			} else {
				moveRigthUp(point);
			}
		} else {
			if (playerPos.y < point.y) {
				moveLeftDown(point);
			} else {
				moveFullLeft(point);
			}
		}
	}

	/**
	 * Metodo que mueve el jugador a la izquierda y arriba
	 * @param point nueva ubicacion
	 */
	private void moveFullLeft(Point point) {
		new Thread(() -> {
			BufferedImage scene = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_RGB);
			LeftMovement(point, scene);
			upMovement(point, scene);
			isMoving = false;
			if (!isIntersected) {
				playerPos = point;
			}
		}).start();
	}

	/**
	 * Metodo que mueve al jugador a la izquierda
	 * @param point nueva ubicacion
	 * @param scene escena
	 */
	private void LeftMovement(Point point, BufferedImage scene) {
		for (int i = playerPos.x; i > point.x && isActive && !isIntersected; i -= 10) {
			scene.getGraphics().drawImage(mapb.getSubimage(0, 0, mapb.getWidth(), mapb.getHeight()), 0, 0, this);
			scene.getGraphics().drawImage(imagePlayer.getImage(), i, playerPos.y, 50, 100, this);
			scene.getGraphics().fillOval(point.x, point.y + 100, 50, 5);
			putPokemonsInMap(scene);
			if (isIntersect()) {
				playerPos = point;
				break;
			}
			map.getGraphics().drawImage(scene, 0, 0, this);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
			repaint();
			playerPos.x = i;
		}
	}

	/**
	 * Metodo que mueve el jugador a la izquierda y abajo
	 * @param point nueva ubicacion
	 */
	private void moveLeftDown(Point point) {
		new Thread(() -> {
			BufferedImage scene = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_RGB);
			LeftMovement(point, scene);
			downMovement(point, scene);
			isMoving = false;
			if (!isIntersected) {
				playerPos = point;
			}
		}).start();
	}

	/**
	 * Metodo que mueve al jugador hacia abajo
	 * @param point nueva ubicacion
	 * @param scene escena
	 */
	private void downMovement(Point point, BufferedImage scene) {
		for (int j = playerPos.y; j < point.y && isActive && !isIntersected; j += 10) {
			scene.getGraphics().drawImage(mapb.getSubimage(0, 0, mapb.getWidth(), mapb.getHeight()), 0, 0, this);
			scene.getGraphics().drawImage(imagePlayer.getImage(), playerPos.x, j, 50, 100, this);
			scene.getGraphics().fillOval(point.x, point.y + 100, 50, 5);
			putPokemonsInMap(scene);
			if (isIntersect()) {
				playerPos = point;
				break;
			}
			map.getGraphics().drawImage(scene, 0, 0, this);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
			repaint();
		}
	}

	/**
	 * Metodo que mueve el jugador a la derecha y arriba
	 * @param point nueva ubicacion
	 */
	private void moveRigthUp(Point point) {
		new Thread(() -> {
			BufferedImage scene = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_RGB);
			rigthMovement(point, scene);
			upMovement(point, scene);
			isMoving = false;
			if (!isIntersected) {
				playerPos = point;
			}
		}).start();
	}

	/**
	 * Metodo que mueve el jugador hacia arriba
	 * @param point nueva ubicacion
	 * @param scene escena
	 */
	private void upMovement(Point point, BufferedImage scene) {
		for (int j = playerPos.y; j > point.y && isActive && !isIntersected; j -= 10) {
			scene.getGraphics().drawImage(mapb.getSubimage(0, 0, mapb.getWidth(), mapb.getHeight()), 0, 0, this);
			scene.getGraphics().drawImage(imagePlayer.getImage(), playerPos.x, j, 50, 100, this);
			scene.getGraphics().fillOval(point.x, point.y + 100, 50, 5);
			putPokemonsInMap(scene);
			if (isIntersect()) {
				playerPos = point;
				break;
			}
			map.getGraphics().drawImage(scene, 0, 0, this);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
			repaint();
		}
	}

	/**
	 * Metodo que mueve el jugador a la derecha y abajo
	 * @param point nueva ubicacion
	 */
	private void moveRigthDown(Point point) {
		new Thread(() -> {
			BufferedImage scene = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_RGB);
			rigthMovement(point, scene);
			downMovement(point, scene);
			isMoving = false;
			if (!isIntersected) {
				playerPos = point;
			}
		}).start();
	}

	/**
	 * Metodo que mueve el jugador a la derecha
	 * @param point nueva ubicacion
	 * @param scene escena
	 */
	private void rigthMovement(Point point, BufferedImage scene) {
		for (int i = playerPos.x; i < point.x && isActive && !isIntersected; i += 10) {
			scene.getGraphics().drawImage(mapb.getSubimage(0, 0, mapb.getWidth(), mapb.getHeight()), 0, 0, this);
			scene.getGraphics().drawImage(imagePlayer.getImage(), i, playerPos.y, 50, 100, this);
			scene.getGraphics().fillOval(point.x, point.y + 100, 50, 5);
			putPokemonsInMap(scene);
			if (isIntersect()) {
				playerPos = point;
				break;
			}
			map.getGraphics().drawImage(scene, 0, 0, this);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
			repaint();
			playerPos.x = i;
		}
	}

	/**
	 * Metodo que carga las imagenes
	 */
	private void openImage() {
		new Thread(() -> {
			try {
				map = ImageIO.read(getClass().getResource(Constants.PATH_IMAGE_MAP));
				mapb = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_RGB);
				mapb.getGraphics().drawImage(map, 0, 0, this);
				this.xOnMap = (map.getWidth() / 2) - (JFrameMain.WIDTH_SCREEN / 2);
				this.yOnMap = (map.getHeight() / 2) - (JFrameMain.HEIGTH_SCREEN / 2);
				mapView = map.getSubimage(xOnMap, yOnMap, JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN);
				framePhoto = new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_FRAMEPHOTO)).getImage();
				charPhoto = new ImageIcon().getImage();
				ray = new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_FRAMEPHOTO_RAYO)).getImage();
				this.dimensionMap = new Dimension(map.getWidth(), map.getHeight());
			} catch (IOException e) {
			}
		}).start();
	}

	/**
	 * Metodo que añade el escuchador del listener de batalla
	 * @param myListener escuchador
	 */
	public void addMyEventListener(IBattleListener myListener) {
		this.myListener = myListener;
	}

	/**
	 * Metodo que restaura el mapa
	 */
	public void restoreMap() {
		map.getGraphics().drawImage(mapb, 0, 0, this);
		if (isVisibleDialogList()) {
			desactivateDialogList();
		}
		if (isVisibleDialogSettings()) {
			desactivateDialogSettings();
		}
	}

	/**
	 * Metodo que remueve un pokemon del mapa 
	 * @param x del pokemon
	 * @param y del pokemon
	 */
	public synchronized void removePokemon(String x, String y) {
		for (int i = 0; i < listPokemons.size(); i++) {
			if (listPokemons.get(i)[1].equals(x) && listPokemons.get(i)[2].equals(y) ) {
				listPokemons.remove(i);
				BufferedImage scene = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_RGB);
				scene.getGraphics().drawImage(mapb.getSubimage(0, 0, mapb.getWidth(), mapb.getHeight()), 0, 0, this);
				scene.getGraphics().drawImage(imagePlayer.getImage(), playerPos.x, playerPos.y, 50, 100, this);
				scene.getGraphics().fillOval(playerPos.x, playerPos.y + 100, 50, 5);
				putPokemonsInMap(scene);
				map.getGraphics().drawImage(scene, 0, 0, this);
				repaint();
				break;
			}
		}
		isIntersected = false;
		isActive = true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(mapView, 0, 0, this);

		g2d.drawImage(framePhoto, getWidth() - getHeight() / 4 - 10, getHeight() - getHeight() / 4 - getHeight() / 9,
				getHeight() / 4 + 10, getHeight() / 4 + getHeight() / 9, this);
		g2d.drawImage(charPhoto, getWidth() - getHeight() / 4 + 5, getHeight() - getHeight() / 4 + 5,
				getHeight() / 4 - 15, getHeight() / 4 - 15, this);
		g2d.drawImage(ray, getWidth() - (getHeight() / 3) - (getHeight() / 3) / 4,
				getHeight() - getHeight() / 4 - getHeight() / 10, getHeight() / 4 + 10,
				getHeight() / 4 + getHeight() / 10, this);
		super.paintComponent(g);
	}

	/**
	 * Metodo que actualiza el nivel del jugador
	 * @param level nuevo nivel
	 */
	public void updateLevel(int level) {
		jPanelControls.setLevel(level);
	}

	/**
	 * Metodo que actualiza la vida del jugador
	 * @param life nueva vida
	 */
	public void updateLife(int life) {
		jPanelControls.setLife(life);
	}

	/**
	 * Metodo que resetea las propiedades del juego
	 */
	public void resetProp() {
		isActive = true;
		isMoving = false;
		isIntersected = false;
		jPanelControls.setLife(100);
		jPanelControls.setLevel(1);
	}

	/**
	 * Metodo que actualiza los pokemones del mapa
	 * @param pokemonsData datos de los pokemones
	 */
	public void actualizeMap(ArrayList<String[]> pokemonsData) {
		this.listPokemons = pokemonsData;
		movePlayer(playerPos, false);
	}

}
