import com.synrgy.data.helper.mockMovieDetailResponse
import com.synrgy.data.helper.mockMovieListResponse
import com.synrgy.data.helper.movieListModel
import com.synrgy.data.local.FakeDatabase
import com.synrgy.data.local.movie.FakeMovieDao
import com.synrgy.data.local.user.FakeUserDao
import com.synrgy.data.remote.api.ApiService
import com.synrgy.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var userDao: FakeUserDao
    private lateinit var movieDao: FakeMovieDao
    private lateinit var database: FakeDatabase
    private lateinit var repository: MovieRepositoryImpl

    @Mock
    private lateinit var apiService: ApiService

    @get:Rule
    val rule: TestRule = object : TestWatcher() {
        override fun starting(description: Description) {
            Dispatchers.setMain(dispatcher)
        }

        override fun finished(description: Description) {
            Dispatchers.resetMain()
        }
    }

    @Before
    fun setUp() {
        userDao = FakeUserDao()
        movieDao = FakeMovieDao()
        database = FakeDatabase(userDao, movieDao)
        repository = MovieRepositoryImpl(apiService, database)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetches movie lists from API and converts to MovieListModel`() = runTest {
        `when`(apiService.getMovieListNowPlaying()).thenReturn(mockMovieListResponse)
        `when`(apiService.getMovieListPopular()).thenReturn(mockMovieListResponse)
        `when`(apiService.getMovieListTopRated()).thenReturn(mockMovieListResponse)

        val nowPlayingResult = repository.getMovieListNowPlaying()
        val popularResult = repository.getMovieListPopular()
        val topRatedResult = repository.getMovieListTopRated()

        verify(apiService).getMovieListNowPlaying()
        verify(apiService).getMovieListPopular()
        verify(apiService).getMovieListTopRated()

        assertEquals(1, nowPlayingResult.size)
        assertEquals(1, popularResult.size)
        assertEquals(1, topRatedResult.size)
    }

    @Test
    fun `getMovieDetail fetches data from API and converts to MovieDetailModel`() = runTest {
        `when`(apiService.getMovieDetail(123)).thenReturn(mockMovieDetailResponse)

        val result = repository.getMovieDetail(123)

        verify(apiService).getMovieDetail(123)
        assertEquals("Title", result.title)
        assertEquals("Overview", result.overview)
        assertEquals("2021-01-01", result.releaseDate)
        assertEquals(120, result.runtime)
        assertEquals(listOf("Action"), result.genres)
        assertEquals("/path/to/poster.jpg", result.posterPath)
    }

    @Test
    fun `addMovieToFavorite, removeMovieFromFavorite, and getFavoriteMovies fetches data from database and converts to MovieListModel`() =
        runTest {
            for (i in 1..5) {
                repository.addMovieToFavorite(movieListModel)
            }
            repository.removeMovieFromFavorite(movieListModel)
            val result = repository.getFavoriteMovies()
            assertEquals(4, result.size)
        }
}
