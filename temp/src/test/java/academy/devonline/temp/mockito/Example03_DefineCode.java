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

package academy.devonline.temp.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://javadoc.io/page/org.mockito/mockito-core/latest/org/mockito/Mockito.html
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
class Example03_DefineCode {

    @Mock
    private List<Integer> list;

    @Test
    void when_example() {
        // Prepare
        when(list.isEmpty()).thenReturn(false);
        when(list.isEmpty()).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(final InvocationOnMock invocation) {
                return Boolean.parseBoolean(System.getProperty("IS_DEV_ENVIRONMENT"));
            }
        });
        when(list.isEmpty())
            .thenAnswer((Answer<Boolean>) invocation ->
                Boolean.parseBoolean(System.getProperty("IS_DEV_ENVIRONMENT")));
        when(list.isEmpty()).thenThrow(new IllegalArgumentException("test")); // Only Runtime allowed!

        doReturn(false)
            .when(list)
            .isEmpty();
        doAnswer((Answer<Boolean>) invocation -> Boolean.parseBoolean(System.getProperty("IS_DEV_ENVIRONMENT")))
            .when(list)
            .isEmpty();
        doThrow(new IllegalArgumentException("test"))
            .when(list)
            .isEmpty();

        // if void only doSmth only
        doNothing().when(list).clear();
        doThrow(new IllegalArgumentException("test")).when(list).clear();
        doAnswer(invocation -> {
            final int value = invocation.getArgument(0);
            System.out.println(value);
            return null;
        }).when(list).add(anyInt());

        // Chain
        when(list.contains(any()))
            .thenReturn(false)
            .thenReturn(true)
            .thenThrow(new IllegalArgumentException("test"));
    }
}
