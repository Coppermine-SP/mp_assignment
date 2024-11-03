/*
    ResultActivity - MyAwesomeApplication1031_2
    Copyright (C) 2024-2025 Coppermine-SP - <국립창원대학교 컴퓨터공학과 20233063 손유찬>
 */
package corp.cloudint.myawesomeapplication1031_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {
    final int[] imageList = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9};

    int[] imageRatingList;
    String[] imageNameList;

    TableLayout resultTable;
    TextView topRatedLabel;
    ImageView topRatedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        imageRatingList = intent.getIntArrayExtra("RatingList");
        imageNameList = intent.getStringArrayExtra("ImageName");

        resultTable = (TableLayout)findViewById(R.id.result_table);
        topRatedLabel = (TextView)findViewById(R.id.top_rated_name);
        topRatedImage = (ImageView)findViewById(R.id.top_rated_image);

        //테이블 요소 동적 주입
        int idx = 0;
        int topRatedIdx = 0;
        int topRating = 0;
        for(String name : imageNameList){
            TableRow row = new TableRow(getApplicationContext());
            TextView label = new TextView(getApplicationContext(), null, 0, R.style.result_name_style);
            RatingBar rating = new RatingBar(getApplicationContext(), null, 0, androidx.appcompat.R.style.Widget_AppCompat_RatingBar_Indicator);

            int rate = imageRatingList[idx++];
            if(rate > topRating){
                topRatedIdx = idx-1;
                topRating = rate;
            }

            label.setText(name);
            rating.setRating(rate);
            row.addView(label);
            row.addView(rating);
            resultTable.addView(row);
        }

        topRatedLabel.setText(imageNameList[topRatedIdx]);
        topRatedImage.setImageResource(imageList[topRatedIdx]);

        findViewById(R.id.return_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}