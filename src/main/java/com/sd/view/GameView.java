package main.java.com.sd.view;

import main.java.com.sd.board.Board;

import java.awt.*;
import javax.swing.*;

import static main.java.com.sd.board.Board.BOARD_HEIGHT;
import static main.java.com.sd.board.Board.BOARD_WIDTH;

public class GameView extends JPanel {
    private final int SQ_SIZE = 64;
    private Board board;

    private JPanel[][] viewSquares = new JPanel[8][8];

    private JPanel boardBackground;

    public GameView(Board board) {
        this.board =board;
        JFrame frame = new JFrame("JavaChess");
        //frame.addKeyListener(new InputHandler(board));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setFocusable(true);

        frame.setResizable(false);
        frame.requestFocus();
        frame.requestFocusInWindow();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SQ_SIZE * BOARD_HEIGHT, SQ_SIZE * BOARD_WIDTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int counter = BOARD_HEIGHT-1;
        for (int i = 0; i < BOARD_WIDTH; i += 1) {
            for (int j = 0; j < BOARD_HEIGHT; j += 1) {
                if ((i + j + 1) % 2 == 0) {
                    g.setColor(Color.BLACK);
                    g.fillRect(j * SQ_SIZE, i * SQ_SIZE, SQ_SIZE, SQ_SIZE);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(j * SQ_SIZE, i * SQ_SIZE, SQ_SIZE, SQ_SIZE);
                }

                if (board.getSquare(counter,j).getCurrentPiece() != null) {
                    g.drawImage(board.getSquare(counter, j).getCurrentPiece().pieceSprite, j * SQ_SIZE, i * SQ_SIZE, this);
                }
            }
            counter--;
        }
    }

    public void updateBoard(Board board) {
        this.board = board;
    }


    public JPanel getBoard() {
        return boardBackground;
    }
}
