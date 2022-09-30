package org.mec.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Entity
@RegisterForReflection //para evitar que la GraalVm se lo garche en compilacion nativa
/**
 * Patron Data Repository, agregar id autogenerado y crear clase repository
 */

public class ProductInventory {
    @Id @GeneratedValue Long id;
    @Null(groups = ValidationGroups.Put.class)
    @NotBlank(groups = ValidationGroups.Post.class)
    private String sku;
    private String category;

    @NotBlank(message = "Name is mandatory and should be provided")
    private String name;

    private int quantity;
    private String powerWatts;
    private String footprint;
    private BigDecimal manufacturingCost;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ProductLine productLine;

    @Convert(converter = ConsumerTypeConverter.class)
    private List<ConsumerType> targetConsumer = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ProductAvailability productAvailability;

    @PositiveOrZero
    private int unitsAvailable;

    public ProductInventory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPowerWatts() {
        return powerWatts;
    }

    public void setPowerWatts(String powerWatts) {
        this.powerWatts = powerWatts;
    }

    public String getFootprint() {
        return footprint;
    }

    public void setFootprint(String footprint) {
        this.footprint = footprint;
    }

    public BigDecimal getManufacturingCost() {
        return manufacturingCost;
    }

    public void setManufacturingCost(BigDecimal manufacturingCost) {
        this.manufacturingCost = manufacturingCost;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductLine getProductLine() {
        return productLine;
    }

    public void setProductLine(ProductLine productLine) {
        this.productLine = productLine;
    }

    public List<ConsumerType> getTargetConsumer() {
        return targetConsumer;
    }

    public void setTargetConsumer(List<ConsumerType> targetConsumer) {
        this.targetConsumer = targetConsumer;
    }

    public ProductAvailability getProductAvailability() {
        return productAvailability;
    }

    public void setProductAvailability(ProductAvailability productAvailability) {
        this.productAvailability = productAvailability;
    }

    public int getUnitsAvailable() {
        return unitsAvailable;
    }

    public void setUnitsAvailable(int unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }
}
