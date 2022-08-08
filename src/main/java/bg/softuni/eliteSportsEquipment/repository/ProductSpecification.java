package bg.softuni.eliteSportsEquipment.repository;

import bg.softuni.eliteSportsEquipment.model.dto.SearchDTO;
import bg.softuni.eliteSportsEquipment.model.entity.product.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSpecification implements Specification<ProductEntity> {

    private final SearchDTO searchDTO;

    public ProductSpecification(SearchDTO searchDTO) {
        this.searchDTO = searchDTO;
    }

    @Override
    public Predicate toPredicate(Root<ProductEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {
        Predicate p = cb.conjunction();

        if (searchDTO.getType() != null && !searchDTO.getType().isEmpty()) {
            String type = searchDTO.getType().toLowerCase().trim();
            p.getExpressions().add(cb.and(cb.equal(root.get("type".toLowerCase()), type)));
        }

        if (searchDTO.getBrand() != null && !searchDTO.getBrand().isEmpty()) {
            String brand = searchDTO.getBrand().toLowerCase().trim();
            p.getExpressions().add(cb.and(cb.equal(root.get("brand".toLowerCase()), brand)));
        }

        if (searchDTO.getName() != null && !searchDTO.getName().isEmpty()) {
            String name = searchDTO.getName().toLowerCase().trim();
            p.getExpressions().add(cb.and(cb.equal(root.get("name".toLowerCase()), name)));
        }
        return p;
    }
}
