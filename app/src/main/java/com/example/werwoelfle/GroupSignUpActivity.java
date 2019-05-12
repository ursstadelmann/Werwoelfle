package com.example.werwoelfle;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

import java.util.ArrayList;

public class GroupSignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = GroupSignUpActivity.class.getName();
    private GameStateConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_group_signup);

        conn = new GameStateConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                super.onServiceConnected(name, service);
                createNameBoxes(conn.getApi().getPlayers().size());
            }
        };

        bindService(new Intent(this, GameState.class), conn, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "GroupSignUpActivity: onResume()");
        bindService(new Intent(this, GameState.class), conn, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(conn);
    }

    private void createNameBoxes(int players) {
        ScrollView scrollView = findViewById(R.id.nameBoxes);
        if (scrollView.getChildCount() > 0) {
            return;
        }
        LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        for (int player = 1; player <= players; player++) {
            // Create Textbox
            EditText textBox = new EditText(this);
            textBox.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            textBox.setSingleLine(true);
            textBox.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
            textBox.setHint("Spieler " + player);

            layout.addView(textBox);
        }
        scrollView.addView(layout);
    }

    private ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();

        ScrollView scrollView = findViewById(R.id.nameBoxes);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof EditText) {
                EditText et = (EditText) linearLayout.getChildAt(i);
                names.add(et.getText().toString());
            }
        }

        return names;
    }

    public void next(View v) {
        // Call RoleSelection Activity
        conn.getApi().setNames(getNames());
        Intent roleSelectionActivity = new Intent(this, RoleSelectionActivity.class);
        startActivity(roleSelectionActivity);
    }

    public void process(View v) {
        Log.d(LOG_TAG, "MainActivity: process()");
        Intent processActivityIntent = new Intent(this, processAblaufButtonActivity.class);
        startActivity(processActivityIntent);
    }

    public void figures(View v) {
        Log.d(LOG_TAG, "MainActivity: figures()");
        Intent figuresActivityIntent = new Intent(this, figuresActivity.class);
        startActivity(figuresActivityIntent);
    }






}
