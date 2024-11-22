package com.example.network.data

import com.google.gson.annotations.SerializedName


data class PlayerStatus(
    val icestats: Icestats,
)

data class Icestats(
    val admin: String,
    val host: String,
    val location: String,
    @SerializedName("server_id")
    val serverId: String,
    @SerializedName("server_start")
    val serverStart: String,
    @SerializedName("server_start_iso8601")
    val serverStartIso8601: String,
    val source: List<Source>,
)

data class Source(
    @SerializedName("audio_info")
    val audioInfo: String,
    val dummy: Any?,
    val genre: String,
    @SerializedName("ice-bitrate")
    val iceBitrate: Long,
    @SerializedName("ice-channels")
    val iceChannels: Long,
    @SerializedName("ice-samplerate")
    val iceSamplerate: Long,
    val listeners: Long,
    val listenurl: String,
    @SerializedName("m3u_url")
    val m3uUrl: String,
    val mount: String,
    @SerializedName("pls_url")
    val plsUrl: String,
    @SerializedName("server_description")
    val serverDescription: String,
    @SerializedName("server_name")
    val serverName: String,
    @SerializedName("server_type")
    val serverType: String,
    @SerializedName("server_url")
    val serverUrl: String,
    @SerializedName("stream_start")
    val streamStart: String,
    @SerializedName("stream_start_iso8601")
    val streamStartIso8601: String,
    val title: String,
    @SerializedName("xspf_url")
    val xspfUrl: String,
)

