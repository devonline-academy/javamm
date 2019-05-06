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

package academy.devonline.temp.token_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example01RegularExpressions {

    private Example01RegularExpressions() {
    }

    public static void main(final String[] args) {
        final List<String> data = List.of(
            "var a1=1+b_2*(35-5)+'hello world'+(\"hello java\")",
            "var e = c+2*d-5 / a[0] + calculateSomeValue(12)",
            "println (a[0] > ar[a[4 - a[3]] * sum (a[1], 0 - a[1])] ? a typeof array & ar typeof array : sum (parseInt (\"12\"), parseDouble (\"12.1\")))"
        );

        final String words = "\\b\\w+";
        final String operators = "[+*()\\-=\\[\\]/><,?:&|]";
        final String stringLiterals = "\"([^\"\\\\]|\\\\.)*\"" + "|" + "\'([^\'\\\\]|\\\\.)*\'";
        final Pattern pattern = Pattern.compile(stringLiterals + "|" + words + "|" + operators);

        for (final String datum : data) {
            final Matcher matcher = pattern.matcher(datum);
            final List<String> list = new ArrayList<>();
            while (matcher.find()) {
                final String token = matcher.group();
                list.add(token);
            }

            list.forEach(v -> System.out.print(v + " "));
            System.out.println();
        }
    }
}
