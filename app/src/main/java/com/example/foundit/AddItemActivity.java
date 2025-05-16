package com.example.foundit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText itemNameInput, itemDescriptionInput;
    private Button saveItemBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemNameInput = findViewById(R.id.itemNameInput);
        itemDescriptionInput = findViewById(R.id.itemDescriptionInput);
        saveItemBtn = findViewById(R.id.saveItemBtn);

        saveItemBtn.setOnClickListener(v -> {
            String name = itemNameInput.getText().toString().trim();
            String description = itemDescriptionInput.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Item saved (placeholder)", Toast.LENGTH_SHORT).show();
                finish(); // Go back to main screen
            }
        });
    }
}
