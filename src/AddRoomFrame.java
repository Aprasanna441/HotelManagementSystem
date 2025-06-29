import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddRoomFrame extends JFrame {
    JTextField roomNumberField, roomTypeField;

    public AddRoomFrame() {
        setTitle("Add New Room");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Room Number:"));
        roomNumberField = new JTextField();
        add(roomNumberField);

        add(new JLabel("Room Type:"));
        roomTypeField = new JTextField();
        add(roomTypeField);

        JButton addBtn = new JButton("Add Room");
        addBtn.addActionListener(e -> addRoom());
        add(addBtn);

        setVisible(true);
    }

    void addRoom() {
        String number = roomNumberField.getText();
        String type = roomTypeField.getText();

        try (Connection conn = DBConnection.connect()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rooms (room_number, type) VALUES (?, ?)");
            ps.setString(1, number);
            ps.setString(2, type);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Room added successfully!");
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
