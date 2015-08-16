package log;

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
