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
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;
import org.reactfx.util.Try;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static academy.devonline.javamm.code.syntax.Keywords.KEYWORDS;
import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;

/**
 * Copied from
 * https://github.com/FXMisc/RichTextFX/blob/master/richtextfx-demos/src/main/java/org/fxmisc/richtext/demo/JavaKeywordsAsyncDemo.java
 *
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class AsyncSyntaxHighlighterImpl implements AsyncSyntaxHighlighter {

    private final Map<String, String> groupPatternMap = Map.ofEntries(
        // Keywords set
        entry("KEYWORD", format("\\b(%s)\\b", join("|", KEYWORDS))),
        // String literals: "..." or "...\n or '...' or '...\n
        entry("STRING", format("%s|%s", "\".*?[\"\\n]", "'.*?['\\n]")),
        // Comments: //...\n or  /* ... */ or /* ... end file
        entry("COMMENT", format("%s|%s", "//[^\n]*", "/\\*(.|\\R)*?(\\*/|\\z)"))
        // () - parentheses set
        // entry("PAREN", "[()]"),
        // {} - curly braces set
        // entry("BRACE", "[{}]"),
        // [] - square brackets set
        // entry("BRACKET", "[\\[\\]]")
    );

    // Merge all groups
    private final Pattern pattern = Pattern.compile(
        groupPatternMap.entrySet()
            .stream().map(e -> format("(?<%s>%s)", e.getKey(), e.getValue()))
            .collect(joining("|"))
    );


    private CodeArea codeArea;

    private ExecutorService executorService;

    private Subscription cleanupWhenDone;

    AsyncSyntaxHighlighterImpl(final CodeArea codeArea,
                               final ExecutorService executorService) {
        this.codeArea = codeArea;
        this.executorService = executorService;
    }

    @Override
    public void enable() {
        cleanupWhenDone = codeArea.multiPlainChanges()
            .successionEnds(Duration.ofMillis(50))
            .supplyTask(this::computeHighlightingAsync)
            .awaitLatest(codeArea.multiPlainChanges())
            .filterMap(Try::toOptional)
            .subscribe(this::applyHighlighting);
    }

    @Override
    public void disable() {
        cleanupWhenDone.unsubscribe();
    }

    @Override
    public void highlightNow() {
        applyHighlighting(computeHighlighting(codeArea.getText()));
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        final Task<StyleSpans<Collection<String>>> task = new Task<>() {
            @Override
            protected StyleSpans<Collection<String>> call() {
                return computeHighlighting(codeArea.getText());
            }
        };
        executorService.execute(task);
        return task;
    }

    private void applyHighlighting(final StyleSpans<Collection<String>> highlighting) {
        codeArea.setStyleSpans(0, highlighting);
    }

    private StyleSpans<Collection<String>> computeHighlighting(final String text) {
        final Matcher matcher = pattern.matcher(text);
        int lastKwEnd = 0;
        final StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            final String styleClass = getStyleClass(matcher);
            spansBuilder.add(List.of(), matcher.start() - lastKwEnd);
            spansBuilder.add(List.of(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(List.of(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private String getStyleClass(final Matcher matcher) {
        for (final String group : groupPatternMap.keySet()) {
            if (matcher.group(group) != null) {
                return group.toLowerCase();
            }
        }
        throw new IllegalStateException(
            "Impossible exception: at least one group should be found, because matcher.find() return true");
    }
}
