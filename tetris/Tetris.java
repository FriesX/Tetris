/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tetris;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author novar
 */
public class Tetris extends JPanel {

    private Tetromino current = Tetromino.randomOne();
    private Tetromino next = Tetromino.randomOne();
    
    private final int row = 20;
    private final int col = 10;
    
    private final Cell[][] wall = new Cell[row][col];
    private static final int CELL_SIZE = 26;
    
    void drawWall(Graphics g) {
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                int x = CELL_SIZE * j;
                int y = CELL_SIZE * i;
                
                Cell cell = wall[i][j];
                if (cell == null) {
                    g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                } else {
                    g.drawImage(cell.getImage(), x, y, CELL_SIZE, CELL_SIZE, null);
                }
            }
        }
    } 
    
    void drawCurrent(Graphics g) {
        Cell[] cells = current.cells;
        for (Cell cell : cells) {
            int x = cell.getCol() * CELL_SIZE;
            int y = cell.getRow() * CELL_SIZE;
            g.drawImage(cell.getImage(), x, y, CELL_SIZE, CELL_SIZE, null);
        }
    }
    
    void drawNext(Graphics g) {
        Cell[] cells = next.cells;
        for (Cell cell : cells) {
            int x = cell.getCol() * CELL_SIZE + 260;
            int y = cell.getRow() * CELL_SIZE + 26;
            g.drawImage(cell.getImage(), x, y, CELL_SIZE, CELL_SIZE, null);
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawWall(g);
        drawCurrent(g);
        drawNext(g);
    }
    
    boolean outOfBound() { // di bawah
        Cell[] cells = current.cells;
        for (Cell cell: cells) {
            int celRow = cell.getRow();
            if (celRow <= 0 || celRow >= row-1){
                return true;
            }
        }
        return false;
    }
    
    boolean tooLeft() { // terlalu ke kiri
        Cell[] cells = current.cells;
        for (Cell cell: cells) {
            int celCol = cell.getCol();
            if (celCol <= 0){
                return true;
            }
        }
        return false;
    }
    
    boolean tooRight() { // terlalu ke kanan
        Cell[] cells = current.cells;
        for (Cell cell: cells) {
            int celCol = cell.getCol();
            if (celCol >= col-1){
                return true;
            }
        }
        return false;
    }
    
    boolean coincide() { // kalau ketemu bidak lainnya
        Cell[] cells = current.cells;
        for (Cell cell: cells) {
            int celCol = cell.getCol();
            int celRow = cell.getRow();
            if (wall[celRow][celCol] != null){
                return true;
            }
        }
        return false;
    }
    
    boolean isDrop() { // pemeriksaan kesempatan turun
        Cell[] cells = current.cells;
        for (Cell cell: cells) {
            int celCol = cell.getCol();
            int celRow = cell.getRow();
            
            if(celRow == row-1) {
                return false;
            }
            
            if (wall[celRow+1][celCol] != null){
                return false;
            }
        }
        return true;
    }
    
    void stopDropping () { // jika bidak sudah sampai bawah
        Cell[] cells = current.cells;
        for(Cell cell : cells) {
            int celrow = cell.getRow();
            int celcol = cell.getCol();
            wall[celrow][celcol] = cell;
        }
    }
    
    protected void softDrop() {
        if(isDrop()) {
            current.softDrop();
        } else {
            stopDropping();
            current = next;
            next = Tetromino.randomOne();
        }        
    }
    
    protected void moveLeft() {
        if(!tooLeft() && !outOfBound() && !coincide())
            current.moveLeft();
    }
    
    protected void moveRight() {
        if(!tooRight() && !outOfBound() && !coincide())
            current.moveRight();
    }
    
    public void start() {
        KeyListener keylist = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent args) {
                int key = args.getKeyCode();
                
                switch(key) {
                    case KeyEvent.VK_DOWN : {
                        softDrop();
                        break;
                    } case KeyEvent.VK_LEFT : {
                        moveLeft();
                        break;
                    } case KeyEvent.VK_RIGHT : {
                        moveRight();
                        break;
                    }
                }
                repaint();
            }
        };
        
        this.addKeyListener(keylist);
        this.requestFocus();
        
        new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    
                    if (isDrop()){
                        softDrop();
                    } else {
                        stopDropping();
                        current = next;
                        next = Tetromino.randomOne();
                    }
                    repaint();
                }
            }
        }.start();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new JFrame("Tetris");
        frame.setSize(530, 580);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Tetris tetrisPanel = new Tetris();
        frame.add(tetrisPanel);
        frame.setVisible(true);
        tetrisPanel.start();
    }
    
}
