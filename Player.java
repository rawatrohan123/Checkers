import java.util.ArrayList;

public class Player implements Move {
	private int numPieces;
	private boolean white;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();

	// Does this piece start at the bottom, and then can move up,
	// or at the bottom and can only go down?
	private boolean movesUp;

	Player() {
		this(false, false);
	}

	Player(boolean white, boolean movesUp) {
		this.white = white;
		this.movesUp = movesUp;

		// Add 12 pieces for each Player/AI to use.
		for (int a = 0; a < 12; a++) {
			Piece newPiece = new Piece(false, white, movesUp);
			pieces.add(0, newPiece);
		}
	}

	public ArrayList<Piece> getPieceList() {
		return pieces;
	}

	// Color of Player
	public void setSymbol(boolean white) {
		this.white = white;
	}

	public String getColor() {
		if (!white) {
			return "Black";
		} else
			return "White";
	}

	/**
	 * Sets movesUp to a new value.
	 * 
	 * @param movesUp: new value to update current movesUp
	 */
	public void setMovesUp(boolean movesUp) {
		this.movesUp = movesUp;
		
		for(int a = 0; a < pieces.size(); a++) {
			pieces.get(a).setMovesUp(movesUp);
		}
	}

	/*
	 * Returns the value of movesUp
	 */
	public boolean getMovesUp() {
		return this.movesUp;
	}

	/**
	 * Check to see if the position ahead can be jumped.
	 *
	 * @param a:     The piece we want to move in the array.
	 * @param board: The board we'll be checking our advancing spot.
	 */
	@Override
	public boolean canJump(int a, int destY, int destX, int[][] board) {
		int xPos = pieces.get(a).getXPos(), yPos = pieces.get(a).getYPos(), pieceToJump = -1,
				currPiece = board[yPos][xPos], destinationPos, evalX = Math.abs(xPos - destX),
				evalY = Math.abs(yPos - destY);

		try {
			destinationPos = board[destY][destX];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("ERROR! Outside of boundries! (Trying to Jump!)");
			return false; // If the position ahead is out of bounds, return false
		}

		if (yPos + 2 == destY && xPos + 2 == destX)
			pieceToJump = board[yPos + 1][xPos + 1];
		else if (yPos - 2 == destY && xPos + 2 == destX)
			pieceToJump = board[yPos - 1][xPos + 1];
		else if (yPos + 2 == destY && xPos - 2 == destX)
			pieceToJump = board[yPos + 1][xPos - 1];
		else if (yPos - 2 == destY && xPos - 2 == destX)
			pieceToJump = board[yPos - 1][xPos - 1];

		if (evalX == 2 && evalY == 2) {
			if (destinationPos != 8 || destinationPos != 9) {
				System.out.println("ERROR! Destination @ the following " + "\\ncoordinates: X: " + destX + " Y: "
						+ destY + "is occupied!");
				return false;
			} else if (pieceToJump == currPiece) {
				System.out.println("ERROR! You can't jump your own piece!!!");
				return false;
			} else if (pieceToJump == 8 || pieceToJump == 9) {
				System.out.println("ERROR! Can't jump over an empty space!");
				return false;
			} else {
				System.out.println("JUMPING!");
				return true;
			}
		}

		return false;
	}

	/**
	 * If the position at y - 2 && x - 2 is valid, move the piece and return true.
	 *
	 * @param a:     The piece we want to move in the array.
	 * @param board: The board we'll be checking our advancing spot.
	 */
	@Override
	public boolean checkLeftDiagonal(int a, int[][] board) {
		int xPos = pieces.get(a).getXPos(), yPos = pieces.get(a).getYPos(), destXPos = xPos - 2, destYPos = yPos - 2,
				pos;

		try {
			pos = board[destYPos][destXPos];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: Can't move piece outside the board's boundries! (UPPER-LEFT DIAGONAL)");
			return false; // If the position ahead is out of bounds, return false
		}

		if (pieces.get(a).getMovesUp() && (pos == 8 || pos == 9)) {
			if (canJump(a, destYPos, destXPos, board)) 
				return true;

		} else if (pieces.get(a).getKing() && (pos == 8 || pos == 9)) {
			if (canJump(a, destYPos, destXPos, board)) 
				return true;
			
		}

		return false;
	}

	/**
	 * If the position at y - 2 && x + 2 is valid, move the piece and return true.
	 *
	 * @param a:     The piece we want to move in the array.
	 * @param board: The board we'll be checking our advancing spot.
	 */
	@Override
	public boolean checkRightDiagonal(int a, int[][] board) {
		int xPos = pieces.get(a).getXPos(), yPos = pieces.get(a).getYPos(), destXPos = xPos + 2, destYPos = yPos - 2,
				pos;

		try {
			pos = board[destYPos][destXPos];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: Can't move piece outside the board's boundries! (UPPER-RIGHT DIAGONAL)");
			return false; // If the position ahead is out of bounds, return false
		}

		if (pieces.get(a).getMovesUp() && pos == 0) {
			if (canJump(a, destYPos, destXPos, board)) 
				return true;
			
		} else if (pieces.get(a).getKing() && pos == 0) {
			if (canJump(a, destYPos, destXPos, board)) 
				return true;
			
		}

		return false;
	}

	/**
	 * See if the position y - 2 && x + 2 is a valid position to move a piece to.
	 *
	 * @param a:     The piece we want to move in the array.
	 * @param board: The board we'll be checking our advancing spot.
	 */
	@Override
	public boolean checkBackRightDiagonal(int a, int[][] board) {
		int xPos = pieces.get(a).getXPos(), yPos = pieces.get(a).getYPos(), destXPos = xPos + 2, destYPos = yPos + 2,
				pos;

		try {
			pos = board[destYPos][destXPos];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: Can't move piece outside the board's boundries! (DOWN-RIGHT DIAGONAL)");
			return false; // If the position ahead is out of bounds, return false
		}

		if (!pieces.get(a).getMovesUp() && (pos == 8 || pos == 9)) {
			if (canJump(a, destYPos, destXPos, board)) 
				return true;
			
		} else if (pieces.get(a).getKing() && (pos == 8 || pos == 9)) {
			if (canJump(a, destYPos, destXPos, board)) 
				return true;
			
		}

		return false;
	}

	/**
	 * Check to see if the position we want is plausible for the piece we want to
	 * move, in this case y + 2 && x - 2. Return true if it's possible, false
	 * otherwise.
	 *
	 * @param a:     The piece we want to move in the array.
	 * @param board: The board we'll be checking our advancing spot.
	 */
	@Override
	public boolean checkBackLeftDiagonal(int a, int[][] board) {
		int xPos = pieces.get(a).getXPos(), yPos = pieces.get(a).getYPos(), destXPos = xPos - 2, destYPos = yPos + 2,
				pos;

		try {
			pos = board[destYPos][destXPos];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: Can't move piece outside the board's boundries! (DOWN-LEFT DIAGONAL)");
			return false; // If the position ahead is out of bounds, return false
		}

		if (!pieces.get(a).getMovesUp() && (pos == 8 || pos == 9)) {
			if (canJump(a, destYPos, destXPos, board)) 
				return true;
			
		} else if (pieces.get(a).getKing() && (pos == 8 || pos == 9)) {
			if (canJump(a, destYPos, destXPos, board)) 
				return true;
			
		}

		return false;
	}

	/**
	 * Take in a number for the piece we're about to move, as well as the character
	 * ahead of the spot we want to move it. If the piece we're checking is either a
	 * King or can move up, and the position ahead is not occupied by a piece,
	 * update the Y position and return true;
	 *
	 * @param a:     Piece to retrieve in the ArrayList
	 * @param board: The board we want to check
	 */
	@Override
	public boolean checkForward(int a, int[][] board) {
		int xPos = pieces.get(a).getXPos(), yPos = pieces.get(a).getYPos(), pos;

		try {
			pos = board[yPos - 1][xPos];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: Can't move piece outside the board's boundries! (UP)");
			return false; // If the position ahead is out of bounds, return false
		}

		if (pieces.get(a).getMovesUp() && (pos == 8 || pos == 9))
			return true;
		else if (pieces.get(a).getKing() && (pos == 8 || pos == 9))
			return true;

		return false;
	}

	/**
	 * Acquire piece we want to move, and see if the position ahead isn't occupied.
	 * If not, see if the piece can move back or is a King. Update the Y position
	 * and return true if conditions met.
	 *
	 * @param a:     Piece to retrieve in the ArrayList
	 * @param board: The board we want to check
	 */
	@Override
	public boolean checkBackward(int a, int[][] board) {
		int xPos = pieces.get(a).getXPos(), yPos = pieces.get(a).getYPos(), currentPiece = board[yPos][xPos], pos;

		try {
			pos = board[yPos + 1][xPos];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: Can't move piece outside the board's boundries! (DOWN)");
			return false; // If the position ahead is out of bounds, return false
		}

		if (!pieces.get(a).getMovesUp() && (pos == 8 || pos == 9))
			return true;
		else if (pieces.get(a).getKing() && (pos == 8 || pos == 9))
			return true;

		return false;
	}

	public boolean coordMove(int choice, int pos, int[][] board, Human p2) {
		int yPos = pieces.get(pos).getYPos(), xPos = pieces.get(pos).getXPos(), piece = board[yPos][xPos];

		if (choice == 1) {
			if (checkForward(pos, board)) {
				pieces.get(pos).moveUp();
				board[yPos - 1][xPos] = piece;
				board[yPos][xPos] = 0;
				return true;
			}
		} else if (choice == 2) {
			if (checkBackward(pos, board)) {
				pieces.get(pos).moveDown();
				board[yPos + 1][xPos] = piece;
				board[yPos][xPos] = 0;
				return true;
			}

		} else if (choice == 3) {
			if (checkLeftDiagonal(pos, board)) {
				pieces.get(pos).jumpUpLeft();
				board[yPos - 2][xPos - 2] = piece;
				board[yPos - 1][xPos - 1] = 0;
				board[yPos][xPos] = 0;
				removePiece(yPos - 1, xPos - 1, p2);
				return true;
			}
		} else if (choice == 4) {
			if (checkBackLeftDiagonal(pos, board)) {
				pieces.get(pos).jumpDownLeft();
				board[yPos + 2][xPos - 2] = piece;
				board[yPos + 1][xPos - 1] = 0;
				board[yPos][xPos] = 0;
				removePiece(yPos + 1, xPos - 1, p2);
				return true;
			}
		} else if (choice == 5) {
			if (checkRightDiagonal(pos, board)) {
				pieces.get(pos).jumpUpRight();
				board[yPos - 2][xPos + 2] = piece;
				board[yPos - 1][xPos + 1] = 0;
				board[yPos][xPos] = 0;
				removePiece(yPos - 1, xPos + 1, p2);
				return true;
			}
		} else if (choice == 6) {
			if (checkBackRightDiagonal(pos, board)) {
				pieces.get(pos).jumpDownRight();
				board[yPos + 2][xPos + 2] = piece;
				board[yPos + 1][xPos + 1] = 0;
				board[yPos][xPos] = 0;
				removePiece(yPos + 1, xPos + 1, p2);
				return true;
			}
		}

		return false;
	}
	
	public static void removePiece(int yPos, int xPos, Human p1) {
		int p1XPos, p1YPos;
		
		for(int a = 0; a < p1.getPieceList().size() ; a++) {
			p1XPos = p1.getPieceList().get(a).getXPos();
			p1YPos = p1.getPieceList().get(a).getYPos();
			
			if(p1XPos == xPos && p1YPos == yPos) {
				p1.getPieceList().remove(a);
				return;
			}	
		}
	}

} // end of Class










