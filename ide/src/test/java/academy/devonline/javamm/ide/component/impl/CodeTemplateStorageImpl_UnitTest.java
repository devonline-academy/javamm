package academy.devonline.javamm.ide.component.impl;

import academy.devonline.javamm.code.exception.ConfigException;
import academy.devonline.javamm.ide.component.CodeTemplateStorage;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CodeTemplateStorageImpl_UnitTest {

    @Test
    @Order(1)
    void Should_throw_ConfigException_if_duplicates_found() {
        final CodeTemplateStorageImpl.Builder builder = new CodeTemplateStorageImpl.Builder()
                .addTemplate("template", "test");

        final ConfigException exception = assertThrows(ConfigException.class, () ->
                builder.addTemplate("template", "test"));
        assertEquals("Duplicate found: key=test, value1=template, value2=template", exception.getMessage());
    }

    @Test
    @Order(2)
    void Get_templates_should_work_correctly() {
        final CodeTemplateStorage storage = new CodeTemplateStorageImpl.Builder()
                .addTemplate("test1", "op")
                .addTemplate("test2", "exp")
                .build();

        assertTrue(storage.getTemplate("op").isPresent());
        assertTrue(storage.getTemplate("notFound").isEmpty());

        assertTrue(storage.getTemplate("exp").isPresent());
        assertTrue(storage.getTemplate("notFound").isEmpty());
    }
}