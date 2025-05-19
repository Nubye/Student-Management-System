// Student.java
public class Student {
    private int id;
    private String rollNumber;
    private String name;
    private String grade;
    private int age;
    private String address;

    public Student(String rollNumber, String name, String grade, int age, String address) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.grade = grade;
        this.age = age;
        this.address = address;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber + ", Name: " + name + ", Grade: " + grade + ", Age: " + age + ", Address: " + address;
    }
}