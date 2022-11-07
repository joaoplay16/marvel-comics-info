package com.playlab.marvelcomicsinfo.data.paging

import androidx.paging.ExperimentalPagingApi
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.model.Thumbnail
import com.playlab.marvelcomicsinfo.repository.FakeComicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@OptIn(
    ExperimentalPagingApi::class,
    ExperimentalCoroutinesApi::class)
@ExperimentalCoroutinesApi
class PagingDataTrasformTest {

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    @Mock
    lateinit var fakeComicRepository: FakeComicRepository

    companion object {
        val comics = listOf<Comic>(
            Comic(id = "01",
                "Hulk",
                Thumbnail("path", ".jpg"),
                null, null),

            Comic(id = "02",
                "Hulk 2",
                Thumbnail("path", ".jpg"),
                null, null),

            Comic(id = "03",
                "Wolverine",
                Thumbnail("path", ".jpg"),
                null, null),
        )
    }
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}