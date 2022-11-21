package view.utils;

import java.awt.BasicStroke;
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
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;

import view.Constants;
import view.JFrame.JFrameMain;

/**
 * Clase que maneja el objeto MyJDialog.java
 * Dialog personalizado
 * @author CRISTIAN DAVID PARADA MARTINEZ
 * @date 3/10/2021
 */
public class MyJDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private Dimension dimension;

	/**
	 * Constructor de la clase MyJDialog
	 */
	public MyJDialog(JFrame jFrame, Dimension dimension) {
		super(jFrame);
		this.dimension = dimension;
		init();
	}

	/**
	 * Metodo que inicializa las propiedades de este dialog
	 */
	private void init() {
		this.setLayout(new BorderLayout());
		this.setUndecorated(true);
		this.setSize(dimension);
		this.setLocationRelativeTo(JFrameMain.getInstance());
		this.getContentPane().setBackground(new Color(0, 0, 0, 0));
		this.setShape(new RoundRectangle2D.Double(0, 0, dimension.getWidth(), dimension.getHeight(), 60, 60));
		this.setOpacity(0.90f);
		try {
			Image cursor = ImageIO.read(getClass().getResource(Constants.PATH_IMAGE_CURSOR_BLUE));
			Cursor myCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "my cursor");
			this.setCursor(myCursor);
		} catch (IOException e) {
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(15));
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(0, 0, (int)dimension.getWidth(), (int)dimension.getHeight(), 60, 60);
	}
}
