package view;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Collision between Player and 3D Block Level Test.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com);
 */
public class View extends Canvas {
    
    private final World world;
    private final Player player;
    
    private boolean running;
    private BufferStrategy bs;
    
    public View() {
        world = new World();
        player = new Player(world);
        addKeyListener(new Input());
    }
    public void start() {
        createBufferStrategy(2);
        bs = getBufferStrategy();
        running = true;
        new Thread(() -> {
            while (running) {
                update();
                Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                draw(g);
                g.dispose();
                bs.show();
                
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException ex) {
                }
            }
        }).start();
    }

    private void update() {
        player.update();
    }
    
    private void draw(Graphics2D g) {
        g.clearRect(0, 0, getWidth(), getHeight());

        g.drawLine(0, 0, getWidth(), getHeight());
        g.drawLine(0, getHeight(), getWidth(), 0);

        world.draw(g, player);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            View view = new View();
            view.setPreferredSize(new Dimension(800, 600));
            JFrame frame = new JFrame();
            frame.setTitle("Collision between Player and 3D Block Level Test");
            frame.getContentPane().add(view);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            view.requestFocus();
            view.start();
        });
    }
    
}
