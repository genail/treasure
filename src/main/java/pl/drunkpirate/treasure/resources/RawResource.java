package pl.drunkpirate.treasure.resources;

import java.util.logging.Logger;

public class RawResource extends Resource {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(RawResource.class.getName());
    
    private static final String PREFIX = "res/raw/";
    
    public RawResource(String path) {
        super(PREFIX + path);
    }
}
