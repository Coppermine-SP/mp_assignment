package corp.cloudint.myawesomeapplication1031_2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    final Integer[] imageBtnList = {R.id.img_1, R.id.img_2, R.id.img_3, R.id.img_4, R.id.img_5, R.id.img_6, R.id.img_7, R.id.img_8, R.id.img_9};
    final String[] imageNameList = {"아이오닉 5N", "다람쥐", "독수리", "꼬양이", "부엉이", "푸바오", "BMW M2", "따봉맨", "마이바흐"};
    Integer[] imageRatingList = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int index;

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

        for(int i = 0; i < 9; i++){
            findViewById(imageBtnList[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    int index = 0;
                    for(int i = 0; i < 9; i++){
                        if(imageBtnList[i] == id){
                            index = i;
                            break;
                        }
                    }

                    Toast.makeText(getApplicationContext(), imageNameList[index] + " 작품에 투표되었습니다.", Toast.LENGTH_SHORT).show();
                    imageRatingList[index]++;
                }
            });
        }

        findViewById(R.id.end_vote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}