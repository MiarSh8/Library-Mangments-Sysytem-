//محاضرة  7
// ميار حازم محمد شحادة
//220222624
//admin الاسم
//admin123 الباسوورد
package FxlLibraryMangmentsSysytem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class FxlLibraryMangmentsSysytem extends Application {

    Stage LoginStage = new Stage();
    Stage RegisterStage = new Stage();
    Stage AdminStage = new Stage();
    Stage showAdminDasboard = new Stage();
    ObservableList<User> Users = FXCollections.observableArrayList();
    ListView<String> ListViewUsersData = new ListView<>();
    Stage ProfileDashboardStage = new Stage();
    Stage showAdminDashboardBookManagementStage = new Stage();
    Stage categoryManagementStage = new Stage();
    String imageName = null;
    Image[] profileImage = {null};
    private ObservableList<Book> Books = FXCollections.observableArrayList();
    private ObservableList<String> Categories = FXCollections.observableArrayList("Sciences", "mathematics", "IT", "Etiquette");
    public User currentUser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ShowLoginStage();
    }

    //LoginStage---------------------------------
    public void ShowLoginStage() {
        //imag-----------------------------------------
        Users.add(new User("Miar", "admin", "admin123", "miar@gmail.com", "0597310435", "Admin", "/images/main.png"));
        Image LoginImage = new Image(FxlLibraryMangmentsSysytem.class.getResourceAsStream("/images/main.png"));
        ImageView LoginImageView = new ImageView(LoginImage);
        LoginImageView.setFitWidth(120);
        LoginImageView.setFitHeight(120);

        //user name --------------------------------------
        Label UserNameLabel = new Label("UserName:");
        TextField UserNameTf = new TextField();
        Label UserNameLabelError = new Label("");

        // Password----------------------------------------
        Label PasswordLabel = new Label("Password:");
        TextField PasswordTf = new TextField();
        Label PasswordLabelError = new Label("");

        // Buttons----------------------------------
        Button BtnLogin = new Button("Login");
        Button RegisterLogin = new Button("Register");
        BtnLogin.setOnAction(e -> {
            UserNameLabelError.setText("");
            PasswordLabelError.setText("");

            String username = UserNameTf.getText();
            String Password = PasswordTf.getText();
            User user = validateUser(username, Password);
            if (user == null) {
                if (username.isEmpty()) {
                    UserNameLabelError.setText("UserName is Required");
                } else if (Password.isEmpty()) {
                    PasswordLabelError.setText("Password is Required");
                } else {
                    UserNameLabelError.setText("Invalid UserName or Password");
                }
            } else {

                if (user.getRole().equals("Admin")) {
                    showAdminDasboard();
                    System.out.println("Admin Dashboard");

                } else if (user.getRole().equals("Librarian")) {
                    System.out.println("Librarian Dashboard");
                } else {

                }

            }
        });

        RegisterLogin.setOnAction(e -> {
            ShowRegisesrarStage();

        });

// Containers-------------------------------
        VBox UsernameVb = new VBox(UserNameLabel, UserNameTf, UserNameLabelError);
        VBox PasswordVb = new VBox(PasswordLabel, PasswordTf, PasswordLabelError); // Corrected variable name to "passwordTf"
        HBox btnhb = new HBox(10, BtnLogin, RegisterLogin);
        VBox MainVb = new VBox(10, LoginImageView, UsernameVb, PasswordVb, btnhb);

        btnhb.setAlignment(Pos.CENTER);

        MainVb.setAlignment(Pos.CENTER);

        MainVb.setPadding(
                new Insets(15));
        Scene sc = new Scene(MainVb);

        SetStageData(LoginStage, sc, "Logo.png", "UserLogin", "Style/LoginStyle.css", 400, 500, 700, 250);
        ShowMyStage(LoginStage);

    }
// RegisterStage-----------------------------------

    public void ShowRegisesrarStage() {

        Label fullNameLabel = new Label("Full Name:");
        TextField fullNameTf = new TextField();
        Label fullNameLabelError = new Label("");

        Label usernameLabel = new Label("Username:");
        TextField usernameTf = new TextField();
        Label usernameLabelError = new Label("");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordTf = new PasswordField();
        Label passwordLabelError = new Label("");

        Label emailLabel = new Label("Email:");
        TextField emailTf = new TextField();
        Label emailLabelError = new Label("");

        Label phoneLabel = new Label("Phone:");
        TextField phoneTf = new TextField();
        Label phoneLabelError = new Label("");

        Label profilePictureLabel = new Label("UserImage:");
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(FxlLibraryMangmentsSysytem.class.getResourceAsStream("/images/main.png")));
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        Label profilePictureLabelError = new Label("");

        Label roleLabel = new Label("Role:");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("User", "Librarian", "Admin");
        roleComboBox.setValue("User");
        roleComboBox.setDisable(true);
        Label roleLabelError = new Label("");

        Button registerButton = new Button("Register");
        Button cancelButton = new Button("Cancel");

//Image selelect--------------------------------------------------------
        Image[] profileImage = {null};
        imageView.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(RegisterStage);
            if (file != null) {
                profileImage[0] = new Image(file.toURI().toString());
                imageView.setImage(profileImage[0]);
                this.imageName = "/images/" + file.getName();
                try {
                    saveImage(profileImage[0], file.getName());
                } catch (IOException ex) {
                    Logger.getLogger(FxlLibraryMangmentsSysytem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//registerButton-------------------------------------------
        registerButton.setOnAction(e -> {
            fullNameLabelError.setText("");
            usernameLabelError.setText("");
            passwordLabelError.setText("");
            emailLabelError.setText("");
            phoneLabelError.setText("");

            boolean hasError = false;

            if (fullNameTf.getText().isEmpty()) {
                fullNameLabelError.setText("Full Name is required.");
                hasError = true;
            }
            if (usernameTf.getText().isEmpty()) {
                usernameLabelError.setText("UserName is required.");
                hasError = true;
            }
            if (passwordTf.getText().isEmpty()) {
                passwordLabelError.setText("Password is required.");
                hasError = true;
            }
            if (emailTf.getText().isEmpty()) {
                emailLabelError.setText("Email is required.");
                hasError = true;

            }
            if (phoneTf.getText().isEmpty()) {
                phoneLabelError.setText("Phone is required.");
                hasError = true;
            }
            if (this.imageName.isEmpty()) {
                profilePictureLabelError.setText("Image is required.");
                hasError = true;
            }
            if (!hasError) {

                boolean isFoundUser = UserExist(usernameTf.getText(), passwordTf.getText());
                if (!isFoundUser) {

                    User newUser = new User(fullNameTf.getText(), usernameTf.getText(), passwordTf.getText(), emailTf.getText(), phoneTf.getText(), roleComboBox.getValue(), this.imageName.toString());
                    Users.add(newUser);
//Alert-----------------------------
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "User has been registered.");
                    alert.showAndWait();

//clear input---------------------------- 
                    fullNameTf.clear();
                    usernameTf.clear();
                    passwordTf.clear();
                    phoneTf.clear();
                    emailTf.clear();
                    this.imageName = null;

                    ShowMyStage(LoginStage);
                } else {
                    usernameLabelError.setText("User already exists with this username and password.");
                }
            }
            ShowMyStage(LoginStage);

        });
//cancelButton-----------------------------
        cancelButton.setOnAction(e -> {

            ShowMyStage(LoginStage);

        });

        VBox mainVb = new VBox(15);
        mainVb.setAlignment(Pos.CENTER);
        mainVb.setPadding(new Insets(20));
        // VBoxes for each section---------------------------------
        VBox imageVb = new VBox(2, profilePictureLabel, imageView, profilePictureLabelError);
        VBox fullNameVb = new VBox(2, fullNameLabel, fullNameTf, fullNameLabelError);
        VBox roleVb = new VBox(2, roleLabel, roleComboBox, roleLabelError);
        VBox userNameVb = new VBox(2, usernameLabel, usernameTf, usernameLabelError);
        VBox passwordVb = new VBox(2, passwordLabel, passwordTf, passwordLabelError);
        VBox emailVb = new VBox(2, emailLabel, emailTf, emailLabelError);
        VBox phoneVb = new VBox(2, phoneLabel, phoneTf, phoneLabelError);

        HBox layer1 = new HBox(15, fullNameVb, roleVb);
        HBox layer2 = new HBox(15, userNameVb, passwordVb);
        HBox layer3 = new HBox(15, emailVb, phoneVb);
        HBox layer4 = new HBox(15, registerButton, cancelButton);
        mainVb.getChildren().addAll(imageVb, layer1, layer2, layer3, layer4);

        Scene sc = new Scene(mainVb);
        SetStageData(RegisterStage, sc, "Logo.png", "Register", "Style/Register.css", 500, 600, 700, 250);
        ShowMyStage(RegisterStage);

    }

    public void showAdminDasboard() {
        RefreshListviewUserData();

        Label fullNameLabel = new Label("Full Name:");
        TextField fullNameTf = new TextField();
        Label fullNameLabelError = new Label("");

        Label usernameLabel = new Label("Username:");
        TextField usernameTf = new TextField();
        Label usernameLabelError = new Label("");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordTf = new PasswordField();
        Label passwordLabelError = new Label("");

        Label emailLabel = new Label("Email:");
        TextField emailTf = new TextField();
        Label emailLabelError = new Label("");

        Label phoneLabel = new Label("Phone:");
        TextField phoneTf = new TextField();
        Label phoneLabelError = new Label("");

        Label profilePictureLabel = new Label("UserImage:");
        ImageView imageView = new ImageView();

        imageView.setImage(new Image(FxlLibraryMangmentsSysytem.class.getResourceAsStream("/images/main.png")));
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        Label profilePictureLabelError = new Label("");
        RefreshListviewUserData();

        Label roleLabel = new Label("Role:");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("User", "Librarian", "Admin");
        roleComboBox.setValue("User");
        roleComboBox.setDisable(false);

        Label roleLabelError = new Label("");
        Label userFullNameLabel = new Label("Name:" + currentUser.getFullName());
        Label userManagementLabel = new Label("User Management\n");
        Label bookManagementLabel = new Label("Book Management");
        VBox profileLayout = new VBox(15);
        profileLayout.setPadding(new Insets(20));
        profileLayout.setAlignment(Pos.CENTER);

        TextField profileFullNameTf = new TextField(currentUser.getFullName());
        TextField profileUsernameTf = new TextField(currentUser.getUserName());
        TextField profilePasswordTf = new TextField(currentUser.getPassword());
        TextField profilePhoneTf = new TextField(currentUser.getPhone());
        TextField profileEmailTf = new TextField(currentUser.getEmail());

        VBox tableContainer = new VBox();
        tableContainer.setSpacing(10);
        tableContainer.setPadding(new Insets(10));

        ComboBox<String> profileRoleComboBox = new ComboBox<>();
        profileRoleComboBox.getStyleClass().add("combo-box");
        TableView<User> tableViewUser = new TableView<>();
        tableContainer.getChildren().addAll(profileRoleComboBox, tableViewUser);
        profileRoleComboBox.getItems().addAll("Admin", "Librarian", "User");

        profileRoleComboBox.setValue(currentUser.getRole());
        Button updateProfileButton = new Button("Update");
        Button cancelProfileButton = new Button("Cancel");
        cancelProfileButton.setOnMouseClicked(e -> ShowMyStage(AdminStage));

        profileLayout.getChildren().addAll(profileFullNameTf, profileUsernameTf, profilePasswordTf, profilePhoneTf, profileEmailTf, profileRoleComboBox, updateProfileButton, cancelProfileButton);
        Label userRoleFilterLabel = new Label("UserRoleFilter:");
        ComboBox<String> userRoleComboBox = new ComboBox<>();

        userRoleComboBox.getItems().addAll("All", "Admin", "Librarian", "User");
        userRoleComboBox.setValue("All");
        Button addButton = new Button("Add");
        Button updateButton = new Button("Updat");
        Button deleteButton = new Button("Delete");
        Button cancelButton = new Button("Cancel");

        //Image selelect--------------------------------------
        Image[] profileImage = {null};
        imageView.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(RegisterStage);
            if (file != null) {
                profileImage[0] = new Image(file.toURI().toString());
                imageView.setImage(profileImage[0]);
                this.imageName = "/images/" + file.getName();
                try {
                    saveImage(profileImage[0], file.getName());
                } catch (IOException ex) {
                    Logger.getLogger(FxlLibraryMangmentsSysytem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // RefreshListviewUserData();
        });

//tableView----------------------------------
        tableViewUser.getSelectionModel().selectedItemProperty().addListener(e -> {
            User user = tableViewUser.getSelectionModel().getSelectedItem();

            if (user != null) {

                fullNameTf.setText(user.getFullName());
                usernameTf.setText(user.getUserName());
                passwordTf.setText(user.getPassword());
                phoneTf.setText(user.getPhone());
                emailTf.setText(user.getEmail());
                roleComboBox.setValue(user.getRole());
                this.imageName = user.getProfileImagePath();

                profileImage[0] = new Image(FxlLibraryMangmentsSysytem.class.getResourceAsStream(user.getProfileImagePath()));
                imageView.setImage(profileImage[0]);

            }

        });

        userRoleComboBox.setOnAction(e -> {
            String selectedRole = userRoleComboBox.getValue();
            ObservableList<User> filteredUsers = FXCollections.observableArrayList();
            if (selectedRole.equals("All")) {
                filteredUsers.setAll(Users);
            } else {
                for (User user : Users) {
                    if (user.getRole().equals(selectedRole)) {
                        filteredUsers.add(user);
                    }
                }

            }
            tableViewUser.setItems(filteredUsers);
        });

// Update Profile--------------------------------------------- 
        updateProfileButton.setOnMouseClicked(e -> {
            currentUser.setFullName(profileFullNameTf.getText());
            currentUser.setUserName(profileUsernameTf.getText());
            currentUser.setPassword(profilePasswordTf.getText());
            currentUser.setPhone(profilePhoneTf.getText());
            currentUser.setEmail(profileEmailTf.getText());
            currentUser.setRole(profileRoleComboBox.getValue());
            tableViewUser.refresh();
            ShowMyStage(AdminStage);

        });

        HBox roleFilterHBox = new HBox(20, userRoleFilterLabel, userRoleComboBox);
        roleFilterHBox.setPadding(new Insets(2));
        TableColumn<User, String> fullNameColumn = new TableColumn<>("Full Name");
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        fullNameColumn.setPrefWidth(100);

        TableColumn<User, String> userNameColumn = new TableColumn<>("UserName");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userNameColumn.setPrefWidth(100);

        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setPrefWidth(100);

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setPrefWidth(100);

        TableColumn<User, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneColumn.setPrefWidth(100);

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.setPrefWidth(100);

        TableColumn<User, ImageView> imageViewColumn = new TableColumn<>("User Image");
        imageViewColumn.setPrefWidth(100);
        imageViewColumn.setCellValueFactory(cellData -> {
            ImageView userImage = new ImageView(cellData.getValue().getProfileImagePath());
            userImage.setFitWidth(40);
            userImage.setFitHeight(40);
            return new SimpleObjectProperty<>(userImage);
        });

        // ProfileImage----------------------------------
        BorderPane mainPane = new BorderPane();
        ImageView userProfileImageView = new ImageView();
        URL imageUrl = getClass().getResource("/images/main.png");
        if (imageUrl != null) {
            userProfileImageView.setImage(new Image(imageUrl.toExternalForm()));
        } else {
            System.out.println("Image not found!");

        }

        userProfileImageView.setFitWidth(50);
        userProfileImageView.setFitHeight(50);
        userProfileImageView.setPreserveRatio(true);
        tableViewUser.getColumns().addAll(imageViewColumn, roleColumn, phoneColumn, emailColumn, passwordColumn, userNameColumn, fullNameColumn);
        tableViewUser.setItems(Users);

        VBox sideBar = new VBox();
        sideBar.getStyleClass().add("side-bar");
        tableViewUser.refresh();

        //TabieViewListner-----------------------------------------
        tableViewUser.getSelectionModel().selectedItemProperty().addListener(e -> {
            int index = tableViewUser.getSelectionModel().getSelectedIndex();
            if (index >= 0 && index < Users.size()) {
                User user = Users.get(index);
                fullNameTf.setText(user.getFullName());
                usernameTf.setText(user.getUserName());
                passwordTf.setText(user.getPassword());
                phoneTf.setText(user.getPhone());
                emailTf.setText(user.getEmail());
                roleComboBox.setValue(user.getRole());
                profileImage[0] = new Image(FxlLibraryMangmentsSysytem.class.getResourceAsStream(user.getProfileImagePath()));
                imageView.setImage(profileImage[0]);
            }
        });

        VBox mainVb = new VBox(15);
        mainVb.setAlignment(Pos.CENTER);
        mainVb.setPadding(new Insets(20));

        userRoleComboBox.setOnAction(e -> {
            String selectedRole = userRoleComboBox.getValue();
            ObservableList<User> filteredUsers = FXCollections.observableArrayList();
            if (selectedRole.equals("All")) {
                filteredUsers.setAll(Users);
            } else {
                for (User user : Users) {
                    if (user.getRole().equals(selectedRole)) {
                        filteredUsers.add(user);
                    }
                }
            }
            tableViewUser.setItems(filteredUsers);
        });

//registerButton-------------------------------------------------
        addButton.setOnAction(e -> {

            fullNameLabelError.setText("");
            usernameLabelError.setText("");
            passwordLabelError.setText("");
            emailLabelError.setText("");
            phoneLabelError.setText("");

            boolean hasError = false;
            if (fullNameTf.getText().isEmpty()) {
                fullNameLabelError.setText("Full Name is required.");
                hasError = true;
            }
            if (usernameTf.getText().isEmpty()) {
                usernameLabelError.setText("UserName is required.");
                hasError = true;
            }
            if (passwordTf.getText().isEmpty()) {
                passwordLabelError.setText("Password is required.");
                hasError = true;
            }
            if (emailTf.getText().isEmpty()) {
                emailLabelError.setText("Email is required.");
                hasError = true;
            }
            if (phoneTf.getText().isEmpty()) {
                phoneLabelError.setText("Phone is required.");
                hasError = true;
            }
            if (this.imageName.isEmpty()) {
                profilePictureLabelError.setText("Image is required.");
                hasError = true;
            }
            if (!hasError) {
                boolean isFoundUser = UserExist(usernameTf.getText(), passwordTf.getText());
                if (!isFoundUser) {
                    User newUser = new User(fullNameTf.getText(), usernameTf.getText(), passwordTf.getText(), emailTf.getText(), phoneTf.getText(), roleComboBox.getValue(), this.imageName.toString());
                    Users.add(newUser);
//Alert-------------------
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "User has been registered.");
                    alert.showAndWait();

//clear input -----------------
                    fullNameTf.clear();
                    usernameTf.clear();
                    passwordTf.clear();
                    phoneTf.clear();
                    emailTf.clear();
                    this.imageName = null;
                } else {
                    usernameLabelError.setText("User already exists with this username and password.");
                }
            }
        });
//update user -------------------------------
        updateButton.setOnAction(e -> {
            // User selectedUser = null;
            User selectedUser = tableViewUser.getSelectionModel().getSelectedItem();

            if (selectedUser != null) {
                for (User user : Users) {
                    if (user.getUserName().equals(selectedUser.getUserName())) {
                        selectedUser = user;
                        break;
                    }
                }
                boolean sameUserFound = false;
                for (User user : Users) {
                    if (selectedUser.getUserName().equals(usernameTf.getText())) {
                        continue;
                    }
                    if (user.getUserName().equals(usernameTf.getText())) {
                        sameUserFound = true;
                        break;
                    }
                }
                if (selectedUser != null) {
                    if (!sameUserFound) {
                        selectedUser.setFullName(fullNameTf.getText());
                        selectedUser.setUserName(usernameTf.getText());
                        selectedUser.setPassword(passwordTf.getText());
                        selectedUser.setPhone(phoneTf.getText());
                        selectedUser.setEmail(emailTf.getText());
                        selectedUser.setRole(roleComboBox.getValue());
                        if (this.imageName != null) {
                            selectedUser.setProfileImagePath(profileImage[0].toString().replace("file:", ""));
                        }
                        RefreshListviewUserData();
                        //tableViewUser.refresh();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "User Update.");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "The UserName Is Used By Aonther User.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a user to update.");
                alert.showAndWait();
            }
        });
//deleteButton
        deleteButton.setOnMouseClicked(e -> {
            User selectedUser = tableViewUser.getSelectionModel().getSelectedItem();

            if (selectedUser != null) {
                for (User user : Users) {
                    if (user.getUserName().equals(selectedUser.getUserName())) {
                        Users.remove(user);
                        fullNameTf.clear();
                        usernameTf.clear();
                        passwordTf.clear();
                        phoneTf.clear();
                        emailTf.clear();
                        this.imageName = null;
                        // RefreshListviewUserData();
                        break;
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a user to Delete.");
                alert.showAndWait();
            }
        });
//cancelButton-------------------
        cancelButton.setOnAction(e -> {
            ShowMyStage(LoginStage);
        });

//BorderPane-------------------------------
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.getChildren().addAll(userProfileImageView, userFullNameLabel, userManagementLabel, bookManagementLabel);
        mainPane.setLeft(sidebar);

        userProfileImageView.setOnMouseClicked(e -> ShowMyStage(ProfileDashboardStage));
        userManagementLabel.setOnMouseClicked(e -> ShowMyStage(AdminStage));
        bookManagementLabel.setOnMouseClicked(e -> {
            showAdminDashboardBookManagementStage();
        });

//VBox sideBar = new VBox(10);
        sideBar.getChildren().add(mainPane);
        VBox userForm = new VBox();
        userForm.getStyleClass().add("user-form");
        tableContainer.getStyleClass().add("table-container");

        mainVb.setAlignment(Pos.CENTER);
        mainVb.setPadding(new Insets(20));
        VBox imageVb = new VBox(4, profilePictureLabel, imageView, profilePictureLabelError);
        VBox fullNameVb = new VBox(4, fullNameLabel, fullNameTf, fullNameLabelError);
        VBox roleVb = new VBox(4, roleLabel, roleComboBox, roleLabelError);
        VBox userNameVb = new VBox(4, usernameLabel, usernameTf, usernameLabelError);
        VBox passwordVb = new VBox(4, passwordLabel, passwordTf, passwordLabelError);
        VBox emailVb = new VBox(4, emailLabel, emailTf, emailLabelError);
        VBox phoneVb = new VBox(4, phoneLabel, phoneTf, phoneLabelError);
        HBox layer1 = new HBox(15, fullNameVb, roleVb);
        HBox layer2 = new HBox(15, userNameVb, passwordVb);
        HBox layer3 = new HBox(15, emailVb, phoneVb);
        HBox layer4 = new HBox(15, addButton, updateButton, deleteButton, cancelButton);
        mainVb.getChildren().addAll(roleFilterHBox, imageVb, layer1, layer2, layer3, layer4);
        HBox mainHb = new HBox(15, sideBar, mainVb, tableContainer);
        mainHb.setAlignment(Pos.CENTER);
        mainHb.setPadding(new Insets(100));
        Scene sc = new Scene(mainHb);
        SetStageData(AdminStage, sc, "Logo.png", "AdminDashboard", "Style/AdminDashboard.css", 1600, 700, 500, 250);
        ShowMyStage(AdminStage);
        Scene profileScene = new Scene(profileLayout, 500, 400);

        SetStageData(ProfileDashboardStage, profileScene, "Logo.png", "Profile Dashboard", "Style/ProfileDashboard.css", 500, 500, 400, 250);
        ProfileDashboardStage.setScene(profileScene);
        ProfileDashboardStage.setTitle("Profile Dashboard");

    }

    public void showAdminDashboardBookManagementStage() {
    // Labels---------------------------------
    Label bookCategoryFilterLabel = new Label("Book Category Filter:");
    ComboBox<String> bookCategoryFilterComboBox = new ComboBox<>(Categories);
    bookCategoryFilterComboBox.getItems().add("All");
    bookCategoryFilterComboBox.setValue("All");
    ImageView categoryLogo = new ImageView(new Image(getClass().getResourceAsStream("/Images/book1.png")));

    // Book Table---------------------------------------
    TableView<Book> tableViewBooks = new TableView<>();
    tableViewBooks.setPrefWidth(800);
    tableViewBooks.setPrefHeight(500);

    TableColumn<Book, ImageView> bookImageColumn = new TableColumn<>("Book Image");
    bookImageColumn.setPrefWidth(100);
    bookImageColumn.setCellValueFactory(cellData -> {
        ImageView bookImage = new ImageView(new Image(cellData.getValue().getImage()));
        bookImage.setFitWidth(50);
        bookImage.setFitHeight(50);
        bookImage.setPreserveRatio(true);
        return new SimpleObjectProperty<>(bookImage);
    });

    TableColumn<Book, String> bookTitleColumn = new TableColumn<>("Title");
    bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    bookTitleColumn.setPrefWidth(150);

    TableColumn<Book, String> bookAuthorColumn = new TableColumn<>("Author");
    bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    bookAuthorColumn.setPrefWidth(150);

    TableColumn<Book, String> bookCategoryColumn = new TableColumn<>("Category");
    bookCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    bookCategoryColumn.setPrefWidth(100);

    TableColumn<Book, String> bookIsbnColumn = new TableColumn<>("ISBN");
    bookIsbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
    bookIsbnColumn.setPrefWidth(120);

    TableColumn<Book, Integer> bookCopyCountColumn = new TableColumn<>("Copy Count");
    bookCopyCountColumn.setCellValueFactory(new PropertyValueFactory<>("copyCount"));
    bookCopyCountColumn.setPrefWidth(80);

    TableColumn<Book, Integer> bookPageCountColumn = new TableColumn<>("Page Count");
    bookPageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
    bookPageCountColumn.setPrefWidth(80);

    TableColumn<Book, String> bookPublishDateColumn = new TableColumn<>("Publish Date");
    bookPublishDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishDate"));
    bookPublishDateColumn.setPrefWidth(100);

    TableColumn<Book, String> bookLanguageColumn = new TableColumn<>("Language");
    bookLanguageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
    bookLanguageColumn.setPrefWidth(80);

    // tableViewBooks----------------------------------
    tableViewBooks.getColumns().addAll(bookImageColumn, bookTitleColumn, bookAuthorColumn, bookCategoryColumn,
            bookIsbnColumn, bookCopyCountColumn, bookPageCountColumn, bookPublishDateColumn, bookLanguageColumn);

    ScrollPane tableScrollPane = new ScrollPane(tableViewBooks);
    tableScrollPane.setFitToWidth(true);
    tableScrollPane.setPrefWidth(850);
    tableScrollPane.setPrefHeight(420);

    tableViewBooks.getStyleClass().add("table-container");

    // Book Image Selection--------------------------------
    Label bookImageLabel = new Label("Book Image:");
    ImageView bookImageView = new ImageView(new Image(getClass().getResourceAsStream("/Images/book.png")));
    bookImageView.setFitWidth(100);
    bookImageView.setFitHeight(100);
    bookImageView.setOnMouseClicked(e -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = fileChooser.showOpenDialog(showAdminDashboardBookManagementStage);
        if (file != null) {
            Image selectedImage = new Image(file.toURI().toString());
            bookImageView.setImage(selectedImage);
            this.imageName = "/images/" + file.getName();
            try {
                saveImage(selectedImage, file.getName());
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    });

    // Book Fields-----------------------------------
    Label bookTitleLabel = new Label("Title:");
    TextField bookTitleTf = new TextField();

    Label bookAuthorLabel = new Label("Author:");
    TextField bookAuthorTf = new TextField();

    Label bookCategoryLabel = new Label("Category:");
    ComboBox<String> bookCategoryComboBox = new ComboBox<>(Categories);
    bookCategoryComboBox.setValue("Fiction");

    Label bookIsbnLabel = new Label("ISBN:");
    TextField bookIsbnTf = new TextField();

    Label bookPublishDateLabel = new Label("Publish Date:");
    TextField bookPublishDateTf = new TextField();

    Label bookLanguageLabel = new Label("Language:");
    ComboBox<String> bookLanguageComboBox = new ComboBox<>();
    bookLanguageComboBox.getItems().addAll("Arabic", "English");
    bookLanguageComboBox.setPromptText("Select Language");

    // تفعيل القائمة عند النقر
    bookLanguageComboBox.setOnMouseClicked(e -> {
        if (!bookLanguageComboBox.isShowing()) {
            bookLanguageComboBox.show(); // إظهار القائمة إذا لم تكن ظاهرة
        }
    });

    Label bookPageCountLabel = new Label("Page Count:");
    TextField bookPageCountTf = new TextField();

    Label bookCopyCountLabel = new Label("Copy Count:");
    TextField bookCopyCountTf = new TextField();

    // Buttons---------------------------
    Button addBookButton = new Button("Add");
    Button updateBookButton = new Button("Update");
    Button deleteBookButton = new Button("Delete");
    Button cancelBookButton = new Button("Cancel");

    addBookButton.setOnAction(e -> {
        if (bookTitleTf.getText().isEmpty() || bookAuthorTf.getText().isEmpty() || bookIsbnTf.getText().isEmpty()
                || bookPageCountTf.getText().isEmpty() || bookCopyCountTf.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all required fields.");
            alert.showAndWait();
        } else {
            try {
                String imageUrl = "/Images/book.png";
                Book newBook = new Book(
                        imageUrl,
                        bookTitleTf.getText(),
                        bookAuthorTf.getText(),
                        bookCategoryComboBox.getValue(),
                        bookIsbnTf.getText(),
                        Integer.parseInt(bookCopyCountTf.getText()),
                        Integer.parseInt(bookPageCountTf.getText()),
                        bookPublishDateTf.getText(),
                        bookLanguageComboBox.getValue()
                );
                Books.add(newBook);
                tableViewBooks.setItems(Books);

                // Alert------------------------------------                   
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book has been added successfully.");
                alert.showAndWait();
                clearFields();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid numbers for Page Count and Copy Count.");
                alert.showAndWait();
            }
        }
    });

    updateBookButton.setOnAction(e -> {
        Book selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            // Update logic
            selectedBook.setTitle(bookTitleTf.getText());
            selectedBook.setAuthor(bookAuthorTf.getText());
            selectedBook.setCategory(bookCategoryComboBox.getValue());
            selectedBook.setIsbn(bookIsbnTf.getText());
            selectedBook.setPublishDate(bookPublishDateTf.getText());
            selectedBook.setLanguage(bookLanguageComboBox.getValue());
            selectedBook.setPageCount(Integer.parseInt(bookPageCountTf.getText()));
            selectedBook.setCopyCount(Integer.parseInt(bookCopyCountTf.getText()));

            if (bookImageView.getImage() != null) {
                selectedBook.setImage(bookImageView.getImage().impl_getUrl());
            }
            tableViewBooks.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book updated successfully.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a book to update.");
            alert.showAndWait();
        }
    });

    deleteBookButton.setOnAction(e -> {
        Book selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Books.remove(selectedBook);
            tableViewBooks.setItems(Books);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book has been deleted successfully.");
            alert.showAndWait();
            clearFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a book to delete.");
            alert.showAndWait();
        }
    });

    cancelBookButton.setOnAction(e -> {
        ShowMyStage(AdminStage);
    });

    HBox buttonBox = new HBox(10, addBookButton, updateBookButton, deleteBookButton, cancelBookButton);
    buttonBox.setAlignment(Pos.CENTER);

    // GridPane--------------------------------------
    GridPane inputGrid = new GridPane();
    inputGrid.setHgap(10);
    inputGrid.setVgap(20);
    inputGrid.add(bookImageLabel, 0, 0);
    inputGrid.add(bookImageView, 0, 1);
    inputGrid.add(bookTitleLabel, 0, 2);
    inputGrid.add(bookTitleTf, 0, 3);
    inputGrid.add(bookIsbnLabel, 0, 4);
    inputGrid.add(bookIsbnTf, 0, 5);
    inputGrid.add(bookPageCountLabel, 0, 6);
    inputGrid.add(bookPageCountTf, 0, 7);
    inputGrid.add(bookAuthorLabel, 1, 2);
    inputGrid.add(bookAuthorTf, 1, 3);
    inputGrid.add(bookPublishDateLabel, 1, 4);
    inputGrid.add(bookPublishDateTf, 1, 5);
    inputGrid.add(bookCopyCountLabel, 1, 6);
    inputGrid.add(bookCopyCountTf, 1, 7);
    inputGrid.add(bookCategoryLabel, 0, 8);
    inputGrid.add(bookCategoryComboBox, 0, 9);
    inputGrid.add(bookLanguageLabel, 0, 10);
    inputGrid.add(bookLanguageComboBox, 0, 11);
    inputGrid.add(buttonBox, 0, 12);

    // Admin Stage setup
    categoryLogo.setOnMouseClicked(e -> categoryManagementStage());



        Label userManagementLabel = new Label("User Management\n\n");
        Label bookManagementLabel = new Label("Book Management\n");

        userManagementLabel.setOnMouseClicked(e -> {
            showAdminDasboard();
        });
        bookManagementLabel.setOnMouseClicked(e -> {
            showAdminDashboardBookManagementStage();
        });

        // SideBar-------------------------------
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.getChildren().addAll(categoryLogo, bookCategoryFilterLabel, bookCategoryFilterComboBox, userManagementLabel, bookManagementLabel);
        HBox mainLayout = new HBox(30, sidebar, inputGrid, tableScrollPane);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        Scene scene = new Scene(mainLayout, 1400, 700);
        SetStageData(showAdminDashboardBookManagementStage, scene, "Logo.png", "AdminBookManagementDashboard", "Style/AdminBookManagementDashboard.css", 1600, 700, 500, 250);
        ShowMyStage(showAdminDashboardBookManagementStage);

    }
//----------------------------------------------------------------

    public void RefreshListviewUserData() {
        ListViewUsersData.getItems().clear();
        for (User user : Users) {
            ListViewUsersData.getItems().add(user.getUserName());

        }

    }

    public User validateUser(String username, String password) {
        for (User user : Users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return user;
            }
        }
        currentUser = null;
        return null;
    }

    public void SetStageData(Stage s, Scene sc, String Logo, String title, String StageStyle, int sw, int sh, int sx, int sy) {
        s.setScene(sc);
        sc.getStylesheets().add(StageStyle);
        s.setTitle(title);
        s.setWidth(sw);
        s.setHeight(sh);
        s.setX(sx);
        s.setY(sy);
        SetStageImageIcon(s, Logo);
        s.setResizable(false);
    }

    public void ShowMyStage(Stage s) {
        LoginStage.hide();
        RegisterStage.hide();
        AdminStage.hide();
        s.show();
    }

    public void SetStageImageIcon(Stage s, String Logo) {
        s.getIcons().add(new Image(FxlLibraryMangmentsSysytem.class
                .getResourceAsStream("/images/" + Logo)));
    }

    public void saveImage(Image img, String name) throws IOException {
        String projectPath = System.getProperty("user.dir");
        String imagesFolderPath = projectPath + "/src/images";
        File imagesFolder = new File(imagesFolderPath);
        if (!imagesFolder.exists()) {
            imagesFolder.mkdir();
        }
        String fullFilePath = imagesFolderPath + "/" + name;
        File file = new File(fullFilePath);
        BufferedImage BI = SwingFXUtils.fromFXImage(img, null);
        ImageIO.write(BI, "png", file);
    }

    public boolean UserExist(String username, String password) {
        boolean userFound = false;
        for (User user : Users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                userFound = true;
            }
        }
        return userFound;
    }

    public void categoryManagementStage() {

        Label categoriesLabel = new Label("Add New Category:");
        categoriesLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Text Field------------------------
        TextField categoryTf = new TextField();
        categoryTf.setPromptText("Enter category name...");
        categoryTf.setMaxWidth(200);

        // Buttons----------------------
        Button addButton = new Button("Add");
        Button cancelButton = new Button("Cancel");

        addButton.setOnAction(e -> {
            String newCategory = categoryTf.getText().trim();
            if (!newCategory.isEmpty()) {

                if (!Categories.contains(newCategory)) {
                    Categories.add(newCategory);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Category added successfully!");
                    alert.showAndWait();
                    categoryTf.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Category already exists!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a category name.");
                alert.showAndWait();
            }
        });

        cancelButton.setOnAction(e -> {

            showAdminDashboardBookManagementStage();
        });

        HBox buttonBox = new HBox(15, addButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(20, categoriesLabel, categoryTf, buttonBox);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 400, 300);
        SetStageData(categoryManagementStage, scene, "Logo.png", "Category Management", "Style/categoryManagement.css", 400, 300, 500, 80);
        ShowMyStage(categoryManagementStage);
    }

    public void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }

    }

}
