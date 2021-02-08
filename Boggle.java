/*
 * Author: Owen Corkery
*/

import java.util.*;
import java.io.*;


/*
 This class creates a Boggle board with random letters in each space and
should go through each letter and find every possibility of a word that can be made.
The letters have to be connected to each other in some way and the same letter can not
be used twice. We use recursive backtracking to go through every possible way a word can
be made. 
*/
public class Boggle
{
	/*
		* The board object and boolean for marked are declared here along with the
		dictionary.
	*/
	private char[][] board = new char[4][4];
	private boolean[][] marked = new boolean[4][4];
	private static Set<String> dict = new HashSet<String>();

	static{
		try{
			Scanner scanner = new Scanner(new File("/usr/share/dict/words"));
			while(scanner.hasNextLine())
			{dict.add(scanner.nextLine().toUpperCase());}
		}
		catch(IOException e)
		{System.out.println("Dictionary file not found.");}
	}
	
	/*
		* Zero Parameter constructor
		* Sets up the board by randomly getting a new letter for each space in the board.
	*/
	public Boggle()
	{
		
		Random ran = new Random();
		for(int row = 0; row < 4; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				int rand = ran.nextInt(26);
				char ch = (char)(65 + rand);
				board[row][col] = ch;
			}
		}		
	}
	/*
		* Single parameter constructor
		* Uses the parameter arr, which is an array containing letters, to fill the board
		up with those letters.
	*/
	public Boggle(char[] arr) throws IllegalArgumentException
	{
		if(arr.length != 16)
		{
			throw new IllegalArgumentException();
		}
		int index = 0;
		for(int row = 0; row < 4; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				board[row][col] = arr[index];
				index++;
			}
		}
	}
	
	/*
		* This method loops through every possible word that can be made starting from each
		letter on the board. If a word is made, it is added to an array list containing
		all possible words.
	*/
	public void explore(int row, int col, String s, ArrayList<String> coll)
	{
		if(dict.contains(s))
			{coll.add(s);}
		
		for(int i = row - 1; i <= row + 1; i++)
		{
			for(int j = col - 1; j <= col + 1; j++)
			{
				if(i >= 0 && i <= 3 && j >= 0 && j <= 3 && this.marked[i][j] == false)
				{
					this.marked[i][j] = true;
					this.explore(i,j,s+this.board[i][j], coll);
					this.marked[i][j] = false;
				}
			}		
		}
	}

// Sets the given index to true and the rest to false
	private void setMarked(int row, int col)
	{
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				this.marked[i][j] = false;
			}
		}

		this.marked[row][col] = true;
	}




	/*
		* This method creates the dictionary using an array list containing valid words, 
		and calls the solve method for every letter in the board so that we can see all
		possible words for each letter. This returns an array list containing all possible
		words.
	*/ 
	public ArrayList<String> solve()
	{

		ArrayList<String> coll = new ArrayList<String>();
		for(int row = 0; row < 4; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				this.setMarked(row, col);
				this.explore(row, col, String.valueOf(this.board[row][col]), coll);
			}
		}
		
		return coll;
	}
	/*
		* This method prints a String representing how to board looks.
	*/
	public void print()
	{
		for(int row = 0; row < 4; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				System.out.print(board[row][col]+ "  ");
			}
			System.out.print("\n");
		}
	}
	
	public static void main(String[] args)
	{
		char[] array = new char[]{'T', 'I', 'S', 'S', 'D', 'E', 'T', 'I', 'T', 'A', 'T', 'W', 'R', 'S', 'P', 'H'};
		Boggle b = new Boggle(array);
		b.print();
		List<String> words = b.solve();
		System.out.println(words.size() + " possible words that can be made from the board.");
		
		Boggle b1 = new Boggle();
		b1.print();
		words = b1.solve();
		System.out.println(words.size() + " possible words that can be made from the board.");
	}
}