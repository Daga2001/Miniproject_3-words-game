/*
 * Programación Interactiva
 * Author: David Alberto Guzmán - 201942789
 * Mini-Project-3: Juego de Palabras.
 */
package JuegoDePalabras;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

// TODO: Auto-generated Javadoc
/**
 * The Class WordsMain. Instantiates the main class.
 */
public class WordsMain {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			String javaLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(javaLookAndFeel);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "There was a damage in the JVM", "Error!", JOptionPane.ERROR_MESSAGE);
		};
		
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				@SuppressWarnings("unused")
				WordsGameGUI mainScreen = new WordsGameGUI();
			}
			
		});
	}
	
}
