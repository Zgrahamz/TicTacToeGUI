import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame implements ActionListener {
    JPanel mainPanel, titlePanel, gridPanel, dialoguePanel, controlPanel;
    JButton[][] board;
    JButton playAgainButton, quitButton;
    JLabel titleLabel;
    JTextArea dialogueArea;
    JScrollPane scroller;
    private JFrame dialogueFrame;

    private static final int ROW = 3;
    private static final int COL = 3;

    private boolean finished = false;
    private boolean playing = true;
    private String player = "X";
    private boolean xPlayer = true;
    private int moveCnt = 0;
    private int row = 1;
    private int col = 1;
    private final int MOVES_FOR_WIN = 5;
    private final int MOVES_FOR_TIE = 7;

    public TicTacToeFrame() {
        setTitle("Tic-Tac-Toe");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        createTitlePanel();
        createGridPanel();
        createControlPanel();
        setVisible(true);
    }

    //A single event listener corresponds to all buttons in the array
    public JButton[][] boardSetup() {
        JButton[][] instBoard = new JButton[ROW][COL];
        board = instBoard;
        return instBoard;
    }

    public JButton[][] clearBoard() {
        JButton[][] instClearedBoard = new JButton[ROW][COL];
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                board[r][c].setText(" ");
                board[r][c].setFont(new Font("Times New Roman", Font.PLAIN, 36));
                board[r][c].addActionListener(this);
                board[r][c].setEnabled(true);
                if(!board[r][c].isEnabled() && !board[r][c].isSelected()) {
                    JOptionPane.showMessageDialog(dialogueFrame, "Invalid Move", "Error", JOptionPane.ERROR_MESSAGE);
                }
                gridPanel.add(board[r][c]);
            }
        }
        board = instClearedBoard;
        return instClearedBoard;
    }

    public void actionPerformed(ActionEvent e) {
        JButton boardBtn = (JButton) e.getSource();
        if (xPlayer) {
            boardBtn.setText("X");
            player = "X";
        } else {
            boardBtn.setText("O");
            player = "O";
        }
        boardBtn.setEnabled(false);
        xPlayer = !xPlayer;

        moveCnt++;

        if (moveCnt >= MOVES_FOR_WIN) {
            isWin(player);
        }
    }

    private void createTitlePanel() {
        titlePanel = new JPanel();
        titleLabel = new JLabel("Tic-Tac-Toe", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font("Times New Roman", 1, 36));
        titleLabel.setVerticalTextPosition(JLabel.BOTTOM);
        titleLabel.setHorizontalTextPosition(JLabel.CENTER);

        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
    }


    private void createGridPanel() {
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3));
        boardSetup();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new JButton(" ");
                board[i][j].setFont(new Font("Times New Roman", Font.PLAIN, 36));
                board[i][j].addActionListener(this);
                gridPanel.add(board[i][j]);

                //if()
            }
        }
        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }

    private void createControlPanel() {
        controlPanel = new JPanel();

        dialogueArea = new JTextArea(10, 45);
        scroller = new JScrollPane(dialogueArea);

        controlPanel.add(scroller);

        playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener((ActionEvent ae) -> {
            clearBoard();
        });

        quitButton = new JButton("Quit");
        quitButton.addActionListener((ActionEvent ae) -> System.exit(0));

        controlPanel.add(playAgainButton);
        controlPanel.add(quitButton);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);
    }




    private void buttonThing () {
        for(int f = 0; f < 3; f++) {
            for (int g = 0; g < 3; g++) {

            }
        }
    }


    private boolean isValidMove(int row, int col) {
        for (int r = 0; r < ROW; r++) {
            for (int colu = 0; colu < COL; colu++) {
                if (!board[r][colu].isEnabled() && board[row][col].isSelected()) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(dialogueFrame, "Invalid Move", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }



    private boolean isWin(String player) {
        if (isColWin(player) || isRowWin(player) || isDiagnalWin(player)) {
            JOptionPane.showMessageDialog(dialogueFrame, player + "Wins! Press 'play again' to restart.", "Finished1", JOptionPane.ERROR_MESSAGE);
        } else if (isTie()) {
            JOptionPane.showMessageDialog(dialogueFrame, "It's a tie! Press 'play again' to restart.", "Finished2", JOptionPane.ERROR_MESSAGE);
        }
        return isColWin(player) || isRowWin(player) || isDiagnalWin(player);
    }

    private boolean isColWin(String player)
    {
        // checks for a col win for specified player
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].getText().equals(player) &&
                    board[1][col].getText().equals(player) &&
                    board[2][col].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no col win
    }
    private boolean isRowWin(String player)
    {
        // checks for a row win for the specified player
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].getText().equals(player) &&
                    board[row][1].getText().equals(player) &&
                    board[row][2].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }
    private boolean isDiagnalWin(String player)
    {
        // checks for a diagonal win for the specified player

        if(board[0][0].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][2].getText().equals(player) )
        {
            return true;
        }
        if(board[0][2].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][0].getText().equals(player) )
        {
            return true;
        }
        return false;
    }

    // checks for a tie before board is filled.
    // check for the win first to be efficient
    private boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].getText().equals("X") ||
                    board[row][1].getText().equals("X") ||
                    board[row][2].getText().equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].getText().equals("O") ||
                    board[row][1].getText().equals("O") ||
                    board[row][2].getText().equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].getText().equals("X") ||
                    board[1][col].getText().equals("X") ||
                    board[2][col].getText().equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].getText().equals("O") ||
                    board[1][col].getText().equals("O") ||
                    board[2][col].getText().equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(board[0][0].getText().equals("X") ||
                board[1][1].getText().equals("X") ||
                board[2][2].getText().equals("X") )
        {
            xFlag = true;
        }
        if(board[0][0].getText().equals("O") ||
                board[1][1].getText().equals("O") ||
                board[2][2].getText().equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(board[0][2].getText().equals("X") ||
                board[1][1].getText().equals("X") ||
                board[2][0].getText().equals("X") )
        {
            xFlag =  true;
        }
        if(board[0][2].getText().equals("O") ||
                board[1][1].getText().equals("O") ||
                board[2][0].getText().equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }

    private void gameLoop () {

    do  // game loop
    {
        // get the move
        do
        {


            System.out.println("Enter move for " + player);
            new TicTacToeTile(row, col);
        }while(!isValidMove(row, col));
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                player = board[i][j].getText();
            }
        }

        moveCnt++;

        if(moveCnt >= MOVES_FOR_WIN)
        {
            if(isWin(player))
            {

                System.out.println("Player " + player + " wins!");
                playing = false;
            }
        }
        if(moveCnt >= MOVES_FOR_TIE)
        {
            if(isTie())
            {

                System.out.println("It's a Tie!");
                playing = false;
            }
        }
        if(player.equals("X"))
        {
            player = "O";
        }
        else
        {
            player = "X";
        }

    }while(playing);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if(board[i][j].getText().equals("X") || board[i][j].getText().equals("O")) {
                    dialogueArea.append("Finished playing? Click the play again button to go again,or the quit button to close.");
                }
            }
        }

    if(playAgainButton.isSelected())
    {
        finished = false;
    } else {
        finished = true;
    }while(!finished);
    }
}
