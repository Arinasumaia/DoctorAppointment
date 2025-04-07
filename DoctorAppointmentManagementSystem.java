import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class DoctorAppointmentManagementSystem extends JFrame {
    private final ArrayList<String[]> doctors = new ArrayList<>();
    private final ArrayList<String[]> patients = new ArrayList<>();
    private final ArrayList<String[]> appointments = new ArrayList<>();

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private DefaultTableModel docTableModel, patTableModel, appTableModel;

    private final JComboBox<String> docList = new JComboBox<>();
    private final JComboBox<String> patList = new JComboBox<>();

    public DoctorAppointmentManagementSystem() {
        setTitle("Doctor Appointment Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel.add(createMenuPanel(), "Menu");
        mainPanel.add(createDoctorPanel(), "DoctorPanel");
        mainPanel.add(createPatientPanel(), "PatientPanel");
        mainPanel.add(createAppointmentPanel(), "AppointmentPanel");
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createMenuPanel() {
        ImageIcon bgIcon = new ImageIcon("D:/OOP Erina/src/doctor.jpg");
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JButton docBtn = styledButton("Register Doctor");
        docBtn.addActionListener(e -> cardLayout.show(mainPanel, "DoctorPanel"));
        gbc.gridy = 0;
        panel.add(docBtn, gbc);

        JButton patBtn = styledButton("Register Patient");
        patBtn.addActionListener(e -> cardLayout.show(mainPanel, "PatientPanel"));
        gbc.gridy = 1;
        panel.add(patBtn, gbc);

        JButton appBtn = styledButton("Make Appointment");
        appBtn.addActionListener(e -> cardLayout.show(mainPanel, "AppointmentPanel"));
        gbc.gridy = 2;
        panel.add(appBtn, gbc);

        return panel;
    }

    private JPanel createDoctorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 250, 255));
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(220, 235, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField name = styledTextField();
        JComboBox<String> gender = new JComboBox<>(new String[]{"Male", "Female"});
        JTextField spec = styledTextField();
        JTextField mbbs = styledTextField();

        String[] labels = {"Name:", "Gender:", "Specialization:", "MBBS Year:"};
        JComponent[] fields = {name, gender, spec, mbbs};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            form.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            form.add(fields[i], gbc);
        }

        JButton add = styledButton("Register");
        JButton back = styledButton("Back");

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(220, 235, 245));
        btnPanel.add(add);
        btnPanel.add(back);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        form.add(btnPanel, gbc);
        panel.add(form, BorderLayout.NORTH);

        docTableModel = new DefaultTableModel(new String[]{"Name", "Gender", "Specialization", "MBBS Year"}, 0);
        JTable table = new JTable(docTableModel);
        styleTable(table);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        add.addActionListener(e -> {
            String[] data = {name.getText(), (String) gender.getSelectedItem(), spec.getText(), mbbs.getText()};
            doctors.add(data);
            docTableModel.addRow(data);
            docList.addItem(data[0]);
            name.setText("");
            gender.setSelectedIndex(0);
            spec.setText("");
            mbbs.setText("");
        });

        back.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 245, 250));
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 220, 235));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField name = styledTextField();
        JComboBox<String> gender = new JComboBox<>(new String[]{"Male", "Female"});
        JTextField disease = styledTextField();
        JTextField age = styledTextField();

        String[] labels = {"Name:", "Gender:", "Disease:", "Age:"};
        JComponent[] fields = {name, gender, disease, age};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            form.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            form.add(fields[i], gbc);
        }

        JButton add = styledButton("Register");
        JButton back = styledButton("Back");

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(245, 220, 235));
        btnPanel.add(add);
        btnPanel.add(back);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        form.add(btnPanel, gbc);
        panel.add(form, BorderLayout.NORTH);

        patTableModel = new DefaultTableModel(new String[]{"Name", "Gender", "Disease", "Age"}, 0);
        JTable table = new JTable(patTableModel);
        styleTable(table);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        add.addActionListener(e -> {
            String[] data = {name.getText(), (String) gender.getSelectedItem(), disease.getText(), age.getText()};
            patients.add(data);
            patTableModel.addRow(data);
            patList.addItem(data[0]);
            name.setText("");
            gender.setSelectedIndex(0);
            disease.setText("");
            age.setText("");
        });

        back.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    // UPDATED Appointment Panel with AM/PM time slots
    private JPanel createAppointmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 255, 240));
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(220, 245, 220));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> day = new JComboBox<>();
        JComboBox<String> month = new JComboBox<>();
        JComboBox<String> timeSlot = new JComboBox<>();
        JTextField fee = styledTextField();
        fee.setText("800");
        fee.setEditable(false);

        for (int i = 1; i <= 31; i++) day.addItem(String.format("%02d", i));
        for (int i = 1; i <= 12; i++) month.addItem(String.format("%02d", i));

        String[] times = {
            "09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM",
            "11:30 AM", "12:00 PM", "12:30 PM", "01:00 PM", "01:30 PM",
            "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM",
            "04:30 PM", "05:00 PM", "05:30 PM"
        };
        for (String t : times) timeSlot.addItem(t);

        String[] labels = {"Doctor:", "Patient:", "Day:", "Month:", "Time:", "Fee:"};
        JComponent[] fields = {docList, patList, day, month, timeSlot, fee};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            form.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            form.add(fields[i], gbc);
        }

        JButton addAppointment = styledButton("Add Appointment");
        JButton back = styledButton("Back");

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(220, 245, 220));
        btnPanel.add(addAppointment);
        btnPanel.add(back);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        form.add(btnPanel, gbc);
        panel.add(form, BorderLayout.NORTH);

        appTableModel = new DefaultTableModel(new String[]{"S.No", "Doctor", "Patient", "Date", "Time", "Fee"}, 0);
        JTable table = new JTable(appTableModel);
        styleTable(table);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        addAppointment.addActionListener(e -> {
            int serial = appointments.size() + 1;
            String dateStr = "2025-" + month.getSelectedItem() + "-" + day.getSelectedItem();
            String timeStr = (String) timeSlot.getSelectedItem();
            String[] appointmentData = {
                String.valueOf(serial),
                (String) docList.getSelectedItem(),
                (String) patList.getSelectedItem(),
                dateStr,
                timeStr,
                fee.getText()
            };
            appointments.add(appointmentData);
            appTableModel.addRow(appointmentData);
        });

        back.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        return panel;
    }

    private JTextField styledTextField() {
        JTextField tf = new JTextField(15);
        tf.setFont(new Font("Arial", Font.PLAIN, 16));
        return tf;
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(new Color(100, 150, 220));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(180, 40));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setContentAreaFilled(true);
        return btn;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setBackground(new Color(180, 200, 240));
        header.setForeground(Color.BLACK);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer(center);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DoctorAppointmentManagementSystem::new);
    }

    public ArrayList<String[]> getPatients() {
        return patients;
    }

    public ArrayList<String[]> getDoctors() {
        return doctors;
    }

    public JComboBox<String> getPatList() {
        return patList;
    }
}
