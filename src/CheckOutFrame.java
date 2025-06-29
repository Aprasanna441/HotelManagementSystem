import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CheckOutFrame extends JFrame {
    JComboBox<String> guestBox;

    public CheckOutFrame() {
        setTitle("Checkout Guest");
        setSize(300, 150);
        setLayout(new GridLayout(2, 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Select Guest:"));
        guestBox = new JComboBox<>();
        loadGuests();
        add(guestBox);

        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.addActionListener(e -> checkout());
        add(checkoutBtn);

        setVisible(true);
    }

    void loadGuests() {
        try (Connection conn = DBConnection.connect()) {
            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT b.id, b.guest_name FROM bookings b " +
                            "JOIN rooms r ON b.room_id = r.id WHERE r.is_booked = TRUE AND b.checkout_date IS NULL");
            while (rs.next()) {
                guestBox.addItem(rs.getInt("id") + "-" + rs.getString("guest_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void checkout() {
        if (guestBox.getSelectedItem() == null) return;
        String selected = (String) guestBox.getSelectedItem();
        int bookingId = Integer.parseInt(selected.split("-")[0]);

        try (Connection conn = DBConnection.connect()) {
            // Get room ID from booking
            PreparedStatement get = conn.prepareStatement("SELECT room_id FROM bookings WHERE id = ?");
            get.setInt(1, bookingId);
            ResultSet rs = get.executeQuery();
            if (rs.next()) {
                int roomId = rs.getInt("room_id");

                // Set checkout date
                PreparedStatement updateBooking = conn.prepareStatement(
                        "UPDATE bookings SET checkout_date = CURDATE() WHERE id = ?");
                updateBooking.setInt(1, bookingId);
                updateBooking.executeUpdate();

                // Mark room as available
                PreparedStatement updateRoom = conn.prepareStatement(
                        "UPDATE rooms SET is_booked = FALSE WHERE id = ?");
                updateRoom.setInt(1, roomId);
                updateRoom.executeUpdate();

                JOptionPane.showMessageDialog(this, "Guest checked out.");
                dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
