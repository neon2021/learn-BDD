# replace default name application.properties with special.properties 

refer to : https://docs.spring.io/spring-boot/docs/1.5.22.RELEASE/reference/html/boot-features-external-config.html#boot-features-external-config-application-property-files


## 24.3 Application property files

`SpringApplication` will load properties from `application.properties` files in the following locations and add them to the Spring `Environment`:

1. A `/config` subdirectory of the current directory.
2. The current directory
3. A classpath `/config` package
4. The classpath root

The list is ordered by precedence (properties defined in locations higher in the list override those defined in lower locations).

[Note]
> You can also use YAML ('.yml') files as an alternative to '.properties'.

If you don’t like `application.properties` as the configuration file name you can switch to another by specifying a `spring.config.name` environment property. You can also refer to an explicit location using the `spring.config.location` environment property (comma-separated list of directory locations, or file paths).

```bash
$ java -jar myproject.jar --spring.config.name=myproject
```
or
```bash
$ java -jar myproject.jar --spring.config.location=classpath:/default.properties,classpath:/override.properties
```

[Warning]
> `spring.config.name` and spring.config.location are used very early to determine which files have to be loaded so they have to be defined as an environment property (typically OS env, system property or command line argument).

If spring.config.location contains directories (as opposed to files) they should end in / (and will be appended with the names generated from spring.config.name before being loaded, including profile-specific file names). Files specified in spring.config.location are used as-is, with no support for profile-specific variants, and will be overridden by any profile-specific properties.

Config locations are searched in reverse order. By default, the configured locations are classpath:/,classpath:/config/,file:./,file:./config/. The resulting search order is:

1. file:./config/
2. file:./
3. classpath:/config/
4. classpath:/

When custom config locations are configured, they are used in addition to the default locations. Custom locations are searched before the default locations. For example, if custom locations classpath:/custom-config/,file:./custom-config/ are configured, the search order becomes:

1. file:./custom-config/
2. classpath:custom-config/
3. file:./config/
4. file:./
5. classpath:/config/
6. classpath:/

This search ordering allows you to specify default values in one configuration file and then selectively override those values in another. You can provide default values for you application in application.properties (or whatever other basename you choose with spring.config.name) in one of the default locations. These default values can then be overriden at runtime with a different file located in one of the custom locations.

[Note]
> If you use environment variables rather than system properties, most operating systems disallow period-separated key names, but you can use underscores instead (e.g. SPRING_CONFIG_NAME instead of spring.config.name).

[Note]
> If you are running in a container then JNDI properties (in java:comp/env) or servlet context initialization parameters can be used instead of, or as well as, environment variables or system properties.


## SpringBoot 1.5+ junit test does not load configuration with custom name

refer to : https://stackoverflow.com/a/50639864 

as @M.Deinum mentioned you should initialize property in @BeforeClass method in test class:

```java
@BeforeClass
public static void initProperties() {
    System.setProperty("spring.config.name", "myapp");
}
```

Good practice is to tidy up properties after the test, so that other tests are not affected by these properties

```java
@AfterClass
public static void clearProperties() {
    System.clearProperty("spring.config.name");
}
```
BUT THERE IS BETTER OPTION TO USE TEST PROPERTIES:

You should use application.yaml in your tests as well. Your dynamical configuration (e.g. DB credentials) shouldn't be placed in this config file. They should be passed to application via env property (hence 12 factor app principles).

During testing, there are various ways how to define this dynamical configuration. E.g.:

1. With above mentioned mechanism `@BeforeClass` + `System.setPRoperty` 
2. Or you would use additional test config file via `@TestProperties` annotation or `SpringBootTest.properties` attribute