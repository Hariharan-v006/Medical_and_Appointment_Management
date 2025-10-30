import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Appointment_GUI {

    JFrame frame;
    JTable table;
    DefaultTableModel model;
    JTextField patientField, doctorField, dateField, timeField;
    JButton addBtn, updateBtn, deleteBtn, refreshBtn;

    public Appointment_GUI() {
        frame = new JFrame("Appointment Management");
        frame.setLayout(null);

        // Header
        JLabel header = new JLabel("Appointment Management", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setBounds(0, 20, 900, 40);
        frame.add(header);

        // Labels
        JLabel patientLabel = new JLabel("Patient Name:");
        JLabel doctorLabel = new JLabel("Doctor Name:");
        JLabel dateLabel = new JLabel("Appointment Date (YYYY-MM-DD):");
        JLabel timeLabel = new JLabel("Appointment Time (HH:MM:SS):");

        patientLabel.setBounds(50, 90, 200, 30);
        doctorLabel.setBounds(50, 130, 200, 30);
        dateLabel.setBounds(50, 170, 250, 30);
        timeLabel.setBounds(50, 210, 250, 30);

        frame.add(patientLabel);
        frame.add(doctorLabel);
        frame.add(dateLabel);
        frame.add(timeLabel);

        // TextFields
        patientField = new JTextField();
        doctorField = new JTextField();
        dateField = new JTextField();
        timeField = new JTextField();

        patientField.setBounds(270, 90, 200, 30);
        doctorField.setBounds(270, 130, 200, 30);
        dateField.setBounds(270, 170, 200, 30);
        timeField.setBounds(270, 210, 200, 30);

        frame.add(patientField);
        frame.add(doctorField);
        frame.add(dateField);
        frame.add(timeField);

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update Status");
        deleteBtn = new JButton("Delete");
        refreshBtn = new JButton("Refresh");

        addBtn.setBounds(50, 260, 130, 35);
        updateBtn.setBounds(200, 260, 160, 35);
        deleteBtn.setBounds(380, 260, 130, 35);
        refreshBtn.setBounds(530, 260, 130, 35);

        Color blue = new Color(0, 123, 255);
        JButton[] btns = {addBtn, updateBtn, deleteBtn, refreshBtn};
        for (JButton b : btns) {
            b.setBackground(blue);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
        }

        frame.add(addBtn);
        frame.add(updateBtn);
        frame.add(deleteBtn);
        frame.add(refreshBtn);

        // Table
        String[] columns = {"AppointmentID", "PatientName", "DoctorName", "AppointmentDate", "AppointmentTime", "Status"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 320, 800, 200);
        frame.add(scroll);

        // Frame settings
        frame.getContentPane().setBackground(new Color(240, 248, 255));
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Load data
        loadAppointments();

        // Button actions
        addBtn.addActionListener(e -> addAppointment());
        updateBtn.addActionListener(e -> updateStatus());
        deleteBtn.addActionListener(e -> deleteAppointment());
        refreshBtn.addActionListener(e -> loadAppointments());
    }

    private Connection getConnection() throws SQLException {
        // Always return a fresh, open connection
        return DatabaseConnection.getConnection();
    }

    private void loadAppointments() {
        model.setRowCount(0); // Clear table
        String sql = "SELECT * FROM mm_appointments";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("AppointmentID"),
                        rs.getString("PatientName"),
                        rs.getString("DoctorName"),
                        rs.getDate("AppointmentDate"),
                        rs.getTime("AppointmentTime"),
                        rs.getString("Status")
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading appointments.");
        }
    }

    private void addAppointment() {
        String patient = patientField.getText().trim();
        String doctor = doctorField.getText().trim();
        String dateStr = dateField.getText().trim();
        String timeStr = timeField.getText().trim();

        if (patient.isEmpty() || doctor.isEmpty() || dateStr.isEmpty() || timeStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            return;
        }

        // Validate date
        java.sql.Date sqlDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            java.util.Date parsedDate = sdf.parse(dateStr);
            sqlDate = new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(frame, "Invalid date format! Use YYYY-MM-DD");
            return;
        }

        // Validate time
        java.sql.Time sqlTime;
        try {
            SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
            stf.setLenient(false);
            java.util.Date parsedTime = stf.parse(timeStr);
            sqlTime = new java.sql.Time(parsedTime.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(frame, "Invalid time format! Use HH:MM:SS");
            return;
        }

        String sql = "INSERT INTO mm_appointments (PatientName, DoctorName, AppointmentDate, AppointmentTime, Status) VALUES (?, ?, ?, ?, 'Scheduled')";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patient);
            ps.setString(2, doctor);
            ps.setDate(3, sqlDate);
            ps.setTime(4, sqlTime);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Appointment Added!");
            loadAppointments();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error adding appointment. Make sure your database connection is active.");
        }
    }

    private void updateStatus() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a row first!");
            return;
        }
        int id = (int) model.getValueAt(selected, 0);
        String newStatus = JOptionPane.showInputDialog(frame, "Enter new status (Scheduled/Completed/Cancelled):");
        if (newStatus == null || newStatus.trim().isEmpty()) return;

        String sql = "UPDATE mm_appointments SET Status=? WHERE AppointmentID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus.trim());
            ps.setInt(2, id);
            ps.executeUpdate();
            loadAppointments();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating status.");
        }
    }

    private void deleteAppointment() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a row first!");
            return;
        }
        int id = (int) model.getValueAt(selected, 0);
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this appointment?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM mm_appointments WHERE AppointmentID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            loadAppointments();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error deleting appointment.");
        }
    }

    public static void main(String[] args) {
        new Appointment_GUI();
    }
}
