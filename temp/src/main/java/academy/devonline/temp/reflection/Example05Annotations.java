package academy.devonline.temp.reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class Example05Annotations {

    private Example05Annotations() {
    }

    public static void main(final String[] args) throws IllegalAccessException {
        final Example example = new Example();
        System.out.println(example.getList1());
        System.out.println(example.getList2());

        processAnnotations(example);

        System.out.println(example.getList1());
        System.out.println(example.getList1().getClass());
        System.out.println(example.getList2());
        System.out.println(example.getList2().getClass());
    }

    private static void processAnnotations(final Object instance) throws IllegalAccessException {
        Class<?> currentClass = instance.getClass();
        while (currentClass != Object.class) {
            for (final Field field : currentClass.getDeclaredFields()) {
                final SetEmptyList annotation = field.getAnnotation(SetEmptyList.class);
                if (annotation != null) {
                    setEmptyValue(field, instance, annotation.useNewStyle());
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }

    private static void setEmptyValue(final Field field,
                                      final Object instance,
                                      final boolean useNewStyle) throws IllegalAccessException {
        if (!field.canAccess(instance)) {
            field.setAccessible(true);
        }
        final List<?> emptyList = useNewStyle ? List.of() : Collections.emptyList();
        field.set(instance, emptyList);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SetEmptyList {

        boolean useNewStyle() default true;
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public static final class Example {

        @SetEmptyList
        private List<String> list1;

        @SetEmptyList(useNewStyle = false)
        private List<String> list2;

        public List<String> getList1() {
            return list1;
        }

        public List<String> getList2() {
            return list2;
        }
    }
}
