import java.util.Arrays;
import java.util.Random;

public class WordGrid {
	/**
	 * grid, solvedGrid, and answerGrid are all i x j arrays of strings
	 * grid is the word search being worked on
	 * solvedGrid is the solved word search
	 * answerGrid is the answer key, and is compared to solvedGrid for correctness
	 * i and j are integers
	 */
	private String[][] grid;
	private String[][] solvedGrid;
	private String[][] answerGrid;
	private int i;
	private int j;
	
	/**
	 * 
	 * @param toFind (list of words) 
	 * @return length of longest word
	 */
	private int maxLen(String[] toFind){
		int m = 0;
		for (int s = 0; s < toFind.length; ++s){
			if (toFind[s].length() > m){
				m = toFind[s].length();
			}
		}
		return m;
	}
	
	/**
	 * WordGrid constructor
	 * @param toFind
	 */
	public WordGrid(String[] toFind){
		int m = maxLen(toFind);
		
		/**
		 * Create an i x j word search, with i and j calculated by the pythagorean
		 * theorem (i, j = m*sqrt(2)) 
		 */
//		this.i = (int) ((int) Math.sqrt(Math.pow(2*m, 2)));
//		this.j = (int) ((int) Math.sqrt(Math.pow(2*m, 2)));
		
		/**
		 * Create an i x j word search as above, but this time multiply by a
		 * number between 1 and 2 (so that the search is not perfectly square)
		 */
		this.i = (int) ((int) Math.sqrt(Math.pow(2*m, 2))*(1+Math.random()));
		this.j = (int) ((int) Math.sqrt(Math.pow(2*m, 2))*(1+Math.random()));

		this.grid = new String[i][j];
		this.solvedGrid = new String[i][j];
		this.answerGrid = new String[i][j];
		for (int x = 0; x < this.i; x++){
			for (int y = 0; y < this.j; y++){
				this.grid[x][y] = " ";
				this.solvedGrid[x][y] = " ";
			}
		}
		for (int s = 0; s < toFind.length; s++){
			insertIntoGrid(toFind[s]);
		}
		for (int y = 0; y < this.j; y++){
			for (int x = 0; x < this.i; x++){
				this.answerGrid[x][y] = this.grid[x][y];
			}
		}
		
		obfuscateGrid();
	}
	
	/**
	 * Obfuscate the word search by adding random letters
	 */
	private void obfuscateGrid(){
		Random r = new Random();
		for (int y = 0; y < this.j; y++){
			for (int x = 0; x < this.i; x++){
				if (this.grid[x][y] == " "){
					char l = (char)(r.nextInt(26) + 'a');
					this.grid[x][y] = Character.toString(l);
				}
			}
		}
	}
	
	/**
	 * Insert String s into the word search
	 * Calls the fill function, with dx/dy values based on pseudorandom selection
	 * 0 -> fill horizontal
	 * 1 -> fill vertical
	 * 2 -> fill diagonal
	 * @param s
	 */
	private void insertIntoGrid(String s){
		Random r = new Random();
		int n = r.nextInt(3);
		switch (n){
		case 0: fill(s, 1, 0);
			break;
		case 1: fill(s, 0, 1);
			break;
		default: fill(s, 1, 1);
			break;
		}
	}
	
	/**
	 * 
	 * @return this.i
	 */
	public int getI(){
		return this.i;
	}
	
	/**
	 * 
	 * @return this.j
	 */
	public int getJ(){
		return this.j;
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @return string at this.grid[i][j]
	 */
	public String getAtIJ(int i, int j){
		return this.grid[i][j];
	}
	
	/**
	 * 
	 * @return formatted word search
	 */
	public String gridToString(){
		String strGrid = "";
		for (int y = 0; y < this.j; y++){
			for (int x = 0; x < this.i; x++){
				String letter = this.grid[x][y];
				strGrid += "[" + letter + "]";
			}
			strGrid += "\n";
		}
		return strGrid;
	}
	
	/**
	 * 
	 * @return formatted solved grid
	 */
	public String solvedToString(){
		String strGrid = "";
		for (int y = 0; y < this.j; y++){
			for (int x = 0; x < this.i; x++){
				String letter = this.solvedGrid[x][y];
				strGrid += "[" + letter + "]";
			}
			strGrid += "\n";
		}
		return strGrid;
	}
	
	/**
	 * 
	 * @return formatted answer key
	 */
	public String answerToString(){
		String strGrid = "";
		for (int y = 0; y < this.j; y++){
			for (int x = 0; x < this.i; x++){
				String letter = this.answerGrid[x][y];
				strGrid += "[" + letter + "]";
			}
			strGrid += "\n";
		}
		return strGrid;
	}
	
	/**
	 * Insert solution (string array) into solvedGrid
	 * starts at solvedGrid[x][y] and then increases dx, dy, or both up to
	 * the length of sArray
	 * @param sArray
	 * @param x
	 * @param y
	 * @param dx
	 * @param dy
	 */
	public void solved(String[] sArray, int x, int y, int dx, int dy){
		for (int l = 0; l < sArray.length; ++l){
			this.solvedGrid[x+l*dx][y+l*dy] = sArray[l];
		}
	}
	
	/**
	 * Insert string into grid - used to populate word search
	 * @param s
	 * @param dx
	 * @param dy
	 */
	private void fill(String s, int dx, int dy){
		boolean canFill = false;
		int i0 = -1;
		int j0 = -1;
		int tries = 0;
		int maxTries = 1000;
		Random r = new Random();
		int l = s.length();
		int iLast = this.i - l;
		int jLast = this.j - l;
		if (dx < dy){
			iLast = this.i;
		}
		else if (dx > dy){
			jLast = this.j;
		}
		while ( (!canFill && tries < maxTries) ){
			tries++;
			i0 = r.nextInt(iLast);
			j0 = r.nextInt(jLast);
			canFill = check(s, i0, j0, dx, dy);
		}
		if (canFill == true){
			for (int d = 0; d < l; ++d){
				this.grid[i0 + d*dx][j0 + d*dy] = Character.toString(s.charAt(d));
			}
		}
	}
	
	/**
	 * Can the word in question (s) be put into the grid at the starting
	 * coordinates (i0, j0) in question, moving along the vector (dx, dy) in 
	 * question?
	 * @param s
	 * @param i0
	 * @param j0
	 * @param dx
	 * @param dy
	 * @return
	 */
	private boolean check(String s, int i0, int j0, int dx, int dy){
		boolean isOkay = true;
		int l = s.length();
		for (int n = 0; n < l; ++n){
			String c = Character.toString(s.charAt(n));
			if (this.grid[i0 + n*dx][j0 + n*dy] == " "){
				continue;
			}
			else{
				if (this.grid[i0 + n*dx][j0 + n*dy].equals(c)){
					continue;
				}
				else{
					isOkay = false;
					return isOkay;
				}
			}
		}
		return isOkay;
	}
	
	/**
	 * Checks to see that the solved grid matches the answer key
	 * @return
	 */
	public boolean isSolved(){
		return Arrays.deepEquals(this.answerGrid, this.solvedGrid);
	}
	
	public String findErrors(){
		String errors = "";
		for (int j = 0; j < this.j; ++j){
			for (int i = 0; i < this.i; ++i){
				if (!(this.solvedGrid[i][j].equals(this.answerGrid[i][j]))){
					String e = "i, j: " + i + ", " + j;
					String e2 = "\n answer: " + this.answerGrid[i][j];
					String e3 = "\n solved: " + this.solvedGrid[i][j];
					errors += e + e2 + e3;
				}
			}
		}
		return errors;
	}
}
