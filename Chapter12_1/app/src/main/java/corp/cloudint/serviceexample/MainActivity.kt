package corp.cloudint.serviceexample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var isMusicStarted: Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.startBtn).setOnClickListener { v -> musicStart()}
        findViewById<Button>(R.id.endBtn).setOnClickListener { v -> musicEnd()}
    }

    private fun musicStart(){
        if(!isMusicStarted) {
            isMusicStarted = true;
            Toast.makeText(this, "음악 재생 시작", Toast.LENGTH_LONG).show();
            startService(Intent(this, MusicService::class.java));
        }
    }

    private fun musicEnd(){
        if(isMusicStarted){
            isMusicStarted = false;
        Toast.makeText(this, "음악 재생 종료", Toast.LENGTH_LONG).show();
        stopService(Intent(this, MusicService::class.java));
        }
    }
}