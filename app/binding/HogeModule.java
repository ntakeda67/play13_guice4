package binding;

import com.google.inject.AbstractModule;

import log.HogeInterface;
import log.Fuga;
import log.Foo;

public class HogeModule extends AbstractModule {
    @Override
    protected void configure(){
	bind(HogeInterface.class).to(Fuga.class);

	bind(HogeInterface.class)
	    .annotatedWith(AnotherHoge.class)
	    .to(Foo.class);
    }
}
