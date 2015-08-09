package com.thorrism.parse.skeleton.validators;

import com.thorrism.designtools.views.ShakeEditText;

import validators.Validator;

/**
 * Created by Lucas Crawford on 8/3/2015.
 */
public class EmailValidator implements Validator {
    private ShakeEditText mEditText;

    public EmailValidator(ShakeEditText editText){
        mEditText = editText;
    }

    @Override
    public boolean validate(){
        return mEditText.getText().toString().contains("@");
    }

}
