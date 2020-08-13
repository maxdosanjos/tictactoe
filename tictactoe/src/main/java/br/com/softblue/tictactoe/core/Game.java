package br.com.softblue.tictactoe.core;

import java.io.IOException;

import br.com.softblue.tictactoe.Constants;
import br.com.softblue.tictactoe.score.FileScoreManager;
import br.com.softblue.tictactoe.score.ScoreManager;
import br.com.softblue.tictactoe.ui.UI;

public class Game {
	Board board = new Board();
	Player[] players = new Player[Constants.SYMBOL_PLAYERS.length];
	private int currentPlayerIndex = -1;
	private ScoreManager scoreManager;

	public void play() throws IOException {
		this.scoreManager = createScoreManager();

		UI.printGameTitle();

		for (int i = 0; i < players.length; i++) {
			Player player = createPlayer(i);
			players[i] = player;
		}

		boolean gameEnded = false;
		Player currentPlayer = nextPlayer();
		Player winner = null;

		while (!gameEnded) {
			board.print();

			boolean sequencieFound;
			try {
				sequencieFound = currentPlayer.play();
			} catch (InvalidMoveException e) {
				UI.printText("Erro: " + e.getMessage());
				continue;
			}
			if (sequencieFound) {
				gameEnded = true;
				winner = currentPlayer;
			} else if (board.isFull()) {
				gameEnded = true;
			} else {
				currentPlayer = nextPlayer();
			}
		}

		if (winner == null) {
			UI.printText("O jogo terminou empatado");
		} else {
			UI.printText("O Jogador '" + winner.getName() + "' venceu o jogo");

			scoreManager.saveScore(winner);
		}
		board.print();
		UI.printText("Fim do jogo");

	}

	private Player createPlayer(int index) {
		String name = UI.readInput("Jogador " + (index + 1) + "=> ");
		char symbol = Constants.SYMBOL_PLAYERS[index];

		Player player = new Player(name, board, symbol);

		Integer score = scoreManager.getScore(player);
		if (score != null) {
			UI.printText("O jogador '" + name + "' já possui '" + score + "' vitória(s)!");
		}

		UI.printText("O jogador '" + name + "' vai usar o símbolo '" + symbol + "'");
		return player;
	}

	private Player nextPlayer() {
//		this.currentPlayerIndex++;
//		if (this.currentPlayerIndex >= players.length) {
//			this.currentPlayerIndex = 0;
//		}
		currentPlayerIndex = (currentPlayerIndex + 1) % this.players.length;

		return this.players[this.currentPlayerIndex];
	}

	private ScoreManager createScoreManager() throws IOException {
		return new FileScoreManager();
	}
}
