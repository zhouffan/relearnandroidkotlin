package org.fw.relearn_android_kotlin._android._08

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_music_video.*
import org.fw.relearn_android_kotlin.R

class MusicVideoActivity : AppCompatActivity() {
    private val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_video)
        initMediaPlayer()
        btn.setOnClickListener {
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
            }
        }
        btn2.setOnClickListener {
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
            }
        }

        //视频相关
        val uri = Uri.parse("android.resource://$packageName/${R.raw.video}")
        videoview.setVideoURI(uri)
        btn3.setOnClickListener {
            if(!videoview.isPlaying){
                videoview.start()
            }
        }
        btn4.setOnClickListener {
            if(videoview.isPlaying){
                videoview.pause()
            }
        }
        btn5.setOnClickListener {
            if(videoview.isPlaying){
                videoview.resume()
            }
        }
    }

    /**
     * music
     */
    private fun initMediaPlayer(){
        val fd = assets.openFd("music.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()

        videoview.suspend()
    }
}