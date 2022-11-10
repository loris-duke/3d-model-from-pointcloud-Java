package graphics;

import BowyerWatson.Delaunay;
import editor.Application;
import util.matrix;
import util.triangle3D;
import util.point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class render3DMesh {
    private ArrayList<point3D> points = new ArrayList<>();
    private ArrayList<triangle3D> mesh = new ArrayList<>();
    private double rotX = 0.2;
    private double rotY = 0.1;
    private double rotZ = 0.01;


    double normalize(double value,double extra)
    {
        return (((value - 0) * (1 - -1)) / (extra - 0)) + -1;
    }
    public render3DMesh()
    {

        if(Application.depthMap != null)
            for (int x = 0; x < Application.depthMap.getWidth(); x+=5)
                for (int y = 0; y < Application.depthMap.getHeight(); y+=5)
                {
                    Color extract = new Color(Application.depthMap.getRGB(x,y));
                    int depth = extract.getRed();
                    double xa = normalize(x,Application.depthMap.getWidth());
                    double ya = normalize(y,Application.depthMap.getHeight());
                    double za = normalize(depth,250);
                    if(xa < 1 && xa > -1 && ya < 1 && ya > -1 && za < 1 && za > -1)
                        points.add( new point3D(xa,ya,za,new Color(depth,depth,depth)));
                }
        System.out.println(points.size());
        Delaunay delaunay = new Delaunay(points);
        delaunay.setBW();
        mesh = delaunay.getTriangulation();
        //NearestNeighborSearch();
    }


    public void NearestNeighborSearch()
    {
        for(int a = 0; a < points.size(); a++)
        {
            point3D nearestPoint = new point3D(99999999, 99999999, 99999999);
            double nearestPointDist = 99999999;

            point3D nearestPoint1 = new point3D(99999999, 99999999, 99999999);
            double nearestPointDist1 = 99999999;

            for (int i = 0; i < points.size(); i++)
            {

                if(i != a)
                {
                    double dist = Math.sqrt(Math.pow(points.get(i).x - points.get(a).x, 2) + Math.pow(points.get(i).y - points.get(a).y, 2)
                            + Math.pow(points.get(i).z - points.get(a).z, 2));
                    if(dist < nearestPointDist)
                    {
                        nearestPointDist = dist;
                        nearestPoint = points.get(i);

                    }
                }
            }
            for (int i = 0; i < points.size(); i++)
            {

                if(i != a && points.get(i).x != nearestPoint.x && points.get(i).y != nearestPoint.y  && points.get(i).z != nearestPoint.z)
                {
                    double dist = Math.sqrt(Math.pow(points.get(i).x - points.get(a).x, 2) + Math.pow(points.get(i).y - points.get(a).y, 2)
                            + Math.pow(points.get(i).z - points.get(a).z, 2));
                    if(dist < nearestPointDist1)
                    {
                        nearestPointDist1 = dist;
                        nearestPoint1 = points.get(i);

                    }
                }
            }
            //mesh.add(new triangle3D(points.get(a),nearestPoint,nearestPoint1));
        }
    }

    public void render(Graphics2D g)
    {
        matrix finalCalc1;
        matrix finalCalc2;
        matrix finalCalc3;
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
        for(int a = 0; a < mesh.size(); a++)
        {

            matrix perspective = new matrix(new double[][]{
                    {1/(6-mesh.get(a).getEdge().get(0).a.z) ,0,0,0},
                    {0,1/(6 -mesh.get(a).getEdge().get(0).a.z),0,0},
                    {0,0,1,0},
                    {0,0,0,1}
            });

            finalCalc1 = new matrix(perspective.multiplyByMatrix(rotationZ.multiplyByMatrix(rotationY.multiplyByMatrix(rotationX.multiplyByMatrix(mesh.get(a).getEdge().get(0).a.returnMatrix())))));
            finalCalc2 = new matrix(perspective.multiplyByMatrix(rotationZ.multiplyByMatrix(rotationY.multiplyByMatrix(rotationX.multiplyByMatrix(mesh.get(a).getEdge().get(1).a.returnMatrix())))));
            finalCalc3 = new matrix(perspective.multiplyByMatrix(rotationZ.multiplyByMatrix(rotationY.multiplyByMatrix(rotationX.multiplyByMatrix(mesh.get(a).getEdge().get(2).a.returnMatrix())))));

            if(finalCalc1.getMatrix()[0][0] < 2 && finalCalc1.getMatrix()[0][0] > -2 && finalCalc1.getMatrix()[1][0] < 2 && finalCalc1.getMatrix()[1][0] > -2 && finalCalc1.getMatrix()[2][0] < 2 && finalCalc1.getMatrix()[2][0] > -2)
                if(finalCalc2.getMatrix()[0][0] < 1 && finalCalc2.getMatrix()[0][0] > -1 && finalCalc2.getMatrix()[1][0] < 1 && finalCalc2.getMatrix()[1][0] > -1)
                    if(finalCalc3.getMatrix()[0][0] < 1 && finalCalc3.getMatrix()[0][0] > -1 && finalCalc3.getMatrix()[1][0] < 1 && finalCalc3.getMatrix()[1][0] > -1)
            {


                int x1 = (int) (finalCalc1.getMatrix()[0][0] * 1000) + 200;
                int y1 = (int) (finalCalc1.getMatrix()[1][0] * 1000) + 200;

                int x2 = (int) (finalCalc2.getMatrix()[0][0] * 1000) + 200;
                int y2 = (int) (finalCalc2.getMatrix()[1][0] * 1000) + 200;

                int x3 = (int) (finalCalc3.getMatrix()[0][0] * 1000) + 200;
                int y3 = (int) (finalCalc3.getMatrix()[1][0] * 1000) + 200;
                g.setColor(Color.white);

                int xa[] = {x1, x2, x3};
                int ya[] = {y1, y2, y3};

                Polygon p = new Polygon(xa, ya, 3);
                g.drawPolygon(p);
            }

        }
        rotY+=0.1;
        //rotX += 0.001;
        //rotZ += 0.01;
    }
}
