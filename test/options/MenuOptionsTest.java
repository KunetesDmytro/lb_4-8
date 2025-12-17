package options;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MenuOptionsTest {

    @Test
    void testEnumValues() {
        assertEquals("1", MenuOptions.VIEW_DISC.getCode());
        assertEquals("Create new disc", MenuOptions.CREATE_DISC.getDescription());

        for (MenuOptions opt : MenuOptions.values()) {
            assertNotNull(opt.getCode());
            assertNotNull(opt.getDescription());
            assertNotNull(MenuOptions.valueOf(opt.name()));
        }
    }
}