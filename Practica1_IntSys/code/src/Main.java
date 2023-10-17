package practice1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class Main {


	static int row = 60;
	static int column = 80;
	static double percobstacle = 0.3;
	static Scanner scanner = new Scanner(System.in);
	static Set<Node> obstacleSet = new HashSet<Node>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		//creating file
		
		String filename = "output.txt";
		File outputFile = new File(filename);
		
		 try {
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
			
			
			Random rand = new Random();
			char[][] matrix= new char[row][column];		
			
			//creating obstacles
			for (int i=0;i<row*column*percobstacle;i++) {
				int r= rand.nextInt(row);
				int c=rand.nextInt(column);
				

				Node obstacle = new Node(r, c);

				obstacleSet.add(obstacle);
				
				matrix[r][c]='*';
				
			}

			
			
			
			//select initial state 
			
			int rs= rand.nextInt(row);
			int cs=rand.nextInt(column);
			

			Node start = new Node(rs, cs);
			
			
			char instate=matrix[rs][cs];
			
			if(instate=='*') {
				bw.write("Initial state is an obstacle");
				System.out.println("File has been writen");
				bw.close();
				System.exit(0);
				
			}
			

			matrix[rs][cs]='I';
		
		
			//select goal state
		
			int rg = rand.nextInt(row);
			int cg = rand.nextInt(column);
		
			Node goal = new Node(rg, cg);
			char goalstate=matrix[rg][cg];
			
			if (goalstate == '*') {
				bw.write("Goal state is an obstacle");
				System.out.println("File has been writen");
				bw.close();
				System.exit(0);
				
			}
			else if(goalstate == 'I') {
				bw.write("Goal state is initial state");
				System.out.println("File has been writen");
				bw.close();
				System.exit(0);
				
			}

						
			matrix[rg][cg]='G';
				
			aStar s=new aStar(matrix,start,goal,column,row,obstacleSet);
			s.findPath();
			
			
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < column; j++) {
					if(j == column - 1){    
						bw.write(matrix[i][j]);
					}
					else if(matrix[i][j] == '*' || matrix[i][j] == '+' || matrix[i][j] == 'I' || matrix[i][j] == 'G'){
						bw.write(matrix[i][j]);
					}
					else{
						bw.write(" ");
					}        
				}
				bw.newLine();
				
			}

			if(s.findPath().isEmpty()){
				bw.write("Impossible to find a path from I to G");
			}
			
			bw.close();
			
			System.out.println("File has been writen");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


}


