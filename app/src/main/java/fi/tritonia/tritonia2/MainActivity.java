package fi.tritonia.tritonia2;


import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setupHyperlink();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        // Check if username and password are already saved
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("USERNAME", null);
        String savedPassword = sharedPreferences.getString("PASSWORD", null);
        String lastUsername = sharedPreferences.getString("LAST_USERNAME", null);
        String lastPassword = sharedPreferences.getString("LAST_PASSWORD", null);


        if (savedUsername != null && savedPassword != null) {
            // Navigate directly to LoggedInActivity
            Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
            intent.putExtra("USERNAME", savedUsername);
            intent.putExtra("PASSWORD", savedPassword);
            startActivity(intent);
            finish(); // Close MainActivity
        } else if (lastUsername != null && lastPassword != null) {
            // Update hints with the last saved username and password
            //username.setHint(lastUsername);
            //password.setHint(lastPassword);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();


                if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    // Save username and password in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("USERNAME", usernameText);
                    editor.putString("PASSWORD", passwordText);
                    editor.apply();

                    Intent intent=new Intent(MainActivity.this, LoggedInActivity.class);
                    intent.putExtra("USERNAME", usernameText);
                    intent.putExtra("PASSWORD", passwordText);

                    startActivity(intent);
                    finish(); // Close MainActivity

                } else {
                    Toast.makeText(MainActivity.this, R.string.unsuccessful, Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupHyperlink() {
        TextView linkTextView = findViewById(R.id.signupText);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        linkTextView.setLinkTextColor(Color.parseColor("#015A7E"));
    }
}