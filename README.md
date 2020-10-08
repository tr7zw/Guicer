# Guicer

Guicer (pronounced 'juicer') is a wrapper/extension for [guice](https://github.com/google/guice) to help automate/decouple/streamline the guice binding process by scanning the classpath.

## Usage

Guicer can simply be setup by using

```Java
Guicer.setupInstance(new GuicerInjector());
Guicer.getHandler().addPackage("dev.tr7zw.example");
Guicer.getHandler().init();
```

to find all classes (recursivly) inside ``dev.tr7zw.example`` that are annotated with ``@Implementation`` or ``@EnabledModule`` and add them to guice.

Example:

```Java
@EnabledModule
public class SomeModule extends AbstractModule{

    @Override
    protected void configure() {
        binder().bind(String.class).annotatedWith(Names.named("somename")).toInstance("abc");
        binder().bind(String.class).annotatedWith(Names.named("othername")).toInstance("xyz");
    }

}
```

```Java
@Implementation(iface = SomeInterface.class)
public class SomeImplementation implements SomeInterface{

    @Override
    public int addValue(int a, int b) {
        return a + b;
    }

}
```

### Note

This project is still in an early stage, so features are missing(singletons for example) and can be requested via issue.
