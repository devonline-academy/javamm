package academy.devonline.temp.gui.gui1_awt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class AwtApplicationWindow extends Frame {

    private static final int WIDTH = 600;

    private static final int HEIGHT = 400;

    private AwtApplicationWindow() {
        super("AWT");
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                dispose();
            }
        });

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        pack();
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        setMenuBar(createMenuBar());
        add(new TextArea(), BorderLayout.CENTER);
    }

    public static void main(final String[] args) {
        new AwtApplicationWindow().setVisible(true);
    }

    private MenuBar createMenuBar() {
        final MenuBar menuBar = new MenuBar();
        final Menu mFile = new Menu("File");
        menuBar.add(mFile);

        final MenuItem miExit = new MenuItem("Exit");
        miExit.addActionListener(e -> System.exit(0));
        mFile.add(miExit);

        return menuBar;
    }
}
