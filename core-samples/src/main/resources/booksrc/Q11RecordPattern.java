public record Coordinate(double lat, double lng) {}
public record GeoFence(Coordinate center, double radiusKm) {}
@@BLOCK@@
// C: ネストされたレコードの分解
Object obj = new GeoFence(new Coordinate(35.6762, 139.6503), 10.0);

if (obj instanceof GeoFence(Coordinate(var lat, var lng), var radius)) {
    // lat, lng, radius が直接使用可能
    System.out.printf("Center: (%.4f, %.4f), Radius: %.1f km%n", lat, lng, radius);
}

// D: アクセサメソッド不要
// 従来: geoFence.center().lat() → パターンマッチング: lat 変数で直接アクセス

// E: switch 式 + when ガード
String classify(Object shape) {
    return switch (shape) {
        case GeoFence(Coordinate(var lat, var lng), var r)
            when r > 100.0 -> "Large zone at (" + lat + ", " + lng + ")";
        case GeoFence(var c, var r)
            when r <= 100.0 -> "Small zone, radius=" + r;
        default -> "Unknown shape";
    };
}