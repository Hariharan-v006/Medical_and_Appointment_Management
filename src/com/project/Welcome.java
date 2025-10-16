import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome implements ActionListener {
    JFrame frame;
    JLabel welcome, to, mms, medical;
    JPanel panel;
    JButton next;

    public Welcome() {
        frame = new JFrame();
        panel = new JPanel();
        next = new JButton("Next");

        welcome = new JLabel("Welcome");
        to = new JLabel("To");
        mms = new JLabel("E - Health Care");

        welcome.setFont(new Font("Bebas Neue", Font.BOLD, 80));
        to.setFont(new Font("Bebas Neue", Font.BOLD, 80));
        mms.setFont(new Font("Bebas Neue", Font.BOLD, 90));

        medical = new JLabel(new ImageIcon(new ImageIcon("D:\\Desktop\\APP project\\src\\images\\medical.png")
                .getImage().getScaledInstance(512, 512, Image.SCALE_DEFAULT)));

        panel.setLayout(null);
        panel.setBackground(new Color(48, 125, 126));
        panel.setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, new Color(0, 128, 0)));

        next.setBounds(1150, 600, 80, 30);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.setFocusable(false);
        next.addActionListener(this);

        welcome.setBounds(450, 150, 700, 80);
        to.setBounds(580, 300, 400, 50);
        mms.setBounds(200, 400, 1000, 85);
        medical.setBounds(350, 80, 512, 512);

        panel.add(welcome);
        panel.add(to);
        panel.add(mms);
        panel.add(medical);
        panel.add(next);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            new Login();
            frame.dispose();
        }
    }
}
