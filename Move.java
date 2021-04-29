public interface Move {
	/**
	 * General directions are the same as a compass and orient as though the pieces
	 * are originating from the bottom. E.G. North == Forward, South == Backward.
	 */
	boolean canJump(int a, int x, int y, int[][] board);
	boolean checkLeftDiagonal(int a, int[][] board);
	boolean checkRightDiagonal(int a, int[][] board);
	boolean checkBackRightDiagonal(int a, int[][] board);
	boolean checkBackLeftDiagonal(int a, int[][] board);
	boolean checkForward(int a, int[][] board);
	boolean checkBackward(int a, int[][] board);
	boolean coordMove(int choice, int pos, int[][] board, Human p2);
}


