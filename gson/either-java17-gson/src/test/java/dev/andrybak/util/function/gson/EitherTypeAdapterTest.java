package dev.andrybak.util.function.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import dev.andrybak.util.function.java17.Either;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TODO add test with custom types {@code Either<Foo, Bar>}
 */
class EitherTypeAdapterTest {
	private static final TypeToken<Either<String, Integer>> PRIMITIVE_TYPE_TOKEN = new TypeToken<>() {};
	private static final Gson PRIMITIVE_GSON = getGson(new TypeToken<Either<String, Integer>>() {});
	private static final DateTimeFormatter CALENDAR_DAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	// TODO maybe move to EitherTypeAdapter
	private static <A, B> Gson getGson(TypeToken<Either<A, B>> typeToken) {
		return EitherTypeAdapter.register(new GsonBuilder(), typeToken).create();
	}

	private static void testPrimitiveGsonRoundTrip(Either<String, Integer> e) {
		testGsonRoundTrip(PRIMITIVE_GSON, PRIMITIVE_TYPE_TOKEN, e);
	}

	private static <A, B> void testGsonRoundTrip(Gson gson, TypeToken<Either<A, B>> typeToken, Either<A, B> e) {
		Type type = typeToken.getType();
		String json = gson.toJson(e, type);
		Either<A, B> deserialized = gson.fromJson(json, type);
		assertEquals(e, deserialized);
	}

	private static <A, B> Gson getCustomGson(TypeToken<Either<A, B>> eitherToken) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Type localDateType = new TypeToken<LocalDate>() {}.getType();
		gsonBuilder.registerTypeAdapter(localDateType, LocalDateJsonAdapter.INSTANCE);
		EitherTypeAdapter.register(gsonBuilder, eitherToken);
		return gsonBuilder.create();
	}

	@Test
	void testLeftInGson() {
		Either<String, Integer> leftValue = Either.left("hello");
		testPrimitiveGsonRoundTrip(leftValue);
	}

	@Test
	void testRightInGson() {
		Either<String, Integer> rightValue = Either.right(42);
		testPrimitiveGsonRoundTrip(rightValue);
	}

	@Test
	void testLeftInCustomGson() {
		TypeToken<Either<LocalDate, String>> leftLocalDateToken = new TypeToken<>() {};
		Either<LocalDate, String> leftValue = Either.left(LocalDate.of(2023, 11, 27));
		testGsonRoundTrip(getCustomGson(leftLocalDateToken), leftLocalDateToken, leftValue);
	}

	@Test
	void testRightInCustomGson() {
		TypeToken<Either<String, LocalDate>> rightLocalDateToken = new TypeToken<>() {};
		Either<String, LocalDate> rightValue = Either.right(LocalDate.of(2023, 11, 27));
		testGsonRoundTrip(getCustomGson(rightLocalDateToken), rightLocalDateToken, rightValue);
	}

	private enum LocalDateJsonAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
		INSTANCE;

		@Override
		public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException
		{
			try {
				return LocalDate.parse(json.getAsString(), CALENDAR_DAY_FORMATTER);
			} catch (DateTimeParseException e) {
				throw new JsonParseException(e);
			}
		}

		@Override
		public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(CALENDAR_DAY_FORMATTER.format(src));
		}
	}
}