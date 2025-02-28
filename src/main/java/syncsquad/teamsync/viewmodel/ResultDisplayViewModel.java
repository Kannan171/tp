package syncsquad.teamsync.viewmodel;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ResultDisplayViewModel {
    private final StringProperty feedbackToUser = new SimpleStringProperty();

    public StringProperty feedbackToUserProperty() {
        return feedbackToUser;
    }

    public void setFeedbackToUser(String feedback) {
        requireNonNull(feedback);
        feedbackToUser.set(feedback);
    }
}
