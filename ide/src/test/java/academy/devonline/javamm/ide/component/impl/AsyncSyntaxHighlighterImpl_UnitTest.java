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

import academy.devonline.javamm.ide.component.AsyncSyntaxHighlighter;
import javafx.concurrent.Task;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.PlainTextChange;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactfx.AwaitingEventStream;
import org.reactfx.EventStream;
import org.reactfx.Subscription;
import org.reactfx.TaskStream;
import org.reactfx.util.Try;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.apache.commons.lang3.reflect.FieldUtils.getDeclaredField;
import static org.apache.commons.lang3.reflect.FieldUtils.removeFinalModifier;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AsyncSyntaxHighlighterImpl_UnitTest {

    @Mock
    private CodeArea codeArea;

    @Mock
    private ExecutorService executorService;

    @Mock
    private Subscription cleanupWhenDone;

    private AsyncSyntaxHighlighter asyncSyntaxHighlighter;

    @BeforeEach
    void beforeEach() {
        asyncSyntaxHighlighter = new AsyncSyntaxHighlighterImpl(codeArea, executorService);
    }

    @Test
    @Order(1)
    void enable_should_create_subscription_with_computeHighlightingAsync_task_which_should_delegate_to_computeHighlighting()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final List<Supplier<Task<StyleSpans<Collection<String>>>>> taskSuppliers = mockRxMethodsAndReturnListOfSupplier();
        when(codeArea.getText()).thenReturn("");

        asyncSyntaxHighlighter.enable();

        final Task<StyleSpans<Collection<String>>> computeHighlightingAsyncTask = taskSuppliers.get(0).get();
        verify(executorService).execute(computeHighlightingAsyncTask);

        final Method callMethod = Task.class.getDeclaredMethod("call");
        callMethod.setAccessible(true);
        callMethod.invoke(computeHighlightingAsyncTask);
    }

    @Test
    @Order(2)
    void disable_should_unsubscribe_the_subscription_created_by_enable_method() {
        mockRxMethodsAndReturnListOfSupplier();

        asyncSyntaxHighlighter.enable();
        asyncSyntaxHighlighter.disable();

        verify(cleanupWhenDone).unsubscribe();
    }

    @Test
    @Order(3)
    void highlightNow_should_invoke_computeHighlighting() {
        when(codeArea.getText()).thenReturn("");

        asyncSyntaxHighlighter.highlightNow();

        final StyleSpans<Collection<String>> expectedStyleSpans = new StyleSpansBuilder<Collection<String>>()
            .add(List.of(), 0)
            .create();
        verify(codeArea).setStyleSpans(0, expectedStyleSpans);
    }

    @Test
    @Order(4)
    void computeHighlighting_should_correct_highlight() {
        when(codeArea.getText()).thenReturn("var a = /* test */ null + 'Hello' + \"world\" //single line comment");
        final StyleSpans<Collection<String>> expectedStyleSpans = new StyleSpansBuilder<Collection<String>>()
            .add(List.of("keyword"), 3) // -> var
            .add(List.of(), 5)          // -> a =
            .add(List.of("comment"), 10)// ->/* test */
            .add(List.of(), 1)
            .add(List.of("keyword"), 4) //-> null
            .add(List.of(), 3)
            .add(List.of("string"), 7)  //-> 'Hello'
            .add(List.of(), 3)          //
            .add(List.of("string"), 7)  //-> "world"
            .add(List.of(), 1)
            .add(List.of("comment"), 21)//-> //single line comment
            .create();

        asyncSyntaxHighlighter.highlightNow();

        verify(codeArea).setStyleSpans(0, expectedStyleSpans);
    }

    @Test
    @Order(5)
    void example_of_Impossible_exception_test() throws IllegalAccessException {
        final Field groupPatternMapField = getDeclaredField(asyncSyntaxHighlighter.getClass(), "groupPatternMap", true);
        removeFinalModifier(groupPatternMapField);
        writeField(groupPatternMapField, asyncSyntaxHighlighter, Map.of());

        when(codeArea.getText()).thenReturn("var a");

        final IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
            asyncSyntaxHighlighter.highlightNow());
        assertEquals(
            "Impossible exception: at least one group should be found, because matcher.find() return true",
            exception.getMessage()
        );
    }

    @SuppressWarnings("unchecked")
    private List<Supplier<Task<StyleSpans<Collection<String>>>>> mockRxMethodsAndReturnListOfSupplier() {
        final EventStream<List<PlainTextChange>> multiPlainChangesEventStream = mock(EventStream.class);
        final AwaitingEventStream<List<PlainTextChange>> listAwaitingEventStream = mock(AwaitingEventStream.class);
        final TaskStream<StyleSpans<Collection<String>>> styleSpansTaskStream = mock(TaskStream.class);
        final AwaitingEventStream<Try<StyleSpans<Collection<String>>>> tryStyleSpansAwaitingEventStream =
            mock(AwaitingEventStream.class);
        final EventStream<StyleSpans<Collection<String>>> styleSpansCollectionEventStream = mock(EventStream.class);

        final List<Supplier<Task<StyleSpans<Collection<String>>>>> taskSuppliers = new ArrayList<>(1);
        when(codeArea.multiPlainChanges()).thenReturn(multiPlainChangesEventStream);
        when(multiPlainChangesEventStream.successionEnds(any())).thenReturn(listAwaitingEventStream);
        when(listAwaitingEventStream.supplyTask(argThat(
            (ArgumentMatcher<Supplier<Task<StyleSpans<Collection<String>>>>>) argument -> {
                taskSuppliers.add(argument);
                return true;
            })
        )).thenReturn(styleSpansTaskStream);
        when(styleSpansTaskStream.awaitLatest(multiPlainChangesEventStream)).thenReturn(tryStyleSpansAwaitingEventStream);
        when(tryStyleSpansAwaitingEventStream.filterMap(argThat(
            (ArgumentMatcher<Function<? super Try<StyleSpans<Collection<String>>>,
                Optional<StyleSpans<Collection<String>>>>>) argument -> true)
        )).thenReturn(styleSpansCollectionEventStream);
        when(styleSpansCollectionEventStream.subscribe(any())).thenReturn(cleanupWhenDone);
        return Collections.unmodifiableList(taskSuppliers);
    }
}