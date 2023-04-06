package org.example.utils;

import org.example.enums.Rating;

import javax.persistence.AttributeConverter;

public class RatingConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(final Rating attribute) {
        if(attribute==null) {
            return null;
        }
        return attribute.getRating();
    }

    @Override
    public Rating convertToEntityAttribute(final String dbData) {
        return Rating.fromString(dbData);
    }
}
