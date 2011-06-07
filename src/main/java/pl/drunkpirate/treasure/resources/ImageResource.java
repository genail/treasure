package pl.drunkpirate.treasure.resources;

import java.lang.ref.WeakReference;
import java.util.logging.Logger;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Describes a image resource.
 * When loaded the instance is hold as weak reference in JVM memory.
 * <p>
 * All images referenced by ImageResource should be located in <code>/res/images/</code>
 * of classpath. (<code>src/main/resources/res/images/</code> in Maven project).
 * <p>
 * Its recommended (but not strict) that image reference is given without extension and filename
 * is written in lower case. The loader will look for following postfixes in this order:
 * <ul>
 * <li>png
 * <li>jpg
 * <li>jpeg
 * <li>bmp
 * </ul>
 * 
 * @author Piotr Korzuszek <piotr.korzuszek@gmail.com>
 *
 */
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
    
    /**
     * Creates a new instance of {@link ImageResource} of image referenced by <code>ref</code>.
     * 
     * @param ref Reference name to image. 
     */
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
