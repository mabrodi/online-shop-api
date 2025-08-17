package org.dimchik.web.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ProductRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldCreateValidProductRequest() {
        ProductRequest request = new ProductRequest();
        request.setName("Valid Product");
        request.setDescription("Valid Description");
        request.setPrice(10.0);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldDetectInvalidWhenNameIsBlank() {
        ProductRequest request = new ProductRequest();
        request.setName("");
        request.setDescription("Valid Description");
        request.setPrice(10.0);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Product name cannot be blank");
    }

    @Test
    void shouldDetectInvalidWhenNameIsTooLong() {
        ProductRequest request = new ProductRequest();
        request.setName("a".repeat(256));
        request.setDescription("Valid Description");
        request.setPrice(10.0);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Product name must be less than 255 characters");
    }

    @Test
    void shouldDetectInvalidWhenDescriptionIsBlank() {
        ProductRequest request = new ProductRequest();
        request.setName("Valid Product");
        request.setDescription("");
        request.setPrice(10.0);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Description cannot be blank");
    }

    @Test
    void shouldDetectInvalidWhenPriceIsNull() {
        ProductRequest request = new ProductRequest();
        request.setName("Valid Product");
        request.setDescription("Valid Description");
        request.setPrice(null);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Price cannot be null");
    }

    @Test
    void shouldDetectInvalidWhenPriceIsZeroOrNegative() {
        ProductRequest request = new ProductRequest();
        request.setName("Valid Product");
        request.setDescription("Valid Description");
        request.setPrice(0.0);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Price must be greater than 0");
    }

    @Test
    void gettersAndSettersShouldWorkCorrectly() {
        ProductRequest request = new ProductRequest();
        String name = "Test Product";
        String description = "Test Description";
        Double price = 99.99;

        request.setName(name);
        request.setDescription(description);
        request.setPrice(price);

        assertThat(request.getName()).isEqualTo(name);
        assertThat(request.getDescription()).isEqualTo(description);
        assertThat(request.getPrice()).isEqualTo(price);
    }

}