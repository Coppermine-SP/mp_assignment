package corp.cloudint.ihatejava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        List<Integer> imageList;

        public ImageAdapter(Set<Integer> images){
            imageList = new ArrayList<Integer>();
            imageList.addAll(images);
        }

        @NonNull
        @Override
        public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.simple_image_view, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
            int actualPosition = position % imageList.size();
            holder.image.setImageResource(imageList.get(actualPosition));
            holder.image.setTag(imageList.get(actualPosition));
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView image;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.posterImage);
                image.setOnClickListener(v -> OnClickPoster((ImageView)v));
            }
        }
    }


    HashMap<Integer, String> movieData;
    ArrayList<Integer> keyIndexTable;
    LinearLayoutManager layout;
    RecyclerView posterList;
    ImageView selectedPoster;
    Integer currentMovie = null;

    protected void OnClickPoster(ImageView v){
        Integer id = (Integer)v.getTag();
        selectedPoster.setImageResource(id);
        currentMovie = id;

        int idx = keyIndexTable.indexOf(id);
        int centerOfScreen = posterList.getWidth() / 2;
        layout.scrollToPositionWithOffset(idx, centerOfScreen);
    }

    private void configureMovieData(){
        movieData = new LinkedHashMap<Integer, String>();

        movieData.put(R.drawable.image_1, "어벤져스 엔드게임");
        movieData.put(R.drawable.image_2, "탑건 매버릭");
        movieData.put(R.drawable.image_3, "아이언맨3");
        movieData.put(R.drawable.image_4, "명량 해적판");
        movieData.put(R.drawable.image_5, "인터스텔라");
        movieData.put(R.drawable.image_6, "오팬하이머");
        movieData.put(R.drawable.image_7, "광해");
        movieData.put(R.drawable.image_8, "오블리비언");
        movieData.put(R.drawable.image_9, "파일럿");

        keyIndexTable = new ArrayList<>(movieData.keySet());
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

        layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        configureMovieData();
        posterList = findViewById(R.id.posterList);
        selectedPoster = findViewById(R.id.selectedPoster);

        posterList.setLayoutManager(layout);
        ImageAdapter adapter = new ImageAdapter(movieData.keySet());
        posterList.setAdapter(adapter);
        selectedPoster.setOnClickListener(v -> OnClickBigPoster());
    }

    private void OnClickBigPoster(){
        if(currentMovie != null){
            Toast.makeText(MainActivity.this, "영화 제목:" + movieData.get(currentMovie), Toast.LENGTH_SHORT).show();
        }
    }
}