package bg.softuni.eliteSportsEquipment.model.entity.user;

import bg.softuni.eliteSportsEquipment.model.entity.BaseEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.FavouriteEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.OrderEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    private CartEntity cart;

    @OneToOne(cascade = CascadeType.ALL)
    private FavouriteEntity favourites;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderEntity> orders = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRoleEntity> roles = new HashSet<>();

    public UserEntity() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(Set<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public CartEntity getCart() {
        return cart;
    }

    public UserEntity setCart(CartEntity cart) {
        this.cart = cart;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserEntity setAddress(String city, String address) {
        this.address = setFullAddress(city, address);
        return this;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public UserEntity setOrders(List<OrderEntity> orders) {
        this.orders = orders;
        return this;
    }

    private String setFullAddress(String city, String address) {
        return String.format("%s, %s", city, address);
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (this.getFirstName() != null) {
            fullName.append(this.getFirstName());
        }
        if (this.getLastName() != null) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(this.getLastName());
        }

        return fullName.toString();
    }

    public FavouriteEntity getFavourites() {
        return favourites;
    }

    public UserEntity setFavourites(FavouriteEntity favourites) {
        this.favourites = favourites;
        return this;
    }
}
