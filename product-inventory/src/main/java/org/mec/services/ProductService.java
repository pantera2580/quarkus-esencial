package org.mec.services;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;
import org.mec.config.ProductInventoryConfig;
import org.mec.model.ConsumerType;
import org.mec.model.ProductAvailability;
import org.mec.model.ProductInventory;
import org.mec.model.ProductLine;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

@ApplicationScoped
public class ProductService {

    private static final Logger LOGGER = Logger.getLogger(ProductService.class);
    private Map<String, ProductInventory> inventory = new HashMap<>();
    @Inject
    ProductInventoryConfig productInventoryConfig;

    void onStart(@Observes StartupEvent startupEvent) {
        //this.loadData();
        System.out.println(">>> Start up !!!" + startupEvent.toString());
    }
    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("Product Inventory Service shutting down...");
    }
    /*
    void loadData() {
        inventory.clear();
        InputStream resourceAsStream = this.getClass().getClassLoader()
                .getResourceAsStream("KinetEco_product_inventory.csv");
        try {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
                String line;
                int id = 0;
                // Category,Name,Package Quantity,SKU,Power (Watts),Footprint (SQ FT),Manufacturing Cost,Suggested Retail,Product Line,Target Consumer,Availability,Units Available
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    ProductInventory productInventory = new ProductInventory();
                    productInventory.setCategory(values[0]);
                    productInventory.setName(values[1]);
                    productInventory.setQuantity(Integer.parseInt(values[2]));
                    productInventory.setSku(values[3]);
                    productInventory.setPowerWatts(values[4]);
                    productInventory.setFootprints(values[5]);
                    productInventory.setManufacturingCost(new BigDecimal(values[6].replace("$","")));
                    productInventory.setPrice(new BigDecimal(values[7].replace("$","")));
                    productInventory.setProductLine(ProductLine.valueOf(values[8].toUpperCase()));
                    List<ConsumerType> targetConsumers = new ArrayList<>();
                    String toUpperCaseConsumerTypes = values[9].toUpperCase();
                    for( ConsumerType consumerType: ConsumerType.values()) {
                        parseConsumerTypes(toUpperCaseConsumerTypes, consumerType, targetConsumers);
                    }
                    productInventory.setTargetConsumer(targetConsumers);
                    productInventory.setProductsAvailability(ProductAvailability.valueOf(values[10].replace(" ", "_").toUpperCase()));
                    productInventory.setUnitsAvailable(Integer.parseInt(values[11]));
                    if (productInventoryConfig.retrieveFullCatalog() || productInventory.getTargetConsumer().contains(ConsumerType.CORPORATE)) {
                        inventory.put(productInventory.getSku(), productInventory);
                    }
                    id++;
                }
            }
        } catch (Exception e) {
            LOGGER.info("Loaded " + inventory.size());
            LOGGER.error("Unable to load catalog.", e);
        }
    }

    private void parseConsumerTypes(String values, ConsumerType consumerType, List<ConsumerType> targetConsumers) {
        if (values.contains(consumerType.name())) {
            targetConsumers.add(consumerType);
        }
    }

    public ProductInventory getProductInventory(String sku) {
        var productInventory = new ProductInventory();
        productInventory.setSku(sku);
        productInventory.setName("KB-eco 180");
        productInventory.setProductsAvailability(ProductAvailability.IN_STOCK);
        productInventory.setQuantity(12);
        productInventory.setPrice(BigDecimal.valueOf(315.0));
        return productInventory;
    }

    public ProductInventory getBySku(String sku) {
        return inventory.get(sku);
    }

    public Collection<ProductInventory> listInventory() {
        return inventory.values();
    }

    public void addProductInventory(ProductInventory productInventory) {
        inventory.putIfAbsent(productInventory.getSku(), productInventory);
    }

    public ProductInventory updateProductInventory(String sku, ProductInventory productInventory) {
        if (inventory.get(sku) == null) {
            return null;
        }

        productInventory.setSku(sku);
        inventory.put(sku, productInventory);
        return productInventory;
    }

    public ProductInventory stockUpdate(String sku, Integer stock) {
        ProductInventory productInventory = inventory.get(sku);
        if (productInventory != null) {
            productInventory.setUnitsAvailable(productInventory.getUnitsAvailable() + stock);
        }
        return productInventory;
    }

    public void delete(String sku) {
        inventory.remove(sku);
    }
    */
}