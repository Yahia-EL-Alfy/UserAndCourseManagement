package com.example.coursemicroservice;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int instructorID;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int enrolledStudents;

    @Column(nullable = true)
    private String listOfReviews;

    
    public Course(Long id, String name, int instructorID, int duration, String category, boolean status, float rating, int capacity, int enrolledStudents, String listOfReviews) {
        this.id = id;
        this.name = name;
        this.instructorID = instructorID;
        this.duration = duration;
        this.category = category;
        this.status = status;
        this.rating = rating;
        this.capacity = capacity;
        this.enrolledStudents = enrolledStudents;
        this.listOfReviews = listOfReviews;
    }

    public Course() {

    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getListOfReviews() {
        return listOfReviews;
    }

    public void setListOfReviews(String listOfReviews) {
        this.listOfReviews = listOfReviews;
    }
}