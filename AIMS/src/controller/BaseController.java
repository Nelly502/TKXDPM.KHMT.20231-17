package controller;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;

import java.util.List;


/**
 * This class is the base controller for our AIMS project.
 *
 * @author nguyenlm
 */

/**
 * Vi phạm Single Responsibility Principle SRP
 * Lớp BaseController thực hiện nhiều nhiệm vụ, bao gồm việc kiểm tra và
 * trả về thông tin về Media trong giỏ hàng cũng như lấy danh sách các mục trong giỏ hàng.
 **/

public class BaseController {

    /**
     * The method checks whether the Media in Cart, if it were in, we will return
     * the CartMedia else return null.
     *
     * @param media media object
     * @return CartMedia or null
     */
    //Content Coupling
    //Communicational Cohesion: Phương thức liên quan đến kiểm tra và trả về thông tin về Media trong giỏ hàng
    public CartMedia checkMediaInCart(Media media) {
        return Cart.getCart().checkMediaInCart(media);
    }

    /**
     * This method gets the list of items in cart.
     *
     * @return List[CartMedia]
     */
    //Content Coupling
    //Communicational Cohesion: Phương thức liên quan đến lấy danh sách các mục trong giỏ hàng

    /**
     * Vi phạm Interface Segregation Principle (ISP)
     * Phương thức getListCartMedia trả về một danh sách chưa rõ ràng về loại dữ liệu.
     * */
    public List getListCartMedia() {
        return Cart.getCart().getListMedia();
    }
}
