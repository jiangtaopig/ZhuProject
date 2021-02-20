package com.example.za_zhujiangtao.zhupro.room_data_base;

import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.room_data_base.meet.MeetNotice;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/20
 */
public class TestRoomActivity extends BaseActivity {

    @BindView(R.id.add)
    Button mAddUser;

    @BindView(R.id.query)
    Button mQueryUser;

    @BindView(R.id.query_all)
    Button mQueryAllByIds;

    @BindView(R.id.delete)
    Button mDeleteUser;

    @BindView(R.id.alert_table)
    Button mAddUserAfterAlertAble;

    @BindView(R.id.query_user_cnt)
    Button mQueryUserCount;

    @BindView(R.id.add_meet)
    Button mAddMeet;

    @BindView(R.id.query_meet)
    Button mQueryMeet;

    private AppDataBase mAppDataBase;

    private User mUser;

    @Override
    protected int layoutId() {
        return R.layout.activity_room_layout;
    }

    @Override
    protected void onInitLogic() {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readWriteLock.readLock().lock();
        readWriteLock.readLock().unlock();

        readWriteLock.writeLock().lock();
        readWriteLock.writeLock().unlock();
        //数据库从版本1升级为版本2，增加一个字段 sex， 且 AppDataBase 上面的 version 也要相应的改为2
        Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(SupportSQLiteDatabase database) {
                database.execSQL("ALTER TABLE users "
                        + " ADD COLUMN sex TEXT");
            }
        };
        // 插入一张新表 , 且 AppDataBase 上面的 version 也要相应的改为 3
        final Migration MIGRATION_2_3 = new Migration(2, 3) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                //创建表
                database.execSQL("DROP TABLE IF EXISTS meetnotice");
                database.execSQL("CREATE TABLE meetnotice (meetId TEXT NOT NULL, time INTEGER NOT NULL DEFAULT 0, PRIMARY KEY(meetId))");
            }
        };
        mAppDataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "zhujiangtao.db").
                addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .allowMainThreadQueries()
                .build();

        mUser = new User();
        mUser.firstName = "zhu";
        mUser.lastName = "jT";

        mAddUser.setOnClickListener(v -> {

            User user;
            User[] users = new User[5];
            for (int i = 0; i < 5; i++) {
                user = new User();
                user.uid = i;
                user.firstName = "zhu" + (i + 1);
                user.lastName = "jT" + (i + 1);
                users[i] = user;
            }

            Observable.just(users)
                    .map(users1 -> {
                        mAppDataBase.userDao().insertAll(users);
                        return true;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aBoolean -> {

                    });
        });

        mDeleteUser.setOnClickListener(v -> {
            Observable.just(mUser)
                    .map(users1 -> {
                        mAppDataBase.userDao().delete(mUser);
                        return true;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aBoolean -> {

                    });

        });

        mQueryUser.setOnClickListener(v -> {
            Observable.just(1)
                    .map(integer -> {
                        List<User> users = mAppDataBase.userDao().getAll();
                        return users;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(users -> {
                        if (users != null && users.size() > 0) {
                            for (User user : users) {
                                Log.e("xxx", "name = " + user.lastName + ", id = " + user.uid);
                            }

                        }
                    });

        });

        mQueryAllByIds.setOnClickListener(v -> {
            Observable.just(1)
                    .map(integer -> {
                        int[] arr = {0, 1, 3};
                        List<User> users = mAppDataBase.userDao().loadAllByIds(arr);
                        return users;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(users -> {
                        if (users != null && users.size() > 0) {
                            for (User user : users) {
                                Log.e("xxx", " query by ids name = " + user.lastName + ", id = " + user.uid);
                            }
                        }
                    });
        });

        mAddUserAfterAlertAble.setOnClickListener(v -> {
            Observable.just(1)
                    .map(integer -> {
                        User user = new User();
                        user.sex = "female";
                        user.uid = 10;
                        user.lastName = "hello";
                        user.firstName = "world";
                        mAppDataBase.userDao().insertAll(user);
                        return true;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aBoolean -> {

                    });

        });

        mQueryUserCount.setOnClickListener(v -> {
            int count = mAppDataBase.userDao().queryGroupCount();
            Log.e("xxx", "query all user number  = " + count);
        });

        mAddMeet.setOnClickListener(v -> {
            Observable.just(1)
                    .map(integer -> {
                        MeetNotice meetNotice = new MeetNotice("10010", 12_1000);
                        mAppDataBase.meetDao().insertNotice(meetNotice);
                        return true;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aBoolean -> {
                    });
        });

        mQueryMeet.setOnClickListener(v -> {
            Observable.just(1)
                    .map(integer -> {
                        return mAppDataBase.meetDao().findByMeetId("10010");
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(meetNotice -> {
                        Log.e("xxx", " query by meetid = " + meetNotice.meetId + ", time = " + meetNotice.time);
                    });
        });
    }


}
