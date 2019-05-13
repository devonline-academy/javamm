package academy.devonline.temp.gui.gui2_swing;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class SwingApplicationWindow extends JFrame {

    private static final int WIDTH = 600;

    private static final int HEIGHT = 400;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private SwingApplicationWindow() {
        super("Swing");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        pack();
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        final JMenuBar menuBar = createMenuBar();
        add(menuBar, BorderLayout.NORTH);
        add(new JTextArea(), BorderLayout.CENTER);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> new SwingApplicationWindow().setVisible(true));
    }

    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu mFile = new JMenu("File");
        menuBar.add(mFile);

        final JMenuItem miExit = new JMenuItem("Exit");
        miExit.addActionListener(e -> System.exit(0));
        mFile.add(miExit);

        return menuBar;
    }
}
