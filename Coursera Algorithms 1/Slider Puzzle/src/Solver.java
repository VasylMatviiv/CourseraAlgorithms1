import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Solver {
    private int countOfMoves;
    private boolean solvable;
    private Node currentNode;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        solve(initial);

    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

    private void solve(Board initial) {
        ArrayList<Board> checkedBoards = new ArrayList<Board>();

        MinPQ<Node> nodes = new MinPQ<Node>(new NodeComparator());
        nodes.insert(new Node(initial));
        while (!nodes.isEmpty()) {
            currentNode = nodes.delMin();
            countOfMoves = currentNode.countOfMoves;
            if (currentNode.current.isGoal()) {
                solvable = true;
                break;
            } else {
                checkedBoards.add(currentNode.current);
                countOfMoves++;
                Iterable<Board> neighbors = currentNode.current.neighbors();
                neighbors.forEach(board ->
                {
                    if (!checkedBoards.contains(board)) {
                        nodes.insert(new Node(currentNode, board, countOfMoves));
                    }
                });
            }
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return countOfMoves;
    }

    public Iterable<Board> solution() {
        Stack<Board> solution = new Stack<>();
        Node copy = currentNode;
        while (copy != null) {
            solution.push(copy.current);
            copy = copy.parent;
        }

        return solvable ? solution : null;
    }

    private class Node {
        private final Board current;
        private final int manhattan;
        private Node parent;
        private int countOfMoves;

        public Node(Board current) {
            this.current = current;
            this.manhattan = current.manhattan();
        }

        public Node(Node parent, Board current, int countOfMoves) {
            this.parent = parent;
            this.current = current;
            this.manhattan = current.manhattan();
            this.countOfMoves = countOfMoves;
        }

        public int getCountOfMoves() {
            return countOfMoves;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return manhattan == node.manhattan &&
                    Objects.equals(current, node.current);
        }

        @Override
        public int hashCode() {
            return Objects.hash(current, manhattan);
        }
    }

    private class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            int manhattan1 = o1.current.manhattan() + o1.getCountOfMoves();
            int manhattan2 = o2.current.manhattan() + o2.getCountOfMoves();
            if (manhattan1 == manhattan2) {
                return 0;
            }
            return manhattan1 > manhattan2 ? 1 : -1;
        }
    }
}
