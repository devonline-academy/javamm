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

import academy.devonline.javamm.code.exception.ConfigException;
import academy.devonline.javamm.ide.component.CodeTemplateStorage;
import academy.devonline.javamm.ide.model.CodeTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
final class CodeTemplateStorageImpl implements CodeTemplateStorage {

    private final Map<String, CodeTemplate> templateMap;

    private CodeTemplateStorageImpl(final Map<String, CodeTemplate> templateMap) {
        this.templateMap = Map.copyOf(templateMap);
    }

    @Override
    public Optional<CodeTemplate> getTemplate(final String key) {
        return Optional.ofNullable(templateMap.get(key));
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public static class Builder {

        private final Map<String, CodeTemplate> templateMap = new HashMap<>();

        Builder addTemplate(final String template,
                            final String... keys) {
            for (final String key : keys) {
                final CodeTemplate codeTemplate = new CodeTemplate(template);
                templateMap.merge(key, codeTemplate, (codeTemplate1, codeTemplate2) -> {
                    throw new ConfigException(format(
                        "Duplicate found: key=%s, value1=%s, value2=%s", key, codeTemplate1, codeTemplate2));
                });
            }
            return this;
        }

        CodeTemplateStorage build() {
            return new CodeTemplateStorageImpl(templateMap);
        }
    }
}
