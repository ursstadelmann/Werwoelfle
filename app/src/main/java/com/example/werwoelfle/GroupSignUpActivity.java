package com.example.werwoelfle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

import java.util.ArrayList;

public class GroupSignUpActivity extends AppCompatActivity {

    private int players;

    private static int LINEAR_LAYOUT_ID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_group_signup);

        Bundle extras = getIntent().getExtras();
        this.players = extras.getInt("players");

        createNameBoxes(this.players);
    }

    private void createNameBoxes(int players) {
        ScrollView scrollView = findViewById(R.id.nameBoxes);
        LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setId(LINEAR_LAYOUT_ID);

        for(int player = 1; player <= players; player++) {
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


    public void next(View v) {
        // Call RoleSelection Activity

    }

    public boolean startService(int players, ArrayList<String> names, ArrayList<Roles> roles) {
        if (names.size() == roles.size()) {
            Intent gameService = new Intent(this, GameState.class);
            gameService.putExtra("players", players);
            gameService.putExtra("names", names);
            gameService.putExtra("roles", roles);
            startService(gameService);
            return true;
        }

        return false;
    }

    private ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();

        LinearLayout linearLayout = findViewById(LINEAR_LAYOUT_ID);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof EditText) {
                EditText et = (EditText)linearLayout.getChildAt(i);
                names.add(et.getText().toString());
            }
        }

        return names;
    }
}
