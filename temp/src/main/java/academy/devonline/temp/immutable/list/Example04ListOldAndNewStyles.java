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

package academy.devonline.temp.immutable.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example04ListOldAndNewStyles {

    private Example04ListOldAndNewStyles() {
    }

    public static void main(final String[] args) {
        final List<Integer> oldStyleFromArray = Arrays.asList(1, 2, 3, 4, 5);
        final List<Integer> newStyleFromArray = List.of(1, 2, 3, 4, 5);
        System.out.println(List.of(1));

        final List<Integer> badEmptyList = new ArrayList<>();
        final List<Integer> oldStyleEmptyList = Collections.emptyList();
        final List<Integer> newStyleEmptyList = List.of();

        final List<Integer> modifiableList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        final List<Integer> oldStyleUnmodifiableList = Collections.unmodifiableList(modifiableList);
        final List<Integer> newStyleUnmodifiableList = List.copyOf(modifiableList);
    }
}
