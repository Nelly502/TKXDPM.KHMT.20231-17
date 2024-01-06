package entity.media;

import entity.db.AIMSDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class DVD extends Media {

    String discType;
    String director;
    int runtime;
    String studio;
    String subtitle;
    Date releasedDate;
    String filmType;

    public DVD() {

    }

    public DVD(int id, String title, String category, int price, int quantity, String imageURL, String type,
               String discType, String director, int runtime, String studio, String subtitle, Date releasedDate, String filmType) {
        super(id, title, category, price, quantity, imageURL, type);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.subtitle = subtitle;
        this.releasedDate = releasedDate;
        this.filmType = filmType;
    }

    public String getDiscType() {
        return this.discType;
    }

    public DVD setDiscType(String discType) {
        this.discType = discType;
        return this;
    }

    public String getDirector() {
        return this.director;
    }

    public DVD setDirector(String director) {
        this.director = director;
        return this;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public DVD setRuntime(int runtime) {
        this.runtime = runtime;
        return this;
    }

    public String getStudio() {
        return this.studio;
    }

    public DVD setStudio(String studio) {
        this.studio = studio;
        return this;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public DVD setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public DVD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    public String getFilmType() {
        return this.filmType;
    }

    public DVD setFilmType(String filmType) {
        this.filmType = filmType;
        return this;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + " discType='" + discType + "'" + ", director='" + director + "'" + ", runtime='"
                + runtime + "'" + ", studio='" + studio + "'" + ", subtitle='" + subtitle + "'" + ", releasedDate='"
                + releasedDate + "'" + ", filmType='" + filmType + "'" + "}";
    }

    @Override
    public DVD from(ResultSet res) throws SQLException {
        super.from(res);
        discType = res.getString("discType");
        director = res.getString("director");
        runtime = res.getInt("runtime");
        studio = res.getString("studio");
        subtitle = res.getString("subtitle");
        String releaseDateColumn = res.getString("releasedDate");
        releasedDate = releaseDateColumn != null ? Date.valueOf(releaseDateColumn) : null;
        filmType = res.getString("filmType");
        return this;
    }

    @Override
    public DVD getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM Media "
                + "LEFT JOIN DVD ON Media.id = DVD.id "
                + "WHERE type = 'dvd' AND Media.id = ?;";
        PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet res = stm.executeQuery();
        if (res.next()) {
            DVD dvd = new DVD().from(res);
            dvd.setId(id);
            return dvd;
        } else {
            return null;
        }
    }

    @Override
    public void create() throws SQLException {
        super.create();
        String sql = "INSERT INTO DVD (id, \"discType\", director, runtime, studio, subtitle, \"releasedDate\", \"filmType\") "
                + "VALUES (?,?,?,?,?,?,?,?);";
        PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);

        stm.setInt(1, id);
        stm.setString(2, discType);
        stm.setString(3, director);
        stm.setInt(4, runtime);
        stm.setString(5, studio);
        stm.setString(6, subtitle);
        stm.setString(7, releasedDate.toString());
        stm.setString(8, filmType);
        stm.executeUpdate();
    }

    @Override
    public void save() throws SQLException {
        if (id == 0) {
            create();
        } else {
            super.save();
            String sql = "UPDATE DVD SET "
                    + "\"discType\" = ?, director = ?, runtime = ?, studio = ?, subtitle = ?, \"releasedDate\" = ?, \"filmType\" = ? "
                    + "WHERE id = ?;";
            PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
            stm.setString(1, discType);
            stm.setString(2, director);
            stm.setInt(3, runtime);
            stm.setString(4, studio);
            stm.setString(5, subtitle);
            stm.setString(6, releasedDate.toString());
            stm.setString(7, filmType);
            stm.setInt(8, id);
            int updated = stm.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM DVD WHERE id = ?;";
        PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
        stm.setInt(1, id);
        stm.executeUpdate();

        super.delete(id);
    }
}
