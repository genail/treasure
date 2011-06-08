package pl.drunkpirate.treasure.resources;

import static com.google.common.base.Preconditions.checkNotNull;
import static pl.drunkpirate.treasure.Strings.r;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.io.Closeables;

public abstract class Resource {
    private static final Logger LOGGER = Logger.getLogger(Resource.class.getName());
    
    private static final String[] DEFAULT_SEARCH_PATTERN = new String[] {"{}"};
    private static final String PROPERTIES_TEMPLATE = "{}.properties";

    
    private final String path;
    private final String[] searchPatterns;
    
    private boolean searched;
    private URL cachedUrl;
    
    private Properties properties;
    
    protected Resource(String path, String... searchPatterns) {
        this.searchPatterns = searchPatterns.length > 0 ? searchPatterns : DEFAULT_SEARCH_PATTERN;
        this.path = checkNotNull(path);
        
        registerResource();
    }
    
    protected URL find() {
        if (!searched) {
            for (String searchPattern : searchPatterns) {
                URL resourceUrl = findResource(r(searchPattern, path));
                if (resourceUrl != null) {
                    cachedUrl = resourceUrl;
                }
            }
            
            searched = true;
        }
        
        return cachedUrl;
    }
    
    private void registerResource() {
        Resources.registerResource(this);
    }
    
    public String getPath() {
        return path;
    }
    
    public String getProperty(String name) {
        return getProperty(name, null);
    }
    
    public String getProperty(String name, String defaultValue) {
        checkNotNull(name);
        
        if (!propertiesLoaded()) {
            loadProperties();
        }
        
        return properties.getProperty(name);
    }

    private boolean propertiesLoaded() {
        return properties != null;
    }
    
    private void loadProperties() {
        properties = new Properties();
        URL propertiesUrl = findResource(r(PROPERTIES_TEMPLATE, path));
        
        if (propertiesUrl != null) {
            InputStream input = null;
            
            try {
                input = propertiesUrl.openStream();
                properties.load(input);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "error loading properties", e);
            } finally {
                Closeables.closeQuietly(input);
            }
        }
    }
    
    private URL findResource(String name) {
        ClassLoader classLoader = Resource.class.getClassLoader();
        return classLoader.getResource(name);
    }

    @Override
    public String toString() {
        return "Resource [path=" + path + "]";
    }
    
}
