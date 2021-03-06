/*
 * Programación Interactiva
 * Author: David Alberto Guzmán - 201942789
 * Mini-Project-3: Juego de Palabras.
 */
package JuegoDePalabras;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

// TODO: Auto-generated Javadoc
/**
 * The Class WordsGameGUI. Instantiates the main GUI of the game
 */
public class WordsGameGUI extends JFrame{

	//----------------------------------------------------------------------------------------------------------------------------------
	//attributes
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The canvas. */
	private Canvas canvas;
	
	/** The listener. */
	private Listener listener;
	
	/** The listener mouse. */
	private ListenerMouse listenerMouse;
	
	/** The timer. */
	private Timer timer;
	
	/** The show menu. */
	private JButton exit, validateData, createUser, showWords, endSerie, showMenu;
	
	/** The icon. */
	private ImageIcon image, icon;
	
	/** The no words. */
	private JLabel username, psswd, time, noWords;
	
	/** The login frame. */
	private JFrame loginFrame;
	
	/** The user. */
	private JTextField user;
	
	/** The password. */
	private JPasswordField password;
	
	/** The instructions GUI. */
	private JFrame myGUI, instructionsGUI;
	
	/** The frame color. */
	private Color borderColor, foregroundWords, frameColor;
	
	/** The font words. */
	private Font fontWords;
	
	/** The words. */
	private JPanel words;
	
	/** The game started. */
	private boolean gameStarted;
	
	/** The once. */
	private int once;
	
	/** The audio in. */
	private AudioInputStream audioIn;
	
	/** The shoowsh. */
	private Clip postClicked, rollOver, rightAnswer, wrongAnswer, fail, airplane, shoowsh;
	
	/** The constraints. */
	private GridBagConstraints constraints;
	
	/** The control game. */
	private ControlGame controlGame;
	
	/** The t 1. */
	private Thread t1;
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//constructor
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Instantiates a new words game GUI.
	 */
	public WordsGameGUI() {
		
		icon = new ImageIcon(getClass().getClassLoader().getResource("mainIcon.png"));
		
		//initialization of GUI
		initGUI();
		
		//config of GUI
		this.setTitle("Juego de Palabras");
		this.getContentPane().setBackground(frameColor);
		this.setUndecorated(false);
		this.pack();
		this.setBackground(Color.WHITE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(false);
		this.setEnabled(false);
		this.requestFocus();
		this.setIconImage(icon.getImage());
		this.addKeyListener(listener);
		this.validate();
		
		//config of LoginFrame
		loginFrame.setTitle("Login");
		loginFrame.getContentPane().setBackground(frameColor);
		loginFrame.setUndecorated(false);
		loginFrame.pack();
		loginFrame.setBackground(Color.WHITE);
		loginFrame.setResizable(false);
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setVisible(true);
		loginFrame.validate();
		loginFrame.requestFocus();
		loginFrame.addKeyListener(listener);
		loginFrame.setIconImage(icon.getImage());
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//methods
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Inits the GUI.
	 */
	private void initGUI() {
		
		//define window icon
		
		
		//make listeners
		listener = new Listener();
		listenerMouse = new ListenerMouse();
		
		//make other variables
		timer = new Timer(10, listener);
		gameStarted = false;
		controlGame = new ControlGame();
		constraints = new GridBagConstraints();
		myGUI = this;
		once = 0;
		fontWords = new Font(Font.SERIF, Font.BOLD+Font.ITALIC, 15);
		frameColor = new Color(153,187,255);
		foregroundWords = new Color(0,85,102);
		borderColor = new Color(0,85,102);
		t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!controlGame.planeCompletedTravels()) {
					if(!airplane.isRunning()) {
						airplane.setFramePosition(0);
						airplane.start();
					}					
				}
				airplane.stop();
			}
		});
		
		//import audio
		try {
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("post-click sound effect.wav"));
			postClicked = AudioSystem.getClip();
			postClicked.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("rollover-sound-effect.wav"));
			rollOver = AudioSystem.getClip();
			rollOver.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("right answer.wav"));
			rightAnswer = AudioSystem.getClip();
			rightAnswer.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("wrong.wav"));
			wrongAnswer = AudioSystem.getClip();
			wrongAnswer.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("fail.wav"));
			fail = AudioSystem.getClip();
			fail.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("Wind-Shoowsh.wav"));
			shoowsh = AudioSystem.getClip();
			shoowsh.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("airplane.wav"));
			airplane = AudioSystem.getClip();
			airplane.open(audioIn);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Audio file not found!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		//makeGUI of loginFrame and instructions
		JOptionPane.showMessageDialog(null, "ˇPor favor, no introduzca sus datos reales en este menú!", "Warning!", JOptionPane.WARNING_MESSAGE);
		loginFrame = new JFrame();
		loginFrame.addKeyListener(listener);
		loginFrame.setLayout(new GridBagLayout());
		createInstructionsGUI();
		
		//textfields
		int spaceForFields = 14;
		Dimension spaceBtwComponents = new Dimension(0, 10);
		username = new JLabel("Usuario");
		username.setForeground(foregroundWords);
		username.setFont(fontWords);
		psswd = new JLabel("Contraseńa");
		psswd.setForeground(foregroundWords);
		psswd.setFont(fontWords);
		user = new JTextField(spaceForFields);
		password = new JPasswordField(spaceForFields);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		loginFrame.add(Box.createRigidArea(spaceBtwComponents), constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		loginFrame.add(username, constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		loginFrame.add(user, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		loginFrame.add(Box.createRigidArea(spaceBtwComponents), constraints);
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		loginFrame.add(psswd, constraints);
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		loginFrame.add(password, constraints);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		loginFrame.add(Box.createRigidArea(spaceBtwComponents), constraints);
		
		//Button for validation of credentials (not are credentials at all, for example real names or something important)
		createUser = new Button("Crear Usuario");
		createUser.addKeyListener(listener);
		createUser.addActionListener(listener);
		createUser.addMouseListener(listenerMouse);
		validateData = new Button("Validar");
		validateData.addKeyListener(listener);
		validateData.addActionListener(listener);
		validateData.addMouseListener(listenerMouse);
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		loginFrame.add(validateData, constraints);
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		loginFrame.add(createUser, constraints);
		
		//makeGUI of WordsGameGUI
		this.setLayout(new GridBagLayout());
		
		//Time
		LineBorder borderTime = new LineBorder(borderColor, 5, false);
		Color colorTime = new Color(0,191,230);
		Color foregroundTime = new Color(0,85,102);
		time = new Title( String.format( "Tiempo: %s", controlGame.updateTime( timer.getDelay()*1.6 ) ), 25, colorTime, foregroundTime, borderTime, true);
		time.setVisible(false);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		this.add(time, constraints);
		
		//buttons
		endSerie = new Button("Terminar Serie");
		endSerie.setVisible(false);
		endSerie.addActionListener(listener);
		endSerie.addKeyListener(listener);
		endSerie.addMouseListener(listenerMouse);
		showWords = new Button("Palabras Adivinadas");
		showWords.setVisible(false);
		showWords.addActionListener(listener);
		showWords.addKeyListener(listener);
		showWords.addMouseListener(listenerMouse);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		this.add(showWords, constraints);
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		this.add(endSerie, constraints);
		exit = new Button("Salir");
		exit.addActionListener(listener);
		exit.addKeyListener(listener);
		exit.addMouseListener(listenerMouse);
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		this.add(exit, constraints);
		
		//menu button
		showMenu = new Button("volver");
		showMenu.setVisible(false);
		showMenu.addActionListener(listener);
		showMenu.addKeyListener(listener);
		showMenu.addMouseListener(listenerMouse);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.add(showMenu, constraints);
		
		//showing words
		Color wordsBackground = new Color(128,212,255);
		words = new JPanel();
		words.setVisible(false);
		words.setBorder(borderTime);
		words.setBackground(wordsBackground);
		words.setLayout(new GridBagLayout());
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		noWords= new JLabel("No has adivinado una palabra aún.");
		noWords.setFont(fontWords);
		noWords.setForeground(foregroundWords);
		words.add(noWords, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.LINE_START;
		this.add(words, constraints);
		
		//canvas
		canvas = new Canvas(controlGame);
		canvas.addKeyListener(listener);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		this.add(canvas, constraints);
	}
	
	/**
	 * Shows the remaining interface, in other words, some missing buttons.
	 */
	private void showRemainingInterface() {
		if(once == 0) {
			showWords.setVisible(true);
			time.setVisible(true);
			endSerie.setVisible(true);
			
			myGUI.repaint();
			myGUI.validate();
			myGUI.pack();
			myGUI.setLocationRelativeTo(null);
			once++;
		}
	}
	
	/**
	 * Hides the remaining interface.
	 */
	private void hideRemainingInterface() {
		once = 0;
		showWords.setVisible(false);
		time.setVisible(false);
		endSerie.setVisible(false);
		showMenu.setVisible(false);
		words.setVisible(false);
		exit.setVisible(true);
		
		myGUI.repaint();
		myGUI.validate();
		myGUI.pack();
		myGUI.setLocationRelativeTo(null)	;
	}
	
	/**
	 * Shows the list of words, hiding some buttons.
	 */
	private void showWords() {
		endSerie.setVisible(false);
		time.setVisible(false);
		showWords.setVisible(false);
		exit.setVisible(false);
		
		showMenu.setVisible(true);
		words.setVisible(true);
		
		myGUI.repaint();
		myGUI.validate();
		myGUI.pack();
		myGUI.setLocationRelativeTo(null);
	}
	
	/**
	 * Shows the menu from the list of words, hiding the list.
	 */
	private void showMenu() {
		showMenu.setVisible(false);
		words.setVisible(false);
		
		endSerie.setVisible(true);
		time.setVisible(true);
		showWords.setVisible(true);
		exit.setVisible(true);
		
		
		myGUI.repaint();
		myGUI.validate();
		myGUI.pack();
		myGUI.setLocationRelativeTo(null);
	}
	
	/**
	 * Adds the word to list.
	 *
	 * @param word the word
	 */
	private void addWordToList(String word) {
		if(controlGame.getNumberOfWordsGuessedInLevel() == 1) {
			words.remove(noWords);
		}
		
		JLabel newWord = new JLabel(word);
		newWord.setFont(fontWords);
		newWord.setForeground(foregroundWords);
		newWord.setHorizontalAlignment(JLabel.CENTER);
		constraints.gridx = 0;
		constraints.gridy = words.getComponentCount();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		words.add(newWord, constraints);

		myGUI.repaint();
		myGUI.validate();
		myGUI.pack();
		myGUI.setLocationRelativeTo(null);
	}
	
	/**
	 * Creates the graphical user interface for instructions.
	 */
	private void createInstructionsGUI() {
		t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				//make instructions GUI
				instructionsGUI = new InstructionsGUI(myGUI, timer, controlGame);	
			}
		});
		t1.start();
	}
	
	/**
	 * Validates the user data.
	 */
	private void validateData() {
		//make instructions GUI
		switch( controlGame.checkCredentials( user.getText(), String.valueOf(password.getPassword()) ) ){
		case "Both Exist":
			requestLoadData();
			controlGame.startGame();
			gameStarted = true;
			loginFrame.setVisible(false);
			loginFrame.setEnabled(false);
			instructionsGUI.setVisible(true);
			instructionsGUI.setEnabled(true);
			break;
		case "Username Exist": 
			JOptionPane.showMessageDialog(null, "El usuario y/o contraseńa son incorrectos.", "Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "They doesn't exist":
			JOptionPane.showMessageDialog(null, "El usuario y/o contraseńa son incorrectos.", "Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "Not valid Credential/s":
			JOptionPane.showMessageDialog(null, "La contraseńa debe ser de minimo 8 caracteres.", "Error!", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}
	
	/**
	 * Creates the user.
	 */
	private void createUser() {
		switch( controlGame.checkCredentials( user.getText(), String.valueOf(password.getPassword()) ) ){
		case "Both Exist": 
			JOptionPane.showMessageDialog(null, "Este usuario ya existe, por favor elija otro.", "Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "Username Exist": 
			JOptionPane.showMessageDialog(null, "Este usuario ya existe, por favor elija otro.", "Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "They doesn't exist":
			JOptionPane.showMessageDialog(null, "Creando usuario.", "", JOptionPane.INFORMATION_MESSAGE);
			controlGame.startGame();
			gameStarted = true;
			loginFrame.setVisible(false);
			loginFrame.setEnabled(false);
			instructionsGUI.setVisible(true);
			instructionsGUI.setEnabled(true);
			break;
		case "Not valid Credential/s":
			JOptionPane.showMessageDialog(null, "La contraseńa debe ser de minimo 8 caracteres.", "Error!", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}
	
	/**
	 * Checks the answer.
	 */
	private void checkAnswer() {
		switch(controlGame.checkAnswer()) {
		case "Next Level":
						words.removeAll();
						words.add(noWords);
		    			controlGame.eraseAnswer();
		    			hideRemainingInterface();
			break;
		case "Next Serie":
						addWordToList(controlGame.getNewWordGuessed());
						controlGame.eraseAnswer();
						hideRemainingInterface();
			break;
		case "Correct": 
						addWordToList(controlGame.getNewWordGuessed());
						controlGame.eraseAnswer();
						rightAnswer.setFramePosition(0);
						rightAnswer.start();
						controlGame.right(true);
		    			controlGame.wrong(false);
		    			controlGame.setTimeStroke(0);
						break;
		case "Already Guessed":
		    			JOptionPane.showMessageDialog(null, "Word already guessed!", "", JOptionPane.INFORMATION_MESSAGE);
						break;
		case "Incorrect": 
						wrongAnswer.setFramePosition(0);
		    			wrongAnswer.start();
		    			controlGame.right(false);
		    			controlGame.wrong(true);
		    			controlGame.setTimeStroke(0);
						break;
		case "Unreachable":
						restartGame();
						break;
		}
	}
	
	/**
	 * Requests to user if he/she wants to load data.
	 */
	private void requestLoadData() {
		if(controlGame.getScore() != 0) {
			image = new ImageIcon(getClass().getClassLoader().getResource("load-game.png"));
			String tittle = "Load screen";
			String body = "<html><body style='text-align: center;'>Le gustaría cargar su progreso anterior?</body></html>";
			int answer = JOptionPane.showConfirmDialog(null, body, tittle, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, image);
			switch(answer) {
			case JOptionPane.YES_OPTION:
										break;
			case JOptionPane.NO_OPTION:
										controlGame.resetData();
										break;
			case JOptionPane.CLOSED_OPTION:
										break;
			}
		}
	}
	
	/**
	 * Requests to user if he/she wants to save data.
	 */
	private void requestSaveData() {
		JOptionPane.showMessageDialog(null, "Good Luck for next time!", "Farewell!", JOptionPane.INFORMATION_MESSAGE);
		if(controlGame.getScore() != controlGame.getOldScore() || controlGame.getLevel() != controlGame.getOldLevel()) {
			image = new ImageIcon(getClass().getClassLoader().getResource("save.png"));
			String tittle = "Save screen";
			String body = "<html><body style='text-align: center;'>Le gustaría guardar su progreso?</body></html>";
			int answer = JOptionPane.showConfirmDialog(null, body, tittle, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, image);
			switch(answer) {
			case JOptionPane.YES_OPTION:
										controlGame.updateData();
										break;
			case JOptionPane.NO_OPTION:
										break;
			case JOptionPane.CLOSED_OPTION:
										break;
			}
		}
		controlGame.closeDbsConnection();
		System.exit(0);
	}
	
	/**
	 * Updates the time.
	 */
	private void updateTime() {
		time.setText( String.format( "Tiempo: %s", controlGame.updateTime( timer.getDelay()*1.6 ) ) );
	}
	
	/**
	 * Restarts the game.
	 */
	private void restartGame() {
		fail.setFramePosition(0);
		fail.start();
		image = new ImageIcon(getClass().getClassLoader().getResource("restart.png"));
		String tittle = "Restart screen";
		String body = "<html><body style='text-align: center;'>Le gustaría volver a jugar desde el principio?</body></html>";
		int answer = JOptionPane.showConfirmDialog(null, body, tittle, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, image);
		switch(answer) {
		case JOptionPane.YES_OPTION:
									controlGame.restartGame();
									controlGame.eraseAnswer();
									hideRemainingInterface();
									break;
		case JOptionPane.NO_OPTION:	
									requestSaveData();
									break;
		case JOptionPane.CLOSED_OPTION:
									break;
		}
	}
	
	/**
	 * Plays the airplane audio.
	 */
	private void playAirplaneAudio() {
		t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!controlGame.getPlaneTraveled()) {
					if(!airplane.isRunning()) {
						airplane.setFramePosition(0);
						airplane.start();
					}					
				}
				airplane.stop();
			}
		});
		t1.start();
	}
	
	/**
	 * The Class Listener. Which extends from KeyAdapter, tries to listen all kind of key from the keyboard in the game
	 * to give an answer.
	 */
	private class Listener extends KeyAdapter implements ActionListener {
		
		/**
		 * Invoked when a key has been pressed.
		 *
		 * @param e the e
		 */
	    public void keyPressed(KeyEvent e) {
	    	if(controlGame.AllWordsWereUsed() && gameStarted) {
		    	if(KeyEvent.getKeyText(e.getKeyCode()) == "Enter") {
		    		checkAnswer();
		    	}
		    	if(KeyEvent.getKeyText(e.getKeyCode()) == "Backspace") {
		    		controlGame.eraseCharOfAnswer();
		    	}
		    	if(KeyEvent.getKeyText(e.getKeyCode()) == "Space") {
		    		controlGame.addCharToAnswer(" ");
		    	}
		    	else {
		    		controlGame.addCharToAnswer(KeyEvent.getKeyText(e.getKeyCode()));
		    	}
	    	}
	    	if(KeyEvent.getKeyText(e.getKeyCode()) == "Left" && gameStarted) {
	    		canvas.changeRectX(-25);
	    	}
	    }

		/**
		 * Action performed.
		 *
		 * @param e the e
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == timer) {
				if(gameStarted) {
					if(controlGame.timeUp()) {
						restartGame();
					}
					if(!controlGame.planeCompletedTravels()) {
						if(!t1.isAlive()) {
							playAirplaneAudio();
						}
						canvas.changeRectX(-2);
					}
					else {
						updateTime();
						controlGame.checkStrokes(timer.getDelay());
						showRemainingInterface();
					}
				}
			}
			if(e.getSource() == exit) {
				requestSaveData();
			}
			if(e.getSource() == validateData) {
				validateData();
			}
			if(e.getSource() == createUser) {
				createUser();
			}
			if(e.getSource() == endSerie) {
				if(controlGame.isReachableNextSerie() ) {
					shoowsh.setFramePosition(0);
					shoowsh.start();
					if(controlGame.nextSerie() == "Next Level") { 
						words.removeAll();
						words.add(noWords);
					}
					controlGame.eraseAnswer();
					hideRemainingInterface();
				}
				else {
					restartGame();
				}
			}
			if(e.getSource() == showWords) {
				showWords();
			}
			if(e.getSource() == showMenu) {
				showMenu();
			}
		}
		
	}
	
	/**
	 * The Class ListenerMouse. Which extends from MouseAdapter, tries to listen all kind of mouse in the game to give an answer.
	 */
	private class ListenerMouse extends MouseAdapter{
		
		/**
		 * Mouse pressed.
		 *
		 * @param e the e
		 */
		public void mousePressed(MouseEvent e) {
			if(e.getSource() == validateData || e.getSource() == createUser) {
				postClicked.setFramePosition(0);
				postClicked.start();
			}
		}
		
		/**
		 * Mouse exited.
		 *
		 * @param e the e
		 */
		public void mouseExited(MouseEvent e) {
			if(e.getSource().getClass() == Button.class) {
				e.getComponent().setBackground(new Color(51,153,255));
			}
		}

	    /**
    	 * Mouse entered.
    	 *
    	 * @param e the e
    	 */
    	public void mouseEntered(MouseEvent e) {
	    	if(e.getSource().getClass() == Button.class) {
	    		rollOver.stop();
	    		rollOver.setMicrosecondPosition(0);
	    		rollOver.start();
	    		e.getComponent().setBackground(new Color(77,225,255));
	    	}
	    }
	}
	
}
