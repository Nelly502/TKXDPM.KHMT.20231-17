package views.screen.home;

import common.exception.ViewCartException;
import controller.HomeController;
import controller.BaseController;
import controller.MediaController;
import controller.ViewCartController;
import entity.cart.Cart;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.cart.CartScreenHandler;
import views.screen.manage.media.MediaManageScreenHandler;
import views.screen.popup.PopupScreen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class HomeScreenHandler extends BaseScreenHandler implements Initializable {

    public static Logger LOGGER = Utils.getLogger(HomeScreenHandler.class.getName());

    @FXML
    private Label numMediaInCart;

    @FXML
    private ImageView aimsImage;

    @FXML
    private ImageView cartImage;

    @FXML
    private VBox vboxMedia1;

    @FXML
    private VBox vboxMedia2;

    @FXML
    private VBox vboxMedia3;

    @FXML
    private HBox hboxMedia;

    @FXML
    private SplitMenuButton splitMenuBtnSearch;

    @FXML
    private TextField searchField;

    @FXML
    private Label pageLabel;

    private List displayedItems;

    private List homeItems;

    public HomeScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    /**
     * @return Label
     */
    public Label getNumMediaCartLabel() {
        return this.numMediaInCart;
    }

    /**
     * @return HomeController
     */
    public HomeController getBController() {
        return (HomeController) super.getBController();
    }

    @Override
    public void show() {
        numMediaInCart.setText(String.valueOf(Cart.getCart().getListMedia().size()) + " media");
        super.show();
    }

    private int currentPage = 0;
    private final int itemsPerPage = 12;

    @FXML
    private void showNextPage(MouseEvent event) {
        int start = currentPage * itemsPerPage;
        int end = Math.min(start + itemsPerPage, displayedItems.size());

        if (end < displayedItems.size()) {
            currentPage++;
            List<MediaHandler> displayedItems = updateMediaDisplay(this.displayedItems);
            addMediaHome(displayedItems);
        }
    }

    @FXML
    private void showPrePage(MouseEvent event) {
        if (currentPage > 0) {
            currentPage--;
            List<MediaHandler> displayedItems = updateMediaDisplay(this.displayedItems);
            addMediaHome(displayedItems);
        }
    }

    private List<MediaHandler> updateMediaDisplay( List Items) {
        int start = currentPage * itemsPerPage;
        int end = Math.min(start + itemsPerPage, Items.size());
        List<MediaHandler> displayedItems = new ArrayList<>(Items.subList(start, end));

        int totalPages = (int) Math.ceil((double) Items.size() / itemsPerPage);
        int currentDisplayPage = currentPage + 1;
        pageLabel.setText("       " + currentDisplayPage + " / " + totalPages);
        return displayedItems;
    }
    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setBController(new HomeController());
        try {
            List medium = getBController().getAllMedia();
            this.homeItems = new ArrayList<>();
            for (Object object : medium) {
                Media media = (Media) object;
                MediaHandler m1 = new MediaHandler(Configs.HOME_MEDIA_PATH, media, this);
                this.homeItems.add(m1);
            }
            this.displayedItems = this.homeItems;
        } catch (SQLException | IOException e) {
            LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
        }

        aimsImage.setOnMouseClicked(e -> {
            addMediaHome(this.homeItems);
        });

        cartImage.setOnMouseClicked(e -> {
            CartScreenHandler cartScreen;
            try {
                LOGGER.info("User clicked to view cart");
                cartScreen = new CartScreenHandler(this.stage, Configs.CART_SCREEN_PATH);
                cartScreen.setHomeScreenHandler(this);
                cartScreen.setBController(new ViewCartController());
                cartScreen.requestToViewCart(this);
            } catch (IOException | SQLException e1) {
                throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
            }
        });
        addMediaHome(this.homeItems);
        addMenuItem(0, "Book", splitMenuBtnSearch);
        addMenuItem(1, "DVD", splitMenuBtnSearch);
        addMenuItem(2, "CD", splitMenuBtnSearch);
        addMenuItem(3, "<10k đ", splitMenuBtnSearch);
        addMenuItem(4, "10k đ-50k đ", splitMenuBtnSearch);
        addMenuItem(5, "50k đ-100k đ", splitMenuBtnSearch);
        addMenuItem(6, ">100k đ", splitMenuBtnSearch);

        aimsImage.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MediaManageScreenHandler mediaManageScreen;
            try {
                mediaManageScreen = new MediaManageScreenHandler(this.stage, Configs.MEDIA_MANAGE_SCREEN_PATH);
                mediaManageScreen.setHomeScreenHandler(this);
                mediaManageScreen.setBController(new MediaController());
                mediaManageScreen.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setImage() {
        // fix image path caused by fxml
        File file1 = new File(Configs.IMAGE_PATH + "/" + "Logo.png");
        Image img1 = new Image(file1.toURI().toString());
        aimsImage.setImage(img1);

        File file2 = new File(Configs.IMAGE_PATH + "/" + "cart.png");
        Image img2 = new Image(file2.toURI().toString());
        cartImage.setImage(img2);
    }

    /**
     * @param items
     */
    public void addMediaHome(List items) {
        ArrayList mediaItems = (ArrayList) ((ArrayList) items).clone();
        hboxMedia.getChildren().forEach(node -> {
            VBox vBox = (VBox) node;
            vBox.getChildren().clear();
        });
        while (!mediaItems.isEmpty()) {
            hboxMedia.getChildren().forEach(node -> {
                int vid = hboxMedia.getChildren().indexOf(node);
                VBox vBox = (VBox) node;
                while (vBox.getChildren().size() < 3 && !mediaItems.isEmpty()) {
                    MediaHandler media = (MediaHandler) mediaItems.get(0);
                    vBox.getChildren().add(media.getContent());
                    mediaItems.remove(media);
                }
            });
            return;
        }
    }

    /**
     * @param position
     * @param text
     * @param menuButton
     */
    private void addMenuItem(int position, String text, MenuButton menuButton) {
        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction(e -> {
            // empty home media
            hboxMedia.getChildren().forEach(node -> {
                VBox vBox = (VBox) node;
                vBox.getChildren().clear();
            });

            // filter only media with the choosen category or price
            List filteredItems = new ArrayList<>();
            homeItems.forEach(me -> {
                MediaHandler media = (MediaHandler) me;
                if (media.getMedia().getTitle().toLowerCase().startsWith(text.toLowerCase())) {
                    filteredItems.add(media);
                }else {
                    if (text.equals("<10k đ")) {
                        if (media.getMedia().getPrice() < 10) {
                            filteredItems.add(media);
                        }

                    } else if (text.equals("10k đ-50k đ")) {
                        if (media.getMedia().getPrice() >= 10 && media.getMedia().getPrice() <= 50) {
                            filteredItems.add(media);
                        }
                    } else if (text.equals("50k đ-100k đ")) {
                        if (media.getMedia().getPrice() > 50 && media.getMedia().getPrice() <= 100) {
                            filteredItems.add(media);
                        }
                    } else if (text.equals("50k đ-100k đ")) {
                        if (media.getMedia().getPrice() > 100) {
                            filteredItems.add(media);
                        }
                    }
                    Collections.sort(filteredItems, Comparator.comparingDouble(
                            mediax -> ((MediaHandler) mediax).getMedia().getPrice()));
                }
            });

            // fill out the home with filted media as category or price
            if (filteredItems.isEmpty()) {
                try {
                    PopupScreen.error("Couldn't find any products!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                // fill out the home with filtered media as a category
                currentPage = 0;
                this.displayedItems = filteredItems;
                List<MediaHandler> displayedItems = updateMediaDisplay(filteredItems);
                addMediaHome(displayedItems);

            }
        });
        menuButton.getItems().add(position, menuItem);
    }

    @FXML
    private void searchButtonClicked(MouseEvent event) {
        String searchText = searchField.getText().toLowerCase().trim();
        // Filter by productname
        List<MediaHandler> filteredItems = new ArrayList<>();
        for (Object item : homeItems) {
            MediaHandler media = (MediaHandler) item;
            if (media.getMedia().getTitle().toLowerCase().contains(searchText)) {
                filteredItems.add(media);
            }
        }
        if (filteredItems.isEmpty()) {
            try {
                PopupScreen.error("Couldn't find any products!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            // fill out the home with filtered media as a category
            currentPage = 0;
            this.displayedItems = filteredItems;
            List<MediaHandler> displayedItems = updateMediaDisplay(filteredItems);
            addMediaHome(displayedItems);
        }
    }

}