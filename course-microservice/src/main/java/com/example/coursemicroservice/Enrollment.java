package com.example.coursemicroservice;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentID;
    
    private Long courseID;

    private String accepted;

    public Enrollment() {
    }

    public Enrollment(Long studentID, Long courseID, String accepted) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.accepted = accepted;
    }

    public Long getEnrollmentID() {
        return id;
    }

    public void setEnrollmentID(Long enrollmentID) {
        this.id = enrollmentID;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentID=" + id +
                ", studentID=" + studentID +
                ", courseID=" + courseID +
                ", accepted='" + accepted + '\'' +
                '}';
    }
}