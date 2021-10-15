import com.google.inject.AbstractModule;

public class ParameterModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BaseClass.class).annotatedWith(FileAnnotation.class).to(LoggerToFile.class);
        bind(BaseClass.class).annotatedWith(ConsoleAnnotation.class).to(LoggerToConsole.class);
    }
}
