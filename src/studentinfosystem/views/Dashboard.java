package studentinfosystem.views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import studentinfosystem.models.Student;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import studentinfosystem.database.DBConnection;
import java.sql.*;
import javafx.geometry.Pos;
import studentinfosystem.models.BorrowedBook;

public class Dashboard extends Application {
    private TextField lastNameField, firstNameField, middleNameField, lrnField, contactNumberField, emailField, ageField, gradeLevelField, sectionField , strandField, searchField;
    private TableView<Student> studentTable;
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private int userId; 
    private TableView<BorrowedBook> borrowedBookTable = new TableView<>();
    private ObservableList<BorrowedBook> borrowedBookList = FXCollections.observableArrayList();
    private TextField bookNameField;
    private DatePicker dateBorrowedField;
    private ComboBox<String> statusField;

    public Dashboard(int userId) {
    this.userId = userId; // Assign the logged-in user ID to the class
}
    @Override    
    public void start(Stage stage) {
        stage.setTitle("Student Info System");

        TabPane tabPane = new TabPane();

        // Student Info Tab
        Tab studentInfoTab = new Tab("Add Student Information", createStudentInfoTab());
        studentInfoTab.setClosable(false);

        // Manage Student Tab
        Tab manageStudentTab = new Tab("Manage Student Information", createManageStudentTab());
        manageStudentTab.setClosable(false);

        tabPane.getTabs().addAll(studentInfoTab, manageStudentTab);

        VBox root = new VBox(createHeader(getFullName()), tabPane);
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.setMaximized(true); 
        stage.show();

        loadStudentData();
    }

    private VBox createStudentInfoTab() {
    VBox studentInfoBox = new VBox(10);
    studentInfoBox.setPadding(new Insets(10));
    studentInfoBox.setPrefWidth(300);
    VBox.setVgrow(studentInfoBox, Priority.ALWAYS);
    
    studentInfoBox.setMaxWidth(800); 
    studentInfoBox.setAlignment(Pos.CENTER);
    studentInfoBox.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #d1d1d1; -fx-border-width: 0 1px 1px 1px; -fx-padding: 20px;");

    GridPane form = new GridPane();
    form.setHgap(10);
    form.setVgap(10);
    form.setPadding(new Insets(0, 0, 10, 0));

    lastNameField = new TextField();
    firstNameField = new TextField();
    middleNameField = new TextField();
    lrnField = new TextField();
    contactNumberField = new TextField();
    emailField = new TextField();
    ageField = new TextField();
    gradeLevelField = new TextField();
    sectionField = new TextField();
    strandField = new TextField();
    

    form.add(new Label("Last Name:"), 0, 0);
    form.add(lastNameField, 1, 0);
    form.add(new Label("First Name:"), 0, 1);
    form.add(firstNameField, 1, 1);
    form.add(new Label("Middle Name:"), 0, 2);
    form.add(middleNameField, 1, 2);
    form.add(new Label("LRN:"), 0, 3);
    form.add(lrnField, 1, 3);
    form.add(new Label("Contact Number:"), 0, 4);
    form.add(contactNumberField, 1, 4);
    form.add(new Label("Email:"), 0, 5);
    form.add(emailField, 1, 5);
    form.add(new Label("Age:"), 0, 6);
    form.add(ageField, 1, 6);
    form.add(new Label("Grade:"), 0, 7);
    form.add(gradeLevelField, 1, 7);
    form.add(new Label("Section:"), 0, 8);
    form.add(sectionField, 1, 8);
    form.add(new Label("Strand:"), 0, 9);
    form.add(strandField, 1, 9);
    
    GridPane.setHgrow(lastNameField, Priority.ALWAYS);
    GridPane.setHgrow(firstNameField, Priority.ALWAYS);
    GridPane.setHgrow(middleNameField, Priority.ALWAYS);
    GridPane.setHgrow(lrnField, Priority.ALWAYS);
    GridPane.setHgrow(contactNumberField, Priority.ALWAYS);
    GridPane.setHgrow(emailField, Priority.ALWAYS);
    GridPane.setHgrow(ageField, Priority.ALWAYS);
    GridPane.setHgrow(gradeLevelField, Priority.ALWAYS);
    GridPane.setHgrow(sectionField, Priority.ALWAYS);
    GridPane.setHgrow(strandField, Priority.ALWAYS);

    Button saveButton = new Button("Save");
    Button clearButton = new Button("Clear");

    saveButton.setOnAction(e -> saveStudentInfo(lastNameField, firstNameField, middleNameField, lrnField, contactNumberField, emailField, ageField, gradeLevelField, sectionField, strandField));
    clearButton.setOnAction(e -> clearFields(lastNameField, firstNameField, middleNameField, lrnField, contactNumberField, emailField, ageField, gradeLevelField, sectionField, strandField));

    HBox buttonBox = new HBox(10, saveButton, clearButton);
    buttonBox.setAlignment(Pos.CENTER_LEFT);
    buttonBox.setPadding(new Insets(0));
    HBox.setHgrow(saveButton, Priority.ALWAYS);
    HBox.setHgrow(clearButton, Priority.ALWAYS);
    saveButton.setMaxWidth(Double.MAX_VALUE);;
    clearButton.setMaxWidth(Double.MAX_VALUE);

    studentInfoBox.getChildren().addAll(form, buttonBox);
    VBox.setVgrow(studentInfoBox, Priority.ALWAYS);

    return studentInfoBox;
}

    private HBox createManageStudentTab() {
    VBox manageInfoBox = new VBox(10);
    manageInfoBox.setPadding(new Insets(10));
    manageInfoBox.setPrefWidth(300);
    VBox.setVgrow(manageInfoBox, Priority.ALWAYS);

    TextField editLastNameField = new TextField();
    TextField editFirstNameField = new TextField();
    TextField editMiddleNameField = new TextField();
    TextField editLRNField = new TextField();
    TextField editContactNumberField = new TextField();
    TextField editEmailField = new TextField();
    TextField editAgeField = new TextField();
    TextField editGradeLevelField = new TextField();
    TextField editSectionField = new TextField();
    TextField editStrandField = new TextField();
    
    editLastNameField.setMaxWidth(Double.MAX_VALUE);
    editFirstNameField.setMaxWidth(Double.MAX_VALUE);
    editMiddleNameField.setMaxWidth(Double.MAX_VALUE);
    editLRNField.setMaxWidth(Double.MAX_VALUE);
    editContactNumberField.setMaxWidth(Double.MAX_VALUE);
    editEmailField.setMaxWidth(Double.MAX_VALUE);
    editAgeField.setMaxWidth(Double.MAX_VALUE);
    editGradeLevelField.setMaxWidth(Double.MAX_VALUE);
    editSectionField.setMaxWidth(Double.MAX_VALUE);
    editStrandField.setMaxWidth(Double.MAX_VALUE);

    GridPane editForm = new GridPane();
    editForm.setHgap(10);
    editForm.setVgap(10);
    editForm.setPadding(new Insets(0, 0, 0, 0));
    GridPane.setHgrow(editLastNameField, Priority.ALWAYS);
    GridPane.setHgrow(editFirstNameField, Priority.ALWAYS);
    GridPane.setHgrow(editMiddleNameField, Priority.ALWAYS);
    GridPane.setHgrow(editLRNField, Priority.ALWAYS);
    GridPane.setHgrow(editContactNumberField, Priority.ALWAYS);
    GridPane.setHgrow(editEmailField, Priority.ALWAYS);
    GridPane.setHgrow(editAgeField, Priority.ALWAYS);
    GridPane.setHgrow(editGradeLevelField, Priority.ALWAYS);
    GridPane.setHgrow(editSectionField, Priority.ALWAYS);
    GridPane.setHgrow(editStrandField, Priority.ALWAYS);

    editForm.add(new Label("Last Name:"), 0, 0);
    editForm.add(editLastNameField, 1, 0);
    editForm.add(new Label("First Name:"), 0, 1);
    editForm.add(editFirstNameField, 1, 1);
    editForm.add(new Label("Middle Name:"), 0, 2);
    editForm.add(editMiddleNameField, 1, 2);
    editForm.add(new Label("LRN:"), 0, 3);
    editForm.add(editLRNField, 1, 3);
    editForm.add(new Label("Contact Number:"), 0, 4);
    editForm.add(editContactNumberField, 1, 4);
    editForm.add(new Label("Email:"), 0, 5);
    editForm.add(editEmailField, 1, 5);
    editForm.add(new Label("Age:"), 0, 6);
    editForm.add(editAgeField, 1, 6);
    editForm.add(new Label("Grade:"), 0, 7);
    editForm.add(editGradeLevelField, 1, 7);
    editForm.add(new Label("Section:"), 0, 8);
    editForm.add(editSectionField, 1, 8);
    editForm.add(new Label("Strand:"), 0, 9);
    editForm.add(editStrandField, 1, 9);

    Button updateButton = new Button("Update");
    Button deleteButton = new Button("Delete");
    Button clearButton = new Button("Clear");
    
    manageInfoBox.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #d1d1d1; -fx-border-width: 0 1px 1px 1px; -fx-padding: 30px 20px 20px 20px; -fx-min-height: 300px; -fx-max-height: 447px;");

    updateButton.setOnAction(e -> updateStudent(editLastNameField, editFirstNameField, editMiddleNameField, editLRNField, editContactNumberField, editEmailField, editAgeField, editGradeLevelField, editSectionField, editStrandField));
    deleteButton.setOnAction(e -> deleteStudent(editLastNameField.getText()));
    clearButton.setOnAction(e -> clearEditFields(editLastNameField, editFirstNameField, editMiddleNameField, editLRNField, editContactNumberField, editEmailField, editAgeField, editGradeLevelField, editSectionField, editStrandField));

    HBox buttonBox = new HBox(10, updateButton, deleteButton, clearButton);
    buttonBox.setAlignment(Pos.CENTER_LEFT);
    buttonBox.setPadding(new Insets(0));
    HBox.setHgrow(updateButton, Priority.ALWAYS);
    HBox.setHgrow(deleteButton, Priority.ALWAYS);
    HBox.setHgrow(clearButton, Priority.ALWAYS);
    updateButton.setMaxWidth(Double.MAX_VALUE);
    deleteButton.setMaxWidth(Double.MAX_VALUE);
    clearButton.setMaxWidth(Double.MAX_VALUE);

    manageInfoBox.getChildren().addAll(editForm, buttonBox);
    VBox.setVgrow(manageInfoBox, Priority.ALWAYS);

    VBox studentListBox = new VBox(10);
    studentListBox.setPadding(new Insets(10));
    VBox.setVgrow(studentListBox, Priority.ALWAYS);

    searchField = new TextField();
    searchField.setPromptText("Search by name...");
    searchField.setMaxWidth(Double.MAX_VALUE);
    Button searchButton = new Button("Search");
    searchButton.setOnAction(e -> searchStudent());

    HBox searchBox = new HBox(10, searchField, searchButton);
    searchBox.setAlignment(Pos.CENTER_LEFT);
    HBox.setHgrow(searchField, Priority.ALWAYS);

    studentTable = new TableView<>();
    studentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 
    TableColumn<Student, String> lastNameCol = new TableColumn<>("Last Name");
    TableColumn<Student, String> firstNameCol = new TableColumn<>("First Name");
    TableColumn<Student, String> middleNameCol = new TableColumn<>("Middle Name");
    TableColumn<Student, String> lrnCol = new TableColumn<>("LRN");
    TableColumn<Student, String> contactCol = new TableColumn<>("Contact Number");
    TableColumn<Student, String> emailCol = new TableColumn<>("Email");
    TableColumn<Student, String> ageCol = new TableColumn<>("Age");
    TableColumn<Student, String> gradeCol = new TableColumn<>("Grade");
    TableColumn<Student, String> sectionCol = new TableColumn<>("Section");
    TableColumn<Student, String> strandCol = new TableColumn<>("Strand");

    lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    middleNameCol.setCellValueFactory(new PropertyValueFactory<>("middleName"));
    lrnCol.setCellValueFactory(new PropertyValueFactory<>("lrn"));
    contactCol.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
    emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
    gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
    sectionCol.setCellValueFactory(new PropertyValueFactory<>("section"));
    strandCol.setCellValueFactory(new PropertyValueFactory<>("strand"));

    studentTable.getColumns().addAll(lastNameCol, firstNameCol, middleNameCol, lrnCol, contactCol, emailCol, ageCol, gradeCol, sectionCol, strandCol);
    studentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    studentTable.setItems(studentList);

    studentTable.setOnMouseClicked(e -> {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            editLastNameField.setText(selected.getLastName());
            editFirstNameField.setText(selected.getFirstName());
            editMiddleNameField.setText(selected.getMiddleName());
            editLRNField.setText(selected.getLRN());
            editContactNumberField.setText(selected.getContactNumber());
            editEmailField.setText(selected.getEmail());
            editAgeField.setText(selected.getAge());
            editGradeLevelField.setText(selected.getGrade());
            editSectionField.setText(selected.getSection());
            editStrandField.setText(selected.getStrand());
            loadBorrowedBooks(selected.getLRN());
        }
    });

    studentListBox.getChildren().addAll(searchBox, studentTable);

    HBox manageStudentsLayout = new HBox(20, manageInfoBox, studentListBox);
    HBox.setHgrow(manageInfoBox, Priority.ALWAYS);
    HBox.setHgrow(studentListBox, Priority.ALWAYS);
    
    VBox borrowedBookForm = createBorrowedBookForm();
    studentListBox.getChildren().add(borrowedBookForm);
    
    TableColumn<BorrowedBook, String> bookNameCol = new TableColumn<>("Book Name");
    bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));

    TableColumn<BorrowedBook, String> dateBorrowedCol = new TableColumn<>("Date Borrowed");
    dateBorrowedCol.setCellValueFactory(new PropertyValueFactory<>("dateBorrowed"));

    TableColumn<BorrowedBook, String> statusCol = new TableColumn<>("Status");
    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    borrowedBookTable.getColumns().addAll(bookNameCol, dateBorrowedCol, statusCol);
    borrowedBookTable.setItems(borrowedBookList);
    
    borrowedBookTable.setOnMouseClicked(e -> {
    BorrowedBook selected = borrowedBookTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
        bookNameField.setText(selected.getBookName());
        if (selected.getDateBorrowed() != null && !selected.getDateBorrowed().isEmpty()) {
            dateBorrowedField.setValue(java.time.LocalDate.parse(selected.getDateBorrowed()));
        }
        statusField.setValue(selected.getStatus());
    }
});

    
    

    VBox borrowedBookBox = new VBox(10, new Label("Borrowed Books"), borrowedBookTable);
    
    studentListBox.getChildren().add(borrowedBookBox);

    
    return manageStudentsLayout;
}
    
    private VBox createBorrowedBookForm() {
    VBox borrowedBookBox = new VBox(10);
    borrowedBookBox.setPadding(new Insets(10));
    borrowedBookBox.setStyle("-fx-background-color: #f5f5f5;");

    bookNameField = new TextField();
    dateBorrowedField = new DatePicker();
    statusField = new ComboBox<>();
    statusField.getItems().addAll("Returned", "Not Returned");

    bookNameField.setPromptText("Enter Borrowed Book Name");
    dateBorrowedField.setPromptText("Date Borrowed");
    statusField.setPromptText("Status");
    statusField.setValue("Returned"); // Set default value initially

    Button saveBookButton = new Button("Insert");
    Button updateBookButton = new Button("Update");
    Button deleteBookButton = new Button("Delete");
    Button cancelButton = new Button("Cancel");

    // Initially disable update, delete, and cancel buttons
    updateBookButton.setDisable(true);
    deleteBookButton.setDisable(true);
    cancelButton.setDisable(true);

    borrowedBookTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            bookNameField.setText(newSelection.getBookName());
            if (newSelection.getDateBorrowed() != null && !newSelection.getDateBorrowed().isEmpty()) {
                dateBorrowedField.setValue(java.time.LocalDate.parse(newSelection.getDateBorrowed()));
            }
            statusField.setValue(newSelection.getStatus());
            saveBookButton.setDisable(true);
            updateBookButton.setDisable(false);
            deleteBookButton.setDisable(false);
            cancelButton.setDisable(false);
        }
    });

    studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            borrowedBookTable.getSelectionModel().clearSelection();
            clearBorrowedBookFields();
            saveBookButton.setDisable(false);
            updateBookButton.setDisable(true);
            deleteBookButton.setDisable(true);
            cancelButton.setDisable(true);
            statusField.setValue("Returned"); // Reset to default value when switching students
        }
    });

    cancelButton.setOnAction(e -> {
        borrowedBookTable.getSelectionModel().clearSelection();
        clearBorrowedBookFields();
        saveBookButton.setDisable(false);
        updateBookButton.setDisable(true);
        deleteBookButton.setDisable(true);
        cancelButton.setDisable(true);
        statusField.setValue("Returned"); // Reset to default value
    });

    saveBookButton.setOnAction(e -> {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a student first!");
            return;
        }
        if (bookNameField.getText().isEmpty() || dateBorrowedField.getValue() == null || statusField.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "All fields are required!");
            return;
        }
        insertBorrowedBook(selected.getLRN(), bookNameField.getText(), dateBorrowedField.getValue().toString(), statusField.getValue());
        clearBorrowedBookFields();
        saveBookButton.setDisable(false);
        statusField.setValue("Returned"); // Reset to default value
    });

    updateBookButton.setOnAction(e -> {
        BorrowedBook selected = borrowedBookTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            updateBorrowedBook(selected, bookNameField.getText(), dateBorrowedField.getValue().toString(), statusField.getValue());
            clearBorrowedBookFields();
            saveBookButton.setDisable(false);
            updateBookButton.setDisable(true);
            deleteBookButton.setDisable(true);
            cancelButton.setDisable(true);
            statusField.setValue("Returned"); // Reset to default value
        }
    });

    deleteBookButton.setOnAction(e -> {
        BorrowedBook selected = borrowedBookTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            deleteBorrowedBook(selected);
            clearBorrowedBookFields();
            saveBookButton.setDisable(false);
            updateBookButton.setDisable(true);
            deleteBookButton.setDisable(true);
            cancelButton.setDisable(true);
            statusField.setValue("Returned"); // Reset to default value
        }
    });

    HBox buttonBox = new HBox(10, saveBookButton, updateBookButton, deleteBookButton, cancelButton);
    buttonBox.setAlignment(Pos.CENTER_LEFT);
    buttonBox.setPadding(new Insets(10, 0, 0, 0));

    borrowedBookBox.getChildren().addAll(
            new Label("Borrowed Book"),
            bookNameField,
            dateBorrowedField,
            statusField,
            buttonBox);

    return borrowedBookBox;
}

    private String getFullName() {
    String fullName = "";
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT full_name FROM users WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            fullName = rs.getString("full_name");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return fullName;
}

    private VBox createHeader(String fullName) {
    // Logo
    Image image = new Image(getClass().getResourceAsStream("/resources/images/logo.png"));
    ImageView logo = new ImageView(image);
    logo.setFitHeight(100); // Set height
    logo.setPreserveRatio(true); // Preserve image aspect ratio

    // Title Label
    Label systemLabel = new Label("STUDENT INFORMATION SYSTEM");
    systemLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

    // Combine Logo and System Label in one HBox
    HBox logoAndTitle = new HBox(10, logo, systemLabel);
    logoAndTitle.setAlignment(Pos.CENTER_LEFT);
    logoAndTitle.setSpacing(10);

    // Welcome Label
    Label welcomeLabel = new Label("Welcome, " + fullName);
    welcomeLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: blue;");

    // Logout Button
    Button logoutButton = new Button("Logout");
    logoutButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
    logoutButton.setOnAction(e -> {
        new studentinfosystem.controllers.Login().start(new Stage()); // Redirect to Login Page
        ((Stage) logoutButton.getScene().getWindow()).close(); // Close current window
    });

    // Header Layout
    HBox header = new HBox(10);
    header.setPadding(new Insets(10));
    header.setStyle("-fx-background-color: #f1f1f1;");
    header.setSpacing(10);
    header.setAlignment(Pos.CENTER_LEFT);

    VBox leftSide = new VBox(logoAndTitle, welcomeLabel);
    leftSide.setSpacing(5);
    leftSide.setAlignment(Pos.CENTER_LEFT);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    header.getChildren().addAll(leftSide, spacer, logoutButton);

    VBox headerBox = new VBox();
    headerBox.getChildren().addAll(header);
    headerBox.setAlignment(Pos.CENTER_LEFT);
    headerBox.setSpacing(5);
    headerBox.setPadding(new Insets(10));
    headerBox.setStyle("-fx-background-color: #f1f1f1;");

    return headerBox;
}

    private void saveStudentInfo(TextField lastNameField, TextField firstNameField, TextField middleNameField, TextField lrnField, TextField contactNumberField, TextField emailField, TextField ageField, TextField gradeLevelField, TextField sectionField, TextField strandField) {
    String lastName = lastNameField.getText();
    String firstName = firstNameField.getText();
    String middleName = middleNameField.getText();
    String lrn = lrnField.getText();
    String contactNumber = contactNumberField.getText();
    String email = emailField.getText();
    String age = ageField.getText();
    String gradeLevel = gradeLevelField.getText();
    String section = sectionField.getText();
    String strand = strandField.getText();

    if (lastName.isEmpty() || firstName.isEmpty() || middleName.isEmpty() || lrn.isEmpty() || contactNumber.isEmpty() || email.isEmpty() || age.isEmpty() || gradeLevel.isEmpty() || section.isEmpty() || strand.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "All fields must be filled!");
        return;
    }

    try (Connection conn = DBConnection.getConnection()) {
        String sql = "INSERT INTO students (last_name, first_name, middle_name, lrn, contact_number, email, age, grade_level, section, strand, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, lastName);
        pstmt.setString(2, firstName);
        pstmt.setString(3, middleName);
        pstmt.setString(4, lrn);
        pstmt.setString(5, contactNumber);
        pstmt.setString(6, email);
        pstmt.setString(7, age);
        pstmt.setString(8, gradeLevel);
        pstmt.setString(9, section);
        pstmt.setString(10, strand);
        pstmt.setInt(11, userId); // Save user_id into the database
        pstmt.executeUpdate();

        showAlert(Alert.AlertType.INFORMATION, "Student info saved successfully!");
        loadStudentData(); // Refresh table after saving
        clearFields(lastNameField, firstNameField, middleNameField, lrnField, contactNumberField, emailField, ageField, gradeLevelField, sectionField, strandField);
    } catch (SQLException ex) {
        showAlert(Alert.AlertType.ERROR, "Error saving student info: " + ex.getMessage());
    }
}

    private void loadStudentData() {
    studentList.clear(); // Clear previous data before fetching
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT * FROM students WHERE user_id = ?"; // Filter based on user
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userId); // Attach userId to the query
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            studentList.add(new Student(
                    rs.getString("last_name"),
                    rs.getString("first_name"),
                    rs.getString("middle_name"),
                    rs.getString("lrn"),
                    rs.getString("contact_number"),
                    rs.getString("email"),
                    rs.getString("age"),
                    rs.getString("grade_level"),
                    rs.getString("section"),
                    rs.getString("strand")
            ));
        }
    } catch (SQLException ex) {
        showAlert(Alert.AlertType.ERROR, "Error loading students: " + ex.getMessage());
    }
}


    private void searchStudent() {
    String searchText = searchField.getText().toLowerCase();
    studentList.clear();

    try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT * FROM students WHERE (LOWER(last_name) LIKE ? OR LOWER(first_name) LIKE ?) AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + searchText + "%");
        pstmt.setString(2, "%" + searchText + "%");
        pstmt.setInt(3, userId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            studentList.add(new Student(
                    rs.getString("last_name"),
                    rs.getString("first_name"),
                    rs.getString("middle_name"),
                    rs.getString("lrn"),
                    rs.getString("contact_number"),
                    rs.getString("email"),
                    rs.getString("age"),
                    rs.getString("grade_level"),
                    rs.getString("section"),
                    rs.getString("strand")
            ));
        }
    } catch (SQLException ex) {
        showAlert(Alert.AlertType.ERROR, "Error searching students: " + ex.getMessage());
    }
}

    private void clearFields(TextField lastNameField1, TextField firstNameField1, TextField middleNameField, TextField lrnField, TextField contactNumberField, TextField emailField, TextField ageField, TextField gradeLevelField1, TextField sectionField1, TextField strandField) {
    lastNameField.clear();
    firstNameField.clear();
    middleNameField.clear();
    lrnField.clear();
    contactNumberField.clear();
    emailField.clear();
    ageField.clear();
    gradeLevelField.clear();
    sectionField.clear();
    strandField.clear();
}

    private void showAlert(Alert.AlertType alertType, String message) {
    Alert alert = new Alert(alertType);
    alert.setContentText(message);
    alert.show();
}

    private void updateStudent(TextField lastNameField, TextField firstNameField, TextField middleNameField, TextField lrnField, TextField contactNumberField, TextField emailField, TextField ageField, TextField gradeLevelField, TextField sectionField, TextField strandField) {
    Student selected = studentTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
        showAlert(Alert.AlertType.WARNING, "No student selected!");
        return;
    }

    try (Connection conn = DBConnection.getConnection()) {
        String sql = "UPDATE students SET last_name = ?, first_name = ?, middle_name = ?, lrn = ?, contact_number = ?, email = ?, age = ?, grade_level = ?, section = ?, strand = ? WHERE last_name = ? AND first_name = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, lastNameField.getText());
        pstmt.setString(2, firstNameField.getText());
        pstmt.setString(3, middleNameField.getText());
        pstmt.setString(4, lrnField.getText());
        pstmt.setString(5, contactNumberField.getText());
        pstmt.setString(6, emailField.getText());
        pstmt.setString(7, ageField.getText());
        pstmt.setString(8, gradeLevelField.getText());
        pstmt.setString(9, sectionField.getText());
        pstmt.setString(10, strandField.getText());
        pstmt.setString(11, selected.getLastName());
        pstmt.setString(12, selected.getFirstName());
        pstmt.setInt(13, userId); // Update only if the user_id matches
        pstmt.executeUpdate();

        showAlert(Alert.AlertType.INFORMATION, "Student updated successfully!");
        loadStudentData(); // Refresh table after updating
    } catch (SQLException ex) {
        showAlert(Alert.AlertType.ERROR, "Error updating student: " + ex.getMessage());
    }
}

    private void deleteStudent(String lastName) {
    Student selected = studentTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
        showAlert(Alert.AlertType.WARNING, "No student selected!");
        return;
    }

    try (Connection conn = DBConnection.getConnection()) {
        String sql = "DELETE FROM students WHERE last_name = ? AND first_name = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, selected.getLastName());
        pstmt.setString(2, selected.getFirstName());
        pstmt.setInt(3, userId); // Only delete if it matches logged-in user
        pstmt.executeUpdate();

        showAlert(Alert.AlertType.INFORMATION, "Student deleted successfully!");
        loadStudentData();
    } catch (SQLException ex) {
        showAlert(Alert.AlertType.ERROR, "Error deleting student: " + ex.getMessage());
    }
}

    private void clearEditFields(TextField lastName, TextField firstName, TextField middleName, TextField lrn, TextField contactNumber, TextField email, TextField age, TextField gradeLevel, TextField section, TextField strand) {
    lastName.clear();
    firstName.clear();
    middleName.clear();
    lrn.clear();
    contactNumber.clear();
    email.clear();
    age.clear();
    gradeLevel.clear();
    section.clear();
    strand.clear();
}
    
    private void insertBorrowedBook(String studentLRN, String bookName, String dateBorrowed, String status) {
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "INSERT INTO borrowed_books (student_lrn, book_name, date_borrowed, status, user_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, studentLRN);
        pstmt.setString(2, bookName);
        pstmt.setString(3, dateBorrowed);
        pstmt.setString(4, status);
        pstmt.setInt(5, userId);
        pstmt.executeUpdate();

        showAlert(Alert.AlertType.INFORMATION, "Borrowed Book Inserted Successfully!");
        loadBorrowedBooks(studentLRN);
    } catch (SQLException ex) {
        showAlert(Alert.AlertType.ERROR, "Error inserting borrowed book: " + ex.getMessage());
    }
}

    private void loadBorrowedBooks(String studentLRN) {
    borrowedBookList.clear();
    try (Connection conn = DBConnection.getConnection()) {
     String sql = "SELECT book_name, date_borrowed, status, student_lrn FROM borrowed_books WHERE student_lrn = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, studentLRN);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            borrowedBookList.add(new BorrowedBook(
                    rs.getString("book_name"),
                    rs.getString("date_borrowed"),
                    rs.getString("status"),
                    rs.getString("student_lrn")
            ));
        }
        borrowedBookTable.setItems(borrowedBookList);
    } catch (SQLException ex) {
        showAlert(Alert.AlertType.ERROR, "Error loading borrowed books: " + ex.getMessage());
    }
}

    private void updateBorrowedBook(BorrowedBook selected, String bookName, String dateBorrowed, String status) {
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "UPDATE borrowed_books SET book_name = ?, date_borrowed = ?, status = ? WHERE book_name = ? AND student_lrn = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, bookName);
        pstmt.setString(2, dateBorrowed);
        pstmt.setString(3, status);
        pstmt.setString(4, selected.getBookName());
        pstmt.setString(5, selected.getStudentLRN());
        pstmt.executeUpdate();

        showAlert(Alert.AlertType.INFORMATION, "Borrowed Book Updated Successfully!");
        loadBorrowedBooks(selected.getStudentLRN());
    } catch (SQLException ex) {
        showAlert(Alert.AlertType.ERROR, "Error updating borrowed book: " + ex.getMessage());
    }
}

private void deleteBorrowedBook(BorrowedBook selected) {
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "DELETE FROM borrowed_books WHERE book_name = ? AND student_lrn = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, selected.getBookName());
        pstmt.setString(2, selected.getStudentLRN());
        pstmt.executeUpdate();

        showAlert(Alert.AlertType.INFORMATION, "Borrowed Book Deleted Successfully!");
        loadBorrowedBooks(selected.getStudentLRN());
    } catch (SQLException ex) {
        showAlert(Alert.AlertType.ERROR, "Error deleting borrowed book: " + ex.getMessage());
    }
}
    
private void clearBorrowedBookFields() {
    bookNameField.clear();
    dateBorrowedField.setValue(null);
    statusField.getSelectionModel().clearSelection();
}

    
    public static void main(String[] args) {
        launch(args);
    }
}