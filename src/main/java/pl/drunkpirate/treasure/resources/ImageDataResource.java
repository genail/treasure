package pl.drunkpirate.treasure.resources;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.logging.Logger;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.ImageIOImageData;

import com.google.common.io.Closeables;

/**
 * Describes a image resource.
 * When loaded the instance is hold as weak reference in JVM memory.
 * <p>
 * All images referenced by {@link ImageDataResource} should be located in <code>/res/images/</code>
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
public class ImageDataResource extends Resource {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(ImageDataResource.class.getName());
    
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
    
    
    private WeakReference<ImageData> imageWeakRef = new WeakReference<ImageData>(null);
    
    /**
     * Creates a new instance of {@link ImageDataResource} of image referenced by <code>ref</code>.
     * 
     * @param ref Reference name to image. 
     */
    public ImageDataResource(String ref) {
        super(PREFIX + ref, SEARCH_PATTERNS);
    }
    
    public ImageData load() throws SlickException {
        ImageData imageData = imageWeakRef.get();
        if (imageData == null) {
            URL url = find();
            InputStream input = null;
            try {
                input = url.openStream();
                ImageIOImageData imageIOImageData = new ImageIOImageData();
                imageIOImageData.loadImage(input);
                
                imageWeakRef = new WeakReference<ImageData>(imageIOImageData);
            } catch (IOException e) {
                throw new SlickException("error loading image", e);
            } finally {
                Closeables.closeQuietly(input);
            }
        }
        
        return imageData;
    }
}
