import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
public class KarnaughMap {

	public  NodeMatris[][] map;
	private boolean[][] controlMap;
	private String booleanExpression;
	private String[] input;
	private String simpleExpression;
	private GridPane grid;
	private Label l;
	public KarnaughMap(String bool){ //2 input
		booleanExpression = bool;
		simpleExpression = "F=";
		input = numberOfInput();
		createMap();
		fillingMap();
	}

	public KarnaughMap(int inputnumber){ 
		booleanExpression = "";
		simpleExpression = "F=";
		
		if(inputnumber == 2){

			input = new String[2];
			input[0] = "A";
			input[1] = "B";
		}
		else if(inputnumber == 3){
			input = new String[3];
			input[0] = "A";
			input[1] = "B";
			input[2] = "C";
		}
		else if(inputnumber == 4){
			input = new String[4];
			input[0] = "A";
			input[1] = "B";
			input[2] = "C";
			input[3] = "D";
		}

		createMap();

	}

	public String simplifier(){
		boolean simple = control16();
		if(!simple){


			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					simple =false;
					if((map[i][j].key && !controlMap[i][j]) || (map[i][j].key && !controlMap[i][(j+1)%(map[0].length)] && map[i][(j+1)%(map[0].length)].key) || 
							(map[i][j].key && !controlMap[(i+1)%(map.length)][j] && map[(i+1)%(map.length)][j].key)){
						//kendi 1 se ve kullanýlmadýysa veya kendi 1se saðýndaki 1 se ve saðýndaki kullanýlmadýysa veya kendi 1 altndaki 1 ve kullanýlmadýysa


						if(input.length == 4 || input.length == 3)
							simple = control8(map[i][j]);

					}
				}
			}

			for (int i = 0; i < map.length; i++) { // 4lü grup için kontrol
				for (int j = 0; j < map[0].length; j++) {
					simple =false;
					if((map[i][j].key && !controlMap[i][j]) || (map[i][j].key && !controlMap[i][(j+1)%(map[0].length)] && map[i][(j+1)%(map[0].length)].key) || 
							(map[i][j].key && !controlMap[(i+1)%(map.length)][j] && map[(i+1)%(map.length)][j].key)){
						//kendi 1 se ve kullanýlmadýysa veya kendi 1se saðýndaki 1 se ve saðýndaki kullanýlmadýysa veya kendi 1 altndaki 1 ve kullanýlmadýysa

						if(!simple){
							simple = control4(map[i][j]);
						}
					}
				}
			}
			for (int i = 0; i < map.length; i++) { // 2lü grup için kontrol
				for (int j = 0; j < map[0].length; j++) {
					simple =false;
					if((map[i][j].key && !controlMap[i][j]) || (map[i][j].key && !controlMap[i][(j+1)%(map[0].length)] && map[i][(j+1)%(map[0].length)].key) || 
							(map[i][j].key && !controlMap[(i+1)%(map.length)][j] && map[(i+1)%(map.length)][j].key)){
						//kendi 1 se ve kullanýlmadýysa veya kendi 1se saðýndaki 1 se ve saðýndaki kullanýlmadýysa veya kendi 1 altndaki 1 ve kullanýlmadýysa

						if(!simple){
							simple = control2(map[i][j]);
						}
					}
				}
			}

			for (int i = 0; i < controlMap.length; i++) {
				for (int j = 0; j < controlMap[0].length; j++) {
					if(map[i][j].key && !controlMap[i][j]){
						if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";

						NodeMatris node = map[i][j];

						String part1 = Integer.toBinaryString(node.i);
						while(part1.length() != 2) part1= "0" + part1;

						String part3 = Integer.toBinaryString(node.j);
						while(part3.length() != 2) part3= "0" + part3;


						if(part1.equals("11")){
							part1 = "10";
						}
						else if(part1.equals("10")){
							part1 = "11";
						}

						if(part3.equals("11")){
							part3 = "10";
						}
						else if(part3.equals("10")){
							part3 = "11";
						}

						part1 += part3;

						String[] inputArray = {"A","B","C","D"};
						for (int k = 0; k < part1.length(); k++) {

							if(part1.charAt(k) == '1'){
								simpleExpression+= inputArray[k];
							}
							else{
								simpleExpression+= inputArray[k] + "'";
							}

						}

					}
				}
			}





		}
		else simpleExpression += "1";

		return simpleExpression;
	}

	public boolean control2(NodeMatris node){
		boolean flag = true;
		boolean flag2 = true;

		boolean isGrouped = false;

		NodeMatris temp = node;

		///2li bulmak için kontrol yapýyor
		for (int i = 0; i < 2; i++) { // yatay 2li
			if(!temp.key)
				flag = false;
			if(controlMap[temp.i][temp.j] == false) isGrouped = true;
			temp = temp.right;
		}
		if(flag && isGrouped){ // eðer bulursa 2linin yerlerini iþaretliyor ve sadeleþtiriyor.
			temp = node;
			for (int i = 0; i < 2; i++) {
				controlMap[temp.i][temp.j] = true;
				temp = temp.right;
			}


			if(input.length == 4){
				if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";

				if(node.i == 0){
					simpleExpression+="A'B'";
				}
				else if(node.i == 1){
					simpleExpression+="A'B";
				}
				else if(node.i == 2){
					simpleExpression+="AB";
				}
				else if(node.i == 3){
					simpleExpression+="AB'";
				}

				if(node.j == 0){
					simpleExpression+="C'";
				}
				else if(node.j == 1){
					simpleExpression+="D";
				}
				else if(node.j == 2){
					simpleExpression+="C";
				}
				else if(node.j == 3){
					simpleExpression+="D'";
				}
			}
			else if(input.length == 3){
				if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";

				if(node.i == 0){
					simpleExpression+="A'";
				}
				else if(node.i == 1){
					simpleExpression+="A";
				}

				if(node.j == 0){
					simpleExpression+="B'";
				}
				else if(node.j == 1){
					simpleExpression+="C";
				}
				else if(node.j == 2){
					simpleExpression+="B";
				}
				else if(node.j == 3){
					simpleExpression+="C'";
				}

			}
			else if(input.length == 2){
				if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";

				if(node.i == 0){
					simpleExpression+="A'";
				}
				else if(node.i == 1){
					simpleExpression+="A";
				}

			}

		}
		else{ // dikey 2li sadeleþtirme
			temp = node;
			isGrouped = false;
			for (int i = 0; i < 2; i++) {// dikey
				if(!temp.key) flag2 = false;
				if(controlMap[temp.i][temp.j] == false) isGrouped = true;
				temp = temp.down;
			}

			if(flag2 && isGrouped){
				temp = node;
				for (int i = 0; i < 2; i++) {
					controlMap[temp.i][temp.j] = true;
					temp = temp.down;
				}


				if(input.length == 4){
					if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";

					if(node.i == 0){
						simpleExpression+="A'";
					}
					else if(node.i == 1){
						simpleExpression+="B";
					}
					else if(node.i == 2){
						simpleExpression+="A";
					}
					else if(node.i == 3){
						simpleExpression+="B'";
					}

					if(node.j == 0){
						simpleExpression+="C'D'";
					}
					else if(node.j == 1){
						simpleExpression+="C'D";
					}
					else if(node.j == 2){
						simpleExpression+="CD";
					}
					else if(node.j == 3){
						simpleExpression+="CD'";
					}
				}
				else if(input.length == 3){

					if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";

					if(node.j == 0){
						simpleExpression+="B'C'";
					}
					else if(node.j == 1){
						simpleExpression+="B'C";
					}
					else if(node.j == 2){
						simpleExpression+="BC";
					}
					else if(node.j == 3){
						simpleExpression+="BC'";
					}
				}
				else if(input.length == 2){
					if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";

					if(node.j == 0){
						simpleExpression+="B'";
					}
					else if(node.j == 1){
						simpleExpression+="B";
					}
				}

			}
		}

		return flag2;
	}

	public boolean control4(NodeMatris node){ //devam edicek
		boolean flag = true;
		NodeMatris temp = node;
		boolean isGrouped = false;
		for (int i = 0; i < 2; i++) { // kare kontrol
			for (int j = 0; j < 2; j++) {
				if(!temp.key) {
					flag = false;
					break;
				}
				if(controlMap[temp.i][temp.j] == false) isGrouped = true;
				temp = temp.right;
			}
			if(!flag) break;
			temp = temp.left.left;
			temp = temp.down;
		}

		boolean flag2 = true;
		boolean flag3 = true;


		if(flag && isGrouped){
			temp = node;
			for (int i = 0; i < 2; i++) { // kare kontrol
				for (int j = 0; j < 2; j++) {
					controlMap[temp.i][temp.j] = true;
					temp = temp.right;
				}
				temp = temp.left;
				temp = temp.left;
				temp = temp.down;
			}


			if(input.length == 4){
				String part1 = Integer.toBinaryString(node.i);
				while(part1.length() != 2) part1= "0" + part1;

				String part2 = Integer.toBinaryString(node.down.i);
				while(part2.length() != 2) part2= "0" + part2;

				String part3 = Integer.toBinaryString(node.j);
				while(part3.length() != 2) part3= "0" + part3;

				String part4 = Integer.toBinaryString(node.right.j);
				while(part4.length() != 2) part4= "0" + part4;

				if(part1.equals("11")){
					part1 = "10";
				}
				else if(part1.equals("10")){
					part1 = "11";
				}

				if(part2.equals("11")){
					part2 = "10";
				}
				else if(part2.equals("10")){
					part2 = "11";
				}

				if(part3.equals("11")){
					part3 = "10";
				}
				else if(part3.equals("10")){
					part3 = "11";
				}

				if(part4.equals("11")){
					part4 = "10";
				}
				else if(part4.equals("10")){
					part4 = "11";
				}

				String binary1 = "";
				String binary2 = "";

				for (int i = 0; i < part1.length(); i++) {
					if(part1.charAt(i) == part2.charAt(i)){
						binary1+=part1.charAt(i);
					}
					else{
						binary1+="-";
					}
					if(part3.charAt(i) == part4.charAt(i)){
						binary2+=part3.charAt(i);
					}
					else{
						binary2+="-";
					}
				}
				binary1 += binary2;

				String[] inputArray = {"A","B","C","D"};

				if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";

				for (int i = 0; i < inputArray.length; i++) {
					if(binary1.charAt(i) == '1'){
						simpleExpression+=inputArray[i];
					}
					else if(binary1.charAt(i) == '0'){
						simpleExpression+=inputArray[i]+"'";
					}
					else{// - için

					}
				}
			}
			else if(input.length == 3){
				if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";

				if(node.j == 0){
					simpleExpression+="B'";
				}
				else if(node.j == 1){
					simpleExpression+="C";
				}
				else if(node.j == 2){
					simpleExpression+="B";
				}
				else if(node.j == 3){
					simpleExpression+="C'";
				}

			}
			else if(input.length == 2){
				simpleExpression = "F=1";
			}
		}
		else{
			isGrouped = false;
			temp = node;
			for (int i = 0; i < 4 && !flag; i++) { // yatay kontrol
				if(!temp.key) {
					flag2 = false;
					break;
				}
				if(controlMap[temp.i][temp.j] == false) isGrouped = true;
				temp = temp.right;
			}
			if(input.length == 2) flag2 = false;
			if(flag2 && isGrouped){

				temp = node;
				for (int i = 0; i < 4; i++) { // yatay kontrol
					controlMap[temp.i][temp.j] = true;
					temp = temp.right;
				}

				if(input.length == 4){
					if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";
					if(temp.i == 0){
						simpleExpression += "A'B'" ;
					}
					else if(temp.i == 1){
						simpleExpression += "A'B" ;
					}
					else if(temp.i == 2){
						simpleExpression += "AB" ;
					}
					else if(temp.i == 3){
						simpleExpression += "AB'" ;
					}
				}
				else if(input.length == 3){
					if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";
					if(temp.i == 0){
						simpleExpression += "A'" ;
					}
					else if(temp.i == 1){
						simpleExpression += "A" ;
					}
				}

			}
			else{
				isGrouped = false;
				temp = node;
				for (int i = 0; i < 4 ; i++) { //dikey kontrol
					if(!temp.key) {
						flag3 = false;
						break;
					}
					if(controlMap[temp.i][temp.j] == false) isGrouped = true;
					temp = temp.down;
				}
				if(input.length != 4) flag3 = false;

				if(flag3 && input.length == 4 && isGrouped){
					for (int i = 0; i < 4; i++) { //dikey kontrol
						controlMap[temp.i][temp.j] = true;
						temp = temp.down;
					}

					if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";
					if(temp.j == 0){
						simpleExpression += "C'D'" ;
					}
					else if(temp.j == 1){
						simpleExpression += "C'D" ;
					}
					else if(temp.j == 2){
						simpleExpression += "CD" ;
					}
					else if(temp.j == 3){
						simpleExpression += "CD'" ;
					}
				}

			}
		}

		return flag3;

	}

	public boolean control8(NodeMatris node){
		boolean flag = true;
		NodeMatris temp = node;
		boolean isGrouped = false;
		for (int i = 0; i < map.length; i++) { //dikey
			for (int j = 0; j < 2; j++) {
				if(!temp.key){
					flag = false;
					break;
				}
				if(controlMap[temp.i][temp.j] == false) isGrouped = true;
				temp = temp.right;
			}
			if(!flag) break;
			temp = temp.left;
			temp = temp.left;
			temp = temp.down;
		}
		if(input.length == 3) flag = false;

		boolean flag2 = true;
		temp = node;
		for (int i = 0; i < 2 && !flag; i++) { // yatay
			for (int j = 0; j < map[0].length; j++) {
				if(!temp.key) {
					flag2 = false;
					break;
				}
				if(controlMap[temp.i][temp.j] == false) isGrouped = true;
				temp = temp.right;
			}
			if(!flag2) break;
			temp = temp.down;
		}

		//boolean control

		if(flag && flag2 && isGrouped){

			temp = node;

			for (int i = 0; i < map.length; i++) { //dikey
				for (int j = 0; j < 2; j++) {

					controlMap[temp.i][temp.j] = true;
					temp = temp.right;
				}
				temp = temp.left.left;
				temp = temp.down;
			}

			if(input.length == 4){
				if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";

				if(node.j == 0 && node.right.j == 1){
					simpleExpression += "C'" ;
				}
				else if(node.j == 1 && node.right.j == 2){
					simpleExpression += "D" ;
				}
				else if(node.j == 2 && node.right.j == 3){
					simpleExpression += "C" ;
				}
				else if(node.j == 3 && node.right.j == 0){
					simpleExpression += "D'" ;
				}
			}


		}
		else if(!flag && flag2 &&isGrouped){
			temp = node;
			for (int i = 0; i < 2; i++) { //yatay
				for (int j = 0; j < map[0].length; j++) {

					controlMap[temp.i][temp.j] = true;
					temp = temp.right;
				}

				temp = temp.down;
			}

			if(input.length == 4){

				if(simpleExpression.charAt(simpleExpression.length()-1) != '=') simpleExpression += "+";

				if(node.i == 0 && node.down.i == 1){
					simpleExpression += "A'" ;
				}
				else if(node.i == 1 && node.down.i == 2){
					simpleExpression += "B" ;
				}
				else if(node.i == 2 && node.down.i == 3){
					simpleExpression += "A" ;
				}
				else if(node.i == 3 && node.down.i == 0){
					simpleExpression += "B'" ;
				}

			}
			else if(input.length == 3){
				simpleExpression = "F=1";
			}


		}



		return flag2;
	}

	public boolean control16(){
		boolean flag = true;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if(!map[i][j].key){
					flag = false;
					break;
				}
			}
			if(!flag) break;
		}
		return flag;
	}

	public void fillingMap(){
		String[] binaryArray = convertingBinary();

		if(binaryArray[0].length() == 2){
			for (int i = 0; i < binaryArray.length; i++) {
				int x = Integer.parseInt(binaryArray[i].substring(0, 1));
				int y = Integer.parseInt(binaryArray[i].substring(1));
				map[x][y].key = true;

			}
		}
		else if(binaryArray[0].length() == 3){
			for (int i = 0; i < binaryArray.length; i++) {
				int x = Integer.parseInt(binaryArray[i].substring(0, 1));
				int y1 = Integer.parseInt(binaryArray[i].substring(1, 2));
				int y2 = Integer.parseInt(binaryArray[i].substring(2));
				int y = 0;
				if(y1 == 0 && y2 == 0) y = 0;
				else if(y1 == 0 && y2 == 1) y = 1;
				else if(y1 == 1 && y2 == 1) y = 2;
				else y = 3;


				map[x][y].key = true;

			}
		}
		else if(binaryArray[0].length() == 4){
			for (int i = 0; i < binaryArray.length; i++) {
				int x1 = Integer.parseInt(binaryArray[i].substring(0, 1));
				int x2 = Integer.parseInt(binaryArray[i].substring(1, 2));
				int x = 0;
				if(x1 == 0 && x2 == 0) x = 0;
				else if(x1 == 0 && x2 == 1) x = 1;
				else if(x1 == 1 && x2 == 1) x = 2;
				else x = 3;

				int y1 = Integer.parseInt(binaryArray[i].substring(2, 3));
				int y2 = Integer.parseInt(binaryArray[i].substring(3));    /*** why didnt we see the error before***/
				int y = 0;
				if(y1 == 0 && y2 == 0) y = 0;
				else if(y1 == 0 && y2 == 1) y = 1;
				else if(y1 == 1 && y2 == 1) y = 2;
				else y = 3;


				map[x][y].key = true;

			}
		}

	}


	public String[] convertingBinary(){

		String[] tempArray = booleanExpression.split("\\+");
		String[] binaryArray = new String[tempArray.length];


		for (int i = 0; i < tempArray.length; i++) { // all expressions are converted to binary and sorted 



			String tempBinary = "";
			boolean flag = false;
			for (int j = 0; j < tempArray[i].length(); j++) {
				if((int)tempArray[i].charAt(j) == 39 && flag){
					tempBinary+= 0;
					flag = false;
				}
				else if(flag){
					tempBinary+=1;
					flag=true;

					if(j == tempArray[i].length() -1){
						tempBinary +=1;

					}
				}
				else{
					flag = true;
					if(j == tempArray[i].length() -1){
						tempBinary +=1;

					}
				}

			}

			while(tempBinary.length() != input.length) tempBinary = "0" + tempBinary;
			binaryArray[i] = tempBinary;
		}


		return binaryArray;

	}

	public String[] numberOfInput(){
		String[] temp = booleanExpression.split("\\+");


		int count = 0;
		for (int i = 0; i < temp[0].length(); i++) {
			if((int)temp[0].charAt(i) != 39){ // it is not equal to '
				count++;
			}
		}

		String[] inputA = new String[count];
		int index = 0;
		for (int i = 0; i < temp[0].length(); i++) {
			if((int)temp[0].charAt(i) != 39){ // it is not equal to '
				inputA[index] = temp[0].substring(i, i+1);
				index++;
			}
		}

		return inputA;
	}

	public void createMap(){

		if(input.length == 2){
			map = new NodeMatris[2][2];
			controlMap = new boolean[2][2];

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					NodeMatris newnode = new NodeMatris();
					map[i][j] = newnode;
				}
			}

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					NodeMatris newnode = map[i][j];
					newnode.i = i;
					newnode.j = j;
					newnode.right = map[i][(j+1)%2];
					newnode.left = map[i][((j+1)%2)];
					newnode.up = map[(i+1)%2][j];
					newnode.down = map[(i+1)%2][j];
				}
			}

		}
		else if(input.length == 3){
			map = new NodeMatris[2][4];
			controlMap = new boolean[2][4];

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					NodeMatris newnode = new NodeMatris();
					map[i][j] = newnode;
				}
			}

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					NodeMatris newnode = map[i][j];
					newnode.i = i;
					newnode.j = j;
					newnode.right = map[i][(j+1)%4];
					newnode.left = map[i][((j+3)%4)];
					newnode.up = map[(i+1)%2][j];
					newnode.down = map[(i+1)%2][j];
				}
			}


		}
		else if(input.length == 4 ){
			map = new NodeMatris[4][4];
			controlMap = new boolean[4][4];

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					NodeMatris newnode = new NodeMatris();
					map[i][j] = newnode;
				}
			}

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					NodeMatris newnode = map[i][j];
					newnode.i = i;
					newnode.j = j;
					newnode.right = map[i][(j+1)%4];
					newnode.left = map[i][((j+3)%4)];
					newnode.up = map[(i+3)%4][j];
					newnode.down = map[(i+1)%4][j];
				}
			}


		}


	}


	public GridPane getGrid(GridPane gridPaneKM) 
	{
		grid = gridPaneKM;
		grid.getChildren().clear();

		if(input.length==2)
		{
			l = new Label(input[0]+"\\" + input[1]);
			l.setPrefWidth(40);
			l.setPrefHeight(40);
			l.setAlignment(Pos.CENTER);
			grid.add(l, 0, 0);
		}
		else if(input.length==3)
		{
			l = new Label(input[0]+"\\" + input[1]+ input[2]);
			l.setPrefWidth(40);
			l.setPrefHeight(40);
			l.setAlignment(Pos.CENTER);
			grid.add(l, 0, 0);
		}
		else
		{
			l = new Label(input[0]+ input[1]+"\\" + input[2]+ input[3]);
			l.setPrefWidth(40);
			l.setPrefHeight(40);
			l.setAlignment(Pos.CENTER);
			grid.add(l, 0, 0);
		}

		int rowLimit =0;
		int colLimit=0;
		if(input.length==3)
		{
			colLimit =4;
			rowLimit=2;
		}
		else rowLimit = colLimit = input.length;
		int k = 0;

		while(k!=colLimit)   //for columns
		{
			int initialK =k;
			if(k==2)
				k=3;
			else if(k==3)
				k=2;
			String b = Integer.toBinaryString(k);
			if(map[0].length>2)
			{
				if(b.length()==1)
					b = "0"+b;
			}
			l = new Label(b);
			l.setPrefWidth(40);
			l.setPrefHeight(40);
			l.setAlignment(Pos.CENTER);
			grid.add(l, initialK+1, 0);
			k = initialK;
			k++;
		}

		k=0;
		while(k!=rowLimit)   //for rows
		{
			int initialK =k;
			if(k==2)
				k=3;
			else if(k==3)
				k=2;
			String b = Integer.toBinaryString(k);
			if(map.length>2)
			{
				if(b.length()==1)
					b = "0"+b;
			}
			l = new Label(b);
			l.setPrefWidth(40);
			l.setPrefHeight(40);
			l.setAlignment(Pos.CENTER);
			grid.add(l, 0, initialK+1);
			k = initialK;
			k++;
		}
		for (int i = 0; i < map.length; i++) 
		{
			for (int j = 0; j < map[0].length; j++) 
			{
				if(map[i][j].key)
				{
					l = new Label("  1   ");
					//	l.setTextFill(Color.WHITE);
					l.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,Insets.EMPTY)));
				}

				else l = new Label("  0   ");
				l.setPrefWidth(40);
				l.setPrefHeight(40);
				l.setAlignment(Pos.CENTER);

				l.setStyle("-fx-border-color: black;");
				grid.add(l, j+1, i+1);


			}

		}

		grid.setStyle("-fx-background-color: transparent;");

		grid.setGridLinesVisible(false);

		return grid;
	}

	public GridPane getGridInp(GridPane grid)
	{
		return grid;

	}

	

	public GridPane initialize(GridPane grid)
	{


		grid.getChildren().clear();

		if(input.length==2)
		{
			l = new Label(input[0]+"\\" + input[1]);
			l.setPrefWidth(40);
			l.setPrefHeight(40);
			l.setAlignment(Pos.CENTER);
			grid.add(l, 0, 0);
		}
		else if(input.length==3)
		{
			l = new Label(input[0]+"\\" + input[1]+ input[2]);
			l.setPrefWidth(40);
			l.setPrefHeight(40);
			l.setAlignment(Pos.CENTER);
			grid.add(l, 0, 0);
		}
		else
		{
			l = new Label(input[0]+ input[1]+"\\" + input[2]+ input[3]);
			l.setPrefWidth(40);
			l.setPrefHeight(40);
			l.setAlignment(Pos.CENTER);
			grid.add(l, 0, 0);
		}

		int rowLimit =0;
		int colLimit=0;
		if(input.length==3)
		{
			colLimit =4;
			rowLimit=2;
		}
		else rowLimit = colLimit = input.length;
		int k = 0;

		while(k!=colLimit)   //for columns
		{
			int initialK =k;
			if(k==2)
				k=3;
			else if(k==3)
				k=2;
			String b = Integer.toBinaryString(k);
			if(colLimit>2)
			{
				if(b.length()==1)
					b = "0"+b;
			}
			l = new Label(b);
			l.setPrefWidth(40);
			l.setPrefHeight(40);
			l.setAlignment(Pos.CENTER);
			grid.add(l, initialK+1, 0);
			k = initialK;
			k++;
		}

		k=0;
		while(k!=rowLimit)   //for rows
		{
			int initialK =k;
			if(k==2)
				k=3;
			else if(k==3)
				k=2;
			String b = Integer.toBinaryString(k);
			if(rowLimit>2)
			{
				if(b.length()==1)
					b = "0"+b;
			}
			l = new Label(b);
			l.setPrefWidth(40);
			l.setPrefHeight(40);
			l.setAlignment(Pos.CENTER);
			grid.add(l, 0, initialK+1);
			k = initialK;
			k++;
		}
		return grid;

	}
/*	public void convertBoolean(){
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if(map[i][j].key){
					
				}
			}
		}
		
		
	}*/
	
	public String convertToBoolean()
	{
		String exp = "";
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if(map[i][j].key){ // if cell is 1
					int x = i;
					int y = j;
					
					if(x == 2) x = 3;
					else if(x == 3) x = 2;
					if(y == 2) y = 3;
					else if(y == 3) y = 2;
					
					String temp1 = Integer.toBinaryString(x);
					String temp2 = Integer.toBinaryString(y);
					
					
					if(input.length==4)
						while(temp1.length() != 2) temp1 = "0" + temp1;
					if(input.length==3 || input.length==4)
						while(temp2.length() != 2) temp2 = "0" + temp2;
					
					temp1 = temp1+temp2; // binary hali
					
					String temp = "";
					for (int k = 0; k < temp1.length(); k++) {
						if(temp1.charAt(k) == '1'){
							temp += input[k];
						}
						else{
							temp += input[k] + "'";
						}
					}
					
					if(!exp.equals("")) exp += "+";
					exp += temp;
					
				}
			}
		}
		exp = "F="+exp;
	//	booleanExpression = exp;
		return exp;
	}

	public void setBooleanExpression(String booleanExpression) {
		this.booleanExpression = booleanExpression;
	}

	
}
