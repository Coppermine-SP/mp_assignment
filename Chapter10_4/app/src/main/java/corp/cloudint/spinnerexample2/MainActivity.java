package corp.cloudint.spinnerexample2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    final String[] cars = {"아이오닉 5N", "아반떼 N", "BWM M2", "BMW M4", "BMW M5"};
    Spinner spinner;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinner = findViewById(R.id.spinner);
        text = findViewById(R.id.text);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cars);
        spinner.setAdapter(adapter);
        findViewById(R.id.btn).setOnClickListener(v -> setText());
    }

    private void setText(){
        text.setText("선택 항목: " + spinner.getSelectedItem().toString());
    }
}