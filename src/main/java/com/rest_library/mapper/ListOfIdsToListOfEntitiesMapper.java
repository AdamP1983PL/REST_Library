package com.rest_library.mapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class ListOfIdsToListOfEntitiesMapper {

    /*This method takes a list of IDs and a Class representing the type of entities you want to create.
    It uses reflection to instantiate instances of the specified entity type,
    sets their IDs using a method named setId, and returns a list of these entities.

    This assumes that your entities have a method named setId that accepts a Long.
    Adjust the method name if needed based on your entity structure.

    The use of reflection has its trade-offs and should be used cautiously.
    It's essential to handle any exceptions that might occur during the reflection process.

    The entityType.getDeclaredConstructor().newInstance() assumes that your entities have a default (no-argument) constructor.
    If your entities require constructor parameters, you may need to adapt this part accordingly.

    This method can be further refined based on your specific entity structure and requirements.
    If your entities share a common base class or interface, consider using that instead of a generic Class<?>.*/

    public List<?> mapIdToEntities(List<Long> ids, Class<?> entityType) {
        return ids.stream()
                .map(id -> {
                    try {
                        Object entity = entityType.getDeclaredConstructor().newInstance();
                        entity.getClass().getMethod("setId", Long.class).invoke(entity, id);
                        return entity;
                    } catch (Exception e) {
                        System.out.println("Something went wrong during mapping Ids list into Entities list.");
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }
}
