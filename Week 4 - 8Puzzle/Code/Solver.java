import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Solver {

    private final MinPQ<SearchNode> pq;
    private int totalMoves;
    private boolean isSolvable;

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        int manhattanDistance;
        SearchNode previousSearchNode;

        private SearchNode(Board board, int moves, SearchNode previousNode) {
            this.board = board;
            this.moves = moves;
            this.manhattanDistance = board.manhattan();
            this.previousSearchNode = previousNode;
        }

        public int compareTo(SearchNode that) {
            int manhattanOfA = this.manhattanDistance;
            int manhattanOfB = that.manhattanDistance;
            if ((this.moves + manhattanOfA) < (that.moves + manhattanOfB)) return -1;
            else if ((this.moves + manhattanOfA) > (that.moves + manhattanOfB)) return 1;
            else return 0;
        }
    }


    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("No argument passes");

        pq = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();

        SearchNode theFirstNode = new SearchNode(initial, 0, null);
        SearchNode theFirstNodeTwin = new SearchNode(initial.twin(), 0, null);

        pq.insert(theFirstNode);
        twinPq.insert(theFirstNodeTwin);

        while (!pq.min().board.isGoal() && !twinPq.min().board.isGoal()) {
            SearchNode removedNormal = pq.delMin();
            SearchNode removedTwin = twinPq.delMin();
            for (Iterator<Board> i = removedNormal.board.neighbors().iterator(); i.hasNext(); ) {
                Board item = i.next();
                if (removedNormal.previousSearchNode != null && item.equals(removedNormal.previousSearchNode.board)) {
                    continue;
                }
                SearchNode newItem = new SearchNode(item, (removedNormal.moves + 1), removedNormal);
                pq.insert(newItem);
            }

            for (Iterator<Board> i = removedTwin.board.neighbors().iterator(); i.hasNext(); ) {
                Board item = i.next();
                if (removedTwin.previousSearchNode != null && item.equals(removedTwin.previousSearchNode.board)) {
                    continue;
                }
                SearchNode newItem = new SearchNode(item, removedTwin.moves + 1, removedTwin);
                twinPq.insert(newItem);
            }
        }

        if (pq.min().board.isGoal()) {
            totalMoves = pq.min().moves;
            isSolvable = true;
        }
        if (twinPq.min().board.isGoal()) {
            totalMoves = -1;
            isSolvable = false;
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return totalMoves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable) return null;
        Stack<Board> sol = new Stack<>();
        SearchNode node = pq.min();
        while (node != null) {
            sol.push(node.board);
            node = node.previousSearchNode;
        }
        return sol;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
