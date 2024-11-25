package corp.cloudint.texteditorexample;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class EditorActivity extends AppCompatActivity {
    private EditText editText;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fileName = this.getIntent().getStringExtra("FILE");
        editText = findViewById(R.id.textEdit);
        ((TextView)findViewById(R.id.fileNameLabel)).setText(fileName);
        findViewById(R.id.newBtn).setOnClickListener(v -> resetText());
        findViewById(R.id.saveBtn).setOnClickListener(v -> saveFile());

        try(FileInputStream fs = openFileInput(fileName)){
            StringBuilder builder = new StringBuilder();

            String text = new BufferedReader(
                    new InputStreamReader(fs, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            editText.setText(text);
        }
        catch (IOException e){
            Toast.makeText(this, "파일 열기 실패", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void saveFile(){
        try(FileOutputStream fs = openFileOutput(fileName, MODE_PRIVATE)){
            fs.write(editText.getText().toString().getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e){
            Toast.makeText(this, "파일 저장 실패", Toast.LENGTH_LONG).show();
        }

        finish();
    }

    private void resetText(){
        editText.setText("");
    }
}