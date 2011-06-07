package pl.drunkpirate.treasure.resources;

import static com.google.common.base.Preconditions.checkNotNull;
import static pl.drunkpirate.treasure.Strings.r;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public abstract class Resource {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(Resource.class.getName());
    
    private static final String[] DEFAULT_SEARCH_PATTERN = new String[] {"{}"};
    
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
    
    protected Resource(String path, String... searchPatterns) {
        this.searchPatterns = searchPatterns.length > 0 ? searchPatterns : DEFAULT_SEARCH_PATTERN;
        this.path = checkNotNull(path);
        collectPath();
    }
    
    protected URL find() {
        if (!searched) {
            ClassLoader classLoader = Resource.class.getClassLoader();
            for (String searchPattern : searchPatterns) {
                URL resourceUrl = classLoader.getResource(r(searchPattern, path));
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
}
