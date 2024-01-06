package views.screen.manage.media.form;

import entity.media.DVD;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DVDScreenHandler extends MediaScreenHandler implements Initializable {

    @FXML
    protected TextField discTypeField;
    @FXML
    protected TextField directorField;
    @FXML
    protected TextField runtimeField;
    @FXML
    protected TextField studioField;
    @FXML
    protected TextField subtitleField;
    @FXML
    protected DatePicker releasedDateField;
    @FXML
    protected TextField filmTypeField;

    public DVDScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }

    @Override
    protected void save() throws SQLException {
        DVD dvd = getDVDValues();
        getBController().saveMedia(dvd);
    }

    protected DVD getDVDValues() {
        Media media = getMediaValues();

        String discType = discTypeField.getText();
        String director = directorField.getText();
        int runtime = Integer.valueOf(runtimeField.getText());
        String studio = studioField.getText();
        String subtitle = subtitleField.getText();
        Date releaseDate = Date.valueOf(releasedDateField.getValue().toString());
        String filmType = filmTypeField.getText();

        return new DVD(media.getId(), media.getTitle(), media.getCategory(), media.getPrice(), media.getQuantity(), media.getImageURL(), "dvd",
               discType, director, runtime, studio, subtitle, releaseDate, filmType);
    }

    public void setDefaultDVDValues() throws SQLException {
        DVD dvd = (DVD) this.getBController().getMediaById(id);

        if (dvd != null) {
            super.setDefaultValues(dvd.getTitle(), dvd.getCategory(), dvd.getPrice(), dvd.getValue(), dvd.getQuantity(), dvd.getImageURL());

            discTypeField.setText(dvd.getDiscType());
            directorField.setText(dvd.getDirector());
            runtimeField.setText("" + dvd.getRuntime());
            studioField.setText(dvd.getStudio());
            subtitleField.setText(dvd.getSubtitle());
            if (dvd.getReleasedDate() != null) releasedDateField.setValue(LocalDate.parse(dvd.getReleasedDate().toString()));
            filmTypeField.setText(dvd.getSubtitle());
        }
    }
}
