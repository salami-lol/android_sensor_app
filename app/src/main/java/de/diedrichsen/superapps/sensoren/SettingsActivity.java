package de.diedrichsen.superapps.sensoren;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // View-Elemente aus XML-Layout Datei erzeugen lassen
        setContentView(R.layout.activity_settings);
        SeekBar seekBar = findViewById(R.id.settingsSeekbar);
        final TextView sView = findViewById(R.id.settingsView);
        final CheckBox checkBox = findViewById(R.id.checkBox);
        final EditText gAcc = findViewById(R.id.settings_gAcc);

        // Initialisieren der App Bar und Aktivieren des Up-Buttons
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        final SharedPreferences sStorage = getApplicationContext().getSharedPreferences("data.pr", Context.MODE_PRIVATE);
        final SharedPreferences.Editor sEditor = sStorage.edit();

        sView.setText(getString(R.string.settingsSensiText) + sStorage.getFloat("sensi", 5F));
        seekBar.setProgress((int) (sStorage.getFloat("sensi", 5F) * 10));
        gAcc.setText(String.valueOf(sStorage.getFloat("gAcc", 9.81F)));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sEditor.putBoolean("slow_mode", b);
                sEditor.apply();
            }
        });


        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sView.setText(getString(R.string.settingsSensiText) + (float) progress / 10);
                sEditor.putFloat("sensi", (float) progress / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sEditor.apply();
            }
        });

        gAcc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (gAcc.getText() != null && gAcc.getText().length() > 0 && !Objects.equals(gAcc.getText().toString(), "-")) {
                    sEditor.putFloat("gAcc", Float.parseFloat(gAcc.getText().toString()));
                    sEditor.apply();
                }

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
