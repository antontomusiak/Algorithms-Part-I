import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;


public class Solver {
    private final ArrayList<Board> s;
    private final int moves;
    private final Board initial;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        this.initial = initial;
        SearchNode sol = solve();
        s = buildSolution(sol);
        if (sol == null) {
            this.moves = -1;
        } else {
            this.moves = sol.moves;
        }
    }

    private SearchNode solve() {
        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));
        SearchNode node = pq.min();
        if (node.board.isGoal()) return node;
        MinPQ<SearchNode> tpq = new MinPQ<>();
        tpq.insert(new SearchNode(initial.twin(), 0, null));
        SearchNode tnode = tpq.min();
        if (tnode.board.isGoal()) return null;
        while (!pq.isEmpty() || !tpq.isEmpty()) {
            node = pq.delMin();
            tnode = tpq.delMin();
            for (Board neighbour: node.board.neighbors()) {
                if (neighbour.isGoal()) {
                    SearchNode res = new SearchNode(neighbour, node.moves + 1, node);
                    return res;
                }
                if (node.previous == null || !node.previous.board.equals(neighbour)) {
                    pq.insert(new SearchNode(neighbour, node.moves + 1, node));
                }
            }
            for (Board neighbour: tnode.board.neighbors()) {
                if (neighbour.isGoal()) {
                    return null;
                }
                if (tnode.previous == null || !tnode.previous.board.equals(neighbour)) {
                    tpq.insert(new SearchNode(neighbour, tnode.moves + 1, tnode));
                }
            }
        }
        return null;
    }

    public int moves() {
        return this.moves;
    }

    public boolean isSolvable() {
        return s != null;
    }

    private ArrayList<Board> buildSolution(SearchNode node) {
        ArrayList<Board> res = new ArrayList<>();
        if (node == null) return null;
        while (node != null) {
            res.add(node.board);
            node = node.previous;
        }
        Collections.reverse(res);
        return res;
    }

    public Iterable<Board> solution() {
        return s;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final int priority;
        private SearchNode previous;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.priority = moves + board.manhattan();
            this.previous = previous;

        }

        @Override
        public int compareTo(SearchNode that) {
            if (this.priority < that.priority) return -1;
            if (this.priority > that.priority) return 1;
            return 0;
        }
    }
}

