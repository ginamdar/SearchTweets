package com.example.guru_i.validator;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Created by guru_i on 2016-04-21.
 */
public abstract class TextValidator implements TextWatcher {
    private final TextView textView;
    protected TextValidator(TextView textView){
        this.textView = textView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        validate(textView, textView.getText().toString());
    }

    //
    public abstract void validate(TextView textView, String text);

}
