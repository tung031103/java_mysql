
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.Driver;




public class TaiSanUi extends JFrame{


    DefaultTableModel dtmTaiSan;
    JTable tblTaiSan;

    public static Connection conn = null;
    public static Statement statement = null;

    JButton btnThemMoi,btnSua;

    JMenuItem menuEdit, menuDelete;
    JPopupMenu popupMenu;

    public TaiSanUi(String title){
        super(title);
        addControls();
        addEvents();
        ketNoiCSDLMySQL();
        hienThiToanBoTaiSan();
    }

    private void hienThiToanBoTaiSan() {
        try {
            String sql = "SELECT * FROM taisan";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            dtmTaiSan.setRowCount(0);
            while(resultSet.next()){
                Vector<Object> vec = new Vector<Object>();
                vec.add(resultSet.getString(1));
                vec.add(resultSet.getString(2));
                vec.add(resultSet.getDate(3));
                vec.add(resultSet.getInt(4));
                vec.add(resultSet.getInt(5));
                dtmTaiSan.addRow(vec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ketNoiCSDLMySQL() {
        try {
            String strConn = "jdbc:mysql://localhost/dbtaisan";
            Properties pro = new Properties();
            pro.put("user", "root");
            pro.put("password", "");
            Driver driver = new Driver();
            conn = driver.connect(strConn, pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addControls(){
        Container con = getContentPane();
        con.setLayout(new BorderLayout());
        JPanel pnNorth  = new JPanel();
        JLabel lbTitle = new JLabel("Chương trình quản lý tài sản");
        lbTitle.setFont(new Font("serif", Font.BOLD, 20));
        lbTitle.setForeground(Color.blue);
        pnNorth.add(lbTitle);
        con.add(pnNorth, BorderLayout.NORTH);

        dtmTaiSan = new DefaultTableModel();
        dtmTaiSan.addColumn("Mã tài sản");
        dtmTaiSan.addColumn("Tên tài sản");
        dtmTaiSan.addColumn("Ngày nhập");
        dtmTaiSan.addColumn("Số năm khấu hao");
        dtmTaiSan.addColumn("Trị giá");

        tblTaiSan = new JTable(dtmTaiSan);
        JScrollPane scTable = new JScrollPane(tblTaiSan,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        con.add(scTable, BorderLayout.CENTER);     

        JPanel pnButton = new JPanel();
        pnButton.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnThemMoi = new JButton("Thêm mới");
        btnSua = new JButton("Sửa thông tin");
        pnButton.add(btnThemMoi);
        pnButton.add(btnSua);
        btnThemMoi.setFocusable(false);
        btnSua.setFocusable(false);
        con.add(pnButton, BorderLayout.SOUTH);

        popupMenu = new JPopupMenu();
        menuEdit = new JMenuItem("Chỉnh sửa");
        menuDelete = new JMenuItem("Xóa");
        popupMenu.add(menuEdit);
        popupMenu.addSeparator();
        popupMenu.add(menuDelete);
    }

    private void addEvents(){
        btnThemMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TaiSanChiTietUi ui = new TaiSanChiTietUi("Thông tin chi tiết tài sản");
                ui.showWindow();
                if(TaiSanChiTietUi.ketQua>0){
                    hienThiToanBoTaiSan();
                }
            }            
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblTaiSan.getSelectedRow();
                if(row == -1) return;
                String ma = tblTaiSan.getValueAt(row, 0)+"";
                TaiSanChiTietUi ui = new TaiSanChiTietUi("Thông tin chi tiết tài sản");
                ui.maTaiSanChon = ma;
                ui.hienThiThongTinChiTiet();
                ui.showWindow();
                if(TaiSanChiTietUi.ketQua>0){
                    hienThiToanBoTaiSan();
                }
            }
            
        });

        tblTaiSan.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()){ // vừa nhấn chuột phải
                    int row = tblTaiSan.rowAtPoint(e.getPoint());
                    int column = tblTaiSan.columnAtPoint(e.getPoint());
                    if(!tblTaiSan.isRowSelected(row)){
                        tblTaiSan.changeSelection(row, column, false, false);
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
        });

        menuEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btnSua.doClick();
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
        String ma = tblTaiSan.getValueAt(tblTaiSan.getSelectedRow(), 0) + "";
        try {
            String sql = "delete from taisan where ma = '"+ma+"'";
            statement=conn.createStatement();
            int x = statement.executeUpdate(sql);
            if(x > 0){
                hienThiToanBoTaiSan();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showWindow(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
