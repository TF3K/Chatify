package com.glsi.chatify;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private SQLiteHelper sqliteHelper;  // SQLiteHelper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        sqliteHelper = new SQLiteHelper(this);  // Initialize SQLiteHelper

        TextView forgotTextView = findViewById(R.id.forgotTextView);
        TextView signUpTextView = findViewById(R.id.signUpTextView);

// Make the bold part of the text clickable for "Forgot your password?"
        String forgotText = getString(R.string.forgot);
        SpannableString forgotSpannable = new SpannableString(forgotText);

// Find the exact start and end indices of the bold part
        String boldText = "Press here.";  // The bold part of the string
        int startIndex = forgotText.indexOf(boldText);
        if (startIndex != -1) {  // Check if the bold text is found
            int endIndex = startIndex + boldText.length();

            // Apply a clickable span to the bold section
            forgotSpannable.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // Navigate to ForgotPasswordActivity
                    Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                    startActivity(intent);
                }
            }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Set the spannable text and make it clickable
            forgotTextView.setText(forgotSpannable);
            forgotTextView.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            // Handle the case where "Press here." was not found (optional)
            Log.e("SignUpActivity", "Bold text 'Press here.' not found in string.");
        }

// Make the bold part of the text clickable for "Sign up"
        String signUpText = getString(R.string.sign_up);
        SpannableString signUpSpannable = new SpannableString(signUpText);

// Find the exact start and end indices of the bold part
        boldText = "Sign up.";  // The bold part of the string
        startIndex = signUpText.indexOf(boldText);
        if (startIndex != -1) {  // Check if the bold text is found
            int endIndex = startIndex + boldText.length();

            // Apply a clickable span to the bold section
            signUpSpannable.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // Navigate to SignUpActivity
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Set the spannable text and make it clickable
            signUpTextView.setText(signUpSpannable);
            signUpTextView.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            // Handle the case where "Sign up." was not found (optional)
            Log.e("SignUpActivity", "Bold text 'Sign up.' not found in string.");
        }

        // Handle login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate input
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform login (Authenticate using SQLite database)
                boolean isValidUser = sqliteHelper.checkUserCredentials(email, password);
                if (isValidUser) {
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                    // Navigate to the main activity
                    Intent intent = new Intent(LoginActivity.this, ContactsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
