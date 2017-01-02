package ${basePackage};

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for Example
 */
public class ExampleTest {

    @Test
    public void testMessage() {
        Example example = new Example();
        Assert.assertNotNull(example.getMessage());
        Assert.assertEquals("Hello World", example.getMessage());

    }
}
