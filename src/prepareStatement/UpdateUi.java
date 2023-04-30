import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.cj.jdbc.Driver;
public class UpdateUi extends JFrame{
    JTextField txtMa, txtTen, txtNgayNhap, txtSoNamKhauHao, txtGiaTri;

    JButton btnLuu;
    
    Connection conn = null;

    public UpdateUi(String title){
        super(title);
        addControls();
        addEvents();
        ketNoi();
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

    private void addEvents() {
        btnLuu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                xuuLyLuu();
            }
            
        });
    }


    protected void xuuLyLuu() {
        try {
            String sql = "update taisan set ten = ?, ngaynhap=?, sonamkhauhao=?, giatri=? where ma=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtTen.getText());
            preparedStatement.setString(2, txtNgayNhap.getText());
            preparedStatement.setInt(3, Integer.parseInt(txtSoNamKhauHao.getText()));
            preparedStatement.setInt(4, Integer.parseInt(txtGiaTri.getText()));
            preparedStatement.setString(5, txtMa.getText());

            int x =preparedStatement.executeUpdate();
            if(x > 0){
                JOptionPane.showMessageDialog(null, "cập nhật tài sản thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void addControls() {
        Container con = getContentPane();
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));

        // field mã 
        JPanel pnMa = new JPanel();
        pnMa.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblMa = new JLabel("Mã tài sản: ");
        txtMa = new JTextField(20);
        pnMa.add(lblMa);
        pnMa.add(txtMa);
        con.add(pnMa);

        JPanel pnTen = new JPanel();
        pnTen.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTen = new JLabel("Tên tài sản: ");
        txtTen = new JTextField(20);
        pnTen.add(lblTen);
        pnTen.add(txtTen);
        con.add(pnTen);

        JPanel pnNgayNhap = new JPanel();
        pnNgayNhap.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblNgayNhap = new JLabel("Ngày nhập: ");
        txtNgayNhap = new JTextField(20);
        pnNgayNhap.add(lblNgayNhap);
        pnNgayNhap.add(txtNgayNhap);
        con.add(pnNgayNhap);

        JPanel pnSoNamKhauHao = new JPanel();
        pnSoNamKhauHao.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblSoNamKhauHao = new JLabel("Số năm khấu hao: ");
        txtSoNamKhauHao = new JTextField(20);
        pnSoNamKhauHao.add(lblSoNamKhauHao);
        pnSoNamKhauHao.add(txtSoNamKhauHao);
        con.add(pnSoNamKhauHao);

        JPanel pnGiaTri = new JPanel();
        pnGiaTri.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblGiaTri = new JLabel("Giá trị tài sản: ");
        txtGiaTri = new JTextField(20);
        pnGiaTri.add(lblGiaTri);
        pnGiaTri.add(txtGiaTri);
        con.add(pnGiaTri);

        lblMa.setPreferredSize(lblSoNamKhauHao.getPreferredSize());
        lblTen.setPreferredSize(lblSoNamKhauHao.getPreferredSize());
        lblNgayNhap.setPreferredSize(lblSoNamKhauHao.getPreferredSize());
        lblGiaTri.setPreferredSize(lblSoNamKhauHao.getPreferredSize());

        JPanel pnButton = new JPanel();
        btnLuu = new JButton("Lưu");
        btnLuu.setFocusable(false);
        pnButton.add(btnLuu);
        con.add(pnButton);

    }


    public void showWindow(){
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(400, 250);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
