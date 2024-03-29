package controller;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;


/** Vi phạm Dependency Inversion Principle (DIP):
 * Lớp PlaceOrderController trực tiếp phụ thuộc vào Cart, Order, và Media, thậm chí còn gọi đến các
 * phương thức tĩnh của Media. Điều này làm cho lớp trở nên cứng nhắc và khó kiểm thử.
 */
public class PlaceOrderController extends BaseController {

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());
    //Functional Cohesion
    /**
     * This method checks the avalibility of product when user click PlaceOrder
     * button
     *
     * @throws SQLException
     */
    /**
     *
     * @throws SQLException
     */
    public void placeOrder() throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     *
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException {
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     *
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {

        order.createOrderEntity();
        return new Invoice(order);
    }
// Sequential Cohesion:
    /**
     * This method takes responsibility for processing the shipping info from user
     *
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    //content coupling
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException {
        validateDeliveryInfo(info);
    }

    /**
     * The method validates the info
     *
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    //content coupling
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {

    }


    /**
     * @param phoneNumber
     * @return boolean
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10)
            return false;
        if (Character.compare(phoneNumber.charAt(0), '0') != 0)
            return false;
        try {
            Long.parseUnsignedLong(phoneNumber);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }


    /**
     * @param name
     * @return boolean
     */
    public boolean validateContainLetterAndNoEmpty(String name) {
        // Check name is not null
        if (name == null)
            return false;
        // Check if contain leter space only
        if (name.trim().length() == 0)
            return false;
        // Check if contain only leter and space
        if (name.matches("^[a-zA-Z ]*$") == false)
            return false;
        return true;
    }


    /**
     * This method calculates the shipping fees of order
     *
     * @param order
     * @return shippingFee
     */
    // Coincidental Cohesion:
    public int calculateShippingFee(int amount) {
        Random rand = new Random();
        int fees = (int) (((rand.nextFloat() * 10) / 100) * amount);
        return fees;
    }

    /**
     * This method get product available place rush order media
     *
     * @param order
     * @return media
     * @throws SQLException
     */
    //Stamp && content coupling
    public Media getProductAvailablePlaceRush(Order order) throws SQLException {
        Media media = new Media();
        for (OrderMedia pd : order.getlstOrderMedia()) {
            // CartMedia cartMedia = (CartMedia) object;
            if( validateMediaPlaceRushorder()){ // This method call indicates stamp coupling.
                media = pd.getMedia();// This line indicates content coupling.
            }
        }
        return media;
    }


    /**
     * @param province
     * @param address
     * @return boolean
     */
    public boolean validateAddressPlaceRushOrder(String province, String address) {
        if (!validateContainLetterAndNoEmpty(address))
            return false;
        if (!province.equals("Hà Nội"))
            return false;
        return true;
    }


    /**
     * @return boolean
     */
    //Stamp coupling
    public boolean validateMediaPlaceRushorder() {
        if (Media.getIsSupportedPlaceRushOrder()) //// This static method call indicates stamp coupling.
            return true;
        return false;
    }
}
