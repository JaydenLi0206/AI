/*
 * Please note:
 * The 2-D array heuristic values represented by 2-D array in this class is taken from Chess Programming Wiki
 * https://www.chessprogramming.org/Main_Page
 * */
package hw2.agents.heuristics;

import hw2.chess.game.piece.Piece;
import hw2.chess.game.piece.PieceType;
import hw2.chess.search.DFSTreeNode;
import hw2.chess.utils.Coordinate;
import hw2.chess.game.Board;
import java.util.*;



public class CustomHeuristics
{
	// heuristic values for each piece
	public static int PAWN = 10;
	public static int ROOK = 50;
	public static int KNIGHT = 30;
	public static int BISHOP = 30;
	public static int QUEEN = 90;
	public static int KING = 900;
	
	// reverse the 2-D array
	public static double[][] reverse(double[][] white){
		double[][] black = new double[8][8];
		for(int j = 0; j < black.length; j++){
			black[j] = white[black.length - 1 - j].clone();
		}
		return black;
	}
	// 2-D array for heuristic values for each piece
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

	// piece mobility evaluation function 
	public static double pieceMobility(Piece piece, DFSTreeNode node, double weight) {
		//System.out.println(piece.getAllMoves(node.getGame()).size());
		// switch statement to return the weight of the piece based on the number of moves it has
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
	
	// piece position evaluation function
	public static double piecePosMapper(Piece piece, Board board, DFSTreeNode node, double weight) {
		//long start = System.nanoTime();
		int x = piece.getCurrentPosition(board).getXPosition() - 1;
		int y = piece.getCurrentPosition(board).getYPosition() - 1;
		// switch statement to return the weight of the piece based on its position on the board
		if (node.getMaxPlayer().getPlayerType().toString().compareTo("BLACK") == 0) {
			switch (piece.getType()) {
			case PAWN:
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
	
	// adjacent pawn evaluation function
	public static double adjacentPawn(DFSTreeNode node, Board board) {
		double pawnEval = 0;
		for (Piece pawn: node.getGame().getBoard().getPieces(node.getMaxPlayer(), PieceType.PAWN)) {
			int x = pawn.getCurrentPosition(board).getXPosition();
			int y = pawn.getCurrentPosition(board).getYPosition();
			Coordinate left = new Coordinate(x - 1, y);
			Coordinate right = new Coordinate(x + 1, y);
			Coordinate upleft = new Coordinate(x - 1, y + 1);
			Coordinate upright = new Coordinate(x + 1, y + 1);
			Coordinate loleft = new Coordinate(x - 1, y - 1);
			Coordinate loright = new Coordinate(x + 1, y - 1);
			//adjacent pawn, subtract
			if(board.getPieceAtPosition(left) != null) {
				if (board.getPieceAtPosition(left).getType() == PieceType.PAWN && board.getPieceAtPosition(left).getPlayer() == node.getMaxPlayer()) {
					pawnEval -=1; 
				}
			}
			if(board.getPieceAtPosition(right) != null) {
				if (board.getPieceAtPosition(right).getType() == PieceType.PAWN && board.getPieceAtPosition(right).getPlayer() == node.getMaxPlayer()) {
					pawnEval -=1;
				}
			}
			//diagonal pawn, increment
			if(board.getPieceAtPosition(upleft) != null) {
				if (board.getPieceAtPosition(upleft).getType() == PieceType.PAWN && board.getPieceAtPosition(upleft).getPlayer() == node.getMaxPlayer()) {
					pawnEval +=1; 
				}
			}
			if(board.getPieceAtPosition(loleft) != null) {
				if (board.getPieceAtPosition(loleft).getType() == PieceType.PAWN && board.getPieceAtPosition(loleft).getPlayer() == node.getMaxPlayer()) {
					pawnEval +=1; 
				}
			}
			if(board.getPieceAtPosition(upright) != null) {
				if (board.getPieceAtPosition(upright).getType() == PieceType.PAWN && board.getPieceAtPosition(upright).getPlayer() == node.getMaxPlayer()) {
					pawnEval +=1;
				}
			}
			if(board.getPieceAtPosition(loright) != null) {
				if (board.getPieceAtPosition(loright).getType() == PieceType.PAWN && board.getPieceAtPosition(loright).getPlayer() == node.getMaxPlayer()) {
					pawnEval +=1;
				}
			}
			
			
		}
		return pawnEval * 0.1;
	}
	
	// piece loop wrapper function to loop through all pieces and call the evaluation functions
	public static double pieceLoopWrapper(Piece x, Board board2, DFSTreeNode node) {

		Board board = x.getGame().getBoard();

		double countEval = 0;
		double positionEval = 0;
		double pawnEval = 0;
		double mobilityEval = 0;
		
		if (x.getMaxPlayer().getPlayerType().toString().compareTo("BLACK") == 0) {
			for(Piece piece : x.getGame().getBoard().getPieces(x.getGame().getBlackPlayer()))
			{
					countEval += countPieces(piece);
					positionEval += piecePosMapper(piece, board, x, 0.1);
					//pawnEval += adjacentPawn(node, board);
					//mobilityEval += pieceMobility(piece, node, 0.1);
				}
				
				for(Piece piece : x.getGame().getBoard().getPieces(x.getGame().getWhitePlayer()))
				{
					countEval -= countPieces(piece);
					positionEval -= piecePosMapper(piece, board, x, 0.1);
				}
			}
		else {
			for(Piece piece : x.getGame().getBoard().getPieces(x.getGame().getBlackPlayer()))
				{
					countEval -= countPieces(piece);
					positionEval -= piecePosMapper(piece, board, x, 0.1);
				}
				
			for(Piece piece : x.getGame().getBoard().getPieces(x.getGame().getWhitePlayer()))
				{
					countEval += countPieces(piece);
					positionEval += piecePosMapper(piece, board, x, 0.1);
					//pawnEval += adjacentPawn(node, board);
					//mobilityEval += pieceMobility(piece, node, 0.1);
				}
		}
		return  countEval + positionEval + pawnEval + mobilityEval;
		
		

	}
	// count pieces evaluation function 
	public static double countPieces(Piece piece) {
		if (piece.getType() == PieceType.PAWN) {return PAWN;}
		if (piece.getType() == PieceType.ROOK) {return ROOK;}
		if (piece.getType() == PieceType.KNIGHT) {return KNIGHT;}
		if (piece.getType() == PieceType.BISHOP) {return BISHOP;}
		if (piece.getType() == PieceType.QUEEN) {return QUEEN;}
		if (piece.getType() == PieceType.KING) {return KING;}

		return 0;
		
	}
	
	public static double getHeuristicValue(DFSTreeNode node)
	{
		//put these variables directly to the function before turning in!

		return pieceLoopWrapper(node, 1, 1);

	}

}