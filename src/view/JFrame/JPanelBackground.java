package view.JFrame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import view.Constants;

/**
 * Clase que maneja el objeto JPanelBackground.java
 * 
 * @author CRISTIAN DAVID PARADA MARTINEZ
 * @date 27/09/2021
 */
public class JPanelBackground extends JPanel {

	private static final long serialVersionUID = 1L;
	private Clip music;
	private boolean isMuted;

	/**
	 * Constructor de la clase JPanelBackground
	 */
	public JPanelBackground() {
		this.isMuted = false;
		init();
	}
	
	/**
	 * Metodo que inicia la musica por defecto
	 */
	public void startMusic() {
		try {
			InputStream audioSrc = getClass().getResourceAsStream(Constants.PATH_AUDIO_SOUND_DAL);
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			music = AudioSystem.getClip();
			music.open(audioStream);
			music.start();
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
		}
	}

	/**
	 * Metodo que detiene la musica
	 */
	public void stopMusic() {
		music.stop();
	}

	/**
	 * Metodo que inicia los componentes
	 */
	private void init() {
		try {
			this.setOpaque(false);
			changeCursor(Constants.PATH_IMAGE_CURSOR_BLUE);
		} catch (IOException | URISyntaxException e) {
		}
	}

	/**
	 * Metodo que cambia el cursor
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void changeCursor(String path) throws IOException, URISyntaxException {
		BufferedImage cursor = ImageIO.read(getClass().getResource(path));
		Cursor myCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "my cursor");
		this.setCursor(myCursor);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(new ImageIcon(getClass().getResource(Constants.PATH_GIF_BACKGROUND)).getImage(), 0, 0, getWidth(),
				getHeight(), this);
		g2d.setFont(new Font("Ravie", Font.BOLD, (int) ((JFrameMain.WIDTH_SCREEN * 40) / 1920)));
		g2d.setColor(Color.WHITE);
		g2d.drawString("   Version 1.0", 0, getHeight() - 50);
		super.paint(g);
	}

	/**
	 * Metodo que verifica si la musica esta muteada y la desmutea o mutea
	 * @return true o false
	 */
	public boolean isMuted() {
		if (!isMuted) {
			mute();
		} else {
			UnMute();
		}
		return isMuted;
	}

	/**
	 * Metodo que mutea la musica
	 */
	private void mute() {
		BooleanControl volume = (BooleanControl) music.getControl(BooleanControl.Type.MUTE);
		volume.setValue(true);
		isMuted = true;
	}

	/**
	 * Metodo que desmutea la musica
	 */
	private void UnMute() {
		BooleanControl volume = (BooleanControl) music.getControl(BooleanControl.Type.MUTE);
		volume.setValue(false);
		isMuted = false;
	}

	/**
	 * Metodo que cambia la cancion de fondo
	 * @param pathAudioSound ruta
	 */
	public void switchMusic(String pathAudioSound) {
		music.stop();
		try {
			try {
				InputStream audioSrc = getClass().getResourceAsStream(pathAudioSound);
				InputStream bufferedIn = new BufferedInputStream(audioSrc);
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
				music = AudioSystem.getClip();
				music.open(audioStream);
			} catch (IOException | UnsupportedAudioFileException e) {
			}
			music.start();
		} catch (LineUnavailableException e) {
		}
	}

}
