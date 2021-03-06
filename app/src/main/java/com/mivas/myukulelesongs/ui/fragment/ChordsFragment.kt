package com.mivas.myukulelesongs.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexboxLayout
import com.mivas.myukulelesongs.R
import com.mivas.myukulelesongs.ui.activity.ChordInfoActivity
import com.mivas.myukulelesongs.ui.dialog.ChordInfoDialog
import com.mivas.myukulelesongs.util.*
import kotlinx.android.synthetic.main.fragment_chords.*
import kotlinx.android.synthetic.main.fragment_chords.hintLayout
import kotlinx.android.synthetic.main.fragment_chords.okHintButton

/**
 * Fragment displaying most of the chords.
 */
class ChordsFragment: Fragment(R.layout.fragment_chords) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
    }

    /**
     * Views initializer.
     */
    private fun initViews() {
        if (!Prefs.getBoolean(Constants.PREF_CHORDS_HINT_READ)) {
            hintLayout.visibility = View.VISIBLE
        }
        initChords()
    }

    /**
     * Chords layout initializer.
     */
    private fun initChords() {
        val margin = DimensionUtils.dpToPx(2)
        val layoutParams = LinearLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(margin, margin, margin, margin)
        val allChords = ChordHelper.getAllChordsWithTypes()
        allChords.forEach { chordGroup ->
            val verticalLayout = LinearLayout(activity).apply { orientation = LinearLayout.VERTICAL }
            chordGroup.forEach { chord ->
                val chordText = LayoutInflater.from(activity).inflate(R.layout.list_item_chord, null) as TextView
                chordText.layoutParams = layoutParams
                chordText.text = chord
                chordText.setOnClickListener {
                    SoundPlayer.playChord(activity!!, chord.toFlats())
                }
                chordText.setOnLongClickListener {
                    if (NetworkUtils.isInternetAvailable()) {
                        activity!!.startActivity(Intent(activity, ChordInfoActivity::class.java).apply {
                            putExtra(Constants.EXTRA_CHORD, chord)
                        })
                    } else {
                        val transaction = activity!!.supportFragmentManager.beginTransaction()
                        transaction.addToBackStack(null)
                        ChordInfoDialog(chord).show(transaction, "")
                    }
                    true
                }
                verticalLayout.addView(chordText)
            }
            horizontalLayout.addView(verticalLayout)
        }
    }

    /**
     * Listeners initializer.
     */
    private fun initListeners() {
        okHintButton.setOnClickListener {
            Prefs.putBoolean(Constants.PREF_CHORDS_HINT_READ, true)
            hintLayout.visibility = View.GONE
        }
    }

}