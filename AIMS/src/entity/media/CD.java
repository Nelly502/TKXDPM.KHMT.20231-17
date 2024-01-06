package entity.media;

import entity.db.AIMSDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class CD extends Media {

    String artist;
    String recordLabel;
    String musicType;
    Date releasedDate;

    public CD() {
    }

    public CD(int id, String title, String category, int price, int quantity, String imageURL, String type,
              String artist, String recordLabel, String musicType, Date releasedDate) {
        super(id, title, category, price, quantity, imageURL, type);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }

    public String getArtist() {
        return this.artist;
    }

    public CD setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getRecordLabel() {
        return this.recordLabel;
    }

    public CD setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
        return this;
    }

    public String getMusicType() {
        return this.musicType;
    }

    public CD setMusicType(String musicType) {
        this.musicType = musicType;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public CD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + " artist='" + artist + "'" + ", recordLabel='" + recordLabel + "'"
                + "'" + ", musicType='" + musicType + "'" + ", releasedDate='"
                + releasedDate + "'" + "}";
    }

    @Override
    public CD from(ResultSet res) throws SQLException {
        super.from(res);
        artist = res.getString("artist");
        recordLabel = res.getString("recordLabel");
        musicType = res.getString("musicType");
        String releaseDateColumn = res.getString("releasedDate");
        releasedDate = releaseDateColumn != null ? Date.valueOf(releaseDateColumn) : null;
        return this;
    }

    @Override
    public CD getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM Media "
                + "LEFT JOIN CD ON Media.id = CD.id "
                + "WHERE type = 'cd' AND Media.id = ?;";
        PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet res = stm.executeQuery();
        if (res.next()) {
            CD cd = new CD().from(res);
            cd.setId(id);
            return cd;
        } else {
            return null;
        }
    }

    @Override
    public void create() throws SQLException {
        super.create();
        String sql = "INSERT INTO CD (id, artist, \"recordLabel\", \"musicType\", \"releasedDate\") "
                + "VALUES (?,?,?,?,?) "
                + ";";
        PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
        stm.setInt(1, id);
        stm.setString(2, artist);
        stm.setString(3, recordLabel);
        stm.setString(4, musicType);
        stm.setString(5, releasedDate.toString());
        stm.executeUpdate();
    }

    @Override
    public void save() throws SQLException {
        if (id == 0) {
            create();
        } else {
            super.save();
            String sql = "UPDATE CD SET "
                    + "artist = ?, \"recordLabel\" = ?, \"musicType\" = ?, \"releasedDate\" = ? "
                    + "WHERE id = ?;";
            PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
            stm.setString(1, artist);
            stm.setString(2, recordLabel);
            stm.setString(3, musicType);
            stm.setString(4, releasedDate.toString());
            stm.setInt(5, id);
            int updated = stm.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM CD WHERE id = ?;";
        PreparedStatement stm = AIMSDB.getConnection().prepareStatement(sql);
        stm.setInt(1, id);
        stm.executeUpdate();

        super.delete(id);
    }
}
