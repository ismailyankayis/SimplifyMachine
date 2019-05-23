

public class BooleanExp {

	private String boolExpression;
	private String standartFormExpression;
	private int count;//For the inputs array.
	private String[] inputs;
	private String[] standartArray;// To keep standard expressions
	private int counterStandart;
	private String [] binaryArray;//To keep the binary version of the expression.
	private int binaryArrayLength;
	private boolean whichOne = true;
	private String [][] xArray;
	private int maximumX;	
	private String []mainArrayCounting;
	private int mainArrayCountingLength;
	private String [][] keepTheCombination;
	private int lengthKeep;
	private int []keepTheX;
	int g = 0;
	private String simpleExpression;
	
	private String[] ttArray;

	////////////////////////////
	private String [] spareArrCounting;
	private int lengthSpareArrCounting;

	private String [] spareArrCounting1;
	private int lengthSpareArrCounting1;

	//Bunlar hep þu iðrenç quine metodunun 2.kýsmý için
	////////////////////////////



	public BooleanExp(String data){
		simpleExpression = "";
		boolExpression = data;
		standartFormExpression = "";
		count = 0;
		standartForm();
		//simplifyIt();
	}

	public void standartForm(){
		String[] tempArray = boolExpression.split("\\+");

		inputs = new String[4]; // keep the different inputs 
		//Özenç made this array length 10.
		//So we can add 10 different letters in the expression.
		count = 0;
		for (int i = 0; i < tempArray.length; i++) {
			String temp = tempArray[i].replaceAll("'", "");
			for (int j = 0; j < temp.length(); j++) {
				String c = temp.substring(j, j+1);
				if(count != 0){
					boolean flag = false;
					for (int k = 0; k < count; k++) {
						if((int)inputs[k].charAt(0) == (int)c.charAt(0)){
							flag = true;
							break;
						}
					}
					if(!flag){
						inputs[count] = c;
						count++;
					}
				}
				else{
					inputs[count] = c;
					count++;
				}
			}
		}//Determining the different letters in the expression

		standartArray = new String[400];// To keep standard expressions
		counterStandart = 0;

		for (int i = 0; i < tempArray.length; i++) {
			String temp = tempArray[i].replaceAll("'", "");
			String missingLetters = "";

			for (int j = 0; j < inputs.length; j++) {

				//inputs[j] != null >>>> Özenç added it because inputs.lentgth is 10 
				//and it was giving a null pointer exception error
				if(inputs[j] != null && !temp.contains(inputs[j])){
					missingLetters += inputs[j]; // to find missing inputs
				}

			}
			if(missingLetters.length() == 0){
				standartArray[counterStandart] = tempArray[i];
				counterStandart++;
			}
			////System.out.println(missingLetters);

			for (int j = 0; j < Math.pow(2, missingLetters.length()) && missingLetters.length() != 0; j++) { // look for all statements
				String binary = Integer.toBinaryString(j);
				while(binary.length() != missingLetters.length()) binary = "0" + binary;
				String tempE = tempArray[i];

				int countM = 0;
				for (int k = 0; k < binary.length(); k++) {
					if(binary.charAt(k) == '0'){
						tempE += missingLetters.charAt(countM) + "'";

					}
					else{
						tempE += missingLetters.charAt(countM);
					}
					countM++;
				}
				standartArray[counterStandart] = tempE;
				counterStandart++;
			}
		}

		regulateTheExp();

		for (int i = 0; i < counterStandart; i++) {
			if(i != counterStandart-1){
				standartFormExpression+= standartArray[i] + "+";
			}
			else{
				standartFormExpression+= standartArray[i];
			}
		}
		////System.out.println(standartFormExpression);
		////////////////////////////////////////////////////////////////////
		//Converting Binary Part
		binaryArray = new String[counterStandart];
		String xr = "";

		for(int i = 0 ; i < counterStandart; i++)
		{
			int x = 1;

			for(int j = 0; j < standartArray[i].length(); j++){
				if((int)standartArray[i].charAt(j) == 39){
					binaryArray[i] = binaryArray[i].substring(0, j-x);
					binaryArray[i] += "0";
					x++;
				}
				else{
					if(binaryArray[i] != null){
						binaryArray[i] += "1";
					}
					else{
						binaryArray[i] = "1";
					}
				}

			}
			////System.out.println(binaryArray[i]);
			//xr += binaryArray[i] + " +";

		}//The binaries are added into the binaryArray.
		////System.out.println(xr);
		/////////////////////////////////////////////////////////////////////
		//Simplify Part

		
		ttArray = binaryArray;

		//System.out.println();


	}

	public void simplifyIt()
	{
		
		int newArrayIndex = 0;
		int newArrayIndex1 = 0;
		String []spareArray = new String[counterStandart];
		String []spareArray1 = new String[counterStandart];
		boolean []binaryBooleanArr = new boolean[counterStandart];
		boolean exit = true;

		convertBinaryToDecimal(binaryArray);

		while(exit){

			deleteTheSameThings();//This deletes the same expressions
			int flag = 0;

			for(int i = 0; i < counterStandart; i++){
				for(int j = i+1; j < counterStandart; j++){
					if(compareTheBits(binaryArray[i], binaryArray[j])){
						flag = 1;
						String temp = "";
						for(int k = 0; k < binaryArray[i].length(); k++){
							if(binaryArray[i].charAt(k) == binaryArray[j].charAt(k)){
								temp += binaryArray[i].charAt(k);
							}
							else{
								temp += "-";
							}
						}
						binaryBooleanArr[i] = true;
						binaryBooleanArr[j] = true;
						if(whichOne){
							spareArray[newArrayIndex] = temp;
							newArrayIndex++;

							spareArrCounting1[lengthSpareArrCounting1] = spareArrCounting[i]+"-"+spareArrCounting[j];

							////System.out.println("spareArrayCounting  "+spareArrCounting1[lengthSpareArrCounting1]+" /// "+temp);
							lengthSpareArrCounting1++;
						}
						else{
							spareArray1[newArrayIndex1] = temp;
							newArrayIndex1++;
							spareArrCounting[lengthSpareArrCounting] = spareArrCounting1[i]+"-"+spareArrCounting1[j];

							////System.out.println("spareArrayCounting1  "+spareArrCounting[lengthSpareArrCounting]+" /// "+temp);
							lengthSpareArrCounting++;
						}
					}// Ýf
				}
			}
			//////////////////////////////////////

			if(flag == 0)
				exit = false;


			if(whichOne){

				for(int b = 0; b < counterStandart; b++){
					if(binaryBooleanArr[b] == false){
						spareArray[newArrayIndex] = binaryArray[b];
						newArrayIndex++;

						if(spareArrCounting1[lengthSpareArrCounting1] == null){
							spareArrCounting1[lengthSpareArrCounting1] = spareArrCounting[b];
						}
						else{
							spareArrCounting1[lengthSpareArrCounting1] += "-"+spareArrCounting[b];
						}

						////System.out.println("spareArray  "+spareArrCounting1[lengthSpareArrCounting1]+" /// "+binaryArray[b]);
						lengthSpareArrCounting1++;
					}
				}

				counterStandart = newArrayIndex;
				spareArray1 = new String[newArrayIndex];
				newArrayIndex1 = 0;
				binaryArray = spareArray;
				binaryBooleanArr = new boolean[newArrayIndex];

				spareArrCounting = new String[lengthSpareArrCounting1];
				lengthSpareArrCounting = 0;

				binaryArrayLength = lengthSpareArrCounting1;

				mainArrayCounting = spareArrCounting1;
				mainArrayCountingLength = lengthSpareArrCounting1;

				whichOne = false;
			}
			else{

				for(int b = 0; b < counterStandart; b++){
					if(binaryBooleanArr[b] == false){
						spareArray1[newArrayIndex1] = binaryArray[b];
						newArrayIndex1++;

						if(spareArrCounting[lengthSpareArrCounting] == null){
							spareArrCounting[lengthSpareArrCounting] = spareArrCounting1[b];
						}
						else{
							spareArrCounting[lengthSpareArrCounting] += "-"+spareArrCounting1[b];
						}


						////System.out.println("spareArray1  "+spareArrCounting[lengthSpareArrCounting]+" /// "+binaryArray[b]);
						lengthSpareArrCounting++;
					}
				}

				counterStandart = newArrayIndex1;
				spareArray = new String[newArrayIndex1];
				newArrayIndex = 0;
				binaryArray = spareArray1;
				binaryBooleanArr = new boolean[newArrayIndex1];

				spareArrCounting1 = new String[lengthSpareArrCounting];
				lengthSpareArrCounting1 = 0;

				binaryArrayLength = lengthSpareArrCounting;

				mainArrayCounting = spareArrCounting;
				mainArrayCountingLength = lengthSpareArrCounting;

				whichOne = true;

			}
			////System.out.println("//////////////////////////////////////////////////////////////////////");

			String tempSimple = "";
			for (int i = 0; i < binaryArray.length; i++) {
				if(binaryArray[i] != null)
					tempSimple += convertBoolean(binaryArray[i]) + "+";
			}

			simpleExpression = tempSimple.substring(0, tempSimple.length()-1);

		}//while

		/*
		int a = (int) Math.pow(2, count);
		keepTheX = new int[mainArrayCountingLength];
		xArray = new String[mainArrayCountingLength][a];

		addTheX(mainArrayCounting);

		lengthKeep = (int) (Math.pow(2, mainArrayCountingLength)-1);
		keepTheCombination = new String[lengthKeep][2];
		lengthKeep = 0;



		for(int i = 1; i <= mainArrayCountingLength; i++){
			printCombination(binaryArray, mainArrayCountingLength, i);
			//System.out.println("//////////////");
			g = 0;
		}

		//System.out.println();*/
	}

	
	public String convertBoolean(String binary){

		String temp = "";

		for (int i = 0; i < binary.length(); i++) {
			if(binary.charAt(i) == '1'){
				temp += inputs[i];
			}
			else if(binary.charAt(i) == '0'){
				temp += inputs[i] + "'";
			}
		}

		return temp;
	}
	
	public int[][] generateTruthTable()
	{
		
		
		int [][] truthTable = new int[(int) Math.pow(2, ttArray[0].length())][ttArray[0].length()+1];
		
		for (int i = 0; i < Math.pow(2, truthTable[0].length-1); i++) 
		{
			String binary = Integer.toBinaryString(i);
			while(binary.length() != ttArray[0].length()) binary = "0" + binary;
			for (int j = 0; j < binary.length(); j++) 
			{
				truthTable[i][j] = Character.getNumericValue(binary.charAt(j));
			}
			boolean flag = false;
			for (int j = 0; j < ttArray.length; j++) 
			{
				if(binary.equals(ttArray[j]))
				{
					flag = true;
					break;
				}
					
			}
			
			if(flag)
				truthTable[i][truthTable[0].length-1]=1;
			else
				truthTable[i][truthTable[0].length-1]=0;
		}
		return truthTable;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	public void combinationUtil(String arr[], String data[], int start, int end, int index, int r) 
	{
		if (index == r) {
			for (int j = 0; j < r; j++){
				/*
				if(keepTheCombination[lengthKeep][0] == null){
					keepTheCombination[lengthKeep][0] = data[j];
					keepTheCombination[lengthKeep][1] = ""+keepTheX[g];
				}
				else{
					keepTheCombination[lengthKeep][0] += "+"+data[j];
					keepTheCombination[lengthKeep][1] =  ""+Integer.parseInt(keepTheCombination[lengthKeep][1])+keepTheX[g];
				}
				 */
				//System.out.print(data[j] + " ");
			}
			lengthKeep++;	
			//System.out.println("");
			return;
		}

		for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
			data[index] = arr[i];
			combinationUtil(arr, data, i + 1, end, index + 1, r);
		}
	}
	
	


	public void printCombination(String arr[], int n, int r) {
		String data[] = new String[r];
		combinationUtil(arr, data, 0, n - 1 , 0, r);
	}

	public void addTheX(String [] arr)
	{


		maximumX = 0;
		for(int i = 0; i < arr.length; i++){
			if(arr[i] != null){
				String[] bullShit = arr[i].split("-");
				for(int j = 0; j < bullShit.length; j++){
					int a = Integer.parseInt(bullShit[j]);
					xArray[i][a] = "x";
					keepTheX[i]++;
					maximumX++;
				}
			}
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////






	public void convertBinaryToDecimal(String [] arr)
	{
		int a = arr.length*5;
		spareArrCounting = new String[a];
		lengthSpareArrCounting = 0;
		//////////////////////////////////////////
		spareArrCounting1 = new String[a];
		lengthSpareArrCounting1 = 0;

		int length = arr.length;

		for(int i = 0; i < length; i++){
			int sum = 0;
			for(int j = 0; j < arr[i].length(); j++){
				int x = Integer.parseInt(""+arr[i].charAt(j));
				int y = arr[i].length()-j-1;
				sum += x*Math.pow(2, y);
			}
			spareArrCounting[i] = ""+sum;
		}
	}

	public void deleteTheSameThings()
	{
		if(whichOne){
			for(int i = 0; i < counterStandart; i++){
				for(int j = i+1; j < counterStandart; j++){
					if(binaryArray[i] != null && binaryArray[j] != null 
							&& binaryArray[i].equalsIgnoreCase(binaryArray[j])){

						for(int k = j; k < counterStandart-1; k++){
							binaryArray[k] = binaryArray[k+1];
							spareArrCounting[k] = spareArrCounting[k+1];
						}
						counterStandart--;
						lengthSpareArrCounting--;
						binaryArray[counterStandart] = null;
						j--;
					}
				}
			}
		}
		else{

			for(int i = 0; i < counterStandart; i++){
				for(int j = i+1; j < counterStandart; j++){
					if(binaryArray[i] != null && binaryArray[j] != null 
							&& binaryArray[i].equalsIgnoreCase(binaryArray[j])){

						for(int k = j; k < counterStandart-1; k++){
							binaryArray[k] = binaryArray[k+1];
							spareArrCounting1[k] = spareArrCounting1[k+1];
						}
						counterStandart--;
						lengthSpareArrCounting1--;
						binaryArray[counterStandart] = null;
						j--;
					}
				}
			}
		}
	}

	public boolean compareTheBits(String temp,String temp1)
	{
		boolean flag = false;
		int countDifferences = 0;

		for(int i = 0; i < temp.length(); i++){
			if((int)temp.charAt(i) != (int)temp1.charAt(i)){
				countDifferences++;
			}
		}
		if(countDifferences == 1){
			flag = true;
		}
		return flag;
	}

	public void regulateTheExp()//Regulate means düzenlemek
	{
		for(int i = 0; i < counterStandart; i++){
			/////////
			String temp = standartArray[i];
			String []tempArray = new String[count];
			int tempArrayCount = 0;
			/////////
			for(int j = 0; j < temp.length(); j++){

				if((int)standartArray[i].charAt(j) == 39){
					tempArray[tempArrayCount-1] += "'";
				}
				else{
					tempArray[tempArrayCount] = ""+standartArray[i].charAt(j);
					tempArrayCount++;
				}
			}
			///////////////////////////////////////////////////////////////
			tempArray = sortingInputs(tempArray);
			standartArray[i] = "";
			for(int l = 0; l < tempArray.length; l++){
				standartArray[i] += tempArray[l];
			}
		}
	}

	public String[] sortingInputs(String []arr)//It is sorted the inputs array.
	{
		int temp1CharValue = -1;
		int n = count;
		int tempCharValue = -1;
		String temp = "";

		for(int i = 0; i < n; i++) {
			for(int j = 1; j < (n-i); j++){
				tempCharValue = (int)arr[j-1].charAt(0);
				temp1CharValue = (int)arr[j].charAt(0);

				if(tempCharValue >= temp1CharValue){
					temp = arr[j-1];
					arr[j-1] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr;
	}

	public String getSimpleExpression() {
		return simpleExpression;
	}

	public String[] getInputs() {
		inputs = sortingInputs(inputs);
		return inputs;
	}

	public int getCount() {
		return count;
	}

	public String getStandartFormExpression() {
		return standartFormExpression;
	}

	

	public String getBoolExpression() {
		return boolExpression;
	}

	
}
