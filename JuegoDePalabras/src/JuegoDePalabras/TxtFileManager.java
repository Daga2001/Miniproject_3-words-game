/*
 * Programación Interactiva
 * Author: David Alberto Guzmán - 201942789
 * Mini-Project-3: Juego de Palabras.
 */
package JuegoDePalabras;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.swing.JOptionPane;

// TODO: Auto-generated Javadoc
/**
 * The Class TxtFileManager. Useful to manage the content of any text file.
 */
public class TxtFileManager {
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//attributes
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/** The file R. */
	private BufferedReader fileR;
	
	/** The words. */
	private Vector<String> words;
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//constructor
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Instantiates a new TxtFileManager.
	 *
	 * @param inputStream the url where is located the text file
	 */
	public TxtFileManager(InputStream inputStream) {
		this.fileR = new BufferedReader(new InputStreamReader(inputStream));
		this.words = new Vector<String>();
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//methods
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Stores words in the words vector.
	 */
	public void storeWords() {
		try {
			String ln;
			while((ln = fileR.readLine()) != null) {
				words.add(ln);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found.", "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the word from the words vector.
	 *
	 * @param number an index
	 * @return the word
	 */
	public String getWord(int number) {
		return words.get(number);
	}
	
	/**
	 * Removes a word from the words vector.
	 *
	 * @param number the index of the element
	 */
	public void removeWord(int number) {
		words.remove(number);
	}
	
	/**
	 * Checks How many words are in the words vector.
	 *
	 * @return the number of words from the vector.
	 */
	public int howManyWords() {
		return words.size();
	}
	
}
