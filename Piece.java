public class Piece{
    
	private boolean king;
	private int xPos, yPos;
	private boolean asterisk;
    
	// Does this piece start at the bottom, and then can move up,
	// or at the bottom and can only go down?
	private boolean movesUp;


	public Piece() {
    	this(false, false, false);
	}

	public Piece(boolean king, boolean asterisk, boolean moveUp) {
    	this.king = king;
    	this.asterisk = asterisk;
    	this.movesUp = moveUp;
	}

	// King
	public void setKing(boolean king) {
    	this.king = king;
	}

	public boolean getKing() {
    	return this.king;
	}

	// Position
	public void setXPos(int xPos) {
    	this.xPos = xPos;
	}

	public int getXPos() {
    	return this.xPos;
	}

	public void setYPos(int yPos) {
    	this.yPos = yPos;
	}

	public int getYPos() {
    	return this.yPos;
	}

	// Color of Player
	public void setSymbol(boolean asterisk) {
    	this.asterisk = asterisk;
	}

	public String getColor() {
    	if (!asterisk) {
        	return "^";
    	} else
        	return "*";
	}
    
	/* Returns the value of movesUp
 	*/
	public boolean getMovesUp() {
		return this.movesUp;
	}
	
	public void setMovesUp(boolean movesUp) {
		this.movesUp = movesUp;
	}
	
	public void moveUp() {
		yPos--;
	}
	
	public void moveDown() {
		yPos++;
	}
	
	public void jumpUpLeft() {
		yPos -= 2;
		xPos -= 2;
	}
	
	public void jumpDownLeft() {
		yPos += 2;
		xPos -= 2;

	}
	
	public void jumpUpRight() {
		yPos -= 2;
		xPos += 2;
	}
	
	public void jumpDownRight() {
		yPos += 2;
		xPos += 2;

	}
	
	public boolean kingMe() {
		if(movesUp && yPos == 0 && !king) 
			king = true;
		else if(!movesUp && yPos == 7 && !king) 
			king = true;

		return king;
	}
}

