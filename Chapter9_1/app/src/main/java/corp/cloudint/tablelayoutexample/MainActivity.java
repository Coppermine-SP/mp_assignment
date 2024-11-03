/*
    MainActivity - TableLayoutExample
    Copyright (C) 2024-2025 Coppermine-SP - <국립창원대학교 컴퓨터공학과 20233063 손유찬>
 */

package corp.cloudint.tablelayoutexample;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.function.BiFunction;

public class MainActivity extends AppCompatActivity {
    TextView resultText;
    EditText lhsText;
    EditText rhsText;

    EditText currentFocusedText = null;
    final HashMap<Integer, Character> buttonTable = new HashMap<Integer, Character>(){{
        put(R.id.num_0_btn, '0');
        put(R.id.num_1_btn, '1');
        put(R.id.num_2_btn, '2');
        put(R.id.num_3_btn, '3');
        put(R.id.num_4_btn, '4');
        put(R.id.num_5_btn, '5');
        put(R.id.num_6_btn, '6');
        put(R.id.num_7_btn, '7');
        put(R.id.num_8_btn, '8');
        put(R.id.num_9_btn, '9');
    }};

    class OperandTextClickListener implements View.OnFocusChangeListener{
        @Override
        public void onFocusChange(View view, boolean b){
            if(b) currentFocusedText = (EditText) view;
        }
    }

    class NumButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            if(currentFocusedText == null){
                Toast.makeText(getApplicationContext(), "먼저 에디트 텍스트를 선택하십시오.", Toast.LENGTH_SHORT).show();
            }
            else {
                //Java에는 왜 null 병합 연산자가 존재하지 않습니까? Java는 나를 매우 화나게 만듭니다.
                currentFocusedText.getText().append(buttonTable.get(view.getId()));
            }
        }
    }

    class OperatorButtonClickListener implements View.OnClickListener{
        BiFunction<Integer, Integer, Integer> func;
        public OperatorButtonClickListener(BiFunction<Integer, Integer, Integer> func){
            this.func = func;
        }

        @Override
        public void onClick(View view) {
            int lhs, rhs;
            try {
                lhs = Integer.parseInt(lhsText.getText().toString());
                rhs = Integer.parseInt(rhsText.getText().toString());
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), "피연산자가 올바르지 않습니다!", Toast.LENGTH_SHORT).show();
                return;
            }
            int result = func.apply(lhs, rhs);

            resultText.setText("계산 결과: " + (result));
        }
    }

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

        resultText = (TextView)findViewById(R.id.result_label);
        lhsText = (EditText)findViewById(R.id.lhs_text);
        rhsText = (EditText)findViewById(R.id.rhs_text);
        findViewById(R.id.lhs_text).setOnFocusChangeListener(new OperandTextClickListener());
        findViewById(R.id.rhs_text).setOnFocusChangeListener(new OperandTextClickListener());
        for(Integer i : buttonTable.keySet()) findViewById(i).setOnClickListener(new NumButtonClickListener());

        findViewById(R.id.add_btn).setOnClickListener(new OperatorButtonClickListener(Integer::sum));
        findViewById(R.id.sub_btn).setOnClickListener(new OperatorButtonClickListener((x, y) -> x-y));
        findViewById(R.id.mul_btn).setOnClickListener(new OperatorButtonClickListener((x,y) -> x*y));
        findViewById(R.id.div_btn).setOnClickListener(new OperatorButtonClickListener((x,y) -> x/y));
    }
}