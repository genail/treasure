package pl.drunkpirate.treasure.motion;

import java.util.logging.Logger;

import pl.drunkpirate.treasure.math.Angle;

class FreeMotionState implements MotionState {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(FreeMotionState.class.getName());
    
    private boolean keyState;
    
    float x, y;
    float vx, vy;
    final Angle rotation = new Angle();

    public FreeMotionState() {
    }
    
    public FreeMotionState(FreeMotionState other) {
        set(other);
    }
    
    private void set(FreeMotionState other) {
        x = other.x;
        y = other.y;
        vx = other.vx;
        vy = other.vy;
        rotation.set(other.rotation);
    }
    
    @Override
    public boolean isKeyState() {
        return keyState;
    }
}
