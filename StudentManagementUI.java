import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentManagementUI extends JFrame {
    private JTextField rollNumberField, nameField, gradeField, ageField, addressField;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private StudentDAO studentDAO;
    private boolean isDarkMode = false; // Track dark mode state

    public StudentManagementUI() {
        studentDAO = new StudentDAO();
        createUI();
    }

    private void createUI() {
        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Roll Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Roll Number:"), gbc);

        gbc.gridx = 1;
        rollNumberField = new JTextField(20);
        inputPanel.add(rollNumberField, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);

        // Grade
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Grade:"), gbc);

        gbc.gridx = 1;
        gradeField = new JTextField(20);
        inputPanel.add(gradeField, gbc);

        // Age
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Age:"), gbc);

        gbc.gridx = 1;
        ageField = new JTextField(20);
        inputPanel.add(ageField, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Address:"), gbc);

        gbc.gridx = 1;
        addressField = new JTextField(20);
        inputPanel.add(addressField, gbc);

        // Buttons
        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        JButton viewButton = new JButton("View Students");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStudents();
            }
        });

        JButton updateButton = new JButton("Update Student");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        JButton deleteButton = new JButton("Delete Student");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        JButton searchButton = new JButton("Search Student");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });

        JButton exportButton = new JButton("Export Data");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportData();
            }
        });

        JButton darkModeButton = new JButton("Toggle Dark Mode");
        darkModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleDarkMode();
            }
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(darkModeButton);
        buttonPanel.add(refreshButton);

        // Table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Roll Number");
        tableModel.addColumn("Name");
        tableModel.addColumn("Grade");
        tableModel.addColumn("Age");
        tableModel.addColumn("Address");

        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Load initial data
        refreshTable();

        setVisible(true);
    }

    private void refreshTable() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            tableModel.setRowCount(0); // Clear the table
            for (Student student : students) {
                tableModel.addRow(new Object[]{
                    student.getRollNumber(),
                    student.getName(),
                    student.getGrade(),
                    student.getAge(),
                    student.getAddress()
                });
            }
        } catch (SQLException ex) {
         JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStudent() {
        try {
            Student student = new Student(
                rollNumberField.getText(),
                nameField.getText(),
                gradeField.getText(),
                Integer.parseInt(ageField.getText()),
                addressField.getText()
            );
            studentDAO.addStudent(student);
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            refreshTable();
            clearFields();
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewStudents() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            tableModel.setRowCount(0); // Clear the table
            for (Student student : students) {
                tableModel.addRow(new Object[]{
                    student.getRollNumber(),
                    student.getName(),
                    student.getGrade(),
                    student.getAge(),
                    student.getAddress()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        try {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student to update!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String rollNumber = (String) tableModel.getValueAt(selectedRow, 0); // Get roll number from the table

            // Create a Student object with the new values (empty fields will remain unchanged)
            Student updatedStudent = new Student(
                rollNumberField.getText().isEmpty() ? null : rollNumberField.getText(),
                nameField.getText().isEmpty() ? null : nameField.getText(),
                gradeField.getText().isEmpty() ? null : gradeField.getText(),
                ageField.getText().isEmpty() ? -1 : Integer.parseInt(ageField.getText()),
                addressField.getText().isEmpty() ? null : addressField.getText()
            );

            // Update the student in the database
            studentDAO.updateStudent(rollNumber, updatedStudent);
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
            refreshTable();
            clearFields();
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        try {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String rollNumber = (String) tableModel.getValueAt(selectedRow, 0); // Get roll number from the table
            studentDAO.deleteStudent(rollNumber);
            JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            refreshTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchStudent() {
        try {
            String rollNumber = rollNumberField.getText(); // Get roll number from the input field
            Student student = studentDAO.searchStudent(rollNumber); // Use rollNumber to search
            if (student != null) {
                tableModel.setRowCount(0); // Clear the table
                tableModel.addRow(new Object[]{
                    student.getRollNumber(),
                    student.getName(),
                    student.getGrade(),
                    student.getAge(),
                    student.getAddress()
                });
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                List<Student> students = studentDAO.getAllStudents();
                exportToCSV(students, fileToSave.getAbsolutePath()); // Call exportToCSV
                JOptionPane.showMessageDialog(this, "Data exported successfully!");
            } catch (SQLException | IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportToCSV(List<Student> students, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Roll Number,Name,Grade,Age,Address\n");
            for (Student student : students) {
                writer.write(String.format("%s,%s,%s,%d,%s\n",
                    student.getRollNumber(),
                    student.getName(),
                    student.getGrade(),
                    student.getAge(),
                    student.getAddress()
                ));
            }
        }
    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
    
        if (isDarkMode) {
            // Dark mode colors
            Color darkBackground = new Color(30, 30, 30);
            Color darkForeground = new Color(200, 200, 200);
            Color darkTableBackground = new Color(50, 50, 50);
            Color darkTableForeground = new Color(220, 220, 220);
            Color darkButtonBackground = new Color(70, 70, 70);
            Color darkButtonForeground = new Color(220, 220, 220);
    
            // Apply dark mode to all components
            applyDarkMode(darkBackground, darkForeground, darkTableBackground, darkTableForeground, darkButtonBackground, darkButtonForeground);
        } else {
            // Light mode colors (default)
            Color lightBackground = UIManager.getColor("Panel.background");
            Color lightForeground = UIManager.getColor("Label.foreground");
            Color lightTableBackground = UIManager.getColor("Table.background");
            Color lightTableForeground = UIManager.getColor("Table.foreground");
            Color lightButtonBackground = UIManager.getColor("Button.background");
            Color lightButtonForeground = UIManager.getColor("Button.foreground");
    
            // Apply light mode to all components
            applyDarkMode(lightBackground, lightForeground, lightTableBackground, lightTableForeground, lightButtonBackground, lightButtonForeground);
        }
    }
    
    private void applyDarkMode(Color background, Color foreground, Color tableBackground, Color tableForeground, Color buttonBackground, Color buttonForeground) {
        // Set colors for the frame and panels
        getContentPane().setBackground(background);
        for (Component component : getContentPane().getComponents()) {
            if (component instanceof JPanel) {
                component.setBackground(background);
                component.setForeground(foreground);
                for (Component subComponent : ((JPanel) component).getComponents()) {
                    subComponent.setBackground(background);
                    subComponent.setForeground(foreground);
                    if (subComponent instanceof JButton) {
                        subComponent.setBackground(buttonBackground);
                        subComponent.setForeground(buttonForeground);
                    }
                }
            }
        }
    
        // Set colors for input fields
        rollNumberField.setBackground(background);
        rollNumberField.setForeground(foreground);
        nameField.setBackground(background);
        nameField.setForeground(foreground);
        gradeField.setBackground(background);
        gradeField.setForeground(foreground);
        ageField.setBackground(background);
        ageField.setForeground(foreground);
        addressField.setBackground(background);
        addressField.setForeground(foreground);
    
        // Set colors for the table
        studentTable.setBackground(tableBackground);
        studentTable.setForeground(tableForeground);
        studentTable.getTableHeader().setBackground(tableBackground);
        studentTable.getTableHeader().setForeground(tableForeground);
    
        // Set colors for the scroll pane
        JScrollPane scrollPane = (JScrollPane) studentTable.getParent().getParent();
        scrollPane.getViewport().setBackground(tableBackground);
        scrollPane.setBackground(tableBackground);
    
        // Set colors for buttons
        for (Component component : getContentPane().getComponents()) {
            if (component instanceof JButton) {
                component.setBackground(buttonBackground);
                component.setForeground(buttonForeground);
            }
        }
    }

    private void clearFields() {
        rollNumberField.setText("");
        nameField.setText("");
        gradeField.setText("");
        ageField.setText("");
        addressField.setText("");
    }

    public static void main(String[] args) {
        new StudentManagementUI();
    }
}