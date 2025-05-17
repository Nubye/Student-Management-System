// AdminDashboard.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        createUI();
    }

    private void createUI() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        // Create User Button
        JButton createUserButton = new JButton("Create User");
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });
        add(createUserButton);

        // Reset Password Button
        JButton resetPasswordButton = new JButton("Reset Password");
        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPassword();
            }
        });
        add(resetPasswordButton);

        // Delete User Button
        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        add(deleteUserButton);

        // Backup Database Button
        JButton backupButton = new JButton("Backup Database");
        backupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backupDatabase();
            }
        });
        add(backupButton);

        // Restore Database Button
        JButton restoreButton = new JButton("Restore Database");
        restoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restoreDatabase();
            }
        });
        add(restoreButton);

        setVisible(true);
    }

    private void createUser() {
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JTextField roleField = new JTextField();

    Object[] message = {
        "Username:", usernameField,
        "Password:", passwordField,
        "Role (admin/user):", roleField
    };

    int option = JOptionPane.showConfirmDialog(this, message, "Create User", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = roleField.getText();

            UserDAO userDAO = new UserDAO();
            userDAO.createUser(username, password, role);
            JOptionPane.showMessageDialog(this, "User created successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    private void resetPassword() {
    JTextField usernameField = new JTextField();
    JPasswordField newPasswordField = new JPasswordField();

    Object[] message = {
        "Username:", usernameField,
        "New Password:", newPasswordField
    };

    int option = JOptionPane.showConfirmDialog(this, message, "Reset Password", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        try {
            String username = usernameField.getText();
            String newPassword = new String(newPasswordField.getPassword());

            UserDAO userDAO = new UserDAO();
            userDAO.resetPassword(username, newPassword);
            JOptionPane.showMessageDialog(this, "Password reset successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    private void deleteUser() {
    String username = JOptionPane.showInputDialog(this, "Enter username to delete:");
    if (username != null && !username.isEmpty()) {
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.deleteUser(username);
            JOptionPane.showMessageDialog(this, "User deleted successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    // AdminDashboard.java
private void backupDatabase() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Backup As");
    int userSelection = fileChooser.showSaveDialog(this);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                "mysqldump", "-u", "YOUR_USERNAME", "-pYOUR_PASSWORD", "studentmanagement"//Enter your username and password
            );
            processBuilder.redirectOutput(fileToSave); 
            Process process = processBuilder.start();
            process.waitFor(); 
            JOptionPane.showMessageDialog(this, "Backup created successfully!");
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void restoreDatabase() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Select Backup File");
    int userSelection = fileChooser.showOpenDialog(this);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToRestore = fileChooser.getSelectedFile();
        try {
            // Use ProcessBuilder to execute the mysql command
            ProcessBuilder processBuilder = new ProcessBuilder(
                "mysql", "-u", "YOUR_USERNAME", "-pYOUR_PASSWORD", "studentmanagement"//Enter your username and password
            );
            processBuilder.redirectInput(fileToRestore); // Redirect input from the backup file
            Process process = processBuilder.start();
            process.waitFor(); // Wait for the process to complete
            JOptionPane.showMessageDialog(this, "Database restored successfully!");
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
}