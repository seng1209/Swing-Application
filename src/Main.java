import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main extends JFrame implements ActionListener {
    private ResultSet rs;

    private JButton btnPrevious;
    private JButton btnNext;
    private JLabel lblID, lblLastName, lblFirstName, lblPhone;
    private JTextField txtID, txtLastName, txtFirstName, txtPhone;

    Main(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel border = new JPanel(new BorderLayout(10,10));

        JPanel grid = new JPanel(new GridLayout(4,1));

        lblID = new JLabel("ID:");
        lblLastName = new JLabel("Last Name:");
        lblFirstName = new JLabel("First Name:");
        lblPhone = new JLabel("Phone:");

        txtID = new JTextField();
        txtLastName = new JTextField();
        txtFirstName = new JTextField();
        txtPhone = new JTextField();

        grid.add(lblID);
        grid.add(txtID);
        grid.add(lblLastName);
        grid.add(txtLastName);
        grid.add(lblFirstName);
        grid.add(txtFirstName);
        grid.add(lblPhone);
        grid.add(txtPhone);

        JPanel flow = new JPanel(new FlowLayout(10, 50, 10));

        btnPrevious = new JButton("Previous");
        btnPrevious.setPreferredSize(new Dimension(100, 30));
        btnPrevious.addActionListener(this);
        btnNext = new JButton("Next");
        btnNext.setPreferredSize(new Dimension(100,30));
        btnNext.addActionListener(this);

        flow.add(btnPrevious);
        flow.add(btnNext);

        border.add(grid, BorderLayout.CENTER);
        border.add(flow, BorderLayout.SOUTH);
        this.add(border);
        this.setSize(400, 200);
        this.setTitle("Customer");
        this.setVisible(true);
        this.disabledFields();
        this.getCustomer();
    }

    public void getCustomer(){
        try{
            Connection con  = GetConnection.getConnection();
            String query = "SELECT * FROM Customer";
            Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(query);
            rs.first();
            txtID.setText(rs.getString(1));
            txtLastName.setText(rs.getString(2));
            txtFirstName.setText(rs.getString(3));
            txtPhone.setText(rs.getString(4));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void displayCustomer() throws SQLException {
        txtID.setText(rs.getString(1));
        txtLastName.setText(rs.getString(2));
        txtFirstName.setText(rs.getString(3));
        txtPhone.setText(rs.getString(4));
    }

    public void disabledFields(){
        txtID.setEditable(false);
        txtLastName.setEditable(false);
        txtFirstName.setEditable(false);
        txtPhone.setEditable(false);
    }

    public static void main(String[] args) {

        new Main();

        try{
            Connection con = GetConnection.getConnection();
            Statement stmt=con.createStatement();
            String createTable = "CREATE TABLE Customer " +
                    "(customer_id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "customer_last_name VARCHAR (30), " +
                    "customer_first_name VARCHAR (30), " +
                    "customer_phone VARCHAR (16)) ";
//          Create Table
//          stmt.execute(createTable);

            String select = "SELECT * FROM Customer";

            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = statement.executeQuery(select);

            // Insert Statement

            rs.moveToInsertRow();

            rs.updateString("customer_last_name", "Chanda");
            rs.updateString("customer_first_name", "Sovisal");
            rs.updateString("customer_phone", "092888999");
            rs.insertRow();

            rs.moveToInsertRow();
            rs.updateString("customer_last_name", "Kom");
            rs.updateString("customer_first_name", "Lina");
            rs.updateString("customer_phone", "092000999");
            rs.insertRow();

            rs.moveToInsertRow();
            rs.updateString("customer_last_name", "Chan");
            rs.updateString("customer_first_name", "Seyha");
            rs.updateString("customer_phone", "092777666");
            rs.insertRow();

            rs.moveToCurrentRow();

            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPrevious){
            try {
                if (rs.previous()){
                    this.displayCustomer();
                    btnNext.setEnabled(true);
                }else if (rs.first()){
                    btnPrevious.setEnabled(false);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == btnNext){
            try {
                if (rs.next()){
                    this.displayCustomer();
                    btnPrevious.setEnabled(true);
                }else if (rs.last()){
                    btnNext.setEnabled(false);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}