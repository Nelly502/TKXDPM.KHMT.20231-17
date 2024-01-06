package views.screen.manage;

import controller.MediaController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;
import views.screen.manage.media.MediaManageScreenHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageScreenHandler extends BaseScreenHandler implements Initializable {

    @FXML
    protected Button mediaManage;

    @FXML
    protected Button backHome;

    protected HomeScreenHandler home;

    public ManageScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backHome.setOnAction(e -> backToHome());
    }

    @FXML
    protected void openMediaManage() {
        try {
            MediaManageScreenHandler mediaManageScreen = createMediaManageScreen();
            configureMediaManageScreen(mediaManageScreen);
            mediaManageScreen.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    protected void backToHome() {
        try {
            HomeScreenHandler homeHandler = createHomeScreen();
            configureHomeScreen(homeHandler);
            homeHandler.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private MediaManageScreenHandler createMediaManageScreen() throws IOException {
        return new MediaManageScreenHandler(stage, Configs.MEDIA_MANAGE_SCREEN_PATH);
    }

    private void configureMediaManageScreen(MediaManageScreenHandler mediaManageScreen) {
        mediaManageScreen.setHomeScreenHandler(home);
        mediaManageScreen.setBController(new MediaController());
    }

    private HomeScreenHandler createHomeScreen() throws IOException {
        return new HomeScreenHandler(stage, Configs.HOME_PATH);
    }

    private void configureHomeScreen(HomeScreenHandler homeHandler) {
        homeHandler.setScreenTitle("Home Screen");
        homeHandler.setImage();
    }
}
