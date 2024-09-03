package inventory.mapper;

import inventory.dto.ProductCreateDTO;
import inventory.dto.ProductResponseDTO;
import inventory.model.Product;
import org.modelmapper.ModelMapper;

public class ProductMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static ProductResponseDTO toDTO(Product product) {
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public static Product toEntity(ProductCreateDTO dto) {
        return modelMapper.map(dto, Product.class);
    }
}
