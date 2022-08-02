package bg.softuni.eliteSportsEquipment.service.product;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDTO;
import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDetailDTO;
import bg.softuni.eliteSportsEquipment.model.entity.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllProductsService {

    private final AllProductsRepository allProductsRepository;

    public AllProductsService(AllProductsRepository allProductsRepository) {
        this.allProductsRepository = allProductsRepository;
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> allProductEntities = this.allProductsRepository.findAll();

        return allProductEntities
                .stream()
                .map(productEntity -> new ProductDTO(
                        productEntity.getId(),
                        productEntity.getName(),
                        productEntity.getPrice(),
                        productEntity.getType()))
                .collect(Collectors.toList());
    }

    public ProductDetailDTO getProductById(Long productId) {
        ProductEntity productEntity = this.allProductsRepository.findById(productId).orElseThrow();

        ProductDetailDTO product = new ProductDetailDTO(
                productEntity.getId(),
                productEntity.getType(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getDescription());

        if (!productEntity.getType().equals("strap")) {
            product.setSize(Arrays.stream(SizeEnum.values()).map(Enum::name).collect(Collectors.toList()));
        }

        return product;
    }
}
