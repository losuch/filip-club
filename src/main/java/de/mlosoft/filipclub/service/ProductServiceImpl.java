package de.mlosoft.filipclub.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import de.mlosoft.filipclub.entity.ProductEntity;
import de.mlosoft.filipclub.error.ErrorCode;
import de.mlosoft.filipclub.error.ErrorInfo;
import de.mlosoft.filipclub.error.FilipClubException;
import de.mlosoft.filipclub.model.Product;
import de.mlosoft.filipclub.persistance.ProductRepository;
import de.mlosoft.filipclub.util.DozerHelper;
import de.mlosoft.filipclub.util.LogUtil;

@Service
@Qualifier("productService")
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LogUtil.getLogger();

    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> response = productRepository.getAllProducts();

        if (response.isEmpty()) {
            // user not found
            ErrorInfo info = new ErrorInfo(ErrorCode.USER_NOT_FOUND.name());
            throw new FilipClubException(info);
        }

        List<Product> products = DozerHelper.map(mapper, response, Product.class);
        LOG.debug("ProductService getAllProducts: {}", products.toString());
        return products;
    }

    @Override
    public Product getProductById(long productId) {
        List<ProductEntity> result = productRepository.findProductById(productId);

        if (result.size() == 1) {
            return mapper.map(result.get(0), Product.class);
        }
        ErrorInfo info = new ErrorInfo(ErrorCode.UNKNOWN_ERROR.name());
        info.setAdditionalInfo("found more then one product for productId:", String.valueOf(productId));
        throw new FilipClubException(info);
    }

    @Override
    public Product createProduct(Product product) {

        ProductEntity entityRequest = mapper.map(product, ProductEntity.class);
        ProductEntity entityResponse = productRepository.createProduct(entityRequest);
        return mapper.map(entityResponse, Product.class);
    }

    @Override
    public Product updateProduct(Product product, long productId) {
        ProductEntity productEntity = mapper.map(product, ProductEntity.class);
        ProductEntity productEntityResponse = productRepository.updateProduct(productEntity, productId);
        return mapper.map(productEntityResponse, Product.class);
    }

    @Override
    public void deleteProduct(long productId) {
        productRepository.deleteProduct(productId);
    }
}
