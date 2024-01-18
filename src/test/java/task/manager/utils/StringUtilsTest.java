package task.manager.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class StringUtilsTest {

    @Test
    public void testIsNotBlankUtilityMethod() {
        Assertions.assertTrue(StringUtils.isBlank(null));
        Assertions.assertTrue(StringUtils.isBlank(""));
        Assertions.assertTrue(StringUtils.isBlank("     "));
        Assertions.assertFalse(StringUtils.isBlank("string"));
    }
}
