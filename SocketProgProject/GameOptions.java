import javax.swing.*;

public class GameOptions extends JPanel {
    int totalCells = 9;
    int totalRows = 3;
    static JTextField t;

    static JFrame f = new JFrame("Game Center");
    //Game option buttons
    static JButton ttt;

    static JButton rps;

    public GameOptions() {
       
        // not formating correctly
        f.getContentPane().setLayout(new BoxLayout(
            f.getContentPane(), BoxLayout.Y_AXIS)

        );
     
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
        ttt = new JButton("Tic-Tac-Toe");

        rps = new JButton("Rock Paper Sissors");
        //button actions
        ttt.addActionListener(e -> {
             new ttt();         
        });

        rps.addActionListener(e -> {
            rps paper = new rps();
            paper.setSize(500,250); 
            paper.createGUI();
            paper.setVisible(true);
           
        });
        JPanel p = new JPanel();
        p.add(ttt);
        p.add(rps);
        f.add(p);

        // set the size of frame
        f.setBounds(200, 200, 300, 250);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }

}
