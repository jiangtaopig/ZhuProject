package com.example.za_zhujiangtao.zhupro.room_data_base;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/20
 */
@Entity(tableName = "users")
public class User {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    public String sex;
}
