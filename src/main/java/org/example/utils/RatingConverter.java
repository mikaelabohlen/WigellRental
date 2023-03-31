package org.example.utils;

import org.example.enums.Rating;

import javax.persistence.AttributeConverter;

public class RatingConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(final Rating attribute) {
        return attribute.getRating();
    }

    @Override
    public Rating convertToEntityAttribute(final String dbData) {
        return Rating.fromString(dbData);
    }
}
