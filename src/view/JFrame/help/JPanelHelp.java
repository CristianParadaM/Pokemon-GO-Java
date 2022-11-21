package view.JFrame.help;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.Constants;
import view.JFrame.JFrameMain;
import view.JFrame.JPanelInfo;
import view.utils.JButtonPokemon;

/**
 * Clase que maneja el objeto JPanelHelp.java
 * 
 * @author CRISTIAN DAVID PARADA MARTINEZ
 * @date 9/10/2021
 */
public class JPanelHelp extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jlabelHelp1;
	private JLabel jlabelHelp2;
	private JLabel jlabelHelp3;
	private JLabel jlabelHelp4;
	private JLabel jlabelHelp5;
	private JButtonPokemon buttonPokemon;

	/**
	 * Constructor de la clase JPanelHelp
	 */
	public JPanelHelp() {
		super();
		this.setPreferredSize(new Dimension(JFrameMain.WIDTH_SCREEN, JFrameMain.HEIGTH_SCREEN * 5 + (JFrameMain.HEIGTH_SCREEN*310/1080)));
		this.jlabelHelp1 = new JLabel(new ImageIcon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_HELP_ONE)).getImage().getScaledInstance(
						JFrameMain.WIDTH_SCREEN - 20, JFrameMain.HEIGTH_SCREEN, Image.SCALE_SMOOTH)));
		this.jlabelHelp2 = new JLabel(new ImageIcon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_HELP_TWO)).getImage().getScaledInstance(
						JFrameMain.WIDTH_SCREEN - 20, JFrameMain.HEIGTH_SCREEN, Image.SCALE_SMOOTH)));
		this.jlabelHelp3 = new JLabel(new ImageIcon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_HELP_THREE)).getImage().getScaledInstance(
						JFrameMain.WIDTH_SCREEN - 20, JFrameMain.HEIGTH_SCREEN, Image.SCALE_SMOOTH)));
		this.jlabelHelp4 = new JLabel(new ImageIcon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_HELP_FOUR)).getImage().getScaledInstance(
						JFrameMain.WIDTH_SCREEN - 20, JFrameMain.HEIGTH_SCREEN, Image.SCALE_SMOOTH)));
		this.jlabelHelp5 = new JLabel(new ImageIcon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_HELP_FIVE)).getImage().getScaledInstance(
						JFrameMain.WIDTH_SCREEN - 20, JFrameMain.HEIGTH_SCREEN, Image.SCALE_SMOOTH)));
		this.buttonPokemon = new JButtonPokemon(
				new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BUTTON_PLAY_TWO)), Constants.COMMAND_BUTTON_PLAY_TWO,
				new Dimension(JPanelInfo.WIDTH_BUTTONS, JPanelInfo.HEITH_BUTTONS));
		init();
	}

	/**
	 * Metodo que adiciona los componentes a este panel usando coordenadas nulas respectivas a la pantalla
	 */
	private void init() {
		this.setOpaque(false);
		this.setLayout(null);
		this.add(jlabelHelp1).setBounds(5, 0, JFrameMain.WIDTH_SCREEN - 20, JFrameMain.HEIGTH_SCREEN);
		this.add(jlabelHelp2).setBounds(5, jlabelHelp1.getHeight(), JFrameMain.WIDTH_SCREEN - 20,
				jlabelHelp1.getHeight());
		this.add(jlabelHelp3).setBounds(5, (2 * jlabelHelp2.getHeight()), JFrameMain.WIDTH_SCREEN - 20,
				jlabelHelp2.getHeight());
		this.add(jlabelHelp4).setBounds(5, (3 * jlabelHelp3.getHeight()), JFrameMain.WIDTH_SCREEN - 20,
				jlabelHelp3.getHeight());
		this.add(jlabelHelp5).setBounds(5, (4 * jlabelHelp4.getHeight()), JFrameMain.WIDTH_SCREEN - 20,
				jlabelHelp4.getHeight());
		this.buttonPokemon.addActionListener(JFrameMain.getInstance());
		this.add(buttonPokemon).setBounds(JFrameMain.WIDTH_SCREEN/2 -JPanelInfo.WIDTH_BUTTONS/2, (50*JFrameMain.HEIGTH_SCREEN/1080) + (5 * jlabelHelp4.getHeight()), JPanelInfo.WIDTH_BUTTONS,
				JPanelInfo.HEITH_BUTTONS);
	}
}
