// LoginUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserDAO userDAO;

    public LoginUI() {
        userDAO = new UserDAO();
        createUI();
    }

    private void createUI() {
        setTitle("Login");
        setSize(300, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        // Username
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // Password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        add(loginButton);

        // Admin Login Button
        JButton adminLoginButton = new JButton("Admin Login");
        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAdminLogin();
            }
        });
        add(adminLoginButton);

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            User user = userDAO.authenticate(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                if (user.getRole().equals("admin")) {
                    new AdminDashboard(); 
                } else {
                    new StudentManagementUI(); 
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openAdminLogin() {
        new AdminLoginUI(); 
    }

    public static void main(String[] args) {
        new LoginUI();
    }
}