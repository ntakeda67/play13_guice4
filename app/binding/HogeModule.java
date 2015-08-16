package binding;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import log.HogeInterface;
import log.Fuga;
import log.Foo;
import log.VariableFuga;

public class HogeModule extends AbstractModule {
    @Override
    protected void configure(){
	bind(HogeInterface.class).to(Fuga.class);
	//
	//	bind(HogeInterface.class)
	//  .annotatedWith(Names.named("Another"))
	//  .to(Foo.class);
    }

    @Provides
    HogeInterface provideVariableFuga(){
	VariableFuga vFuga = new VariableFuga("Variable");
	return vFuga;
    }
}
