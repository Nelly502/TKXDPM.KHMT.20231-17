package views.screen.manage.media.form;

import controller.MediaController;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.manage.media.MediaManageScreenHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MediaScreenHandler extends BaseScreenHandler implements Initializable {
    @FXML
    protected Label formTitle;

    @FXML
    protected TextField titleField;

    @FXML
    protected TextField categoryField;

    @FXML
    protected TextField priceField;

    @FXML
    protected TextField quantityField;

    @FXML
    protected Button uploadButton;

    @FXML
    protected Button saveButton;

    @FXML
    protected Button cancelButton;

    protected int id;
    protected String uploadedFilePath = "";

    public void setId(int id) {
        this.id = id;
    }

    public MediaScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public MediaController getBController() {
        return (MediaController) super.getBController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelButton.setOnAction(e -> {
            try {
                backScreen();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        saveButton.setOnAction(e -> {
            try {
                save();
                backScreen();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                    .addAll(
                            new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg"),
                            new FileChooser.ExtensionFilter("png files (*.jpeg)", "*.jpeg"),
                            new FileChooser.ExtensionFilter("png files (*.png)", "*.png")
                    );
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                String assetsFolder = "assets/images/uploaded";
                File assetsDir = new File(assetsFolder);
                if (!assetsDir.exists()) {
                    assetsDir.mkdirs();
                }
                try {
                    String uniqueFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                    Path destinationPath = Paths.get(assetsFolder, uniqueFileName);
                    Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    uploadButton.setText(selectedFile.getName());
                    uploadedFilePath = assetsFolder + "/" + uniqueFileName;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    protected void save() throws SQLException {
        Media media = createMediaFromInput();
        getBController().saveMedia(media);
    }

    protected Media createMediaFromInput() {
        String title = titleField.getText();
        String category = categoryField.getText();
        int price = Integer.valueOf(priceField.getText());
        int quantity = Integer.valueOf(quantityField.getText());

        return new Media(id, title, category, price, quantity, uploadedFilePath, "");
    }

    protected void backScreen() throws IOException {
        MediaManageScreenHandler mediaScreen = new MediaManageScreenHandler(this.stage, Configs.MEDIA_MANAGE_SCREEN_PATH);
        mediaScreen.setBController(new MediaController());
        mediaScreen.show();
    }

    protected void setDefaultValues(String title, String category, int price, int value, int quantity, String imageURL) {
        titleField.setText(title);
        categoryField.setText(category);
        priceField.setText(String.valueOf(price));
        quantityField.setText(String.valueOf(quantity));
        uploadedFilePath = imageURL;
    }
    public void setTitle(String title) {
        this.formTitle.setText(title);
    }
}

