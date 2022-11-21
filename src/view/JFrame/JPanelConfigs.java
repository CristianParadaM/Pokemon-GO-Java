package view.JFrame;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.Constants;

/** Clase que maneja el objeto JPanelConfigs.java
 *  @author CRISTIAN DAVID PARADA MARTINEZ
 *  @date 3/10/2021
 */
public class JPanelConfigs extends JPanel {

	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc;
	private JLabel changeMusic;
	private JLabel changeCursor;
	private JComboBox<String> musics;
	private JComboBox<String> cursors;

	/**
	 * Constructor de la clase JPanelConfigs
	 */
	public JPanelConfigs() {
		super();
		this.gbc = new GridBagConstraints();
		this.changeMusic = new JLabel("<html><center>"+Constants.MESSAGE_JLABEL_CHANGEMUSIC+"<center></html>", JLabel.CENTER);
		this.changeCursor = new JLabel(Constants.MESSAGE_JLABEL_CHANGECURSOR, JLabel.CENTER);
		this.musics = new JComboBox<>();
		this.cursors = new JComboBox<>();
		init();
	}

	/**
	 * Metodo que inicializa las propiedades de este panel
	 */
	private void init() {
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.musics.addItemListener(JFrameMain.getInstance());
		this.cursors.addItemListener(JFrameMain.getInstance());
		addComponents();
	}

	/**
	 * Metodo que adiciona los componentes
	 */
	private void addComponents() {
		gbc.insets.top = 10;
		gbc.insets.bottom = 20;
		this.changeMusic.setOpaque(false);
		this.musics.setOpaque(false);
		this.cursors.setOpaque(false);
		this.changeMusic.setFont(new Font("Ravie", Font.BOLD, (int)((JFrameMain.WIDTH_SCREEN * 30 )/ 1920)));
		this.changeMusic.setForeground(new Color(0, 139, 255));
		this.add(changeMusic, gbc);
		this.musics.setFont(new Font("Ravie", Font.BOLD, (int)((JFrameMain.WIDTH_SCREEN * 20 )/ 1920)));
		this.musics.setForeground(Color.BLACK);
		this.cursors.setFont(new Font("Ravie", Font.BOLD, (int)((JFrameMain.WIDTH_SCREEN * 20 )/ 1920)));
		this.cursors.setForeground(Color.BLACK);
		gbc.gridy =1;
		this.musics.addItem("Dialga's Fight to the Finish");
		this.musics.addItem("Mewmore Emotion");
		this.musics.addItem("Mythical Pokemon");
		this.musics.addItem("Treachery");
		this.musics.addItem("Victory");
		gbc.insets.top = 0;
		gbc.insets.bottom = 100;
		this.add(musics, gbc);
		gbc.insets.bottom = 20;
		gbc.gridy =2;
		this.changeCursor.setFont(new Font("Ravie", Font.BOLD, (int)((JFrameMain.WIDTH_SCREEN * 30 )/ 1920)));
		this.changeCursor.setForeground(new Color(0, 139, 255));
		this.changeCursor.setOpaque(false);
		this.cursors.addItem("Cursor Blue");
		this.cursors.addItem("Cursor Red");
		this.cursors.addItem("Cursor Pink");
		this.cursors.addItem("Cursor Yellow");
		this.add(changeCursor, gbc);
		gbc.gridy = 3;
		gbc.insets.bottom = 10;
		this.add(cursors, gbc);
	}
}
