package pl.drunkpirate.treasure.resources;


import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.drunkpirate.treasure.resources.ImageResource;
import pl.drunkpirate.treasure.resources.RawResource;

public class ResourcesTest {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(ResourcesTest.class.getName());

    public static class R {
        public static class images {
            public static final ImageResource existing = new ImageResource("image");
            public static final ImageResource not_existing = new ImageResource("image2");
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
}
