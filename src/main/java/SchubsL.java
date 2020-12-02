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


 public class SchubsL
 {
	private static final int R = 256;
	private static final int L = 4096;
	private static final int W = 12;
	
	public static void compress(BinaryIn in, BinaryOut out) {
		String input = in.readString();
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
        out.write(R, W);
        out.close();
    } 

    public static void main(String[] args) {
		BinaryIn in = null;
		BinaryOut out = null;
		File currentFile;
		
		for(int i = 0; i < args.length; i++)
		{
			try{
			currentFile = new File(args[i]);
			
			if(!currentFile.exists() || !currentFile.isFile())
				System.out.println(args[i] + " could not be compressed because is not a file.");
			else if(currentFile.length() == 0)
				System.out.println(args[i] + " could not be compressed because it is empty.");
			else
			{
				in = new BinaryIn(args[i]);
				out = new BinaryOut(args[i] + ".ll");
				compress(in, out);
			}
			} finally
			{
				if(out != null)
					out.close();
			}
		}
    }

}
