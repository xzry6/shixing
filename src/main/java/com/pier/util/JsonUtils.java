package com.pier.util;

import java.lang.reflect.Type;
import java.text.DateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

	public static String bean2json(Object bean) {
		Gson gson = new GsonBuilder().registerTypeAdapter(java.sql.Date.class,
				new SQLDateSerializer()).registerTypeAdapter(
				java.util.Date.class, new UtilDateSerializer()).setDateFormat(
				DateFormat.LONG).setPrettyPrinting().disableHtmlEscaping().create();
		return gson.toJson(bean);
	}

	public static <T> T json2bean(String json, Type type) {
		Gson gson = new GsonBuilder().registerTypeAdapter(java.sql.Date.class,
				new SQLDateDeserializer()).registerTypeAdapter(
				java.util.Date.class, new UtilDateDeserializer())
				.setDateFormat(DateFormat.LONG).disableHtmlEscaping().create();
		return gson.fromJson(json, type);
	}
	

}
