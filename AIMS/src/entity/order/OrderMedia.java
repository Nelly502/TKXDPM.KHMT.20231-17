package entity.order;

import entity.media.Media;

public class OrderMedia {

    private Media media;
    private int price;
    private int quantity;
    //Media, quantity, price đang được dùng bên ngoài lớp, nên thêm các hành động cụ thể cho chúng
    public OrderMedia(Media media, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }


    /**
     * @return String
     */
    //Cần hiển thị rõ ràng hơn về đối tượng OrderMedia
    @Override
    public String toString() {
        return "{" +
                "  media='" + media + "'" +
                ", quantity='" + quantity + "'" +
                ", price='" + price + "'" +
                "}";
    }


    /**
     * @return Media
     */
    public Media getMedia() {
        return this.media;
    }


    /**
     * @param media
     */
    public void setMedia(Media media) {
        this.media = media;
    }


    /**
     * @return int
     */
    public int getQuantity() {
        return this.quantity;
    }


    /**
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     * @return int
     */
    public int getPrice() {
        return this.price;
    }


    /**
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

}
