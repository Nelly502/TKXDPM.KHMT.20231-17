package views.screen.manage.media.form;

import entity.media.Book;
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

public class BookScreenHandler extends MediaScreenHandler implements Initializable {

    @FXML
    protected TextField authorField;
    @FXML
    protected TextField coverTypeField;
    @FXML
    protected TextField publisherField;
    @FXML
    protected DatePicker publishDateField;
    @FXML
    protected TextField numOfPagesField;
    @FXML
    protected TextField languageField;
    @FXML
    protected TextField bookCategoryField;

    public BookScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }

    @Override
    protected void save() throws SQLException {
        Book book = getBookValues();
        getBController().saveMedia(book);
    }

    protected Book getBookValues() {
        Media media = getMediaValues();

        String author = authorField.getText();
        String coverType = coverTypeField.getText();
        String publisher = publisherField.getText();
        Date publishDate = Date.valueOf(publishDateField.getValue().toString());
        int numOfPages = Integer.valueOf(numOfPagesField.getText());
        String language = languageField.getText();
        String bookCategory = bookCategoryField.getText();

        return new Book(media.getId(), media.getTitle(), media.getCategory(), media.getPrice(), media.getQuantity(), media.getImageURL(), "book",
                author, coverType, publisher, publishDate, numOfPages, language, bookCategory);
    }

    public void setDefaultBookValues() throws SQLException {
        Book book = (Book)this.getBController().getMediaById(id);

        if (book != null) {
            super.setDefaultValues(book.getTitle(), book.getCategory(), book.getPrice(), book.getValue(), book.getQuantity(), book.getImageURL());

            authorField.setText(book.getAuthor());
            coverTypeField.setText(book.getCoverType());
            publisherField.setText(book.getPublisher());
            if (book.getPublishDate() != null) publishDateField.setValue(LocalDate.parse(book.getPublishDate().toString()));
            numOfPagesField.setText("" + book.getNumOfPages());
            languageField.setText(book.getLanguage());
            bookCategoryField.setText(book.getBookCategory());
        }
    }
}
