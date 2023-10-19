import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasketballScoreboardGUI extends JFrame {
    private int teamAScore = 0;
    private int teamBScore = 0;
    private int quarter = 0;
    private JLabel teamAScoreLabel;
    private JLabel teamBScoreLabel;
    private JLabel quarterLabel;
    private Timer timer;
    private int remainingMinutes = 12;
    private int remainingSeconds = 0;
    private boolean timerRunning = false;
    private JLabel remainingTimeLabel;
    private JLabel currentTimeLabel;


    public BasketballScoreboardGUI() {
        setTitle("Basketball Scoreboard");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout()); // Use GridBagLayout

        JLabel teamALabel = new JLabel("HOME");
        teamAScoreLabel = new JLabel(Integer.toString(teamAScore));

        JLabel teamBLabel = new JLabel("VISITOR");
        teamBScoreLabel = new JLabel(Integer.toString(teamBScore));

        JButton teamAScorePlus1Button = new JButton("+1");
        JButton teamAScoreMinus1Button = new JButton("-1");

        JButton teamBScorePlus1Button = new JButton("+1");
        JButton teamBScoreMinus1Button = new JButton("-1");

        quarterLabel = new JLabel(Integer.toString(quarter));

        JButton quarterPlus1Button = new JButton("+1");
        JButton quarterMinus1Button = new JButton("-1");
        JLabel quarterLabelName = new JLabel("QUARTER");

        remainingTimeLabel = new JLabel("12:00");

        JButton stopButton = new JButton("Stop");
        JButton pauseButton = new JButton("Pause");
        JButton resetButton = new JButton("Reset");

        teamAScorePlus1Button.addActionListener(e -> updateScore(true, teamAScoreLabel));
        teamAScoreMinus1Button.addActionListener(e -> updateScore(false, teamAScoreLabel));
        teamBScorePlus1Button.addActionListener(e -> updateScore(true, teamBScoreLabel));
        teamBScoreMinus1Button.addActionListener(e -> updateScore(false, teamBScoreLabel));

        quarterPlus1Button.addActionListener(e -> incrementQuarter());
        quarterMinus1Button.addActionListener(e -> decrementQuarter());

        stopButton.addActionListener(e -> stopTimer());
        pauseButton.addActionListener(e -> pauseTimer());
        resetButton.addActionListener(e -> resetTimer());

        currentTimeLabel = new JLabel("Current Time: 00:00:00"); // Initialize the label
        addComponent(currentTimeLabel, 0, 8, 5); // Add it to the layout
        currentTimeLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text

        timer = new Timer(1000, e -> {
            updateRemainingTime();
            updateCurrentTimeLabel();
        });
        //timer.start();

        addComponent(teamALabel, 1, 0, 1); // Set grid width to 1
        addComponent(teamAScoreLabel, 1, 1, 1);
        addComponent(teamAScorePlus1Button, 0, 0, 1);
        addComponent(teamAScoreMinus1Button, 0, 1, 1);
        addComponent(teamBLabel, 4, 0, 1);
        addComponent(teamBScoreLabel, 4, 1, 1);
        addComponent(teamBScorePlus1Button, 3, 0, 1);
        addComponent(teamBScoreMinus1Button, 3, 1, 1);
        addComponent(quarterLabelName, 1, 3, 2);
        addComponent(quarterLabel, 1, 4, 1);
        addComponent(quarterPlus1Button, 3, 3, 1);
        addComponent(quarterMinus1Button, 3, 4, 1);
        addComponent(stopButton, 2, 6, 1);
        addComponent(pauseButton, 3, 6, 1);
        addComponent(resetButton, 4, 6, 1);
        addComponent(remainingTimeLabel, 0, 6, 2); // Span across 3 columns

        teamAScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        teamBScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quarterLabel.setHorizontalAlignment(SwingConstants.CENTER);

        setVisible(true);
    }

    private void addComponent(Component component, int gridx, int gridy, int gridwidth) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(component, gbc);
    }

    private void updateScore(boolean increment, JLabel label) {
        int score = Integer.parseInt(label.getText());
        score = increment ? score + 1 : score - 1;
        label.setText(Integer.toString(score));
    }

    private void incrementQuarter() {
        if (quarter < 4) {
            quarter++;
            quarterLabel.setText(Integer.toString(quarter));
            startTimer(); // Start the timer when incrementing the quarter
        }
    }

    private void decrementQuarter() {
        if (quarter > 0) {
            quarter--;
            quarterLabel.setText(Integer.toString(quarter));
        }
    }

    private void startTimer() {
        timerRunning = true;
        timer.start();
    }

    private void stopTimer() {
        timer.stop();
        timerRunning = false;
        remainingMinutes = 0;
        remainingSeconds = 0;
        updateRemainingTimeLabel();
    }

    private void pauseTimer() {
        timer.stop();
    }

    private void resetTimer() {
        timer.stop();
        timerRunning = false;
        remainingMinutes = 12;
        remainingSeconds = 0;
        updateRemainingTimeLabel();

        // Reset Team A, Team B, and Quarter to 0
        teamAScore = 0;
        teamBScore = 0;
        quarter = 0;
        teamAScoreLabel.setText("0");
        teamBScoreLabel.setText("0");
        quarterLabel.setText("0");
    }


    private void updateRemainingTime() {
        if (remainingMinutes == 0 && remainingSeconds == 0) {
            stopTimer();
        } else {
            if (remainingSeconds == 0) {
                remainingMinutes--;
                remainingSeconds = 59;
            } else {
                remainingSeconds--;
            }
            updateRemainingTimeLabel();
        }
    }

    private void updateRemainingTimeLabel() {
        DecimalFormat df = new DecimalFormat("00");
        remainingTimeLabel.setText(df.format(remainingMinutes) + ":" + df.format(remainingSeconds));
    }

    private void updateCurrentTimeLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        currentTimeLabel.setText("Current Time: " + currentTime);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BasketballScoreboardGUI();
        });
    }
}
