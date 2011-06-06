package pl.drunkpirate.treasure;

import java.lang.ref.WeakReference;
import java.util.logging.Logger;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageResource extends Resource {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(ImageResource.class.getName());
    
    private static final String PREFIX = "res/images/";
    private static final String[] SEARCH_PATTERNS = {
        "{}",
        "{}.png",
        "{}.jpg",
        "{}.jpeg",
        "{}.bmp",
        "{}.PNG",
        "{}.JPG",
        "{}.JPEG",
        "{}.BMP",
    };
    
    
    private WeakReference<Image> imageWeakRef = new WeakReference<Image>(null);
    
    
    public ImageResource(String ref) {
        super(PREFIX + ref, SEARCH_PATTERNS);
    }
    
    public Image load() throws SlickException {
        Image image = imageWeakRef.get();
        if (image == null) {
            image = new Image(getPath());
            imageWeakRef = new WeakReference<Image>(image);
        }
        
        return image;
    }
}
