package com.igor.boardingmanager.controller.components;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonMergePatch;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import com.igor.boardingmanager.controller.patch.PatchMediaType;
@Component
public class JsonMergePatchHttpMessageConverter extends AbstractHttpMessageConverter<JsonMergePatch> {
	public JsonMergePatchHttpMessageConverter() {
	    super(PatchMediaType.APPLICATION_MERGE_PATCH, MediaType.APPLICATION_JSON);
	}
	@Override
	protected boolean supports(Class<?> clazz) {
		return JsonMergePatch.class.isAssignableFrom(clazz);
	}

	@Override
	protected JsonMergePatch readInternal(Class<? extends JsonMergePatch> clazz, HttpInputMessage inputMessage)	throws IOException, HttpMessageNotReadableException {
	    try (JsonReader reader = Json.createReader(inputMessage.getBody())) {
	        return Json.createMergePatch(reader.readValue());
	    } catch (Exception e) {
	        throw new HttpMessageNotReadableException(e.getMessage(), inputMessage);
	    }
	}

	@Override
	protected void writeInternal(JsonMergePatch t, HttpOutputMessage outputMessage)	throws IOException, HttpMessageNotWritableException {
        try (JsonWriter writer = Json.createWriter(outputMessage.getBody())) {
            writer.write(t.toJsonValue());
        } catch (Exception e) {
            throw new HttpMessageNotWritableException(e.getMessage(), e);
        }
	}

}
