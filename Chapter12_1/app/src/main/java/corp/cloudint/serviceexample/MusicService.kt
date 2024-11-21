package corp.cloudint.serviceexample

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {
    lateinit var player: MediaPlayer;
    override fun onBind(intent: Intent): IBinder {
        TODO("NOT IMPLEMENTED")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this, R.raw.music);
        player.start();
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop();
    }

}