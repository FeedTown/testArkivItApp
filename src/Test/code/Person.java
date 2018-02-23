package Test.code;

public class Person {

    private String name;
    private String lName;

    public Person(String name, String lName) {
        this.name = name;
        this.lName = lName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

}