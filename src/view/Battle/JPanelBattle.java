package view.Battle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Random;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import view.Constants;
import view.JFrame.JFrameMain;
import view.utils.MyJProgressBar;
import view.utils.IGameListener;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Clase que maneja el objeto JPanelBattle.java
 * 
 * @author CRISTIAN DAVID PARADA MARTINEZ
 * @date 3/10/2021
 */
public class JPanelBattle extends JPanel implements MouseListener {

	private static final int TOTAL_lIFE_PLAYER = 1000;
	private static final int LEVEL_FIVE = 1000;
	private static final int LEVEL_FOUR = 2000;
	private static final int LEVEL_THREE = 3000;
	private static final int LEVEL_TWO = 4000;
	private static final int LEVEL_ONE = 5000;
	private static final long serialVersionUID = 1L;
	private BufferedImage imageBackGround;
	private BufferedImage imageBackGroundB;
	private int totalLifePokemon;
	private int lifePokemon;
	private int lifePlayer;
	private int xOnScene;
	private int yOnScene;
	private int size;
	private int levelDifficult;
	private boolean isMuted;
	private String[] pokemonData;
	private String pathMusic;
	private BufferedImage scene;
	private MyJProgressBar jProgressBarPlayer;
	private MyJProgressBar jProgressBarPokemon;
	private Timer timerDamage;
	private JLabel jLabelPokemonLife;
	private JLabel jLabelYourLife;
	private IGameListener iGame;
	private Clip musicAtack;

	/**
	 * Constructor de la clase JPanelBattle
	 */
	public JPanelBattle(String[] pokemonData, String[] playerData, boolean isMuted) {
		super();
		this.pokemonData = pokemonData;
		this.imageBackGround = new BufferedImage(JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN,
				BufferedImage.TYPE_INT_RGB);
		this.imageBackGroundB = new BufferedImage(JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN,
				BufferedImage.TYPE_INT_RGB);
		this.levelDifficult = Integer.parseInt(playerData[0]);
		this.lifePlayer = Integer.parseInt(playerData[1]);
		this.jLabelPokemonLife = new JLabel();
		this.jLabelYourLife = new JLabel();
		this.isMuted = isMuted;
		init();
	}

	/**
	 * Metodo que
	 */
	private void init() {
		this.setOpaque(true);
		this.addMouseListener(this);
		openBattle();
		chooseBackGround();
		paintScene();
		paintLife();
		startTimerDamage();
	}

	/**
	 * Metodo que
	 */
	private void openBattle() {
		new Thread(() -> {
			try {
				this.setBackground(Color.BLACK);
				Thread.sleep(1000);
				for (int i = 0; i < 250; i++) {
					this.setBackground(new Color(0, 0, 0, 250 - i));
					Thread.sleep(5);
				}
			} catch (InterruptedException e) {
			}
		}).start();
	}

	/**
	 * Metodo que
	 */
	private void startTimerDamage() {
		int level = levelDifficult == 1 ? LEVEL_ONE
				: levelDifficult == 2 ? LEVEL_TWO
						: levelDifficult == 3 ? LEVEL_THREE : levelDifficult == 4 ? LEVEL_FOUR : LEVEL_FIVE;

		timerDamage = new Timer(level, (x) -> {
			lifePlayer -= Integer.parseInt(pokemonData[5]);
			hitPlayer();
			if (!isMuted) {
				startMusic();
			}
			updateLifes();
		});
		timerDamage.start();
	}

	/**
	 * Metodo que
	 */
	private void paintScene() {
		switch (pokemonData[0]) {
		case "0" -> paintPokemon(optionPikachu());
		case "1" -> paintPokemon(optionSquirtle());
		case "2" -> paintPokemon(optionJiggle());
		case "3" -> paintPokemon(optionCharmander());
		case "4" -> paintPokemon(optionBulbasaur());
		case "5" -> paintPokemon(optionSnorlax());
		case "6" -> paintPokemon(optionMewTwo());
		case "7" -> paintPokemon(optionEkans());
		}
	}

	public void startMusic() {
		try {
			musicAtack = AudioSystem.getClip();
			musicAtack.open(AudioSystem
					.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(pathMusic))));
			musicAtack.start();
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
		}
	}

	public void stopMusic() {
		musicAtack.stop();
	}

	/**
	 * Metodo que
	 */
	private void paintLife() {
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		gbc.fill = 1;
		gbc.weightx = 1;
		gbc.insets = new Insets(0, 100, 0, 100);
		jLabelYourLife.setText("Tu fuerza:");
		jLabelYourLife.setHorizontalAlignment(JLabel.CENTER);
		jLabelYourLife.setForeground(Color.WHITE);
		jLabelYourLife.setFont(new Font("Ravie", Font.BOLD, (int) ((JFrameMain.WIDTH_SCREEN * 40) / 1920)));
		this.add(jLabelYourLife, gbc);
		jProgressBarPlayer = new MyJProgressBar();
		jProgressBarPlayer.setValue(lifePlayer * 100 / TOTAL_lIFE_PLAYER);
		gbc.gridy = 1;
		this.add(jProgressBarPlayer, gbc);
		gbc.gridy = 2;
		this.add(Box.createRigidArea(new Dimension(0, (int) ((JFrameMain.HEIGTH_SCREEN * 1550) / 1920))), gbc);
		gbc.gridy = 3;
		jLabelPokemonLife.setForeground(Color.RED);
		jLabelPokemonLife.setFont(new Font("Ravie", Font.BOLD, (int) ((JFrameMain.WIDTH_SCREEN * 40) / 1920)));
		this.add(jLabelPokemonLife, gbc);
		gbc.gridy = 4;
		jProgressBarPokemon = new MyJProgressBar();
		jProgressBarPokemon.setValue(lifePokemon * 100 / totalLifePokemon);
		this.add(jProgressBarPokemon, gbc);
	}

	/**
	 * Metodo que
	 */
	private void chooseBackGround() {
		int number = new Random().nextInt(3);
		switch (number) {
		case 0 -> imageBackGround.getGraphics()
				.drawImage(new ImageIcon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BACKGROUND_BATTLE1))
						.getImage()
						.getScaledInstance(JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN, Image.SCALE_SMOOTH))
								.getImage(),
						0, 0, this);
		case 1 -> imageBackGround.getGraphics()
				.drawImage(new ImageIcon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BACKGROUND_BATTLE2))
						.getImage()
						.getScaledInstance(JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN, Image.SCALE_SMOOTH))
								.getImage(),
						0, 0, this);
		case 2 -> imageBackGround.getGraphics()
				.drawImage(new ImageIcon(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BACKGROUND_BATTLE3))
						.getImage()
						.getScaledInstance(JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN, Image.SCALE_SMOOTH))
								.getImage(),
						0, 0, this);
		}
		imageBackGround.getGraphics().drawImage(imageBackGround, 0, 0, JFrameMain.WIDTH_SCREEN,
				JFrameMain.HEIGTH_SCREEN, this);
		scene = imageBackGround.getSubimage(0, 0, JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN);
		imageBackGroundB.getGraphics().drawImage(imageBackGround, 0, 0, JFrameMain.WIDTH_SCREEN,
				JFrameMain.HEIGTH_SCREEN, this);
	}

	/**
	 * Metodo que pinta el pokemon y lo mueve
	 */
	private void paintPokemon(ImageIcon pokemonImage) {
		new Thread(() -> {
			size = JFrameMain.HEIGTH_SCREEN * (Integer.parseInt(pokemonData[3]) * 6) / 1080;
			yOnScene = JFrameMain.HEIGTH_SCREEN / 2 - size;
			int velocity = levelDifficult == 1 ? 8 : levelDifficult == 2 ? 15 : levelDifficult == 3 ? 20 : 30;
			while (lifePlayer > 0 && lifePokemon > 0) {
				xOnScene = 0;
				moveRigth(pokemonImage, velocity);
				moveLeft(pokemonImage, velocity);
			}
			finishBattle();
		}).start();
	}

	/**
	 * Metodo que mueve el pokemon a la izquierda
	 * @param pokemonImage imagen del pokemon
	 * @param velocity velocidad
	 */
	private void moveLeft(ImageIcon pokemonImage, int velocity) {
		for (int i = JFrameMain.WIDTH_SCREEN - size; i >= 0 && lifePlayer > 0 && lifePokemon > 0; i -= velocity) {
			BufferedImage sceneAux = new BufferedImage(JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN,
					BufferedImage.TYPE_INT_RGB);
			sceneAux.getGraphics().drawImage(imageBackGroundB, 0, 0, this);
			sceneAux.getGraphics().drawImage(pokemonImage.getImage(), i, yOnScene, size, size, this);
			sceneAux.getGraphics().drawImage(
					new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_POKEMON_POKEBALL)).getImage(),
					JFrameMain.WIDTH_SCREEN / 2 - 100, JFrameMain.HEIGTH_SCREEN / 3 * 2 - 100, 200, 200, this);
			scene.getGraphics().drawImage(sceneAux, 0, 0, this);
			repaint();
			xOnScene -= velocity;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			repaint();
		}
	}

	/**
	 * Metodo que mueve el pokemon a la derecha
	 * @param pokemonImage imagen del pokemon
	 * @param velocity velocidad
	 */
	private void moveRigth(ImageIcon pokemonImage, int velocity) {
		for (int i = xOnScene; i <= JFrameMain.WIDTH_SCREEN - size && lifePlayer > 0
				&& lifePokemon > 0; i += velocity) {
			BufferedImage sceneAux = new BufferedImage(JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN,
					BufferedImage.TYPE_INT_RGB);
			sceneAux.getGraphics().drawImage(imageBackGroundB, 0, 0, this);
			sceneAux.getGraphics().drawImage(pokemonImage.getImage(), i, yOnScene, size, size, this);
			sceneAux.getGraphics().drawImage(
					new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_POKEMON_POKEBALL)).getImage(),
					JFrameMain.WIDTH_SCREEN / 2 - 100, JFrameMain.HEIGTH_SCREEN / 3 * 2 - 100, 200, 200, this);
			scene.getGraphics().drawImage(sceneAux, 0, 0, this);
			repaint();
			xOnScene += velocity;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Metodo que pone las propiedades de mewTwo
	 * @return Imagen del personaje
	 */
	private ImageIcon optionMewTwo() {
		lifePokemon = totalLifePokemon = 3000; // TODO poner esta vida en el modelo y poner dato default data[7]
		jLabelPokemonLife.setText("Mew Two");
		jLabelPokemonLife.setHorizontalAlignment(JLabel.CENTER);
		this.pathMusic = Constants.PATH_AUDIO_ATACK_MEWTWO;
		return new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_POKEMON_MEWTWO));
	}

	/**
	 * Metodo que pone las propiedades de ekans
	 * @return Imagen del personaje
	 */
	private ImageIcon optionEkans() {
		lifePokemon = totalLifePokemon = 1200; // TODO
		jLabelPokemonLife.setText("Ekans");
		jLabelPokemonLife.setHorizontalAlignment(JLabel.CENTER);
		this.pathMusic = Constants.PATH_AUDIO_ATACK_EKANS;
		return new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_POKEMON_EKANS));
	}

	/**
	 * Metodo que pone las propiedades de Snorlax
	 * @return Imagen del personaje
	 */
	private ImageIcon optionSnorlax() {
		lifePokemon = totalLifePokemon = 2000; // TODO
		jLabelPokemonLife.setText("Snorlax");
		jLabelPokemonLife.setHorizontalAlignment(JLabel.CENTER);
		this.pathMusic = Constants.PATH_AUDIO_ATACK_SNORLAX;
		return new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_POKEMON_SNORLAX));
	}

	/**
	 * Metodo que pone las propiedades de Bulbasaur
	 * @return Imagen del personaje
	 */
	private ImageIcon optionBulbasaur() {
		lifePokemon = totalLifePokemon = 1500; // TODO
		jLabelPokemonLife.setText("Bulbasaur");
		jLabelPokemonLife.setHorizontalAlignment(JLabel.CENTER);
		this.pathMusic = Constants.PATH_AUDIO_ATACK_BULBASAUR;
		return new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_POKEMON_BULBASAUR));
	}

	/**
	 * Metodo que pone las propiedades de Charmander
	 * @return Imagen del personaje
	 */
	private ImageIcon optionCharmander() {
		lifePokemon = totalLifePokemon = 900;
		jLabelPokemonLife.setText("Charmander");
		jLabelPokemonLife.setHorizontalAlignment(JLabel.CENTER);
		this.pathMusic = Constants.PATH_AUDIO_ATACK_CHAR;
		return new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_POKEMON_CHARMANDER));
	}

	/**
	 * Metodo que pone las propiedades de JigglyPugg
	 * @return Imagen del personaje
	 */
	private ImageIcon optionJiggle() {
		lifePokemon = totalLifePokemon = 500;
		jLabelPokemonLife.setText("Jigglypugg");
		jLabelPokemonLife.setHorizontalAlignment(JLabel.CENTER);
		this.pathMusic = Constants.PATH_AUDIO_ATACK_JIGGLY;
		return new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_POKEMON_JIGGLYPUGG));
	}

	/**
	 * Metodo que pone las propiedades de Squirtle
	 * @return Imagen del personaje
	 */
	private ImageIcon optionSquirtle() {
		lifePokemon = totalLifePokemon = 1500;
		jLabelPokemonLife.setText("Squirtle");
		jLabelPokemonLife.setHorizontalAlignment(JLabel.CENTER);
		this.pathMusic = Constants.PATH_AUDIO_ATACK_SQUIRTLE;
		return new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_POKEMON_SQUIRTLE));
	}

	/**
	 * Metodo que pone las propiedades de Pikachu
	 * @return Imagen del personaje
	 */
	private ImageIcon optionPikachu() {
		lifePokemon = totalLifePokemon = 1200;
		jLabelPokemonLife.setText("Pikachu");
		jLabelPokemonLife.setHorizontalAlignment(JLabel.CENTER);
		this.pathMusic = Constants.PATH_AUDIO_ATACK_PIK;
		return new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_POKEMON_PIKACHU));
	}

	/**
	 * Metodo que finaliza una batalla
	 */
	private void finishBattle() {
		this.setLayout(new BorderLayout());
		this.jProgressBarPlayer.setVisible(false);
		this.jProgressBarPokemon.setVisible(false);
		String pokemon = jLabelPokemonLife.getText();
		this.jLabelPokemonLife.setVisible(false);
		this.jLabelYourLife.setVisible(false);
		timerDamage.stop();
		if (lifePokemon <= 0) {
			JLabel end = new JLabel("<html><center>Has Capturado a " + pokemon + "</center></html>", JLabel.CENTER);
			end.setForeground(Color.WHITE);
			end.setFont(new Font("Ravie", Font.BOLD, (int) ((JFrameMain.WIDTH_SCREEN * 180) / 1920)));
			this.add(end, BorderLayout.CENTER);
			iGame.win(pokemonData, new String[] { levelDifficult + "", lifePlayer + "" });
		} else {
			gameOver();
			JLabel end = new JLabel("<html><center> Oh no !! Te Han vencido<center><html>", JLabel.CENTER);
			end.setForeground(Color.WHITE);
			end.setFont(new Font("Ravie", Font.BOLD, (int) ((JFrameMain.WIDTH_SCREEN * 180) / 1920)));
			this.add(end);
			iGame.lose(pokemonData, new String[] { levelDifficult + "", lifePlayer + "" });
		}
	}

	/**
	 * Metodo que si pierde cierra la escena con una pantalla en negro
	 */
	private void gameOver() {
		new Thread(() -> {
			try {
				Thread.sleep(1000);
				for (int i = 0; i < 250; i++) {
					this.setBackground(new Color(0, 0, 0, i));
					Thread.sleep(10);
				}
				this.setBackground(Color.BLACK);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}).start();
	}

	/**
	 * Metodo que pone el efecto rojo de golpe en la pantalla
	 */
	private void hitPlayer() {
		new Thread(() -> {
			try {
				this.setBackground(Color.RED);
				Thread.sleep(10);
				for (int i = 0; i < 175; i++) {
					this.setBackground(new Color(255, 0, 0, 175 - i));
					Thread.sleep(1);
				}
			} catch (InterruptedException e) {
			}
		}).start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(scene, 0, 0, this);
		super.paintComponent(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public synchronized void mouseReleased(MouseEvent e) {
		if (lifePlayer > 0 && lifePokemon > 0) {
			if (e.getXOnScreen() >= xOnScene && e.getXOnScreen() <= (xOnScene + size) && e.getYOnScreen() >= yOnScene
					&& e.getYOnScreen() <= (yOnScene + size)) {
				lifePokemon -= 100;
				updateLifes();
			}
			BufferedImage sceneAux = new BufferedImage(JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN,
					BufferedImage.TYPE_INT_RGB);
			sceneAux.getGraphics().drawImage(imageBackGroundB, 0, 0, this);
			sceneAux.getGraphics().drawImage(
					new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_POKEMON_POKEBALL)).getImage(),
					e.getXOnScreen(), e.getYOnScreen(), 50, 50, this);
			scene.getGraphics().drawImage(sceneAux, 0, 0, this);
			repaint();
		}
	}

	/**
	 * Metodo que actualiza las barras de vida
	 */
	private void updateLifes() {
		if (lifePokemon <= 0) {
			timerDamage.stop();
		}
		animlifePlayer();
		animLifePokemon();
	}

	/**
	 * Metodo que actualiza la vida del pokemon
	 */
	private void animLifePokemon() {
		new Thread(() -> {
			for (int i = jProgressBarPlayer.getValue(); i >= lifePlayer * 100 / 1000; i--) {
				jProgressBarPlayer.setValue(i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
		}).start();
	}

	/**
	 * Metodo que actualiza la vida del jugador
	 */
	private void animlifePlayer() {
		new Thread(() -> {
			try {
				for (int i = jProgressBarPokemon.getValue(); i >= lifePokemon * 100 / totalLifePokemon; i--) {
					jProgressBarPokemon.setValue(i);
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
			}
		}).start();
	}

	/**
	 * Metodo que adiciona el listener de juegox
	 * @param iGame
	 */
	public void addIGame(IGameListener iGame) {
		this.iGame = iGame;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
