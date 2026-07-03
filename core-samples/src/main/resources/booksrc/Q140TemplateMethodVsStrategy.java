abstract class ImportTemplate {
    final ImportResult run(Input input) {
        validate(input);
        List<ParsedRecord> records = parse(input);
        persist(records);
        return publish(records);
    }

    protected abstract void validate(Input input);
    protected abstract List<ParsedRecord> parse(Input input);
}
@@BLOCK@@
interface ImportParser {
    List<ParsedRecord> parse(Input input);
}

class ImportService {
    ImportResult run(Input input, ImportParser parser, ImportValidator validator) {
        validator.validate(input);
        List<ParsedRecord> records = parser.parse(input);
        persist(records);
        return publish(records);
    }
}
@@BLOCK@@
@FunctionalInterface
interface Validator<T> {
    void validate(T value);
}

ImportService service = new ImportService();
service.run(input, csvParser, value -> {
    if (value.isEmpty()) throw new ValidationException();
});