/*
 * Programación Interactiva
 * Author: David Alberto Guzmán - 201942789
 * Mini-Project-3: Juego de Palabras.
 */
package JuegoDePalabras;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

// TODO: Auto-generated Javadoc
/**
 * The Class Button. Instantiates a button, but you can edit some features in this class if you require.
 */
public class Button extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new button.
	 *
	 * @param text the text
	 */
	public Button(String text) {
		Color colorButtons = new Color(51,153,255);
		Color foregroundButtons = new Color(0,85,102);
		Color borderColor = foregroundButtons;
		LineBorder borderButtons = new LineBorder(borderColor, 5, false);
		Font fontButtons= new Font(Font.SERIF, Font.BOLD+Font.ITALIC, 25);
		
		this.setText(text);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setBackground(colorButtons);
		this.setForeground(foregroundButtons);
		this.setBorder(borderButtons);
		this.setFocusPainted(false);
		this.setFont(fontButtons);
	}
}
