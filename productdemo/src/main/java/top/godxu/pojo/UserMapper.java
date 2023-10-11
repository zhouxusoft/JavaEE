package top.godxu.pojo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserById(@Param("userId") int id);
    
}
