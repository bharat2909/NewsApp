package com.androiddevs.mvvmnewsapp.ui.db

import android.content.Context
import androidx.room.*
import com.androiddevs.mvvmnewsapp.ui.models.Article
import com.androiddevs.mvvmnewsapp.ui.utils.Converters

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class articleDatabase: RoomDatabase() {

    abstract fun getArticleDAO() : articleDAO

    companion object{
        @Volatile
        private var instance : articleDatabase?=null

        private val LOCK = Any()
        operator fun invoke(context : Context) = instance?: synchronized(LOCK){
            instance?: createDatabase(context).also{ instance = it}
        }

        fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                articleDatabase :: class.java,
                "articleDB.db"
            ).build()

    }
}