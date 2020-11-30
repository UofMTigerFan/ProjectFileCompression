/*  Program: ProjectFileCompressionTest
 *  Author : Wade Linder
 *  Date   : 11/06/20
 *  Course : CS375 Software Engineering II
 *  Compile: mvn compile
 *  Execute: mvn test
 *  Note   : Tests all parts of file compression project
 */

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.util.*;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ProjectFileCompressionTest {
	public Boolean fileExists(String file)
	{
		File f = new File(file);
		return f.exists();
	}
	
	public void fileDelete(String file)
	{
		File f = new File(file);
		f.delete();
	}
	
	@Test	//Normal case Huffman
	public void normalH() throws IOException
	{
		String file = "src" + File.separator + "files" + File.separator + "input.txt";
		String fileAnswer = "src" + File.separator + "files" + File.separator + "input.txt.hh";
		
		if(fileExists(fileAnswer))
			fileDelete(fileAnswer);
		
		String[] args = {file};
		SchubsH.main(args);
		
		assert(fileExists(fileAnswer));
	}
	
	@Test	//Normal case LZW
	public void normalL() throws IOException
	{
		String file = "src" + File.separator + "files" + File.separator + "input.txt";
		String fileAnswer = "src" + File.separator + "files" + File.separator + "input.txt.ll";
		
		if(fileExists(fileAnswer))
			fileDelete(fileAnswer);
		
		String[] args = {file};
		SchubsL.main(args);
		
		assert(fileExists(fileAnswer));
	}
	
	@Test //File does not exists Huffman
	public void DNEH() throws IOException
	{
		String file = "src" + File.separator + "files" + File.separator + "fileDNE.txt";
		String[] args = {file};
		SchubsH.main(args);
	}
	
	@Test //File does not exist LZW
	public void DNEL() throws IOException
	{
		String file = "src" + File.separator + "files" + File.separator + "fileDNE.txt";
		String[] args = {file};
		SchubsL.main(args);
	}
	
	@Test
	public void isDirH() throws IOException
	{
		String file = "src" + File.separator + "files";
		String[] args = {file};
		SchubsH.main(args);
	}
	
	@Test
	public void isDirL() throws IOException
	{
		String file = "src" + File.separator + "files";
		String[] args = {file};
		SchubsL.main(args);
	}
	
	@Test
	public void isEmptyH() throws IOException
	{
		String file = "src" + File.separator + "files" + File.separator + "empty.txt";
		String[] args = {file};
		SchubsH.main(args);
	}
	
	@Test
	public void isEmptyL() throws IOException
	{
		String file = "src" + File.separator + "files" + File.separator + "empty.txt";
		String[] args = {file};
		SchubsL.main(args);
	}
}
