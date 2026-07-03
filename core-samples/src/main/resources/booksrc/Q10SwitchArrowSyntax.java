// ■ アロー形式: フォールスルーなし、式として使用可能
String seasonType = switch (month) {
    case MARCH, APRIL, MAY       -> "Spring";
    case JUNE, JULY, AUGUST      -> "Summer";
    case SEPTEMBER, OCTOBER, NOVEMBER -> "Autumn";
    case DECEMBER, JANUARY, FEBRUARY  -> "Winter";
};
// POINT: break 不要。各 case の右辺が自動的に switch 式の結果値になる。
// POINT: enum の全値がカバーされているため default 不要。

// ■ コロン形式: フォールスルーあり、break 必須
String seasonType;
switch (month) {
    case MARCH: case APRIL: case MAY:
        seasonType = "Spring";
        break;  // POINT: break を忘れると Summer に落ちるバグ
    case JUNE: case JULY: case AUGUST:
        seasonType = "Summer";
        break;
    // ...
    default:
        seasonType = "Unknown";
        break;
}

// ■ アロー形式で複数文が必要な場合 → ブロック + yield
int result = switch (status) {
    case "OK" -> 200;
    case "CREATED" -> 201;
    case "ERROR" -> {
        log.error("Error occurred");
        yield 500;  // POINT: ブロック内では yield で値を返す
    }
    default -> 0;
};

// POINT: アロー ≠ ラムダ
// -> in switch: case の実行（フォールスルーなし）
// -> in lambda: 関数の本体（Function, Consumer 等のインターフェース実装）
// 全く異なる概念だが構文が似ている