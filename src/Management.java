import java.io.*;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.*;

public class Management extends Application{

	Stage stage = new Stage();

	public Management() throws Exception{
		start(stage);

	}

	GridPane gridPaneTT;
	GridPane gridPaneKM;
	TabPane tabpane;
	Tab fileTab;
	//FÝLE TAB ----->
	Label pathLb;
	TextField pathFld;
	TextField tTable;
	Button select;
	File file;
	String path;
	Label boolLb;
	TextField boolFld;
	Button read;
	ListView<String> list;

	//FÝLE TAB END <-----

	Tab inputTab;
	//INPUT TAB ----->
	static int inputTypeChoice = 0;
	static int inputNumberChoice = 0;
	RadioButton boolRd;
	RadioButton TTRd;
	RadioButton KMRd;
	Label inputType;
	RadioButton twoRd;
	RadioButton threeRd;
	RadioButton fourRd;
	Label inputNumber;
	Button confirm;
	TextField inputFld;
	GridPane inputKM;
	KarnaughMap karno;
	
	TableView<?> TTtable;
	TableView<?> KMtable;
	Button simplify = new Button("Simplify");
	
	static int isCreated = 0;
	//INPUT TAB END <----

	Tab outputTab;
	//OUTPUT TAB ----->

	//OUTPUT TAB END <-----

	@Override
	public void start(Stage stage) throws Exception {

		tabpane = new TabPane();
		//FÝLE TAB ----->
		inputKM = new GridPane();
		inputFld = new TextField();
		fileTab = new Tab("FILE");
		VBox fileBox = new VBox();


		list = new ListView<String>();
		pathLb = new Label("Path");
		pathLb.setFont(new Font("Arian",20));
		pathFld = new TextField();
		select = new Button("Select");
		pathFld.setMaxWidth(300);
		Image selectImg = new Image(getClass().getResourceAsStream("select.png"));
		ImageView iwSelect = new ImageView(selectImg);
		iwSelect.setFitHeight(30);
		iwSelect.setFitWidth(30);
		select.setGraphic(iwSelect);
		//select.setStyle("-fx-background-color: #FF282F");

		select.setOnAction(e-> {
			FileChooser fileChooser = new FileChooser();
			file = fileChooser.showOpenDialog(stage);
			if (file != null)
			{
				pathFld.setText(file.getAbsolutePath().toString());
				path = file.getAbsolutePath().toString(); 
			}
		});
		DropShadow shadow = new DropShadow();
		select.addEventHandler(MouseEvent.MOUSE_ENTERED, 
				new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				select.setEffect(shadow);
			}
		});
		select.addEventHandler(MouseEvent.MOUSE_EXITED, 
				new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				select.setEffect(null);
			}
		});

		boolLb = new Label("Boolean Expression");
		boolLb.setFont(new Font("Arian",20));
		boolFld = new TextField();
		read = new Button("Read");
		boolFld.setMinWidth(250);
		Image readImg = new Image(getClass().getResourceAsStream("read.png"));
		ImageView iwRead = new ImageView(readImg);
		iwRead.setFitHeight(30);
		iwRead.setFitWidth(30);
		read.setGraphic(iwRead);
		read.setOnAction(e-> {
			if(path!=null)
			{
				if(path.substring(path.length()-2).equalsIgnoreCase("be"))
				{

					FileReader fileReader=null;
					try {
						fileReader = new FileReader(path);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					BufferedReader br = new BufferedReader(fileReader);
					String line = null;
					try {
						line = br.readLine();
						boolFld.setText(line);

					} catch (IOException e1) {
						e1.printStackTrace();
					}

					BooleanExp b = new BooleanExp(line.substring(2));
					int[][] truthTableArray = b.generateTruthTable();
					String[] inpArray = b.getInputs();
					int inputCount = b.getCount();

					TruthTable t = new TruthTable(gridPaneTT, truthTableArray, inpArray,inputCount);
					gridPaneTT = t.getGrid();
					KarnaughMap km = new KarnaughMap(b.getStandartFormExpression());
					gridPaneKM = km.getGrid(gridPaneKM);
					int column = truthTableArray[0].length;
					int row = (int) Math.pow(2, column-1);
					for (int i = 0; i < row; i++) {
						String temp = "";
						for (int j = 0; j < column; j++) {
							if(j == column-2)
								temp+= truthTableArray[i][j]+";";
							else if(j == column-1)
								temp+= truthTableArray[i][j];
							else
								temp+= truthTableArray[i][j]+",";
						}
						ObservableList<String> data = FXCollections.observableArrayList(temp);
						list.getItems().addAll(data.toString());
					}


				}

				else if(path.substring(path.length()-2).equalsIgnoreCase("tt"))
				{

					FileReader fileReader=null;
					try {
						fileReader = new FileReader(path);
					} catch (FileNotFoundException e1) {

						e1.printStackTrace();
					}
					BufferedReader br = new BufferedReader(fileReader); 
					String line;
					try {

						String temp = "F=";
						int count = -1;
						while ((line = br.readLine()) != null){
							ObservableList<String> data = FXCollections.observableArrayList(line);
							list.getItems().addAll(data.toString());
							if(count != -1 && line.charAt(line.length()-1) == '1')
								temp += convertToBoolean((line.length()-1)/2, count) + "+"; 
							count++;
						}

						temp = temp.substring(0, temp.length()-1);
						boolFld.setText(temp);

						BooleanExp b = new BooleanExp(temp.substring(2));
						int[][] truthTableArray = b.generateTruthTable();
						String[] inpArray = b.getInputs();
						int inputCount = b.getCount();

						TruthTable t = new TruthTable(gridPaneTT, truthTableArray, inpArray,inputCount);
						gridPaneTT = t.getGrid();

						KarnaughMap km = new KarnaughMap(b.getStandartFormExpression());
						gridPaneKM = km.getGrid(gridPaneKM);

					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
				else
				{
					boolFld.setText("Wrong File Type!");
				}


			}
		});
		read.addEventHandler(MouseEvent.MOUSE_ENTERED, 
				new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				read.setEffect(shadow);
			}
		});
		read.addEventHandler(MouseEvent.MOUSE_EXITED, 
				new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				read.setEffect(null);
			}
		});
		Button simplifyy = new Button("Simplify");
		simplifyy.setMaxHeight(50);
		simplifyy.setMaxWidth(100);
		simplifyy.setOnAction(e-> {
			if(!boolFld.getText().equals("")){
				BooleanExp b = new BooleanExp(boolFld.getText().substring(2));
				//b.standartForm();

				Label text = new Label("Simple Expression");
				TextField txtfld = new TextField();

				KarnaughMap km = new KarnaughMap(boolFld.getText().substring(2));
				txtfld.setText(km.simplifier());


				txtfld.setMaxWidth(300);
				VBox vbox = new VBox(text,txtfld);
				vbox.setAlignment(Pos.BASELINE_CENTER);
				vbox.setSpacing(10);
				outputTab = new Tab("OUTPUT");
				outputTab.setContent(vbox);
				tabpane.getTabs().addAll(outputTab);
			}
		});

		Label truthTlb = new Label("TruthTable");  //truthTable display
		truthTlb.setFont(new Font("Arian",20));
		gridPaneTT = new GridPane();
		gridPaneTT.setPrefHeight(300);
		gridPaneTT.setPrefWidth(150);
		gridPaneTT.setStyle("-fx-background-color: silver;");
		VBox truthTBox = new VBox(truthTlb,gridPaneTT);
		truthTBox.setSpacing(10);

		VBox boolbox = new VBox(boolLb,boolFld);   //Boolean Exp display
		boolbox.setSpacing(10);

		Label kMlb = new Label("Karnaugh Map");  //KarnaughMap display
		kMlb.setFont(new Font("Arian",20));
		gridPaneKM = new GridPane();
		gridPaneKM.setPrefHeight(300);
		gridPaneKM.setPrefWidth(300);
		gridPaneKM.setStyle("-fx-background-color: silver;");
		VBox kMBox = new VBox(kMlb,gridPaneKM);
		kMBox.setSpacing(10);

		HBox hb = new HBox(truthTBox,boolbox,kMBox);
		hb.setPadding(new Insets(10,10,10,10));
		hb.setSpacing(10);
		fileBox.getChildren().addAll(pathLb,pathFld,select,read,simplifyy,hb);
		fileBox.setSpacing(10);
		fileBox.setAlignment(Pos.BASELINE_CENTER);
		fileTab.setContent(fileBox);
		fileTab.setClosable(false);


		tabpane.getTabs().addAll(fileTab);
		//FÝLE TAB END <-----

		//INPUT TAB ----->
		inputTab = new Tab("INPUT");
		HBox typeBox = new HBox();
		HBox numberBox = new HBox();
		VBox inTabBox = new VBox();

		ToggleGroup groupType = new ToggleGroup();
		boolRd = new RadioButton("Boolean Expression");
		boolRd.setToggleGroup(groupType);
		boolRd.setSelected(true);
		//	TTRd = new RadioButton("Truth Table");  we longer need it
		//	TTRd.setToggleGroup(groupType);
		KMRd = new RadioButton("Karnaugh Map");
		KMRd.setToggleGroup(groupType);
		inputType = new Label("Input Type :");
		inputType.setFont(new Font("Arian",10));

		typeBox.getChildren().addAll(inputType,boolRd,KMRd);
		typeBox.setSpacing(10);
		TitledPane typeTP = new TitledPane("Type",typeBox);
		typeTP.setCollapsible(false);

		ToggleGroup groupNumber = new ToggleGroup();

		twoRd = new RadioButton("2");
		//twoRd.setToggleGroup(groupNumber);
		twoRd.setSelected(true);
		threeRd = new RadioButton("3");
		//threeRd.setToggleGroup(groupNumber);
		fourRd = new RadioButton("4");
		//fourRd.setToggleGroup(groupNumber);
		inputNumber = new Label("Number of Inputs :");
		inputNumber.setFont(new Font("Arian",10));
		groupNumber.getToggles().addAll(twoRd,threeRd,fourRd);






		numberBox.getChildren().addAll(inputNumber,twoRd,threeRd,fourRd);
		numberBox.setSpacing(10);
		TitledPane numberTP = new TitledPane("Number",numberBox);
		numberTP.setCollapsible(false);



		confirm = new Button("Confirm");
		inTabBox.getChildren().addAll(typeTP,numberTP,confirm);
		inTabBox.setSpacing(15);
		inTabBox.setPadding(new Insets(10,10,10,10));

		Button continueBT = new Button("Continue"); 

		confirm.setOnAction(e-> {

			create();
			if(inputTypeChoice == 1 && isCreated != 1){ //Boolean 
				Label text = new Label("Boolean Expression");
				text.setFont(new Font("Arian",14));
				inTabBox.getChildren().addAll(text,inputFld);
				isCreated = 1;
			}
			else if(inputTypeChoice == 2 && isCreated != 2){ //KM

				Label text = new Label("Karnaugh Map");
				text.setFont(new Font("Arian",14));
				
				karno = new KarnaughMap(inputNumberChoice);
				inputKM.setPrefHeight(100);
				inputKM.setMaxWidth(200);
				inputKM.setStyle("-fx-background-color: silver;");
				inputKM = karno.initialize(inputKM);
				
				
				int row = 0;
				int col = 0;
				if(inputNumberChoice == 4){
					row = 4;
					col = 4;
				}
				else if(inputNumberChoice == 3){
					row = 2;
					col = 4;
					
				}
				else if(inputNumberChoice == 2){
					row = 2;
					col = 2;
				}
				
				//inputKM = new GridPane();
				for (int i = 0; i < row; i++) 
				{
					for (int j = 0; j < col; j++) 
					{
						int x = i;
						int y = j;
						
						Button btn = new Button("0");
						btn.setOnAction(new EventHandler<ActionEvent>(){
							public void handle(ActionEvent event) {
								NodeMatris temp = karno.map[x][y];
								temp.key = !temp.key;
								if(btn.getText() == "1"){
									btn.setText("0");
								}
								else{
									btn.setText("1");
									btn.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,Insets.EMPTY)));
								}
								
							}
						});
						
						btn.setPrefWidth(40);
						btn.setPrefHeight(40);
						btn.setAlignment(Pos.CENTER);

						btn.setStyle("-fx-border-color: black;");
						inputKM.add(btn, j+1, i+1);
						
					}
				}
				
			
				inTabBox.getChildren().addAll(text,inputKM);

				isCreated = 2;
			}


			inTabBox.getChildren().addAll(continueBT);

		});


		continueBT.setOnAction(e->{
			BooleanExp b = null;
			VBox vKM = null;
			VBox vBool = null;
			Label text;
			if(inputTypeChoice == 1)
			{
				b = new BooleanExp(inputFld.getText().substring(2)); 
				karno = new KarnaughMap(b.getStandartFormExpression());
				inputKM= karno.getGrid(inputKM);			
				text = new Label("Karnaugh Map");
				text.setFont(new Font("Arian",14));
				vKM = new VBox(text, inputKM);
			}
			else if(inputTypeChoice == 2)    //create BE
			{
				
				
				text = new Label("Boolean Expression");
				text.setFont(new Font("Arian",14));
				inputFld.setText(karno.convertToBoolean());
				vBool = new VBox(text,inputFld);
				b = new BooleanExp(inputFld.getText().substring(2)); 
				b.standartForm();
				karno.setBooleanExpression(b.getBoolExpression());
			}


			text = new Label("Truth Table");
			text.setFont(new Font("Arian",14));


			int[][] truthTableArray = b.generateTruthTable();
			String[] inpArray = b.getInputs();
			int inputCount = b.getCount();
			GridPane inputTT = new GridPane();
			TruthTable t = new TruthTable(inputTT, truthTableArray, inpArray,inputCount);
			inputTT = t.getGrid();
			VBox vTT = new VBox(text, inputTT);


			HBox h;
			if(inputTypeChoice==1)
				h = new HBox(vTT,vKM);
			else 
				h = new HBox(vTT,vBool); //boolean vkme yerine

			h.setSpacing(50);
			inTabBox.getChildren().addAll(h);
			inTabBox.getChildren().addAll(simplify);	
		}
				);
		simplify.setOnAction(e-> {

			if(!inputFld.getText().equals("")){
				BooleanExp b = new BooleanExp(inputFld.getText().substring(2));
				b.standartForm();
                b.simplifyIt();

				Label text = new Label("Simple Expression");
				TextField txtfld = new TextField(""); //(b.getSimpleExpression());
				txtfld.setMaxWidth(300);
				System.out.println(inputFld.getText());
	
				txtfld.setText(karno.simplifier()); //simplification using KMap

				VBox vbox = new VBox(text,txtfld);
				vbox.setAlignment(Pos.BASELINE_CENTER);
				vbox.setSpacing(10);
				outputTab = new Tab("OUTPUT");
				outputTab.setContent(vbox);
				tabpane.getTabs().addAll(outputTab);
			}
		});

		inputTab.setContent(inTabBox);
		inputTab.setClosable(false);
		tabpane.getTabs().addAll(inputTab);
		//INPUT TAB END <-----


		//OUTPUT TAB ---->



		//OUTPUT TAB END <----



		Scene scene = new Scene(tabpane,600,600);
		stage.setScene(scene);
		stage.setTitle("Logic Expression Simplifier");
		stage.setWidth(800);
		stage.setWidth(1000);
		stage.show();
	}

	public void create(){
		if(boolRd.isSelected()){
			inputTypeChoice = 1;
		}
		else if(KMRd.isSelected()){
			inputTypeChoice = 2;
		}
		if(twoRd.isSelected()){
			inputNumberChoice = 2;
		}
		else if(threeRd.isSelected()){
			inputNumberChoice = 3;
		}
		else if(fourRd.isSelected()){
			inputNumberChoice = 4;
		}


		if(inputTypeChoice == 1){ // 2 input
			inputFld = new TextField();
			inputFld.setMaxWidth(300);
		}
		else if(inputTypeChoice == 2){ //3 input
			if(inputNumberChoice == 2){
				//2x2  km
			}
			else if(inputNumberChoice == 3){
				//2x4 km
			}
			else if(inputNumberChoice == 4){
				//4x4km
			}
		}

	}

	public String convertToBoolean(int input,int binary){

		String temp = Integer.toBinaryString(binary);
		while(temp.length() < input) temp = "0" + temp;

		String[] positive = {"A","B","C","D"};
		String[] negative = {"A'","B'","C'","D'"};

		String temp2 = "";
		for (int i = 0; i < temp.length(); i++) {
			if(temp.charAt(i) == '1'){
				temp2+= positive[i];
			}
			else{
				temp2+= negative[i];
			}
		}

		return temp2;
	}



}
