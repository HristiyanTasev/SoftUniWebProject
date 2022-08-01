package bg.softuni.eliteSportsEquipment.model.entity;

import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import bg.softuni.eliteSportsEquipment.repository.FavouriteRepository;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "favourites")
public class FavouriteEntity extends BaseEntity{

    @ManyToMany
    private List<ProductEntity> products = new ArrayList<>();

    public FavouriteEntity() {
    }

    public FavouriteEntity(List<ProductEntity> products) {
        this.products = products;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public FavouriteEntity setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }
}
