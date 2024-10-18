package util.javafx;

import javafx.scene.control.Label;

// Utility methods for working with JavaFX Labels.
public class LabelUtils {

    // Creates a copy of an existing Label, preserving style and dimensions.
    public static Label cloneLabel(Label original) {
        Label clone = new Label();
        clone.setFont(original.getFont());
        clone.setMinWidth(original.getMinWidth());
        clone.setMaxWidth(original.getMaxWidth());
        clone.setPrefWidth(original.getPrefWidth());
        clone.setMinHeight(original.getMinHeight());
        clone.setMaxHeight(original.getMaxHeight());
        clone.setPrefHeight(original.getPrefHeight());
        clone.setStyle(original.getStyle());
        clone.setAlignment(original.getAlignment());
        clone.setText(original.getText());
        return clone;
    }
}
