package task.manager.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class StringUtilsTest {

    @Test
    public void testIsNotBlankUtilityMethod() {
        Assertions.assertFalse(StringUtils.isNotBlank(null));
        Assertions.assertFalse(StringUtils.isNotBlank(""));
        Assertions.assertFalse(StringUtils.isNotBlank("     "));
        Assertions.assertTrue(StringUtils.isNotBlank("string"));
    }
}
