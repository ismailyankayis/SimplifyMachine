
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TruthTable {	
private String[] inputs;
private int[][] arr;
private GridPane grid;
private int inputCount;
public TruthTable(GridPane gridPaneTT, int[][] table,String[] inputs, int inputCount)
	{
		arr = table;
		this.inputs = inputs;
		this.inputCount = inputCount;
		grid = gridPaneTT;
		grid.getChildren().clear();
		createTable();
	}

public void createTable()
{
        Text t;
		
		int i = 0;
		while(i!=inputCount)
		{
			 t = new Text("   "+inputs[i]+"   ");
			 grid.add(t, i,0);
			i++;
		}
		t = new Text("   F   ");
	
		grid.add(t,i,0);
	
		for (i = 0; i < arr.length; i++) 
		{
			for (int j = 0; j < arr[i].length; j++) 
			{
				t = new Text(String.valueOf("   " + arr[i][j]+ "   "));
			
				grid.add(t, j,i+1);
			}
			
		}
		grid.setStyle("-fx-background-color: transparent;");
		grid.setStyle("-fx-grid-lines-visible: true");
		grid.setVgap(0);
		grid.setHgap(0);

}


public GridPane getGrid() {
	return grid;
}


}