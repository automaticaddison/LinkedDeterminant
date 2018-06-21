Author: Addison Sears-Collins

Used Java Eclipse IDE (Java 8: JavaSE 1.8)

LinkedDeterminantDriver.java contains the main method and is the driver class for the application.

If running program from the command line on Windows 7, navigate to where the compiled classes are 
located (usually the bin directory) in the file and use the command
     java -cp . determinant.DeterminantDriver 
in order to run the program. Alternatively, you can delete the package statement on the first line
of the program and run using the command 'javac LinkedDeterminantDriver.java' + 'java LinkedDeterminantDriver' 
in the command prompt

The program will prompt for the file input name (e.g. input.txt or C:\Users\Addison\Desktop\input.txt) 
and file output name (e.g. output.txt or C:\Users\Addison\Desktop\output.txt).

The input file needs to exist in order for the program to run. The output file does not have to exist.

15 input files (with the corresponding output) were used (1 required test case and 14 extra cases that I created):
•	all_order_1_order_input.txt – Contains matrices only of order 1
•	all_order_6_matrix_input.txt – Contains matrices only of order 6
•	blank_input.txt – A blank input text file
•	extreme_input_10000.txt – Contains 10,000 matrices
•	extreme_input_100000.txt – Contains 100,000 matrices
•	negative_order_input.txt – Contains matrices with erroneous negative orders
•	non_int_values_input.txt – Contains matrices with erroneous non-integer values
•	non_integer_order_input.txt – Contains matrices with erroneous non-integer orders
•	non_single_digit_order_input.txt – Contains matrices with erroneous non-single digit orders
•	not_enough_values_input.txt – Contains matrices with not enough values given the order
•	random_errors_input.txt – Contains matrices with random errors
•	random_input.txt – Contains random matrices
•	required_input.txt – Contains the 8 required matrices
•	too_many_values_input.txt – Contains matrices with too many values given the order number
•	toolarge_order_input.txt – Contains erroneous matrix orders that are too large