package views.screen.manage.media.form;

import entity.media.CD;
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

public class CDScreenHandler extends MediaScreenHandler implements Initializable {

    @FXML
    protected TextField artistField;
    @FXML
    protected TextField recordLabelField;
    @FXML
    protected TextField musicTypeField;
    @FXML
    protected DatePicker releasedDateField;

    public CDScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }

    @Override
    protected void save() throws SQLException {
        CD cd = getCDValues();
        getBController().saveMedia(cd);
    }

    protected CD getCDValues() {
        Media media = getMediaValues();

        String artist = artistField.getText();
        String recordLabel = recordLabelField.getText();
        String musicType = musicTypeField.getText();
        Date releaseDate = Date.valueOf(releasedDateField.getValue().toString());

        return new CD(media.getId(), media.getTitle(), media.getCategory(), media.getPrice(), media.getQuantity(), media.getImageURL(), "cd",
                artist, recordLabel, musicType, releaseDate);
    }

    public void setDefaultCDValues() throws SQLException {
        CD cd = (CD)this.getBController().getMediaById(id);

        if (cd != null) {
            super.setDefaultValues(cd.getTitle(), cd.getCategory(), cd.getPrice(), cd.getValue(), cd.getQuantity(), cd.getImageURL());

            artistField.setText(cd.getArtist());
            recordLabelField.setText(cd.getRecordLabel());
            musicTypeField.setText(cd.getMusicType());
            if (cd.getReleasedDate() != null) releasedDateField.setValue(LocalDate.parse(cd.getReleasedDate().toString()));
        }
    }
}
