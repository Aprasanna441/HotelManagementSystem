import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewRoomsFrame extends JFrame {
    JTable table;

    public ViewRoomsFrame() {
        setTitle("All Rooms");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        table = new JTable();
        loadRooms();

        add(new JScrollPane(table));
        setVisible(true);
    }

    void loadRooms() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Room #", "Type", "Booked"}, 0);
        try (Connection conn = DBConnection.connect()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM rooms");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("room_number"),
                        rs.getString("type"),
                        rs.getBoolean("is_booked") ? "Yes" : "No"
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
