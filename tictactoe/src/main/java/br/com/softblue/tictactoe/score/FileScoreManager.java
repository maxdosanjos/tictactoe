package br.com.softblue.tictactoe.score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.softblue.tictactoe.core.Player;

public class FileScoreManager implements ScoreManager {

	private static final Path SCORE_FILE = Paths.get("score.txt");

	private Map<String, Integer> scoreMap = new HashMap<>();

	public FileScoreManager() throws IOException {
		this.setup();
	}

	private void setup() throws IOException {
		if (!Files.exists(SCORE_FILE)) {
			Files.createFile(SCORE_FILE);
		}

		try (BufferedReader reader = Files.newBufferedReader(SCORE_FILE)) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\|");
				this.scoreMap.put(tokens[0], Integer.valueOf(tokens[1]));
			}
		}

	}

	@Override
	public Integer getScore(Player player) {

		return this.scoreMap.get(player.getName().toUpperCase());
	}

	@Override
	public void saveScore(Player player) throws IOException {
		Integer score = getScore(player);
		if (score == null) {
			score = 0;
		}

		this.scoreMap.put(player.getName().toUpperCase(), score + 1);

		try (BufferedWriter writer = Files.newBufferedWriter(SCORE_FILE)) {
			Set<Map.Entry<String, Integer>> entries = this.scoreMap.entrySet();

			for (Entry<String, Integer> entry : entries) {
				String name = entry.getKey();
				Integer s = entry.getValue();

				writer.write(name + "|" + s);
				writer.newLine();
			}
		}
	}

}
