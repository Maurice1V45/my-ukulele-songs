package com.mivas.myukulelesongs.util

import android.graphics.Color

object Constants {

    /* Privacy policy */
    const val URL_PRIVACY_POLICY = "https://www.freeprivacypolicy.com/privacy/view/75eb2f2494a017039da6c2b979dbc8b8"

    /* Ukulele chords */
    const val API_KEY_UKULELE_CHORDS = "bbec19965dd6b66598365c7129784401"

    /* Drive */
    const val CONFIG_FILE_NAME = "config.json"
    const val CONFIG_FILE_DEFAULT_VALUE = "{\"songs\":[]}"

    /* Preferences */
    const val PREFS = "templates_prefs"
    const val PREF_FIRST_RUN = "pref_first_run"
    const val PREF_CHORDS_SONG_TEXT_SIZE = "pref_tab_text_size"
    const val PREF_CHORDS_SONG_TEXT_COLOR = "pref_tab_text_color"
    const val PREF_CHORDS_SONG_CHORD_COLOR = "pref_tab_chord_color"
    const val PREF_CHORDS_SONG_BACKGROUND_COLOR = "pref_tab_background_color"
    const val PREF_TAB_SONG_TEXT_SIZE = "pref_tab_song_text_size"
    const val PREF_TAB_SONG_HEADER_COLOR = "pref_tab_song_header_color"
    const val PREF_TAB_SONG_NUMBERS_COLOR = "pref_tab_song_numbers_color"
    const val PREF_TAB_SONG_LINE_COLOR = "pref_tab_song_line_color"
    const val PREF_TAB_SONG_BACKGROUND_COLOR = "pref_tab_song_background_color"
    const val PREF_PREFER_SHARP = "pref_prefer_sharp"
    const val PREF_DRIVE_SYNC = "pref_drive_sync"
    const val PREF_DRIVE_CONFIG_FILE_ID = "pref_drive_config_file_id"
    const val PREF_CHORDS_HINT_READ = "pref_chords_hint_read"
    const val PREF_LAST_SCROLL_SPEED = "pref_last_scroll_speed"

    /* Defaults */
    const val DEFAULT_CHORDS_SONG_TEXT_SIZE = 15
    val DEFAULT_CHORDS_SONG_TEXT_COLOR = Color.parseColor("#333333")
    val DEFAULT_CHORDS_SONG_CHORD_COLOR = Color.parseColor("#e00000")
    val DEFAULT_CHORDS_SONG_BACKGROUND_COLOR = Color.parseColor("#ededed")
    const val DEFAULT_TAB_SONG_TEXT_SIZE = 15
    val DEFAULT_TAB_SONG_HEADER_COLOR = Color.parseColor("#333333")
    val DEFAULT_TAB_SONG_LINE_COLOR = Color.parseColor("#888888")
    val DEFAULT_TAB_SONG_NUMBERS_COLOR = Color.parseColor("#e00000")
    val DEFAULT_TAB_SONG_BACKGROUND_COLOR = Color.parseColor("#ededed")
    const val DEFAULT_PREFER_SHARP = false
    const val DEFAULT_SCROLL_SPEED = 30
    const val TAB_FILLER = "---------1---------2---------3---------4---------5---------6---------7---------8---------9---------0---------1---------2---------3---------4---------5---------6---------7---------8---------9---------0---------1---------2---------3---------4---------5---------6---------7---------8---------9---------0---------1---------2---------3---------4---------5---------6---------7---------8---------9---------0---------1---------2---------3---------4---------5---------6---------7---------8---------9---------0---------1---------2---------3---------4---------5---------6---------7---------8---------9---------0---------1---------2---------3---------4---------5---------6---------7---------8---------9---------0---------1---------2---------3---------4---------5---------6---------7---------8---------9---------0---------1---------2---------3---------4---------5---------6---------7---------8---------9---------0---------1---------2---------3---------4---------5---------6---------7---------8---------9---------0"
    const val TAB_DEFAULT_TEXT = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"

    /* Extra */
    const val EXTRA_ID = "com.mivas.myukulelesongs.EXTRA_ID"
    const val EXTRA_IS_TAB = "com.mivas.myukulelesongs.EXTRA_IS_TAB"
    const val EXTRA_CHORD = "com.mivas.myukulelesongs.EXTRA_CHORD"
    const val EXTRA_AFTER_RESTORE = "com.mivas.myukulelesongs.EXTRA_AFTER_RESTORE"
    const val EXTRA_LISTENER = "com.mivas.myukulelesongs.EXTRA_LISTENER"

    /* Broadcast */
    const val BROADCAST_CUSTOMIZATIONS_UPDATED = "broadcast_customizations_update"

}