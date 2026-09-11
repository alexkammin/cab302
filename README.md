# Community Garden

## Running the shared tests

Install JDK 21 and set `JAVA_HOME` to its installation directory (not its `bin` directory).
Run commands from the repository folder containing `pom.xml`. The Maven wrapper
downloads the project's Maven version, so a separate Maven installation is not required.

Windows PowerShell:

```powershell
$env:JAVA_HOME = 'C:\path\to\your\jdk-21'
.\mvnw.cmd clean test
```

macOS / Linux:

```sh
export JAVA_HOME=/path/to/your/jdk-21
sh ./mvnw clean test
```

In IntelliJ, select JDK 21 for the project and Maven runner, then reload the Maven
project. Tests in `src/test/java` can also be run through IntelliJ's test runner.
Use the Maven command above to check the same suite as GitHub Actions.

## Where tests belong

Keep one shared set of Java tests under `src/test/java`, in directories matching
their package declarations. Move existing tests instead of copying them. Test
classes should have names ending in `Test` so Surefire discovers them automatically.

The consolidated suite contains 104 active test methods across 10 test classes.
The two previous `AccountTest` files are combined: three basic null-input checks
were replaced by the existing checks that also verify the exception messages.
The combined class preserves field checks and profile-update behaviour.

`GardenPlotTest.java` is a preserved, fully commented-out draft, and `GardenTest`
contains commented-out plot tests. These are unfinished code, not executed or
skipped JUnit tests, and are excluded from the active count. They must be adapted
to the implemented model before being enabled.

Check Maven's final `Tests run`, `Failures`, `Errors`, and `Skipped` totals.
Detailed reports are written to `target/surefire-reports`. The build fails if
Surefire discovers no tests. Compilation errors mean the test suite did not run.

GitHub Actions runs the shared suite on pushes and pull requests using Java 21.
