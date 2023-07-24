package com.yefeng.netdisk.myPageUtil.annotation;

/**
 * This class is for
 *
 * @author 夜枫
 * @version 2023-03-26 16:04
 */
public interface PageHandler<V> {
     V solve(Object data);
}