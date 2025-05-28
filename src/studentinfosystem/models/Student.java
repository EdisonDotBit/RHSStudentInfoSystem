package studentinfosystem.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final StringProperty lastName;
    private final StringProperty firstName;
    private final StringProperty middleName;
    private final StringProperty lrn;
    private final StringProperty contactNumber;
    private final StringProperty email;
    private final StringProperty age;
    private final StringProperty grade;
    private final StringProperty section;
    private final StringProperty strand;

    public Student(String lastName, String firstName, String middleName, String lrn, String contactNumber, String email, String age, String grade, String section, String strand) {
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.middleName = new SimpleStringProperty(middleName);
        this.lrn = new SimpleStringProperty(lrn);
        this.contactNumber = new SimpleStringProperty(contactNumber);
        this.email = new SimpleStringProperty(email);
        this.age = new SimpleStringProperty(age);
        this.grade = new SimpleStringProperty(grade);
        this.section = new SimpleStringProperty(section);
        this.strand = new SimpleStringProperty(strand);
    }

    public String getLastName() { return lastName.get(); }
    public void setLastName(String value) { lastName.set(value); }
    public StringProperty lastNameProperty() { return lastName; }

    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String value) { firstName.set(value); }
    public StringProperty firstNameProperty() { return firstName; }

    public String getMiddleName() { return middleName.get(); }
    public void setMiddleName(String value) { middleName.set(value); }
    public StringProperty middleNameProperty() { return middleName; }

    public String getLRN() { return lrn.get(); }
    public void setLRN(String value) { lrn.set(value); }
    public StringProperty lrnProperty() { return lrn; }

    public String getContactNumber() { return contactNumber.get(); }
    public void setContactNumber(String value) { contactNumber.set(value); }
    public StringProperty contactNumberProperty() { return contactNumber; }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }
    public StringProperty emailProperty() { return email; }

    public String getAge() { return age.get(); }
    public void setAge(String value) { age.set(value); }
    public StringProperty ageProperty() { return age; }

    public String getGrade() { return grade.get(); }
    public void setGrade(String value) { grade.set(value); }
    public StringProperty gradeProperty() { return grade; }

    public String getSection() { return section.get(); }
    public void setSection(String value) { section.set(value); }
    public StringProperty sectionProperty() { return section; }

    public String getStrand() { return strand.get(); }
    public void setStrand(String value) { strand.set(value); }
    public StringProperty strandProperty() { return strand; }
}
