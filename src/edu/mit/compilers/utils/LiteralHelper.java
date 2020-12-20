package edu.mit.compilers.utils;

public class LiteralHelper {
    public static long parseIntLiteral(String literalValue) {
        String rewrite = literalValue.toLowerCase();
        int radix = 10;
        for (int i = 0; i + 1 < rewrite.length(); ++i) {
            if (rewrite.charAt(i) == '0' && rewrite.charAt(i+1) == 'x') {
                radix = 16;
                rewrite = rewrite.substring(0, i) + rewrite.substring(i+2);
                break;
            }
        }
        return Long.parseLong(rewrite, radix);
    }

    public static char parseCharLiteral(String literalValue) {
        char value = literalValue.charAt(0);
        if (value == '\\') {
            switch (literalValue) {
                case "\\n":
                    value = '\n';
                    break;
                case "\\r":
                    value = '\r';
                    break;
                case "\\t":
                    value = '\t';
                    break;
                default:
                    value = literalValue.charAt(1);
                    break;
            }
        }
        return value;
    }

    public static <T> Literal<T> makeLiteralByValue(T value) {
        Literal<T> ret = new Literal<>(value.toString());
        // System.out.println(ret.getLiteralValue());
        ret.setValue(value);
        return ret;
    }
}
