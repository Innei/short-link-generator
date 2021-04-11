package dev.innei.work.short_url_generator.model.repository.url.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dev.innei.work.short_url_generator.model.repository.url.URLModel
import dev.innei.work.short_url_generator.model.repository.url.dao.URLDao
import dev.innei.work.short_url_generator.utils.Database

class URLViewModel(application: Application) : AndroidViewModel(application) {
    private val dao: URLDao = Database.getURLDatabase(application).urlDao

    fun getAllUrls(): LiveData<List<URLModel>> {
        return this.dao.findAll()
    }

}
