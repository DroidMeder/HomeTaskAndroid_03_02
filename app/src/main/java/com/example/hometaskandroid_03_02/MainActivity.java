package com.example.hometaskandroid_03_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonSend;
    private EditText editTextEmailTo, editTextTitle, editTextMessage;
    private boolean isEditTextEmailTo=false, isEditTextTitle=false, isEditTextMessage=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        onClick();

    }

    private void onClick() {
        buttonSend.setOnClickListener(v -> {
            changeListener(editTextEmailTo);
            changeListener(editTextTitle);
            changeListener(editTextMessage);

            if (isEditTextEmailTo) {
                if (isEditTextTitle) {
                    if (isEditTextMessage) {
                        composeEmail(new String[] {editTextEmailTo.getText().toString()},
                                editTextTitle.getText().toString(), editTextMessage.getText().toString());
                    } else {
                        editTextMessage.setError("Нельзя отправлять пустое сообщение!!!");
                    }
                } else {
                    editTextTitle.setError("Сообщение без заголовка!!!");
                }
            }else {
                editTextEmailTo.setError("Неправильный email адрес!!!");
            }


        });
    }

    private void init() {
        editTextEmailTo = findViewById(R.id.editTextTextEmailAddress);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextMessage = findViewById(R.id.editTextSimpleText);
        buttonSend = findViewById(R.id.buttonSend);
    }

    public void composeEmail(String[] addresses, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeListener(EditText editText){
        if (editText == editTextEmailTo){
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    isEditTextEmailTo = !s.toString().isEmpty() && s.toString().contains("@");
                }
            });
        }else if (editText == editTextTitle){
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    isEditTextTitle = !s.toString().isEmpty();
                }
            });
        }
        else {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    isEditTextMessage = !s.toString().isEmpty();
                }
            });
        }
    }

}