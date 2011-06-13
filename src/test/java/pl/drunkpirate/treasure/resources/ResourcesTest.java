package pl.drunkpirate.treasure.resources;


import static junit.framework.Assert.*;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.xml.XMLElement;
import org.newdawn.slick.util.xml.XMLElementList;

public class ResourcesTest {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(ResourcesTest.class.getName());

    static class R {
        static void init() {
            Resources.init(R.class);
        }
        
        public static class images {
            static final ImageDataResource existing = new ImageDataResource("image");
            static final ImageDataResource not_existing = new ImageDataResource("image2");
        }
        
        public static class raw {
            static final RawResource existing = new RawResource("raw.xml");
            static final RawResource not_existing = new RawResource("raw2.xml");
        }
        
        public static class xml {
            static final XmlResource map = new XmlResource("map");
        }
    }
    
    @Before
    public void setUp() throws Exception {
        R.init();
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testExisting() {
        assertTrue(Resources.hasMissing());
        List<Resource> missingResources = Resources.getMissing();
        
        assertTrue(missingResources.contains(R.images.not_existing));
        assertTrue(missingResources.contains(R.raw.not_existing));
        
        assertNotNull(R.images.existing.find());
        assertNotNull(R.raw.existing.find());
        
        assertNull(R.images.not_existing.find());
        assertNull(R.raw.not_existing.find());
    }
    
    @Test
    public void testMapXml() throws SlickException {
        XMLElement xmlElement = R.xml.map.get();
        assertEquals("map", xmlElement.getName());
        
        XMLElementList sizeElementList = xmlElement.getChildrenByName("size");
        assertEquals(1, sizeElementList.size());
        
        XMLElement sizeElement = sizeElementList.get(0);
        assertEquals("5", sizeElement.getContent());
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
