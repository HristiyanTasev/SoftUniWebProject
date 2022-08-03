package bg.softuni.eliteSportsEquipment.service.product;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDTO;
import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDetailDTO;
import bg.softuni.eliteSportsEquipment.model.entity.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.ProductMapper;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllProductsService {

    private final AllProductsRepository allProductsRepository;
    private final ProductMapper productMapper;
    private List<ProductEntity> featuredProducts = new ArrayList<>();

    public AllProductsService(AllProductsRepository allProductsRepository, ProductMapper productMapper) {
        this.allProductsRepository = allProductsRepository;
        this.productMapper = productMapper;
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

    public List<ProductDTO> getFeaturedProducts() {

        // return first 3 products in case featuredProducts is empty
        if (this.featuredProducts.size() != 3) {
            return this.allProductsRepository.findAll().subList(0, 3)
                    .stream()
                    .map(this.productMapper::productEntityToProductDTO)
                    .collect(Collectors.toList());
        }

        return this.featuredProducts
                .stream()
                .map(this.productMapper::productEntityToProductDTO)
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "@daily")
    public void generateFeaturedProducts() {
        List<ProductEntity> all = this.allProductsRepository.findAll();

        this.featuredProducts.clear();

        int max = all.size() - 1;
        int min = 1;
        int range = max - min + 1;

        for (int i = 0; i < 3; i++) {
            int productId = (int) (Math.random() * range) + min;
            ProductEntity productEntity = all.get(productId);
            if (this.featuredProducts.contains(productEntity)) {
                i--;
            } else {
                this.featuredProducts.add(productEntity);
            }
        }
    }
}
