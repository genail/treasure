package pl.drunkpirate.treasure.resources;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.logging.Logger;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.xml.XMLElement;
import org.newdawn.slick.util.xml.XMLParser;

public class XmlResource extends Resource {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(XmlResource.class.getName());
    
    private static final String PREFIX = "res/xml/";
    private static final String[] SEARCH_PATTERNS = {
        "{}",
        "{}.xml",
        "{}.XML",
    };
    
    
    private WeakReference<XMLElement> ref = new WeakReference<XMLElement>(null);
    
    protected XmlResource(String path) {
        super(PREFIX + path, SEARCH_PATTERNS);
    }
    
    public XMLElement get() throws SlickException {
        XMLElement element = ref.get();
        if (element == null) {
            URL url = find();
            if (url == null) {
                throw new SlickException("xml resource not found: " + getPath());
            }

            InputStream input = null;
            
            try {
                input = url.openStream();
                XMLParser xmlParser = new XMLParser();
                element = xmlParser.parse("", input);
                ref = new WeakReference<XMLElement>(element);
            } catch (IOException e) {
                throw new SlickException("error loading xml resource", e);
            }
        }
        
        return element;
    }
}
