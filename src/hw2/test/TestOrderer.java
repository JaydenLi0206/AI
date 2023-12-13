package hw2.test;
import java.util.*;

import java.util.ArrayList;
import java.util.List;

import hw2.agents.heuristics.CustomHeuristics;
import hw2.chess.game.move.MoveType;
import hw2.chess.search.DFSTreeNode;

public class TestOrderer {
	

		/**
		 * TODO: implement me!
		 * This method should perform move ordering. Remember, move ordering is how alpha-beta pruning gets part of its power from.
		 * You want to see nodes which are beneficial FIRST so you can prune as much as possible during the search (i.e. be faster)
		 * @param nodes. The nodes to order (these are children of a DFSTreeNode) that we are about to consider in the search.
		 * @return The ordered nodes.
		 */
		static Comparator<DFSTreeNode> CaptureComparator = new Comparator<DFSTreeNode>() {
			public int compare(DFSTreeNode node1, DFSTreeNode node2) {
				double value1 = TestHeuristics.piecesWeThreaten(node1);
				double value2 = TestHeuristics.piecesWeThreaten(node2);
				return Double.compare(value2, value1);
			}
		};



		static Comparator<DFSTreeNode> heuristicComparator = new Comparator<DFSTreeNode>() {
			public int compare(DFSTreeNode node1, DFSTreeNode node2) {
				double value1 = TestHeuristics.getMaxPlayerHeuristicValue(node1, null);
				double value2 = TestHeuristics.getMaxPlayerHeuristicValue(node2, null);
				return Double.compare(value2, value1); // sort in descending order
			}
		};
		
		public static List<DFSTreeNode> order(List<DFSTreeNode> nodes)
		{
			// please replace this!
			// Implement first case is controlling the board// by default get the CaptureMoves first
			List<DFSTreeNode> moveOrder = new LinkedList<DFSTreeNode>(); //
			List<DFSTreeNode> captureNodes = new LinkedList<DFSTreeNode>(); //prioritizing capturing
			List<DFSTreeNode> defendNodes = new LinkedList<DFSTreeNode>(); //prioritizing capturing
			List<DFSTreeNode> otherNodes = new LinkedList<DFSTreeNode>(); //prioritizing random....?

			for(DFSTreeNode node : nodes){
				if(node.getMove() != null)
				{
					if (node.getGame().isInCheck(TestHeuristics.getMinPlayer(node))) {
						    moveOrder.add(node);
					} else if (TestHeuristics.DefensiveHeuristics.getNumberOfPiecesThreateningMaxPlayer(node) <TestHeuristics.getNumberOfPiecesMaxPlayerIsThreatening(node)){ //prioritize attacking?
						if (node.getMove().getType() == MoveType.CAPTUREMOVE) {
							captureNodes.add(node);
						} else {
							otherNodes.add(node);
						}
					}
					else if (TestHeuristics.DefensiveHeuristics.getNumberOfPiecesThreateningMaxPlayer(node) > TestHeuristics.getNumberOfPiecesMaxPlayerIsThreatening(node)) { //prioritizing defending
						if (node.getMove().getType() == MoveType.MOVEMENTMOVE) {
							defendNodes.add(node);
						} otherNodes.add(node);
					}
					else if (TestHeuristics.DefensiveHeuristics.getNumberOfPiecesThreateningMaxPlayer(node) ==TestHeuristics.getNumberOfPiecesMaxPlayerIsThreatening(node)) {//when equal # of pieces r threatening each other
						//if ()
						double theirThreat = TestHeuristics.pieceThatThreatUs(node); // Them threatening our piece
						double ourThreat = TestHeuristics.piecesWeThreaten(node); // Us threatening their piece
								
						if (ourThreat > theirThreat) {
							captureNodes.add(node);
						} else {
							defendNodes.add(node);
						}
						
					}else if (node.getGame().isInCheck(TestHeuristics.getMinPlayer(node))) { // Prioritizing checking the opponent the most?
						moveOrder.add(node);
					}
				} else {
						otherNodes.add(node);
					}
				///moveOrder.add(node)
			
			}

			captureNodes.sort(heuristicComparator);
			moveOrder.addAll(captureNodes);
			defendNodes.sort(heuristicComparator);
			moveOrder.addAll(defendNodes);
			otherNodes.sort(heuristicComparator);
			moveOrder.addAll(otherNodes);
			return moveOrder;

		}


	}
