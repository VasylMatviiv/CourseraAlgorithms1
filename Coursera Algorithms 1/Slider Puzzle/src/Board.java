import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final int[][] board;

    public Board(int[][] board) {
        if (board.length < 2 || board.length > 128) throw new IllegalArgumentException();
        this.board = copy(board);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.print(initial.manhattan());
        StdOut.print(initial.neighbors());
    }

    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append("Manhattan = " + manhattan() + "\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                boardString.append(board[i][j] + " ");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    public int dimension() {
        return board.length;
    }

    public int hamming() {
        int countOfHamming = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] != 0 && board[i][j] != j + i * dimension() + 1)
                    countOfHamming++;
            }

        }
        return countOfHamming;
    }

    public int manhattan() {
        int countOfManhattan = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] != 0) {
                    countOfManhattan += Math.abs(i - (board[i][j] - 1) / dimension());
                    countOfManhattan += Math.abs(j - (board[i][j] - 1) % dimension());
                }
            }
        }
        return countOfManhattan;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board board1 = (Board) y;
        return Arrays.deepEquals(board, board1.board);
    }

    private Point getBlank() {
        int col = 0, row;
        Point point = null;
        for (row = 0; row < dimension(); row++) {
            for (col = 0; col < dimension(); col++) {
                if (board[row][col] == 0)
                    point = new Point(row, col);
            }
        }
        return point;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        Point blank = getBlank();

        if (blank.getRow() > 0)
            neighbors.add(swap(board, Direction.UP, blank));
        if (blank.getRow() < dimension() - 1)
            neighbors.add(swap(board, Direction.DOWN, blank));
        if (blank.getCol() > 0)
            neighbors.add(swap(board, Direction.LEFT, blank));
        if (blank.getCol() < dimension() - 1)
            neighbors.add(swap(board, Direction.RIGHT, blank));

        return neighbors;
    }

    private int[][] copy(int[][] board) {
        int[][] newBoard = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    private Board swap(int[][] board, Direction direction, Point point) {
        int temp;
        int[][] copy = copy(board);
        switch (direction) {
            case UP: {
                temp = copy[point.getRow() - 1][point.getCol()];
                copy[point.getRow() - 1][point.getCol()] = copy[point.getRow()][point.getCol()];
                copy[point.getRow()][point.getCol()] = temp;
                break;
            }
            case DOWN: {
                temp = copy[point.getRow() + 1][point.getCol()];
                copy[point.getRow() + 1][point.getCol()] = copy[point.getRow()][point.getCol()];
                copy[point.getRow()][point.getCol()] = temp;
                break;
            }
            case LEFT: {
                temp = copy[point.getRow()][point.getCol() - 1];
                copy[point.getRow()][point.getCol() - 1] = copy[point.getRow()][point.getCol()];
                copy[point.getRow()][point.getCol()] = temp;
                break;
            }
            case RIGHT: {
                temp = copy[point.getRow()][point.getCol() + 1];
                copy[point.getRow()][point.getCol() + 1] = copy[point.getRow()][point.getCol()];
                copy[point.getRow()][point.getCol()] = temp;
                break;
            }
        }

        return new Board(copy);
    }

    public Board twin() {
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board.length - 1; col++)
                if (board[row][col] != 0 && board[row][col + 1] != 0)
                    return swap(board, Direction.RIGHT, new Point(row, col));
        throw new RuntimeException();
    }

    private enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    private static class Point {
        private final int row;
        private final int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }
}
