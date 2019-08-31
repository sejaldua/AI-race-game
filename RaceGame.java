import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

/**
 * 
 * @author Sejal Dua
 *
 */
public class RaceGame {
	static Scanner in;
	static String[] board;
	static String turn;
	static int curr;
	static int mode;
	static int size;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		turn = "X";
		curr = 0;
		String winner = null;
		System.out.println("Welcome to ~THE RACE GAME~.");
		System.out.println("RULES:");
		System.out.println("------");
		System.out.println("Imagine that each move represents a ball sliding 1, 2, or 3 slots.");
		System.out.println("The object of the game is for YOU to be the one to slide");
		System.out.println("the ball into the very last slot. Best of luck!");
		System.out.println("------");
		System.out.println("How many players?");
		System.out.println("\t1: you will play against an AI!");
		System.out.println("\t2: play against a friend");
		while (true) {
			try {
				mode = in.nextInt();
				if (!(mode == 1 || mode == 2)) {
					System.out.println("Invalid input; please enter either 1 or 2: ");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input; please enter either 1 or 2: ");
				in.next(); // consumes invalid token
			}
		}
		System.out.println("Enter a game board size (between 9 and 21 please): ");
		while (true) {
			try {
				size = in.nextInt();
				if (!(size > 8 && size <=21)) {
					System.out.println("Invalid input; re-enter size between 9 and 21: ");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input; re-enter size between 9 and 21: ");
				in.next(); // consumes invalid token
			}
		}
		board = new String[size];
		populateEmptyBoard();
		printBoard();
		if (mode == 1) {
			System.out.println("You get to go first.");
		} else {
			System.out.println(turn + " gets to go first.");
		}

		while (winner == null) {
			if (mode == 2) {
				int numInput;
				try {
					numInput = in.nextInt();
					if (!(numInput > 0 && numInput <= size)) {
						System.out.println("Invalid input; re-enter slot number:");
						continue;
					}
					else if (!(numInput <= (curr + 3))) {
						System.out.println("Invalid input; You can only move 1, 2, or 3 slots forward.");
						continue;
					}
					else if (numInput < curr) {
						System.out.println("Invalid input; You cannot move backwards.");
						continue;
					}
				} catch (InputMismatchException e) {
					System.out.println("Invalid input; re-enter slot number:");
					in.next(); // consumes invalid token
					continue;
				}
				if (board[numInput-1].equals(".")) {
					board[numInput-1] = turn;
					for (int a = curr; a < numInput - 1; a++) {
						board[a] = " ";
					}
					curr = numInput;
					if (turn.equals("X")) {
						turn = "O";
					} else {
						turn = "X";
					}
					printBoard();
					winner = checkWinner();
				} else {
					System.out.println("Slot already taken; re-enter slot number:");
					continue;
				}
			}
			else {
				if (turn.equals("X")) {
					int numInput;
					try {
						numInput = in.nextInt();
						if (!(numInput > 0 && numInput <= size)) {
							System.out.println("Invalid input; re-enter slot number:");
							continue;
						}
						else if (!(numInput <= (curr + 3))) {
							System.out.println("Invalid input; You can only move 1, 2, or 3 slots forward.");
							continue;
						}
						else if (numInput < curr) {
							System.out.println("Invalid input; You cannot move backwards.");
							continue;
						}
					} catch (InputMismatchException e) {
						System.out.println("Invalid input; re-enter slot number:");
						continue;
					}
					if (board[numInput-1].equals(".")) {
						board[numInput-1] = turn;
						for (int a = curr; a < numInput - 1; a++) {
							board[a] = " ";
						}
						curr = numInput;
						turn = "O";
						printBoard();
						winner = checkWinner();
					} else {
						System.out.println("Slot already taken; re-enter slot number:");
						continue;
					}
				} else {
					int numAI;
					numAI = smartMove();
					if (board[numAI-1].equals(".")) {
						board[numAI-1] = turn;
						for (int a = curr; a < numAI - 1; a++) {
							board[a] = " ";
						}
						curr = numAI;
						turn = "X";
						printBoard();
						winner = checkWinner();
					}
				}
			}
		}
		if (mode == 2) {
			System.out.println("Congratulations! " + winner + "'s have won! Thanks for playing.");
		}
		else {
			if (winner.equals("X")) {
				System.out.println("Darn-- you won! Thanks for playing.");
			}
			else {
				System.out.println("I feel intelligent. Better luck next time.");
			}
		}
	}

	static String checkWinner() {
		if (board[size - 1].equals("X")) {
			return "X";
		} else if (board[size - 1].equals("O")) {
			return "O";
		}

		if (mode == 2) {
			System.out.println(turn + "'s turn; enter a slot number to place " + turn + " in:");
		}
		else {
			if (turn.equals("X")) {
				System.out.println("Your turn; enter a slot number to place X in:");
			}
			else {
				System.out.println("The AI will make a move now. beep bop...");
			}
		}
		return null;
	}

	static void printBoard() {
		System.out.print("\\---");
		for (int a = 0; a < size - 1; a++) {
			System.out.print("|---");
		};
		System.out.println("/");
		System.out.print("| ");
		for (int a = 0; a < size; a++) {
			System.out.print(board[a] + " | ");
		}
		System.out.println();
		System.out.print("/---");
		for (int a = 0; a < size - 1; a++) {
			System.out.print("|---");
		};
		System.out.println("\\");
		System.out.print("| ");
		for (int a = 0; a < size; a++) {
			if (a > 8) {
				System.out.print(String.valueOf(a+1) + "| ");
			}
			else {
				System.out.print(String.valueOf(a+1) + " | ");
			}
		}
		System.out.println();
	}

	static void populateEmptyBoard() {
		for (int a = 0; a < size; a++) {
			board[a] = ".";
		}
	}

	static int smartMove() {
		for (int a = 0; a < (size / 4) + 1; a++) {
			if (curr == (size % 4) + (4*a)) {
				Random rand = new Random(); 
				return (curr + rand.nextInt(3) + 1);
			}
			else if (curr < (size % 4) + (4*a)) {
				return (size % 4) + (4*a);
			}
		}
		return 0;
	}
}
