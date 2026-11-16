package chatapp;
/**
 * Login class (simple Part 1 version).
 * If your Part 1 Login already works, keep yours instead.
 */
public class Login {

    private String username;
    private String password;

    // Username must contain an underscore and be max 5 characters
    public boolean checkUserName(String user) {
        return user.contains("_") && user.length() <= 5;
    }

    // Password: 8+ characters, a capital letter, a number and a special character
    public boolean checkPasswordComplexity(String pass) {
        boolean longEnough = pass.length() >= 8;
        boolean hasCapital = pass.matches(".*[A-Z].*");
        boolean hasNumber = pass.matches(".*[0-9].*");
        boolean hasSpecial = pass.matches(".*[^A-Za-z0-9].*");
        return longEnough && hasCapital && hasNumber && hasSpecial;
    }

    // Registers the user if username and password are both valid
    public String registerUser(String user, String pass) {
        if (!checkUserName(user)) {
            return "Username is not correctly formatted (needs _ and max 5 characters).";
        }
        if (!checkPasswordComplexity(pass)) {
            return "Password is not correctly formatted.";
        }
        username = user;
        password = pass;
        return "User successfully registered.";
    }

    // Checks the login details against the registered ones
    public boolean loginUser(String user, String pass) {
        return user.equals(username) && pass.equals(password);
    }

    // Returns a message about the login result
    public String returnLoginStatus(boolean loggedIn) {
        if (loggedIn) {
            return "Welcome, it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}

