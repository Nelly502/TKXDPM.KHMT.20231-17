package views.screen.manage.media;

import controller.MediaController;
import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.manage.ManageScreenHandler;
import views.screen.manage.media.form.BookScreenHandler;
import views.screen.manage.media.form.CDScreenHandler;
import views.screen.manage.media.form.DVDScreenHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MediaManageScreenHandler extends ManageScreenHandler implements Initializable {
    public static Logger LOGGER = Utils.getLogger(ManageScreenHandler.class.getName());
    private final String BOOK = "book";
    private final String DVD = "dvd";
    private final String CD = "cd";

    @FXML
    private ComboBox addComboBox;

    @FXML
    private TableView<Media> mediaTableView;

    @FXML
    private TableColumn<Media, Integer> idColumn;

    @FXML
    private TableColumn<Media, String> titleColumn;

    @FXML
    private TableColumn<Media, String> categoryColumn;

    @FXML
    private TableColumn<Media, Integer> valueColumn;

    @FXML
    private TableColumn<Media, Integer> priceColumn;

    @FXML
    private TableColumn<Media, Integer> quantityColumn;

    @FXML
    private TableColumn<Media, String> typeColumn;

    @FXML
    private TableColumn<Media, String> imageColumn;

    @FXML
    private TableColumn<Media, Media> actionsColumn;

    private MediaController bookController;
    private MediaController cdController;
    private MediaController dvdController;

    public MediaController getBController() {
        return (MediaController) super.getBController();
    }

    public MediaManageScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        super.setBController(new MediaController());
        bookController = new MediaController(new Book());
        cdController = new MediaController(new CD());
        dvdController = new MediaController(new DVD());
        ObservableList<String> addComboBoxItems = FXCollections.observableArrayList(BOOK, DVD, CD);
        addComboBox.setItems(addComboBoxItems);
        addComboBox.setOnAction(e -> {
            String type = addComboBox.getSelectionModel().getSelectedItem().toString();
            switch (type) {
                case BOOK: {
                    redirectToBookForm(0);
                    break;
                }
                case CD: {
                    redirectToCDForm(0);
                    break;
                }
                case DVD: {
                    redirectToDVDForm(0);
                    break;
                }
            }
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<Media, Integer>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Media, String>("title"));
        categoryColumn.setCellValueFactory((new PropertyValueFactory<Media, String>("category")));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Media, Integer>("value"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Media, Integer>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Media, Integer>("quantity"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Media, String>("type"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<Media, String >("imageURL"));

        actionsColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actionsColumn.setCellFactory(param -> new TableCell<Media, Media>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Media media, boolean empty) {
                if (empty) {
                    setGraphic(null);
                    return;
                }

                HBox buttonsHBox = new HBox(editButton, deleteButton);

                switch (media.getType()) {
                    case BOOK: {
                        editButton.setOnAction(e -> {
                            redirectToBookForm(media.getId());
                        });

                        deleteButton.setOnAction(e -> {
                            try {
                                bookController.deleteMediaById(media.getId());
                                openMediaManage();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                        break;
                    }
                    case CD: {
                        editButton.setOnAction(e -> {
                            redirectToCDForm(media.getId());
                        });

                        deleteButton.setOnAction(e -> {
                            try {
                                cdController.deleteMediaById(media.getId());
                                openMediaManage();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                        break;
                    }
                    case DVD: {
                        editButton.setOnAction(e -> {
                            redirectToDVDForm(media.getId());
                        });

                        deleteButton.setOnAction(e -> {
                            try {
                                dvdController.deleteMediaById(media.getId());
                                openMediaManage();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                        break;
                    }
                }

                setGraphic(buttonsHBox);
            }
        });

        try {
            mediaTableView.setItems(getBController().getAllMedia());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void redirectToBookForm(int id) {
        try {
            BookScreenHandler bookFormScreen = new BookScreenHandler(this.stage, Configs.BOOK_SCREEN_PATH);
            bookFormScreen.setId(id);
            bookFormScreen.setBController(bookController);
            bookFormScreen.setDefaultBookValues();
            if (id != 0) {
                bookFormScreen.setFormTitle("EDITbook");
            } else  {
                bookFormScreen.setFormTitle("ADD book");
            }
            bookFormScreen.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void redirectToCDForm(int id) {
        try {
            CDScreenHandler cdFormScreen = new CDScreenHandler(this.stage, Configs.CD_SCREEN_PATH);
            cdFormScreen.setId(id);
            cdFormScreen.setBController(cdController);
            cdFormScreen.setDefaultCDValues();
            if (id != 0) {
                cdFormScreen.setFormTitle("EDIT CD");
            } else  {
                cdFormScreen.setFormTitle("ADD CD");
            }
            cdFormScreen.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void redirectToDVDForm(int id) {
        try {
            DVDScreenHandler dvdFormScreen = new DVDScreenHandler(this.stage, Configs.DVD_SCREEN_PATH);
            dvdFormScreen.setId(id);
            dvdFormScreen.setBController(dvdController);
            dvdFormScreen.setDefaultDVDValues();
            if (id != 0) {
                dvdFormScreen.setFormTitle("EDIDVD");
            } else  {
                dvdFormScreen.setFormTitle("ADD DVD");
            }
            dvdFormScreen.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
