package view;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import static view.World.GRAVITY;

/**
 * Player class.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class Player {

    private static final double SPEED = 0.05;
    
    private final World world;
    
    private final Vec3 position = new Vec3(3, 3, 0);
    private final Vec3 positionTmp = new Vec3();
    private final Vec3 velocity = new Vec3();
    private final Vec3 velocityTmp = new Vec3();
    
    private static final double PLAYER_SIZE = 0.5;
    
    private final Vec3[] deltaPositions = {
        new Vec3(-PLAYER_SIZE, -PLAYER_SIZE, -PLAYER_SIZE),
        new Vec3(-PLAYER_SIZE, +PLAYER_SIZE, -PLAYER_SIZE),
        new Vec3(+PLAYER_SIZE, +PLAYER_SIZE, -PLAYER_SIZE),
        new Vec3(+PLAYER_SIZE, -PLAYER_SIZE, -PLAYER_SIZE),
        new Vec3(-PLAYER_SIZE, -PLAYER_SIZE, +PLAYER_SIZE),
        new Vec3(-PLAYER_SIZE, +PLAYER_SIZE, +PLAYER_SIZE),
        new Vec3(+PLAYER_SIZE, +PLAYER_SIZE, +PLAYER_SIZE),
        new Vec3(+PLAYER_SIZE, -PLAYER_SIZE, +PLAYER_SIZE),
    };

    private final Vec3 translateTmp = new Vec3(1, -2, -5);

    public Player(World world) {
        this.world = world;
    }

    public Vec3 getPosition() {
        return position;
    }
    
    public void update() {
        updateControl();
        
        // update gravity
        velocity.y += GRAVITY.y;
        if (velocity.y > 2) {
            velocity.y = 2;
        }
        
        // resolve collision with 3D block level
        for (Vec3 deltaPosition : deltaPositions) {
            positionTmp.set(position);
            positionTmp.add(deltaPosition);
            positionTmp.add(velocity);
            
            velocityTmp.set(velocity);
            //velocityTmp.normalize();
            velocityTmp.scale(0.025);

            while (isWorldBlocked(positionTmp, 0, 0, 0, 1)) { 
                positionTmp.sub(velocityTmp);
            }

            if((velocity.y > 0 && isWorldBlocked(positionTmp, 0, 0.01, 0, 1))
                || (velocity.y < 0 
                    && isWorldBlocked(positionTmp, 0, -0.01, 0, 1))) {
                
                velocity.y = 0;
            }
            if((velocity.x > 0 && isWorldBlocked(positionTmp, 0.01, 0, 0, 1))
                || (velocity.x < 0 
                    && isWorldBlocked(positionTmp, -0.01, 0, 0, 1))) {
                
                velocity.x = 0;
            }
            if((velocity.z > 0 && isWorldBlocked(positionTmp, 0, 0, 0.01, 1))
                || (velocity.z < 0 
                    && isWorldBlocked(positionTmp, 0, 0, -0.01, 1))) {
                
                velocity.z = 0;
            }
        }

        // update position
        position.add(velocity);
    }
    
    private void updateControl() {
        velocity.x = 0;
        velocity.z = 0;
        
        if (Input.isKeyPressed(KeyEvent.VK_LEFT) 
                && isWorldBlocked(position, -SPEED - PLAYER_SIZE, 0, 0, 0)) {
            
            velocity.x = -SPEED;
        }
        else if (Input.isKeyPressed(KeyEvent.VK_RIGHT) 
                && isWorldBlocked(position, SPEED + PLAYER_SIZE, 0, 0, 0)) {
            
            velocity.x = SPEED;
        }

        if (Input.isKeyPressed(KeyEvent.VK_UP) 
                && isWorldBlocked(position, 0, 0, -SPEED - PLAYER_SIZE, 0)) {
            
            velocity.z = -SPEED;
        }
        else if (Input.isKeyPressed(KeyEvent.VK_DOWN) 
                && isWorldBlocked(position, 0, 0, SPEED + PLAYER_SIZE, 0)) {
            
            velocity.z = SPEED;
        }        

        // jump
        if (Input.isKeyPressed(KeyEvent.VK_SPACE)) {
            velocity.y = -3 * SPEED;
        }
    }
    
    private boolean isWorldBlocked(Vec3 pos
            , double dx, double dy, double dz, int block) {
        
        int ix = (int) (pos.x + dx);
        int iy = (int) (pos.y + dy);
        int iz = (int) (pos.z + dz);
        return world.getBlock(ix, iy, iz) == block;
    }
    
    public void draw(Graphics2D g) {
        translateTmp.set(position.x + 6.5
                , 0.5 - position.y, position.z - 15.5);
        CubeRenderer.draw(g, translateTmp);
    }
    
}
