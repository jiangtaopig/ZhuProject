package com.example.za_zhujiangtao.zhupro.room_data_base;

import android.util.Log;
import android.widget.Button;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

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

    private UserDataBase mUserDataBase;

    private User mUser;

    @Override
    protected int layoutId() {
        return R.layout.activity_room_layout;
    }

    @Override
    protected void onInitLogic() {
        //数据库从版本1升级为版本2，增加一个字段 sex
         Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(SupportSQLiteDatabase database) {
                database.execSQL("ALTER TABLE users "
                        + " ADD COLUMN sex TEXT");
            }
        };
        mUserDataBase = Room.databaseBuilder(getApplicationContext(), UserDataBase.class, "contact").
                addMigrations(MIGRATION_1_2).build();

        mUser = new User();
        mUser.firstName = "zhu";
        mUser.lastName = "jT";

        mAddUser.setOnClickListener(v -> {

            User user;
            User [] users = new User[5];
            for (int i = 0; i < 5; i++){
                user  = new User();
                user.uid = i;
                user.firstName = "zhu"+(i+1);
                user.lastName = "jT"+(i+1);
                users[i] = user;
            }


            Observable.just(users)
                    .map(users1 -> {
                        mUserDataBase.userDao().insertAll(users);
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
                mUserDataBase.userDao().delete(mUser);
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
                       List<User> users = mUserDataBase.userDao().getAll();
                       return users;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(users -> {
                        if (users != null && users.size() > 0){
                            for (User user : users){
                                Log.e("xxx", "name = "+user.lastName + ", id = "+user.uid);
                            }

                        }
                    });

        });

        mQueryAllByIds.setOnClickListener(v -> {
            Observable.just(1)
                    .map(integer -> {
                        int [] arr = {0, 1, 3};
                        List<User> users = mUserDataBase.userDao().loadAllByIds(arr);
                        return users;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(users -> {
                        if (users != null && users.size() > 0){
                            for (User user : users){
                                Log.e("xxx", " query by ids name = "+user.lastName + ", id = "+user.uid);
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
                       mUserDataBase.userDao().insertAll(user);
                       return true;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aBoolean -> {

                    });
        });
    }
}
