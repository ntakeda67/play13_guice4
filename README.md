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

### DI
Moduleで注入するものとされるものの対応を定義する。Injectorが依存性を解決してインスタンスを生成する。
InjectorにModuleを食わせることで、Injectorに依存性解決方法を指定する。

#### 例1.
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

#### 例2.
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

```HogeServiceのインスタンス化と使用
	Injector injector = Guice.createInjector(new HogeModule());
	HogeService service = injector.getInstance(HogeService.class);
	String hogeIs = service.getHogeName();
```
