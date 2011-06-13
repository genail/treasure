package pl.drunkpirate.treasure.math;

import java.io.Serializable;
import java.util.logging.Logger;

public class Angle implements Serializable {
    private static final long serialVersionUID = 1L;
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(Angle.class.getName());
    
    public float angleRadians;
    
    public static Angle fromRadians(float radians) {
        Angle angle = new Angle();
        angle.setRadians(radians);
        
        return angle;
    }
    
    public static Angle fromDegrees(float degrees) {
        Angle angle = new Angle();
        angle.setDegrees(degrees);
        
        return angle;
    }
    
    public Angle() {
        // empty
    }
    
    public void set(Angle face) {
        angleRadians = face.angleRadians;
    }
    
    public void setRadians(float radians) {
        angleRadians = radians;
    }
    
    public void setDegrees(float degrees) {
        angleRadians = (float) Math.toRadians(degrees);
    }
    
    public float getRadians() {
        return angleRadians;
    }
    
    public float getDegrees() {
        return (float) Math.toDegrees(angleRadians);
    }
    
    @Override
    public String toString() {
        return "#[Angle degrees=" + getDegrees() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(angleRadians);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Angle other = (Angle) obj;
        if (Float.floatToIntBits(angleRadians) != Float.floatToIntBits(other.angleRadians)) {
            return false;
        }
        return true;
    }
}
