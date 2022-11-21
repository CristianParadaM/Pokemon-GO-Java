package view.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import controller.Controller;
import model.TextPrompt;
import view.Constants;
import view.utils.JButtonPokemon;

/**
 * Clase que maneja el objeto JPanelCharacterSel.java
 * 
 * @author CRISTIAN DAVID PARADA MARTINEZ
 * @date 2/10/2021
 */
public class JPanelCharacterSel extends JPanel {

	private static final Dimension PRES_SIZE = new Dimension(JFrameMain.WIDTH_SCREEN / 4 - 100,
			JFrameMain.HEIGTH_SCREEN * 720 / 1080);
	private static final long serialVersionUID = 1L;
	private static final int MAX_LENGTH_NAME = 10;
	private JLabel jLabelTittle;
	private JPanel jpanelButtons;
	private JPanel jpanelTittle;
	private JPanel jpanelNickName;
	private JButtonPokemon ashPresButton;
	private JButtonPokemon serenaPresButton;
	private JButtonPokemon brockPresButton;
	private JButtonPokemon gohPresButton;
	private GridBagConstraints gbc;
	private JTextField jtextFieldName;

	/**
	 * Constructor de la clase JPanelCharacterSel
	 */
	public JPanelCharacterSel() {
		super();
		this.jLabelTittle = new JLabel(Constants.MESSAGE_JLABEL_SELECTCHAR, JLabel.CENTER);
		this.ashPresButton = new JButtonPokemon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_ASHPR)),
				Constants.COMMAND_BUTTON_ASHPR, new Dimension(PRES_SIZE));
		this.serenaPresButton = new JButtonPokemon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_SERPR)),
				Constants.COMMAND_BUTTON_SERPR, new Dimension(PRES_SIZE));
		this.brockPresButton = new JButtonPokemon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_BROCKPR)),
				Constants.COMMAND_BUTTON_BROCKPR, new Dimension(PRES_SIZE));
		this.gohPresButton = new JButtonPokemon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_GOHPR)),
				Constants.COMMAND_BUTTON_GOHPR, new Dimension(PRES_SIZE));
		this.gbc = new GridBagConstraints();
		this.jpanelButtons = new JPanel();
		this.jtextFieldName = new JTextField();
		this.jpanelTittle = new JPanel();
		this.jpanelNickName = new JPanel();
		init();
	}

	/**
	 * Metodo que inicia las propiedades de este panel
	 */
	private void init() {
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.jpanelButtons.setOpaque(false);
		this.jpanelButtons.setLayout(new GridBagLayout());
		this.jpanelTittle.setLayout(new GridBagLayout());
		this.jpanelNickName.setLayout(new GridBagLayout());
		addTittle();
		addAshPresentation();
		addSerenaPresentation();
		addBrockPresentation();
		addGohPresentation();
		addTextField();
	}

	/**
	 * Metodo que adiciona el campo de texto y modifica sus propiedades
	 */
	private void addTextField() {
		GridBagConstraints gbc = new GridBagConstraints();
		JButtonPokemon buttonExit = new JButtonPokemon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_EXIT_MAP)),
				Constants.COMMAND_BUTTON_EXIT_SEL, new Dimension(50, 50));
		buttonExit.addActionListener(JFrameMain.getInstance());
		gbc.insets = new Insets(10, 100, 10, 10);
		this.jtextFieldName.setLayout(new BorderLayout());
		this.jtextFieldName.setFont(new Font("Ravie", Font.BOLD, 30));
		this.jtextFieldName.setForeground(Color.WHITE);
		this.jtextFieldName.setBackground(new Color(0, 0, 0, 0));
		this.jtextFieldName.setPreferredSize(new Dimension(JFrameMain.WIDTH_SCREEN / 2, 50));
		this.jtextFieldName.setBorder(new LineBorder(Color.WHITE, 3));
		this.jtextFieldName.setOpaque(false);
		this.jtextFieldName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ') {
					e.consume();
				}
				if (jtextFieldName.getText().length() == MAX_LENGTH_NAME) {
					e.consume();
				}
			}
		});
		JButton jButtonSend = new JButton("         >          ");
		jButtonSend.setContentAreaFilled(false);
		jButtonSend.setBorder(new LineBorder(Color.WHITE, 3));
		jButtonSend.setForeground(Color.WHITE);
		jButtonSend.setFont(new Font("Ravie", Font.BOLD, (int) ((JFrameMain.WIDTH_SCREEN * 20) / 1920)));
		jButtonSend.setSelected(false);
		jButtonSend.setFocusable(false);
		jButtonSend.setActionCommand(Constants.COMMAND_BUTTON_SEND);
		jButtonSend.addActionListener(Controller.getInstance());
		TextPrompt textPrompt = new TextPrompt(Constants.MESSAGE_PLACEHOLDER_USERNAME, jtextFieldName);
		this.jtextFieldName.add(jButtonSend, BorderLayout.EAST);
		textPrompt.changeAlpha(0.90f);
		textPrompt.changeStyle(Font.ITALIC);
		jpanelNickName.setOpaque(false);
		jpanelNickName.add(jtextFieldName, gbc);
		gbc.insets = new Insets(10, 0, 10, 20);
		gbc.gridx = 1;
		jpanelNickName.add(buttonExit, gbc);
		this.add(jpanelNickName, BorderLayout.SOUTH);
	}

	/**
	 * Metodo que adiciona el boton presentacion de ash
	 */
	private void addAshPresentation() {
		ashPresButton.addActionListener(JFrameMain.getInstance());
		ashPresButton.setName("ash");
		jpanelButtons.add(ashPresButton, gbc);
	}

	/**
	 * Metodo que adiciona el boton presentacion de Serena
	 */
	private void addSerenaPresentation() {
		gbc.gridx = 1;
		serenaPresButton.addActionListener(JFrameMain.getInstance());
		serenaPresButton.setName("serena");
		jpanelButtons.add(serenaPresButton, gbc);
	}

	/**
	 * Metodo que adiciona el boton presentacion de Brock
	 */
	private void addBrockPresentation() {
		gbc.gridx = 2;
		brockPresButton.addActionListener(JFrameMain.getInstance());
		brockPresButton.setName("brock");
		jpanelButtons.add(brockPresButton, gbc);
	}

	/**
	 * Metodo que adiciona el boton presentacion de Goh
	 */
	private void addGohPresentation() {
		gbc.gridx = 3;
		gohPresButton.addActionListener(JFrameMain.getInstance());
		gohPresButton.setName("goh");
		jpanelButtons.add(gohPresButton, gbc);
		this.add(jpanelButtons, BorderLayout.CENTER);
	}

	/**
	 * Metodo que deselecciona los botones de presentacion
	 */
	public void deselectAll() {
		ashPresButton.setBorderPainted(false);
		serenaPresButton.setBorderPainted(false);
		brockPresButton.setBorderPainted(false);
		gohPresButton.setBorderPainted(false);
		jtextFieldName.setText("");
	}

	/**
	 * Metodo que selecciona un boton
	 * @param nameButton nombre del boton
	 */
	public void select(String nameButton) {
		switch (nameButton) {
		case "ash":
			ashPresButton.setBorder(new LineBorder(Color.GREEN, 5));
			ashPresButton.setBorderPainted(true);
			serenaPresButton.setBorderPainted(false);
			brockPresButton.setBorderPainted(false);
			gohPresButton.setBorderPainted(false);
			break;
		case "serena":
			serenaPresButton.setBorder(new LineBorder(Color.GREEN, 5));
			serenaPresButton.setBorderPainted(true);
			ashPresButton.setBorderPainted(false);
			brockPresButton.setBorderPainted(false);
			gohPresButton.setBorderPainted(false);
			break;
		case "brock":
			brockPresButton.setBorder(new LineBorder(Color.GREEN, 5));
			brockPresButton.setBorderPainted(true);
			ashPresButton.setBorderPainted(false);
			serenaPresButton.setBorderPainted(false);
			gohPresButton.setBorderPainted(false);
			break;
		case "goh":
			gohPresButton.setBorder(new LineBorder(Color.GREEN, 5));
			gohPresButton.setBorderPainted(true);
			ashPresButton.setBorderPainted(false);
			serenaPresButton.setBorderPainted(false);
			brockPresButton.setBorderPainted(false);
			break;
		}
	}

	/**
	 * Metodo que adiciona el titulo
	 */
	private void addTittle() {
		GridBagConstraints gbc = new GridBagConstraints();
		this.jLabelTittle.setFont(new Font("Ravie", Font.BOLD, (int) ((JFrameMain.HEIGTH_SCREEN * 80) / 1080)));
		this.jLabelTittle.setForeground(Color.WHITE);
		jpanelTittle.setOpaque(false);
		gbc.insets.top = 50;
		jpanelTittle.add(jLabelTittle, gbc);
		this.add(jpanelTittle, BorderLayout.NORTH);
	}

	/**
	 * Metodo que devuelve que boton fue seleccionado
	 * @return nombre del personaje
	 */
	public String getSelectedChar() {
		if (ashPresButton.isBorderPainted()) {
			return "Ash";
		} else if (serenaPresButton.isBorderPainted()) {
			return "Serena";
		} else if (brockPresButton.isBorderPainted()) {
			return "Brock";
		} else if (gohPresButton.isBorderPainted()) {
			return "Goh";
		}
		return "none";
	}

	/**
	 * Metodo que devuelve lo digitado en el textField
	 * @return apodo del jugador
	 */
	public String getNickName() {
		return jtextFieldName.getText();
	}

}
