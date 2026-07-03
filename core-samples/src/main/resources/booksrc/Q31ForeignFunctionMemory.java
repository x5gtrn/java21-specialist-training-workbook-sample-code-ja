// FFM API による C の strlen 関数の呼び出し
import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class NativeCallExample {
    public static void main(String[] args) throws Throwable {
        // 1. ネイティブリンカーとシンボルルックアップの取得
        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();

        // 2. ネイティブ関数の検索と MethodHandle 作成
        MethodHandle strlen = linker.downcallHandle(
            stdlib.find("strlen").orElseThrow(),
            FunctionDescriptor.of(
                ValueLayout.JAVA_LONG,  // 戻り値: size_t (long)
                ValueLayout.ADDRESS  // 引数: const char*
            )
        );

        // 3. オフヒープメモリの確保と使用
        try (Arena arena = Arena.ofConfined()) {
            // C 文字列をオフヒープに確保
            MemorySegment cString = arena.allocateUtf8String("Hello, FFM!");

            // ネイティブ関数呼び出し
            long length = (long) strlen.invoke(cString);
            System.out.println("Length: " + length);  // 11
        }
        // POINT: Arena スコープ終了 → オフヒープメモリ自動解放
    }
}

// JNI との比較
// JNI: Java → javah → C header → C 実装 → コンパイル → .so/.dll → ロード → 呼び出し
// FFM: Java → Linker.downcallHandle() → 呼び出し（C コード一切不要）