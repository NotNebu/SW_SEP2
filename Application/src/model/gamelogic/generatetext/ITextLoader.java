package model.gamelogic.generatetext;

import java.util.List;

// Interface for loading text data
public interface ITextLoader {
  // Loads random text data into the provided list
  void loadRandomText(List<String> textsForGame);
}
