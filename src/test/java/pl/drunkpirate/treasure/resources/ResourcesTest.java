package pl.drunkpirate.treasure.resources;


import static junit.framework.Assert.*;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.SlickException;

public class ResourcesTest {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(ResourcesTest.class.getName());

    public static class R {
        public static class images {
            public static final ImageDataResource existing = new ImageDataResource("image");
            public static final ImageDataResource not_existing = new ImageDataResource("image2");
        }
        
        public static class raw {
            public static final RawResource existing = new RawResource("raw.xml");
            public static final RawResource not_existing = new RawResource("raw2.xml");
        }
    }
    
    @Before
    public void setUp() throws Exception {
        
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testExisting() {
        assertNotNull(R.images.existing.find());
        assertNotNull(R.raw.existing.find());
        
        assertNull(R.images.not_existing.find());
        assertNull(R.raw.not_existing.find());
    }
    
    @Test
    public void testProperties() throws SlickException {
        ImageDataResource imageResource = R.images.existing;
        assertEquals("vegetable", imageResource.getProperty("name"));
        assertEquals("180", imageResource.getProperty("rotation"));
        assertEquals("0.5", imageResource.getProperty("centerX"));
        assertEquals("0.7", imageResource.getProperty("centerY"));
    }
}
