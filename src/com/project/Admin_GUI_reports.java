import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class Admin_GUI_reports extends JFrame {
    private JComboBox<String> reportType;

    public Admin_GUI_reports() {
        setTitle("Report Management");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 240, 250));
        panel.setLayout(null);
        add(panel);

        JLabel title = new JLabel("📊 Generate Reports");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(200, 20, 400, 40);
        panel.add(title);

        JLabel lblType = new JLabel("Select Report Type:");
        lblType.setFont(new Font("Arial", Font.BOLD, 16));
        lblType.setBounds(100, 100, 200, 30);
        panel.add(lblType);

        String[] options = {"Stock Report", "Sales Report"};
        reportType = new JComboBox<>(options);
        reportType.setBounds(300, 100, 200, 30);
        panel.add(reportType);

        JButton btnGenerate = new JButton("Generate Report");
        btnGenerate.setBounds(250, 180, 200, 40);
        btnGenerate.setBackground(new Color(51, 102, 255));
        btnGenerate.setForeground(Color.white);
        btnGenerate.setFont(new Font("Arial", Font.BOLD, 15));
        btnGenerate.addActionListener(e -> generateReport());
        panel.add(btnGenerate);

        setVisible(true);
    }

    private void generateReport() {
    String selected = (String) reportType.getSelectedItem();
    if (selected == null) return;

    String query = "";
    String fileName = "";

    if (selected.equals("Stock Report")) {
        query = "SELECT SN, Name, Type, Price, Quantity, `Expiry days`, Company, `Shelf No.` FROM mm_drugs";
        fileName = "Stock_Report.csv";
    } else if (selected.equals("Sales Report")) {
        query = "SELECT SN, Name, Type, Price, Quantity, `Total Price`, Date FROM mm_sales";
        fileName = "Sales_Report.csv";
    }

    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        // Load all rows into memory first
        java.util.List<String[]> rows = new java.util.ArrayList<>();
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();

        // Add header
        String[] header = new String[cols];
        for (int i = 1; i <= cols; i++) {
            header[i - 1] = meta.getColumnName(i);
        }
        rows.add(header);

        // Add data rows
        while (rs.next()) {
            String[] row = new String[cols];
            for (int i = 1; i <= cols; i++) {
                row[i - 1] = rs.getString(i);
            }
            rows.add(row);
        }

        // Write to CSV
        writeToCSV(rows, fileName);
        JOptionPane.showMessageDialog(this, "Report generated successfully: " + fileName);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
}

private void writeToCSV(java.util.List<String[]> rows, String fileName) throws IOException {
    try (FileWriter fw = new FileWriter(fileName)) {
        for (String[] row : rows) {
            fw.write(String.join(",", row) + "\n");
        }
    }
}


    private void writeToCSV(ResultSet rs, String fileName) throws SQLException, IOException {
        FileWriter fw = new FileWriter(fileName);
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();

        // Header
        for (int i = 1; i <= cols; i++) {
            fw.write(meta.getColumnName(i) + (i < cols ? "," : "\n"));
        }

        // Data
        while (rs.next()) {
            for (int i = 1; i <= cols; i++) {
                fw.write(rs.getString(i) + (i < cols ? "," : "\n"));
            }
        }

        fw.close();
    }

    public static void main(String[] args) {
        new Admin_GUI_reports();
    }
}