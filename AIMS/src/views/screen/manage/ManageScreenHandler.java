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

        backHome.setOnAction(e -> {
            System.out.println("back home");
            backToHome();
        });
    }

    protected void openMediaManage() {
        try {
            MediaManageScreenHandler mediaManageScreen = new MediaManageScreenHandler(this.stage, Configs.MEDIA_MANAGE_SCREEN_PATH);
            mediaManageScreen.setHomeScreenHandler(this.home);
            mediaManageScreen.setBController(new MediaController());
            mediaManageScreen.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void backToHome() {
        try {
            HomeScreenHandler homeHandler = new HomeScreenHandler(stage, Configs.HOME_PATH);
            homeHandler.setScreenTitle("Home Screen");
            homeHandler.setImage();
            homeHandler.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
