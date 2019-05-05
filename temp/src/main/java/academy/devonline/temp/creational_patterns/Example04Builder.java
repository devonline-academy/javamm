package academy.devonline.temp.creational_patterns;

import academy.devonline.temp.immutable.simple.Example03Complex;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * {@link Example03Complex}
 */
public final class Example04Builder {

    private final int param1;

    private final String param2;

    private final boolean param3;

    private final Object param4;

    private final int param5;

    private Example04Builder(final int param1,
                             final String param2,
                             final boolean param3,
                             final Object param4,
                             final int param5) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
    }

    public int getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }

    public boolean getParam3() {
        return param3;
    }

    public Object getParam4() {
        return param4;
    }

    public int getParam5() {
        return param5;
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public static final class Builder {

        private int param1;

        private String param2;

        private boolean param3;

        private Object param4;

        private int param5;

        public Builder setParam1(final int param1) {
            this.param1 = param1;
            return this;
        }

        public Builder setParam2(final String param2) {
            this.param2 = param2;
            return this;
        }

        public Builder setParam3(final boolean param3) {
            this.param3 = param3;
            return this;
        }

        public Builder setParam4(final Object param4) {
            this.param4 = param4;
            return this;
        }

        public Builder setParam5(final int param5) {
            this.param5 = param5;
            return this;
        }

        public Example04Builder build() {
            return new Example04Builder(param1, param2, param3, param4, param5);
        }
    }
}
