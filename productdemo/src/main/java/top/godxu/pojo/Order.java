package top.godxu.pojo;

public class Order {
    private Integer id;
    private Integer user_id;
    private Integer product_id;
    private Integer num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer id) {
        this.user_id = id;
    }

    public Integer getProductId() {
        return product_id;
    }

    public void setProductId(Integer id) {
        this.product_id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", user_id=" + user_id + ", product_id=" + product_id + ", num=" + num + "]";
    }
}
