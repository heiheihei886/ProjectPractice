package com.example.demo.dao;

import com.example.demo.ApiResult;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



/*
实现两个功能
	1、根据用户id查询用户信息
	2、查询同一年龄下的所有用户

返回一组数据我们用 List<E> 来存储，传递多个参数我们用 Map 来存储
例如 public List<User> selectById4(@Param("map") Map map);
*/
@Repository
public interface UserDao {
    //使用@Param
    public int getUserid(@Param("username") String username);

    public User getUserById(@Param("id") int id);

    public User getUserByName(@Param("username") String username);

    public List<User> getUserList(@Param("department") String department);

    public List<User> getUserList2(@Param("department") String department);

    public List<User> getUserList3(@Param("department") String department);

//    public ApiResult processLogin(@Param("username") String username,
//                                  @Param("password") String password);

    public void processRegister(@Param("username") String username,
                                @Param("password") String password,
                                @Param("name")String name,
                                @Param("sex") String sex,
                                @Param("age") int age);

    public void deleteUser(@Param("name")String name);
}

/*传递多个参数我们可以用 Map 来实现
public List<User> queryUser4(Integer age, Integer start, Integer move){
    Map<String,Object> hashMap=new HashMap<>();
    hashMap.put("age",age);
    hashMap.put("start",start);
    hashMap.put("move",move);
    return userDao.selectById4(hashMap);
}
*/
