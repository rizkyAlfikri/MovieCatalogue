package com.dicoding.picodiploma.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.MovieDetailWithInfoEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity.SearchWithMovieTvPeopleEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_popular_entity")
    fun getAllMoviePopular(): LiveData<List<MoviePopularEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListMoviePopular(listMovie: List<MoviePopularEntity>)

    @Query("SELECT * FROM movie_popular_entity ORDER by num DESC ")
    fun getAllMovieFavorite(): DataSource.Factory<Int, MoviePopularEntity>

    @Query("SELECT * FROM movie_popular_entity WHERE idMovie = :id")
    fun getMovieFavoriteById(id: Int): LiveData<MoviePopularEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieFavorite(moviePopularEntity: MoviePopularEntity)

    @Delete
    fun deleteMovieFavorite(moviePopularEntity: MoviePopularEntity)

    @Query("DELETE FROM movie_popular_entity WHERE idMovie = :movieId")
    fun deleteMovieFavoriteById(movieId: Int)

    @Transaction
    @Query("SELECT * FROM movie_detail WHERE id = :id")
    fun getMovieDetailById(id: Int): LiveData<MovieDetailWithInfoEntity>

    @Transaction
    @Query("SELECT * FROM search_entity")
    fun getSearchEntity(): LiveData<SearchWithMovieTvPeopleEntity>

    @Query("SELECT * FROM people_detail WHERE idPeople = :id")
    fun getPeopleDetailById(id: Int): LiveData<PeopleDetailEntity>

}