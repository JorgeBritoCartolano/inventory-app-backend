package inventory.service.impl;


import inventory.dto.ProductCreateDTO;
import inventory.dto.ProductResponseDTO;
import inventory.mapper.ProductMapper;
import inventory.model.Product;
import inventory.repository.ProductRepository;
import inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO) {
        validatePriceAndStock(productCreateDTO.getPrice(), productCreateDTO.getStock());
        Product product = productRepository.save(ProductMapper.toEntity(productCreateDTO));
        return ProductMapper.toDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductCreateDTO productCreateDTO) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        validatePriceAndStock(productCreateDTO.getPrice(), productCreateDTO.getStock());

        Product product = ProductMapper.toEntity(productCreateDTO);
        product.setId(id);
        productRepository.save(product);

        return ProductMapper.toDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return ProductMapper.toDTO(product);
    }

    private void validatePriceAndStock(double price, int stock){
        if(price < 0){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Price must not be a negative number.");
        }
        if(stock < 0){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Stock must not be a negative number.");
        }
    }
}

