package util;

import java.util.ArrayList;

public class triangle3D {
    private ArrayList<edge3D> normal = new ArrayList<>();

    public triangle3D(edge3D vertex1, edge3D vertex2, edge3D vertex3) {
        this.normal.add(vertex1);
        this.normal.add(vertex2);
        this.normal.add(vertex3);
    }
    public boolean containsEdge(edge3D other)
    {
        return this.normal.get(0).equals(other) ||this.normal.get(1).equals(other) || this.normal.get(2).equals(other);
    }


    public ArrayList<edge3D> getEdge() {
        return normal;
    }
}

