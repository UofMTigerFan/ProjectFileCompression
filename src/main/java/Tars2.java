/*  Program: Tars2
 *  Author : Wade Linder
 *  Date   : Fall 2020
 *  Course : CS375 Software Engineering II
 *  Compile: javac Tars2.java
 *  Execute: java Tars2 archive-name file1 file2
 */

import java.io.IOException;
import java.io.File;

public class Tars2
{
    static void tarit (String filename, String outName, BinaryOut out)
	{
		char separator = (char) 255; //All ones 11111111
		
		try {
			File in1 = new File(filename);
			if (!in1.exists() || !in1.isFile()) return;
			long filesize = in1.length();
			int filenamesize = filename.length();
			
			out.write(filenamesize);
			out.write(separator);
			
			out.write(filename);
			out.write(separator);
			
			out.write(filesize);
			out.write(separator);
			
			System.out.println("archive " + outName);
			System.out.println("adding file " + filename + " (" + filesize + ")");
			BinaryIn bin1 = new BinaryIn(filename);
			while(! bin1.isEmpty() ) {
				out.write( bin1.readChar( ));
			}
		}	catch( RuntimeException ex ){
				System.out.println(ex.toString());
				System.out.println("File not found " + filename);
			}
	}
	
	public static void main(String[] args) {	//Expect three arguments, archive, file1, file2
		BinaryOut out = new BinaryOut(args[0]);
		try {
			tarit( args[1], args[0], out);
			tarit( args[2], args[0], out);
		}	finally {
			if(out != null)
				out.close();
		}
	}

}

