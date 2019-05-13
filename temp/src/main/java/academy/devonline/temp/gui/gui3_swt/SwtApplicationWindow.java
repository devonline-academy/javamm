package academy.devonline.temp.gui.gui3_swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://www.eclipse.org/swt/
 * @link http://download.eclipse.org/eclipse/downloads/
 * <p>
 * mvn install:install-file -Dfile=/tmp/swt.jar -DgroupId=org.eclipse -DartifactId=swt -Dversion=4.10 -Dpackaging=jar
 */
@SuppressWarnings("CheckStyle")
public final class SwtApplicationWindow {

    private static final int WIDTH = 600;

    private static final int HEIGHT = 400;

    private SwtApplicationWindow() {
        final Display display = new Display();
        final Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE));
        shell.setText("SWT");
        final Menu bar = createMenuBar(shell);
        shell.setMenuBar(bar);
        shell.setLayout(new FillLayout());
        new StyledText(shell, SWT.NONE);

        shell.setSize(WIDTH, HEIGHT);

        final Monitor primary = display.getPrimaryMonitor();
        final Rectangle bounds = primary.getBounds();
        final Rectangle rect = shell.getBounds();
        final int x = bounds.x + (bounds.width - rect.width) / 2;
        final int y = bounds.y + (bounds.height - rect.height) / 2;
        shell.setLocation(x, y);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
    }

    public static void main(final String[] args) {
        new SwtApplicationWindow();
    }

    private Menu createMenuBar(final Shell shell) {
        final Menu bar = new Menu(shell, SWT.BAR);

        final MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
        fileItem.setText("File");
        final Menu submenu = new Menu(shell, SWT.DROP_DOWN);
        fileItem.setMenu(submenu);
        final MenuItem item = new MenuItem(submenu, SWT.PUSH);
        item.addListener(SWT.Selection, e -> System.exit(0));
        item.setText("Exit");

        return bar;
    }
}
