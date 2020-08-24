package JunitTest.TestTool;

import antlr.Token;

public class FakeToken extends Token {
    private String text;

    public FakeToken(int var1, String var2) {
        super(var1);
        this.text = var2;
    }

    @Override
    public String getText() {
        return text;
    }
}
