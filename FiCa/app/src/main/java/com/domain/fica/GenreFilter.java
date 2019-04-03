package com.domain.fica;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class GenreFilter extends Activity {
    private Intent returnFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.genrefilterlayout);
        DisplayMetrics Genredisplay = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(Genredisplay);

        int width = Genredisplay.widthPixels;
        int height = Genredisplay.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        Button cancel = (Button) findViewById(R.id.Filter_Genre_Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button submit = (Button) findViewById(R.id.Filter_Genre_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Convert();
            }
        });
    }

    private void Convert() {
        EditText textGenre = (EditText) findViewById(R.id.GenreText);
        String[] stringToStringArray = textGenre.getText().toString().split(",");
        ArrayList<String> stringArrayToArray = new ArrayList<String>() ;
        for (int i =0; i<stringToStringArray.length; i++){
            stringToStringArray[i] = "Genre:"+ stringToStringArray[i];
            String stringlisttosingelstring = stringToStringArray[i];
            stringArrayToArray.add(stringlisttosingelstring);
        }
        returnFilter = new Intent();
        returnFilter.putStringArrayListExtra("Genres", stringArrayToArray);
        setResult(RESULT_OK, returnFilter);
        finish();
    }
}
