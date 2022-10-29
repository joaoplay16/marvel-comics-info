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
class MarvelRemoteKeysDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: MarvelDatabaseAndroidTest
    private lateinit var remoteKeysDao: MarvelRemoteKeysDao

    @Before
    fun before (){
        hiltRule.inject()
        remoteKeysDao = database.marvelRemoteKeysDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertRemoteKeys() = runTest{
        val remoteKeys = MarvelRemoteKeys(
            "1",
            null,
            2
        )

        remoteKeysDao.addAllRemoteKeys(listOf(remoteKeys))

        val result: MarvelRemoteKeys = remoteKeysDao.getRemoteKeys(remoteKeys.id)

        assertThat(result).isEqualTo(remoteKeys)
    }

    @Test
    fun deleteAllRemoteKeys() = runTest{
        val remoteKeysList = listOf(
            MarvelRemoteKeys("1",null,2 ),
            MarvelRemoteKeys("2",1,3 )
        )

        remoteKeysDao.addAllRemoteKeys(remoteKeysList)

        remoteKeysDao.deleteAllRemoteKeys()

        for (remoteKeys in remoteKeysList){
            val result = remoteKeysDao.getRemoteKeys(remoteKeys.id)
            assertThat(result).isNull()
        }
    }
}