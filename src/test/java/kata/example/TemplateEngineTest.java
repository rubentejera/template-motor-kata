package kata.example;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateEngineTest {

    @Test
    void shouldReturnInputTextWhenItHasNoVariables() {
        TemplateEngine engine = new TemplateEngine();
        Map<String, String> tagDictionary = new HashMap<>();
        String inputText = "IRRELEVANT_TEXT";

        String output = engine.transform(tagDictionary, inputText).getValue();

        assertEquals(inputText, output);
    }

    @Test
    void shouldSubstituteASingleVariable() {
        TemplateEngine engine = new TemplateEngine();
        Map<String, String> tagDictionary = new HashMap<>();
        tagDictionary.put("variable", "foo");
        String inputText = "This is a template with one ${variable}";

        String output = engine.transform(tagDictionary, inputText).getValue();

        assertEquals("This is a template with one foo", output);
    }

    @Test
    void shouldSubstituteMoreThanOneVariables() {
        TemplateEngine engine = new TemplateEngine();
        Map<String, String> tagDictionary = new HashMap<>();
        tagDictionary.put("variable", "foo");
        tagDictionary.put("variable2", "foo2");
        String inputText = "This is a template with both ${variable} and ${variable2}";

        String output = engine.transform(tagDictionary, inputText).getValue();

        assertEquals("This is a template with both foo and foo2", output);
    }


    @Test
    void shouldNotUsedVariablesNotContainedTest() {
        TemplateEngine engine = new TemplateEngine();
        Map<String, String> tagDictionary = new HashMap<>();
        tagDictionary.put("variable", "foo");
        tagDictionary.put("variable2", "foo2");
        tagDictionary.put("variable3", "foo3");
        String inputText = "This is a template with both ${variable} and ${variable2}";

        String output = engine.transform(tagDictionary, inputText).getValue();

        assertEquals("This is a template with both foo and foo2", output);
    }

    @Test
    void shouldBeReturnTheSameTextIfNotFoundOnDictionary() {
        TemplateEngine engine = new TemplateEngine();
        Map<String, String> tagDictionary = new HashMap<>();
        tagDictionary.put("variable", "foo");
        String inputText = "This is a template with both ${variable} and ${variable2}";

        Result<TransformError, String> output = engine.transform(tagDictionary, inputText);

        assertEquals(TransformError.NOT_FOUND_DICTIONARY_KEY, output.getError());
    }

    @Test
    void shouldSubstituteMoreThanOnceTheSameVariable() {
        TemplateEngine engine = new TemplateEngine();
        Map<String, String> tagDictionary = new HashMap<>();
        tagDictionary.put("variable", "foo");
        String inputText = "This is a template with both ${variable} and ${variable}";

        String output = engine.transform2(tagDictionary, inputText).get();

        Result<TransformError, String> foo = engine.transform(tagDictionary, inputText);

        assertEquals("This is a template with both foo and foo", output);
    }

    @Test
    void shouldBeReturnTheSameTextIfDictionaryIsNull() { //TODO para mirar
        TemplateEngine engine = new TemplateEngine();
        String inputText = "This is a template with both ${variable} and ${variable}";

        Result<TransformError, String> output = engine.transform(null, inputText);

        assertEquals(TransformError.NOT_DICTIONARY_FOUND, output.getError());
    }

    @Test
    void shouldBeReturnNullIfInputTextIsNull() { // TODO para mirar
        TemplateEngine engine = new TemplateEngine();
        Map<String, String> tagDictionary = new HashMap<>();
        tagDictionary.put("variable", "foo");

        String output = engine.transform(tagDictionary, null).getValue();

        assertEquals(null, output);
    }

    @Test
    void shouldBeReturnValueIfIsEmptyKeyOnDictionary() { //TODO parar mirar
        TemplateEngine engine = new TemplateEngine();
        Map<String, String> tagDictionary = new HashMap<>();
        tagDictionary.put("", "foo");
        tagDictionary.put(" ", "foo2");
        String inputText = "This is a template with one ${} and other ${ }";

        String output = engine.transform(tagDictionary, inputText).getValue();

        assertEquals("This is a template with one foo and other foo2", output);
    }

    @Test
    void foo() { //TODO parar mirar
        TemplateEngine engine = new TemplateEngine();
        Map<String, String> tagDictionary = new HashMap<>();
        tagDictionary.put("variable", "foo");
        String inputText = "This is a template with one ${variable}}";

        String output = engine.transform(tagDictionary, inputText).getValue();

        assertEquals("This is a template with one foo", output);
    }

}