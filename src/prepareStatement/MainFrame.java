import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

    JButton btnSelect, btnInsert, btnUpdate, btnDelete;

    public MainFrame(String title){
        super(title);
        addControls();
        addEvents();

    }

    private void addEvents() {
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectUi ui = new SelectUi("Màn hình select cho prepareStatement");
                ui.showWindow();
            }
        });

        btnInsert.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                InsertUI ui = new InsertUI("Màn hình insert cho prepareStatement");
                ui.showWindow();
            }
            
        });

        btnUpdate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateUi ui = new UpdateUi("màn hình update cho PrepareStatement");
                ui.showWindow();
            }
            
        });

        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SelectToDelete ui = new SelectToDelete("Màn hình Delete cho PrepareStatement");
                ui.showWindow();
            }
            
        });

    }

    private void addControls() {
        Container con = getContentPane();
        JPanel pnMain = new JPanel();
        pnMain.setLayout(new FlowLayout());
        btnSelect = new JButton("PreparedStatement - select");
        btnInsert = new JButton("PreparedStatement - insert");
        btnUpdate = new JButton("PreparedStatement - update");
        btnDelete = new JButton("PreparedStatement - delete");
        pnMain.add(btnSelect);
        pnMain.add(btnInsert);
        pnMain.add(btnUpdate);
        pnMain.add(btnDelete);
        con.add(pnMain);

    }

    public void showWindow(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
