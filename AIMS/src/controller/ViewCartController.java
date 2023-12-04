package controller;

import entity.cart.Cart;

import java.sql.SQLException;

/**
 * This class controls the flow of events when users view the Cart
 *
 * @author nguyenlm
 */

/**
 * Functional cohesion vì thực hiện một chức năng cụ thể liên quan đến xem giỏ hàng.
 * Communicational cohesion do có sự truyền đối tượng Cart làm tham số trong các phương thức.
 */
public class ViewCartController extends BaseController {

    /**
     * This method checks the available products in Cart
     *
     * @throws SQLException
     */
    /** control coupling
     Phương thức checkAvailabilityOfProduct trong ViewCartController
     kiểm soát luồng thực thi của phương thức checkAvailabilityOfProduct trong lớp Cart */
    /** data coupling
     Phương thức này chỉ gọi các phương thức của lớp Cart thông qua việc truyền dữ liệu */
    public void checkAvailabilityOfProduct() throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method calculates the cart subtotal
     *
     * @return subtotal
     */
    /** data coupling
     Phương thức getCartSubtotal chỉ gọi các phương thức của lớp Cart thông qua việc truyền dữ liệu */
    public int getCartSubtotal() {
        int subtotal = Cart.getCart().calSubtotal();
        return subtotal;
    }

}
