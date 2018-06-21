package linked_determinant;

import java.io.*;
import java.util.*; // Program prompts for the input file name

/**
 * This program uses a recursive algorithm to compute the determinant of a
 * matrix. It reads the order of the matrix, reads the matrix, prints it out,
 * computes, and prints the determinant. It handles matrices up to and 
 * including those of order 6.
 * 
 * @version 1.0 2017-07-25
 * @author Addison Sears-Collins
 */

public class LinkedDeterminantDriver {
	
	/**
     *  Main entry point for the program.
     */
	public static void main(String[] args) {
		
		BufferedReader input = null;
		BufferedWriter output = null;
		String inputFileName = null;
		String outputFileName = null;
		String sCurrentLine = null;
		long startTime, endTime, duration; // Execution time of each matrix
		long totalDuration = 0; // Keeps track of the total execution time
		boolean buildMatrix = false; // Only true after we read matrix order
		int n = -1; // Order of the matrix
		int line = 0; // Used to count the line we are on in the input file
		int r = 0; // The row of the matrix
		int matricesRead = 0; // Keeps track of the number of matrices read
		int o1Total = 0; // Number of order 1 matrices read
		int o2Total = 0; // Number of order 2 matrices read
		int o3Total = 0; // Number of order 3 matrices read
		int o4Total = 0; // Number of order 4 matrices read
		int o5Total = 0; // Number of order 5 matrices read
		int o6Total = 0; // Number of order 6 matrices read
		float o1Prct; // Percentage of order 1 matrices read
		float o2Prct; // Percentage of order 2 matrices read
		float o3Prct; // Percentage of order 3 matrices read
		float o4Prct; // Percentage of order 4 matrices read
		float o5Prct; // Percentage of order 5 matrices read
		float o6Prct; // Percentage of order 6 matrices read
		long o1Duration = 0; // Total execution time of order 1 matrices
		long o2Duration = 0; // Total execution time of order 2 matrices
		long o3Duration = 0; // Total execution time of order 3 matrices
		long o4Duration = 0; // Total execution time of order 4 matrices
		long o5Duration = 0; // Total execution time of order 5 matrices
		long o6Duration = 0; // Total execution time of order 6 matrices
		long o1DurationAve = 0; // Average execution time of order 1 matrices
		long o2DurationAve = 0; // Average execution time of order 2 matrices
		long o3DurationAve = 0; // Average execution time of order 3 matrices
		long o4DurationAve = 0; // Average execution time of order 4 matrices
		long o5DurationAve = 0; // Average execution time of order 5 matrices
		long o6DurationAve = 0; // Average execution time of order 6 matrices
		StringBuilder sb = new StringBuilder();
		
		
		// Prompt for the file input and output name
		Scanner in = new Scanner(System.in);
		
		// Provide instructions to the user on what input the program
		// accepts.
		System.out.println("DETERMINANT CALCULATOR");
		System.out.println("This program calculates the determinant of a " + 
			"matrix of integers, for matrices up to");
		System.out.println("and including those of order 6. For your" + 
				" input file, the order of the matrix must");
		System.out.println("precede the matrix. Example format is below" +
				" (for an order 1 and order 2 matrix).");
		System.out.println("");
		
		// Show the user some sample input
		System.out.println("1");
		System.out.println("5");		
		System.out.println("2");	
		System.out.println("3 2");
		System.out.println("1 5");		
		System.out.println("");
		
		
		// Prompt for the file input and output name.
		System.out.println("Enter your file input " +
			"name to begin (e.g. input.txt" +
			" or C:\\Users\\Desktop\\input.txt):");
		inputFileName = in.nextLine();
		System.out.println("Now enter your file output " +
			"name (e.g. output.txt or" +
			" C:\\Users\\Desktop\\output.txt):");
		outputFileName = in.nextLine();		
		
		// Close the Scanner
		in.close();
		
		// Open the files that will be used for input and output	
        try {        
        	input = new BufferedReader(new FileReader(inputFileName));
            output = new BufferedWriter(new FileWriter(outputFileName));            
        } catch (Exception ioe) {
        	System.out.println("");
        	System.out.println("Oops! The file cannot be found. Please " +
        			"try again.");
            System.err.println(ioe.toString());
            return;            
        }        
        
        // Read the order of the matrix, read the matrix, print it out, 
        // compute, and print the determinant
        try {        	
     	        	
        	/*
        	 * Creates the introductory information for the matrix results
        	 * output file
        	 */
        	output.write("DETERMINANT OF A MATRIX RESULTS");
        	output.newLine();
        	output.newLine();
        	output.write("Author: Addison Sears-Collins");
        	output.newLine();
        	output.write("----------------");
        	output.newLine();
        	
        	/*
        	 * Read the input file line by line.
        	 */
        	while ((sCurrentLine = input.readLine()) != null) {  
        		
        		// Keep track of which line we are currently on in the
        		// input file
        		line++;
        		
        		// If the line is empty, continue to the next line
        		if(sCurrentLine.trim().isEmpty()) {
        			continue;
        		}
        		
        		// If the line contains my name, continue to the next line
        		if(sCurrentLine.trim().equals("Addison Sears-Collins")) {
        			continue;
        		}
        		
        		// If buildMatrix is false, this tells us that we are
        		// ready to accept the order of the next matrix.
        		// If buildMatrix is true, we are ready to build the 
        		// next matrix.   		
        		if(!buildMatrix) {
        			if (sCurrentLine.trim().length() == 1) {
        				try {
        					
        	        		/* The order of the matrix needs to be
        	        		 * a single digit integer between 1 and 6 
        	        		 */    
        					n = Integer.parseInt(sCurrentLine.trim());
        					
        					if (n >= 1 && n <= 6) {
        						
        						// The matrix order number is valid, so
        						// we are ready to build our matrix, starting
        						// from the first row
        						buildMatrix = true;
        						r = 0; // Initialize the row number to 0
        						continue;        						
        					}
        					else {
        						
        						// Error if matrix order is invalid
        						output.write("Invalid matrix size at line " + 
        								line + ": \"" +  sCurrentLine + 
        								"\". Order must be between 1 and 6" +
        								" (inclusive).");
        		            	output.newLine();   
        		        		output.write("----------------");
        		        		output.newLine();
        						continue;
        					}
        					
        					/* Error if matrix order is not an integer */
        				} catch (NumberFormatException e) {
        					output.write("Invalid matrix order at " +
        							"line " + line + ": \"" + 
        							sCurrentLine + "\"");
    		            	output.newLine();   
    		        		output.write("----------------");
    		        		output.newLine();
                			continue;
        				}      
        			}
        			else {
        				
        				// Error if the matrix order is not a single digit
    					output.write("Invalid matrix order at " +
    							"line " + line + ": \"" + 
    							sCurrentLine + "\"");
		            	output.newLine();   
		        		output.write("----------------");
		        		output.newLine();
            			continue;     
        			}  
        		}  
       		
        		// If we are ready to build our matrix, error check the 
        		// input line
        		if(buildMatrix) {

        			// Convert the string into a string array of potential
        			// integer values
        			String[] intsInString = 
        					sCurrentLine.trim().split("\\s+"); 
      			        			
        			// Error if too many values for the n-order matrix
        			if (intsInString.length > n) {
    					output.write("Too many values for this order " +
    							n + " matrix at line " + line + 
    							": \"" + sCurrentLine + "\"");
		            	output.newLine();   
		        		output.write("----------------");
		        		output.newLine();
		        		
		        		/* Read in the next matrix */
		        		buildMatrix = false;
		        		sb.setLength(0);
            			continue; 
        			}
            		
            		// Error if too few values for the n-order matrix
        			if (intsInString.length < n) {
    					output.write("Not enough values for this order " +
    							n + " matrix at line " + line + 
    							": \"" + sCurrentLine + "\"");
		            	output.newLine();   
		        		output.write("----------------");
		        		output.newLine();
		        		
		        		/* Read in the next matrix */
		        		buildMatrix = false;
		        		sb.setLength(0);
            			continue;              			
            			
        			} 
        			
        			// Check for non-integers
        			try {
    					
        				/* Create a new integer array */
    					int a[] = new int[intsInString.length];
    					
    					/* Check if only integers on this line */  				
    					for (int i = 0; i < intsInString.length; i++) {
    						a[i] = Integer.parseInt(intsInString[i]);
    					}
    					
    				// Error if this line contains non-integer values
    				} catch (NumberFormatException e) {
    					output.write("Invalid matrix value at " +
    							"line " + line + ": \"" + 
    							sCurrentLine + "\"");
		            	output.newLine();   
		        		output.write("----------------");
		        		output.newLine();
		        		
		        		/* Read in the next matrix */
		        		buildMatrix = false;
		        		sb.setLength(0);
            			continue;    
    				}         			
        		}	
        		
        		// Convert this line into a string array of
    			// integer values
    			String[] intsToString = 
    					sCurrentLine.trim().split("\\s+");
    			
    			// Build our 1-d string of integers
    			for (int i = 0; i < intsToString.length; i++) {			
    				sb.append(intsToString[i]);
    				sb.append(" ");
    				
    			}
    			
    			// We have read into the StringBuilder one more row
    			r++;

    			// Continue building our matrix if we have more rows
    			// to read
    			if (r < n) {
    				continue;
    			}
    			buildMatrix = false;

    			// Convert StringBuilder object into a string of integers
    			// separated by spaces. Reset StringBuilder to 0
    			String longString = sb.toString();
    			sb.setLength(0);
    			
        		// Convert this line into a string array of
    			// integer values
    			String[] x = 
    					longString.trim().split("\\s+");    			
     		
    			// Create our singly linked list
    			LinkedListS m = new LinkedListS();
             	
            	// Helps us move inside our string array
            	int counter = 0;
            	
            	// Stores the element of the string array
            	int element = 0;
            	
            	if (x.length == (n * n)) {
            		// If the string array has n * n integers,
            		// fill up the singly linked list
            		// a = row and b = column
            		for (int a = 0; a < n; a++) {
            			for (int b = 0; b < n; b++) {
            				element = Integer.parseInt(x[counter]);
            				m.insertAtEnd(a, b, element);
            				counter++;
            			}
            		}
            	}
            	else {
					output.write("Invalid matrix at " +
							"line " + line + ": \"" + 
							sCurrentLine + "\"");
	            	output.newLine();   
	        		output.write("----------------");
	        		output.newLine();
	        		continue;            		
            	}
        		
        		// Print the order of the matrix and the matrix
        		output.newLine();
        		output.write("Order " + n);
        		output.newLine();
        		output.newLine();
        		
        		// Element of the matrix to print to the text file
        		int info = 0;
        		
        		// a = row
        		// b = column
        		for (int a = 0; a < n; a++) {
        			for (int b = 0; b < n; b++) {
        				info = m.getValue(a, b);
        				output.write(info + " ");    			
        			}        		
        			output.newLine();        			
        		}
        		output.newLine();
        		
        		// Begin calculating the runtime of the code to calculate
        		// the determinant of the matrix
        		startTime = System.nanoTime();
        		
        		// Increment the number of matrices read
        		matricesRead++;     
        		
        		// Keep track of the type of matrices we read
        		if (n == 1)
        			o1Total++; // Order 1 matrix counter
        		else if (n == 2)
        			o2Total++; // Order 2 matrix counter
        		else if (n == 3)
        			o3Total++; // Order 3 matrix counter
        		else if (n == 4)
        			o4Total++; // Order 4 matrix counter
        		else if (n == 5)
        			o5Total++; // Order 5 matrix counter
        		else 
        			o6Total++; // Order 6 matrix counter
        		      		
        		// Instantiate the object of Determinant type
        		Determinant matrix = new Determinant(m);        		

        		// Calculate the determinant of this matrix
        		double result = matrix.det(m, n); 
     		
        		// Output the determinant result
        		output.write("Determinant = " + result);
        		output.newLine();
        		
            	// Calculate the execution time of the code
            	endTime = System.nanoTime();     	
            	duration = (endTime - startTime) / 1000;  // microseconds            	
            	output.newLine();
            	output.write("Execution Time: " + duration + 
            			" microseconds");
            	output.newLine();    
        		output.write("----------------");
        		output.newLine();
        		
        		// Keep track of the runtime of each matrix type
        		if (n == 1)
        			o1Duration += duration; // Order 1 runtime counter
        		else if (n == 2)
        			o2Duration += duration; // Order 2 runtime counter
        		else if (n == 3)
        			o3Duration += duration; // Order 3 runtime counter
        		else if (n == 4)
        			o4Duration += duration; // Order 4 runtime counter
        		else if (n == 5)
        			o5Duration += duration; // Order 5 runtime counter
        		else 
        			o6Duration += duration; // Order 6 runtime counter
        		
        		// Keep track of the total duration
        		totalDuration += duration;
    		}
        	
        	// Outputs the number of matrices read
        	output.newLine();
        	output.write("----------------------------------------");
        	output.newLine();
        	output.write("DESCRIPTIVE STATISTICS");
        	output.newLine();
        	output.write("----------------------------------------");
        	output.newLine();
        	
        	// Print the total number of matrices read
        	output.write("Total Number of Matrices Read: " 
        			+ matricesRead);
        	output.newLine();
        	
        	// Print the total number of matrices read of each order
        	output.write("Number of Order 1 Matrices: " + o1Total);
        	output.newLine();
        	output.write("Number of Order 2 Matrices: " + o2Total);
        	output.newLine();
        	output.write("Number of Order 3 Matrices: " + o3Total);
        	output.newLine();
        	output.write("Number of Order 4 Matrices: " + o4Total);
        	output.newLine();
        	output.write("Number of Order 5 Matrices: " + o5Total);
        	output.newLine();
        	output.write("Number of Order 6 Matrices: " + o6Total);
        	output.newLine();
        	output.newLine();
        	
        	// Calculate the percentage distribution of each type of matrix
        	o1Prct = (o1Total * 100.0f) / matricesRead;
        	o2Prct = (o2Total * 100.0f) / matricesRead;
        	o3Prct = (o3Total * 100.0f) / matricesRead;
        	o4Prct = (o4Total * 100.0f) / matricesRead;
        	o5Prct = (o5Total * 100.0f) / matricesRead;
        	o6Prct = (o6Total * 100.0f) / matricesRead;
        	
        	output.write("Percentage of Order 1 Matrices: " + o1Prct + "%");
        	output.newLine();
        	output.write("Percentage of Order 2 Matrices: " + o2Prct + "%");
        	output.newLine();
        	output.write("Percentage of Order 3 Matrices: " + o3Prct + "%");
        	output.newLine();
        	output.write("Percentage of Order 4 Matrices: " + o4Prct + "%");
        	output.newLine();
        	output.write("Percentage of Order 5 Matrices: " + o5Prct + "%");
        	output.newLine();
        	output.write("Percentage of Order 6 Matrices: " + o6Prct + "%");
        	
        	output.newLine();
        	output.newLine();
        	
        	// Calculate the average runtime of each type of matrix
        	if (o1Total != 0) {
        		o1DurationAve = o1Duration / o1Total;
        		output.write("Average Runtime of Order 1 Matrices: " + 
            			o1DurationAve + " microseconds");
        		output.newLine();
        	}
        	else {
        		output.write("There were no Order 1 Matrices");
        		output.newLine();
        	}
        	
        	if (o2Total != 0) {
        		o2DurationAve = o2Duration / o2Total;
        		output.write("Average Runtime of Order 2 Matrices: " + 
            			o2DurationAve + " microseconds");
        		output.newLine();
        	}
        	else {
        		output.write("There were no Order 2 Matrices");
        		output.newLine();
        	}
        	
        	if (o3Total != 0) {
        		o3DurationAve = o3Duration / o3Total;
        		output.write("Average Runtime of Order 3 Matrices: " + 
            			o3DurationAve + " microseconds");
        		output.newLine();
        	}
        	else {
        		output.write("There were no Order 3 Matrices");
        		output.newLine();
        	}
        	
        	if (o4Total != 0) {
        		o4DurationAve = o4Duration / o4Total;
        		output.write("Average Runtime of Order 4 Matrices: " + 
            			o4DurationAve + " microseconds");
        		output.newLine();
        	}
        	else {
        		output.write("There were no Order 4 Matrices");
        		output.newLine();
        	}
        	
        	if (o5Total != 0) {
        		o5DurationAve = o5Duration / o5Total;
        		output.write("Average Runtime of Order 5 Matrices: " + 
            			o5DurationAve + " microseconds");
        		output.newLine();
        	}
        	else {
        		output.write("There were no Order 5 Matrices");
        		output.newLine();
        	}
        	
        	if (o6Total != 0) {
        		o6DurationAve = o6Duration / o6Total;
        		output.write("Average Runtime of Order 6 Matrices: " + 
            			o6DurationAve + " microseconds");
        		output.newLine();
        	}
        	else {
        		output.write("There were no Order 6 Matrices");
        		output.newLine();
        	}    
        	
        	output.newLine();
        	output.newLine();
        	// Print the total number of lines read in the input file
        	output.write("Total Number of Lines Read in the Input File: " 
        			+ line);
        	output.newLine();
        	output.newLine();
        	// Print the total execution time
        	output.write("Total Execution Time of the Program: " 
        			+ totalDuration + " microseconds");
        	
        } catch (IOException e) {
         	e.printStackTrace();
        }      
                
        //  Clean up and return to the operating system.
        try {
            input.close();
        	output.close();
        } catch (Exception x) {
            System.err.println(x.toString());
        }
        return;	
	}
}

/**
 *  The class where all the work is done to calculate the determinant
 */
class Determinant {
	
	/**
     *  Constructor
     *  @param matrix The current matrix that we are evaluating
     */
	public Determinant(LinkedListS matrix){}
	
	/**
	 *  Calculates the determinant of the input matrix using recursion
	 *  Expansion occurs along the 0th row of the input matrix
	 *  @return Returns the determinant as a double value  
	 *  @param matrix The current matrix that we are evaluating
	 *  @param order The order of the matrix
	 */
	public double det(LinkedListS matrix, int order) {
		int matrixOrder = order; 
	  
		if (matrixOrder == 1) {
	 		return matrix.getValue(0, 0); // Stopping case
     	}
		else if (matrixOrder == 2) {
			return ((matrix.getValue(0, 0) * matrix.getValue(1, 1)) 
					- (matrix.getValue(0, 1) * matrix.getValue(1, 0)));
		}
		else if (matrixOrder == 3) {
			return  ((matrix.getValue(0, 0) * matrix.getValue(1, 1) * 
					matrix.getValue(2, 2)) - (matrix.getValue(0, 0) * 
					matrix.getValue(1, 2) * matrix.getValue(2, 1)) - 
					(matrix.getValue(0, 1) * matrix.getValue(1, 0) * 
					matrix.getValue(2, 2)) + (matrix.getValue(0, 1) * 
					matrix.getValue(1, 2) * matrix.getValue(2, 0)) + 
					(matrix.getValue(0, 2) * matrix.getValue(1, 0) * 
					matrix.getValue(2, 1)) - (matrix.getValue(0, 2) * 
					matrix.getValue(1, 1) * matrix.getValue(2, 0)));
		}
		else {
			double sum = 0;
					
			// Loop through each column 
			for (int b = 0; b < matrixOrder; b++) {	
				// The minor of an nxn square matrix is n - 1
				sum += Math.pow(-1, b) * matrix.getValue(0, b) * 				
				det(minor(matrix, 0, b, matrixOrder), (matrixOrder - 1));  
				// Recursive call
			}
			return sum;			
		}	
	}
	 
	/**
	 *  Creates the minor (a.k.a. submatrix) formed by deleting the 
	 *  i-th row and j-th column
	 *  @param matrix This is the input matrix
	 *  @param i The i-th row of matrix
	 *  @param j The j-th column of matrix
	 *  @param order The order of the matrix
	 *  @return Returns the minor
	 */
	private LinkedListS minor(LinkedListS matrix, int i, int j, int order) {
		int matrixOrder = order;
		LinkedListS minor = new LinkedListS();
		int a = 0;
		int b = 0;
	
		int element = 0;
		
		// Fill our minor with values by ignoring the elements in the
		// i-th row and j-th column of the larger matrix
		for (int row = 0; row < matrixOrder; row++) {
			if (row == i) { 
				continue;
			}
			for (int column = 0; column < matrixOrder; column++) {
				if (column == j) {
					continue;
				}
	 			element = matrix.getValue(row, column);
				minor.insertAtEnd(a, b, element);	
				++b;
			}
			++a;
			b = 0; // Reset to the first column of the next row of the minor
		}		
		return minor;
	}
}

/**
 *  The class is the blueprint for the nodes on the singly linked list
 */
class Node {
	private int row; // The row that the node is in
	private int col; // The column that the node is in
	private int number; // The value stored in the node
	private Node next; // The next node in the linked list
	
	/**
     *  Constructor
     *  @param row The row that the node is in
     *  @param col The column that the node is in
     *  @param value The value stored in the node
     *  @param next The next node in the linked list
     */		
	public Node(int row, int col, int number, Node next) {
		this.row = row;
		this.col = col;
		this.number = number;
		this.next = next;
	}
	
	/**
     *  @return Returns the row of the node
     */	
	public int getRow() {
		return this.row;
	}
	
	/**
     *  @return Returns the column of the node
     */	
	public int getCol() {
		return this.col;
	}
	
	/**
     *  @return Returns the number stored in the node
     */	
	public int getNum() {
		return this.number;
	}
	
	/**
     *  @return Returns the next node
     */	
	public Node getNext() {
		return this.next;
	}
	
	/**
     *  Sets the next node of the current node
     *  @param newNext The next node in the list
     */	
	public void setNext(Node newNext) {
		this.next = newNext;
	}
}


/**
 *  The class for the singly linked list
 */	
class LinkedListS {
	private Node first; //
	private Node last; //
	
	/**
     *  Constructor
     */		
	public LinkedListS () {
		first = null;
		last = null;
	}
	
	/**
     *  @return Returns true if the list is empty
     */		
	public boolean isEmpty() {
		return (first == null);
	}
	
	
	/**
     *  @return Insert a node at the end of the list
     *  @param row The row index stored in the node
     *  @param col The column index stored in the node
     *  @param value The value corresponding to the row
     *  and column of the matrix   
     */	
	public void insertAtEnd(int row, int col, int value){
		// Create a new node
		Node p = new Node(row, col, value, null);
		
		// Checks if the list is empty
		if (isEmpty())
			first = p;
		else
			last.setNext(p);
		
		// We have a new last node in the list
		last = p;
		
	}
	
	/**
     *  @return Find the node that contains the given row and column
     *  indices
     *  @param row The row index
     *  @param col The column index
     */	
	private Node findNode(int row, int col) {
		Node curr;

		// Start from the first element of the linked list
		// and traverse one element at a time until you find
		// a match
		for (curr = first; curr != null; curr = curr.getNext()) {
			if ((curr.getRow() == row) && (curr.getCol() == col)) {
				return curr; // We have a matching node
			}
		}
		
		// There is no value at that row and column index
		return null;		
	}
		
	/**
     *  @return Return the value at given the index of the row and column
     *  @param row The row index
     *  @param col The column index
     */	
	public int getValue(int row, int col){
		Node c; // Declare a node
		int value = 0; // Initialize the value variable
		
		c = findNode(row, col);
		
		if (c != null)
			value = c.getNum();
		
		return value;
	}	

}
