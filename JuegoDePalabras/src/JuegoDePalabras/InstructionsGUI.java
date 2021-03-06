/*
 * Programación Interactiva
 * Author: David Alberto Guzmán - 201942789
 * Mini-Project-3: Juego de Palabras.
 */
package JuegoDePalabras;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.Border;


// TODO: Auto-generated Javadoc
/**
 * The Class InstructionsGUI. The frame which displays instructions.
 */
public class InstructionsGUI extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The image icon. */
	//attributes
	private ImageIcon imageIcon;
	
	/** The listener. */
	private Listener listener;
	
	/** The buttons panel. */
	private JPanel buttonsPanel, usersDescription;
	
	/** The exit. */
	private JButton play, exit;
	
	/** The instruction gif. */
	private JLabel background, instructionsBody, instructionPng, keyboard, buttonsTitle, usersTitle;
	
	/** The users data. */
	private JTextArea usersData;
	
	/** The users data scroll bar. */
	private JScrollPane usersDataScrollBar;
	
	/** The constraints. */
	private GridBagConstraints constraints;
	
	/** The g 2 d. */
	private Graphics2D g2d;
	
	/** The b image. */
	private BufferedImage bImage;
	
	/** The img. */
	private Image img;
	
	/** The line inner border. */
	private Border compound, lineOuterBorder, lineInnerBorder;
	
	/** The my GUI. */
	private JFrame myGUI, myGameGUI;
	
	/** The audio in. */
	private AudioInputStream audioIn;
	
	/** The control game. */
	private ControlGame controlGame;
	
	/** The timer. */
	private Timer timer;
	
	/** The body color. */
	private Color bodyColor, foregroundTitle;
	
	/** The body font. */
	private Font bodyFont;
	
	/** The roll over. */
	private Clip postClicked, rollOver, backgroundMusic;
	
	/**
	 * Instantiates a new instructions GUI.
	 *
	 * @param gameGUI the game GUI
	 * @param timerGame the timer of game
	 * @param control the controlGame
	 */
	public InstructionsGUI(JFrame gameGUI, Timer timerGame, ControlGame control) {
		//storing the control game.
		controlGame = control;
		
		//inits the GUI
		init();
		
		//default window configuration.
		this.setUndecorated(true);
		this.pack();
		this.getContentPane().setBackground(new Color(4,26,0));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(false);
		this.setEnabled(false);
		
		//storing myGameGUI, timer.
		myGameGUI = gameGUI;
		timer = timerGame;
	}
	
	/**
	 * Inits the GUI.
	 */
	private void init() {
		
		//set window icon
		imageIcon = new ImageIcon(getClass().getClassLoader().getResource("mainIcon.png"));
		this.setIconImage(imageIcon.getImage());
		
		//listener and constraints
		listener = new Listener();
		constraints = new GridBagConstraints();
		
		//make some variables
		Color borderColor = new Color(102,230,255);
		lineOuterBorder = BorderFactory.createLineBorder(borderColor);
		lineInnerBorder = BorderFactory.createLineBorder(borderColor);
		compound = BorderFactory.createCompoundBorder(lineOuterBorder, lineInnerBorder);
		foregroundTitle = new Color(25,255,255);
		bodyFont = newFont(Font.SERIF, Font.BOLD+Font.ITALIC, 25);
		bodyColor = new Color(255,255,255);
		
		//audio
		try {
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("post-click sound effect.wav"));
			postClicked = AudioSystem.getClip();
			postClicked.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("rollover-sound-effect.wav"));
			rollOver = AudioSystem.getClip();
			rollOver.open(audioIn);
			
			audioIn = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("background music.wav"));
			backgroundMusic = AudioSystem.getClip();
			backgroundMusic.open(audioIn);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Audio file not found!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		//storing myGUI
		myGUI = this;
		
		//make GUI
		
		//background
		//Preferred size for this image: 1000x667 pixels
		img = new ImageIcon(getClass().getClassLoader().getResource("background-instructions.png")).getImage();
		imageIcon = new ImageIcon(img);
		background = new JLabel();
		background.setIcon(imageIcon);
		background.setBorder(compound);
		background.setLayout(new GridBagLayout());
		
		//title
		Title tittle = new Title("Instrucciones", 40, null, foregroundTitle, null, false);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.weighty = 0.01;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		background.add(tittle, constraints);
		
		//instructions background
		String text = "<html><body style='text-align: center;'>Memoriza las palabras que veas, luego con los botones del teclado"
				+ " escribe la palabra que recuerdes y presiona ENTER para verificarla. "
				+ "La tabla de abajo muestra la distribución de series, niveles y palabras. "
				+ "Buena suerte, dale clic al botón de play cuando estés preparad@</body></html>";
		instructionsBody = new JLabel(text);
		instructionsBody.setFont(bodyFont);
		instructionsBody.setForeground(bodyColor);
		instructionsBody.setOpaque(false);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 3;
		background.add(instructionsBody, constraints);
		
		//User's Description JPanel	
		int subtitleSize = 30;
		int space = 30;
		Dimension verticalSpace = new Dimension(0, space);
		Dimension horizontalSpace = new Dimension(space, 0);
		buttonsTitle = new Title("Botones", subtitleSize, null, foregroundTitle, null, false);
		new Title("Datos de Usuario", subtitleSize, null, foregroundTitle, null, false);
		usersTitle = new Title("Puntuaciones", subtitleSize, null, foregroundTitle, null, false);
		imageIcon = new ImageIcon(getClass().getClassLoader().getResource("instructions.png"));
		instructionPng = new JLabel(imageIcon);
		imageIcon = new ImageIcon(getClass().getClassLoader().getResource("keyboard.png"));
		keyboard = new JLabel(imageIcon);
		usersData = new JTextArea();
		usersData.setEditable(false);
		usersData.append("--- Usuarios que han jugado anteriormente ---\n");
		while(!controlGame.allUsersWereStored()){System.out.println("Loading users...");};
		controlGame.insertUsersDataInTextArea(usersData);
		usersDataScrollBar = new JScrollPane(usersData, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		usersDescription = new JPanel();
		usersDescription.setOpaque(false);
		usersDescription.setLayout(new GridBagLayout());
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		usersDescription.add(buttonsTitle, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		usersDescription.add(keyboard, constraints);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		usersDescription.add(Box.createRigidArea(horizontalSpace), constraints);
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		usersDescription.add(usersTitle, constraints);
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.BOTH;
		usersDescription.add(usersDataScrollBar, constraints);
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		usersDescription.add(Box.createRigidArea(verticalSpace), constraints);
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 3;
		usersDescription.add(instructionPng, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		background.add(usersDescription, constraints);
		
		//exit and play buttons
		buttonsPanel = new JPanel();
		buttonsPanel.setOpaque(false);
		play = new Button("Play");
		play.addActionListener(listener);
		play.addMouseListener(listener);
		exit = new Button("Salir");
		exit.addActionListener(listener);
		exit.addMouseListener(listener);
		buttonsPanel.setLayout(new GridBagLayout());
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0.7;
		buttonsPanel.add(play, constraints);
		constraints.gridx = 3;
		constraints.gridwidth = 1;
		buttonsPanel.add(exit, constraints);
		//------------space btw btns--------------------
		space = 50;
		constraints.gridx = 0;
		constraints.gridwidth = 1;
		buttonsPanel.add(Box.createHorizontalStrut(space),constraints);
		constraints.gridx = 2;
		constraints.gridwidth = 1;
		buttonsPanel.add(Box.createHorizontalStrut(0),constraints);
		constraints.gridx = 4;
		constraints.gridwidth = 1;
		buttonsPanel.add(Box.createHorizontalStrut(space),constraints);
		//----------------------------------------------
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 3;
		background.add(buttonsPanel, constraints);
		
		this.add(background, BorderLayout.NORTH);

	}

	/**
	 * Sets the transparency.
	 *
	 * @param image the image
	 * @param flt the float between 0 and 1
	 * @return the image
	 */
	@SuppressWarnings("unused")
	private Image setTransparency(Image image, Float flt) {
		bImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g2d = (Graphics2D) bImage.createGraphics();
		//here i set the transparency of the background
		g2d.setComposite(AlphaComposite.SrcOver.derive(flt));
		g2d.drawImage(image, 0, 0, null);
		return bImage;
	}
	
	/**
	 * New font. sets a new font.
	 *
	 * @param family the family
	 * @param style the style
	 * @param size the size
	 * @return the font
	 */
	private Font newFont(String family, int style, int size) {
		Font font= new Font(family, style, size);
		return font;
	}
	
	/**
	 * The Class Listener.
	 */
	private class Listener extends MouseAdapter implements ActionListener{

		/**
		 * Action performed.
		 *
		 * @param event the event
		 */
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			if(event.getSource() == play) {
				myGUI.setVisible(false);
				myGUI.setEnabled(false);
				myGameGUI.setVisible(true);
				myGameGUI.setEnabled(true);
				backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
				timer.start();
			}
			if(event.getSource() == exit) {
				System.exit(0);
			}
		}
		
		/**
		 * Mouse pressed.
		 *
		 * @param e the e
		 */
		public void mousePressed(MouseEvent e) {
			if(e.getSource() == play) {
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
