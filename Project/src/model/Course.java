package model;

public class Course {
    private String courseId;
    private String name;
    private int credits;
    private String type;

    public Course(String courseId, String name, int credits, String type) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.type = type;
    }

    public String getCourseId() { return courseId; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public String getType() { return type; }

    @Override
    public String toString() {
        return courseId + " - " + name + " - " + credits + " tín chỉ - " + type;
    }
}
