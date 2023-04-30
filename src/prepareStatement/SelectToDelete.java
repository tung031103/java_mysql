import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;


import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.Driver;

public class SelectToDelete extends JFrame {
    DefaultTableModel dtmTaiSan;
    JTable tblTaiSan;
    Connection conn = null;

    JMenuItem menuDelete;
    JPopupMenu popupMenu;


    public SelectToDelete(String title){
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
            String sql = "select * from taisan";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            dtmTaiSan.setRowCount(0);
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
        menuDelete = new JMenuItem("Xóa");
        popupMenu = new JPopupMenu();
        popupMenu.add(menuDelete);
        
    }

    private void addEvents() {


        tblTaiSan.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()){
                    int row = tblTaiSan.rowAtPoint(e.getPoint());
                    int column = tblTaiSan.columnAtPoint(e.getPoint());
                    if(!tblTaiSan.isRowSelected(row)){
                        tblTaiSan.changeSelection(row, column, false, false);
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });

        menuDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                xuuLyXoa();
            }
            
        });
    }

    protected void xuuLyXoa() {
        try {
            String ma = tblTaiSan.getValueAt(tblTaiSan.getSelectedRow(), 0) +"";

            String sql = "delete from taisan where ma=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ma);
            int x = preparedStatement.executeUpdate();
            if(x>0){
                JOptionPane.showMessageDialog(null,"Xóa thành công");
                hienThiDanhSachTaiSan();
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void showWindow(){
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
