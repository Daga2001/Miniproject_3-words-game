/*
 * Programación Interactiva
 * Author: David Alberto Guzmán - 201942789
 * Mini-Project-3: Juego de Palabras.
 */
package JuegoDePalabras;

import java.awt.Component;
import java.util.Random;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextArea;

// TODO: Auto-generated Javadoc
/**
 * The Class ControlGame. Manages the logic of this game.
 */
public class ControlGame {
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//attributes
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/** The txt file. */
	private TxtFileManager txtFile;
	
	/** The database. */
	private Database database;
	
	/** The t 1. */
	private Thread 
			t1 = new Thread(new Runnable() {
				@Override
				public void run() {
					database = new Database(
							"jdbc:mysql://bhkfgeduxgdgbwozgs9x-mysql.services.clever-cloud.com/bhkfgeduxgdgbwozgs9x",
							"uziipdy3xhed2jgj",
							"XlJJmbniilxZrpPXdS98");
					retrieveAllUsers();
				}
			});
	
	/** The time stroke. */
	private int series, currentSerie, numberOfWordsToShow, currentScore, currentLevel, oldScore, oldLevel, planeTravels, minimumWordsToGuess, miliseconds, seconds, minutes, timeStroke;
	
	/** The level user. */
	private String answer, currentUser, currentPassword, time, scoreUser, levelUser;
	
	/** The words guessed in level. */
	private Vector<String> usedWords, wordsGuessed, wordsGuessedInSerie, wordsGuessedInLevel;
	
	/** The users. */
	private Vector<Vector<String>> users;
	
	/** The all users stored. */
	private boolean allWordsShown, levelRestarted, planeTraveled, wrong, right, allUsersStored;
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//constructor
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Instantiates a new control game.
	 */
	public ControlGame() {
		t1.start();
		answer = "";
		usedWords = new Vector<String>(); 
		txtFile = new TxtFileManager(getClass().getClassLoader().getResourceAsStream("words.txt"));
		txtFile.storeWords();
		planeTravels = 0;
		series = 2;
		currentSerie = 1;
		levelRestarted = false;
		allWordsShown = false;
		allUsersStored = false;
		wrong = false;
		right = false;
		time = "00:00";
		timeStroke = 0;
		miliseconds = 0;
		seconds = 0;
		minutes = 3;
		users = new Vector<Vector<String>>();
		wordsGuessed = new Vector<String>();
		wordsGuessedInSerie = new Vector<String>();
		wordsGuessedInLevel = new Vector<String>();
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//methods
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Sets the words to show.
	 *
	 * @param level the level
	 * @return the int
	 */
	public int setWordsToShow(int level) {
		int numberOfWords = 2;
		return numberOfWords + (2 * level);
	}
	
	/**
	 * Sets the minimum words to guess in level.
	 *
	 * @param level the level
	 * @return the int
	 */
	public int setMinimumWordsToGuess(int level) {
		if(level == 1) {
			return 7;
		}
		int number = 9;
		return number + ( 3 * (level-2) );
	}
	
	/**
	 * Checks if is reachable the next serie.
	 *
	 * @return true, if is reachable next serie
	 */
	public boolean isReachableNextSerie() {
		return ( wordsGuessedInLevel.size() + numberOfWordsToShow * ( series - currentSerie ) ) >= minimumWordsToGuess;
	}
	
	/**
	 * Picks a random word to display in screen.
	 *
	 * @return the string
	 */
	public String pickRandomWord() {
		Random random = new Random();
		int randomNumber = random.nextInt(txtFile.howManyWords());
		String word = txtFile.getWord(randomNumber);
		while(isInVector(wordsGuessedInLevel, word)) {
			randomNumber = random.nextInt(txtFile.howManyWords());
			word = txtFile.getWord(randomNumber);
		}
		return word;
	}
	
	/**
	 * Checks if all words were used in a serie.
	 *
	 * @return true, if successful
	 */
	public boolean AllWordsWereUsed() {
		allWordsShown = usedWords.size() == this.numberOfWordsToShow;
		return allWordsShown; 
	}
	
	/**
	 * Checks if a character is in string.
	 *
	 * @param string the string
	 * @param character the character
	 * @return true, if is in string
	 */
	public boolean isInString(String string, char character) {
		for(int i = 0; i < string.length(); i++) {
			if(character == string.charAt(i)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a character to answer.
	 *
	 * @param character the character
	 */
	public void addCharToAnswer(String character) {
		String vocalsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
		if(isInString(vocalsInUpperCase, character.charAt(0)) && character.length() == 1 && answer.length() < 14) {
			answer += character;
		}
	}
	
	/**
	 * Erases characters of answer.
	 */
	public void eraseCharOfAnswer() {
		if(answer.length() > 0) {
			answer = answer.substring(0, answer.length()-1);
		}
	}
	
	/**
	 * Erases the answer.
	 */
	public void eraseAnswer() {
		answer = "";
	}
	
	/**
	 * Gets the planeTravels attribute.
	 */
	public void planeTravel() {
		this.planeTravels++;
	}
	
	/**
	 * Checks if the plane completed all the travels.
	 *
	 * @return true, if successful
	 */
	public boolean planeCompletedTravels() {
		return planeTravels == numberOfWordsToShow;
	}
	
	/**
	 * Gets the answer.
	 *
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	
	/**
	 * Checks if is a word found.
	 *
	 * @param word the word
	 * @return true, if is word found
	 */
	boolean isWordFound(String word) {
		for(int i=0; i < usedWords.size(); i++) {
			if(word == usedWords.get(i)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds the used word to the vector usedWords.
	 *
	 * @param word the word
	 */
	public void addUsedWord(String word) {
		usedWords.add(word);
	}
	
	/**
	 * Updates previous used words in level.
	 *
	 * @param components the components
	 */
	public void updatePreviousUsedWords(Component[] components) {
		wordsGuessedInLevel.clear();
		for(int i = 0; i < components.length; i++) {
			if(components[i].getClass() == JLabel.class) {
				JLabel component= (JLabel) components[i];
				wordsGuessedInLevel.add(component.getText());
			}
		}
	}
	
	/**
	 * Checks if a string is in vector.
	 *
	 * @param vector the vector
	 * @param word the word
	 * @return true, if is in vector
	 */
	private boolean isInVector(Vector<String> vector,String word) {
		for(int i = 0; i < vector.size(); i++) {
			if(vector.get(i).equals(word)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks credentials from user.
	 *
	 * @param user the user
	 * @param password the password
	 * @return the string
	 */
	public String checkCredentials(String user, String password) {
		while(t1.isAlive()){System.out.println("Loading resources...");};
		currentUser = user;
		currentPassword = password;
		scoreUser = database.getTableValue("recordings", "score", String.format("user = '%s'", currentUser));
		levelUser = database.getTableValue("recordings", "level", String.format("user = '%s'", currentUser));
		boolean isUserName = database.isInTable("recordings", user, "user");
		boolean isPassword = database.isInTable("recordings", password, "password");
		database.resetTableId("recordings");
		if( password.length() < 8 && !isUserName ) { 
			return "Not valid Credential/s";
		}
		if(isUserName && isPassword) {
			currentScore = Integer.parseInt(scoreUser);
			currentLevel = Integer.parseInt(levelUser);
			oldScore = currentScore;
			oldLevel = currentLevel;
			return "Both Exist";
		}
		if(isUserName) {
			return "Username Exist";
		}
		return "They doesn't exist";
	}
	
	/**
	 * Starts the game.
	 */
	public void startGame() {
		if(database.isInTable("recordings", currentUser, "user")) {
			//login the user
			numberOfWordsToShow = setWordsToShow(currentLevel);
			minimumWordsToGuess = setMinimumWordsToGuess(currentLevel);
		}
		else {
			//create a new user
			database.insertValuesInTable("recordings", "user, password, score, level", 
										String.format("('%s', '%s', %s, %s);", currentUser, currentPassword, 0, 1));
			numberOfWordsToShow = setWordsToShow(currentLevel);
			minimumWordsToGuess = setMinimumWordsToGuess(currentLevel);
			currentScore = 0;
			currentLevel = 1;
			oldScore = currentScore;
			oldLevel = currentLevel;
		}
	}
	
	/**
	 * Checks if is the level restarted.
	 *
	 * @return true, if is level restarted
	 */
	public boolean isLevelRestarted() {
		return levelRestarted;
	}
	
	/**
	 * Restarts the level.
	 */
	private void restartLevel() {
		levelRestarted = true;
		minutes = 3;
		seconds = 0;
		this.planeTravels = 0;
		usedWords.clear();
		wordsGuessedInSerie.clear();
	}
	
	/**
	 * Restarts the game.
	 */
	public void restartGame() {
		currentLevel = 1;
		restartLevel();
	}
	
	/**
	 * Makes the level as if isn't restarted.
	 */
	public void levelNotRestarted() {
		levelRestarted = false;
	}
	
	/**
	 * Gets the new word guessed.
	 *
	 * @return the new word guessed
	 */
	public String getNewWordGuessed() {
		return wordsGuessed.get(wordsGuessed.size() -1 );
	}
	
	/**
	 * Goes to next serie.
	 *
	 * @return the string
	 */
	public String nextSerie() {
		if(currentSerie == series && currentLevel < 5 &&  wordsGuessedInLevel.size() >= minimumWordsToGuess ) {
			currentLevel++;
			currentSerie = 1;
			numberOfWordsToShow = setWordsToShow(currentLevel);
			minimumWordsToGuess = setMinimumWordsToGuess(currentLevel);
			wordsGuessedInLevel.clear();
			allWordsShown = false;
			restartLevel();
			return "Next Level";
		}
		currentSerie++;
		allWordsShown = false;
		restartLevel();
		return "Next Serie";
	}
	
	/**
	 * Checks the answer.
	 *
	 * @return the string
	 */
	public String checkAnswer() {
		if(isInVector(usedWords, answer) && !isInVector(wordsGuessedInLevel, answer)) {
			currentScore += 10;
			wordsGuessed.add(answer);
			wordsGuessedInSerie.add(answer);
			wordsGuessedInLevel.add(answer);
			if(wordsGuessedInSerie.size() == usedWords.size()) {
				return nextSerie();
			} 
			return "Correct";
		}
		if(isInVector(wordsGuessedInLevel, answer)) {
			return "Already Guessed";
		}
		return "Incorrect";
	}
	
	/**
	 * Changes the word.
	 *
	 * @return the string
	 */
	public String changeWord() {
		String word = this.pickRandomWord();
		while(this.isWordFound(word)) {
			word = this.pickRandomWord();
		}
		this.addUsedWord(word);
		return word;
	}
	
	/**
	 * Updates the time.
	 *
	 * @param timerDelay the timer delay
	 * @return the string
	 */
	public String updateTime(double timerDelay) {
		miliseconds += timerDelay;
		if(minutes == 0 && seconds == 0) {
			return time;
		}
		if(miliseconds >= 1000) {
			if(seconds == 0) {
				seconds = 59;
				minutes--;
			}
			else seconds--;
			miliseconds = 0;
		}
		String min = String.valueOf(minutes);
		String sec = String.valueOf(seconds);
		if(min.length() == 1) {
			min = "0" + min;
		}
		if(sec.length() == 1) {
			sec = "0" + sec;
		}
		time = String.format("%s:%s", min, sec);
		return time;
	}
	
	/**
	 * Checks some strokes.
	 *
	 * @param delay the delay
	 */
	public void checkStrokes(int delay) {
		if(this.isRight()) {
			if(timeStroke <= 800) {
				timeStroke += delay;
			}
			else {
				right = false;
				timeStroke = 0;
			}
		}
		if(this.isWrong()) {
			if(timeStroke <= 800) {
				timeStroke += delay;
			}
			else {
				wrong = false;
				timeStroke = 0;
			}
		}
	}
	
	/**
	 * Retrieves all users.
	 */
	public void retrieveAllUsers() {
		int index = 1;
		String id = "";
		String userName = "";
		String score = "";
		String level = "";
		Vector<String> userData = new Vector<String>();
		while( ( id = database.getTableValue("recordings", "id", String.format("id = %s", index)) )  != "not found!") {
			userData = new Vector<String>();
			userName = database.getTableValue("recordings", "user", String.format("id = %s", index));
			score = database.getTableValue("recordings", "score", String.format("id = %s", index));
			level = database.getTableValue("recordings", "level", String.format("id = %s", index));
			userData.add(id);
			userData.add(userName);
			userData.add(score);
			userData.add(level);
			users.add(userData);
			index++;
		}
		allUsersStored = true;
	}
	
	/**
	 * Gets the user info.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the user info
	 */
	public String getUserInfo(int x, int y) {
		return users.get(y).get(x);
	}
	
	/**
	 * Inserts users data in text area.
	 *
	 * @param area the area
	 */
	public void insertUsersDataInTextArea(JTextArea area) {
		for(int i = 0; i < users.size(); i++) {
			area.append(String.format("--- id: %s   ", users.get(i).get(0)));
			area.append(String.format("user: %s   ", users.get(i).get(1)));
			area.append(String.format("score: %s   ", users.get(i).get(2)));
			if(i != users.size()-1) {
				area.append(String.format("level: %s ---\n", users.get(i).get(3)));
			}
			else {
				area.append(String.format("level: %s ---", users.get(i).get(3)));
			}
		}
	}
	
	/**
	 * Updates the user data.
	 */
	public void updateData() {
		database.updateTable("recordings", String.format("score = %s, level = %s", currentScore, currentLevel), String.format("user = '%s'", currentUser));
	}
	
	/**
	 * Resets the user data.
	 */
	public void resetData() {
		currentScore = 0;
		currentLevel = 1;
		database.updateTable("recordings", String.format("score = %s, level = %s", currentScore, currentLevel), String.format("user = '%s'", currentUser));
	}
	
	/**
	 * Checks if time is up.
	 *
	 * @return true, if successful
	 */
	public boolean timeUp() {
		return minutes == 0 && seconds == 0;
	}
	
	/**
	 * Sets a value to right attribute.
	 *
	 * @param answer the answer
	 */
	public void right(boolean answer) {
		right = answer;
	}
	
	/**
	 * Checks if is right the answer.
	 *
	 * @return true, if is right
	 */
	public boolean isRight() {
		return right;
	}
	
	/**
	 * Sets a value to wrong attribute.
	 *
	 * @param answer the answer
	 */
	public void wrong(boolean answer) {
		wrong = answer;
	}
	
	/**
	 * Checks if is wrong the answer.
	 *
	 * @return true, if is wrong
	 */
	public boolean isWrong() {
		return wrong;
	}
	
	/**
	 * Checks if all users were stored in the vector of users.
	 *
	 * @return true, if successful
	 */
	public boolean allUsersWereStored() {
		return allUsersStored;
	}
	
	/**
	 * Sets a value to plane traveled.
	 *
	 * @param answer the answer
	 */
	public void planeTraveled(boolean answer) {
		planeTraveled = answer;
	}
	
	/**
	 * Gets the plane traveled.
	 *
	 * @return the plane traveled
	 */
	public boolean getPlaneTraveled() {
		return planeTraveled;
	}
	
	/**
	 * Gets the number of words guessed in level.
	 *
	 * @return the number of words guessed in level
	 */
	public int getNumberOfWordsGuessedInLevel() {
		return wordsGuessedInLevel.size();
	}
	
	/**
	 * Sets the time stroke.
	 *
	 * @param number the new time stroke
	 */
	public void setTimeStroke(int number) {
		timeStroke = number;
	}
	
	/**
	 * Gets the old score.
	 *
	 * @return the old score
	 */
	public int getOldScore() {
		return oldScore;
	}

	/**
	 * Gets the old level.
	 *
	 * @return the old level
	 */
	public int getOldLevel() {
		return oldLevel;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore() {
		return currentScore;
	}
	
	/**
	 * Gets the current serie.
	 *
	 * @return the current serie
	 */
	public int getCurrentSerie() {
		return currentSerie;
	}
	
	/**
	 * Gets the series.
	 *
	 * @return the series
	 */
	public int getSeries() {
		return series;
	}
	
	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		return currentLevel;
	}
	
	/**
	 * Closes the database connection.
	 */
	public void closeDbsConnection() {
		database.killConnections();
	}
	
}
