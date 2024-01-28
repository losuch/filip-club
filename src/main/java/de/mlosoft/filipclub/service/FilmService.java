package de.mlosoft.filipclub.service;

import java.util.List;

import de.mlosoft.filipclub.model.Film;

public interface FilmService {
    public List<Film> getAllFilms();

    public Film getFilmById(long filmId);

    public Film createFilm(Film film);

    public Film updateFilm(Film film, long filmId);

    public void deleteFilm(long filmId);
}
