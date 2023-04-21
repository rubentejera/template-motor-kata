package kata.example;

import io.vavr.control.Either;

import java.util.Map;

public class TemplateEngine {

    public Result<TransformError, String> transform(Map<String, String> tagDictionary, String inputText) {
        if (inputText == null) {
            return new Result<>(TransformError.NOT_INPUT_TEXT_FOUND);
        }
        if (tagDictionary == null) {
            return new Result<>(TransformError.NOT_DICTIONARY_FOUND);
        }
        Either.left("Marks not acceptable");

        for (String key : tagDictionary.keySet()) {
            String tag = "${" + key + "}";
            inputText = inputText.replace(tag, tagDictionary.get(key));
        }

        boolean hasMoreTags = inputText.contains("${");
        if (hasMoreTags) {
            return new Result<>(TransformError.NOT_FOUND_DICTIONARY_KEY);
        }
        return new Result<>(inputText);
    }


    public Either<TransformError, String> transform2(Map<String, String> tagDictionary, String inputText) {
        if (inputText == null) {
            return Either.left(TransformError.NOT_INPUT_TEXT_FOUND);
        }
        if (tagDictionary == null) {
            return Either.left(TransformError.NOT_DICTIONARY_FOUND);
        }
        Either.left("Marks not acceptable");

        for (String key : tagDictionary.keySet()) {
            String tag = "${" + key + "}";
            inputText = inputText.replace(tag, tagDictionary.get(key));
        }

        boolean hasMoreTags = inputText.contains("${");
        if (hasMoreTags) {
            return Either.left(TransformError.NOT_FOUND_DICTIONARY_KEY);
        }
        return Either.right(inputText);
    }


    public Either<TransformError, String> transform3(Map<String, String> tagDictionary, String inputText) {
        return checkInput(inputText)
                .flatMap(x -> checkTagDictionary(tagDictionary))
                .flatMap(x -> transformText(tagDictionary, inputText));
    }


    private Either<TransformError, String> checkInput(String inputText) {
        if (inputText == null) {
            return Either.left(TransformError.NOT_INPUT_TEXT_FOUND);
        }
        return Either.right(inputText);
    }


    private Either<TransformError, String> transformText(Map<String, String> tagDictionary, String inputText) {
        for (String key : tagDictionary.keySet()) {
            String tag = "${" + key + "}";
            inputText = inputText.replace(tag, tagDictionary.get(key));
        }

        boolean hasMoreTags = inputText.contains("${");
        if (hasMoreTags) {
            return Either.left(TransformError.NOT_FOUND_DICTIONARY_KEY);
        }
        return Either.right(inputText);
    }

    private Either<TransformError, Map<String, String>> checkTagDictionary(Map<String, String> tagDictionary) {
        if (tagDictionary == null) {
            return Either.left(TransformError.NOT_DICTIONARY_FOUND);
        }
        return Either.right(tagDictionary);
    }
}
