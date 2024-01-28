package de.mlosoft.filipclub.persistance;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.mlosoft.filipclub.entity.FilmEntity;
import de.mlosoft.filipclub.error.ErrorCode;
import de.mlosoft.filipclub.error.ErrorInfo;
import de.mlosoft.filipclub.error.FilipClubException;
import de.mlosoft.filipclub.util.LogUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
@Qualifier("filmRepository")
public class FilmRepositoryImpl implements FilmRepository {

    private static final Logger LOG = LogUtil.getLogger();

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<FilmEntity> getAllFilms() {
        LOG.debug("FilmRepository - getAllFilms");
        Query query = em.createQuery("SELECT p FROM FilmEntity p");
        try {
            @SuppressWarnings("unchecked")
            List<FilmEntity> result = (List<FilmEntity>) query.getResultList();
            return result;
        } catch (Exception e) {
            LOG.error("FilmRepository getAllFilms: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public List<FilmEntity> findFilmById(long filmId) {
        LOG.debug("FilmRepository - findFilmById - filmId: {}", filmId);
        Query query = em.createQuery("SELECT p FROM FilmEntity p WHERE p.filmId=:filmId")
                .setParameter("filmId", filmId);

        try {
            @SuppressWarnings("unchecked")
            List<FilmEntity> result = (List<FilmEntity>) query.getResultList();
            if (result.isEmpty()) {
                // no user found
                ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
                info.setAdditionalInfo("no film fount for filmId:", String.valueOf(filmId));
                throw new FilipClubException(info);
            }
            return result;
        } catch (FilipClubException f) {
            LOG.warn("FilmRepository findFilmById {}", f.getMessage());
            throw f;
        } catch (Exception e) {
            LOG.error("FilmRepository findFilmById: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public FilmEntity createFilm(FilmEntity film) {

        LOG.debug("FilmRepository - createFilm: {}", film.toString());
        try {
            FilmEntity FilmEntity = em.merge(film);
            flushAndClear();
            return FilmEntity;
        } catch (Exception e) {
            LOG.error("FilmRepository createFilm: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public FilmEntity updateFilm(FilmEntity film, long filmId) {

        FilmEntity filmEntity;
        try {
            @SuppressWarnings("unchecked")
            List<FilmEntity> result = (List<FilmEntity>) em.createQuery(
                    "SELECT a FROM FilmEntity a WHERE a.filmId = :filmId")
                    .setParameter("filmId", filmId).getResultList();
            if (result.size() == 1) {

                // update role
                filmEntity = result.get(0);
                filmEntity.setName(film.getName());
                filmEntity.setType(film.getType());
                filmEntity.setActive(film.getActive());
                filmEntity.setYtLink(film.getYtLink());

            } else {
                // no user found
                ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
                info.setAdditionalInfo("film not found for filmId:", String.valueOf(filmId));
                throw new FilipClubException(info);
            }
            flushAndClear();
            LOG.debug("FilmRepositoryImpl updateFilm return: {}", filmEntity.toString());
            return filmEntity;
        } catch (FilipClubException f) {
            LOG.warn("FilmRepository updateAccou: {}", f.getMessage());
            throw f;
        } catch (Exception e) {
            LOG.error("FilmRepository updateFilm: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public void deleteFilm(long filmId) {
        Query query = em.createQuery("SELECT a FROM FilmEntity a WHERE a.filmId = :filmId");
        query.setParameter("filmId", filmId);

        try {
            @SuppressWarnings("unchecked")
            List<FilmEntity> result = (List<FilmEntity>) query.getResultList();
            if (result.isEmpty()) {
                // no user found
                ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
                info.setAdditionalInfo("Film not found for filmId:", String.valueOf(filmId));
                throw new FilipClubException(info);
            }
            FilmEntity userEntity = result.get(0);

            em.remove(userEntity);
            flushAndClear();
        } catch (FilipClubException f) {
            LOG.warn("FilmRepository deleteFilm: {}", f.getMessage());
            throw f;
        } catch (Exception e) {
            LOG.error("FilmRepository deleteFilm: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    private void flushAndClear() {
        em.flush();
        em.clear();
    }

}
