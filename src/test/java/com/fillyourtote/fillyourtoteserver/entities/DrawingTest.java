package com.fillyourtote.fillyourtoteserver.entities;

import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

class DrawingTest {

    @Autowired
    private Validator validator;

    @Test
    void validDrawing() {
        Drawing drawing = new Drawing("Sunset", "sunset", "A beautiful sunset drawing", "http://example.com/sunset.png");
        var violations = validator.validate(drawing);
        assertTrue(violations.isEmpty(), "Expected no validation violations");
    }

    @Test
    void invalidDrawing_BlankFields() {
        Drawing drawing = new Drawing(" ", " ", "Some description", " ");
        var violations = validator.validate(drawing);

        assertEquals(2, violations.size(), "Expected 2 validation violations (name and imageUrl)");

        violations.forEach(v ->
                System.out.println(v.getPropertyPath() + " -> " + v.getMessage())
        );
    }

    @Test
    void equalsAndHashCode() {
        Drawing d1 = new Drawing("Tree", "tree", "Green tree", "http://example.com/tree.png");
        Drawing d2 = new Drawing("Tree", "tree", "Green tree", "http://example.com/tree.png");

        d1.setId(1L);
        d2.setId(1L);

        assertEquals(d1, d2, "Drawings with the same id should be equal");
        assertEquals(d1.hashCode(), d2.hashCode(), "Equal objects should have equal hash codes");
    }

    @Test
    void toStringContainsFields() {
        Drawing drawing = new Drawing("Flower", "flower", "Red rose", "http://example.com/rose.png");
        drawing.setId(5L);

        String result = drawing.toString();

        assertTrue(result.contains("Flower"));
        assertTrue(result.contains("Red rose"));
        assertTrue(result.contains("http://example.com/rose.png"));
        assertTrue(result.contains("5"));
    }
}
