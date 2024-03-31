import luaycli.MainCLI;

public class TestData
{
    public static void main(String[] args) {
        MainCLI.main(new String[] {"-I", "test.pdata", " ./luay-cli/src/test/testcli.lua"} );
    }
}
