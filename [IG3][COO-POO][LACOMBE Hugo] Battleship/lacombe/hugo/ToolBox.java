package lacombe.hugo;
import java.util.Scanner;

public abstract class ToolBox {


	public static Coordinates askPosition() {
		Scanner sc = new Scanner(System.in);
		Coordinates result = new Coordinates();
		boolean validEntry = false;
		while(!(validEntry)) {
			System.out.println("Type in position like :(A-J)(1-10)");
			String position = sc.nextLine();
			if(position.length() == 2 || position.length() == 3) {
				if(position.length() == 2){
					result.giveValues(convertLetterToInt(position.charAt(0)),(int)position.charAt(1)-49);
				}
				else{
					result.giveValues(convertLetterToInt(position.charAt(0)),10*(int)(position.charAt(1)-48) + (int)position.charAt(2)-49 ) ;
				}
				System.out.println(result);
				validEntry = result.correctCoordinates();
				if(!validEntry) {
					System.out.println("Wrong typing, try again.");
				}
			}
			else {
				System.out.println("Wrong typing, try again.");
			}
		}
		return result;
	}


	private static int convertLetterToInt(char val)
	{
		int toReturn = -1;
		switch (val)
		{
		case 'A':   toReturn = 0;
		break;
		case 'B':   toReturn = 1;
		break;
		case 'C':   toReturn = 2;
		break;
		case 'D':   toReturn = 3;
		break;
		case 'E':   toReturn = 4;
		break;
		case 'F':   toReturn = 5;
		break;
		case 'G':   toReturn = 6;
		break;
		case 'H':   toReturn = 7;
		break;
		case 'I':   toReturn = 8;
		break;
		case 'J':   toReturn = 9;
		break;
		default:    toReturn = -1;
		break;
		}

		return toReturn;
	}

	public static int[] getGameParameters() {
		Scanner sc = new Scanner(System.in);
		int parameter[] = new int[3];
		System.out.println("Choose a Game Mode: 1-Player Vs Player 2-Player Vs Computer 3-Spectator");
		try{
			parameter[0] = sc.nextInt();
			sc.nextLine();
			if(parameter[0] == 2) {
				System.out.println("Choose a Computer Difficulty : 1-Easy 2-Medium 3-Hard 4-Impossible");
				parameter[1] = sc.nextInt();
				if( parameter[1]!=1 && parameter[1]!=2 && parameter[1]!=3) {
					throw new java.util.InputMismatchException();
				}
			}
			if(parameter[0] == 3) {
				System.out.println("Choose a difficulty for Computer 1 : 1-Easy 2-Medium 3-Hard");
				parameter[1] = sc.nextInt();
				if( parameter[1]!=1 && parameter[1]!=2 && parameter[1]!=3) {
					throw new java.util.InputMismatchException();
				}
				System.out.println("Choose a difficulty for Computer 2 : 1-Easy 2-Medium 3-Hard");
				parameter[2] = sc.nextInt();
				if( parameter[2]!=1 && parameter[2]!=2 && parameter[2]!=3) {
					throw new java.util.InputMismatchException();
				}
			}
			else {
				throw new java.util.InputMismatchException();
			}
			return parameter;
		}
		catch(java.util.InputMismatchException e) {
			System.out.println("Incorrect input, try again");
			return getGameParameters();
		}
		finally{
			sc.nextLine();
		}
	}

	public static Boolean wantToContinue() {
		Scanner sc = new Scanner(System.in);
		String line;
		try {
			line = sc.nextLine();
			if(line.equals("y")) {
				return true;
			}
			if(line.equals("n")) {
				return false;
			}
			else {
				throw new java.util.InputMismatchException();
			}
		}
		catch(java.util.InputMismatchException e) {
			System.out.println("Incorrect input, try again");
			return wantToContinue();
		}
	}

}
