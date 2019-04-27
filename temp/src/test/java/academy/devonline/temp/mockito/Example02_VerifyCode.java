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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://javadoc.io/page/org.mockito/mockito-core/latest/org/mockito/Mockito.html
 */
@ExtendWith(MockitoExtension.class)
class Example02_VerifyCode {

    @Mock
    private List<Integer> list;

    @Test
    void verify_example() {
        // Test
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list.add(0, 20);

        list.size();
        list.size();
        list.isEmpty();

        // Verification
        verify(list).add(1);
        verify(list).add(2);
        verify(list).add(eq(3));
        verify(list).add(eq(0), anyInt());

        verify(list, times(5)).add(anyInt());

        verify(list, atLeastOnce()).size();
        verify(list, atLeastOnce()).isEmpty();
        verify(list, atLeast(2)).size();

        verify(list, never()).remove(anyInt());
    }
}
