package controller;

import entity.shipping.Shipment;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * This class controls the flow of place rush order usecase in our AIMS project
 */

/**
 * Functional cohesion vì thực hiện một chức năng cụ thể là kiểm tra và xác nhận đơn hàng cần giao hàng nhanh
 * Communicational cohesion do có sự truyền đối tượng Shipment làm tham số trong phương thức
 * validatePlaceRushOrderData. Thành phần này cung cấp và sử dụng dữ liệu thông qua tham số.
 */
public class PlaceRushOrderController extends BaseController {
    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());

    /**data coupling
     Phương thức validatePlaceRushOrderData thực hiện kiểm tra dữ liệu trong đối tượng Shipment.
     Mức độ phụ thuộc chỉ dựa trên dữ liệu được truyền vào. */
    /**SOLID
     * Không vi phạm nguyên tắc nào
     */
    public static void validatePlaceRushOrderData(Shipment deliveryData) {
        if (deliveryData.getShipType() == utils.Configs.PLACE_RUSH_ORDER) {
           // validate
        }
    }
}
