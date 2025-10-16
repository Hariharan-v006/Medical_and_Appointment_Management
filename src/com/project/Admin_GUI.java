import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Admin_GUI implements ActionListener {

    JFrame frame;
    JPanel panel;
    JButton btnDrugs, btnSales, btnWarnings, btnCompany, btnLogout;
    JButton btnAdd, btnUpdate, btnDelete;
    JTable table;
    JScrollPane scrollPane;
    String currentTable = "mm_drugs"; // Default table

    public Admin_GUI() {
        frame = new JFrame("Admin Dashboard");
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(48, 125, 126));

        JLabel header = new JLabel("Admin Dashboard");
        header.setFont(new Font("Bebas Neue", Font.BOLD, 40));
        header.setBounds(450, 20, 500, 50);
        panel.add(header);

        // Dashboard Buttons
        btnDrugs = new JButton("Drugs");
        btnSales = new JButton("Sales");
        btnWarnings = new JButton("Warnings");
        btnCompany = new JButton("Companies");
        btnLogout = new JButton("Logout");

        JButton[] buttons = {btnDrugs, btnSales, btnWarnings, btnCompany, btnLogout};
        int y = 100;
        for (JButton btn : buttons) {
            btn.setBounds(50, y, 150, 40);
            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.WHITE);
            btn.setFocusable(false);
            btn.addActionListener(this);
            panel.add(btn);
            y += 60;
        }
        JButton reportButton = new JButton("📊 Reports");
    reportButton.setBounds(50, 400, 150, 40);
    reportButton.setBackground(new Color(0, 153, 76));
    reportButton.setForeground(Color.white);
    reportButton.setFont(new Font("Arial", Font.BOLD, 16));
    reportButton.addActionListener(e -> new Admin_GUI_reports());
    panel.add(reportButton);

        // CRUD Buttons
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        JButton[] crudButtons = {btnAdd, btnUpdate, btnDelete};
        y = 450;
        for (JButton btn : crudButtons) {
            btn.setBounds(50, y, 150, 40);
            btn.setBackground(Color.DARK_GRAY);
            btn.setForeground(Color.WHITE);
            btn.setFocusable(false);
            btn.addActionListener(this);
            panel.add(btn);
            y += 60;
        }

        // Table
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(250, 100, 900, 500);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Load default table
        loadTable(currentTable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDrugs) {
            currentTable = "mm_drugs";
            loadTable(currentTable);
        } else if (e.getSource() == btnSales) {
            currentTable = "mm_sales";
            loadTable(currentTable);
        } else if (e.getSource() == btnWarnings) {
            currentTable = "mm_warning";
            loadTable(currentTable);
        } else if (e.getSource() == btnCompany) {
            currentTable = "mm_company";
            loadTable(currentTable);
        } else if (e.getSource() == btnLogout) {
            new Login();
            frame.dispose();
        } else if (e.getSource() == btnAdd) {
            addDialog();
        } else if (e.getSource() == btnUpdate) {
            updateDialog();
        } else if (e.getSource() == btnDelete) {
            deleteDialog();
        }
    }

    // Load table data
    private void loadTable(String tableName) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            DefaultTableModel model = new DefaultTableModel();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }

            table.setModel(model);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading data from " + tableName);
        }
    }

    // Generic Add Dialog
    private void addDialog() {
        switch (currentTable) {
            case "mm_drugs":
                addOrUpdateDrug(false);
                break;
            case "mm_company":
                addOrUpdateCompany(false);
                break;
            case "mm_warning":
                addOrUpdateWarning(false);
                break;
            case "mm_sales":
                JOptionPane.showMessageDialog(null, "Sales cannot be added manually.");
                break;
        }
    }

    // Generic Update Dialog
    private void updateDialog() {
        switch (currentTable) {
            case "mm_drugs":
                addOrUpdateDrug(true);
                break;
            case "mm_company":
                addOrUpdateCompany(true);
                break;
            case "mm_warning":
                addOrUpdateWarning(true);
                break;
            case "mm_sales":
                JOptionPane.showMessageDialog(null, "Sales cannot be updated manually.");
                break;
        }
    }

    // Generic Delete Dialog
    private void deleteDialog() {
        String id = JOptionPane.showInputDialog("Enter primary key to delete:");
        if (id == null || id.isEmpty()) return;

        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "";
            PreparedStatement ps;
            switch (currentTable) {
                case "mm_drugs":
                    sql = "DELETE FROM mm_drugs WHERE SN=?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(id));
                    ps.executeUpdate();
                    break;
                case "mm_company":
                    sql = "DELETE FROM mm_company WHERE Name=?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, id);
                    ps.executeUpdate();
                    break;
                case "mm_warning":
                    sql = "DELETE FROM mm_warning WHERE Name=?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, id);
                    ps.executeUpdate();
                    break;
                case "mm_sales":
                    JOptionPane.showMessageDialog(null, "Cannot delete sales manually.");
                    return;
            }
            JOptionPane.showMessageDialog(null, "Deleted successfully!");
            loadTable(currentTable);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting data.");
        }
    }

    // Add/Update Drug
    private void addOrUpdateDrug(boolean isUpdate) {
        JTextField sn = new JTextField();
        if (!isUpdate) sn.setEditable(false);
        JTextField name = new JTextField();
        JTextField type = new JTextField();
        JTextField price = new JTextField();
        JTextField expiry = new JTextField();
        JTextField company = new JTextField();
        JTextField shelf = new JTextField();
        JTextField quantity = new JTextField();

        Object[] fields = isUpdate ?
                new Object[]{"SN:", sn, "Name:", name, "Type:", type, "Price:", price, "Expiry days:", expiry,
                        "Company:", company, "Shelf No:", shelf, "Quantity:", quantity} :
                new Object[]{"Name:", name, "Type:", type, "Price:", price, "Expiry days:", expiry,
                        "Company:", company, "Shelf No:", shelf, "Quantity:", quantity};

        int option = JOptionPane.showConfirmDialog(null, fields, (isUpdate ? "Update" : "Add") + " Drug", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps;
            if (isUpdate) {
                String sql = "UPDATE mm_drugs SET Name=?, Type=?, Price=?, `Expiry day's`=?, Company=?, `Shelf No.`=?, Quantity=? WHERE SN=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, name.getText());
                ps.setString(2, type.getText());
                ps.setInt(3, Integer.parseInt(price.getText()));
                ps.setInt(4, Integer.parseInt(expiry.getText()));
                ps.setString(5, company.getText());
                ps.setInt(6, Integer.parseInt(shelf.getText()));
                ps.setInt(7, Integer.parseInt(quantity.getText()));
                ps.setInt(8, Integer.parseInt(sn.getText()));
            } else {
                String sql = "INSERT INTO mm_drugs(Name, Type, Price, `Expiry day's`, Company, `Shelf No.`, Quantity) VALUES (?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, name.getText());
                ps.setString(2, type.getText());
                ps.setInt(3, Integer.parseInt(price.getText()));
                ps.setInt(4, Integer.parseInt(expiry.getText()));
                ps.setString(5, company.getText());
                ps.setInt(6, Integer.parseInt(shelf.getText()));
                ps.setInt(7, Integer.parseInt(quantity.getText()));
            }
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, (isUpdate ? "Updated" : "Added") + " successfully!");
            loadTable(currentTable);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving drug.");
        }
    }

    // Add/Update Company
    private void addOrUpdateCompany(boolean isUpdate) {
        JTextField name = new JTextField();
        JTextField address = new JTextField();
        JTextField phone = new JTextField();

        Object[] fields = {"Name:", name, "Address:", address, "Phone:", phone};
        int option = JOptionPane.showConfirmDialog(null, fields, (isUpdate ? "Update" : "Add") + " Company", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps;
            if (isUpdate) {
                String sql = "UPDATE mm_company SET Address=?, `Phone No.`=? WHERE Name=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, address.getText());
                ps.setString(2, phone.getText());
                ps.setString(3, name.getText());
            } else {
                String sql = "INSERT INTO mm_company(Name, Address, `Phone No.`) VALUES (?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, name.getText());
                ps.setString(2, address.getText());
                ps.setString(3, phone.getText());
            }
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, (isUpdate ? "Updated" : "Added") + " successfully!");
            loadTable(currentTable);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving company.");
        }
    }

    // Add/Update Warning
    private void addOrUpdateWarning(boolean isUpdate) {
        JTextField name = new JTextField();
        JTextField type = new JTextField();
        JTextField expiry = new JTextField();
        JTextField quantity = new JTextField();

        Object[] fields = {"Name:", name, "Type:", type, "Expiry days:", expiry, "Quantity:", quantity};
        int option = JOptionPane.showConfirmDialog(null, fields, (isUpdate ? "Update" : "Add") + " Warning", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps;
            if (isUpdate) {
                String sql = "UPDATE mm_warning SET Type=?, `Expiry day's`=?, Quantity=? WHERE Name=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, type.getText());
                ps.setInt(2, Integer.parseInt(expiry.getText()));
                ps.setInt(3, Integer.parseInt(quantity.getText()));
                ps.setString(4, name.getText());
            } else {
                String sql = "INSERT INTO mm_warning(Name, Type, `Expiry day's`, Quantity) VALUES (?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, name.getText());
                ps.setString(2, type.getText());
                ps.setInt(3, Integer.parseInt(expiry.getText()));
                ps.setInt(4, Integer.parseInt(quantity.getText()));
            }
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, (isUpdate ? "Updated" : "Added") + " successfully!");
            loadTable(currentTable);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving warning.");
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        new Admin_GUI();
    }
}
