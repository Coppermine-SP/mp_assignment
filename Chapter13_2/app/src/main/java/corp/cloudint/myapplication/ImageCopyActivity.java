package corp.cloudint.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageCopyActivity extends AppCompatActivity {
    final String FOLDER_NAME = "MyAppImages";
    private final String[] FILES_NAME = {"img_1.jpg", "img_2.jpg", "img_3.jpg", "img_4.jpg", "img_5.jpg"};
    private final Integer[] FILES_ID = {R.raw.img_1, R.raw.img_2, R.raw.img_3, R.raw.img_4, R.raw.img_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_copy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lstView = findViewById(R.id.imgList);

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }

        initInternalStorage();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(FILES_NAME));
        lstView.setAdapter(adapter);

        lstView.setOnItemClickListener((p, v, pos, id) -> {
            copyToInternalStorage(pos);
        });
    }

    private void initInternalStorage() {
        File folder = new File(getExternalFilesDir(null), FOLDER_NAME);
        if(!folder.exists()) {
            if (folder.mkdirs()) Toast.makeText(this, "폴더를 생성하였습니다.", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "폴더 생성 실패.", Toast.LENGTH_SHORT).show();
        }
    }

    private void copyToInternalStorage(int fileIdx){
        File target = new File(getExternalFilesDir(null) + "/" + FOLDER_NAME, FILES_NAME[fileIdx]);

        if(target.exists()){
            Toast.makeText(this, "이미 파일이 존재합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        try(InputStream in = getResources().openRawResource(FILES_ID[fileIdx])){
            try(FileOutputStream out = new FileOutputStream(target)){
                byte[] buf = new byte[1024];
                int len;

                while((len = in.read(buf)) > 0) out.write(buf, 0, len);
            }

        }
        catch (IOException e){
            Toast.makeText(this, "파일 복사 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, "복사 왼료.", Toast.LENGTH_SHORT).show();
    }
}