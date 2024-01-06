package controller;

import common.exception.PaymentException;
import common.exception.TransactionNotDoneException;
import common.exception.UnrecognizedException;
import entity.cart.Cart;
import subsystem.VnPayInterface;
import subsystem.VnPaySubsystem;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Map;

/**
 * This {@code PaymentController} class control the flow of the payment process
 * in our AIMS Software.
 *
 * @author
 */
//FIUNCTION COHESION
//-CONTENT COUPLING:
// Class PaymentController có sử dụng các ngoại lệ như PaymentException, TransactionNotDoneException,
//và UnrecognizedException.
// Điều này là một dạng của content coupling vì nó yêu cầu PaymentController biết và phụ thuộc
// vào các chi tiết cụ thể về ngoại lệ từ gói common.exception.

//-CONTROL COUPING:
//Có một phương thức makePayment trong PaymentController mà sử dụng một interface VnPayInterface
//và "makePaymentTransaction" từ vnPayService.
// Điều này tạo ra sự phụ thuộc kiểm soát giữa PaymentController và VnPaySubsystem

//-DATA COUPLING:
//Phương thức makePayment sử dụng một Map<String, String> để truyền dữ liệu giữa các thành phần.
//Điều này là một mức độ data coupling, nhưng không phải là mức độ cao vì nó chỉ sử dụng một đối
//tượng dữ liệu chung.

/**
 * Vi phạm Single Responsibility Principle (SRP):
 * Lớp PaymentController đang thực hiện nhiều nhiệm vụ,
 * bao gồm việc xử lý thanh toán, tạo URL thanh toán, và xóa giỏ hàng.
 */

/**
 * Vi phạm  Dependency Inversion Principle (DIP):
 * Lớp PaymentController tạo mới một đối tượng VnPaySubsystem bên trong phương thức,
 * điều này làm cho lớp trở nên cứng nhắc và khó tái sử dụng.
 */
public class PaymentController extends BaseController {


    /**
     * Represent the Interbank subsystem
     */
    private VnPayInterface vnPayService;

    /**
     * Validate the input date which should be in the format "mm/yy", and then
     * return a {@link java.lang.String String} representing the date in the
     * required format "mmyy" .
     *
     * @param date - the {@link java.lang.String String} represents the input date
     * @return {@link java.lang.String String} - date representation of the required
     * format
     * @throws TransactionNotDoneException - if the string does not represent a valid date
     *                                     in the expected format
     */


    public Map<String, String> makePayment(Map<String, String> res, int orderId) {
        Map<String, String> result = new Hashtable<String, String>();

        try {
            this.vnPayService = new VnPaySubsystem();
            var trans = vnPayService.makePaymentTransaction(res);
            trans.save(orderId);
            result.put("RESULT", "PAYMENT SUCCESSFUL!");
            result.put("MESSAGE", "You have succesffully paid the order!");
        } catch (PaymentException | UnrecognizedException | SQLException ex) {
            result.put("MESSAGE", ex.getMessage());
            result.put("RESULT", "PAYMENT FAILED!");

        } catch (ParseException ex) {
            result.put("MESSAGE", ex.getMessage());
            result.put("RESULT", "PAYMENT FAILED!");
        }
        return result;
    }

    /**
     * Gen url thanh toán vnPay
     * @param amount
     * @param content
     * @return
     */
    public String getUrlPay(int amount, String content){
        vnPayService = new VnPaySubsystem();
        var url = vnPayService.generatePayUrl(amount, content);
        return url;
    }

    public void emptyCart() {
        Cart.getCart().emptyCart();
    }
}