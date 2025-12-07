import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class FocusBuddy {

    private static Timer studyTimer;
    private static Timer breakTimer;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Focus Buddy – Study Timer (Yellow Mode)");
        frame.setSize(420, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().setBackground(new Color(255, 230, 120)); // soft yellow bg

        JLabel title = new JLabel("Focus Buddy – Study Timer");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.BLACK);

        JLabel inputLabel = new JLabel("Enter study duration (in minutes):");
        inputLabel.setForeground(Color.BLACK);

        JTextField minutesField = new JTextField(10);
        minutesField.setBackground(new Color(255, 240, 160)); // lighter yellow
        minutesField.setForeground(Color.BLACK);
        minutesField.setCaretColor(Color.BLACK);

        JButton startButton = new JButton("Start Timer");
        startButton.setBackground(new Color(255, 210, 80)); // darker yellow
        startButton.setForeground(Color.BLACK);

        JLabel timerLabel = new JLabel("Time left: ~m ~s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.BLACK);

        frame.add(title);
        frame.add(inputLabel);
        frame.add(minutesField);
        frame.add(startButton);
        frame.add(timerLabel);

        String[] quotes = {
                "A bright start leads to a brilliant finish.",
                "Stay glowing, stay growing.",
                "Every second counts — shine through.",
                "Consistency is golden.",
                "Your hard work lights the way."
        };

        Random rand = new Random();

        startButton.addActionListener(e -> {
            try {
                int minutes = Integer.parseInt(minutesField.getText());
                int seconds = minutes * 60;

                startButton.setEnabled(false);

                studyTimer = new Timer(1000, new ActionListener() {
                    int timeLeft = seconds;

                    public void actionPerformed(ActionEvent e) {
                        int m = timeLeft / 60;
                        int s = timeLeft % 60;

                        timerLabel.setText("Time left: " + m + "m " + s + "s");

                        if (timeLeft <= 0) {
                            studyTimer.stop();
                            timerLabel.setText("Time's up!");

                            String quote = quotes[rand.nextInt(quotes.length)];
                            JOptionPane.showMessageDialog(
                                    frame,
                                    "Time's up!\n\n" + quote,
                                    "Study Session Complete",
                                    JOptionPane.PLAIN_MESSAGE
                            );

                            askBreak(frame, timerLabel, startButton);
                        }

                        timeLeft--;
                    }
                });

                studyTimer.start();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
            }
        });

        frame.setVisible(true);
    }

    private static void askBreak(JFrame frame, JLabel timerLabel, JButton startButton) {
        int choice = JOptionPane.showConfirmDialog(
                frame,
                "Would you like to take a 5-minute break?",
                "Break Time",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            startBreak(frame, timerLabel, startButton);
        } else {
            JOptionPane.showMessageDialog(frame, "Okay, stay productive.");
            startButton.setEnabled(true);
        }
    }

    private static void startBreak(JFrame frame, JLabel timerLabel, JButton startButton) {

        breakTimer = new Timer(1000, new ActionListener() {
            int timeLeft = 300;

            public void actionPerformed(ActionEvent e) {
                int m = timeLeft / 60;
                int s = timeLeft % 60;

                timerLabel.setText("Break time left: " + m + "m " + s + "s");

                if (timeLeft <= 0) {
                    breakTimer.stop();
                    JOptionPane.showMessageDialog(frame, "Break over. Time to focus again.");
                    startButton.setEnabled(true);
                }

                timeLeft--;
            }
        });

        breakTimer.start();
    }
}
