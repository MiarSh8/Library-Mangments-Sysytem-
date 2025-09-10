package FxlLibraryMangmentsSysytem;

public class User {

    private String fullName;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String role;
    private String profileImagePath;
    
  
    public User(String fullName, String userName, String password, String email, String phone, String role, String profileImagePath) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.profileImagePath = profileImagePath;
    }

    public User() {}

    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", profileImagePath='" + profileImagePath + '\'' +
                '}';
    }
}
