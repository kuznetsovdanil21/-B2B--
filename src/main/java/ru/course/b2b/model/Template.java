package ru.course.b2b.model;

public class Template {

    private int id;

    private String name;
    private String goal;
    private String requirements;
    private String expectedResult;

    public Template(
            int id,
            String name,
            String goal,
            String requirements,
            String expectedResult
    ) {
        this.id = id;
        this.name = name;
        this.goal = goal;
        this.requirements = requirements;
        this.expectedResult = expectedResult;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGoal() {
        return goal;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    @Override
    public String toString() {
        return name;
    }
}