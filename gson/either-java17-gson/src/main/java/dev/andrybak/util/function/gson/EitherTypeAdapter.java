package dev.andrybak.util.function.gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import dev.andrybak.util.function.java17.Either;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Function;

public class EitherTypeAdapter<A, B> implements JsonSerializer<Either<A, B>>, JsonDeserializer<Either<A, B>> {
	private static final String TAG_KEY = "T";
	private static final String OBJECT_KEY = "O";

	private static final String LEFT_TAG = "L";
	private static final String RIGHT_TAG = "R";

	private final Type leftType;
	private final Type rightType;

	private EitherTypeAdapter(Type leftType, Type rightType) {
		this.leftType = leftType;
		this.rightType = rightType;
	}

	public static <A, B> GsonBuilder register(GsonBuilder gsonBuilder, TypeToken<Either<A, B>> eitherTypeToken) {
		Type eitherType = eitherTypeToken.getType();
		return gsonBuilder.registerTypeAdapter(
				eitherType,
				fromTypeToken(eitherTypeToken)
		);
	}

	public static <A, B> EitherTypeAdapter<A, B> fromTypeToken(TypeToken<Either<A, B>> eitherTypeToken) {
		Type eitherType = eitherTypeToken.getType();
		Type[] leftRightTypes = ((ParameterizedType) eitherType).getActualTypeArguments();
		return new EitherTypeAdapter<>(leftRightTypes[0], leftRightTypes[1]);
	}

	@Override
	public JsonElement serialize(Either<A, B> src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add(TAG_KEY, new JsonPrimitive(src.<String>match(
				ignored -> LEFT_TAG,
				ignored -> RIGHT_TAG
		)));
		result.add(OBJECT_KEY, context.serialize(src.match(
				Function.identity(),
				Function.identity()
		)));
		return result;
	}

	@Override
	public Either<A, B> deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException
	{
		if (!json.isJsonObject()) {
			throw new JsonParseException("Cannot parse " + json + " into Either");
		}
		JsonObject jsonObject = json.getAsJsonObject();
		JsonElement tag = jsonObject.get(TAG_KEY);
		if (tag == null || !tag.isJsonPrimitive()) {
			throw new JsonParseException("Cannot parse tag in " + json);
		}
		JsonElement object = jsonObject.get(OBJECT_KEY);
		return switch (tag.getAsString()) {
			case LEFT_TAG -> Either.left(context.deserialize(object, leftType));
			case RIGHT_TAG -> Either.right(context.deserialize(object, rightType));
			default -> throw new JsonParseException("Cannot parse tag in " + json);
		};
	}
}
