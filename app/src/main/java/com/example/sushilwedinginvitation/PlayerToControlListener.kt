package com.example.sushilwedinginvitation

interface PlayerToControlListener {
    fun isPlaying(isPlaying: Boolean) // if isPlaying is true -> Video is in play state else video is in pause state

    fun isForwarded(isForwarded: Boolean)

    fun isRewinded(isRewinded: Boolean)

    fun onVideoReady(isReady: Boolean)

    fun onVideoEnd(isEnd: Boolean)

    fun getPlayerState(state: String)

}
