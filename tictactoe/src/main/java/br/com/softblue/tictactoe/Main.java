package br.com.softblue.tictactoe;

import java.io.IOException;

import br.com.softblue.tictactoe.core.Game;

public final class Main {
	
	public static void main(String[] args) throws IOException {
		Game g = new Game();
		g.play();
	}

}
