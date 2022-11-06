package com.milk.model.vo;

import lombok.Data;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 17:43
 */
@Data
public class MetaVo {
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

}
