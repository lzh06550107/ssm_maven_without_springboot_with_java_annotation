package cn.liaozhonghao.www.dao.user;

import cn.liaozhonghao.www.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Delete("delete from t_user where u_id = #{uId}")
    int deleteByPrimaryKey(Integer uId);

    @Insert("insert into t_user (u_name, u_password) values (#{uName}, #{uPassword})")
    @Options(useGeneratedKeys=true,keyProperty="u_id")
    int insert(User record);

    @Select("select u_id, u_name, u_password from t_user where u_id = #{uId}")
    User selectByPrimaryKey(Integer uId);

    //@ResultMap("BaseResultMap")
    @Select("select u_id, u_name, u_password from t_user")
    @Results({
            @Result(id=true,column="u_id",property="uId"),
            @Result(column="u_name",property="uName"),
            @Result(column="u_password",property="uPassword"),
    })
    List<User> selectAll();

    @Update("update t_user set u_name = #{uName},u_password = #{uPassword} where u_id = #{uId}")
    int updateByPrimaryKey(User record);
}