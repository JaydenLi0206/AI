package hw2.agents.worker;

import java.util.Spliterator;

import hw2.chess.game.Board;
import hw2.chess.game.piece.Piece;
import hw2.chess.search.DFSTreeNode;
import hw2.agents.heuristics.CustomHeuristics;

public class Worker implements Runnable {
		private Piece[] pa;
		private Board board;
		private DFSTreeNode node;
		private double posValue;
		private double countValue;
		
		public Worker(Piece[] arr, Board board, DFSTreeNode node) {
			this.pa = arr;
			this.board = board;
			this.node = node;
			this.posValue = 0;
			this.countValue = 0;
		}
		
		public double getPosValue() {
			return this.posValue;
		}
		
		public double getCountValue() {
			return this.countValue;
		}
		
	    public void run()
	    {
	    	for (Piece x: pa) {
	        	//this.posValue += CustomHeuristics.pieceLoopWrapper(x, this.board, this.node);
	        	this.countValue += CustomHeuristics.countPieces(x);
	    	}
	    }
}
