package com.mivas.myukulelesongs.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import androidx.lifecycle.ViewModelProviders
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.mivas.myukulelesongs.R
import com.mivas.myukulelesongs.util.Constants
import com.mivas.myukulelesongs.viewmodel.CustomizeTabSongViewModel
import kotlinx.android.synthetic.main.activity_customize_tab_song.*
import org.jetbrains.anko.alert

class CustomizeTabSongActivity : AppCompatActivity() {

    private lateinit var viewModel: CustomizeTabSongViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customize_tab_song)

        initViewModel()
        initViews()
        initListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_customize_song, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_reset -> {
            showResetCustomizationsDialog()
            true
        }
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CustomizeTabSongViewModel::class.java)
    }

    private fun initViews() {
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.customize_song_activity_title_tab)
        }
        val textSize = viewModel.getTextSize()
        textSizeSeekBar.progress = textSize
        textSizeText.text = textSize.toString()
        headerColorView.background = viewModel.createBackground(viewModel.getHeaderColor())
        lineColorView.background = viewModel.createBackground(viewModel.getLineColor())
        numbersColorView.background = viewModel.createBackground(viewModel.getNumbersColor())
        backgroundColorView.background = viewModel.createBackground(viewModel.getBackgroundColor())
    }

    private fun initListeners() {
        textSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) {
                viewModel.setTextSize(p0!!.progress)
                sendUpdateBroadcast()
            }

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                textSizeText.text = p1.toString()
            }

        })
        headerColorLayout.setOnClickListener {
            ColorPickerDialogBuilder
                .with(this)
                .initialColor(viewModel.getHeaderColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton(R.string.generic_ok) { _, color, _ ->
                    viewModel.setHeaderColor(color)
                    headerColorView.background = viewModel.createBackground(color)
                    sendUpdateBroadcast()
                }
                .build()
                .show()
        }
        lineColorLayout.setOnClickListener {
            ColorPickerDialogBuilder
                .with(this)
                .initialColor(viewModel.getLineColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton(R.string.generic_ok) { _, color, _ ->
                    viewModel.setLineColor(color)
                    lineColorView.background = viewModel.createBackground(color)
                    sendUpdateBroadcast()
                }
                .build()
                .show()
        }
        numbersColorLayout.setOnClickListener {
            ColorPickerDialogBuilder
                .with(this)
                .initialColor(viewModel.getNumbersColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton(R.string.generic_ok) { _, color, _ ->
                    viewModel.setNumbersColor(color)
                    numbersColorView.background = viewModel.createBackground(color)
                    sendUpdateBroadcast()
                }
                .build()
                .show()
        }
        backgroundColorLayout.setOnClickListener {
            ColorPickerDialogBuilder
                .with(this)
                .initialColor(viewModel.getBackgroundColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton(R.string.generic_ok) { _, color, _ ->
                    viewModel.setBackgroundColor(color)
                    backgroundColorView.background = viewModel.createBackground(color)
                    sendUpdateBroadcast()
                }
                .build()
                .show()
        }
    }

    private fun showResetCustomizationsDialog() {
        alert(R.string.customize_song_activity_dialog_reset_description, R.string.customize_song_activity_dialog_reset_title) {
            negativeButton(R.string.generic_cancel) {}
            positiveButton(R.string.generic_reset) {
                viewModel.reset()
                initViews()
                sendUpdateBroadcast()
            }
        }.show()
    }

    private fun sendUpdateBroadcast() = sendBroadcast(Intent(Constants.BROADCAST_CUSTOMIZATIONS_UPDATED))

}
