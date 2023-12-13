package hw2.agents.moveorder;
import java.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hw2.agents.heuristics.CustomHeuristics;
import hw2.chess.game.Board;
import hw2.chess.game.move.MoveType;
import hw2.chess.game.piece.Piece;
import hw2.chess.search.DFSTreeNode;

public class CustomMoveOrderer {
	
    public static List<DFSTreeNode> order(List<DFSTreeNode> nodes) {
    	
        //implement functions for CAPTUREMOVE,CASTLEMOVE, MOVEMENTMOVE, PROMOTEPAWNMOVE, ENPASSANTMOVE

        List<DFSTreeNode> captureNodes = new ArrayList<>();
        List<DFSTreeNode> promotionNodes = new ArrayList<>();
        List<DFSTreeNode> otherNodes = new ArrayList<>();
        List<DFSTreeNode> enPassantNodes = new ArrayList<>();
        List<DFSTreeNode> castleNodes = new ArrayList<>();
        List<DFSTreeNode> anyNodes = new ArrayList<>();

        for (DFSTreeNode node : nodes) {
            if (node.getMove() != null) {
                switch (node.getMove().getType()) {
                    case CAPTUREMOVE:
                        captureNodes.add(node);
                        break;
                    case PROMOTEPAWNMOVE:
                        promotionNodes.add(node);
                        break;
                    case ENPASSANTMOVE:
                        enPassantNodes.add(node);
                        break;
                    case CASTLEMOVE:
                        anyNodes.add(node);
                        break;
                    default:
                        anyNodes.add(node);
                        break;
                }
            } else {
                anyNodes.add(node);
            }
        }

        List<DFSTreeNode> orderedNodes = new ArrayList<>();
       
        orderedNodes.addAll(promotionNodes);
        orderedNodes.addAll(captureNodes);
        orderedNodes.addAll(enPassantNodes);
        
       if(orderedNodes.get(0).getGame().getCurrentPlayer().getPlayerType().toString().compareTo("BLACK") == 0) {
        	anyNodes = maxorder(anyNodes);
        	orderedNodes.addAll(anyNodes);
        	return orderedNodes;
        	}
        else {
        	anyNodes = minorder(anyNodes);
        	anyNodes.addAll(orderedNodes);
        	return anyNodes;
        }
    }
    
    public static <T> List<List<T>> listPartition(List<T> collection,int size){
        int i = 0;
        List<List<T>> batches = new ArrayList<List<T>>();
        while(i<collection.size()){
            int nextInc = Math.min(collection.size()-i, size);
            List<T> batch = collection.subList(i,i+nextInc);
            batches.add(batch);
            i = i + nextInc;
        }

        return batches;
    }
    

   
    public static List<DFSTreeNode> maxorder(List<DFSTreeNode> nodes) //reordering base on the heuristic value
    {
     Map<Double,DFSTreeNode> T = new TreeMap<Double, DFSTreeNode>();
     List<DFSTreeNode> result = new LinkedList<DFSTreeNode>();
     
     for(DFSTreeNode node: nodes) {
      double val = CustomHeuristics.getHeuristicValue(node);
      node.setMaxPlayerHeuristicValue(val);
      T.put(-val, node);
     
     }
    
     for(Double key: T.keySet()) {
      result.add(T.get(key));
     }
     return result;
    }

  //reordering base on the heuristic value
    public static List<DFSTreeNode> minorder(List<DFSTreeNode> nodes)
    {
     Map<Double,DFSTreeNode> T = new TreeMap<Double, DFSTreeNode>();
     List<DFSTreeNode> result = new LinkedList<DFSTreeNode>();
     
     if (nodes.isEmpty()) {return result;}
     
     for(DFSTreeNode node: nodes) {
      double val = CustomHeuristics.getHeuristicValue(node);
      node.setMaxPlayerHeuristicValue(val);
      T.put(val, node);
     }
     for(Double key: T.keySet()) {
      result.add(T.get(key));
     }
     
     return result;

    }
}
