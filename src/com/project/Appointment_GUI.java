
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Appointment_GUI {
    JFrame frame;
    JTable table;
    DefaultTableModel model;
    JTextField patientField, doctorField, dateField, timeField;
    JButton addBtn, updateBtn, deleteBtn, refreshBtn;

    public Appointment_GUI() {
        frame = new JFrame("Appointment Management");
        frame.setLayout(null);

        JLabel header = new JLabel("Appointment Management", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setBounds(0, 20, 900, 40);

        String[] cols = {"ID", "Patient", "Doctor", "Date", "Time", "Status"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 250, 800, 300);

        patientField = new JTextField();
        doctorField = new JTextField();
        dateField = new JTextField();
        timeField = new JTextField();

        patientField.setBounds(100, 90, 150, 30);
        doctorField.setBounds(270, 90, 150, 30);
        dateField.setBounds(440, 90, 150, 30);
        timeField.setBounds(610, 90, 150, 30);

        addBtn = new JButton("Add");
        updateBtn = new JButton("Update Status");
        deleteBtn = new JButton("Delete");
        refreshBtn = new JButton("Refresh");

        addBtn.setBounds(100, 150, 130, 35);
        updateBtn.setBounds(250, 150, 160, 35);
        deleteBtn.setBounds(430, 150, 130, 35);
        refreshBtn.setBounds(580, 150, 130, 35);

        Color blue = new Color(0, 123, 255);
        JButton[] btns = {addBtn, updateBtn, deleteBtn, refreshBtn};
        for (JButton b : btns) {
            b.setBackground(blue);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
        }

        frame.add(header);
        frame.add(scroll);
        frame.add(patientField);
        frame.add(doctorField);
        frame.add(dateField);
        frame.add(timeField);
        frame.add(addBtn);
        frame.add(updateBtn);
        frame.add(deleteBtn);
        frame.add(refreshBtn);

        frame.getContentPane().setBackground(new Color(240, 248, 255));
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        loadAppointments();

        addBtn.addActionListener(e -> addAppointment());
        updateBtn.addActionListener(e -> updateStatus());
        deleteBtn.addActionListener(e -> deleteAppointment());
        refreshBtn.addActionListener(e -> loadAppointments());
    }

    private void loadAppointments() {
        model.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM appointments")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("patient_name"),
                    rs.getString("doctor_name"),
                    rs.getDate("date"),
                    rs.getTime("time"),
                    rs.getString("status")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addAppointment() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO appointments (patient_name, doctor_name, date, time, status) VALUES (?, ?, ?, ?, 'Pending')")) {
            ps.setString(1, patientField.getText());
            ps.setString(2, doctorField.getText());
            ps.setString(3, dateField.getText());
            ps.setString(4, timeField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Appointment Added!");
            loadAppointments();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateStatus() {
        int selected = table.getSelectedRow();
        if (selected == -1) return;
        int id = (int) model.getValueAt(selected, 0);
        String newStatus = JOptionPane.showInputDialog(frame, "Enter new status (Pending/Completed/Cancelled):");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE appointments SET status=? WHERE id=?")) {
            ps.setString(1, newStatus);
            ps.setInt(2, id);
            ps.executeUpdate();
            loadAppointments();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteAppointment() {
        int selected = table.getSelectedRow();
        if (selected == -1) return;
        int id = (int) model.getValueAt(selected, 0);
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM appointments WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            loadAppointments();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
