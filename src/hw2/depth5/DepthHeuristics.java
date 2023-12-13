package hw2.depth5;

import hw2.chess.game.piece.Piece;
import hw2.chess.game.piece.PieceType;
import hw2.chess.search.DFSTreeNode;
import hw2.chess.game.Board;
import java.util.*;

import hw2.agents.worker.Worker;

public class DepthHeuristics
{
	
	public static int PAWN = 10;
	public static int ROOK = 50;
	public static int KNIGHT = 30;
	public static int BISHOP = 30;
	public static int QUEEN = 90;
	public static int KING = 900;
	
	//HashMap<String, Integer> positionScores = new HashMap<>();
//	public static Map<String, Double> scores = new HashMap<String, Double>() {{
//	    put("(4, 4)", 1.0);
//	    put("(4, 5)", 1.0);
//	    put("(5, 4)", 1.0);
//	    put("(5, 5)", 1.0);
//	    put("(3, 3)", 0.5);
//	    put("(4, 3)", 0.5);
//	    put("(5, 3)", 0.5);
//	    put("(6, 3)", 0.5);
//	    put("(3, 6)", 0.5);
//	    put("(4, 6)", 0.5);
//	    put("(5, 6)", 0.5);
//	    put("(6, 6)", 0.5);
//	    put("(3, 4)", 0.5);
//	    put("(3, 5)", 0.5);
//	    put("(6, 4)", 0.5);
//	    put("(6, 5)", 0.5);
//	}};
	
	public static double[][] reverse(double[][] white){
		double[][] black = new double[8][8];
		for(int j = 0; j < black.length; j++){
			black[j] = white[black.length - 1 - j].clone();
		}
		return black;
	}
	
	public static double[][] whitePawnPos = {
	                           {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
	                           {5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0},
	                           {1.0, 1.0, 2.0, 3.0, 3.0, 2.0, 1.0, 1.0},
	                           {0.5, 0.5, 1.0, 2.5, 2.5, 1.0, 0.5, 0.5},
	                           {0.0, 0.0, 0.0, 2.0, 2.0, 0.0, 0.0, 0.0},
	                           {0.5, -0.5, -1.0, 0.0, 0.0, -1.0, -0.5, 0.5},
	                           {0.5, 1.0, 1.0, -2.0, -2.0, 1.0, 1.0, 0.5},
	                           {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
	                       };
	
	public static double[][] blackPawnPos = reverse(whitePawnPos);
	
	public static double[][] whiteKnightPos = {
	                                           {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
	                                           {-4.0, -2.0, 0.0, 0.0, 0.0, 0.0, -2.0, -4.0},
	                                           {-3.0, 0.0, 1.0, 1.5, 1.5, 1.0, 0.0, -3.0},
	                                           {-3.0, 0.5, 1.5, 2.0, 2.0, 1.5, 0.5, -3.0},
	                                           {-3.0, 0.0, 1.5, 2.0, 2.0, 1.5, 0.0, -3.0},
	                                           {-3.0, 0.5, 1.0, 1.5, 1.5, 1.0, 0.5, -3.0},
	                                           {-4.0, -2.0, 0.0, 0.5, 0.5, 0.0, -2.0, -4.0},
	                                           {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}
	                                       };
	
	public static double[][] blackKnightPos = reverse(whiteKnightPos);
	
	public static double[][] whiteBishopPos = {
	                                           {-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
	                                           {-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0},
	                                           {-1.0, 0.0, 0.5, 1.0, 1.0, 0.5, 0.0, -1.0},
	                                           {-1.0, 0.5, 0.5, 1.0, 1.0, 0.5, 0.5, -1.0},
	                                           {-1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, -1.0},
	                                           {-1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, -1.0},
	                                           {-1.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.5, -1.0},
	                                           {-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}
	                                       };
	
	public static double[][] blackBishopPos = reverse(whiteBishopPos);
	
	public static double[][] whiteRookPos = {
	                                         {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
	                                         {0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5},
	                                         {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
	                                         {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
	                                         {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
	                                         {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
	                                         {-0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5},
	                                         {0.0, 0.0, 0.0, 0.5, 0.5, 0.0, 0.0, 0.0}
	                                     };
	
	public static double[][] blackRookPos = reverse(whiteRookPos);
	
	public static double[][] whiteQueenPos =  {
	                                           {-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0},
	                                           {-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0},
	                                           {-1.0, 0.0, 0.5, 0.8, 0.5, 0.5, 0.0, -1.0},
	                                           {-0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5},
	                                           {0.0, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5},
	                                           {-1.0, 0.5, 0.5, 0.5, 0.5, 0.5, 0.0, -1.0},
	                                           {-1.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, -1.0},
	                                           {-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0}
	                                       };
	
	public static double[][] blackQueenPos = reverse(whiteQueenPos);
	
	public static double[][] whiteKingPos = {
	                                         {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
	                                         {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
	                                         {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
	                                         {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
	                                         {-2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
	                                         {-1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
	                                         {2.0, 2.0, 0.0, 0.0, 0.0, 0.0, 2.0, 2.0},
	                                         {2.0, 3.0, 1.0, 0.0, 0.0, 1.0, 3.0, 2.0}
	                                     };
	
	public static double[][] blackKingPos = reverse(whiteKingPos);
	
	public static double pieceMobility(Piece piece, DFSTreeNode node, double weight) {
		//System.out.println(piece.getAllMoves(node.getGame()).size());
		switch (piece.getAllMoves(node.getGame()).size()){
			case 6:
				return weight * 6;
			case 5:
				return weight * 5;
			case 4:
				return weight * 4;
			case 3:
				return weight * 3;
			case 2:
				return weight * 2;
			case 1:
				return weight * 1;
			default:
				return 0;
		}
	}
	
	public static double piecePosMapper(Piece piece, Board board, DFSTreeNode node) {
		//long start = System.nanoTime();
		int x = piece.getCurrentPosition(board).getXPosition() - 1;
		int y = piece.getCurrentPosition(board).getYPosition() - 1;
		if (node.getMaxPlayer().getPlayerType().toString().compareTo("BLACK") == 0) {
			switch (piece.getType()) {
			case PAWN:
				//long finish = System.nanoTime();
//				System.out.println(finish - start);
				return blackPawnPos[x][y];
			case ROOK:
				return blackRookPos[x][y];
			case KNIGHT:
				return blackKnightPos[x][y];
			case BISHOP:
				return blackBishopPos[x][y];
			case QUEEN:
				return blackQueenPos[x][y];
			case KING:
				return blackKingPos[x][y];
			}
		}else {
			switch (piece.getType()) {
			case PAWN:
				return whitePawnPos[x][y];
			case ROOK:
				return whiteRookPos[x][y];
			case KNIGHT:
				return whiteKnightPos[x][y];
			case BISHOP:
				return whiteBishopPos[x][y];
			case QUEEN:
				return whiteQueenPos[x][y];
			case KING:
				return whiteKingPos[x][y];
		}}

		return 0.0;
	}
	
	public static double pieceLoopWrapper(DFSTreeNode node, double countWeights, double positionWeights) {

		Board board = node.getGame().getBoard();

		double countEval = 0;
		double positionEval = 0;
		double mobilityEval = 0;
		
		if (node.getMaxPlayer().getPlayerType().toString().compareTo("BLACK") == 0) {
			for(Piece piece : node.getGame().getBoard().getPieces(node.getGame().getBlackPlayer()))
			{
					countEval += countPieces(piece);
					//positionEval += piecePosMapper(piece, board, node);
					//mobilityEval += pieceMobility(piece, node, 0.4);
				}
				
				for(Piece piece : node.getGame().getBoard().getPieces(node.getGame().getWhitePlayer()))
				{
					countEval -= countPieces(piece);
					//positionEval -= piecePosMapper(piece, board, node);
					//mobilityEval -= pieceMobility(piece, node, 0.4);
				}
			}
		else {
			for(Piece piece : node.getGame().getBoard().getPieces(node.getGame().getBlackPlayer()))
				{
					countEval -= countPieces(piece);
					//positionEval -= piecePosMapper(piece, board, node);
					//mobilityEval -= pieceMobility(piece, node, 0.4);
				}
				
			for(Piece piece : node.getGame().getBoard().getPieces(node.getGame().getWhitePlayer()))
				{
					countEval += countPieces(piece);
					//positionEval += piecePosMapper(piece, board, node);
					//mobilityEval += pieceMobility(piece, node, 0.4);
				}
		}
		//System.out.println(countEval + positionEval);
		//System.out.println(countEval);
		return  countEval + positionEval + mobilityEval;
	}
	
	public static double countPieces(Piece piece) {
		if (piece.getType() == PieceType.PAWN) {return PAWN;}
		if (piece.getType() == PieceType.ROOK) {return ROOK;}
		if (piece.getType() == PieceType.KNIGHT) {return KNIGHT;}
		if (piece.getType() == PieceType.BISHOP) {return BISHOP;}
		if (piece.getType() == PieceType.QUEEN) {return QUEEN;}
		if (piece.getType() == PieceType.KING) {return KING;}

		return 0;
		
	}
//	
//	public static double piecePosition(Piece piece, Board board) { //Change it to still be in that position in two moves
//		
//		long sTime = System.nanoTime();
//		
//		String key = piece.getCurrentPosition(board).toString();
//		long fTime = System.nanoTime();
//		if (scores.containsKey(key)) {
//			return scores.get(key);}
//		return 0.0;
//	}
	
	public static double getHeuristicValue(DFSTreeNode node)
	{
		//put these variables directly to the function before turning in!
		double countWeights = 1;
		double positionWeights = 1;

		return pieceLoopWrapper(node, countWeights, positionWeights);

	}

}
