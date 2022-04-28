
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class rps extends JFrame implements ActionListener {

    private JButton rockButton, paperButton, scissorsButton;
    private JTextField textField, textField2;
    int wins, losses, ties;
    public void createGUI() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container window = getContentPane();
        window.setLayout(new FlowLayout());
        setTitle("Rock Paper Scissors");
        setLocation(500, 0);

        textField = new JTextField(15);
        window.add(textField);

        textField2 = new JTextField(15);
        window.add(textField2);

        Icon rock = new ImageIcon("rock.png");
        rockButton = new JButton(rock);
        window.add(rockButton);
        rockButton.addActionListener(this);

        Icon paper = new ImageIcon("paper.png");
        paperButton = new JButton(paper);
        window.add(paperButton);
        paperButton.addActionListener(this);

        Icon scissors = new ImageIcon("scissors.png");
        scissorsButton = new JButton(scissors);
        window.add(scissorsButton);
        scissorsButton.addActionListener(this);

    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        int playerChoice;
        int compChoice;
        String winner;
        Random randomSeed = new Random();
        if (source == rockButton) {
            playerChoice = 0;
        } else if (source == paperButton) {
            playerChoice = 1;
        } else {
            playerChoice = 2;
        }
        compChoice = randomSeed.nextInt(3);
        winner = findWinner(playerChoice, compChoice);
        textField.setText(winner + "!");

        if (compChoice == 0) {
            textField2.setText("Computer chooses rock");
        } else if (compChoice == 1) {
            textField2.setText("Computer chooses paper");
        } else {
            textField2.setText("Computer chooses scissors");
        }

    }

    private String findWinner(int playerChoice, int compChoice) {
        String winner;
        
        if (playerChoice == compChoice) {
            winner = "It's a tie";
            ++ties;
            score(winner);
        } else if (playerChoice == 0 && compChoice == 1) {
            winner = "Computer Won";
            ++losses;
            score(winner);
        } else if (playerChoice == 1 && compChoice == 2) {
            winner = "Computer Won";
            ++losses;
            score(winner);
        } else if (playerChoice == 2 && compChoice == 0) {
            winner = "Computer Won";
            ++losses;
            score(winner);
        } else {
            winner = "You Win";
            ++wins;
            score(winner);
        }
        return winner;
    }
    private void score(String result){
        if (JOptionPane.showConfirmDialog(null,
                    "You have " + wins + " wins, " + losses + " losses, " + ties + " draws\n"
                            + "Play again?",
                    result, JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                System.exit(0);
            }

    }
}