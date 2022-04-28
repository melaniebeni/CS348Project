import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.Random;

public class ttt extends JFrame {
    public JSlider slider;
    public JButton oButton, xButton;
    private Board board;
    private Color oColor = Color.BLUE, xColor = Color.RED;
    static final char BLANK = ' ', O = 'O', X = 'X';
    private char position[] = { 
            BLANK, BLANK, BLANK,
            BLANK, BLANK, BLANK,
            BLANK, BLANK, BLANK };
    private int wins = 0, losses = 0, draws = 0; // game count by user

    public ttt() {
        super("Tic Tac Toe ");
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        add(topPanel, BorderLayout.NORTH);
        add(board = new Board(), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

    // Board is what actually plays and displays the game
    private class Board extends JPanel implements MouseListener {
        private Random random = new Random();
        private int rows[][] = { { 0, 2 }, { 3, 5 }, { 6, 8 }, { 0, 6 }, { 1, 7 }, { 2, 8 }, { 0, 8 }, { 2, 6 } };
        // Endpoints of the 8 rows in position[] (across, down, diagonally)

        public Board() {
            addMouseListener(this);
        }

        // Redraw the board
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth();
            int h = getHeight();
            Graphics2D g2d = (Graphics2D) g;

            // Draw the grid
            g2d.setPaint(Color.WHITE);
            g2d.fill(new Rectangle2D.Double(0, 0, w, h));
            g2d.setPaint(Color.BLACK);
            //g2d.setStroke(new BasicStroke(lineThickness));
            g2d.draw(new Line2D.Double(0, h / 3, w, h / 3));
            g2d.draw(new Line2D.Double(0, h * 2 / 3, w, h * 2 / 3));
            g2d.draw(new Line2D.Double(w / 3, 0, w / 3, h));
            g2d.draw(new Line2D.Double(w * 2 / 3, 0, w * 2 / 3, h));

            // Draw the Os and Xs
            for (int i = 0; i < 9; ++i) {
                double xpos = (i % 3 + 0.5) * w / 3.0;
                double ypos = (i / 3 + 0.5) * h / 3.0;
                double xr = w / 8.0;
                double yr = h / 8.0;
                if (position[i] == O) {
                    g2d.setPaint(oColor);
                    g2d.draw(new Ellipse2D.Double(xpos - xr, ypos - yr, xr * 2, yr * 2));
                } else if (position[i] == X) {
                    g2d.setPaint(xColor);
                    g2d.draw(new Line2D.Double(xpos - xr, ypos - yr, xpos + xr, ypos + yr));
                    g2d.draw(new Line2D.Double(xpos - xr, ypos + yr, xpos + xr, ypos - yr));
                }
            }
        }

        // Draw an O where the mouse is clicked
        public void mouseClicked(MouseEvent e) {
            int xpos = e.getX() * 3 / getWidth();
            int ypos = e.getY() * 3 / getHeight();
            int pos = xpos + 3 * ypos;
            if (pos >= 0 && pos < 9 && position[pos] == BLANK) {
                position[pos] = O;
                repaint();
                putX(); // computer plays
                repaint();
            }
        }

        // Ignore other mouse events
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        // Computer plays X
        void putX() {

            // Check if game is over
            if (won(O))
                newGame(O);
            else if (isDraw())
                newGame(BLANK);

            // Play X, possibly ending the game
            else {
                nextMove();
                if (won(X))
                    newGame(X);
                else if (isDraw())
                    newGame(BLANK);
            }
        }

        // Return true if player has won
        boolean won(char player) {
            for (int i = 0; i < 8; ++i)
                if (testRow(player, rows[i][0], rows[i][1]))
                    return true;
            return false;
        }

        // Has player won in the row from position[a] to position[b]?
        boolean testRow(char player, int a, int b) {
            return position[a] == player && position[b] == player
                    && position[(a + b) / 2] == player;
        }

        // Play X in the best spot
        void nextMove() {
            int r = findRow(X); // complete a row of X and win if possible
            if (r < 0)
                r = findRow(O); // or try to block O from winning
            if (r < 0) { // otherwise move randomly
                do
                    r = random.nextInt(9);
                while (position[r] != BLANK);
            }
            position[r] = X;
        }

        // Return 0-8 for the position of a blank spot in a row if the
        // other 2 spots are occupied by player, or -1 if no spot exists
        int findRow(char player) {
            for (int i = 0; i < 8; ++i) {
                int result = find1Row(player, rows[i][0], rows[i][1]);
                if (result >= 0)
                    return result;
            }
            return -1;
        }

        // If 2 of 3 spots in the row from position[a] to position[b]
        // are occupied by player and the third is blank, then return the
        // index of the blank spot, else return -1.
        int find1Row(char player, int a, int b) {
            int c = (a + b) / 2; // middle spot
            if (position[a] == player && position[b] == player && position[c] == BLANK)
                return c;
            if (position[a] == player && position[c] == player && position[b] == BLANK)
                return b;
            if (position[b] == player && position[c] == player && position[a] == BLANK)
                return a;
            return -1;
        }

        // Are all 9 spots filled?
        boolean isDraw() {
            for (int i = 0; i < 9; ++i)
                if (position[i] == BLANK)
                    return false;
            return true;
        }

        // Start a new game
        void newGame(char winner) {
            repaint();

            // Announce result of last game. Ask user to play again.
            String result;
            if (winner == O) {
                ++wins;
                result = "You Win!";
            } else if (winner == X) {
                ++losses;
                result = "I Win!";
            } else {
                result = "Tie";
                ++draws;
            }
            if (JOptionPane.showConfirmDialog(null,
                    "You have " + wins + " wins, " + losses + " losses, " + draws + " draws\n"
                            + "Play again?",
                    result, JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                System.exit(0);
            }

            // Clear the board to start a new game
            for (int j = 0; j < 9; ++j)
                position[j] = BLANK;

            // Computer starts first every other game
            if ((wins + losses + draws) % 2 == 1)
                nextMove();
        }
    } 

    
} 
