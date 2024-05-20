package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;


    /**
     * 添加购物车业务方法
     * @param shoppingCartDTO
     */
   public void addShoppingCart(ShoppingCartDTO shoppingCartDTO){
        //判断当前加入购物车的商品是否存在
       ShoppingCart shoppingCart = new ShoppingCart();
       BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);

       Long userId = BaseContext.getCurrentId();
       shoppingCart.setUserId(userId);
       List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

       //如果存在 只需要将数量加一
        if(list != null &&list.size()>0){
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber()+1); //update shopping_cart
            shoppingCartMapper.updateNumberById(cart);
        }else{
            //如果不存在 需要插入一条购物车数据
            //判断本次添加到购物车的是菜品还是套餐
            Long dishId = shoppingCartDTO.getDishId();
            if(dishId != null){
                //本次添加的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());

            }else{
                //本次添加是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());

            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);

        }


   }
    /**
     * 删除购物车方法
     * @param shoppingCartDTO
     */
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO){
        ShoppingCart shoppingCart = new ShoppingCart();
        Long dishId = shoppingCartDTO.getDishId();
        Long userId = BaseContext.getCurrentId();
        if(dishId != null){
            //删除添加的是菜品
            //Dish dish = dishMapper.getById(dishId);
            shoppingCart = shoppingCartMapper.getByDishIdAndUserId(dishId,userId);

        }else{
            //本次删除是套餐
            Long setmealId = shoppingCartDTO.getSetmealId();
            //Setmeal setmeal = setmealMapper.getById(setmealId);
            shoppingCart = shoppingCartMapper.getBySetMealIdAndUserId(setmealId,userId);

        }
        //如果数量大于1 数量减一
        if(shoppingCart.getNumber()>1){
            shoppingCart.setNumber(shoppingCart.getNumber()-1);
            shoppingCartMapper.updateNumberById(shoppingCart);
        }else{
            //如果数量为一 删除购物车商品
            shoppingCartMapper.deleteById(shoppingCart.getId());

        }

    }

    /**
     * 查看购物车
     * @return
     */
    public  List<ShoppingCart> showShoppingCart(){
        //获取当前微信用户id
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        return list;
    }

    /**
     * 清空购物车
     */
    public void cleanShoppingCart(){
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }
}
