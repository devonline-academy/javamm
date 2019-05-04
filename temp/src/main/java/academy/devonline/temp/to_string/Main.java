package academy.devonline.temp.to_string;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {
        final VarDeclaration example1 = new Example01VarDeclarationConcat("array", 12);
        System.out.println(example1);

        final VarDeclaration example2 = new Example02VarDeclarationStringBuilder("array", 12);
        System.out.println(example2);

        final VarDeclaration example3 = new Example03VarDeclarationFormat("array", 12);
        System.out.println(example3);
    }
}
