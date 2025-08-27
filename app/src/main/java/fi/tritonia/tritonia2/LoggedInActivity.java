package fi.tritonia.tritonia2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.journeyapps.barcodescanner.BarcodeEncoder;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String password = intent.getStringExtra("PASSWORD");

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(username, com.google.zxing.BarcodeFormat.CODE_39, 600, 300);

            ImageView barcodeImageView = findViewById(R.id.barcodeImageView);
            barcodeImageView.setImageBitmap(bitmap);

            TextView usernameTextView = findViewById(R.id.usernameTextView);
            usernameTextView.setText(username);

            TextView passwordTextView = findViewById(R.id.passwordTextView);
            passwordTextView.setText(password);

            Button showPasswordButton = findViewById(R.id.showPasswordButton);
            showPasswordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (passwordTextView.getVisibility() == View.VISIBLE) {
                        passwordTextView.setVisibility(View.GONE);
                        showPasswordButton.setText(R.string.show_pin);
                    } else {
                        passwordTextView.setVisibility(View.VISIBLE);
                        showPasswordButton.setText(R.string.hide_pin);
                    }
                }
            });

            Button logoutButton = findViewById(R.id.logoutButton);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Save the username and password in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //editor.putString("LAST_USERNAME", username);
                    //editor.putString("LAST_PASSWORD", password);
                    //editor.apply();

                    // Clear the saved username and password
                    editor.remove("USERNAME");
                    editor.remove("PASSWORD");
                    editor.apply();

                    // Navigate back to MainActivity
                    Intent intent = new Intent(LoggedInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close LoggedInActivity
                }
            });

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}

