/*      Licensed Materials - Property of Value Transformation Services
 *
 *      Restricted Materials of Value Transformation Services
 *
 *      (C) COPYRIGHT V-TS ScPA. 2018-2026 All Rights Reserved
 *
 *      Use, duplication or disclosure restricted by MSA Schedule Contract with Value Transformation Services.
 *      Value Transformation Services provides the Work on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 *      OF ANY KIND, either express or implied, if the Work is used without the direct control of Value
 *      Transformation Services. You are solely responsible for determining the appropriateness of using the
 *      Work and assume any risks associated with Your exercise.
 */
package luaycli;

import picocli.CommandLine;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class CliOptions {
    @CommandLine.Option(order = 99, names = {"-f", "--file"}, paramLabel = "FILE", description = "the luay file", required = false)
    public File infile;

    @CommandLine.Option(order = 50, names = "-D", description = "set options")
    public Properties options = new Properties();

    @CommandLine.Option(order = 20, names = {"-I", "--ini"}, description = "preconfigured data file", required = false)
    public File optFile;

    @CommandLine.Option(order = 99, names = {"-L"}, paramLabel = "path", description = "include path", required = false)
    public List<File> includePaths;

    @CommandLine.Parameters()
    public List<String> arguments;
}
