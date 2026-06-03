package com.test.movieapp.data.remote.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.movieapp.data.mapper.toDomain
import com.test.movieapp.domain.model.Review
import java.io.IOException
import retrofit2.HttpException

class ReviewPagingSource(
    private val apiService: MovieApiService,
    private val movieId: Int
) : PagingSource<Int, Review>() {

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getMovieReviews(movieId, page)
            LoadResult.Page(
                data = response.results.map { it.toDomain() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
