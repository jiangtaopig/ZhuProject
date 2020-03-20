package com.example.za_zhujiangtao.zhupro.token_auto;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/20
 */
public class TokenAutoActivity extends BaseActivity implements TokenCompleteTextView.TokenListener<Person> {

    private ContactsCompletionView completionView;
    private Person[] people;
    private ArrayAdapter<Person> adapter;

    private RecyclerView mRecyclerView;
    private CustomAdapter mCustomAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_token_complete_layout;
    }

    @Override
    protected void onInitLogic() {
        people = Person.samplePeople();
//        adapter = new PersonAdapter(this, R.layout.person_layout, people);

        completionView = (ContactsCompletionView) findViewById(R.id.searchView);
//        completionView.setAdapter(adapter);
        completionView.setThreshold(1);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);
        completionView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("TokenAutoActivity", "afterTextChanged val = " + editable.toString());
//                ((TextView) findViewById(R.id.textValue)).setText(editable.toString());
            }
        });

        Button removeButton = (Button) findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Person> people = completionView.getObjects();
                if (people.size() > 0) {
                    completionView.removeObjectAsync(people.get(people.size() - 1));
                }
            }
        });

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                completionView.addObjectAsync(people[rand.nextInt(people.length)]);
            }
        });

        mRecyclerView = findViewById(R.id.recycle_view);
        mCustomAdapter = new CustomAdapter();
        mRecyclerView.setAdapter(mCustomAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> datas = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14");
        mCustomAdapter.setList(datas);
    }

    private void updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("Current tokens:\n");
        for (Object token : completionView.getObjects()) {
            sb.append(token.toString());
            sb.append("\n");
        }

//        Log.e("TokenAutoActivity", "updateTokenConfirmation val = "+sb.toString());

//        ((TextView) findViewById(R.id.tokens)).setText(sb);
    }

    @Override
    public void onTokenAdded(Person token) {
//        ((TextView) findViewById(R.id.lastEvent)).setText("Added: " + token);
        updateTokenConfirmation();
    }

    @Override
    public void onTokenRemoved(Person token) {
//        ((TextView) findViewById(R.id.lastEvent)).setText("Removed: " + token);
        updateTokenConfirmation();
    }

    @Override
    public void onTokenIgnored(Person token) {
//        ((TextView) findViewById(R.id.lastEvent)).setText("Ignored: " + token);
        updateTokenConfirmation();
    }
}
