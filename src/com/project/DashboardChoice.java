
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardChoice {
    JFrame frame;
    JComboBox<String> choice;
    JButton openButton;

    public DashboardChoice() {
        frame = new JFrame("Choose Module");
        frame.setLayout(null);

        JLabel title = new JLabel("Select a Module", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(0, 50, 500, 40);

        String[] options = {"Medical Shop Management", "Appointment Management"};
        choice = new JComboBox<>(options);
        choice.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        choice.setBounds(120, 130, 260, 40);

        openButton = new JButton("Open");
        openButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        openButton.setBackground(new Color(0, 123, 255));
        openButton.setForeground(Color.WHITE);
        openButton.setBounds(170, 200, 160, 40);

        openButton.addActionListener(e -> {
            String selected = (String) choice.getSelectedItem();
            frame.dispose();

            if (selected.equals("Medical Shop Management")) {
                new Admin_GUI();
            } else {
                new Appointment_GUI();
            }
        });

        frame.add(title);
        frame.add(choice);
        frame.add(openButton);
        frame.getContentPane().setBackground(new Color(245, 255, 250));
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
