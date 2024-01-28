package de.mlosoft.filipclub.persistance;

import java.util.List;

import de.mlosoft.filipclub.entity.FilmEntity;

public interface FilmRepository {

    public List<FilmEntity> getAllFilms();

    public List<FilmEntity> findFilmById(long productId);

    public FilmEntity createFilm(FilmEntity product);

    public FilmEntity updateFilm(FilmEntity product, long productId);

    public void deleteFilm(long productId);

}
