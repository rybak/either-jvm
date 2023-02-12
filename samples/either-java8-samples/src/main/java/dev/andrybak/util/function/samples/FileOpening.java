// SPDX-License-Identifier: MIT
package dev.andrybak.util.function.samples;

import dev.andrybak.util.function.java8.Either;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class FileOpening {
	/**
	 * @param s filepath
	 * @return either an error message ({@link String}) indicating a problem with the given path,
	 * or a {@link Path} object
	 */
	public static Either<String, Path> checkPath(String s) {
		Path p;
		try {
			p = Paths.get(s);
		} catch (InvalidPathException e) {
			return Either.left("Invalid path: '" + e.getInput() + "'. " + e.getMessage());
		}
		if (Files.exists(p)) {
			if (Files.isRegularFile(p) && Files.isReadable(p)) {
				return Either.right(p);
			} else {
				return Either.left("Path " + p + " doesn't point to a readable file");
			}
		} else {
			return Either.left("File with path " + p + " doesn't exist");
		}
	}

	/**
	 * @param path a valid path for an existing text file
	 * @return either an error message, or the first line of the file
	 */
	public static Either<String, String> readFirstLine(Path path) {
		try (var reader = Files.newBufferedReader(path)) {
			return Optional.ofNullable(reader.readLine())
					.map(Either::<String, String>right)
					.orElseGet(() -> Either.left("File is empty"));
		} catch (IOException e) {
			return Either.left("Cannot read " + path + ". Got exception " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		Stream.of("input1.txt", "input2.txt")
				.forEach(path -> {
					Either<String, Path> errorOrCheckedPath = checkPath(path);
					Either<String, String> errorOrFirstLine = EitherMonad.bind(
							errorOrCheckedPath,
							FileOpening::readFirstLine
					);
					errorOrFirstLine.accept(System.err::println, System.out::println);
				});
	}
}
