package pl.drunkpirate.treasure.resources;

import static com.google.common.base.Preconditions.checkNotNull;
import static pl.drunkpirate.treasure.Strings.r;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Closeables;

public abstract class Resource {
    private static final Logger LOGGER = Logger.getLogger(Resource.class.getName());
    
    private static final String[] DEFAULT_SEARCH_PATTERN = new String[] {"{}"};
    private static final String PROPERTIES_TEMPLATE = "{}.properties";
    
    private static final Set<String> paths = Sets.newHashSet();

    /** @return true if there are missing resources */
    public static boolean hasMissing() {
        return !getMissing().isEmpty();
    }
    
    /** @return list of missing resources */
    public static List<String> getMissing() {
        List<String> missing = Lists.newArrayList();
        
        ClassLoader classLoader = Resource.class.getClassLoader();
        for (String path : paths) {
            URL resource = classLoader.getResource(path);
            if (resource == null) {
                missing.add(path);
            }
        }
        
        return missing;
    }
    

    private final String path;
    private final String[] searchPatterns;
    
    private boolean searched;
    private URL cachedUrl;
    
    private Properties properties;
    
    protected Resource(String path, String... searchPatterns) {
        this.searchPatterns = searchPatterns.length > 0 ? searchPatterns : DEFAULT_SEARCH_PATTERN;
        this.path = checkNotNull(path);
        collectPath();
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
    
    private void collectPath() {
        synchronized (paths) {
            paths.add(path);
        }
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
}
