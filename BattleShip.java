/* 
 * BattleShip.java
 * 
 * Version: 
 *    BattleShip.java v 1
 * 
 * Revisions: 
 *     initial revision
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The programs depicts the game of battleship. This program prompts the user for
 * an input file which will contain the battle field dimensions. After providing
 * the input file user has to provide the coordinate where he wants to hit the
 * missile. If the boat will be present in that position It will say return
 * other wise it will say missed it.The game will end once there are no boats
 * available in the battlefield.
 *
 * 
 */
public class BattleShip {

	/**
	 * The main program.
	 *
	 * @param args command line arguments (ignored)
	 * @exception FileNotFoundException
	 * @throws FileNotFoundException
	 */

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Enter the File Path or Enter exit to quit");
		String filePath = scnr.nextLine();
		if (filePath.contains("exit")) {
			System.exit(0);
		}
		File file = new File(filePath);
		if (!file.exists()) {
			while (!file.exists()) {
				System.out.println("File Does Not exist,Try Again. Please give the full path name");
				System.out.println("Enter the File Path or Enter exit to quit");
				filePath = scnr.nextLine();
				if (filePath.equalsIgnoreCase("exit")) {
					System.out.println("exiting with code 0");
					System.exit(0);
				}
				file = new File(filePath);
			}
		}

		Scanner sc = new Scanner(file);
		int matrixLength = 0;

		while (sc.hasNextLine()) {
			sc.nextLine(); // just in case want to print the initial setup
			matrixLength++;
		}

		String[] dimensionArray = new String[matrixLength];
		int dimensionArrayIndex = 0;
		String[] battleField = dimensionArray;
		boolean checkTheEnd = false;
		sc.close();
		sc = new Scanner(file);
		while (sc.hasNextLine()) {
			dimensionArray[dimensionArrayIndex] = sc.nextLine().replaceAll(" ", "");
			dimensionArrayIndex++;
		}

		while (!checkTheEnd) {
			System.out.println("Please enter the row coordinate From 0 To " + (dimensionArray.length - 1));
			int rowCoordinate = scnr.nextInt();
			System.out.println("Please enter the column coordinate From 0 To " + (dimensionArray.length - 1));
			int columnCoordinate = scnr.nextInt();
			battleField = checkHit(rowCoordinate, columnCoordinate, dimensionArray);
			dimensionArray = battleField;
			checkTheEnd = checkEndOfTheGame(battleField);
		}
		System.out.println("No Boats are Left");
		sc.close();
		scnr.close();
	}

	/**
	 * The function will check whether the missile has hit the boat or not. If it
	 * hits it will pass the coordinate to function checkMiddlehit. It will also
	 * return the updated battlefield after ship got hit.
	 * 
	 * 
	 * @param      rowX       Coordinate X
	 * @param      columnY    Coordinate Y
	 * @param      field      The initial setup of the battle Field
	 * @return     field      Updated setup of the battle Field
	 */

	public static String[] checkHit(int rowX, int columnY, String[] field) {
		String shot = "";
		String vertical = "";
		String horizontal = "";
		String newHorizontal = "";
		String newVertical = "";
		if (Character.isDigit(field[rowX].charAt(columnY))) {
			horizontal = field[rowX];
			newHorizontal = checkMiddleHit(horizontal, columnY);

			for (int index = 0; index < field.length; index++) {
				vertical = vertical + field[index].charAt(columnY);
			}
			newVertical = checkMiddleHit(vertical, rowX);

			field[rowX] = newHorizontal;
			for (int index = 0; index < field.length; index++) {
				field[index] = field[index].replace(field[index].charAt(columnY), newVertical.charAt(index));
			}
			shot = "Hit";
		} else {
			shot = "Missed it";
		}
		System.out.println(shot);
		return field;

	}

	/**
	 * The function will check whether the missile has hit the boat in middle or
	 * not. If it is hit in the middle it will remove the entire boat.(Given the length of the boat is odd). 
         * Otherwise it will return the damaged boat.
	 * 
	 * 
	 * @param     boat                Contains string of the boat (Column wise or Row
	 *                                  wise)
	 * @param    coordinateToCheck    Coordinate where the boat has been hit
	 * 
	 * @return    newBoat returns     String of damaged boat if not in the middle else the
	 *                                damaged boat string
	 */

	public static String checkMiddleHit(String boat, int coordinateToCheck) {
		int countLeft = 0;
		int countRight = 0;
		String newBoat = "";
		char[] charArray = boat.toCharArray();
		if (Character.isDigit(boat.charAt(coordinateToCheck))) {
			for (int index = 0; index < coordinateToCheck; index++) {
				if (charArray[index] == charArray[coordinateToCheck]) {
					countLeft++;
				}
			}
			for (int index = coordinateToCheck + 1; index < charArray.length; index++) {
				if (charArray[coordinateToCheck] == charArray[index]) {
					countRight++;
				}
			}
			if (countLeft == countRight) {
				while (countLeft > 0) {
					charArray[coordinateToCheck + countLeft] = 's';
					charArray[coordinateToCheck - countLeft] = 's';
					countLeft--;
				}
			}
		}
		charArray[coordinateToCheck] = 's';
		for (int index = 0; index < charArray.length; index++) {
			newBoat = newBoat + charArray[index];
		}

		return newBoat;
	}

	/**
	 * It will check whether there are any boats present in the battlefield or not.
	 * If it is not present it will End the game
	 * 
	 * 
	 * @param       battleGround         The setup of the battleGround
	 * @return      true | false         If the game ends return true else false
	 */

	public static boolean checkEndOfTheGame(String[] battleGround) {
		String toCheck = "";
		int countEmptyRow = 0;
		for (int index = 0; index < battleGround.length; index++) {
			toCheck = toCheck + "s";
		}
		for (int index = 0; index < battleGround.length; index++) {
			if (toCheck.equals(battleGround[index])) {
				countEmptyRow++;
			}
		}
		if (countEmptyRow == battleGround.length) {
			return true;
		} else
			return false;
	}
}
