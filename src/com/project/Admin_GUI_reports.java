
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

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "";
            String fileName = "";

            if (selected.equals("Stock Report")) {
                query = "SELECT drug_id, name, quantity, price FROM drugs";
                fileName = "Stock_Report.csv";
            } else if (selected.equals("Sales Report")) {
                query = "SELECT s.sale_id, d.name, s.quantity_sold, s.total_price, s.date " +
                        "FROM sales s JOIN drugs d ON s.drug_id = d.drug_id";
                fileName = "Sales_Report.csv";
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            writeToCSV(rs, fileName);
            JOptionPane.showMessageDialog(this, "Report generated successfully: " + fileName);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
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
}
