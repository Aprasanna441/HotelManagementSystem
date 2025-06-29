import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BookRoomFrame extends JFrame {
    JTextField guestField;
    JComboBox<String> roomBox;

    public BookRoomFrame() {
        setTitle("Book Room");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Guest Name:"));
        guestField = new JTextField();
        add(guestField);

        add(new JLabel("Select Room:"));
        roomBox = new JComboBox<>();
        loadAvailableRooms();
        add(roomBox);

        JButton bookBtn = new JButton("Book Room");
        bookBtn.addActionListener(e -> bookRoom());
        add(bookBtn);

        setVisible(true);
    }

    void loadAvailableRooms() {
        try (Connection conn = DBConnection.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT room_number FROM rooms WHERE is_booked = FALSE");

            while (rs.next()) {
                roomBox.addItem(rs.getString("room_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void bookRoom() {
        String guest = guestField.getText();
        String roomNumber = (String) roomBox.getSelectedItem();

        if (guest.isEmpty() || roomNumber == null) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            // Get room ID
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM rooms WHERE room_number = ?");
            ps.setString(1, roomNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int roomId = rs.getInt("id");

                // Insert booking
                PreparedStatement insert = conn.prepareStatement(
                        "INSERT INTO bookings (guest_name, room_id, checkin_date) VALUES (?, ?, CURDATE())");
                insert.setString(1, guest);
                insert.setInt(2, roomId);
                insert.executeUpdate();

                // Mark room as booked
                PreparedStatement update = conn.prepareStatement(
                        "UPDATE rooms SET is_booked = TRUE WHERE id = ?");
                update.setInt(1, roomId);
                update.executeUpdate();

                JOptionPane.showMessageDialog(this, "Room booked!");
                dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
