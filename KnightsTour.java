import java.util.Arrays;

class KnightsTour extends Thread {

    // Array of moves available to knight in y direction
    private static int[] dy = {2, 1, -1, -2, -2, -1, 1, 2};

    // Array of moves available to knight in x direction
    private static int[] dx = {1, 2, 2, 1, -1, -2, -2, -1};

    private static int rows = 5;
    private static int cols = 5;

    private static int [][] numSolutions = new int[rows][cols];

    private static int totalSolutions = 0;

    // 8x8 two dimensional array representing the chess board
    private int[][] board;

    private int x;

    private int y;

    public KnightsTour(int x, int y) {
        this.x = x;
        this.y = y;
        board = new int[rows][cols];
    }

    public void run() {
        int solutions = solve(x, y, 1, 0);
        numSolutions[y][x] = solutions;
        totalSolutions += solutions;
        printBoard(numSolutions);
        //System.out.println(String.format("%d, %d done", x, y));
        //System.out.println(String.format("%d solutions found", solutions));
    }

    /*
     * Prints the chessboard. Prints "_" if space is empty.
     * Prints the integer corresponsing to the sequence of 
     * moves the knight has made.
     */
    private static void printBoard(int[][] array) {
        for (int[] line : array) {
            System.out.println(Arrays.toString(line));
        }
        System.out.println();
    }

    /*
     * Determines if desired x, y move is valid for the knight.
     * Checks if the move is in bounds of the board and not yet
     * visited.
     */
    private boolean isValid(int x, int y) {
        if (x < 0 || y < 0 || x >= cols || y >= rows) {
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
    private int solve(int x, int y, int currMove, int solutions) {
        board[y][x] = currMove;

        if (currMove >= rows * cols) {
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

    public static void main(String[] args) throws InterruptedException {
        KnightsTour[] threads = new KnightsTour[rows*cols];
        long startTime = System.nanoTime();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                KnightsTour thread = new KnightsTour(i, j);
                threads[rows * i + j] = thread;
                thread.start();
            }
        }

        // Wait for all threads to finish
        for (KnightsTour thread : threads) {
            thread.join();
        }
        long endTime = System.nanoTime();
        long durationMS = (endTime - startTime) / 1000000;

        printBoard(numSolutions);

        System.out.println(String.format("%d solutions found", totalSolutions));
        System.out.println(String.format("%d ms", durationMS));
    }
}