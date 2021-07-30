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

import com.copart.b2b.event.processor.vo.EventInfoMessage;
import com.copart.b2b.eventpublisher.dao.ActionDao;
import com.copart.b2b.eventpublisher.entity.maria.EventsInfo;
import com.copart.b2b.eventpublisher.entity.seller.SellerEventMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MergeJson {
     @Autowired
    private ObjectMapper objectMapper;

  public static JsonNode merge(JsonNode mainNode, JsonNode updateNode) {
		Iterator<String> fieldNames = updateNode.fieldNames();
		while (fieldNames.hasNext()) {

			String fieldName = fieldNames.next();
			JsonNode jsonNode = mainNode.get(fieldName);
			// if field exists and is an embedded object
			if (jsonNode != null && jsonNode.isObject()) {
				merge(jsonNode, updateNode.get(fieldName));
			} else {
				if (mainNode instanceof ObjectNode) {
					// Overwrite field
					JsonNode value = updateNode.get(fieldName);
					((ObjectNode) mainNode).set(fieldName, value);//TODO test this piece
				}
			}
		}
		return mainNode;
	}

	/**
	 * Merges two Jsons where localJson has highest precedence.
	 * @param globalJson - nullable
	 * @param localJson - nullable
	 * @return Root node of Json which is JsonNode
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public JsonNode mergedJsonNode(String globalJson, String localJson)
			throws JsonMappingException, JsonProcessingException {
		if (StringUtils.isAllBlank(globalJson, localJson)) {
			return objectMapper.createObjectNode();
		} else if (StringUtils.isBlank(globalJson)) {
			return objectMapper.readTree(localJson);
		} else if (StringUtils.isBlank(localJson)) {
			return objectMapper.readTree(globalJson);
		} else {
			JsonNode inputNode1 = objectMapper.readTree(globalJson);
			JsonNode inputNode2 = objectMapper.readTree(localJson);
			return merge(inputNode1, inputNode2);
		}
	}

	/**
	 * Merges two Jsons where localJson has highest precedence.
	 * @param globalJson
	 * @param localJson
	 * @return Pretty formatted merged Json.
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public String mergedPrettyJson(String globalJson, String localJson)
			throws JsonMappingException, JsonProcessingException {
		return mergedJsonNode(globalJson, localJson).toPrettyString();
	}

	/**
	 * Merges two Jsons where localJson has highest precedence.
	 * @param globalJson
	 * @param localJson
	 * @return Merged unformatted Json
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public String mergedJson(String globalJson, String localJson) throws JsonMappingException, JsonProcessingException {
		return mergedJsonNode(globalJson, localJson).toString();
	}
}
