package duke;

public class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return Ui.GENERIC_ERROR_MESSAGE_PREFIX + super.getMessage();
    }
}
