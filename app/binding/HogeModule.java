package binding;

import com.google.inject.AbstractModule;

import log.HogeInterface;
import log.Fuga;

public class HogeModule extends AbstractModule {
    @Override
    protected void configure(){
	bind(HogeInterface.class).to(Fuga.class);
    }
}
