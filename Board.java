import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Board /* implements Comparable<T> */ {
	private int board[][] = new int[8][8];
	private ArrayList<Human> accounts = new ArrayList<Human>();
	private static int totalPlayedPlayers;
	private static int currentPieceXPos, currentPieceYPos;
	
	/**
	 * Getter method for board
	 *
	 */
	public int[][] getBoard() {
		return board;
	}

	/**
	 * Setter method for totalPlayedPlayers
	 *
	 *@param totalPlayers : The value you want to store in totalPlayedPlayers
	 */
	private static void setTotalPlayedPlayers(int totalPlayers) {
		totalPlayedPlayers = totalPlayers;
	}

	
	/**
	 * Getter method for totalPlayedPlayers
	 *
	 *
	 */
	static int getTotalPlayedPlayers() {
		return totalPlayedPlayers;
	}

	
	/**
	 * Getter method for currentPieceXPos
	 *
	 *
	 */
	static int getCurrentPieceXPos() {
		return currentPieceXPos;
	}

	
	/**
	 * Getter method for currentPieceYPos
	 *
	 *
	 */
	static int getCurrentPieceYPos() {
		return currentPieceYPos;
	}
	
	static void setCurrentPieceXPos(int xPos) {
		currentPieceXPos = xPos;
	}

	static void setCurrentPieceYPos(int yPos) {
		currentPieceYPos = yPos;
	}

	
	/**
	 * Starts to store values into the board array and assigns values to the pieces in each of the player pieces arraylist.
	 *
	 *@param Player p1 : First player
	 *@param Player p2 : Second player
	 *@param boolean symbChoice : The choice of the user on what symbol to use as a representation of a checkers piece.
	 */
	public void generateBoard(Player p1, Player p2, boolean symbChoice) {
		int z = 0;
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {

				// First three rows of the board for top player
				if (y < 3) {

					if ((y == 1 && x % 2 == 0) || (y != 1 && x % 2 != 0)) {
						if (!symbChoice)
							board[y][x] = 1;
						else
							board[y][x] = 2;

						p2.getPieceList().get(z).setYPos(y);
						p2.getPieceList().get(z).setXPos(x);
						z++;
					} else if (y % 2 == 0) {
						if (x % 2 == 0)
							board[y][x] = 8;
						else
							board[y][x] = 9;

					} else if (y % 2 != 0) {
						if (x % 2 == 0)
							board[y][x] = 9;
						else
							board[y][x] = 8;
					}

					// Last three rows for the bottom player
				} else if (y > 4) {

					if ((y == 6 && x % 2 != 0) || (y != 6 && x % 2 == 0)) {
						if (symbChoice)
							board[y][x] = 1;
						else
							board[y][x] = 2;
						p1.getPieceList().get(z).setYPos(y);
						p1.getPieceList().get(z).setXPos(x);
						z++;
					} else if (y % 2 == 0) {
						if (x % 2 == 0)
							board[y][x] = 8;
						else
							board[y][x] = 9;

					} else if (y % 2 != 0) {
						if (x % 2 == 0)
							board[y][x] = 9;
						else
							board[y][x] = 8;
					}

				} else if (y % 2 == 0) {
					if (x % 2 == 0)
						board[y][x] = 8;
					else
						board[y][x] = 9;

				} else if (y % 2 != 0) {
					if (x % 2 == 0)
						board[y][x] = 9;
					else
						board[y][x] = 8;
				}

				// Once 12 pieces are allocated, reset for next set
				if (z == 12)
					z = 0;
			}
		}
	}

	
	/**
	 * Starts to store values into the board array and assigns values to the pieces in each of the player pieces arraylist.
	 *
	 *@param Player p1 : First player
	 *@param Player p2 : Second player
	 *@param boolean symbChoice : The choice of the user on what symbol to use as a representation of a checkers piece.
	 */
	public void readInPlayer() throws IOException {
		try {
			File text = new File("pastPlayers.txt");
			Scanner input = new Scanner(text);
			while (input.hasNextLine()) {
				Human h = new Human();
				h.setName(input.nextLine());
				h.setPassword(input.nextLine());
				h.setTotalWins(input.nextInt());
				if (input.hasNextLine()) {
					input.nextLine();
				}
				h.setTotalLosses(input.nextInt());
				if (input.hasNextLine()) {
					input.nextLine();
				}
				accounts.add(h);
			}
			input.close();
		} catch (IOException e) {
			throw new IOException("Error: readInPlayer(): " + "File is not found. Unable to read data.");
		}

		Board.setTotalPlayedPlayers(accounts.size());
	}
	
	
	/**
	 * Checks to see if a player has won or lost by seeing whether the number of pieces in the arraylist is equal to 0.
	 *
	 *@param Player p1 : First player
	 *@param Player p2 : Second player
	 */
	public boolean gameOver(Player p1, Player p2) {
		if (p1.getPieceList().size() == 0 || p2.getPieceList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * Prints out the 2D board array
	 *
	 */
	public void printBoard() {
		System.out.print("  X ");
		for (int h = 0; h < board.length; h++) {
			System.out.print(h + " ");
		}
		System.out.print("\nY ");

		for (int h = 0; h < board.length * 2.3; h++) {
			System.out.print("#");

		}
		System.out.println();

		for (int i = 0; i < board.length; i++) {
			System.out.print(i + " #" + " ");
			for (int c = 0; c < board[0].length; c++) {
				int temp = board[i][c];

				if (temp == 1)
					System.out.print("*" + " ");
				else if (temp == 2)
					System.out.print("^" + " ");
				else if (temp == 3)
					System.out.print("&" + " ");
				else if (temp == 4)
					System.out.print("@" + " ");
				else if (temp == 8)
					System.out.print("-" + " ");
				else if (temp == 9)
					System.out.print("+" + " ");
				else
					System.out.print(temp + " ");

			}
			System.out.println("#");
		}
		System.out.print("  ");
		for (int h = 0; h < board.length * 2.3; h++) {
			System.out.print("#");
		}
		System.out.println();
	}

	
	/**
	 * Keeps prompting the user to enter a X and Y position until one of the pieces in the piecelist 
	 * matches those coordinates. Then it returns the index of that piece in the arraylist.
	 *
	 *@param Human p1
	 */
	static int findHumanPos(Human p1) {
		int xCurrentPos, yCurrentPos;
		Scanner input = new Scanner(System.in);

		while (true) {
			try {
				System.out.println("Select a Piece.");
				System.out.print("Enter the X position of the piece: ");
				xCurrentPos = input.nextInt();

				if (xCurrentPos == 9)
					return xCurrentPos;

				input.nextLine();
				System.out.print("Enter the Y position of the piece: ");
				yCurrentPos = input.nextInt();

				if (yCurrentPos == 9)
					return yCurrentPos;

				input.nextLine();

				break;
			} catch (InputMismatchException e) {
				System.out.println("ERROR:!\nYou didn't input a valid (literal) number! Please, try again.\n");
				input.nextLine();
			}
		}

		for (int i = 0; i < p1.getPieceList().size(); i++) {
			if (p1.getPieceList().get(i).getXPos() == xCurrentPos
					&& p1.getPieceList().get(i).getYPos() == yCurrentPos) {
				return i;
			}
		}

		return -1;
	}

	
	
	/**
	 * Asks the user to enter the coordinates of where to move to and if the checker is unable to move to that location it will display an error 
	 * and loop again. Otherwise it will move to that location entered.
	 *
	 *@param int currentPieceindex
	 *@param int[][] board
	 *@param Human p1
	 *@param Human p2
	 */
	static boolean moveHumanPiece(int currentPieceIndex, int[][] board, Human p1, Human p2) {
		int choice, xCurrentPos, yCurrentPos;
		Scanner input = new Scanner(System.in);

		if (currentPieceIndex == -1) {
			System.out.println("None of your pieces are stored in that position!");
			return false;
		}

		xCurrentPos = p1.getPieceList().get(currentPieceIndex).getXPos();
		yCurrentPos = p1.getPieceList().get(currentPieceIndex).getYPos();

		while (true) {
			try {

				System.out.print("What direction do you want your piece at co-ords\n" + "X: " + Board.getCurrentPieceXPos() 
						+ "   Y: " + Board.getCurrentPieceYPos() + "?\n"
						+ "Input a following number for the direction you want to move your piece:\n" + 
						"1: Move Up\n" + 
						"2: Move Down\n" + 
						"3: Jump Up Left Diagonal\n" + 
						"4: Jump Down Left Diagonal\n" +
						"5: Jump up Right Diagnoal\n" + 
						"6: Jump Down Right Diagonal\n" + "Direction: ");
				choice = input.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("You didn't input a valid (literal) number! Please, try again.\n");
				input.nextLine();
			}
		}

		if (p1.coordMove(choice, currentPieceIndex, board, p2)) {
			System.out.println("Moving Piece!");
			return true;
		}

		System.out.println("Move Unsuccessful!");
		return false;
	}

	
	/**
	 * Allows user to select what symbol he/she wants to play as. Either * or ^. Will give an error if something else is entered.
	 *
	 *@param Scanner input
	 */
	public static boolean selectSymbol(Scanner input) {
		// 1 == Asterisk, 2 == o
		int choice = -1;

		while (true) {
			System.out
					.print("Please select what symbol you would like to play as.\n" + "Input 1 for '*' or 2 for '^': ");
			try {
				choice = input.nextInt();
				input.nextLine();
				System.out.print("You have chosen ");
				if (choice == 1) {
					System.out.println("'*'! The second player will automatically become 'o'.\n");
					return true;
				} else {
					System.out.println("'o'! The second player will automatically become '^'.\n");
					return false;
				}
			} catch (InputMismatchException e) {
				System.out.println("You didn't input a valid (literal) number! Please, try again.\n");
				input.nextLine();
			}

		}
	}

	
	/**
 	* Retrieves the top 3 players by wins
 	* First sorts the arrayList by wins
 	* @return String returns the top 3 Players
 	*/
	public String getTopPlayers(ArrayList<Human> accounts) {
		bubbleSortWins(accounts);
		return "******TOP 3 PLAYERS******" + "\n" + "1) " + accounts.get(0).toString() + "\n" + "2) "
				+ accounts.get(1).toString() + "\n" + "3) " + accounts.get(3).toString()
				+ "*************************\n";
	}

	public void bubbleSortNames(ArrayList<Human> accounts) {
		for (int i = 0; i < accounts.size(); i++) {
			for (int a = 0; a < accounts.size() - 1; a++) {
				if (accounts.get(a).getName().compareTo(accounts.get(a + 1).getName()) > 0) {
					Human temp = accounts.get(a);
					accounts.set(a, accounts.get(a + 1));
					accounts.set(a + 1, temp);
				}
			}
		}
	}
	
	 /**
 	* Sorts the ArrayList by highest wins to lowest wins
 	* @param ArrayList<Human> account the ArrayList of Humans to sort
 	*/
	public void bubbleSortWins(ArrayList<Human> accounts) {
		for (int i = 0; i < accounts.size(); i++) {
			for (int a = 0; a < accounts.size() - 1; a++) {
				if (Integer.compare(accounts.get(a).getTotalWins(), accounts.get(a + 1).getTotalWins()) < 0) {
					Human temp = accounts.get(a);
					accounts.set(a, accounts.get(a + 1));
					accounts.set(a + 1, temp);
				}
			}
		}
	}

	
	/**
 	* Prints a menu for the user to select/view certain options.
 	*/
	public void printMenu() {
		System.out.print("Please select from one of the options:\r\n" + "\r\n" + 
						"A. Display Rules\r\n" + 
						"B. Display Top 3 Players\r\n" + 
						"C. Begin Game\r\n" + 
						"X. End Game\n\n" + "Enter your choice: ");
	}

	
	 /**
 	* OptionA(): Prints out the game rules
 	*/
	public void optionA() {
		System.out.print("\n*************************************************************************" + "\n"
				+ "Game Rules:\n" + "The game requires two players." + "\n"
				+ "Each player may create an account or log into their existing" + " account." + "\n"
				+ "Normal pieces must advance to the opposite side." + "\n"
				+ "They can jump an opposing piece diagonally if one is " + "positioned as such." + "\n"
				+ "When a normal piece reached the end, it become a king." + "\n"
				+ "Kings move forwards and backwards and may jump an " + "opposing piece in any " + "\n"
				+ "diagonal direction when present."
				+ "\n************************************************************************\n");
	}

	
	/**
 	* Utilizes information from input file which is
 	* stored in an ArrayList of Objects. Once the ArrayList is Updated,
 	* it will write out once the user is finished playing the game or chooses to exit.
 	*/
	public void printOut() throws FileNotFoundException {
		try {
			File outfile = new File("newPastPlayers.txt");
			PrintWriter out = new PrintWriter(outfile);
			for (int i = 0; i < accounts.size(); i++) {
				out.print(accounts.get(i));
			}
			out.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("ERROR: printOut(): FILE NOT FOUND.");
		}
	}



	public static void main(String[] args) throws Exception {
		System.out.println("Start");
		String choice;
		int currentPieceIndex = -1;
		boolean p1SymbAst;
		Scanner input = new Scanner(System.in);

		Board game = new Board();
		game.readInPlayer();
		game.bubbleSortNames(game.accounts);

		Human p1 = new Human();
		Human p2 = new Human();

		do {
			game.printMenu();
			choice = input.nextLine();
			if (choice.equalsIgnoreCase("A")) {
				game.optionA();
			} else if (choice.equalsIgnoreCase("B")) {
				System.out.print(game.getTopPlayers(game.accounts));
				game.bubbleSortNames(game.accounts);
			} else if (choice.equalsIgnoreCase("C")) {

				System.out.println("Player 1 Login: ");
				p1.accountSearchAndCreation(input, p1, game.accounts);

				System.out.println("Player 2 Login: ");
				p2.accountSearchAndCreation(input, p2, game.accounts);

				p1SymbAst = selectSymbol(input);

				p1.setSymbol(p1SymbAst);
				p1.setMovesUp(true);

				p2.setSymbol(!p1SymbAst);
				p2.setMovesUp(false);

				game.generateBoard(p1, p2, p1SymbAst);

				while (!game.gameOver(p1, p2)) {

					do {
						game.printBoard();
						System.out.println("Player 1's turn");
						currentPieceIndex = Board.findHumanPos(p1);
						if (currentPieceIndex == 9)
							break;

					} while (!Board.moveHumanPiece(currentPieceIndex, game.board, p1, p2));
					checkForKings(p1, game.board);
					if (currentPieceIndex == 9)
						break;

					do {
						game.printBoard();
						System.out.println("\nPlayer 2's turn");
						currentPieceIndex = Board.findHumanPos(p2);
					} while (!Board.moveHumanPiece(currentPieceIndex, game.board, p2, p1));
					checkForKings(p2, game.board);
				}

				System.out.println("GAEM OVAH");
			}
		} while (!choice.equalsIgnoreCase("X"));
		
		System.out.println("Thank you so much for playing my game!");
		input.close();
		game.bubbleSortNames(game.accounts);
		game.printOut();
		
	} // end of main

	static void checkForKings(Human p1, int[][] board) {
		for (int a = 0; a < p1.getPieceList().size(); a++) {

			if (p1.getPieceList().get(a).kingMe()) {
				int yPos = p1.getPieceList().get(a).getYPos(), xPos = p1.getPieceList().get(a).getXPos(),
						currentPiece = board[yPos][xPos];

				if (currentPiece == 1) {
					board[yPos][xPos] = 3;
				} else
					board[yPos][xPos] = 4;

			}
		}
	}

} // end of class
