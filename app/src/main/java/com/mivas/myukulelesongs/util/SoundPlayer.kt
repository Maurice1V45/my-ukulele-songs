package com.mivas.myukulelesongs.util

import android.content.Context
import android.media.MediaPlayer
import com.mivas.myukulelesongs.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch

/**
 * Class that handles playing of notes.
 */
object SoundPlayer {
    private var mediaPlayer1: MediaPlayer? = null
    private var mediaPlayer2: MediaPlayer? = null
    private var mediaPlayer3: MediaPlayer? = null
    private var mediaPlayer4: MediaPlayer? = null
    private var mediaPlayers = mutableListOf(mediaPlayer1, mediaPlayer2, mediaPlayer3, mediaPlayer4)

    private val scale = listOf(
        "C1",
        "Db1",
        "D1",
        "Eb1",
        "E1",
        "F1",
        "Gb1",
        "G1",
        "Ab1",
        "A1",
        "Bb1",
        "B1",
        "C2",
        "Db2",
        "D2",
        "Eb2",
        "E2",
        "F2",
        "Gb2",
        "G2",
        "Ab2",
        "A2",
        "Bb2",
        "B2",
        "C3",
        "Db3",
        "D3",
        "Eb3"
    )

    private val soundsMap = mapOf(
        "C1" to R.raw.sound_c1,
        "Db1" to R.raw.sound_db1,
        "D1" to R.raw.sound_d1,
        "Eb1" to R.raw.sound_eb1,
        "E1" to R.raw.sound_e1,
        "F1" to R.raw.sound_f1,
        "Gb1" to R.raw.sound_gb1,
        "G1" to R.raw.sound_g1,
        "Ab1" to R.raw.sound_ab1,
        "A1" to R.raw.sound_a1,
        "Bb1" to R.raw.sound_bb1,
        "B1" to R.raw.sound_b1,
        "C2" to R.raw.sound_c2,
        "Db2" to R.raw.sound_db2,
        "D2" to R.raw.sound_d2,
        "Eb2" to R.raw.sound_eb2,
        "E2" to R.raw.sound_e2,
        "F2" to R.raw.sound_f2,
        "Gb2" to R.raw.sound_gb2,
        "G2" to R.raw.sound_g2,
        "Ab2" to R.raw.sound_ab2,
        "A2" to R.raw.sound_a2,
        "Bb2" to R.raw.sound_bb2,
        "B2" to R.raw.sound_b2,
        "C3" to R.raw.sound_c3,
        "Db3" to R.raw.sound_db3,
        "D3" to R.raw.sound_d3,
        "Eb3" to R.raw.sound_eb3
    )

    private val chordsMap = mapOf(
        Pair("A", listOf(2, 1, 0, 0)),
        Pair("Am", listOf(2, 0, 0, 0)),
        Pair("A7", listOf(0, 1, 0, 0)),
        Pair("Am7", listOf(0, 0, 0, 0)),
        Pair("Aaug", listOf(2, 1, 1, 4)),
        Pair("Adim", listOf(2, 3, 5, 3)),
        Pair("Amaj7", listOf(1, 1, 0, 0)),
        Pair("Am7b5", listOf(2, 3, 3, 3)),
        Pair("Asus2", listOf(2, 4, 5, 2)),
        Pair("Asus4", listOf(2, 2, 0, 0)),
        Pair("A7sus4", listOf(0, 2, 0, 0)),
        Pair("A9", listOf(2, 1, 3, 2)),
        Pair("A11", listOf(2, 2, 3, 4)),
        Pair("A13", listOf(0, 1, 2, 0)),
        Pair("A6", listOf(2, 4, 2, 4)),
        Pair("Am6", listOf(2, 4, 2, 3)),
        Pair("Aadd9", listOf(2, 1, 0, 2)),
        Pair("Am9", listOf(2, 0, 3, 2)),
        Pair("A5", listOf(2, 4, 0, 0)),
        Pair("Adim7", listOf(2, 3, 2, 3)),
        Pair("Am13", listOf(0, 0, 2, 0)),
        Pair("A7sus2", listOf(4, 4, 3, 0)),
        Pair("AmMaj7", listOf(1, 0, 0, 0)),
        Pair("Am11", listOf(0, 0, 5, 5)),
        Pair("Amaj9", listOf(4, 1, 4, 0)),

        Pair("Bb", listOf(3, 2, 1, 1)),
        Pair("Bbm", listOf(3, 1, 1, 1)),
        Pair("Bb7", listOf(1, 2, 1, 1)),
        Pair("Bbm7", listOf(1, 1, 1, 1)),
        Pair("Bbaug", listOf(3, 2, 2, 1)),
        Pair("Bbdim", listOf(3, 1, 0, 1)),
        Pair("Bbmaj7", listOf(3, 2, 1, 0)),
        Pair("Bbm7b5", listOf(1, 1, 0, 1)),
        Pair("Bbsus2", listOf(3, 0, 1, 1)),
        Pair("Bbsus4", listOf(3, 3, 1, 1)),
        Pair("Bb7sus4", listOf(1, 3, 1, 1)),
        Pair("Bb9", listOf(3, 2, 4, 3)),
        Pair("Bb11", listOf(3, 3, 4, 5)),
        Pair("Bb13", listOf(0, 2, 4, 1)),
        Pair("Bb6", listOf(0, 2, 1, 1)),
        Pair("Bbm6", listOf(0, 1, 1, 1)),
        Pair("Bbadd9", listOf(3, 2, 1, 3)),
        Pair("Bbm9", listOf(3, 1, 4, 3)),
        Pair("Bb5", listOf(3, -1, 1, 1)),
        Pair("Bbdim7", listOf(0, 1, 0, 1)),
        Pair("Bbm13", listOf(1, 1, 3, 1)),
        Pair("Bb7sus2", listOf(1, 0, 1, 1)),
        Pair("BbmMaj7", listOf(3, 1, 1, 0)),
        Pair("Bbm11", listOf(6, 8, 6, 6)),
        Pair("Bbmaj9", listOf(3, 0, 5, 5)),

        Pair("B", listOf(4, 3, 2, 2)),
        Pair("Bm", listOf(4, 2, 2, 2)),
        Pair("B7", listOf(2, 3, 2, 2)),
        Pair("Bm7", listOf(2, 2, 2, 2)),
        Pair("Baug", listOf(4, 3, 3, 2)),
        Pair("Bdim", listOf(4, 2, 1, 2)),
        Pair("Bmaj7", listOf(4, 3, 2, 1)),
        Pair("Bm7b5", listOf(2, 2, 1, 2)),
        Pair("Bsus2", listOf(4, 1, 2, 2)),
        Pair("Bsus4", listOf(4, 4, 2, 2)),
        Pair("B7sus4", listOf(2, 4, 2, 2)),
        Pair("B9", listOf(4, 3, 5, 4)),
        Pair("B11", listOf(4, 3, 0, 0)),
        Pair("B13", listOf(4, 3, 4, 0)),
        Pair("B6", listOf(1, 3, 2, 2)),
        Pair("Bm6", listOf(1, 2, 2, 2)),
        Pair("Badd9", listOf(4, 3, 2, 4)),
        Pair("Bm9", listOf(4, 2, 5, 4)),
        Pair("B5", listOf(-1, -1, 2, 2)),
        Pair("Bdim7", listOf(1, 2, 1, 2)),
        Pair("Bm13", listOf(4, 2, 4, 0)),
        Pair("B7sus2", listOf(2, 1, 2, 2)),
        Pair("BmMaj7", listOf(3, 2, 2, 2)),
        Pair("Bm11", listOf(4, 2, 0, 0)),
        Pair("Bmaj9", listOf(3, 3, 7, 4)),

        Pair("C", listOf(0, 0, 0, 3)),
        Pair("Cm", listOf(0, 3, 3, 3)),
        Pair("C7", listOf(0, 0, 0, 1)),
        Pair("Cm7", listOf(3, 3, 3, 3)),
        Pair("Caug", listOf(1, 0, 0, 3)),
        Pair("Cdim", listOf(-1, 3, 2, 3)),
        Pair("Cmaj7", listOf(0, 0, 0, 2)),
        Pair("Cm7b5", listOf(3, 3, 2, 3)),
        Pair("Csus2", listOf(0, 2, 3, 3)),
        Pair("Csus4", listOf(0, 0, 1, 3)),
        Pair("C7sus4", listOf(0, 0, 1, 1)),
        Pair("C9", listOf(3, 2, 0, 3)),
        Pair("C11", listOf(5, 5, 0, 1)),
        Pair("C13", listOf(3, 0, 0, 0)),
        Pair("C6", listOf(0, 0, 0, 0)),
        Pair("Cm6", listOf(2, 3, 3, 3)),
        Pair("Cadd9", listOf(0, 2, 0, 3)),
        Pair("Cm9", listOf(5, 3, 6, 5)),
        Pair("C5", listOf(0, 0, 3, 3)),
        Pair("Cdim7", listOf(2, 3, 2, 3)),
        Pair("Cm13", listOf(3, 3, 5, 3)),
        Pair("C7sus2", listOf(3, 0, 3, 5)),
        Pair("CmMaj7", listOf(4, 3, 3, 3)),
        Pair("Cm11", listOf(3, 3, 1, 3)),
        Pair("Cmaj9", listOf(4, 0, 0, 5)),

        Pair("Db", listOf(1, 1, 1, 4)),
        Pair("Dbm", listOf(1, 1, 0, -1)),
        Pair("Db7", listOf(1, 1, 1, 2)),
        Pair("Dbm7", listOf(1, 1, 0, 2)),
        Pair("Dbaug", listOf(2, 1, 1, 0)),
        Pair("Dbdim", listOf(0, 1, 0, 4)),
        Pair("Dbmaj7", listOf(1, 1, 1, 3)),
        Pair("Dbm7b5", listOf(0, 1, 0, 2)),
        Pair("Dbsus2", listOf(1, 3, 4, 4)),
        Pair("Dbsus4", listOf(1, 1, 2, 4)),
        Pair("Db7sus4", listOf(1, 1, 2, 2)),
        Pair("Db9", listOf(1, 1, 1, 2)),
        Pair("Db11", listOf(6, 6, 7, 8)),
        Pair("Db13", listOf(4, 1, 1, 1)),
        Pair("Db6", listOf(1, 1, 1, 1)),
        Pair("Dbm6", listOf(1, 1, 0, 1)),
        Pair("Dbadd9", listOf(1, 3, 1, 4)),
        Pair("Dbm9", listOf(4, 3, 0, 4)),
        Pair("Db5", listOf(1, 1, 4, 4)),
        Pair("Dbdim7", listOf(0, 1, 0, 1)),
        Pair("Dbm13", listOf(3, 1, 0, 2)),
        Pair("Db7sus2", listOf(4, 3, 4, 4)),
        Pair("DbmMaj7", listOf(1, 0, 0, 4)),
        Pair("Dbm11", listOf(4, 4, 2, 4)),
        Pair("Dbmaj9", listOf(5, 5, 9, 6)),

        Pair("D", listOf(2, 2, 2, 0)),
        Pair("Dm", listOf(2, 2, 1, 0)),
        Pair("D7", listOf(2, 2, 2, 3)),
        Pair("Dm7", listOf(2, 2, 1, 3)),
        Pair("Daug", listOf(3, 2, 2, 1)),
        Pair("Ddim", listOf(1, 2, 1, -1)),
        Pair("Dmaj7", listOf(2, 2, 2, 4)),
        Pair("Dm7b5", listOf(1, 2, 1, 3)),
        Pair("Dsus2", listOf(2, 2, 0, 0)),
        Pair("Dsus4", listOf(0, 2, 3, 0)),
        Pair("D7sus4", listOf(2, 2, 3, 3)),
        Pair("D9", listOf(5, 4, 2, 5)),
        Pair("D11", listOf(0, 2, 2, 3)),
        Pair("D13", listOf(5, 2, 2, 2)),
        Pair("D6", listOf(2, 2, 2, 2)),
        Pair("Dm6", listOf(2, 2, 1, 2)),
        Pair("Dadd9", listOf(2, 4, 2, 5)),
        Pair("Dm9", listOf(5, 4, 1, 5)),
        Pair("D5", listOf(2, 2, -1, 0)),
        Pair("Ddim7", listOf(1, 2, 1, 2)),
        Pair("Dm13", listOf(5, 5, 7, 5)),
        Pair("D7sus2", listOf(2, 0, 0, 5)),
        Pair("DmMaj7", listOf(6, 5, 5, 5)),
        Pair("Dm11", listOf(0, 2, 1, 3)),
        Pair("Dmaj9", listOf(6, 6, 0, 5)),

        Pair("Eb", listOf(3, 3, 3, 1)),
        Pair("Ebm", listOf(3, 3, 2, 1)),
        Pair("Eb7", listOf(3, 3, 3, 4)),
        Pair("Ebm7", listOf(3, 3, 2, 4)),
        Pair("Ebaug", listOf(0, 3, 3, 2)),
        Pair("Ebdim", listOf(2, 3, 2, 0)),
        Pair("Ebmaj7", listOf(3, 3, 3, 5)),
        Pair("Ebm7b5", listOf(2, 3, 2, 4)),
        Pair("Ebsus2", listOf(3, 3, 1, 1)),
        Pair("Ebsus4", listOf(1, 3, 4, 1)),
        Pair("Eb7sus4", listOf(3, 3, 4, 4)),
        Pair("Eb9", listOf(0, 3, 1, 4)),
        Pair("Eb11", listOf(0, 3, 4, 4)),
        Pair("Eb13", listOf(5, 3, 3, 4)),
        Pair("Eb6", listOf(3, 3, 3, 3)),
        Pair("Ebm6", listOf(3, 3, 2, 3)),
        Pair("Ebadd9", listOf(0, 3, 1, 1)),
        Pair("Ebm9", listOf(6, 5, 2, 6)),
        Pair("Eb5", listOf(3, 3, 6, 6)),
        Pair("Ebdim7", listOf(2, 3, 2, 3)),
        Pair("Ebm13", listOf(6, 6, 8, 6)),
        Pair("Eb7sus2", listOf(6, 5, 6, 6)),
        Pair("EbmMaj7", listOf(3, 2, 2, 6)),
        Pair("Ebm11", listOf(6, 6, 4, 6)),
        Pair("Ebmaj9", listOf(0, 3, 1, 5)),

        Pair("E", listOf(1, 4, 0, 2)),
        Pair("Em", listOf(0, 4, 3, 2)),
        Pair("E7", listOf(1, 2, 0, 2)),
        Pair("Em7", listOf(0, 2, 0, 2)),
        Pair("Eaug", listOf(1, 0, 0, 3)),
        Pair("Edim", listOf(0, 4, 0, 1)),
        Pair("Emaj7", listOf(1, 3, 0, 2)),
        Pair("Em7b5", listOf(0, 2, 0, 1)),
        Pair("Esus2", listOf(4, 4, 2, 2)),
        Pair("Esus4", listOf(4, 4, 0, 0)),
        Pair("E7sus4", listOf(4, 4, 5, 5)),
        Pair("E9", listOf(7, 6, 4, 7)),
        Pair("E11", listOf(1, 2, 0, 0)),
        Pair("E13", listOf(1, 2, 0, 4)),
        Pair("E6", listOf(4, 4, 4, 4)),
        Pair("Em6", listOf(0, 1, 0, 2)),
        Pair("Eadd9", listOf(1, 4, 2, 2)),
        Pair("Em9", listOf(0, 6, 0, 5)),
        Pair("E5", listOf(4, 4, 0, 2)),
        Pair("Edim7", listOf(0, 1, 0, 1)),
        Pair("Em13", listOf(0, 2, 0, 4)),
        Pair("E7sus2", listOf(4, 4, 2, 5)),
        Pair("EmMaj7", listOf(0, 3, 0, 2)),
        Pair("Em11", listOf(0, 2, 0, 0)),
        Pair("Emaj9", listOf(8, 8, 0, 9)),

        Pair("F", listOf(2, 0, 1, 0)),
        Pair("Fm", listOf(1, 0, 1, 3)),
        Pair("F7", listOf(2, 3, 1, 3)),
        Pair("Fm7", listOf(1, 3, 1, 3)),
        Pair("Faug", listOf(2, 1, 1, 0)),
        Pair("Fdim", listOf(1, -1, 1, 2)),
        Pair("Fmaj7", listOf(2, 4, 1, 3)),
        Pair("Fm7b5", listOf(1, 3, 1, 2)),
        Pair("Fsus2", listOf(0, 0, 1, 3)),
        Pair("Fsus4", listOf(3, 0, 1, 1)),
        Pair("F7sus4", listOf(5, 5, 6, 6)),
        Pair("F9", listOf(0, 3, 1, 0)),
        Pair("F11", listOf(3, 3, 1, 0)),
        Pair("F13", listOf(2, 3, 1, 5)),
        Pair("F6", listOf(2, 2, 1, 3)),
        Pair("Fm6", listOf(1, 2, 1, 3)),
        Pair("Fadd9", listOf(0, 0, 1, 0)),
        Pair("Fm9", listOf(0, 5, 4, 6)),
        Pair("F5", listOf(-1, 0, 1, 3)),
        Pair("Fdim7", listOf(1, 2, 1, 2)),
        Pair("Fm13", listOf(8, 8, 11, 8)),
        Pair("F7sus2", listOf(0, 3, 1, 3)),
        Pair("FmMaj7", listOf(1, 4, 1, 3)),
        Pair("Fm11", listOf(1, 3, 1, 1)),
        Pair("Fmaj9", listOf(0, 4, 1, 0)),

        Pair("Gb", listOf(3, 1, 2, 1)),
        Pair("Gbm", listOf(2, 1, 2, 0)),
        Pair("Gb7", listOf(3, 4, 2, 4)),
        Pair("Gbm7", listOf(2, 4, 2, 4)),
        Pair("Gbaug", listOf(3, 2, 2, 1)),
        Pair("Gbdim", listOf(2, 0, 2, 0)),
        Pair("Gbmaj7", listOf(3, 5, 2, 4)),
        Pair("Gbm7b5", listOf(2, 4, 2, 3)),
        Pair("Gbsus2", listOf(1, 1, 2, 4)),
        Pair("Gbsus4", listOf(4, 1, 2, 2)),
        Pair("Gb7sus4", listOf(6, 6, 7, 7)),
        Pair("Gb9", listOf(1, 4, 2, 1)),
        Pair("Gb11", listOf(4, 4, 2, 1)),
        Pair("Gb13", listOf(9, 6, 6, 6)),
        Pair("Gb6", listOf(3, 3, 2, 4)),
        Pair("Gbm6", listOf(2, 3, 2, 4)),
        Pair("Gbadd9", listOf(1, 1, 2, 1)),
        Pair("Gbm9", listOf(11, 9, 12, 11)),
        Pair("Gb5", listOf(-1, 1, 2, 4)),
        Pair("Gbdim7", listOf(2, 3, 2, 3)),
        Pair("Gbm13", listOf(2, 4, 2, 6)),
        Pair("Gb7sus2", listOf(1, 4, 2, 4)),
        Pair("GbmMaj7", listOf(2, 5, 2, 4)),
        Pair("Gbm11", listOf(4, 4, 2, 0)),
        Pair("Gbmaj9", listOf(1, 5, 2, 1)),

        Pair("G", listOf(0, 2, 3, 2)),
        Pair("Gm", listOf(0, 2, 3, 1)),
        Pair("G7", listOf(0, 2, 1, 2)),
        Pair("Gm7", listOf(0, 2, 1, 1)),
        Pair("Gaug", listOf(0, 3, 3, 2)),
        Pair("Gdim", listOf(0, 1, 3, 1)),
        Pair("Gmaj7", listOf(0, 2, 2, 2)),
        Pair("Gm7b5", listOf(0, 1, 1, 1)),
        Pair("Gsus2", listOf(0, 2, 3, 0)),
        Pair("Gsus4", listOf(0, 2, 3, 3)),
        Pair("G7sus4", listOf(0, 2, 1, 3)),
        Pair("G9", listOf(0, 5, 5, 2)),
        Pair("G11", listOf(0, 0, 1, 2)),
        Pair("G13", listOf(0, 4, 1, 2)),
        Pair("G6", listOf(0, 2, 0, 2)),
        Pair("Gm6", listOf(0, 2, 0, 1)),
        Pair("Gadd9", listOf(0, 2, 5, 2)),
        Pair("Gm9", listOf(0, 5, 5, 1)),
        Pair("G5", listOf(-1, 2, 3, 5)),
        Pair("Gdim7", listOf(0, 1, 0, 1)),
        Pair("Gm13", listOf(0, 4, 1, 1)),
        Pair("G7sus2", listOf(0, 2, 1, 0)),
        Pair("GmMaj7", listOf(0, 2, 2, 1)),
        Pair("Gm11", listOf(0, 0, 1, 1)),
        Pair("Gmaj9", listOf(4, 6, 3, 0)),

        Pair("Ab", listOf(5, 3, 4, 3)),
        Pair("Abm", listOf(4, 3, 4, 2)),
        Pair("Ab7", listOf(1, 3, 2, 3)),
        Pair("Abm7", listOf(1, 3, 2, 2)),
        Pair("Abaug", listOf(1, 4, 4, 3)),
        Pair("Abdim", listOf(1, 2, -1, 2)),
        Pair("Abmaj7", listOf(1, 3, 3, 3)),
        Pair("Abm7b5", listOf(1, 2, 2, 2)),
        Pair("Absus2", listOf(1, 3, 4, 1)),
        Pair("Absus4", listOf(1, 3, 4, 4)),
        Pair("Ab7sus4", listOf(1, 3, 2, 4)),
        Pair("Ab9", listOf(1, 0, 2, 1)),
        Pair("Ab11", listOf(1, 1, 2, 3)),
        Pair("Ab13", listOf(10, 8, 8, 9)),
        Pair("Ab6", listOf(1, 3, 1, 3)),
        Pair("Abm6", listOf(4, 5, 4, 6)),
        Pair("Abadd9", listOf(3, 3, 4, 3)),
        Pair("Abm9", listOf(11, 10, 7, 11)),
        Pair("Ab5", listOf(1, 3, 4, -1)),
        Pair("Abdim7", listOf(1, 2, 1, 2)),
        Pair("Abm13", listOf(4, 6, 4, 8)),
        Pair("Ab7sus2", listOf(1, 3, 2, 1)),
        Pair("AbmMaj7", listOf(0, 3, 4, 2)),
        Pair("Abm11", listOf(4, 6, 4, 4)),
        Pair("Abmaj9", listOf(0, 0, 4, 1))

    )

    /**
     * Plays 4 or less sounds matching a chord.
     *
     * @param context The context
     * @param chord The chord to be played
     */
    fun playChord(context: Context, chord: String) = CoroutineScope(Default).launch {
        val sounds = getSoundsList(chordsMap.getValue(chord))
        for (i in 0..3) {
            if (sounds[i].isNotEmpty()) {
                mediaPlayers[i]?.release()
                mediaPlayers[i] = null
                mediaPlayers[i] = MediaPlayer.create(context, soundsMap.getValue(sounds[i]))
                mediaPlayers[i]?.setOnCompletionListener {
                    it?.stop()
                    it?.release()
                }
                mediaPlayers[i]?.start()
            }
        }
    }

    /**
     * Returns a list of 4 numbers representing a sounds to be played.
     *
     * @param ints The list of the tab representation in reverse of a chord
     * @return The list of sounds to be played
     */
    private fun getSoundsList(ints: List<Int>): List<String> {
        return listOf(
            if (ints[0] >= 0) scale[7 + ints[0]] else "",
            if (ints[1] >= 0) scale[0 + ints[1]] else "",
            if (ints[2] >= 0) scale[4 + ints[2]] else "",
            if (ints[3] >= 0) scale[9 + ints[3]] else ""
        )
    }

    /**
     * Plays a sound.
     *
     * @param context The context
     * @param string The uke string the sound is played from
     * @param note The note the sound ois played from
     */
    fun playSound(context: Context, string: Int, note: String) {
        mediaPlayers[string]?.release()
        mediaPlayers[string] = null
        mediaPlayers[string] = MediaPlayer.create(context, soundsMap.getValue(note))
        mediaPlayers[string]?.setOnCompletionListener {
            it?.stop()
            it?.release()
        }
        mediaPlayers[string]?.start()
    }

    /**
     * Returns a note based on the string and the position there the finger is pressed.
     *
     * @param string The uke string the note is played from
     * @param add The position of the finger on the string
     */
    fun getNoteFromStrings(string: Int, add: Int) = when (string) {
        0 -> scale[9 + add]
        1 -> scale[4 + add]
        2 -> scale[0 + add]
        3 -> scale[7 + add]
        else -> ""
    }
}