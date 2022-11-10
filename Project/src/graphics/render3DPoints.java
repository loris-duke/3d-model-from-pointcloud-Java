package graphics;

import editor.Application;
import util.matrix;
import util.point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public class render3DPoints
{
    private double rotX = 0.7;
    private double rotY = 0;
    private double rotZ = 3.2;
    private matrix finalCalc;

    private ArrayList<point3D> points = new ArrayList<>();
    public render3DPoints()
    {
        if(Application.depthMap != null)
            for (int x = 0; x < Application.depthMap.getWidth(); x+=10)
                for (int y = 0; y < Application.depthMap.getHeight(); y+=10)
                {
                    Color extract = new Color(Application.depthMap.getRGB(x,y));
                    int depth = extract.getRed();
                    double xa = normalize(x,Application.depthMap.getWidth());
                    double ya = normalize(y,Application.depthMap.getHeight());
                    double za = normalize(depth,250);
                    if(depth > 30)
                        points.add( new point3D(xa,ya,za,new Color(depth,depth,depth)));
                }
        Collections.sort(points);

    }
    double normalize(double value,double extra)
    {
        return (((value - 0) * (1 - -1)) / (extra - 0)) + -1;
    }

    public void render(Graphics g) throws InterruptedException {

        long startTime = System.nanoTime();
            matrix rotationX = new matrix(new double[][]{
                    {1,0,0,0},
                    {0, Math.cos(rotX), -Math.sin(rotX),0},
                    {0, Math.sin(rotX), Math.cos(rotX),0},
                    {0,0,0,1}
            });
            matrix rotationY = new matrix(new double[][]{
                    {Math.cos(rotY), 0, Math.sin(rotY),0},
                    {0, 1, 0,0},
                    {-Math.sin(rotY), 0, Math.cos(rotY),0},
                    {0,0,0,1}
            });
            matrix rotationZ = new matrix(new double[][]{
                    {Math.cos(rotZ), -Math.sin(rotZ), 0,0},
                    {Math.sin(rotZ), Math.cos(rotZ), 0,0},
                    {0, 0 ,1,0},
                    {0,0,0,1}
            });
            int pointsSize = points.size();
            for(int i = 0; i < pointsSize; i+=2)
            {
                matrix perspective = new matrix(new double[][]{
                        {1/(9-points.get(i).z) ,0,0,0},
                        {0,1/(9 -points.get(i).z),0,0},
                        {0,0,1,0},
                        {0,0,0,1}
                });
                finalCalc = new matrix(perspective.multiplyByMatrix(rotationZ.multiplyByMatrix(rotationX.multiplyByMatrix(rotationY.multiplyByMatrix(points.get(i).returnMatrix())))));
                int xa = (int) (finalCalc.getMatrix()[0][0] * 1000);
                int ya = (int) (finalCalc.getMatrix()[1][0] * 1000);
                g.setColor(Color.white);
                g.fillRect(xa + 250,ya+ 220, 1 ,1);
            }


        long elapsedTime = System.nanoTime() - startTime;
        //System.out.println("Total execution time: " + elapsedTime/1000000);

        rotX += 0.01;
        //rotY += 0.01;
        //rotZ += 0.01;


    }



}
