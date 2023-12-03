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

    //-CONTENT COUPLING:
    // Class PaymentController có sử dụng các ngoại lệ như PaymentException, TransactionNotDoneException, và UnrecognizedException. 
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
//  private String getExpirationDate(String date) throws TransactionNotDoneException {
//    String[] strs = date.split("/");
//    if (strs.length != 2) {
//      throw new TransactionNotDoneException();
//    }
//
//    String expirationDate = null;
//    int month = -1;
//    int year = -1;
//
//    try {
//      month = Integer.parseInt(strs[0]);
//      year = Integer.parseInt(strs[1]);
//      if (month < 1 || month > 12 || year < Calendar.getInstance().get(Calendar.YEAR) % 100 || year > 100) {
//        throw new TransactionNotDoneException();
//      }
//      expirationDate = strs[0] + strs[1];
//
//    } catch (Exception ex) {
//      throw new TransactionNotDoneException();
//    }
//
//    return expirationDate;
//  }
//
//  /**
//   * Pay order, and then return the result with a message.
//   *
//   * @param amount         - the amount to pay
//   * @param contents       - the transaction contents
//
//   * @return {@link java.util.Map Map} represent the payment result with a
//   *         message.
//   */

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