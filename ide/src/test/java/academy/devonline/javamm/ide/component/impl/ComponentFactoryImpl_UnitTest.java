/*
 * Copyright (c) 2019. http://devonline.academy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package academy.devonline.javamm.ide.component.impl;

import academy.devonline.javamm.code.component.Console;
import academy.devonline.javamm.code.fragment.SourceCode;
import academy.devonline.javamm.ide.component.ComponentFactory;
import org.fxmisc.richtext.CodeArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.apache.commons.lang3.reflect.FieldUtils.getDeclaredField;
import static org.apache.commons.lang3.reflect.FieldUtils.removeFinalModifier;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ComponentFactoryImpl_UnitTest {

    private final ComponentFactory componentFactory = new ComponentFactoryImpl();

    @Mock
    private Console console;

    @Mock
    private CodeArea codeArea;

    @Mock
    private List<SourceCode> sourceCodes;

    @Mock
    private ExecutorService executorService;

    @BeforeEach
    void beforeEach() throws IllegalAccessException {
        final Field executorServiceField = getDeclaredField(componentFactory.getClass(), "executorService", true);
        removeFinalModifier(executorServiceField);
        writeField(executorServiceField, componentFactory, executorService);
    }

    @Test
    @Order(1)
    void getPairedTokensHelper_should_get_a_singleton_instance() {
        assertSame(componentFactory.getPairedTokensHelper(), componentFactory.getPairedTokensHelper());
    }

    @Test
    @Order(2)
    void getPairedTokensHelper_should_get_a_instance_of_PairedTokensHelperImpl_class() {
        assertEquals(PairedTokensHelperImpl.class, componentFactory.getPairedTokensHelper().getClass());
    }

    @Test
    @Order(3)
    void getNewLineHelper_should_get_a_singleton_instance() {
        assertSame(componentFactory.getNewLineHelper(), componentFactory.getNewLineHelper());
    }

    @Test
    @Order(4)
    void getNewLineHelper_should_get_a_instance_of_NewLineHelperImpl_class() {
        assertEquals(NewLineHelperImpl.class, componentFactory.getNewLineHelper().getClass());
    }

    @Test
    @Order(5)
    void getCodeTemplateHelper_should_get_a_singleton_instance() {
        assertSame(componentFactory.getCodeTemplateHelper(), componentFactory.getCodeTemplateHelper());
    }

    @Test
    @Order(6)
    void getCodeTemplateHelper_should_get_a_instance_of_CodeTemplateHelperImpl_class() {
        assertEquals(CodeTemplateHelperImpl.class, componentFactory.getCodeTemplateHelper().getClass());
    }

    @Test
    @Order(7)
    void getCodeFormatter_should_get_a_singleton_instance() {
        assertSame(componentFactory.getCodeFormatter(), componentFactory.getCodeFormatter());
    }

    @Test
    @Order(8)
    void getCodeFormatter_should_get_a_instance_of_CodeFormatterImpl_class() {
        assertEquals(CodeFormatterImpl.class, componentFactory.getCodeFormatter().getClass());
    }

    @Test
    @Order(9)
    void createVirtualMachineRunner_should_create_a_new_instance() {
        assertNotSame(
            componentFactory.createVirtualMachineRunner(console, sourceCodes),
            componentFactory.createVirtualMachineRunner(console, sourceCodes)
        );
    }

    @Test
    @Order(10)
    void createReleaseVirtualMachineRunner_should_create_an_instance_of_VirtualMachineRunnerImpl_class() {
        assertEquals(VirtualMachineRunnerImpl.class,
            componentFactory.createVirtualMachineRunner(console, sourceCodes).getClass());
    }

    @Test
    @Order(13)
    void createSyntaxHighlighter_should_create_a_new_instance() {
        assertNotSame(
            componentFactory.createAsyncSyntaxHighlighter(codeArea),
            componentFactory.createAsyncSyntaxHighlighter(codeArea)
        );
    }

    @Test
    @Order(14)
    void createSyntaxHighlighter_should_create_an_instance_of_AsyncSyntaxHighlighterImpl_class() {
        assertEquals(AsyncSyntaxHighlighterImpl.class,
            componentFactory.createAsyncSyntaxHighlighter(codeArea).getClass());
    }

    @Test
    @Order(15)
    void release_should_shutdownNow_the_executorService() {
        componentFactory.release();

        verify(executorService).shutdownNow();
    }
}