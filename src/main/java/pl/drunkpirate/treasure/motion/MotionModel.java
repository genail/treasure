package pl.drunkpirate.treasure.motion;

import org.newdawn.slick.geom.Vector2f;

import pl.drunkpirate.treasure.math.Angle;

public interface MotionModel {
    Vector2f getPosition(Vector2f out);
    void setPosition(Vector2f position);
    
    Angle getRotation(Angle out);
    void setRotation(Angle rotation);
}
