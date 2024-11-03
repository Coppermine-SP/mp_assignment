/*
    MainActivity - MyAwesomeApplication1031_1
    Copyright (C) 2024-2025 Coppermine-SP - <국립창원대학교 컴퓨터공학과 20233063 손유찬>
 */
package corp.cloudint.myawesomeapplication1031_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Class<?> target;
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

        ((RadioGroup)findViewById(R.id.btn_group)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.btn_1) target = Activity2.class;
                else if(i == R.id.btn_2) target = Activity3.class;
                else if(i == R.id.btn_3) target = Activity4.class;
            }
        });

        findViewById(R.id.go_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(target == null){
                    Toast.makeText(getApplicationContext(), "이동할 엑티비티를 선택하십시오!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), target);
                startActivity(intent);
            }
        });
    }
}