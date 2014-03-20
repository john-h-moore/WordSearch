import java.io.*;
import java.util.ArrayList;


public class WordSearch {
	
	public static void main(String[] args) throws IOException{
		String filename = "src/sample.txt";
		String[] toFind = wordList(filename);
		WordGrid wg = new WordGrid(toFind);
		solve(toFind, wg);
		System.out.println("Word Search Grid");
		System.out.println(wg.gridToString());
		System.out.println("\nSolved Word Search Grid");
		System.out.println(wg.solvedToString());
		System.out.println("\nAnswer Key");
		System.out.println(wg.answerToString());
		System.out.println("The word search was solved correctly: " + wg.isSolved());
		if (!wg.isSolved()){
			System.out.println(wg.findErrors());
		}
	}
	
	/**
	 * Convert file into word list, to be used in building the word search
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static String[] wordList(String filename) throws IOException{
		ArrayList<String> wordArrayList = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = in.readLine()) != null){
			wordArrayList.add(line);
		}
		in.close();
		String[] wordList = new String[wordArrayList.size()];
		for (int i = 0; i < wordList.length; ++i){
			wordList[i] = (String) wordArrayList.get(i);
		}
		return wordList;
	}
	
	/**
	 * Solves the word search
	 * @param toFind
	 * @param wg
	 */
	public static void solve(String[] toFind, WordGrid wg){
		for(int n = 0; n < toFind.length; ++n){
			if (!(search(toFind[n], wg))){
				System.out.println("\nFailed to find " + toFind[n]);
			}
		}
	}
	
	/**
	 * Calls the three search methods - horizontal, vertical, and diagonal -
	 * for the word in question
	 * @param stringToFind
	 * @param wg
	 * @return
	 */
	public static boolean search(String stringToFind, WordGrid wg){
		boolean h = searchHorizontal(stringToFind, wg);
		boolean v = searchVertical(stringToFind, wg);
		boolean d = searchDiagonal(stringToFind, wg);
		return (h || v || d);
	}
	
	/**
	 * Search horizontally for the word
	 * @param stringToFind
	 * @param wg
	 * @return
	 */
	public static boolean searchHorizontal(String stringToFind, WordGrid wg){
		boolean isFound = false;
		String[] findMe = new String[stringToFind.length()];
		for (int l = 0; l < stringToFind.length(); ++l){
			char c = stringToFind.charAt(l);
			findMe[l] = Character.toString(c);
		}
		for (int j = 0; j < wg.getJ(); ++j){
			for (int i = 0; i < wg.getI(); ++i){
				if (wg.getAtIJ(i, j).equals(findMe[0])){
					int x = 1;
					
					while (
							(x < findMe.length)
							&& ((i+x) < wg.getI())
							&& (wg.getAtIJ(i+x, j).equals(findMe[x]))
							)
					{
						x++;
					}
					if (x == findMe.length){
						isFound = true;
						wg.solved(findMe, i, j, 1, 0);
						return isFound;
					}
					else {
						x = 1;
					}
				}
			}
		}
		return isFound;
	}
	
	/**
	 * Search vertically for the word
	 * @param stringToFind
	 * @param wg
	 * @return
	 */
	public static boolean searchVertical(String stringToFind, WordGrid wg){
		boolean isFound = false;
		String[] findMe = new String[stringToFind.length()];
		for (int l = 0; l < stringToFind.length(); ++l){
			char c = stringToFind.charAt(l);
			findMe[l] = Character.toString(c);
		}
		for (int j = 0; j < wg.getJ(); ++j){
			for (int i = 0; i < wg.getI(); ++i){
				if (wg.getAtIJ(i, j).equals(findMe[0])){
					int y = 1;
					while ( 
							(y < findMe.length) 
							&& ((j + y) < wg.getJ()) 
							&& (wg.getAtIJ(i, j+y).equals(findMe[y])) 
							)
					{
						y++;
					}
					if (y == findMe.length){
						isFound = true;
						wg.solved(findMe, i, j, 0, 1);
						return isFound;
					}
					else{
						y = 1;
					}
				}
			}
		}
		return isFound;
	}
	
	/**
	 * Search diagonally for the word
	 * @param stringToFind
	 * @param wg
	 * @return
	 */
	public static boolean searchDiagonal(String stringToFind, WordGrid wg){
		boolean isFound = false;
		String[] findMe = new String[stringToFind.length()];
		for (int l = 0; l < stringToFind.length(); ++l){
			char c = stringToFind.charAt(l);
			findMe[l] = Character.toString(c);
		}
		for (int j = 0; j < wg.getJ(); ++j){
			for (int i = 0; i < wg.getI(); ++i){
				if (wg.getAtIJ(i, j).equals(findMe[0])){
					int d = 1;
					while (
							(d < findMe.length)
							&& ((i+d) < wg.getI())
							&& ((j+d) < wg.getJ())
							&& ((wg.getAtIJ(i+d, j+d).equals(findMe[d])))
							)
					{
						d++;
					}
					if (d == findMe.length){
						isFound = true;
						wg.solved(findMe, i, j, 1, 1);
						return isFound;
					}
				}
			}
		}
		return isFound;
	}

}
