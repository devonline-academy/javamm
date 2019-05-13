package academy.devonline.temp.gui.gui4_javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://openjfx.io/
 */
@SuppressWarnings("CheckStyle")
public class JavafxApplicationWindow extends Application {

    private static final int WIDTH = 600;

    private static final int HEIGHT = 400;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("JavaFx");
        primaryStage.setResizable(false);

        final BorderPane borderPane = new BorderPane();
        final MenuBar menuBar = createMenuBar();
        borderPane.setTop(menuBar);
        borderPane.setCenter(new TextArea());

        primaryStage.setScene(new Scene(borderPane, WIDTH, HEIGHT));
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        final MenuBar menuBar = new MenuBar();
        final Menu mFile = new Menu("File");
        menuBar.getMenus().add(mFile);

        final MenuItem miExit = new MenuItem("Exit");
        miExit.setOnAction(event -> System.exit(0));
        mFile.getItems().add(miExit);

        return menuBar;
    }
}
