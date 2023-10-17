package practice1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class aStar {
	char[][] matrix;
	Node start;
	Node end;
	int column;
	int row;
    Set<Node> obstacleSet;

	
    public aStar(char[][] matrix, Node start, Node end,int column,int row, Set<Node> obstacleSet) {
		super();
		this.matrix = matrix;
		this.start = start;
		this.end = end;
		this.column=column;
		this.row=row;
        this.obstacleSet=obstacleSet;
	}


	static double getDistance(Node a, Node b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
        
    }

    List<Node> findPath() {

        Set<Node> openSet = new HashSet<>();
        Set<Node> closedSet = new HashSet<>();
        Map<Node, Double> gScore = new HashMap<>();
        Map<Node, Double> fScore = new HashMap<>();
        Map<Node, Node> parents = new HashMap<>();

        // initializing g and f values of start node
        gScore.put(start, 0.0);
        start.h = getDistance(start, end);
        fScore.put(start, 0.0 + start.h);

        openSet.add(start);

        //tiebreaking function
        Random random = new Random();

        while (!openSet.isEmpty()) {
            //select the node with the minimum fScore value
            Node current = openSet.stream().min(Comparator.comparing(fScore::get).thenComparing(node -> random.nextDouble())).get();

            //if we have reached the destination, we return the path
            if (current.equals(end)) {
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    if (matrix[current.x][current.y] != 'I' && matrix[current.x][current.y] != 'G') {
                        matrix[current.x][current.y] = '+';
                    }
                    current = parents.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            //we remove the current node from the openSet and add it to the closedSet
            openSet.remove(current);
            closedSet.add(current);

            //find every node close to the current node
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if ((i == 0 && j == 0) || (i != 0 && j != 0)) {
                        continue;
                    }

                    int neighborX = current.x + i;
                    int neighborY = current.y + j;

                    boolean flag = false;

                    if ((neighborX < 0 || neighborX >= column) || (neighborY < 0 || neighborY >= row)) {
                        continue;
                    }
                        
                    for (Node aNode : obstacleSet) {
                        if (aNode.getX() == neighborX && aNode.getY() == neighborY) {
                            flag=true;
                        }
                    }
                    if(flag) {
                        break;
                    }
                
                    Node neighbor = new Node(neighborX, neighborY);

                    // if the neighbor is in the closedSet we skip it
                    if (closedSet.contains(neighbor)) {
                        continue;
                    }

                    //calculating g of neighbor node
                    double tentativeGScore = gScore.get(current) + getDistance(current, neighbor);

                    boolean isalreadyin = false;

                    for (Node aNode : openSet) {
                        if (aNode.getX() == neighborX && aNode.getY() == neighborY) {
                            isalreadyin = true;
                        }
                    }

                    for(Node aNode:closedSet){
                        if(aNode.getX()==neighborX && aNode.getY() == neighborY){
                            isalreadyin = true;
                            break;
                        }
                    }

                    if (isalreadyin == false && tentativeGScore < gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                        parents.put(neighbor, current);
                        gScore.put(neighbor, tentativeGScore);
                        fScore.put(neighbor, tentativeGScore + getDistance(neighbor, end) + random.nextDouble());
                        openSet.add(neighbor);

                    }    
                    
                }
            }
        }
    return new ArrayList<>();
    }
}


