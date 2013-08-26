package de.neuland.jade4j.helper.beans;



public class TestBean {
	private Level2TestBean level2;
	private String name;
	private int id;

	public Level2TestBean getLevel2() {
		return level2;
	}

	public void setLevel2(Level2TestBean level2) {
		this.level2 = level2;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }
}
