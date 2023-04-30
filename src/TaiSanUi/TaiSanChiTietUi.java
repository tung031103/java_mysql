import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



import java.sql.ResultSet;


public class TaiSanChiTietUi extends JDialog{

    JTextField txtMa, txtTen, txtNgayNhap, txtSoNamKhauHao, txtGiaTri;
    JButton btnLuu;

    Connection conn = TaiSanUi.conn;
    Statement statement = TaiSanUi.statement;
    public String maTaiSanChon = "";
    public static int ketQua = -1;



    public TaiSanChiTietUi(String title){
        this.setTitle(title);
        addControls();
        addEvents();
        if(maTaiSanChon.length() != 0){
            hienThiThongTinChiTiet();
            
        }
    }

    public void hienThiThongTinChiTiet() {
        try {
            String sql = "SELECT * FROM taisan where ma= '"+ maTaiSanChon +"'";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if(result.next()){
                txtMa.setText(result.getString(1));
                txtTen.setText(result.getString(2));
                txtNgayNhap.setText(result.getDate(3)+"");
                txtSoNamKhauHao.setText(result.getInt(4)+"");
                txtGiaTri.setText(result.getInt(5)+"");
            }
            txtMa.setEditable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addEvents() {
        btnLuu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                xuuLyLuu();
            }
        });

    }

    protected void xuuLyLuu() {
        if(maTaiSanChon.length()==0){
            try {
                String sql = "insert into taisan values('" + txtMa.getText() + "', '" + txtTen.getText() + "', '" + txtNgayNhap.getText() + "', '" + txtSoNamKhauHao.getText() + "','" + txtGiaTri.getText()+"');"; 
                statement = conn.createStatement();
                int x = statement.executeUpdate(sql);
                ketQua = x; // 
                dispose(); //nếu x = -1 thì ẩn màn hình
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                String sql = "update taisan set ten ='" + txtTen.getText()+"'"
                +", NgayNhap='"+ txtNgayNhap.getText() +"'"
                +", SoNamKhauHao='"+ txtSoNamKhauHao.getText()+"'"
                +", GiaTri= '"+ txtGiaTri.getText()+"'"
                + " where ma='"+txtMa.getText()+"'";
                statement = conn.createStatement();
                int x = statement.executeUpdate(sql);
                ketQua = x; // 
                dispose(); //nếu x = -1 thì ẩn màn hình
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addControls() {
        Container con = getContentPane();
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));

        JPanel pnMa = new JPanel();
        pnMa.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lbMa = new JLabel("Mã tài sản: ");
        txtMa = new JTextField(20);
        pnMa.add(lbMa);
        pnMa.add(txtMa);
        con.add(pnMa);

        JPanel pnTen = new JPanel();
        pnTen.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lbTen = new JLabel("Tên tài sản: ");
        txtTen = new JTextField(20);
        pnTen.add(lbTen);
        pnTen.add(txtTen);
        con.add(pnTen);

        JPanel pnNgayNhap = new JPanel();
        pnNgayNhap.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lbNgayNhap = new JLabel("Ngày nhập: ");
        txtNgayNhap = new JTextField(20);
        pnNgayNhap.add(lbNgayNhap);
        pnNgayNhap.add(txtNgayNhap);
        con.add(pnNgayNhap);

        JPanel pnSoNamKhauHao = new JPanel();
        pnSoNamKhauHao.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lbSoNamKhauHao = new JLabel("Số năm khấu hao: ");
        txtSoNamKhauHao = new JTextField(20);
        pnSoNamKhauHao.add(lbSoNamKhauHao);
        pnSoNamKhauHao.add(txtSoNamKhauHao);
        con.add(pnSoNamKhauHao);

        JPanel pnGiaTri = new JPanel();
        pnGiaTri.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lbGiaTri = new JLabel("Giá trị tài sản: ");
        txtGiaTri = new JTextField(20);
        pnGiaTri.add(lbGiaTri);
        pnGiaTri.add(txtGiaTri);
        con.add(pnGiaTri);

        lbMa.setPreferredSize(lbSoNamKhauHao.getPreferredSize());
        lbTen.setPreferredSize(lbSoNamKhauHao.getPreferredSize());
        lbNgayNhap.setPreferredSize(lbSoNamKhauHao.getPreferredSize());
        lbGiaTri.setPreferredSize(lbSoNamKhauHao.getPreferredSize());

        JPanel pnButton = new JPanel();
        pnButton.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnLuu = new JButton("Lưu tài sản");
        btnLuu.setFocusable(false);
        pnButton.add(btnLuu);
        con.add(pnButton);

    }

    public void showWindow(){
        this.setSize(400, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setVisible(true);
    }
}
