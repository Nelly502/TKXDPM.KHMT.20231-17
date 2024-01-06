package entity.media;

import entity.db.AIMSDB;
import utils.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * The general media class, for another media it can be done by inheriting this class
 */
public class Media {

    protected static boolean isSupportedPlaceRushOrder = new Random().nextBoolean();
    private static Logger LOGGER = Utils.getLogger(Media.class.getName());
    protected Statement stm;
    protected int id;
    protected String title;
    protected String category;
    protected int value; // the real price of product (eg: 450)
    protected int price; // the price which will be displayed on browser (eg: 500)
    protected int quantity;
    protected String type;
    protected String imageURL;

    public Media() {
        try {
            stm = AIMSDB.getConnection().createStatement();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Media(int id, String title, String category, int price, int quantity, String imageURL, String type) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
        this.type = type;

        this.value = 0;

        //stm = AIMSDB.getConnection().createStatement();
    }

    public static boolean getIsSupportedPlaceRushOrder() {
        return Media.isSupportedPlaceRushOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Media from(ResultSet res) throws SQLException {
        title = res.getString("title");
        category = res.getString("category");
        price = res.getInt("price");
        quantity = res.getInt("quantity");
        imageURL = res.getString("imageUrl");
        type = res.getString("type");
        return this;
    }

    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM Media "
                + "WHERE id = " + id + ";";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {
            return new Media().from(res).setId(id);
        }
        return null;
    }

    public List<Media> getAllMedia() throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery("select * from Media ORDER BY id DESC;");
        ArrayList listMedia = new ArrayList<>();
        while (res.next()) {
            Media media = new Media().from(res).setId(res.getInt("id"));
            listMedia.add(media);
        }
        return listMedia;
    }

    public void updateMediaFieldById(String tbname, int id, String field, String value) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        value = "\"" + value + "\"";
        stm.executeUpdate(" update " + tbname + " set" + " "
                + field + "=" + value + " "
                + "where id=" + id + ";");
    }

    public void create() throws SQLException {
        String sql = "INSERT INTO Media (title, value, price, quantity, category, \"imageUrl\", type) "
                + "VALUES (?,?,?,?,?,?,?) "
                + ";";
        PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql, new String[]{"id"});
        stm.setString(1, title);
        stm.setInt(2, value);
        stm.setInt(3, price);
        stm.setInt(4, quantity);
        stm.setString(5, category);
        stm.setString(6, imageURL);
        stm.setString(7, type);
        if (stm.executeUpdate() == 0) {
            throw new SQLException("Creating failed, no rows affected.");
        }
        ResultSet res = stm.getGeneratedKeys();
        if (res.next()) {
            id = res.getInt(1);
        }
    }

    public void save() throws SQLException {
        if (id == 0) {
            create();
        } else {
            String sql = "UPDATE Media SET "
                    + "title = ?, value = ?, price = ?, quantity = ?, category = ?, \"imageUrl\" = ?, type = ? "
                    + "WHERE id = ?;";
            PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
            stm.setString(1, title);
            stm.setInt(2, value);
            stm.setInt(3, price);
            stm.setInt(4, quantity);
            stm.setString(5, category);
            stm.setString(6, imageURL);
            stm.setString(7, type);
            stm.setInt(8, id);
            stm.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Media WHERE id = ?;";
        PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
        stm.setInt(1, id);
        stm.executeUpdate();
    }

    // getter and setter
    public int getId() {
        return this.id;
    }

    public Media setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public Media setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public Media setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public Media setMediaURL(String url) {
        this.imageURL = url;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Media setType(String type) {
        this.type = type;
        return this;
    }

    public int getValue() {
        return this.value;
    }

    public Media setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", title='" + title + "'" +
                ", category='" + category + "'" +
                ", price='" + price + "'" +
                ", quantity='" + quantity + "'" +
                ", type='" + type + "'" +
                ", imageURL='" + imageURL + "'" +
                "}";
    }
}