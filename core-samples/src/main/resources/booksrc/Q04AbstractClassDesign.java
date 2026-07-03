public abstract class DocumentConverter {
    // POINT: Template Method: 変換ワークフローを固定（final → サブクラスでオーバーライド不可）
    public final Document convert(byte[] input) {
        log.info("Starting conversion...");
        RawContent raw = parse(input);  // フック1: サブクラスが実装
        ProcessedContent processed = transform(raw);  // フック2: サブクラスが実装
        Document result = render(processed);  // フック3: サブクラスが実装
        log.info("Conversion complete: {} pages", result.pageCount());
        return result;
    }

    // POINT: Abstract hooks: サブクラスに実装を強制
    protected abstract RawContent parse(byte[] input);
    protected abstract ProcessedContent transform(RawContent raw);
    protected abstract Document render(ProcessedContent content);

    // POINT: Optional hook（オプション）: デフォルト実装あり、必要に応じてオーバーライド
    protected boolean shouldCompress() { return false; }
}

public class PdfConverter extends DocumentConverter {
    @Override protected RawContent parse(byte[] input) {
        return PdfParser.parse(input);  // PDF 固有のパース処理
    }
    @Override protected ProcessedContent transform(RawContent raw) {
        return PdfTransformer.transform(raw);
    }
    @Override protected Document render(ProcessedContent content) {
        return PdfRenderer.render(content);
    }
}

// 使用側: ワークフロー（convert）は同じ、各ステップの実装だけが異なる
DocumentConverter converter = new PdfConverter();
Document doc = converter.convert(rawBytes);
// → parse(PDF 方式) → transform(PDF 方式) → render(PDF 方式) の順序は固定