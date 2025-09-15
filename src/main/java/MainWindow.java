import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import melody.Melody;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Melody melody;

    private Image userImage;
    private Image melodyImage;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Load images in initialize() to ensure they're loaded properly
        try {
            userImage = new Image(getClass().getResourceAsStream("/images/DaUser.jpeg"));
            melodyImage = new Image(getClass().getResourceAsStream("/images/MelodyAI.jpg"));

            // Add error checking
            if (userImage.isError() || melodyImage.isError()) {
                System.err.println("Failed to load images!");
            }
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }

    /** Injects the Melody instance */
    public void setMelody(Melody m) {
        melody = m;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        // Add null/empty check
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        String response = melody.getResponse(input);
        String commandType = melody.getCommandType();

        // Add null checks for response and commandType
        if (response == null) {
            response = "I didn't understand that command.";
        }
        if (commandType == null) {
            commandType = "";
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMelodyDialog(response, melodyImage, commandType)
        );
        userInput.clear();
    }
}