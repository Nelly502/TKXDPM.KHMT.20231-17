import controller.HomeController;
import views.screen.home.HomeScreenHandler;
import entity.cart.Cart;
import entity.media.Media;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import views.screen.home.HomeScreenHandler;
import views.screen.home.MediaHandler;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.powermock.api.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HomeScreenHandlerTest {

    private HomeScreenHandler homeScreenHandler;

    private Media createMockMedia(String title, int price, String category) {
        Media mockMedia = mock(Media.class);
        when(mockMedia.getTitle()).thenReturn(title);
        when(mockMedia.getPrice()).thenReturn(price);
        when(mockMedia.getCategory()).thenReturn(category);
        // Các thuộc tính khác của Media cũng có thể được đặt giá trị tương tự nếu cần
        return mockMedia;
    }

    private MediaHandler createMockMediaHandler(String title, int price, String category) {
        MediaHandler mediaHandler = mock(MediaHandler.class);
        when(mediaHandler.getMedia()).thenReturn(createMockMedia(title, price, category));
        return mediaHandler;
    }

    @BeforeEach
    void setUp() throws IOException, SQLException {
        // Mock dependencies
        HomeController mockController = mock(HomeController.class);
        List<Media> mockMediaList = new ArrayList<>();
        when(mockController.getAllMedia()).thenReturn(mockMediaList);

        // Create HomeScreenHandler instance for testing
        try {
            homeScreenHandler = new HomeScreenHandler(mock(Stage.class), "path/to/screen.fxml");
            homeScreenHandler.setBController(mockController);
        } catch (IOException e) {
            e.printStackTrace(); // Handle IOException according to your needs
        }
    }

    @Test
    void testShow() {
        // Mock Cart for testing
        Cart.getCart().getListMedia().add(mock(Media.class));
        homeScreenHandler.show();
        assertEquals("1 media", homeScreenHandler.getNumMediaCartLabel().getText());
    }

    @Test
    void testUpdateMediaDisplay() {
        List<MediaHandler> mockMediaHandlers = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            mockMediaHandlers.add(mock(MediaHandler.class));
        }
        homeScreenHandler.setCurrentPage(1);
        homeScreenHandler.setDisplayedItems(mockMediaHandlers);

        List<MediaHandler> updatedDisplay = homeScreenHandler.updateMediaDisplay(mockMediaHandlers);
        assertEquals(3, updatedDisplay.size()); // Assuming itemsPerPage is 12
    }


    @Test
    void testAddMediaHome() {
        List<MediaHandler> mockMediaHandlers = new ArrayList<>();
        mockMediaHandlers.add(createMockMediaHandler("Book1", 9, "book"));
        mockMediaHandlers.add(createMockMediaHandler("Book2", 21, "book"));
        mockMediaHandlers.add(createMockMediaHandler("DVD1", 49, "dvd"));

        homeScreenHandler.addMediaHome(mockMediaHandlers);

        assertEquals(3, homeScreenHandler.hboxMedia.getChildren().size());

        homeScreenHandler.addMediaHome(new ArrayList<>());

        assertEquals(0, homeScreenHandler.hboxMedia.getChildren().size());
    }

    @Test
    void testSearchButtonClicked() {
        // Mock data
        List<MediaHandler> mockMediaHandlers = new ArrayList<>();
        mockMediaHandlers.add(createMockMediaHandler("Book1", 5, "book"));
        mockMediaHandlers.add(createMockMediaHandler("Book2", 30, "book"));
        mockMediaHandlers.add(createMockMediaHandler("DVD1", 80, "dvd"));
        mockMediaHandlers.add(createMockMediaHandler("CD1", 120, "cd"));

        homeScreenHandler.setHomeItems(Arrays.asList(
                createMockMedia("Book1", 5, "book"),
                createMockMedia("Book2", 30, "book"),
                createMockMedia("DVD1", 80, "dvd"),
                createMockMedia("CD1", 120, "cd")
        ));

        homeScreenHandler.setDisplayedItems(mockMediaHandlers);

        // Test search by product name
        homeScreenHandler.getSearchField().setText("Book");
        homeScreenHandler.searchButtonClicked(null);

        assertEquals(2, homeScreenHandler.getDisplayedItems().size());
        assertTrue(((MediaHandler) homeScreenHandler.getDisplayedItems().get(0)).getMedia().getTitle().startsWith("Book"));

        // Test search by category
        homeScreenHandler.getSearchField().setText("DVD");
        homeScreenHandler.searchButtonClicked(null);

        assertEquals(1, homeScreenHandler.getDisplayedItems().size());
        assertTrue(((MediaHandler) homeScreenHandler.getDisplayedItems().get(0)).getMedia().getTitle().startsWith("DVD"));

        // Test search by price < 10
        homeScreenHandler.getSearchField().setText("<10");
        homeScreenHandler.searchButtonClicked(null);

        assertEquals(1, homeScreenHandler.getDisplayedItems().size());
        assertTrue(((MediaHandler) homeScreenHandler.getDisplayedItems().get(0)).getMedia().getPrice() < 10);

        // Test search by price 10-50
        homeScreenHandler.getSearchField().setText("10-50");
        homeScreenHandler.searchButtonClicked(null);

        assertEquals(1, homeScreenHandler.getDisplayedItems().size());
        assertTrue(((MediaHandler) homeScreenHandler.getDisplayedItems().get(0)).getMedia().getPrice() >= 10
                && ((MediaHandler) homeScreenHandler.getDisplayedItems().get(0)).getMedia().getPrice() <= 50);

        // Test search by price 50-100
        homeScreenHandler.getSearchField().setText("50-100");
        homeScreenHandler.searchButtonClicked(null);

        assertEquals(1, homeScreenHandler.getDisplayedItems().size());
        assertTrue(((MediaHandler) homeScreenHandler.getDisplayedItems().get(0)).getMedia().getPrice() > 50
                && ((MediaHandler) homeScreenHandler.getDisplayedItems().get(0)).getMedia().getPrice() <= 100);

        // Test search by price > 100
        homeScreenHandler.getSearchField().setText(">100");
        homeScreenHandler.searchButtonClicked(null);

        assertEquals(1, homeScreenHandler.getDisplayedItems().size());
        assertTrue(((MediaHandler) homeScreenHandler.getDisplayedItems().get(0)).getMedia().getPrice() > 100);

        // Test search with no results
        homeScreenHandler.getSearchField().setText("Nonexistent");
        assertThrows(IOException.class, () -> homeScreenHandler.searchButtonClicked(null));
    }

}
