import javax.swing.*;

public class DashboardFrame extends JFrame {
    public DashboardFrame() {
        setTitle("Hotel Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton addRoomBtn = new JButton("Add Room");
        JButton bookRoomBtn = new JButton("Book Room");
        JButton checkoutBtn = new JButton("Checkout");
        JButton viewRoomsBtn = new JButton("View Rooms");

        addRoomBtn.addActionListener(e -> new AddRoomFrame());
        bookRoomBtn.addActionListener(e -> new BookRoomFrame());
        checkoutBtn.addActionListener(e -> new CheckOutFrame());
        viewRoomsBtn.addActionListener(e -> new ViewRoomsFrame());

        JPanel panel = new JPanel();
        panel.add(addRoomBtn);
        panel.add(bookRoomBtn);
        panel.add(checkoutBtn);
        panel.add(viewRoomsBtn);

        add(panel);
        setVisible(true);
    }
}
