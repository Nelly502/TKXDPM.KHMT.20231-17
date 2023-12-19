package controller;

import entity.media.Media;

import java.sql.SQLException;
import java.util.List;

/**
 * This class controls the flow of events in homescreen
 *
 * @author nguyenlm
 */

/**
 * Vi phạm Single Responsibility Principle (SRP):
 * Lớp HomeController mở rộng từ BaseController và thực hiện một chức năng mới
 * liên quan đến việc lấy tất cả các Media từ cơ sở dữ liệu.
 */

public class HomeController extends BaseController {

    /**
     * this method gets all Media in DB and return back to home to display
     *
     * @return List[Media]
     * @throws SQLException
     */
    //Content Coupling
    //Functional Cohesion: This method has functional cohesion as it performs a specific function
    public List getAllMedia() throws SQLException {
        return new Media().getAllMedia();
    }

}
