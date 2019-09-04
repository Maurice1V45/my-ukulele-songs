package com.mivas.myukulelesongs.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mivas.myukulelesongs.R
import com.mivas.myukulelesongs.database.model.Song
import com.mivas.myukulelesongs.util.Constants
import com.mivas.myukulelesongs.viewmodel.TabViewModel
import com.mivas.myukulelesongs.util.Constants.EXTRA_ID
import com.mivas.myukulelesongs.viewmodel.TabViewModelFactory
import kotlinx.android.synthetic.main.activity_tab.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColor


class TabActivity : AppCompatActivity() {

    private lateinit var viewModel: TabViewModel
    private val scrollHandler = Handler()
    private val scrollRunnable = object : Runnable {
        override fun run() {
            scrollView.smoothScrollTo(0, scrollView.scrollY + 1)
            scrollHandler.postDelayed(this, 150 - scrollSeekBar.progress.toLong())
        }
    }
    private val customizationsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            initStyles()
            tabText.setText(
                viewModel.getChordData(viewModel.getSong().value!!).spannableBuilder,
                TextView.BufferType.SPANNABLE
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        val viewModelFactory = TabViewModelFactory(application, intent.getLongExtra(EXTRA_ID, -1))
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TabViewModel::class.java)

        initStyles()
        initListeners()
        initObservers()

        registerReceiver(
            customizationsReceiver,
            IntentFilter(Constants.BROADCAST_CUSTOMIZATIONS_UPDATED)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tab, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_auto_scroll -> {
                scrollTabView.visibility =
                    if (scrollTabView.visibility == View.GONE) View.VISIBLE else View.GONE
                true
            }
            R.id.action_edit_song -> {
                startActivity(Intent(this, AddEditSongActivity::class.java).apply {
                    putExtra(EXTRA_ID, intent.getLongExtra(EXTRA_ID, -1))
                })
                true
            }
            R.id.action_customize -> {
                startActivity(Intent(this, CustomizeTabActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initObservers() {
        viewModel.getSong().observe(this, Observer<Song> {
            it?.run {
                this@TabActivity.title = it.title
                val chordData = viewModel.getChordData(it)
                chordsText.text = viewModel.getDisplayChords(chordData)
                strummingPatternsText.text = it.strummingPatterns
                strummingPatternsLayout.visibility =
                    if (it.strummingPatterns.isEmpty()) View.GONE else View.VISIBLE
                pickingPatternsText.text = it.pickingPatterns
                pickingPatternsLayout.visibility =
                    if (it.pickingPatterns.isEmpty()) View.GONE else View.VISIBLE
                tabText.setText(chordData.spannableBuilder, TextView.BufferType.SPANNABLE)
            } ?: finish()
        })
    }

    private fun initListeners() {
        playButton.setOnClickListener {
            if (viewModel.scrollRunning) {
                stopScroll()
            } else {
                startScroll()
            }
        }
        closeButton.setOnClickListener {
            scrollTabView.visibility = View.GONE
        }
    }

    private fun initStyles() {
        tabText.textSize = viewModel.getTextSize().toFloat()
        tabText.textColor = viewModel.getTextColor()
        tabParent.backgroundColor = viewModel.getBackgroundColor()
    }

    private fun startScroll() {
        viewModel.scrollRunning = true
        playButton.imageResource = R.drawable.selector_button_pause
        scrollHandler.postDelayed(scrollRunnable, 150 - scrollSeekBar.progress.toLong())
    }

    private fun stopScroll() {
        viewModel.scrollRunning = false
        playButton.imageResource = R.drawable.selector_button_play
        scrollHandler.removeCallbacks(scrollRunnable)
    }

    override fun onDestroy() {
        unregisterReceiver(customizationsReceiver)
        super.onDestroy()
    }

}
