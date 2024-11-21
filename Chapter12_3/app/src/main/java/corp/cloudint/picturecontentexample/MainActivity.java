package corp.cloudint.picturecontentexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Bitmap> pictures;
    ImageView imgView;
    int currentIdx;

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

        imgView = findViewById(R.id.mainImg);
        findViewById(R.id.prevBtn).setOnClickListener(v -> prev());
        findViewById(R.id.nextBtn).setOnClickListener(v -> next());

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
        else readPictures();
    }

    private void prev(){
        if(pictures == null || currentIdx == 0) return;
        currentIdx--;
        setPicture();
    }

    private void next(){
        if(pictures == null || currentIdx >= pictures.size() - 1) return;
        currentIdx++;
        setPicture();
    }

    private void setPicture(){
        imgView.setImageBitmap(pictures.get(currentIdx));
    }

    private void readPictures(){
        String[] proj = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA
        };

        try(Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null)){
            int size = cursor.getCount();
            if(size == 0){
                Toast.makeText(this, "장치에 이미지가 없습니다.", Toast.LENGTH_LONG).show();
                return;
            }

            pictures = new ArrayList<>();
            while(cursor.moveToNext()){
                String loc = cursor.getString(1);
                File file = new File(loc);
                if(file.exists()){
                    pictures.add(BitmapFactory.decodeFile(loc));
                }
            }

            currentIdx = 0;
            setPicture();
        }


    }
}