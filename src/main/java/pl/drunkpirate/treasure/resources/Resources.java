package pl.drunkpirate.treasure.resources;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.collect.Lists;

public abstract class Resources {
    private static final Logger LOGGER = Logger.getLogger(Resources.class.getName());
    
    
    static final List<WeakReference<Resource>> allResources = Lists.newArrayList();

    
    public static void init(Class<?>... classesToInit) {
        for (Class<?> classToInit : classesToInit) {
            init(classToInit);
        }
    }
    
    public static void init(Class<?> classToInit) {
        initFields(classToInit);
        initMemberClasses(classToInit);
    }

    private static void initFields(Class<?> classToInit) {
        Field[] declaredFields = classToInit.getDeclaredFields();
        for (Field field : declaredFields) {
            try {
                field.get(null);
            } catch (IllegalArgumentException e) {
                LOGGER.log(Level.SEVERE, "error initializing field", e);
            } catch (IllegalAccessException e) {
                LOGGER.log(Level.SEVERE, "error initializing field", e);
            }
        }
    }
    
    private static void initMemberClasses(Class<?> classToInit) {
        Class<?>[] memberClasses = classToInit.getClasses();
        init(memberClasses);
    }
    
    /** @return true if there are missing resources */
    public static boolean hasMissing() {
        return !getMissing().isEmpty();
    }
    
    static void registerResource(Resource resource) {
        allResources.add(new WeakReference<Resource>(resource));
        
        if (allResources.size() % 10000 == 0) {
            removeDeadResources();
        }
    }

    private static void removeDeadResources() {
        List<WeakReference<Resource>> newResources = Lists.newArrayList();
        
        for (WeakReference<Resource> ref : allResources) {
            if (ref.get() != null) {
                newResources.add(ref);
            }
        }
        
        allResources.clear();
        allResources.addAll(newResources);
    }
    
    /** @return list of missing resources */
    public static List<Resource> getMissing() {
        List<Resource> missing = Lists.newArrayList();

        for (WeakReference<Resource> resourceRef : allResources) {
            Resource resource = resourceRef.get();
            if (resource == null) {
                continue;
            }
            
            URL url = resource.find();
            if (url == null) {
                missing.add(resource);
            }
        }
        
        return missing;
    }
}
