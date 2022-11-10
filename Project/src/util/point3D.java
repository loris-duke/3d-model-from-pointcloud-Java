package util;

import java.awt.*;

public class point3D implements Comparable<point3D> {
    public double x,y,z;
    public Color colour;
    public point3D(double x,double y,double z)
    {
        this.x = x; this.y = y; this.z = z;
    }
    public point3D(double x, double y, double z, Color colour)
    {
        this.x = x; this.y = y; this.z = z;
        this.colour = colour;
    }
    public double[][] returnMatrix()
    {
        return new double[][]{
                {x},
                {y},
                {z},
                {1}
        };
    }
    public void outputPoints()
    {
        System.out.println("X "+this.x +" "+"Y "+this.y+" "+"Z"+this.z);
    }

    @Override
    public int compareTo(point3D o) {
        if(o.z< this.z)
            return -1;
        if(o.z > this.z)
            return 1;
        else
            return 0;
    }
}
