package entity.media;

import entity.db.AIMSDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Book extends Media {

    String author;
    String coverType;
    String publisher;
    Date publishDate;
    int numOfPages;
    String language;
    String bookCategory;

    public Book() {
    }

    public Book(int id, String title, String category, int price, int quantity, String imageURL, String type, String author,
                String coverType, String publisher, Date publishDate, int numOfPages, String language, String bookCategory) {
        super(id, title, category, price, quantity, imageURL, type);
        this.author = author;
        this.coverType = coverType;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.numOfPages = numOfPages;
        this.language = language;
        this.bookCategory = bookCategory;
    }

    // getter and setter
    public int getId() {
        return this.id;
    }

    public String getAuthor() {
        return this.author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getCoverType() {
        return this.coverType;
    }

    public Book setCoverType(String coverType) {
        this.coverType = coverType;
        return this;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public Book setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }


    public Date getPublishDate() {
        return this.publishDate;
    }

    public Book setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public int getNumOfPages() {
        return this.numOfPages;
    }

    public Book setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
        return this;
    }

    public String getLanguage() {
        return this.language;
    }

    public Book setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getBookCategory() {
        return this.bookCategory;
    }

    public Book setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
        return this;
    }

    @Override
    public Book from(ResultSet res) throws SQLException {
        super.from(res);

        author = res.getString("author");
        coverType = res.getString("coverType");
        publisher = res.getString("publisher");
        String publishDateColumn = res.getString("publishDate");
        publishDate = publishDateColumn != null ? Date.valueOf(publishDateColumn) : null;
        numOfPages = res.getInt("numOfPages");
        language = res.getString("language");
        bookCategory = res.getString("bookCategory");
        return this;
    }

    @Override
    public Book getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM Media "
                + "LEFT JOIN Book ON Media.id = Book.id "
                + "WHERE type = 'book' AND Media.id = ?;";
        PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet res = stm.executeQuery();
        if (res.next()) {
            Book book = new Book().from(res);
            book.setId(id);
            return book;
        }
        return null;
    }

    @Override
    public List<Media> getAllMedia() throws SQLException {
        List<Media> books = new ArrayList<Media>();

        String sql = "SELECT *, Media.id AS media_id FROM Media "
                + "LEFT JOIN Book ON Media.id = Book.id "
                + "WHERE type = 'book'"
                + "ORDER BY Media.id DESC;";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            Book book = new Book().from(res);
            book.setId(res.getInt("media_id"));
            books.add(book);
        }
        return books;
    }

    @Override
    public void create() throws SQLException {
        super.create();
        String sql = "INSERT INTO Book (id, author, coverType, publisher, \"publishDate\", \"numOfPages\", language, \"bookCategory\") "
                + "VALUES (?,?,?,?,?,?,?,?) "
                + ";";
        PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
        stm.setInt(1, id);
        stm.setString(2, author);
        stm.setString(3, coverType);
        stm.setString(4, publisher);
        stm.setString(5, publishDate.toString());
        stm.setInt(6, numOfPages);
        stm.setString(7, language);
        stm.setString(8, bookCategory);
        stm.executeUpdate();
    }

    @Override
    public void save() throws SQLException {
        if (id == 0) {
            create();
        } else {
            super.save();
            String sql = "UPDATE Book SET "
                    + "author = ?, \"coverType\" = ?, publisher = ?, \"publishDate\" = ?, \"numOfPages\" = ?, language = ?, \"bookCategory\" = ? "
                    + "WHERE id = ?;";
            PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
            stm.setString(1, author);
            stm.setString(2, coverType);
            stm.setString(3, publisher);
            stm.setString(4, publishDate.toString());
            stm.setInt(5, numOfPages);
            stm.setString(6, language);
            stm.setString(7, bookCategory);
            stm.setInt(8, id);
            int updated = stm.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Book WHERE id = ?;";
        PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
        stm.setInt(1, id);
        stm.executeUpdate();

        super.delete(id);
    }

    @Override
    public String toString() {
        return "{" +
                super.toString() +
                " author='" + author + "'" +
                ", coverType='" + coverType + "'" +
                ", publisher='" + publisher + "'" +
                ", publishDate='" + publishDate + "'" +
                ", numOfPages='" + numOfPages + "'" +
                ", language='" + language + "'" +
                ", bookCategory='" + bookCategory + "'" +
                "}";
    }
}
