package com.example.za_zhujiangtao.zhupro.room_data_base;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.za_zhujiangtao.zhupro.room_data_base.meet.MeetDao;
import com.example.za_zhujiangtao.zhupro.room_data_base.meet.MeetNotice;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/2/7
 */
@Database(entities = {User.class, MeetNotice.class}, version = 3)
abstract class AppDataBase extends RoomDatabase {
    abstract UserDao userDao();
    abstract MeetDao meetDao();
}
