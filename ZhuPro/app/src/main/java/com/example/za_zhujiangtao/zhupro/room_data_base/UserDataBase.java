package com.example.za_zhujiangtao.zhupro.room_data_base;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/20
 */
@Database(entities = {User.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao userDao();
}
