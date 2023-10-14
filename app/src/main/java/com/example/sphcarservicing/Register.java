package com.example.sphcarservicing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;


public class Register extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        SharedPreferences preferences_provider = PreferenceManager.getDefaultSharedPreferences(this);

        EditText name = findViewById(R.id.editTextTextPersonName2);
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText address = findViewById(R.id.editTextAddress);
        EditText phone = findViewById(R.id.editTextPhone);
        EditText password = findViewById(R.id.editPwd);

        CheckBox chbx = findViewById(R.id.checkBox1);

        Button login = findViewById(R.id.buttonLoginAsk);
        Button regist = findViewById(R.id.buttonRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            boolean isInserted;

            @Override
            public void onClick(View view) {

//                System.out.println(email.getText());
//                System.out.println(name.getText());
//                System.out.println(address.getText());
//                System.out.println(phone.getText());
//                System.out.println(password.getText());

                if (chbx.isChecked()) {
                    String emailValue = email.getText().toString();
                    String phoneValue = phone.getText().toString();

                    // Validate email
                    if (!isValidEmail(emailValue)) {
                        Toast.makeText(Register.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Validate phone number
                    if (!isValidPhoneNumber(phoneValue)) {
                        Toast.makeText(Register.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    isInserted = databaseHelper.addData(email.getText().toString(),
                            name.getText().toString(), address.getText().toString(), phone.getText().toString(),
                            password.getText().toString(), "1");
                    if (isInserted) {
                        Toast.makeText(Register.this, "Welcome service Provider!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, company_details.class));
                        SharedPreferences.Editor editor = preferences_provider.edit();
                        editor.putString("EMAIL", email.getText().toString());
                        editor.commit();
                    } else {
                        Toast.makeText(Register.this, "Sorry not registered, something is missing", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    isInserted = databaseHelper.addData(email.getText().toString(),
                            name.getText().toString(), address.getText().toString(), phone.getText().toString(),
                            password.getText().toString(), "0");
                    if (isInserted) {
                        Toast.makeText(Register.this, "Welcome new User,You are registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                    } else {
                        Toast.makeText(Register.this, "Sorry not registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private boolean isValidPhoneNumber(CharSequence phone) {
        // You can customize the phone number pattern to match your requirements
        String phoneNumberPattern = "^[+]?[0-9]{10,13}$";
        return phone.toString().matches(phoneNumberPattern);
    }
}