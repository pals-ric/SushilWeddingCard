package com.example.sushilwedinginvitation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.library.BuildConfig
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_youtube_player.*

class YoutubePlayerFragment : Fragment() {

    private lateinit var playerToControlListener: PlayerToControlListener
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var youTubePlayer: YouTubePlayer
    private lateinit var videoUrl: String

    var totalDuration: Float = 0f
    var currentDuration: Float = 0f
    var playing: Boolean = false
    var buffering: Boolean = false
    var paused: Boolean = false
    var isEnd: Boolean = false
    var isStarted: Boolean = false
    var isPreparing: Boolean = false
    var currentPlayState = ""
    var continueWatchingDuration: Float = 0f

    private fun isPlaying() = playing
    private fun isPaused() = paused
    private fun isBuffering() = buffering
    private fun videoEnd() = isEnd
    private fun seekTo(second: Float) {
        if (this::youTubePlayer.isInitialized) {
            youTubePlayer.seekTo(second)
        }
    }

    fun pause() {
        if (this::youTubePlayer.isInitialized) {
            youTubePlayer.pause()
        }
    }

    fun play() {
        if (this::youTubePlayer.isInitialized) {
            youTubePlayer.play()
        }
    }

    companion object {
        const val REWIND_FORWARD_TIME: Float = 10f
    }

    override fun onCreateView(inflater: LayoutInflater, vg: ViewGroup?, saved: Bundle?): View {

            videoUrl = "NNqLB7znMLU"

        return inflater.inflate(R.layout.fragment_youtube_player, vg, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(youtube_player_view)
        youTubePlayerView = view.findViewById(R.id.youtube_player_view)

        youTubePlayerView.setOnClickListener(null)
        youTubePlayerView.setOnKeyListener(null)
//        youTubePlayerView.setOnTouchListener(null)
//        youTubePlayerView.getPlayerUiController().showMenuButton(false)
//        youTubePlayerView.getPlayerUiController().showVideoTitle(false)
        setViews()
    }

    private fun setViews() {
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                //UNKNOWN, INVALID_PARAMETER_IN_REQUEST, HTML_5_PLAYER, VIDEO_NOT_FOUND, VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER

                when (error) {
                    PlayerConstants.PlayerError.UNKNOWN -> {
                        if (BuildConfig.DEBUG) {
                            //     Toast.makeText(activity, "UNKNOWN", Toast.LENGTH_SHORT).show()
                        }
                    }
                    PlayerConstants.PlayerError.INVALID_PARAMETER_IN_REQUEST -> {
                        if (BuildConfig.DEBUG) {
                            Toast.makeText(
                                activity,
                                "INVALID_PARAMETER_IN_REQUEST",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    PlayerConstants.PlayerError.HTML_5_PLAYER -> {
                        if (BuildConfig.DEBUG) {
                            Toast.makeText(activity, "HTML_5_PLAYER", Toast.LENGTH_SHORT).show()
                        }
                    }
                    PlayerConstants.PlayerError.VIDEO_NOT_FOUND -> {
                        if (BuildConfig.DEBUG) {
                            Toast.makeText(activity, "VIDEO_NOT_FOUND", Toast.LENGTH_SHORT).show()
                        }

                    }
                    PlayerConstants.PlayerError.VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER -> {
                        if (BuildConfig.DEBUG) {
                            Toast.makeText(
                                activity,
                                "VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }

            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@YoutubePlayerFragment.youTubePlayer = youTubePlayer
                youTubePlayer.loadOrCueVideo(lifecycle, videoUrl, continueWatchingDuration)
                currentPlayState = "ready"
                isPreparing = true
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                super.onCurrentSecond(youTubePlayer, second)
                currentDuration = second
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                super.onVideoDuration(youTubePlayer, duration)
                totalDuration = duration
                if (isPreparing) {
                    isPreparing = false
                }
            }

            override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerState) {
                super.onStateChange(youTubePlayer, state)
                when (state) {
                    PlayerState.PLAYING -> {
                        isEnd = false
                        isStarted = false
                        paused = false
                        buffering = false
                        playing = true
                    }
                    PlayerState.UNSTARTED, PlayerState.VIDEO_CUED -> {
                        isEnd = false
                        isStarted = false
                        paused = false
                        buffering = false
                        playing = false

                    }
                    PlayerState.BUFFERING -> {
                        paused = false
                        buffering = true
                        playing = false

                    }
                    PlayerState.PAUSED -> {
                        isEnd = false
                        paused = true
                        buffering = false
                        playing = false


                    }
                    PlayerState.ENDED -> {
                        isEnd = true
                        paused = false
                        buffering = false
                        playing = false

                    }
                    else -> {

                    }
                }
            }
        })
    }


    fun playPause() {
        if (isPlaying()) {
            youTubePlayer.pause()
            playerToControlListener.isPlaying(false)


        } else if (isPaused()) {
            youTubePlayer.play()
            playerToControlListener.isPlaying(true)

        }
    }

    fun seekForward10Second() {
        if (currentDuration + REWIND_FORWARD_TIME < totalDuration) {
            seekTo(currentDuration + REWIND_FORWARD_TIME)

        } else {
            seekTo(totalDuration)
        }

        play()
        playerToControlListener.isForwarded(true)
        playerToControlListener.isPlaying(true)
    }

    fun seekRewind10Second() {
        if (currentDuration - REWIND_FORWARD_TIME > 0f) {
            seekTo(currentDuration - REWIND_FORWARD_TIME)

        } else {
            seekTo(0f)
        }
        play()
        playerToControlListener.isRewinded(true)
        playerToControlListener.isPlaying(true)
    }

    fun seekForwardByPercentage(seconds: Int) {
        if (seconds < totalDuration) {
            seekTo(seconds.toFloat())

        } else {
            seekTo(totalDuration)
        }

        play()
        playerToControlListener.isForwarded(true)
        playerToControlListener.isPlaying(true)
    }

    fun seekRewindByPercentage(seconds: Int) {
        if (seconds > 0f) {
            seekTo(seconds.toFloat())

        } else {
            seekTo(0f)
        }
        play()
        playerToControlListener.isRewinded(true)
        playerToControlListener.isPlaying(true)
    }

    fun replayVideo() {
        seekTo(0f)
        play()
        isPreparing = true
    }

    override fun onStop() {
        super.onStop()
    }

    fun setPlayerToControllerListener(callback: PlayerToControlListener) {
        this.playerToControlListener = callback
    }

}