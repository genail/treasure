package pl.drunkpirate.treasure.math;


public abstract class Func1 {
    public static final Func1 NULL = Func1.ofValue(0);
    
    public static Func1 ofValue(final float value) {
        return new Func1() {
            @Override
            public float compute(float t) {
                return value;
            }
        };
    }
    
    /** @param t - usually time from 0 to 1 */
    public abstract float compute(float t);
}
