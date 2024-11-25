package corp.cloudint.texteditorexample;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity {
    private ListView fileList;
    private TextView noFileLabel;
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

        fileList = findViewById(R.id.fileList);
        noFileLabel = findViewById(R.id.noFileLabel);
        findViewById(R.id.newBtn).setOnClickListener(v -> {
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("파일 이름을 입력하세요.");

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("파일 생성")
                    .setMessage("새 파일을 생성하시겠습니까?")
                    .setView(input)
                    .setPositiveButton("추가", (d, i) -> editFile(input.getText().toString()))
                    .setNegativeButton("취소", null)
                    .show();
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        getFiles();
    }

    private void getFiles(){
        String[] files = fileList();

        if(files.length == 0){
            noFileLabel.setVisibility(View.VISIBLE);
            return;
        }

        noFileLabel.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, files);
        fileList.setAdapter(adapter);
        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("파일 동작")
                        .setMessage("수행할 동작을 선택하세요.")
                        .setNeutralButton("취소", null)
                        .setPositiveButton("삭제", (d, i) -> removeFile(files[pos]))
                        .setNegativeButton("수정", (d, i) -> editFile(files[pos]))
                        .show();
            }
        });
    }

    private void removeFile(String name){
        Toast.makeText(this, name + " 파일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
        deleteFile(name);

        getFiles();
    }

    private void editFile(String name){
        Intent intent = new Intent(this, EditorActivity.class);
        intent.putExtra("FILE", name);
        startActivity(intent);
    }

}