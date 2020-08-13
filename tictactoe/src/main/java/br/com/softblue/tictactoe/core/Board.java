package br.com.softblue.tictactoe.core;

import br.com.softblue.tictactoe.Constants;
import br.com.softblue.tictactoe.ui.UI;

public class Board {
	char[][] matrix;

	public Board() {
		this.matrix = new char[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
		clear();
	}

	public void clear() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = ' ';
			}
		}
	}

	public void print() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				UI.printTextWithNoNewLine(String.valueOf(matrix[i][j]));
				if (j < matrix[i].length - 1)
					UI.printTextWithNoNewLine(" | ");
			}
			UI.printNewLine();
			if (i < matrix[i].length - 1)
				UI.printText("---------");
		}
	}

	public boolean isFull() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == ' ')
					return false;
			}
		}
		return true;
	}

	private boolean checkRows(Player player) {
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			if (checkRow(i, player))
				return true;
		}
		return false;
	}

	private boolean checkRow(int i, Player player) {
		char symbol = player.getSymbol();
		boolean check = false;
		for (int j = 0; j < Constants.BOARD_SIZE; j++) {
			if (matrix[i][j] != symbol) {
				check = false;
				break;
			} else {
				check = true;
			}
		}
		return check;
	}

	private boolean checkCol(int j, Player player) {
		char symbol = player.getSymbol();
		boolean check = false;
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			if (matrix[i][j] != symbol) {
				check = false;
				break;
			} else {
				check = true;
			}
		}
		return check;
	}

	private boolean checkCols(Player player) {
		for (int j = 0; j < Constants.BOARD_SIZE; j++) {
			if (checkCol(j, player))
				return true;
		}
		return false;
	}

	private boolean checkDiagonal(Player player) {
		char symbol = player.getSymbol();
		boolean check = false;
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			if (matrix[i][i] != symbol) {
				check = false;
				break;
			} else {
				check = true;
			}
		}
		return check;
	}

	private boolean checkDiagonal2(Player player) {
		char symbol = player.getSymbol();
		boolean check = false;

		int lastLine = Constants.BOARD_SIZE - 1;

		for (int i = lastLine, j = 0; i >= 0; i--, j++) {
			if (matrix[i][j] != symbol) {
				check = false;
				break;
			} else {
				check = true;
			}
		}

		return check;
	}

	public boolean play(Player player, Move move) throws InvalidMoveException {
		int i = move.getI();
		int j = move.getJ();

		if (i < 0 || j < 0 || i >= Constants.BOARD_SIZE || j >= Constants.BOARD_SIZE) {
			throw new InvalidMoveException("O intervalo de jogada é inválido");
		}

		if (matrix[i][j] != ' ') {
			throw new InvalidMoveException("Jogada é inválida pois já foi realizada");
		}

		matrix[i][j] = player.getSymbol();

		return checkCols(player) || checkRows(player) || checkDiagonal(player) || checkDiagonal2(player);
	}
}
