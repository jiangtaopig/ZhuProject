package com.example.za_zhujiangtao.zhupro.token_auto;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by mgod on 5/27/15.
 *
 * Simple custom view example to show how to get selected events from the token
 * view. See ContactsCompletionView and contact_token.xml for usage
 */
public class TokenTextView extends AppCompatTextView {

    public TokenTextView(Context context) {
        super(context);
    }

    public TokenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
//        setCompoundDrawablesWithIntrinsicBounds(0, 0, selected ? R.drawable.close_x : 0, 0);
        if (selected){
            setTextColor(Color.parseColor("#ffffffff"));
        }else {
            setTextColor(Color.parseColor("#ff0073e6"));
        }
    }
}
