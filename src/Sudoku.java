import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sudoku {

	public static int N;
	public static Map<Integer,ArrayList<Integer>> rowMap = new HashMap<Integer,ArrayList<Integer>>();
	public static Map<Integer,ArrayList<Integer>> colMap = new HashMap<Integer,ArrayList<Integer>>();
	public static Map<Integer,ArrayList<Integer>> blockMap = new HashMap<Integer,ArrayList<Integer>>();

	public boolean sudokuUtils(int[][] matrix){
		int row = 0 ,col = 0 ;
		boolean flag = true;
		outer : for(row=0;row<N;row++){
			for(col=0;col<N;col++){
				if(matrix[row][col] == 0){
					flag=false;
					break outer;
				}
			}
		}

		if(flag)
			return true;

		for(int i=1;i<=9;i++){
			int block = getBlockNumber(row,col);
			if(isSafe(matrix,row,col,i,block)){
				matrix[row][col] = i;

				if(rowMap.get(row)!=null){
					ArrayList<Integer> rowList = rowMap.get(row);
					rowList.add(i);
					rowMap.put(row, rowList);
				}else{
					ArrayList<Integer> rowList = new ArrayList<>();
					rowList.add(i);
					rowMap.put(row, rowList);
				}

				if(colMap.get(col)!=null){
					ArrayList<Integer> colList = colMap.get(col);
					colList.add(i);
					colMap.put(col, colList);
				}else{
					ArrayList<Integer> colList = new ArrayList<>();
					colList.add(i);
					colMap.put(col, colList);
				}

				if(blockMap.get(block)!=null){
					ArrayList<Integer> blockList = blockMap.get(block);
					blockList.add(i);
					blockMap.put(block, blockList);
				}else{
					ArrayList<Integer> blockList = new ArrayList<>();
					blockList.add(i);
					blockMap.put(block, blockList);
				}

				if(sudokuUtils(matrix))
					return true;
				matrix[row][col] = 0;
				ArrayList<Integer> rowList = rowMap.get(row);
				rowList.remove(new Integer(i));
				rowMap.put(row, rowList);
				ArrayList<Integer> colList = colMap.get(col);
				colList.remove(new Integer(i));
				colMap.put(col, colList);
				ArrayList<Integer> blockList = blockMap.get(block);
				blockList.remove(new Integer(i));
				blockMap.put(block, blockList);
			}
		}
		return false;
	}

	public boolean checkComplete(Integer[][] matrix, int row, int col){
		for(row=0;row<N;row++){
			for(col=0;col<N;col++){
				if(matrix[row][col] == 0){
					return false;
				}
			}
		}
		return true;
	}

	public boolean checkRowSafe(int row,int num){
		if(rowMap.get(row)!=null && rowMap.get(row).contains(num)){
			return false;
		}
		return true;
	}

	public boolean checkColSafe(int col,int num){
		if(colMap.get(col)!=null && colMap.get(col).contains(num)){
			return false;
		}
		return true;
	}

	public int getBlockNumber(int row,int col){
		if(row/3==0){
			if(col/3==0)
				return 0;
			else if(col/3==1)
				return 1;
			else
				return 2;
		}
		else if(row/3==1){
			if(col/3==0)
				return 3;
			else if(col/3==1)
				return 4;
			else
				return 5;
		}
		else{
			if(col/3==0)
				return 6;
			else if(col/3==1)
				return 7;
			else
				return 8;
		}
	}

	public boolean checkBlockSafe(int row,int col,int num, int block){
		if(blockMap.get(block)!=null && blockMap.get(block).contains(num)){
			return false;
		}
		return true;
	}

	public boolean isSafe(int[][] matrix,int row,int col,int num,int block){
		if(!checkRowSafe(row, num) || !checkColSafe(col, num) || !checkBlockSafe(row, col, num,block)){
			return false;
		}
		return true;
	}

	public void populateMaps(int[][] matrix){
		for(int row=0;row<N;row++){
			for(int col=0;col<N;col++){
				int i = matrix[row][col];
				int block = getBlockNumber(row, col);
				if(matrix[row][col]!=0){
					if(rowMap.get(row)!=null){
						ArrayList<Integer> rowList = rowMap.get(row);
						rowList.add(i);
						rowMap.put(row, rowList);
					}else{
						ArrayList<Integer> rowList = new ArrayList<>();
						rowList.add(i);
						rowMap.put(row, rowList);
					}

					if(colMap.get(col)!=null){
						ArrayList<Integer> colList = colMap.get(col);
						colList.add(i);
						colMap.put(col, colList);
					}else{
						ArrayList<Integer> colList = new ArrayList<>();
						colList.add(i);
						colMap.put(col, colList);
					}

					if(blockMap.get(block)!=null){
						ArrayList<Integer> blockList = blockMap.get(block);
						blockList.add(i);
						blockMap.put(block, blockList);
					}else{
						ArrayList<Integer> blockList = new ArrayList<>();
						blockList.add(i);
						blockMap.put(block, blockList);
					}
				}
			}
		}
	}

	public void printSudoku(int[][] matrix){
		for(int row=0;row<N;row++){
			for(int col=0;col<N;col++){
				System.out.print(matrix[row][col]);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Sudoku sudoku = new Sudoku();
		int[][] grid = {{3, 0, 6, 5, 0, 8, 4, 0, 0},
				{5, 2, 0, 0, 0, 0, 0, 0, 0},
				{0, 8, 7, 0, 0, 0, 0, 3, 1},
				{0, 0, 3, 0, 1, 0, 0, 8, 0},
				{9, 0, 0, 8, 6, 3, 0, 0, 5},
				{0, 5, 0, 0, 9, 0, 6, 0, 0},
				{1, 3, 0, 0, 0, 0, 2, 5, 0},
				{0, 0, 0, 0, 0, 0, 0, 7, 4},
				{0, 0, 5, 2, 0, 6, 3, 0, 0}};
		N=9;
		sudoku.populateMaps(grid);
		boolean value = sudoku.sudokuUtils(grid);
		System.out.println(value);
		sudoku.printSudoku(grid);
	}

}
