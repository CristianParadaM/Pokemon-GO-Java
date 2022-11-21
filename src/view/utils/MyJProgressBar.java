package view.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * Clase que maneja el objeto MyJProgressBar.java
 * Barra de progreso personalizada 
 * @author Cristian David Parada Martinez
 * @date 19/05/2021
 *
 */
public class MyJProgressBar extends JProgressBar {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de Bar
	 */
	public MyJProgressBar() {
		super();
		init();
	}

	/**
	 * Metodo que inicializa las propiedades de la barra de progreso
	 */
	private void init() {
		this.setLayout(new GridLayout());
		this.setStringPainted(true);
		this.setBorderPainted(false);
		this.setOpaque(false);
		this.setForeground(new Color(0, 193, 35));
		this.setUI(new BasicProgressBarUI() {

			@Override
			protected void paintDeterminate(Graphics g, JComponent c) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				paintBackGround(c, g2d);
				paintProgress(c, g2d);
				paintStringProgress(c, g2d);
			}

			private void paintStringProgress(JComponent c, Graphics2D g2d) {
				g2d.setFont(new Font("Arial", 0, 15));
				g2d.setColor(Color.WHITE);
				String progress = ((int) (progressBar.getPercentComplete() * 100) + "%");
				g2d.drawString(progress, c.getWidth() / 2 - 15, 18);
			}

			private void paintProgress(JComponent c, Graphics2D g2d) {
				g2d.setPaint(new GradientPaint(0, 0, new Color(65, 119, 224).darker(), c.getWidth(), 0,
						new Color(57, 255, 193).darker()));
				g2d.fillRoundRect(0, 0, (int) (c.getWidth() * progressBar.getPercentComplete() - 10), c.getHeight(), 20,
						20);
			}

			private void paintBackGround(JComponent c, Graphics2D g2d) {
				g2d.setPaint(new GradientPaint(0, 0, new Color(57, 255, 193).darker(), c.getWidth(), 0,
						new Color(16, 0, 93).darker()));
				g2d.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
			}

		});
	}

}
