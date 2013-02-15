package email;

public class Feature {

	String value;
	int frequency;
	int index;
	boolean type; 			/* true-Uni, false-Bi */
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public boolean isType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	
	public boolean equals(Feature f)
	{
		return this.value.equals(f.value);
	}
	
	
}
