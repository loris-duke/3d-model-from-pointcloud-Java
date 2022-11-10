package editor;

import graphics.carving;
import graphics.render3DMesh;
import graphics.render3DPoints;

import javax.swing.*;
import java.awt.*;

public class MeshView extends JPanel implements Runnable{
    private Graphics2D g2d;
    private Thread gameloop;
    private render3DMesh render;

    private carving carve;
    public MeshView()
    {
        gameloop = new Thread(this);
        gameloop.start();
        render = new render3DMesh();
        carve = new carving();
    }
    @Override
    public void paintComponent(Graphics g0)
    {
        Graphics2D g = (Graphics2D) g0;
        g.clearRect(0,0,getWidth(),getHeight());
        g.setColor(Color.blue);
        g.fillRect(0,0,getWidth(),getHeight());

        render.render(g);

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
