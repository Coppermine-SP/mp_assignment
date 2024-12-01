package corp.cloudint.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ImageViewActivity extends AppCompatActivity {
    private final String FOLDER_NAME = "MyAppImages";
    private List<String> fileNames;
    private List<File> fileObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_show);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lstView = findViewById(R.id.imgList);
        initInternalStorage();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNames);
        lstView.setAdapter(adapter);
        lstView.setOnItemClickListener((p, v, pos, id) -> {
            ImageView view = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeFile(fileObjects.get(pos).getAbsolutePath());
            view.setImageBitmap(bitmap);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(fileNames.get(pos))
                    .setPositiveButton("닫기", null)
                    .setView(view)
                    .show();
        });
    }

    private void initInternalStorage() {
        File folder = new File(getExternalFilesDir(null), FOLDER_NAME);
        if(!folder.exists()) {
            Toast.makeText(this, "폴더가 없습니다. 먼저 이미지를 복사하십시오.", Toast.LENGTH_SHORT).show();
            finish();
        }

        fileNames = new ArrayList<>();
        fileObjects = new ArrayList<>();

        if(folder.listFiles() != null) {
            for (File x : Objects.requireNonNull(folder.listFiles())) {
                fileNames.add(x.getName());
                fileObjects.add(x);
            }
        }
    }
}