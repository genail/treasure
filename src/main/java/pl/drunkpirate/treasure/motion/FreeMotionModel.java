package pl.drunkpirate.treasure.motion;

import java.util.logging.Logger;

import org.newdawn.slick.geom.Vector2f;

import pl.drunkpirate.treasure.math.Angle;
import pl.drunkpirate.treasure.math.Easing;
import pl.drunkpirate.treasure.math.Func1;
import pl.drunkpirate.treasure.math.Time;
import pl.drunkpirate.treasure.math.TimeUnit;

public class FreeMotionModel implements MotionModel {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(FreeMotionModel.class.getName());
    
    private final Func1 accelFunc = Easing.REGULAR_IN;
    private final Time accelTime = new Time(2, TimeUnit.SECOND);
    
    private final Func1 slowDownFunc = Easing.REGULAR_OUT;
    private final Time slowDownTime = new Time(5, TimeUnit.SECOND);
    
    private FreeMotionState currentState;
    

    @Override
    public Vector2f getPosition(Vector2f out) {
        out.x = currentState.x;
        out.y = currentState.y;
        return out;
    }
    
    @Override
    public void setPosition(Vector2f position) {
        FreeMotionState newState = new FreeMotionState(currentState);
        newState.x = position.x;
        newState.y = position.y;
        currentState = newState;
    }

    @Override
    public Angle getRotation(Angle out) {
        out.set(currentState.rotation);
        return out;
    }
    
    @Override
    public void setRotation(Angle rotation) {
        FreeMotionState newState = new FreeMotionState(currentState);
        newState.rotation.set(rotation);
        currentState = newState;
    }
}
