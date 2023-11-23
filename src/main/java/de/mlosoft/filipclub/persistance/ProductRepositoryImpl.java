package de.mlosoft.filipclub.persistance;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.mlosoft.filipclub.entity.ProductEntity;
import de.mlosoft.filipclub.error.ErrorCode;
import de.mlosoft.filipclub.error.ErrorInfo;
import de.mlosoft.filipclub.error.FilipClubException;
import de.mlosoft.filipclub.util.LogUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
@Qualifier("productRepository")
public class ProductRepositoryImpl implements ProductRepository {

    private static final Logger LOG = LogUtil.getLogger();

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<ProductEntity> getAllProducts() {
        LOG.debug("ProductRepository - getAllProducts");
        Query query = em.createQuery("SELECT p FROM ProductEntity p");
        try {
            @SuppressWarnings("unchecked")
            List<ProductEntity> result = (List<ProductEntity>) query.getResultList();
            return result;
        } catch (Exception e) {
            LOG.error("ProductRepository getAllProducts: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public List<ProductEntity> findProductById(long productId) {
        LOG.debug("ProductRepository - findProductById - productId: {}", productId);
        Query query = em.createQuery("SELECT p FROM ProductEntity p WHERE p.productId=:productId")
                .setParameter("productId", productId);

        try {
            @SuppressWarnings("unchecked")
            List<ProductEntity> result = (List<ProductEntity>) query.getResultList();
            if (result.isEmpty()) {
                // no user found
                ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
                info.setAdditionalInfo("no user fount for productId:", String.valueOf(productId));
                throw new FilipClubException(info);
            }
            return result;
        } catch (FilipClubException f) {
            LOG.warn("ProductRepository findProductById {}", f.getMessage());
            throw f;
        } catch (Exception e) {
            LOG.error("ProductRepository findProductById: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public ProductEntity createProduct(ProductEntity Product) {

        LOG.debug("ProductRepository - createProduct: {}", Product.toString());
        try {
            ProductEntity ProductEntity = em.merge(Product);
            flushAndClear();
            return ProductEntity;
        } catch (Exception e) {
            LOG.error("ProductRepository createProduct: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public ProductEntity updateProduct(ProductEntity product, long productId) {

        ProductEntity productEntity;
        try {
            @SuppressWarnings("unchecked")
            List<ProductEntity> result = (List<ProductEntity>) em.createQuery(
                    "SELECT a FROM ProductEntity a WHERE a.productId = :productId")
                    .setParameter("productId", productId).getResultList();
            if (result.size() == 1) {

                // update role
                productEntity = result.get(0);
                productEntity.setName(product.getName());
                productEntity.setDescription(product.getDescription());
                productEntity.setActive(product.getActive());
                productEntity.setImagesUrl(product.getImagesUrl());

            } else {
                // no user found
                ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
                info.setAdditionalInfo("product not found for productId:", String.valueOf(productId));
                throw new FilipClubException(info);
            }
            flushAndClear();
            LOG.debug("ProductRepositoryImpl updateProduct return: {}", productEntity.toString());
            return productEntity;
        } catch (FilipClubException f) {
            LOG.warn("ProductRepository updateAccou: {}", f.getMessage());
            throw f;
        } catch (Exception e) {
            LOG.error("ProductRepository updateProduct: {}", e.getMessage());
            ErrorInfo info = new ErrorInfo(ErrorCode.DB_ERROR.name());
            info.setAdditionalInfo(ErrorCode.DB_ERROR.name(), e.getMessage());
            throw new FilipClubException(info, e);
        }
    }

    @Override
    @Transactional
    public void deleteProduct(long productId) {
        Query query = em.createQuery("SELECT a FROM ProductEntity a WHERE a.productId = :productId");
        query.setParameter("productId", productId);

        try {
            @SuppressWarnings("unchecked")
            List<ProductEntity> result = (List<ProductEntity>) query.getResultList();
            if (result.isEmpty()) {
                // no user found
                ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
                info.setAdditionalInfo("Product not found for productId:", String.valueOf(productId));
                throw new FilipClubException(info);
            }
            ProductEntity userEntity = result.get(0);

            em.remove(userEntity);
            flushAndClear();
        } catch (FilipClubException f) {
            LOG.warn("ProductRepository deleteProduct: {}", f.getMessage());
            throw f;
        } catch (Exception e) {
            LOG.error("ProductRepository deleteProduct: {}", e.getMessage());
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
