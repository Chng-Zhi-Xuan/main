package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.place.ReadOnlyPlace;

/**
 * An UI component that displays information of a {@code Place}.
 */
public class PlaceCard extends UiPart<Region> {

    private static final String FXML = "PlaceListCard.fxml";
    private static String[] colors = { "red", "blue", "orange", "brown", "green", "grey", "burlywood", "coral",
        "cyan", "dodgerblue", "olivedrab", "turquoise", "darksalmon", "slategray", "teal", "mediumturquoise",
        "lawngreen", "darkcyan", "saddlebrown", "darkblue"};
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPlace place;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PlaceCard(ReadOnlyPlace place, int displayedIndex) {
        super(FXML);
        this.place = place;
        id.setText(displayedIndex + ". ");
        initTags(place);
        bindListeners(place);
    }

    /** Assign a random color from String[] colors
     *  Ensure each tag has a different color by using HashMap tagColors
     */
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Place} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPlace place) {
        name.textProperty().bind(Bindings.convert(place.nameProperty()));
        phone.textProperty().bind(Bindings.convert(place.phoneProperty()));
        address.textProperty().bind(Bindings.convert(place.addressProperty()));
        email.textProperty().bind(Bindings.convert(place.emailProperty()));
        place.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            //place.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            initTags(place);
        });
    }

    /**
     * Assign a tag with color to a place
     */
    private void initTags(ReadOnlyPlace place) {
        //place.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        place.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PlaceCard)) {
            return false;
        }

        // state check
        PlaceCard card = (PlaceCard) other;
        return id.getText().equals(card.id.getText())
                && place.equals(card.place);
    }
}