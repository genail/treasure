package pl.drunkpirate.treasure.math;


/**
 * Basic time unit is second. All other units are fractions or multipliers of that base.
 * @author Piotr Korzuszek
 *
 */
public enum TimeUnit {
    MILLISECOND(0.001),
    CENTISECOND(0.01),
    DECISECOND(0.1),
    SECOND(1),
    MINUTE(60),
    HOUR(3600),
    DAY(86400);
    
    private final double multiplier;

    private TimeUnit(double multiplier) {
        this.multiplier = multiplier;
    }
    
    public double getMultiplier() {
        return multiplier;
    }
}
