package com.bear.inventario.stepdefinitions;

import com.bear.inventario.dto.category.CreateCategoryDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class createNewCategoryStepDefinition {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String categoryName;

    private ResponseEntity<Void> responseEntity;
    @Given ("a new name like {string} for a category")
    public void given_new_category(String categoryName){
        this.categoryName = categoryName;
    }

    @When("the user use it in the save endpoint")
    public void when_user_use_in_save_endpoint() {
        String url = "http://localhost:" + port + "/api/v1/categories";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        CreateCategoryDTO requestDTO = new CreateCategoryDTO();
        requestDTO.setNombre(categoryName);

        HttpEntity<CreateCategoryDTO> requestEntity = new HttpEntity<>(requestDTO, headers);
        responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
    }

    @Then("a new category should be created")
    public void then_new_category_should_be_created() {
        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.CREATED);
    }
}
