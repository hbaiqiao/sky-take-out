package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 动态条件查询
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 根据id修改商品数量
     * @param shoppingCart
     */
    @Update("update shopping_cart set number = #{number} where id =#{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * 插入购物车数据
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time)" +
    "values (#{name},#{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount}, #{createTime}) ")
    void insert(ShoppingCart shoppingCart);

    /**
     * 根据用户id删除购物车数据
     * @param userId
     */
    @Delete("delete from shopping_cart where user_id = #{userId};")
    void deleteByUserId(Long userId);

    /**
     * 批量插入购物车数据
     * @param shoppingCartList
     */
    void insertBatch(List<ShoppingCart> shoppingCartList);

    /**
     * 根据菜品id和用户id查找购物车物品
     * @param dishId
     * @param userId
     * @return
     */
    @Select("select * from shopping_cart where dish_id = #{dishId} and user_id = #{userId}")
    ShoppingCart getByDishIdAndUserId(Long dishId, Long userId);

    /**
     * 根据套餐id和用户id查找购物车物品
     * @param setmealId
     * @param userId
     * @return
     */
    @Select("select * from shopping_cart where setmeal_id = #{setmealId} and user_id = #{userId}")
    ShoppingCart getBySetMealIdAndUserId(Long setmealId, Long userId);

    /**
     * 删除购物车菜品
     * @param id
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);
}
