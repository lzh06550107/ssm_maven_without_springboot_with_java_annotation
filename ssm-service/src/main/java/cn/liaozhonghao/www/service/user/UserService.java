package cn.liaozhonghao.www.service.user;

import cn.liaozhonghao.www.pojo.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUser();

    public User getUserById(Integer userId);

    public int insertUser(User user);

    public int updateUser(User user);

    public int deleteUser(Integer userId);
}
