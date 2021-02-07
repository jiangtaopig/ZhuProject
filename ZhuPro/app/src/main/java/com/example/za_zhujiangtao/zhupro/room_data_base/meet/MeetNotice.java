package com.example.za_zhujiangtao.zhupro.room_data_base.meet;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MeetNotice {
    @PrimaryKey
    @NonNull
    public String meetId;
    public long time;

    public MeetNotice(@NonNull String meetId, long time) {
        this.meetId = meetId;
        this.time = time;
    }
}
