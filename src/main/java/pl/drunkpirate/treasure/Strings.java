package pl.drunkpirate.treasure;

import java.util.Arrays;
import java.util.logging.Logger;

public class Strings {
    private static final Logger LOGGER = Logger.getLogger(Strings.class.getName());
    
        public static String r(String base, Object...objects) {
        
        String result = base;
        
        int i = 0;
        int index;
        while ((index = result.indexOf("{}")) != -1) {
            if (objects.length > i) {
                result = result.substring(0, index) + objects[i] + result.substring(index + 2);
                i++;
            } else {
                LOGGER.warning("to many '{}' for string '" + base + "' with params "
                        + Arrays.toString(objects));
                break;
            }
        }
        
        if (i != objects.length) {
            LOGGER.warning("to many arguments for string '" + base + "' with params "
                    + Arrays.toString(objects));
        }
        
        return result;
    }
}
