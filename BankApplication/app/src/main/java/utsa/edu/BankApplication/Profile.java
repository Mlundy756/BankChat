package utsa.edu.BankApplication;

/**
 * Created by Matthew on 4/16/2016.
 */

/* Simple class that populates the profile for use within the app
 */
public class Profile {

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;

    public Profile() {

    }

    public Profile(String firstName, String middleName, String lastName, String email, String password) {
        setfirstName(firstName);
        setmiddleName(middleName);
        setlastName(lastName);
        setEmail(email);
        setPassword(password);
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getmiddleName() {
        return middleName;
    }

    public void setmiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
