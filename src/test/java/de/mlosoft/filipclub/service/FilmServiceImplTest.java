package de.mlosoft.filipclub.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.mlosoft.filipclub.entity.FilmEntity;
import de.mlosoft.filipclub.model.Film;
import de.mlosoft.filipclub.persistance.FilmRepository;

public class FilmServiceImplTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmServiceImpl filmService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllFilms() {
        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setName("Test Film");
        filmEntity.setActive(1);

        when(filmRepository.getAllFilms()).thenReturn(Arrays.asList(filmEntity));

        List<Film> films = filmService.getAllFilms();

        assertFalse(films.isEmpty());
        assertEquals(filmEntity.getName(), films.get(0).getName());
        assertEquals(filmEntity.getActive() == 1, films.get(0).isActive());
        verify(filmRepository, times(1)).getAllFilms();
    }

    @Test
    public void testGetFilmById() {
        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setFilmId(1L);
        filmEntity.setName("Test Film");

        when(filmRepository.findFilmById(anyLong())).thenReturn(Collections.singletonList(filmEntity));

        Film film = filmService.getFilmById(1L);

        assertEquals(filmEntity.getName(), film.getName());
        verify(filmRepository, times(1)).findFilmById(anyLong());
    }
}