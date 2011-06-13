package pl.drunkpirate.treasure.math;

import java.util.logging.Logger;

public class Easing extends Func1 {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(Easing.class.getName());
    
    public enum Type {
        IN,
        OUT,
        IN_OUT
    }
    
    public enum Function {
        LINEAR,
        QUADRADIC,
        CUBIC,
        QUARTIC,
        QUINTIC,
        BACK,
        ELASTIC
    }
    
    public static final Easing NONE = new Easing(Type.IN, Function.LINEAR);
    
    public static final Easing REGULAR_IN = new Easing(Type.IN, Function.QUADRADIC);
    public static final Easing REGULAR_OUT = new Easing(Type.OUT, Function.QUADRADIC);
    public static final Easing REGULAR_IN_OUT = new Easing(Type.IN_OUT, Function.QUADRADIC);
    
    public static final Easing STRONG_IN = new Easing(Type.IN, Function.QUINTIC);
    public static final Easing STRONG_OUT = new Easing(Type.OUT, Function.QUINTIC);
    public static final Easing STRONG_IN_OUT = new Easing(Type.IN_OUT, Function.QUINTIC);
    
    public static final Easing BACK_IN = new Easing(Type.IN, Function.BACK);
    public static final Easing BACK_OUT = new Easing(Type.OUT, Function.BACK);
    public static final Easing BACK_IN_OUT = new Easing(Type.IN_OUT, Function.BACK);
    
    public static final Easing ELASTIC_IN = new Easing(Type.IN, Function.ELASTIC);
    public static final Easing ELASTIC_OUT = new Easing(Type.OUT, Function.ELASTIC);
    public static final Easing ELASTIC_IN_OUT = new Easing(Type.IN_OUT, Function.ELASTIC);
    
    private final Type type;
    private final Function function;
    
    public Easing(Type type, Function function) {
        this.type = type;
        this.function = function;
    }
    
    @Override
    public float compute(float t) {
        float easedT;
        
        switch (type) {
            
            default:
                easedT = t;
                break;
            
            case IN: 
                easedT = ease(t);
                break;
                
            case OUT: 
                easedT = 1-ease(1-t);
                break;
            
            case IN_OUT: 
                if (t < 0.5) {
                    easedT = ease(2*t)/2;
                }
                else {
                    easedT = 1-ease(2 - 2*t)/2;
                }
                break;
        }
        
        return easedT;
    }
    
    protected float ease(float t) {
        
        float t2;
        float t3;
        
        switch (function) {
            
            case LINEAR: 
                return t;
            
            case QUADRADIC:
                return t * t;
            
            case CUBIC: 
                return t * t * t;
            
            case QUARTIC: 
                t2 = t * t;
                return t2 * t2;
                
            case QUINTIC: 
                t2 = t * t;
                return t2 * t2 * t;
                
            case BACK:
                t2 = t * t;
                t3 = t2 * t;
                return t3 + t2 - t;
                
            case ELASTIC:
                t2 = t * t;
                t3 = t2 * t;
                
                float scale = t2 * (2*t3 + t2 - 4*t + 2);
                float wave = (float)-Math.sin(t * 3.5 * Math.PI);
                
                return scale * wave;
                
            default:
                throw new RuntimeException("unknown type: " + function);
        }
    }
}
