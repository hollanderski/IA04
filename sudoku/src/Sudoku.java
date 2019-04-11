import jade.core.Agent;

public class Sudoku extends Agent {

	private Cell[][] sudoku  = {
									{new Cell(5), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(4), new Cell(0), new Cell(0), new Cell(8)},
									{new Cell(0), new Cell(1), new Cell(0), new Cell(9), new Cell(0), new Cell(7), new Cell(0), new Cell(0), new Cell(0)},
									{new Cell(0), new Cell(9), new Cell(2), new Cell(8), new Cell(5), new Cell(0), new Cell(7), new Cell(0), new Cell(6)},
									{new Cell(7), new Cell(0), new Cell(0), new Cell(3), new Cell(0), new Cell(1), new Cell(0), new Cell(0), new Cell(4)},
									{new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
									{new Cell(6), new Cell(0), new Cell(0), new Cell(2), new Cell(0), new Cell(8), new Cell(0), new Cell(0), new Cell(1)},
									{new Cell(1), new Cell(0), new Cell(8), new Cell(0), new Cell(3), new Cell(2), new Cell(4), new Cell(9), new Cell(0)},
									{new Cell(0), new Cell(0), new Cell(0), new Cell(1), new Cell(0), new Cell(6), new Cell(0), new Cell(5), new Cell(0)},
									{new Cell(3), new Cell(0), new Cell(0), new Cell(7), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(2)}
								};	
	
	public Cell[] getLine(int line) {
		return sudoku[line];
	}
	
	public Cell[] getColumn(int column) {
		Cell col[] = new Cell[9];
			for (int i = 0; i < 9; i++)
				col[i] = sudoku[i][column];
		return col;
	}
	
	public Cell[] getSquare(int square) {
		Cell col[] = new Cell[9];
		int squareLine = square/3;
		int squareColumn = square%3;
		int k = 0;
		for (int i = 3*squareLine; i < 3*squareLine+3; i++)
			for (int j = 3*squareColumn; j < 3*squareColumn+3; j++)
				col[k++] = sudoku[i][j];
		return col;
	}
	
	public void setup(){
		addBehaviour(new WaitForNotificationBehaviour());
	}

	public void updateLine(int i, Cell[] cells) {
		for (int j=0; j<cells.length; j++)
			sudoku[i][j].update(cells[j]);
	}
	
	public void updateColumn(int i, Cell[] cells) {
		for (int j=0; j<cells.length; j++)
			sudoku[j][i].update(cells[j]);
	}
	
	public void updateSquare(int i, Cell[] cells) {
		for (int j=i%3; j<i%3+3; j++)
			for (int k=3*(i%3); k<3*(i%3)+3; k++)
				sudoku[j][k].update(cells[j]);
	}

	public void displaySudoku() {
		String str = new String();
		for (Cell[] line : sudoku) {
			for (Cell c : line) {
				str+= Integer.toString(c.valueIfExists()) + " ";
			}
			str +="\n";
		}
		str+="\n";
		System.out.println(str);
	}
}
