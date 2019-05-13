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

package academy.devonline.javamm.compiler.component.impl;

import academy.devonline.javamm.code.fragment.ByteCode;
import academy.devonline.javamm.code.fragment.FunctionName;
import academy.devonline.javamm.code.fragment.SourceCode;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.function.DeveloperFunction;
import academy.devonline.javamm.code.fragment.operation.Block;
import academy.devonline.javamm.compiler.Compiler;
import academy.devonline.javamm.compiler.component.FunctionNameBuilder;
import academy.devonline.javamm.compiler.component.FunctionReader;
import academy.devonline.javamm.compiler.component.SourceLineReader;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
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

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompilerImpl_UnitTest {

    private final SourceLine sourceLine = new SourceLine("module1", 5, List.of());

    @Mock
    private SourceCode sourceCode;

    @Mock
    private SourceLineReader sourceLineReader;

    @Mock
    private FunctionReader functionReader;

    @Mock
    private FunctionNameBuilder functionNameBuilder;

    @Mock
    private List<SourceLine> lines;

    @Mock
    private ListIterator<SourceLine> iterator;

    @Mock
    private FunctionName mainFunctionName;

    @Mock
    private FunctionName function1;

    private DeveloperFunction developerFunction;

    private Compiler compiler;

    @BeforeEach
    void beforeEach() {
        compiler = new CompilerImpl(functionNameBuilder, sourceLineReader, functionReader);
        developerFunction = new DeveloperFunction.Builder()
            .setName(function1)
            .setDeclarationSourceLine(sourceLine)
            .setBody(new Block(List.of(), sourceLine))
            .build();

        when(sourceLineReader.read(sourceCode)).thenReturn(lines);
        when(lines.listIterator()).thenReturn(iterator);
        when(functionReader.read(iterator)).thenReturn(developerFunction);
    }

    @Test
    @Order(1)
    void Should_compile_successful() {
        when(iterator.hasNext())
            .thenReturn(true)
            .thenReturn(false);
        when(functionNameBuilder.build(eq("main"), eq(List.of()), any())).thenReturn(mainFunctionName);

        final ByteCode byteCode = compiler.compile(sourceCode);

        assertEquals(mainFunctionName, byteCode.getMainFunctionName());
        assertEquals(1, byteCode.getAllFunctions().size());
        assertEquals(Optional.of(developerFunction), byteCode.getFunction(function1));
    }

    @Test
    @Order(2)
    void Should_throw_error_if_function_is_already_defined_at_the_bytecode() {
        when(iterator.hasNext())
            .thenReturn(true)
            .thenReturn(true);
        when(function1.toString()).thenReturn("function1()");

        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            compiler.compile(sourceCode));
        assertEquals(
            "Syntax error in 'module1' [Line: 5]: Function 'function1()' is already defined",
            error.getMessage());
    }
}