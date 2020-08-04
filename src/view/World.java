package view;

import java.awt.Graphics2D;

/**
 * World class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class World {

    public static final int SIZE = 50;
    public static final int HALF_SIZE = SIZE / 2;
    public static final Vec3 GRAVITY = new Vec3(0, 0.005, 0);

    private final int[][][] blocks = new int[10][10][10]; // [x][y][z]
    private final Vec3 translateTmp = new Vec3(1, -2, -5);

    public World() {
        createTestWorld();
    }
    
    private void createTestWorld() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (x > y) {
                    blocks[x][9 - y][0] = 1;
                }
                blocks[x][0][0] = 1;
            }
        }

        for (int z = 0; z < 10; z++) {
            for (int x = 0; x < 10; x++) {
                blocks[x][9][z] = 1;
            }
        }

        for (int z = 6; z < 9; z++) {
            for (int x = 6; x < 9; x++) {
                blocks[x][8][z] = 1;
            }
        }

        blocks[1][7][8] = 1;
        blocks[1][8][8] = 1;
        blocks[1][6][8] = 1;
        blocks[1][6][7] = 1;
        blocks[1][6][6] = 1;
        blocks[1][6][5] = 1;
        blocks[1][8][5] = 1;
        blocks[1][7][5] = 1;
    }
    
    // very horrible painter's algorithm including player 
    // for rendering the 3D world for a fixed (hardcoded) camera point of view
    public void draw(Graphics2D g, Player player) {
        int px = (int) player.getPosition().x;
        int py = (int) player.getPosition().y;
        int pz = (int) player.getPosition().z;
        
        for (int y = 9; y >= 0; y--) {
            for (int z = 0; z < 10; z++) {
                for (int x = 9; x >= 0; x--) {
                    if (blocks[x][y][z] == 1) {
                        //g.drawRect(x * SIZE, y * SIZE, SIZE, SIZE);
                        translateTmp.set(x - 8 + 15, 0 - y, z - 15);
                        CubeRenderer.draw(g, translateTmp);
                    }
                        
                    if ((px == x && (pz == z || pz == z + 1) && py == y)) {
                        Vec3 position = player.getPosition();
                        translateTmp.set(position.x + 6.5
                                , 0.5 - position.y, position.z - 15.5);
                        CubeRenderer.draw(g, translateTmp);
                    }
               }
            }
        }
    }
    
    public int getBlock(int x, int y, int z) {
        try {
            return blocks[x][y][z];
        }
        catch (Exception e) {
            return 0;
        }
    }
    
}
