package amata1219.amachat.processor;

public interface TextProcessor {

	String process(String text);

	default boolean canProcess(String text){
		return true;
	}

}
