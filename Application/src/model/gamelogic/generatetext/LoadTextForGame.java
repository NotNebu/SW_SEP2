package model.gamelogic.generatetext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Loads text data from files into a list of strings
public class LoadTextForGame implements ITextLoader {
    private static final Random random = new Random();
    private static final int DEFAULT_FILE_NUMBER = 1;
    private static final String FILE_NAME_PATTERN = "/datatext/Text%d.txt";

    @Override
    public void loadRandomText(List<String> textsForGame) {
        String fileName = String.format(FILE_NAME_PATTERN, DEFAULT_FILE_NUMBER);

        // Load files from resources
        try (InputStream inputStream = LoadTextForGame.class.getResourceAsStream(fileName)) {
            if (inputStream != null) {
                List<String> lines = readLines(inputStream);
                if (!lines.isEmpty()) {
                    // Add a random line from the file to the queue
                    textsForGame.add(lines.get(random.nextInt(lines.size())));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads all lines from the input stream
    private List<String> readLines(InputStream inputStream) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
