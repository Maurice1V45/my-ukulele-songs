package com.mivas.myukulelesongs.repository

import android.app.Application
import com.mivas.myukulelesongs.database.Db
import com.mivas.myukulelesongs.database.model.Song
import org.jetbrains.anko.doAsync

class SongsRepository(application: Application) {

    private val songDao = Db.instance.getSongsDao()

    fun insert(song: Song) = doAsync { songDao.insert(song) }
    fun delete(song: Song) = doAsync { songDao.delete(song) }
    fun getAll() = songDao.getAllLive()
    fun getWithFilter(filter: String) = songDao.getWithFilterLive("%$filter%")

}