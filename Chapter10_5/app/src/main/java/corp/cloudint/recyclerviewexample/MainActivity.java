package corp.cloudint.recyclerviewexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder>{
    private List<String> list;

    public StringAdapter(List<String> dataSource){
        list = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_text_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.label.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView label;

        public ViewHolder(View v){
            super(v);
            label = v.findViewById(R.id.text_label);

            label.setOnClickListener(x -> onLabelClick());
        }

        private void onLabelClick(){
            int pos = getAdapterPosition();
            Toast.makeText(this.itemView.getContext(), "Selected: " + list.get(pos), Toast.LENGTH_SHORT).show();
        }
    }
}

public class MainActivity extends AppCompatActivity {
    RecyclerView container;
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

        container = findViewById(R.id.recycler_view);
        container.setLayoutManager(new LinearLayoutManager(this));
        List<String> data = Arrays.asList("아이오닉 5N", "아반떼 N", "BMW M2 CS", "BMW M5 CS", "BMW M760Li");

        StringAdapter adapter = new StringAdapter(data);
        container.setAdapter(adapter);
    }
}