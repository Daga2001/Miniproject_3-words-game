/*
 * Programación Interactiva
 * Author: David Alberto Guzmán - 201942789
 * Mini-Project-3: Juego de Palabras.
 */
package JuegoDePalabras;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.Border;

// TODO: Auto-generated Javadoc
/**
 * The Class Titulos. display titles with a text, size, background color, foreground and border defined by user.
 */
public class Title extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//atributos
	/**
	 * Instantiates a new titulos.
	 * @param text the text
	 * @param size the size
	 * @param backgroundColor the background color
	 * @param foreGround the foreground color
	 * @param border the border 
	 * @param opaque type true here if you want to display a background color in this title.
	 */
	//metodos
	public Title(String text, int size, Color backgroundColor, Color foreGround, Border border, boolean opaque) {
		this.setText(text);
		Font font = new Font(Font.SERIF, Font.BOLD+Font.ITALIC, size);
		this.setFont(font);
		this.setBorder(border);
		this.setBackground(backgroundColor);
		this.setForeground(foreGround);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setOpaque(opaque);
	}
}
