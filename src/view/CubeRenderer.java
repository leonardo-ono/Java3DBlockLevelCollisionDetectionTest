package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * CubeRenderer class.
 * 
 * Auxiliar class for rendering a 3D partial cube.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class CubeRenderer {

    private static final double HALF_SIZE = 0.5;
    
    static Vec3[] vertices = {
        new Vec3(-HALF_SIZE, -HALF_SIZE, +HALF_SIZE),
        new Vec3(-HALF_SIZE, +HALF_SIZE, +HALF_SIZE),
        new Vec3(+HALF_SIZE, +HALF_SIZE, +HALF_SIZE),
        new Vec3(+HALF_SIZE, -HALF_SIZE, +HALF_SIZE),
        new Vec3(-HALF_SIZE, -HALF_SIZE, -HALF_SIZE),
        new Vec3(-HALF_SIZE, +HALF_SIZE, -HALF_SIZE),
        new Vec3(+HALF_SIZE, +HALF_SIZE, -HALF_SIZE),
        new Vec3(+HALF_SIZE, -HALF_SIZE, -HALF_SIZE),
    };
    
    private static final int[][] EDGES = { 
        {0, 1},
        {1, 2},
        {2, 3},
        {3, 0},
        
        {1, 5},
        {5, 6},
        {6, 2},
        {2, 1},
        
        {4, 5},
        {5, 1},
        {1, 0},
        {0, 4},
    };

    private static final Color[] COLORS = { 
        Color.GRAY, Color.DARK_GRAY, Color.BLACK };
    
    private static final Polygon POLYGON_TMP = new Polygon();
        
    private static final Vec3 PA_TMP = new Vec3();
    private static final Vec3 PB_TMP = new Vec3();
    
    public static void draw(Graphics2D g, Vec3 translate) {
        int face = 0;
        int faceIndex = 0;
        
        for (int[] edge : EDGES) {
            PA_TMP.set(vertices[edge[0]]);
            PB_TMP.set(vertices[edge[1]]);
            
            PA_TMP.x += translate.x;
            PA_TMP.y += translate.y;
            PA_TMP.z += translate.z;
            
            PB_TMP.x += translate.x;
            PB_TMP.y += translate.y;
            PB_TMP.z += translate.z;
            
            PA_TMP.rotateY(Math.toRadians(-45));
            PB_TMP.rotateY(Math.toRadians(-45));
            
            double sx1 = 500 * PA_TMP.x / -PA_TMP.z;
            double sy1 = 500 * PA_TMP.y / PA_TMP.z;
            double sx2 = 500 * PB_TMP.x / -PB_TMP.z;
            double sy2 = 500 * PB_TMP.y / PB_TMP.z;
            
            int a = (int) sx1 + 400;
            int b = (int) sy1 + 30;
            int c = (int) sx2 + 400;
            int d = (int) sy2 + 30;
            
            g.drawLine(a, b, c, d);

            POLYGON_TMP.addPoint(a, b);
            face++;
            
            if (face > 3) {
                face = 0;
                g.setColor(COLORS[faceIndex++]);
                g.fill(POLYGON_TMP);
                POLYGON_TMP.reset();
            }
        }
    }
    
}
