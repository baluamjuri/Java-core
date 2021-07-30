import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FindObjectEmptyOrNot {
    @Autowired
    private ObjectMapper objectMapper;

    public static Class<?> wrappersPrimitivesDate[] =
            { Number.class, CharSequence.class, Boolean.class, Long.TYPE, Integer.TYPE, Double.TYPE, Float.TYPE,
                    Boolean.TYPE, Date.class, Byte.class, Byte.TYPE };
  /**
     * Checks whether an object is empty or not.
     *
     * @param obj
     * @return true if empty else false
     * @see {@link Utils#isEmpty(Object, boolean)}
     */
    public static boolean isEmpty(Object obj) {
        return isEmpty(obj, false);
    }

    /**
     * Checks Object emptiness in deep such as all the complex type objects and each collection element as well
     *
     * @param obj
     * @return true if empty else false
     * @see {@link Utils#isEmpty(Object, boolean)}
     * @see {@link Utils#isEmpty(Object)}
     */
    public static boolean areLeavesEmpty(Object obj) {
        return isEmpty(obj, true);
    }

    /**
     * Checks whether an object is empty or not
     *
     * @param obj
     * @param leafCheck - checks all the simple and complex objects
     * @return true if empty else false
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static boolean isEmpty(Object obj, boolean leafCheck) {
        if (obj == null)
            return true;

        Class<? extends Object> objClazz = obj.getClass();

        if (Collection.class.isAssignableFrom(objClazz)) {
            Collection collection = (Collection) obj;
            if (!leafCheck)
                return collection.isEmpty();
            else if (!collection.isEmpty()) {
                return collection.stream().allMatch(e -> isEmpty(e, leafCheck));
            }
        }
        else if (Map.class.isAssignableFrom(objClazz)) {
            Map map = (Map) obj;
            if (!leafCheck)
                return map.isEmpty();
            else if (!map.isEmpty()) {
                Collection collection = map.values();
                return isEmpty(collection, leafCheck);
            }
        }
        else {
            for (Field f : objClazz.getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    if (f.get(obj) != null) {
                        if (leafCheck) {
                            Class<?> fieldType = f.getType();
                            boolean isWrapperOrPrimitiveOrDate = Arrays.stream(wrappersPrimitivesDate).anyMatch(
                                    clazz -> clazz.isAssignableFrom(fieldType));
                            if (isWrapperOrPrimitiveOrDate) {
                                if (!"serialVersionUID".equals(f.getName()))
                                    return false;
                            }
                            else if (!isEmpty(f.get(obj), leafCheck)) {
                                return false;
                            }
                        }
                        else {
                            return false;
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.error("Unable to check the emptiness of the object!");
                    return false;
                }
            }
        }
        return true;
    }


}
