package com.ctm.technician;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.ctm.technician.constants.CommonSharePrefrences;
import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends BaseActivity {
    Spinner spinner2;
    private CommonSharePrefrences pref;
    TextView skip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_language);
        pref = CommonSharePrefrences.getInstance(this);

        spinner2 = (Spinner) findViewById(R.id.language_button);
        skip = (TextView) findViewById(R.id.skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LanguageActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        List<String> list = new ArrayList<String>();
        list.add("Select language");
        list.add("English");
        list.add("हिंदी");
        //list.add("मराठी");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spinner2.getSelectedItem() == "Select language") {
                    return;
                } else {
                    String selectedItem = "" + spinner2.getSelectedItem();
                    if (selectedItem.equals("हिंदी")) {
                        selectedItem = "hi";
                    }
                  /*  if (selectedItem.equals("मराठी")) {
                        selectedItem = "mr";
                    }*/
                    if (selectedItem.equals("English")) {
                        selectedItem = "en";
                    }
                    pref.setlanguage(selectedItem);
                    restartApp();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void restartApp() {
        Intent i = new Intent(LanguageActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
