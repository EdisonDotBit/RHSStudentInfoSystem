package studentinfosystem.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BorrowedBook {
    private final StringProperty bookName;
    private final StringProperty dateBorrowed;
    private final StringProperty status;
    private final StringProperty studentLRN; // Add this field

    public BorrowedBook(String bookName, String dateBorrowed, String status, String studentLRN) {
        this.bookName = new SimpleStringProperty(bookName);
        this.dateBorrowed = new SimpleStringProperty(dateBorrowed);
        this.status = new SimpleStringProperty(status);
        this.studentLRN = new SimpleStringProperty(studentLRN); // Assign the student LRN
        
    }

    public String getBookName() { return bookName.get(); }
    public String getDateBorrowed() { return dateBorrowed.get(); }
    public String getStatus() { return status.get(); }
    public String getStudentLRN() { return studentLRN.get(); }// Getter for LRN

    public StringProperty bookNameProperty() { return bookName; }
    public StringProperty dateBorrowedProperty() { return dateBorrowed; }
    public StringProperty statusProperty() { return status; }
}
