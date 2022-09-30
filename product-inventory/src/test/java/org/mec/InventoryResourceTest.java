package org.mec;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mec.config.ProductInventoryConfig;
import org.mec.model.ProductInventory;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class InventoryResourceTest {

    @Inject
    ProductInventoryConfig productInventoryConfig;
    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/products")
          .then()
             .statusCode(200)
             .body(is("Hello!"));
    }
    /*@Test
    public void getBySkuTest() {
         ProductInventory productInventory = given()
                .when().get("/products/123")
                .then()
                .statusCode(200)
                 .extract().body().as(ProductInventory.class);
        Assertions.assertEquals("KE-180", productInventory.getSku());
    }
    */
    @Test
    public void testLoadDataFullCatalog() {
        //Mockito.when(productInventoryConfig.fullCatalog()).thenReturn(true);
    }

}