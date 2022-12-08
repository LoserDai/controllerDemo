package com.com.df.utils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Author feng.dai
 * @Date 2022/11/28 15:30
 * @Project_Name controllerDemo
 * @Package_Name com.com.df.utils
 */
public class AboutList {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("qwe");
        list.add("  rty");
        //迭代器
        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}
