package de.mlosoft.filipclub.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.mlosoft.filipclub.model.Film;
import de.mlosoft.filipclub.model.FilmList;
import de.mlosoft.filipclub.service.FilmService;
import de.mlosoft.filipclub.util.LogUtil;

@RestController
@RequestMapping("/api/film")
@CrossOrigin()
public class FilmController {

    private static final Logger LOG = LogUtil.getLogger();

    @Autowired(required = true)
    FilmService filmService;

    @GetMapping("/films")
    @JsonSerialize
    @ResponseBody
    public FilmList getAllFilms() {
        LOG.debug("FilmController - getAllFilms");
        return new FilmList(filmService.getAllFilms());
    }

    @GetMapping("/films/{filmId}")
    @JsonSerialize
    @ResponseBody
    public Film getFilmById(@PathVariable("filmId") long filmId) {
        LOG.debug("FilmController - getFilmById accluntId: {}", filmId);
        return filmService.getFilmById(filmId);
    }

    @PostMapping("/films")
    @JsonSerialize
    @ResponseBody
    public Film createFilm(@RequestBody Film film) {
        LOG.debug("FilmController - createFilm Film: {}", film);
        return filmService.createFilm(film);
    }

    @PutMapping("/films/{filmId}")
    @JsonSerialize
    @ResponseBody
    public Film updateFilm(@RequestBody Film film, @PathVariable("filmId") long filmId) {
        LOG.debug("FilmController - updateFilm filmId: {} Film: {}", filmId, film);
        return filmService.updateFilm(film, filmId);
    }

    @DeleteMapping("/films/{filmId}")
    @JsonSerialize
    public void deleteFilm(@PathVariable("filmId") long filmId) {
        LOG.debug("FilmController - deleteFilm filmId: {}", filmId);
        filmService.deleteFilm(filmId);
    }

}
