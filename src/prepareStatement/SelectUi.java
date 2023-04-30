import java.awt.BorderLayout;
import java.awt.Container;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.Driver;

public class SelectUi extends JFrame {
    DefaultTableModel dtmTaiSan;
    JTable tblTaiSan;
    Connection conn = null;


    public SelectUi(String title){
        super(title);
        addControls();
        addEvents();
        ketNoi();
        hienThiDanhSachTaiSan();
    }

    private void ketNoi() {
        try {
            String strConnect = "jdbc:mysql://localhost/dbtaisan";
            Properties pro = new Properties();
            pro.put("user", "root");
            pro.put("password", "");
            Driver driver = new Driver();
            conn = driver.connect(strConnect, pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hienThiDanhSachTaiSan() {
        try {
            String sql = "select * from taisan where giatri > ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,50);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Object []arr = {resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getDate(3),
                                resultSet.getInt(4),
                                resultSet.getInt(5)};
                dtmTaiSan.addRow(arr);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addControls() {
        Container con = getContentPane();
        con.setLayout(new BorderLayout());
        dtmTaiSan = new DefaultTableModel();
        dtmTaiSan.addColumn("Mã tài sản");
        dtmTaiSan.addColumn("Tên tài sản");
        dtmTaiSan.addColumn("Ngày nhập");
        dtmTaiSan.addColumn("Năm khấu hao");
        dtmTaiSan.addColumn("Giá trị");

        tblTaiSan = new JTable(dtmTaiSan);
        JScrollPane sc = new JScrollPane(tblTaiSan,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        con.add(sc,BorderLayout.CENTER);
    }

    private void addEvents() {

    }

    public void showWindow(){
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
