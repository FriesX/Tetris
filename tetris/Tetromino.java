/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tetris;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author novar
 */
public class Tetromino {
    protected Cell[] cells;
    
    public Tetromino() {
        cells = new Cell[4];
    }
    
    public void moveLeft() {
        for(Cell cell : cells) {
            cell.left();
        }
    }
    
    public void moveRight() {
        for(Cell cell : cells) {
            cell.right();
        }
    }
    
    public void softDrop() {
        for(Cell cell : cells) {
            cell.down();
        }
    }
    
    public static Tetromino randomOne() {
        Tetromino t = null;
        int n = (int) (Math.random() * 7);
        switch(n) {
            case 0: t = new Shape_I(); break;
            case 1: t = new Shape_J(); break;
            case 2: t = new Shape_L(); break;
            case 3: t = new Shape_O(); break;
            case 4: t = new Shape_S(); break;
            case 5: t = new Shape_Z(); break;
            case 6: t = new Shape_T(); break;
        }
        return t;
    }
    
    public static BufferedImage I;
    public static BufferedImage J;
    public static BufferedImage L;
    public static BufferedImage O;
    public static BufferedImage S;
    public static BufferedImage Z;
    public static BufferedImage T;
    
    static {
        try {
            I = ImageIO.read(new File("resources/I.png"));
            J = ImageIO.read(new File("resources/J.png"));
            L = ImageIO.read(new File("resources/L.png"));
            O = ImageIO.read(new File("resources/O.png"));
            S = ImageIO.read(new File("resources/S.png"));
            Z = ImageIO.read(new File("resources/Z.png"));
            T = ImageIO.read(new File("resources/T.png"));            
        } catch (IOException ex) {
            Logger.getLogger(Tetromino.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
}
