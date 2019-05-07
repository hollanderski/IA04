import java.util.ArrayList;

public class Cell {
	
	private ArrayList<Integer> possibleValues = new ArrayList<Integer>();
	// Une cellule est donn�e simultanemment � 3 agents d'analyse : sa ligne, sa colonne et son carr�
	
	public Cell() {}
	
	public Cell(int k){
		this.setPossibleValues(new ArrayList<Integer>());
		if (k != 0)
			this.possibleValues.add(k);
		else
			for (int i = 1; i<=9; i++)
				this.possibleValues.add(i);
	}

	
	public int valueIfExists() {
		if (possibleValues.size() == 1)
			return possibleValues.get(0);
		else return 0;
	}

	public void update(Cell cell) {
		possibleValues.retainAll(cell.getPossibleValues());
	}

	public void remove(ArrayList<Integer> existingValues) {
		if (getPossibleValues().size() == 1) return;
		possibleValues.removeAll(existingValues);	
	}


	public ArrayList<Integer> getPossibleValues() {
		return possibleValues;
	}


	public void setPossibleValues(ArrayList<Integer> possibleValues) {
		this.possibleValues = (ArrayList<Integer>) possibleValues.clone();
	}
}
