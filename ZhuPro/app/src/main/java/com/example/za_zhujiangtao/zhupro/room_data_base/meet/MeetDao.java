package com.example.za_zhujiangtao.zhupro.room_data_base.meet;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MeetDao {
    @Query("SELECT COUNT(*) FROM meetnotice WHERE meetId = :meetId")
    int isNoticeExits(String meetId);

    @Query("DELETE FROM meetnotice where time < :time")
    void deleteNoticeBefore(long time);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotice(MeetNotice meetNotice);

    @Query("SELECT * FROM meetnotice WHERE meetId = :meetId")
    MeetNotice findByMeetId(String meetId);
}
