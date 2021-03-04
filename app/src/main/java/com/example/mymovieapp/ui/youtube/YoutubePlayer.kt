package com.example.mymovieapp.ui.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mymovieapp.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_youtube_player.*

class YoutubePlayer : YouTubeBaseActivity() {

    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_player)
        val videoId = intent.getStringExtra("video")

        initializePlayer(videoId!!)


    }

    private fun initializePlayer(videoId: String) {

        youtubePlayer.initialize(getString(R.string.api_key),
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, youtubePlayer: YouTubePlayer?, p2: Boolean) {
                    youtubePlayer!!.loadVideo(videoId)
                    youtubePlayer.play()
                }

                override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                Toast.makeText(applicationContext, "Something went wrong !! ", Toast.LENGTH_SHORT).show()
                }
            })
    }

}