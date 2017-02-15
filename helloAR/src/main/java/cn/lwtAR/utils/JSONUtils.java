package cn.lwtAR.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON工具类
 * @author zz
 *
 */
public class JSONUtils {

	private static final Gson gson = new Gson();
	
	/**
	 * 将JSON字符串转换成T对象并返回
	 * @param jsonStr JSON字符串
	 * @param clazz T对象的类
	 * @return T对象
	 */
	public static <T> T fromJson(String jsonStr, Class<? extends T> clazz){
		return gson.fromJson(jsonStr, clazz);
	}
	
	/**
	 * 将对象转换成JSON字符串并返回
	 * @param instance 对象
	 * @return JSON字符串
	 */
	public static <T> String toJsonStr(T instance){
		return gson.toJson(instance);
	}
	
	/**
	 *  将JSON字符串转换成T对象列表并返回
	 * @param jsonStr JSON字符串
	 * @param clazz T对象的类
	 * @return T对象列表
	 */
	public static <T> List<T> fromJsonForList(String jsonStr, Class<? extends T> clazz){
		List<T> list = gson.fromJson(jsonStr, new TypeToken<List<T>>(){}.getType());
		return list;
	}

	public static <T> List<T> fromJsonArray(String json, Class<? extends T> clazz){
		List<T> lst = new ArrayList<>();

		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for(final JsonElement elem : array){
			lst.add(new Gson().fromJson(elem, clazz));
		}

		return lst;
	}
}
