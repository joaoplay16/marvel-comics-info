package com.playlab.marvelcomicsinfo.data.local.dao

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.playlab.marvelcomicsinfo.data.local.MarvelDatabaseAndroidTest
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.model.Creators
import com.playlab.marvelcomicsinfo.model.MarvelRemoteKeys
import com.playlab.marvelcomicsinfo.model.Thumbnail
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class MarvelComicDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: MarvelDatabaseAndroidTest
    private lateinit var marvelComicDao: MarvelComicDaoAndroidTest

    @Before
    fun before (){
        hiltRule.inject()
        marvelComicDao = database.marvelComicDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertComic() = runTest{
        val comic = Comic(
            "1",
            "Spider-Man",
            Thumbnail("path", ".jpg"),
            "",
            Creators(listOf())
        )

        marvelComicDao.addComics(listOf(comic))

        val result = marvelComicDao.getAllComics()

        assertThat(result).contains(comic)
    }

    @Test
    fun deleteAllComics() = runTest {
        val comic = Comic(
            "1",
            "Spider-Man",
            Thumbnail("path", ".jpg"),
            "",
            Creators(listOf())
        )

        marvelComicDao.addComics(listOf(comic))

        marvelComicDao.deleteAllComics()

        val result = marvelComicDao.getAllComics()

        assertThat(result.isEmpty()).isTrue()
    }
}