package top.godxu.pojo;

public class Position {
    private Integer id;
    private String position_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    @Override
    public String toString() {
        return "Position [id=" + id + ", position_name=" + position_name + "]";
    }
}
