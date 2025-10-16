import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> moduleChoice;

    public Login() {
        setTitle("Login - Medical Management System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 250, 255));
        add(panel);

        JLabel title = new JLabel("🔐 Login Portal");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBounds(150, 20, 300, 40);
        panel.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userLabel.setBounds(80, 100, 100, 30);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 100, 200, 30);
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passLabel.setBounds(80, 150, 100, 30);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 150, 200, 30);
        panel.add(passwordField);

        JLabel moduleLabel = new JLabel("Select Module:");
        moduleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        moduleLabel.setBounds(80, 200, 130, 30);
        panel.add(moduleLabel);

        String[] modules = {"Medical Shop Management", "Appointment Management"};
        moduleChoice = new JComboBox<>(modules);
        moduleChoice.setBounds(220, 200, 200, 30);
        panel.add(moduleChoice);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setBounds(180, 270, 120, 40);
        loginBtn.setBackground(new Color(0, 123, 255));
        loginBtn.setForeground(Color.WHITE);
        panel.add(loginBtn);

        loginBtn.addActionListener(e -> loginAction());

        setVisible(true);
    }

    private void loginAction() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String selectedModule = (String) moduleChoice.getSelectedItem();

        // Hardcoded credentials
        String validUser = "admin";
        String validPass = "hariharan";

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        // Check credentials without database
        if (username.equals(validUser) && password.equals(validPass)) {
            JOptionPane.showMessageDialog(this, "Login successful!");

            // Open module window based on selection
            if (selectedModule.equals("Medical Shop Management")) {
                new Admin_GUI();
            } else if (selectedModule.equals("Appointment Management")) {
                new Appointment_GUI(); // create this class next
            }

            dispose(); // close login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
