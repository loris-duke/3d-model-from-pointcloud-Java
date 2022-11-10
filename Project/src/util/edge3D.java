package util;

public class edge3D {
    public point3D a;
    public point3D b;
    private static final double D = 0.01;
    public edge3D(point3D a, point3D b)
    {
        this.a = a;
        this.b = b;
    }
    @Override
    public boolean equals(Object other) {
        if (other instanceof edge3D) {
            return ((Math.abs(this.a.x - ((edge3D) other).a.x) < D && Math.abs(this.a.y - ((edge3D) other).a.y) < D) && (Math.abs(this.b.x - ((edge3D) other).b.x) < D && Math.abs(this.b.y - ((edge3D) other).b.y) < D)) ||
                    ((Math.abs(this.b.x - ((edge3D) other).a.x) < D && Math.abs(this.b.y - ((edge3D) other).a.y) < D) && (Math.abs(this.a.x -((edge3D) other).b.x) < D && Math.abs(this.a.y - ((edge3D) other).b.y) < D));
        }
        return false;
    }

}
