/*  Program: SchubsL
 *  Author : Wade Linder
 *  Date   : 11/06/20
 *  Course : CS375 Software Engineering II
 *  Compile: javac SchubsL.java
 *  Execute: java SchubsL - < file-name
 *  Note   : Extract files from an archive
 */
 
 import java.io.File;
 import java.io.IOException;
 import org.apache.commons.io.FilenameUtils;


 public class SchubsL
 {
	private static final int R = 256;
	private static final int L = 4096;
	private static final int W = 12;
	
	public static void compress() {
		String input = BinaryStdIn.readString();
		TST<Integer> st = new TST<Integer>();
		for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);  
            BinaryStdOut.write(st.get(s), W);      
            int t = s.length();
            if (t < input.length() && code < L)    
                st.put(input.substring(0, t + 1), code++);
            input = input.substring(t);           
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    } 

    public static void main(String[] args) {
		String filename = args[2] + ".ll";
		try {
			File myObj = new File(filename);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
		System.out.println("An error occurred.");
		e.printStackTrace();
		}
		if      (args[0].equals("-")) compress();
        else throw new RuntimeException("Illegal command line argument.");
    }

}
