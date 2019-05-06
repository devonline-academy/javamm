package academy.devonline.temp.token_parser;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example02StreamIterator {

    private Example02StreamIterator() {
    }

    public static void main(final String[] args) {
        final List<String> data = List.of(
                "\t\t var a=var1++-b+-7   ",
                "calculate(22+12,   ++var1+var2++)"
        );
        for (final String datum : data) {
            final StringIterator iterator = new StringIterator(datum.trim());
            final List<String> tokens = new ArrayList<>();
            final StringBuilder tokenBuilder = new StringBuilder();

            while (iterator.hasNext()) {
                final char ch = iterator.next();
                if (ch == ' ') {
                    if (tokenBuilder.length() > 0) {
                        tokens.add(tokenBuilder.toString());
                        tokenBuilder.delete(0, tokenBuilder.length());
                    }
                } else if ("+-=(),".indexOf(ch) != -1) {
                    if (tokenBuilder.length() > 0) {
                        tokens.add(tokenBuilder.toString());
                        tokenBuilder.delete(0, tokenBuilder.length());
                    }
                    if (ch == '+' && iterator.hasNext()) {
                        final char next = iterator.next();
                        if (next == '+') {
                            tokens.add("++");
                        } else {
                            tokens.add("+");
                            iterator.previous();
                        }
                    } else {
                        tokens.add(String.valueOf(ch));
                    }
                } else {
                    tokenBuilder.append(ch);
                }
            }
            if (tokenBuilder.length() > 0) {
                tokens.add(tokenBuilder.toString());
                tokenBuilder.delete(0, tokenBuilder.length());
            }
            tokens.forEach(v -> System.out.print(v + " "));
            System.out.println();
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class StringIterator {

        private final String string;

        private int index;

        private StringIterator(final String string) {
            this.string = requireNonNull(string);
            this.index = 0;
        }

        private boolean hasNext() {
            return index < string.length();
        }

        private char next() {
            return string.charAt(index++);
        }

        private void previous() {
            --index;
        }
    }
}
