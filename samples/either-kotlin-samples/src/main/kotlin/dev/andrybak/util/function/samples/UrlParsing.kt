// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.samples

import dev.andrybak.util.function.kotlin.Either
import java.net.MalformedURLException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL

fun parseUrl(s: String?): Either<String, URL> {
	if (s == null) {
		return Either.left("Cannot parse null as a URL")
	}
	return try {
		val res: URL = URI(s).toURL()
		Either.right(res);
	} catch (e: URISyntaxException) {
		Either.left("Cannot parse '" + s + "'. Got exception: " + e.message)
	} catch (e: IllegalArgumentException) {
		Either.left("Cannot parse a relative URL: '" + s + "'. Got exception: " + e.message)
	} catch (e: MalformedURLException) {
		Either.left("Cannot parse malformed URL '" + s + "'. Got exception: " + e.message)
	}
}

fun main() {
	val urlStrings: List<String?> = listOf(
		null,
		"",
		"example/foo/bar",
		"https:///\\example.org",
		"ftp:///example-example.org",
		"file:///example-example/org",
		"hps://example.net",
		"https://example.net",
		"https://example.org/foo/bar"
	)
	for (s in urlStrings) {
		println(parseUrl(s))
	}
}
