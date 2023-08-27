/*Birkti Ayele
 *3/3/2021
 *ConnectFour is a game that can be played by two players. In this game the first player is always 'Y' 
 *and the second player is 'R'. The players turn by turn drop a disk into a seven-column six-row vertically
 *suspended board. The program redisplays the board and show the status of the game (win, draw, or continue).
 * 
 */
import java.util.Scanner;

public class ConnectFour {
	public static Scanner input = new Scanner(System.in);
    static final int ROWS = 6, COLS = 7;
    static char[][] board = new char[ROWS][COLS]; // 6-x-7 array to simulate the board
    public static void main(String[] args) {
        // declare variables
        int turn = 1; // turn starts from one
        char player = 'Y'; // yellow goes first
        boolean winner = false;
        int game = 0;
        do {
            displayBoard(board); // display the board
            // play a turn
            while (!winner && turn <= 42) {
                boolean place;
                int column;
                do {
                    column = input.nextInt();
                    place = dropDisk(board, column, player);
                    displayBoard(board);
                } while (!place);
                if(isWinner(board)) // determine the winner
                {
                    winner = true;
                    break;
                }
                // switch players
                if (player == 'R') {
                    player = 'Y';
                } else {
                    player = 'R';
                }
                turn++; //Update total disks in the board
            }
            // display the winner or declare a draw
            if (winner) {
                if (player == 'R') {
                    System.out.println("R has WON the game!");
                } else {
                    System.out.println("Y has WON the game!");
                }
            } else {
                System.out.println("I declare a draw");
            }

            // We get here if the game is done, now we check if user wants to play again
            // prompt user to type 1 if they want to continue the game
            System.out.println("DO YOU WANT TO PLAY A NEW GAME? (type 1 for yes)");
            game = input.nextInt();
            // Reset game regardless of input
            clearBoard(board); // clear the board and restart the game
            winner = false;
        } while (game == 1);
    }
    private static void displayBoard(char[][] board) {
        // This method displays the current board, so the user can see what's
        // currently where, and they can plan their next move accordingly
        //
        for (int i = board.length - 1; i >= 0; i--) {
            System.out.print("| ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println(); // newline
        }
    }// displayBoard
    public static boolean dropDisk(char[][] board, int column, char player) {
        // This method checks if the column is invalid or if the column is full.
        // if the column is invalid or full it asks for another input
        //
        for (int i = 0; i < board.length; i++)
            while (column < 0 || column > 6 || board[5][column] != '\u0000') {
                System.out.println("YOU ENTERED AN INVALID INTEGER OR THE COLUMN IS FULL.");
                column = input.nextInt();
            }
        boolean x = placeADisk(board, column, player);
        return x;
    }
    public static boolean placeADisk(char[][] board, int column, char player) {
        for (int i = 0; i < ROWS; i++) {
            if (board[i][column] == '\u0000') {
                board[i][column] = player;
                return true;
            }
        }
        return false;
    }
    public static boolean isWinner(char[][] board) {
        // this method checks the board to see if there are four-in-a-row in either the
        // vertical, horizontal, or
        // the two diagonal directions. It starts by going through all combinations of
        // row and column that can
        // represent the START of four-in-a-row, and then checks the next three cells in
        // the appropriate direction
        // from there. As soon as it finds four that match (that aren't matching because
        // they're still blanks!),
        // it returns true. If it tires ALL combinations and doesn't find four-in-a-row,
        // it returns false.
        //
        // First - horizontal winners. They can start from any of the cells marked with
        // an asterisk below:
        //
        //
        // |*|*|*|*| | | | If we tried to start our four-in-a-row
        // |*|*|*|*| | | | any further to the right than this, we
        // |*|*|*|*| | | | would run out of columns before we could
        // |*|*|*|*| | | | possibly GET four-in-a-row!
        // |*|*|*|*| | | |
        // |*|*|*|*| | | |
        //
        //
        for (int r = 0; r < ROWS; r++) { // for EVERY row
            for (int c = 0; c <= 3; c++) { // for columns 0-3
                if (board[r][c] != '\u0000' && // if it's not empty, AND
                        board[r][c] == board[r][c + 1] && // it matches neighbor 1 AND
                        board[r][c] == board[r][c + 2] && // it matches TWO over AND
                        board[r][c] == board[r][c + 3]) {
                    return true; // THREE over, then it's a WINNER!
                }
            }
        }
        for (int r = 0; r < 3; r++) { // for every row 0-3
            for (int c = 0; c < COLS; c++) { // for EVERY col
                if (board[r][c] != '\u0000' && // if it's not empty, AND
                        board[r][c] == board[r + 1][c] && // it matches neighbor 1 AND
                        board[r][c] == board[r + 2][c] && // it matches TWO over AND
                        board[r][c] == board[r + 3][c]) {
                    return true; // THREE over, then it's a WINNER!
                }
            }
        }
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c <= 3; c++) {
                if (board[r][c] != '\u0000' && board[r][c] == board[r + 1][c + 1] && board[r][c] == board[r + 2][c + 2]
                        && board[r][c] == board[r + 3][c + 3]) {
                    return true; // THREE over, then it's a WINNER!
                }
            }
        }
        for (int r = 0; r < 3; r++) {
            for (int c = 3; c < COLS; c++) {
                if (board[r][c] != '\u0000' && board[r][c] == board[r + 1][c - 1] && board[r][c] == board[r + 2][c - 2]
                        && board[r][c] == board[r + 3][c - 3]) {
                    return true; // THREE over, then it's a WINNER!
                }
            }
        }
        return false; // If we haven't hit any of the above cases, then we don't have a winner
    }
    public static void clearBoard(char[][] board) {
        // This method clears the board and restart a new game
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = '\u0000'; // set every row/col to 'u/0000' like it was initially
            }
        }
    }

}


