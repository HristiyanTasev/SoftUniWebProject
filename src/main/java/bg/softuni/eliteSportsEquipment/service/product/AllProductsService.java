package bg.softuni.eliteSportsEquipment.service.product;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDTO;
import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDetailDTO;
import bg.softuni.eliteSportsEquipment.model.entity.product.BeltEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.SleeveEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.StrapEntity;
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
                        productEntity.getType(),
                        productEntity.getPicture().getUrl()))
                .collect(Collectors.toList());
    }

    public ProductDetailDTO getProductById(Long productId) {
        ProductEntity productEntity = this.allProductsRepository.findById(productId).orElseThrow();

        ProductDetailDTO product = this.productMapper.productEntityToProductDetailDTO(productEntity);

        if (!productEntity.getType().equals("strap")) {
            product.setSize(Arrays.stream(SizeEnum.values()).map(Enum::name).collect(Collectors.toList()));
        }

        switch (productEntity.getType()) {
            case "belt" -> {
                BeltEntity belt = (BeltEntity) productEntity;
                product.setBeltLeverType(belt.getLeverType().toString());
                product.setBeltMaterialType(belt.getMaterialType().toString());
            }
            case "strap" -> {
                StrapEntity strap = (StrapEntity) productEntity;
                product.setStrapType(strap.getStrapType().toString());
            }
            case "sleeve" -> {
                SleeveEntity sleeve = (SleeveEntity) productEntity;
                product.setSleeveType(sleeve.getSleeveType().toString());
            }
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
