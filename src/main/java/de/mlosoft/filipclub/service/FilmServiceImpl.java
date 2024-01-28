package de.mlosoft.filipclub.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import de.mlosoft.filipclub.entity.FilmEntity;
import de.mlosoft.filipclub.error.ErrorCode;
import de.mlosoft.filipclub.error.ErrorInfo;
import de.mlosoft.filipclub.error.FilipClubException;
import de.mlosoft.filipclub.model.Film;
import de.mlosoft.filipclub.persistance.FilmRepository;
import de.mlosoft.filipclub.util.DozerHelper;
import de.mlosoft.filipclub.util.LogUtil;

@Service
@Qualifier("filmService")
public class FilmServiceImpl implements FilmService {

    private static final Logger LOG = LogUtil.getLogger();

    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<Film> getAllFilms() {
        List<FilmEntity> response = filmRepository.getAllFilms();

        if (response.isEmpty()) {
            // user not found
            ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
            throw new FilipClubException(info);
        }

        List<Film> films = DozerHelper.map(mapper, response, Film.class);
        LOG.debug("FilmService getAllFilms: {}", films.toString());
        return films;
    }

    @Override
    public Film getFilmById(long filmId) {
        List<FilmEntity> result = filmRepository.findFilmById(filmId);

        if (result.size() == 1) {
            return mapper.map(result.get(0), Film.class);
        }
        ErrorInfo info = new ErrorInfo(ErrorCode.UNKNOWN_ERROR.name());
        info.setAdditionalInfo("found more then one film for filmId:", String.valueOf(filmId));
        throw new FilipClubException(info);
    }

    @Override
    public Film createFilm(Film film) {

        FilmEntity entityRequest = mapper.map(film, FilmEntity.class);
        FilmEntity entityResponse = filmRepository.createFilm(entityRequest);
        return mapper.map(entityResponse, Film.class);
    }

    @Override
    public Film updateFilm(Film film, long filmId) {
        FilmEntity filmEntity = mapper.map(film, FilmEntity.class);
        FilmEntity filmEntityResponse = filmRepository.updateFilm(filmEntity, filmId);
        return mapper.map(filmEntityResponse, Film.class);
    }

    @Override
    public void deleteFilm(long filmId) {
        filmRepository.deleteFilm(filmId);
    }
}
