/*
 * ConnectFour.java
 * Birkti Ayele
 * Date: 3/3/2021
 * Description: ConnectFour is a two-player game where one player is 'Y' and the other is 'R'. Players take turns
 * dropping disks into a vertically suspended board with seven columns and six rows. The program displays the
 * current board and game status (win, draw, or continue).
 */
import java.util.Scanner;

public class ConnectFour {
	public static Scanner input = new Scanner(System.in);
    static final int ROWS = 6, COLS = 7;
    static char[][] board = new char[ROWS][COLS]; // 6-x-7 array to simulate the board
    public static void main(String[] args) {
         // Initialize variables
         int turn = 1; // Turn starts from one
         char player = 'Y'; // Yellow goes first
         boolean winner = false;
         int game = 0;

        do {
            displayBoard(board); // Display the board

            // Play a turn
            while (!winner && turn <= 42) {
                boolean place;
                int column;
                do {
                    column = input.nextInt();
                    place = dropDisk(board, column, player);
                    displayBoard(board);
                } while (!place);

                if(isWinner(board)) // Determine the winner
                {
                    winner = true;
                    break;
                }

                // Switch players
                if (player == 'R') {
                    player = 'Y';
                } else {
                    player = 'R';
                }
                turn++; //Update total disks in the board
            }

            // Display the winner or declare a draw
            if (winner) {
                if (player == 'R') {
                    System.out.println("R has WON the game!");
                } else {
                    System.out.println("Y has WON the game!");
                }
            } else {
                System.out.println("I declare a draw");
            }

            // Check if the user wants to play again
            System.out.println("DO YOU WANT TO PLAY A NEW GAME? (type 1 for yes)");
            game = input.nextInt();

            // Reset game regardless of input
            clearBoard(board); // Clear the board and restart the game
            winner = false;
        } while (game == 1);
    }

    // Display the current game board
    private static void displayBoard(char[][] board) {
        for (int i = board.length - 1; i >= 0; i--) {
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++) {
                char disc = board[i][j];
                if (disc == '\u0000') {
                    System.out.print("   |"); 
                } else {
                    System.out.print(" " + disc + " |"); // Place 'Y' or 'R' with spacing
                }
            }
            System.out.println(); // Newline
        }
    }

    // Check if a disk can be dropped into the specified column
    public static boolean dropDisk(char[][] board, int column, char player) {
        for (int i = 0; i < board.length; i++)
            while (column < 0 || column > 6 || board[5][column] != '\u0000') {
                System.out.println("YOU ENTERED AN INVALID INTEGER OR THE COLUMN IS FULL.");
                column = input.nextInt();
            }
        boolean x = placeADisk(board, column, player);
        return x;
    }

    // Place a disk in the specified column
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
        
        // This method checks the board to see if there are four-in-a-row in either the
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
        
        for (int r = 0; r < ROWS; r++) { // For EVERY row
            for (int c = 0; c <= 3; c++) { // For columns 0-3
                if (board[r][c] != '\u0000' && // If it's not empty, AND
                        board[r][c] == board[r][c + 1] && // It matches neighbor 1 AND
                        board[r][c] == board[r][c + 2] && // It matches TWO over AND
                        board[r][c] == board[r][c + 3]) {
                    return true; // THREE over, then it's a WINNER!
                }
            }
        }
        for (int r = 0; r < 3; r++) { // For every row 0-3
            for (int c = 0; c < COLS; c++) { // For EVERY col
                if (board[r][c] != '\u0000' && // If it's not empty, AND
                        board[r][c] == board[r + 1][c] && // It matches neighbor 1 AND
                        board[r][c] == board[r + 2][c] && // It matches TWO over AND
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
        return false; // If no winner is found
    }

    // Clear the game board to start a new game
    public static void clearBoard(char[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = '\u0000'; // Reset every row/column to '\u0000'
            }
        }
    }

}
