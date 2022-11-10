package editor;

import graphics.carving;
import graphics.render3DPoints;

import javax.swing.*;
import java.awt.*;

public class PerspectiveView extends JPanel implements Runnable
{
    private Graphics2D g2d;
    private Thread gameloop;
    private render3DPoints render;

    private carving carve;
    public PerspectiveView()
    {
        gameloop = new Thread(this);
        gameloop.start();
        render = new render3DPoints();
        carve = new carving();
    }
    @Override
    public void paintComponent(Graphics g0)
    {
        Graphics2D g = (Graphics2D) g0;
        g.clearRect(0,0,getWidth(),getHeight());
        g.setColor(Color.blue);
        g.fillRect(0,0,getWidth(),getHeight());

        try {
            render.render(g);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void run()
    {
        while(true)
        {
            repaint();
        }
    }
}
