import java.util.Arrays;
import java.util.Scanner;

class KnightsTour {

    // 8x8 two dimensional array representing the chess board
    private static int[][] board = new int[6][6];

    // Array of moves available to knight in y direction
    private static int[] dy = {2, 1, -1, -2, -2, -1, 1, 2};

    // Array of moves available to knight in x direction
    private static int[] dx = {1, 2, 2, 1, -1, -2, -2, -1};

    /*
     * Prints the chessboard. Prints "_" if space is empty.
     * Prints the integer corresponsing to the sequence of 
     * moves the knight has made.
     */
    private static void printBoard() {
        for (int[] line : board) {
            System.out.println(Arrays.toString(line));
        }
        System.out.println();
    }

    /*
     * Converts the desired starting space string into discrete x and y
     * coordinates on the chessboard. Returns int array with x, y.
     */
    private static int[] startingSpaceToPos(String startingSpace) {
        char letter = startingSpace.charAt(0);
        int x = letter - 'a';
        int y = board.length - Integer.parseInt(startingSpace.substring(1));

        return new int[] {x, y};
    }

    /*
     * Determines if desired x, y move is valid for the knight.
     * Checks if the move is in bounds of the board and not yet
     * visited.
     */
    private static boolean isValid(int x, int y) {
        if (x < 0 || y < 0 || x >= board.length || y >= board.length) {
            return false;
        }
        if (board[y][x] != 0) {
            return false;
        }

        return true;
    }

    /*
     * Recursively backtracks through all possible solutions starting at the 
     * user defined starting position. When a solution is found, prints the board,
     * and increments the solution counter. Returns total number of solutions found.
     */
    private static int solve(int x, int y, int currMove, int solutions) {
        board[y][x] = currMove;

        if (currMove >= board.length * board.length) {
            //printBoard();
            board[y][x] = 0;
            return solutions + 1;
        }

        for (int i = 0; i < dy.length; i++) {
            int nextX = x + dx[i];
            int nextY = y + dy[i];

            if (isValid(nextX, nextY)) {
                solutions = solve(nextX, nextY, currMove + 1, solutions);
            }
        }

        board[y][x] = 0;

        return solutions;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the space to start the Knight's Tour: ");
        String startingSpace = scanner.nextLine();

        int[] pos = startingSpaceToPos(startingSpace);

        long startTime = System.nanoTime();
        int solutions = solve(pos[0], pos[1], 1, 0);
        long endTime = System.nanoTime();
        long durationMS = (endTime - startTime) / 1000000;

        System.out.println(String.format("%d solutions found", solutions));
        System.out.println(String.format("%d ms", durationMS));

        scanner.close();
    }
}