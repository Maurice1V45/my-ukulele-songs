package com.mivas.myukulelesongs.util

import com.mivas.myukulelesongs.model.UCChordData

/**
 * Helper class that handles chords.
 */
object ChordHelper {

    private val chords = listOf("Ab", "A", "A#", "Bb", "B", "C", "C#", "Db", "D", "D#", "Eb", "E", "F", "F#", "Gb", "G", "G#")
    private val uniqueChords = listOf("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#")
    private val chordTypes =
        listOf("m", "7", "m7", "maj7", "aug", "dim", "dim7", "sus2", "sus4", "7sus2", "7sus4", "m7b5", "9", "11", "13", "6", "m6", "add9", "m9", "5", "m13", "mMaj7", "m11", "maj9")

    /**
     * Checks if a line if text has only chords.
     *
     * @param line The line
     * @return True if the line has only chords, else false
     */
    fun isChordLine(line: String): Boolean {
        val words = line.split(" ").map { it.trim() }
        for (word in words) {
            if (word.isBlank()) continue
            if (word.contains('/')) continue
            var chordDetected = false
            for (chord in chords) {
                if (word.startsWith(chord)) {
                    if (word == chord) {
                        chordDetected = true
                        break
                    } else {
                        for (chordType in chordTypes) {
                            if (word == chord + chordType) {
                                chordDetected = true
                                break
                            }
                        }
                    }
                }
            }
            if (!chordDetected) return false
        }
        return true
    }

    /**
     * Returns the chords from a text line.
     *
     * @param line The line
     * @return A list of chords
     */
    fun getChordsInLine(line: String): List<String> {
        val chords = mutableListOf<String>()
        val words = line.split(" ").map { it.trim() }
        for (word in words) {
            if (word.isBlank()) continue
            if (word.contains('/')) {
                val invertedChords = word.split("/")
                invertedChords.forEach { chords.addAll(getChordsInWord(it)) }
            }
            chords.addAll(getChordsInWord(word))
        }
        return chords
    }

    /**
     * Returns the chords from a word.
     *
     * @param word The word
     * @return The list of chords
     */
    private fun getChordsInWord(word: String): List<String> {
        val chords = mutableListOf<String>()
        for (chord in ChordHelper.chords) {
            if (word.startsWith(chord)) {
                if (word == chord) {
                    chords.add(word)
                    break
                } else {
                    for (chordType in chordTypes) {
                        if (word == chord + chordType) {
                            chords.add(chord + chordType)
                            break
                        }
                    }
                }
            }
        }
        return chords
    }

    /**
     * Forms the chord data that needs to be sent to ukulele-chords.com for a given chord.
     *
     * @param chord The chord
     * @return The [UCChordData] needed for the call
     */
    fun getUCChordData(chord: String): UCChordData {
        if (chords.contains(chord)) {
            return UCChordData(chord.toFlats(), "major")
        } else {
            chords.forEach { preChord ->
                chordTypes.forEach { chordType ->
                    if (chord == preChord + chordType) {
                        return UCChordData(preChord.toFlats(), chordType)
                    }
                }
            }
        }
        return UCChordData("", "")
    }

    /**
     * Returns all possible chords with their types.
     *
     * @return All chords with types
     */
    fun getAllChordsWithTypes(): List<List<String>> {
        val preferSharps = Prefs.getBoolean(Constants.PREF_PREFER_SHARP)
        val all = mutableListOf<List<String>>()
        all.add(uniqueChords.map { if (preferSharps) it.toSharps() else it.toFlats() })
        chordTypes.forEach { chordType ->
            all.add(uniqueChords.map { "${if (preferSharps) it.toSharps() else it.toFlats()}$chordType" })
        }
        return all
    }

}