package pl.drunkpirate.treasure.math;

import java.util.logging.Logger;

/**
 * Time period.
 * 
 * @author Piotr Korzuszek
 */
public class Time {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(Time.class.getName());
    
    private final double value;
    private final TimeUnit unit;
    
    public Time(double value, TimeUnit unit) {
        this.value = value;
        this.unit = unit;
    }
    
    /** @return Time period value using given unit. */
    public double get(TimeUnit unit) {
        return value * this.unit.getMultiplier() / unit.getMultiplier();
    }
}
