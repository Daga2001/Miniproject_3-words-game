/*
 * Programación Interactiva
 * Author: David Alberto Guzmán - 201942789
 * Mini-Project-3: Juego de Palabras.
 */
package JuegoDePalabras;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class Canvas. Instantiates a canvas for drawing purposes in your project.
 */
public class Canvas extends JPanel {

	//----------------------------------------------------------------------------------------------------------------------------------
	//attributes
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The font. */
	private Font font;
	
	/** The background color. */
	private Color backgroundColor;
	
	/** The word. */
	private String word;
	
	/** The image. */
	private ImageIcon image;
	
	/** The control game. */
	private ControlGame controlGame;
	
	/** The rect height. */
	private int x, y, width, height, rectX, rectY, rectWidth, rectHeight;
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//constructor
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Instantiates a new canvas.
	 *
	 * @param control the controlGame
	 */
	public Canvas(ControlGame control) {
		//config canvas
		backgroundColor = new Color(140,194,255);
		font = new Font(Font.DIALOG, Font.BOLD, 27);
		this.setBackground(backgroundColor);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(800, 400));
		this.repaint();
		this.validate();
		controlGame = control;
		word = controlGame.changeWord();
		width = 100;
		height = 100;
		x = this.getPreferredSize().width;
		y = this.getPreferredSize().height/2 - height/2;
		rectX = x + width;
		rectY = y;
		rectWidth = 300;
		rectHeight = 100;
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//methods
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Paints all the graphic components in the canvas.
	 *
	 * @param g the graphics
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		//drawing the background
		image = new ImageIcon(this.getClass().getClassLoader().getResource("background-canvas.png"));
		g2d.drawImage(image.getImage(), 0, 0, this.getWidth(), this.getHeight(), image.getImageObserver());
		//setting the position of spaces according to the context of game 
		this.setRectToEnd();
		this.setRectInMiddle();
		//first, i draw the rectangles, which are spaces for words.
		this.drawRectangle(g2d);
		//drawing some words in spaces.
		g2d.setFont(font);
		FontMetrics metrics = g2d.getFontMetrics();
		g2d.setColor(Color.BLUE);
		g2d.drawString(word, rectX + rectWidth/2 - metrics.stringWidth(word)/2, rectY + rectHeight/2 + metrics.getHeight()/3);
		//drawing score
		Color scoreColor = new Color(0,85,102);
		g2d.setColor(scoreColor);
		String score = String.format("Score: %s", controlGame.getScore());
		String level = String.format("Level: %s", controlGame.getLevel());
		g2d.drawString( score, (float) (this.getWidth()/2 - metrics.stringWidth(score)/2), (float) (this.getHeight() * 0.1));
		g2d.drawString( level, (float) (this.getWidth()/2 - metrics.stringWidth(level)/2), (float) (this.getHeight() * 0.2));
		//plane
		image = new ImageIcon(this.getClass().getClassLoader().getResource("plane.png"));
		g2d.drawImage(image.getImage(), x, y, width, height, image.getImageObserver());
	}
	
	/**
	 * Draws a rectangle.
	 *
	 * @param g2d the graphics 2d
	 */
	private void drawRectangle(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 100, 100);
		determineStrokeColor(g2d);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawRoundRect(rectX, rectY, rectWidth, rectHeight, 100, 100);
	}
	
	/**
	 * Determines the stroke color of the rectangle.
	 *
	 * @param g2d the graphics 2d
	 */
	private void determineStrokeColor(Graphics2D g2d) {
		if(controlGame.planeCompletedTravels()) {
			if(controlGame.isRight()) {
				g2d.setColor(Color.GREEN);
			}
			if(controlGame.isWrong()) {
				g2d.setColor(Color.RED);
			}
			if( !( controlGame.isWrong() || controlGame.isRight() ) ) {
				g2d.setColor(Color.BLUE);
			}
		}
		else {
			g2d.setColor(Color.BLUE);
		}
	}
	
	/**
	 * Checks if is the rectangle at beginning.
	 *
	 * @return true, if is rectangle at beginning
	 */
	private boolean isRectAtBeginning() {
		controlGame.planeTraveled(false);
		if(x + width + rectWidth <= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the rectangle to end.
	 */
	private void setRectToEnd() { //|| levelrestarted
		if( isRectAtBeginning() || controlGame.isLevelRestarted() ) {
			x = this.getWidth();
			rectX = x + width;
			if(!controlGame.AllWordsWereUsed()) {
				word = controlGame.changeWord();
			}
			if(!controlGame.isLevelRestarted()) controlGame.planeTravel();
			controlGame.levelNotRestarted();
			controlGame.planeTraveled(true);
		}
	}
	
	/**
	 * Sets the rectangle in middle of canvas.
	 */
	private void setRectInMiddle() {//&& levelrestarted
		if(controlGame.planeCompletedTravels()) {
			x = this.getWidth()/2 - width*2;
			rectX = x + width;
			word = controlGame.getAnswer();
			this.repaint();
		}
	}
	
	/**
	 * Changes the rectX and x attributes.
	 *
	 * @param velX the velocity in X
	 */
	public void changeRectX(double velX) {
		x += velX;
		rectX += velX;
		this.repaint();
	}
	
	/**
	 * Changes the rectY and y attributes.
	 *
	 * @param velY the velocity in Y
	 */
	public void changeRectY(double velY) {
		y += velY;
		rectY += velY;
		this.repaint();
	}

}
