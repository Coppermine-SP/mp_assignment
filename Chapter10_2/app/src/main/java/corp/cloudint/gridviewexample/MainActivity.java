/*
    MainActivity - GridViewExample
    Copyright (C) 2024-2025 Coppermine-SP - <국립창원대학교 컴퓨터공학과 20233063 손유찬>
 */
package corp.cloudint.gridviewexample;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

class ImageGridAdapter extends BaseAdapter {
    Context context;
    List<Integer> imgList;
    public ImageGridAdapter(Context c, List<Integer> l){
        context = c;
        imgList = l;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Object getItem(int i) {
        return imgList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams((new ViewGroup.LayoutParams(200, 300)));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(5,5,5,5);
        imageView.setImageResource(imgList.get(i));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = View.inflate(context, R.layout.dialog, null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                ImageView image = dialogView.findViewById(R.id.image);

                image.setImageResource(imgList.get(pos));
                dialog.setTitle("큰 포스터");
                dialog.setIcon(R.drawable.ic_launcher_foreground);
                dialog.setView(dialogView);
                dialog.setNegativeButton("닫기", null);
                dialog.show();
            }
        });

        return imageView;

    }
}

public class MainActivity extends AppCompatActivity {
    Integer[] imgs = { R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8,  R.drawable.image9};
    GridView grid;
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

        grid = findViewById(R.id.imgGrid);
        grid.setAdapter(new ImageGridAdapter(this, Arrays.asList(imgs)));
    }
}