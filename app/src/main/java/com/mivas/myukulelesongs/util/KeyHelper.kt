package com.mivas.myukulelesongs.util

object KeyHelper {

    private val keys = listOf(
        listOf("C", "Dm", "Em", "F", "G", "Am", "Bdim"),
        listOf("G", "Am", "Bm", "C", "D", "Em", "F#dim"),
        listOf("G", "Am", "Bm", "C", "D", "Em", "Gbdim"),
        listOf("D", "Em", "F#m", "G", "A", "Bm", "C#dim"),
        listOf("D", "Em", "Gbm", "G", "A", "Bm", "Dbdim"),
        listOf("A", "Bm", "C#m", "D", "E", "F#m", "G#dim"),
        listOf("A", "Bm", "Dbm", "D", "E", "Gbm", "Abdim"),
        listOf("E", "F#m", "G#m", "A", "B", "C#m", "D#dim"),
        listOf("E", "Gbm", "Abm", "A", "B", "Dbm", "Ebdim"),
        listOf("B", "C#m", "D#m", "E", "F#", "G#m", "A#dim"),
        listOf("B", "Dbm", "Ebm", "E", "Gb", "Abm", "Bbdim"),
        listOf("F", "Gm", "Am", "A#", "C", "Dm", "Edim"),
        listOf("F", "Gm", "Am", "Bb", "C", "Dm", "Edim"),
        listOf("A#", "Cm", "Dm", "D#", "F", "Gm", "Adim"),
        listOf("Bb", "Cm", "Dm", "Eb", "F", "Gm", "Adim"),
        listOf("D#", "Fm", "Gm", "G#", "A#", "Cm", "Ddim"),
        listOf("Eb", "Fm", "Gm", "Ab", "Bb", "Cm", "Ddim"),
        listOf("G#", "A#m", "Cm", "C#", "D#", "Fm", "Gdim"),
        listOf("Ab", "Bbm", "Cm", "Db", "Eb", "Fm", "Gdim"),
        listOf("C#", "D#m", "Fm", "F#", "G#", "A#m", "Cdim"),
        listOf("Db", "Ebm", "Fm", "Gb", "Ab", "Bbm", "Cdim"),
        listOf("F#", "G#m", "A#m", "B", "C#", "D#m", "Fdim"),
        listOf("Gb", "Abm", "Bbm", "B", "Db", "Ebm", "Fdim")
    )
    
    private val originalKeys = listOf("Ab", "A", "A#", "Bb", "B", "C", "C#", "Db", "D", "D#", "Eb", "E", "F", "F#", "Gb", "G", "G#", "Abm", "Am", "A#m", "Bbm", "Bm", "Cm", "C#m", "Dbm", "Dm", "D#m", "Ebm", "Em", "Fm", "F#m", "Gbm", "Gm", "G#m")

    fun findKey(chords: Set<String>): String {
        var bestMatches = 0
        var bestList = listOf<String>()
        keys.forEach { keySet ->
            val matches = keySet.count { chords.contains(it) }
            if (matches > bestMatches) {
                bestMatches = matches
                bestList = keySet
            }
        }
        return if (bestList.isEmpty()) "" else bestList[0]
    }

    fun getOriginalKeys() = originalKeys

}