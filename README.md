# Playframework 1.3.1 で Guice 4.0を使う

## 気持ち
- LoggerとかGsonとかのインスタンス生成書きたくない。勝手に作って突っ込んで欲しい。
- play の module で落ちてくるGuiceのバージョンがマキシマム古い。 https://www.playframework.com/modules/guice

## 導入
### playframeworkプロジェクト側の設定

- 依存性管理ファイルにGuice 4.0のMaven repositoryを指定

```yaml:conf/dependencies.yml
require:
    - play
    - com.google.inject -> guice 4.0
```

playで依存性解決

```bash
~/work/play_guice# play dependencies
~        _            _
~  _ __ | | __ _ _  _| |
~ | '_ \| |/ _' | || |_|
~ |  __/|_|\____|\__ (_)
~ |_|            |__/
~
~ play! 1.3.1, https://www.playframework.com
~
~ Resolving dependencies using /home/takeda/work/play_guice/conf/dependencies.yml,
~
~       com.google.inject->guice 4.0 (from mavenCentral)
~       aopalliance->aopalliance 1.0 (from mavenCentral)
~       com.google.guava->guava 16.0.1 (from mavenCentral)
~       org.ow2.asm->asm 5.0.3 (from mavenCentral)
~       cglib->cglib 3.1 (from mavenCentral)
~
~ Downloading required dependencies,
~
~       downloaded https://repo1.maven.org/maven2/com/google/inject/guice/4.0/guice-4.0-sources.jar
~       downloaded https://repo1.maven.org/maven2/com/google/inject/guice/4.0/guice-4.0.jar
~       downloaded https://repo1.maven.org/maven2/aopalliance/aopalliance/1.0/aopalliance-1.0.jar
~       downloaded https://repo1.maven.org/maven2/com/google/guava/guava/16.0.1/guava-16.0.1.jar
~       downloaded https://repo1.maven.org/maven2/org/ow2/asm/asm/5.0.3/asm-5.0.3.jar
~       downloaded https://repo1.maven.org/maven2/cglib/cglib/3.1/cglib-3.1.jar
~
~ Some dependencies have been evicted,
~
~       javax.inject 1 is overriden by javax.inject 1.0
~
~ Installing resolved dependencies,
~
~       lib/guice-4.0.jar
~       lib/aopalliance-1.0.jar
~       lib/guava-16.0.1.jar
~       lib/asm-5.0.3.jar
~       lib/cglib-3.1.jar
~
~ Done!
```
## Guiceについて

### 依存性注入
Moduleで注入するものとされるものの対応を定義する。Injectorが依存性を解決してインスタンスを生成する。
InjectorにModuleを食わせることで、Injectorに依存性解決方法を指定する。

#### 例.基本
HogeInterfaceにFugaを注入する。

```HogeInterface.java
public interface HogeInterface {
    public String iam();
}
```

```Fuga.java
public class Fuga implements HogeInterface {
    @Override
    public String iam(){
	return "Fuga";
    }
}
```

```Module
public class HogeModule extends AbstractModule {
    @Override
    protected void configure(){
	bind(HogeInterface.class).to(Fuga.class);
    }
}
```

```Injector
public class ShowHoge extends Controller {

    public static void index() {
	Injector injector = Guice.createInjector(new HogeModule());
	HogeInterface hoge = injector.getInstance(HogeInterface.class);
	String hogeIs = hoge.iam();
        render(hogeIs);
    }
}
```

### 注入方法
#### コンストラクタインジェクション
HogeServiceのHogeフィールドに対してFugaを注入する。
Hogeフィールドの注入はHogeServiceのコンストラクタで行う

``` HogeService
public class HogeService {
    private HogeInterface hoge;

    @Inject
    public HogeService(HogeInterface hoge){
	this.hoge = hoge;
    }

    public String getHogeName(){
	return hoge.name();
    }
}
```

```Module
@Override
protected void configure(){
    bind(HogeInterface.class).to(Fuga.class);
}
```

```HogeServiceのインスタンス化と呼び出し
	Injector injector = Guice.createInjector(new HogeModule());
	HogeService service = injector.getInstance(HogeService.class);
	String hogeIs = service.getHogeName();
```

#### フィールドインジェクション
HogeSerivceのHogeフィールドに対してFugaを注入する。
この注入指定をメンバ変数フィールドで行う。

```HogeService
public class HogeService {
    @Inject
    private HogeInterface hoge;

    public String getHogeName(){
	return hoge.name();
    }
}
```


```HogeModule
public class HogeModule extends AbstractModule {
    @Override
    protected void configure(){
	bind(HogeInterface.class).to(Fuga.class);
}
```

```HogeSerivceのインスタンス化と呼び出し
	Injector injector = Guice.createInjector(new HogeModule());
	HogeService service = injector.getInstance(HogeService.class);
	String hogeIs = service.getHogeName();
```

#### メソッドインジェクション

```HogeService
public class HogeService {
    private HogeInterface hoge;
    
    @Inject
    public void setHogeName(HogeInterface hoge) {
		Logger.info("Hoge injection via method");
        this.hoge = hoge;
    }
}
```


### 注入内容の判別
#### BindingAnnotationでの修飾による注入内容の判別
HogeSerivceのHogeフィールドに対してFugaを注入する。
また、もうひとつのHoge型フィールドに対して、BidingAnnotationを用いて注入内容にFooのインスタンスを指定する。

```HogeService
public class HogeService {
    @Inject
    private HogeInterface hoge;
    @Inject @AnotherHoge
    private HogeInterface anotherHoge;

    public String getHogeName(){
	return hoge.name();
    }

    public String getAnotherHogeName(){
	return anotherHoge.name();
    }
}
```

```AnotherHoge.java
import com.google.inject.BindingAnnotation;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@BindingAnnotation @Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
public @interface AnotherHoge {}
```

```HogeModule
public class HogeModule extends AbstractModule {
    @Override
    protected void configure(){
	bind(HogeInterface.class).to(Fuga.class);

    // @AnotherHoge で修飾されているものには、Foo.classを注入する。
	bind(HogeInterface.class)
	    .annotatedWith(AnotherHoge.class)
	    .to(Foo.class);
    }
}
```

```HogeSerivceのインスタンス化と呼び出し
	Injector injector = Guice.createInjector(new HogeModule());
	HogeService service = injector.getInstance(HogeService.class);
	String hogeIs = service.getHogeName();
	String anotherHogeIs = service.getAnotherHogeName();
```

#### @Namedに記述文字列による注入対象の判別

```HogeService.java
    @Inject
    private HogeInterface hoge;
    
    @Inject @Named("Another")
    private HogeInterface anotherHoge;

    public String getHogeName(){
	return hoge.name();
    }

    public String getAnotherHogeName(){
	return anotherHoge.name();
    }
```

```HogeModule.java
public class HogeModule extends AbstractModule {
    @Override
    protected void configure(){
	bind(HogeInterface.class).to(Fuga.class);

	bind(HogeInterface.class)
	    .annotatedWith(Names.named("Another"))
	    .to(Foo.class);
    }
}
```

### 注入インスタンスの作成方法指定
#### @Providesメソッドによるインスタンスの作成

```HogeService.java
public class HogeService {
    @Inject
    private HogeInterface hoge;
    
    public String getHogeName(){
	    return hoge.name();
    }
}
```

```呼び出しとインスタンス化
	Injector injector = Guice.createInjector(new HogeModule());
	HogeService service = injector.getInstance(HogeService.class);
	String hogeIs = service.getHogeName();
```

```HogeModule.java
public class HogeModule extends AbstractModule {
    @Override
    protected void configure(){
    }

    @Provides
    HogeInterface provideVariableFuga(){
	VariableFuga vFuga = new VariableFuga("Variable");
	    return vFuga;
    }
}
```

```VariableHoge.java
public class VariableFuga implements HogeInterface {
    private String variable;

    public VariableFuga(String variable){
	this.variable = variable;
    }
    
    @Override
    public String name(){
	return this.variable + " Fuga";
    }
}
```

なお、注入対象の指定方法が重複しているとModule使用時に例外

```HogeModule.java
public class HogeModule extends AbstractModule {
    @Override
    protected void configure(){
	bind(HogeInterface.class).to(Fuga.class);
    }

    @Provides
    HogeInterface provideVariableFuga(){
	VariableFuga vFuga = new VariableFuga("Variable");
	return vFuga;
    }
}

```


```発生例外
play.exceptions.JavaExecutionException: Unable to create injector, see the following errors:

1) A binding to log.HogeInterface was already configured at binding.HogeModule.configure(HogeModule.java:15).
at binding.HogeModule.provideVariableFuga(Unknown Source)

1 error
at play.mvc.ActionInvoker.invoke(ActionInvoker.java:228)
at Invocation.HTTP Request(Play!)
Caused by: com.google.inject.CreationException: Unable to create injector, see the following errors:

1) A binding to log.HogeInterface was already configured at binding.HogeModule.configure(HogeModule.java:15).
at binding.HogeModule.provideVariableFuga(Unknown Source)

1 error
at com.google.inject.internal.Errors.throwCreationExceptionIfErrorsExist(Errors.java:466)
at com.google.inject.internal.InternalInjectorCreator.initializeStatically(InternalInjectorCreator.java:155)
at com.google.inject.internal.InternalInjectorCreator.build(InternalInjectorCreator.java:107)
at com.google.inject.Guice.createInjector(Guice.java:96)
at com.google.inject.Guice.createInjector(Guice.java:73)
at com.google.inject.Guice.createInjector(Guice.java:62)
at controllers.ShowHoge.index(ShowHoge.java:18)
at play.mvc.ActionInvoker.invokeWithContinuation(ActionInvoker.java:524)
at play.mvc.ActionInvoker.invoke(ActionInvoker.java:475)
at play.mvc.ActionInvoker.invokeControllerMethod(ActionInvoker.java:451)
at play.mvc.ActionInvoker.invokeControllerMethod(ActionInvoker.java:446)
at play.mvc.ActionInvoker.invoke(ActionInvoker.java:160)
... 1 more

```
