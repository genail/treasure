package pl.drunkpirate.treasure.math;

import org.newdawn.slick.geom.Vector2f;

public abstract class Func2 {
    
    public static Func2 of(final Func1 xFunc, final Func1 yFunc) {
        return new Func2() {
            Vector2f result = new Vector2f();
            
            @Override
            public Vector2f compute(float t) {
                result.x = xFunc.compute(t);
                result.y = yFunc.compute(t);
                return result;
            }
        };
    }
    
    /** @param t - usually time from 0 to 1 */
    public abstract Vector2f compute(float t);
}
