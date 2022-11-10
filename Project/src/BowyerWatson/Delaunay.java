package BowyerWatson;

import editor.Editor;
import util.edge3D;
import util.triangle3D;
import util.point3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class Delaunay
{

    ArrayList<point3D> pointList;
    ArrayList<triangle3D> triangulation = new ArrayList<>();
    point3D superPointA = new point3D(2,2,2);
    point3D superPointB = new point3D(-2,-2,2);
    point3D superPointC = new point3D(2,-2,2);
    triangle3D superTriangle = new triangle3D(new edge3D(superPointA,superPointB),new edge3D(superPointB,superPointC),new edge3D(superPointC,superPointA));

//Implementation from Wikipedia https://en.wikipedia.org/wiki/Bowyer–Watson_algorithm
    public Delaunay(ArrayList<point3D> pointList)
    {
        this.pointList = pointList;
        triangulation.add(superTriangle);

    }


    private boolean inCircle (point3D a, point3D b,point3D c, point3D d) {
        double ax_ = a.x-d.x;
        double ay_ = a.y-d.y;
        double bx_ = b.x-d.x;
        double by_ = b.y-d.y;
        double cx_ = c.x-d.x;
        double cy_ = c.y-d.y;
        double ans =         (ax_*ax_ + ay_*ay_) * (bx_*cy_-cx_*by_) -
                        (bx_*bx_ + by_*by_) * (ax_*cy_-cx_*ay_) +
                        (cx_*cx_ + cy_*cy_) * (ax_*by_-bx_*ay_);
        if(ans > 0)
            return true;
        else
            return false;
    }
    public void setBW()
    {
        System.out.println(pointList.size());

        for(point3D point : pointList)
        {
            ArrayList<triangle3D> badTriangles = new ArrayList<>();
            for(triangle3D triangle : triangulation)
            {
                if (inCircle(triangle.getEdge().get(0).a, triangle.getEdge().get(1).a, triangle.getEdge().get(2).a, point))
                    badTriangles.add(triangle);
            }
            ArrayList<edge3D> polygon = new ArrayList<>();
            for(triangle3D eachTriangle : badTriangles)
            {
                for(edge3D eachEdge: eachTriangle.getEdge())
                {
                    boolean sharedEdge = false;
                    for(triangle3D otherTriangle: badTriangles)
                    {
                        if(eachTriangle != otherTriangle && otherTriangle.containsEdge(eachEdge))
                        {
                            sharedEdge = true;
                            break;
                        }
                    }
                    if(!sharedEdge)
                        polygon.add(eachEdge);

                }
            }
            for(triangle3D eachTriangle : badTriangles)
            {
                triangulation.remove(eachTriangle);
            }

            for(edge3D eachEdge : polygon)
            {
                triangulation.add(new triangle3D(new edge3D(eachEdge.a,eachEdge.b),new edge3D(eachEdge.b,point),new edge3D(point,eachEdge.a)));
            }

        }
        for(int i = 0; i < triangulation.size()-1; i++)
        {
            for(int a = 0; a < triangulation.get(i).getEdge().size(); a++)
            {
                if(triangulation.get(i).getEdge().get(a).a.x >= 1)
                    triangulation.remove(i);
                if(triangulation.get(i).getEdge().get(a).a.z >= 1)
                    triangulation.remove(i);
                if(triangulation.get(i).getEdge().get(a).a.x <= -1)
                    triangulation.remove(i);
                if(triangulation.get(i).getEdge().get(a).a.z <= -1)
                    triangulation.remove(i);
                if(triangulation.get(i).getEdge().get(a).a.y <= -1)
                    triangulation.remove(i);
                if(triangulation.get(i).getEdge().get(a).a.y > 1)
                    triangulation.remove(i);
                if(triangulation.get(i).getEdge().get(a).b.x >= 1)
                    triangulation.remove(i);
                if(triangulation.get(i).getEdge().get(a).b.z >= 1)
                    triangulation.remove(i);
                if(triangulation.get(i).getEdge().get(a).b.x <= -1)
                    triangulation.remove(i);
                if(triangulation.get(i).getEdge().get(a).b.z <= -1)
                    triangulation.remove(i);
                if(triangulation.get(i).getEdge().get(a).b.y <= -1)
                    triangulation.remove(i);
                if(triangulation.get(i).getEdge().get(a).b.y > 1)
                    triangulation.remove(i);
            }
        }

    }
    public ArrayList<triangle3D> getTriangulation()
    {
        return this.triangulation;
    }

}

