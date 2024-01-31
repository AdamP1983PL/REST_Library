package com.rest_library.mapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class IdToEntityMapperToBeDiscarded {

    /*
     * This method takes an ID and a Class representing the type of entity you want to create.
     * It uses reflection to instantiate an instance of the specified entity type,
     * sets its ID using a method named setId, and returns the entity.*/

    public Object mapIdToEntity(Long id, Class<?> entityType) {
        try {
            Object entity = entityType.getDeclaredConstructor().newInstance();
            entity.getClass().getMethod("setId", Long.class).invoke(entity, id);
            return entity;
        } catch (Exception e) {
            System.out.println("Something went wrong during mapping Id to Entity");
            e.printStackTrace();
            return null;
        }
    }

}
