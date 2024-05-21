package org.example.userandcoursemanagement;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@SpringBootClientQualifier
public class SpringBootAPIClient {
    private static final String BASE_URL = "http://localhost:8090/";

    public String addCourse(Course course) {
        try {
            URL url = new URL(BASE_URL + "courses/add");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Convert Course object to JSON string
            String jsonInputString = convertCourseToJson(course);

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            } else {
                return "Error: HTTP " + responseCode;
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    private String convertCourseToJson(Course course) {
        // Convert Course object to JSON string
        return "{\"name\":\"" + course.getName() +
                "\",\"instructorID\":" + course.getInstructorID() +
                ",\"duration\":" + course.getDuration() +
                ",\"category\":\"" + course.getCategory() +
                "\",\"status\":" + course.isStatus() +
                ",\"rating\":" + course.getRating() +
                ",\"capacity\":" + course.getCapacity() +
                ",\"enrolledStudents\":" + course.getEnrolledStudents() +
                ",\"listOfReviews\":\"" + course.getListOfReviews() +
                "\"}";
    }

    public List<Course> getAllCourses() {
        try {
            URL url = new URL(BASE_URL + "courses/all");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return convertJsonToCourses(response.toString());
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    private List<Course> convertJsonToCourses(String json) {
        return new Gson().fromJson(json, new TypeToken<List<Course>>() {}.getType());
    }

    public String updateCourse(Long id, Course updatedCourse) {
        try {
            URL url = new URL(BASE_URL + "courses/update/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Convert Course object to JSON string
            String jsonInputString = convertCourseToJson(updatedCourse);

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            } else {
                return "Error: HTTP " + responseCode;
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String deleteCourse(Long courseId) {
        try {
            URL url = new URL(BASE_URL + "courses/delete/" + courseId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return "Course deleted successfully";
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                return "Course not found";
            } else {
                return "Error: HTTP " + responseCode;
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public List<Course> searchCoursesByName(String name) {
        try {
            URL url = new URL(BASE_URL + "courses/searchByName?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return convertJsonToCourses(response.toString());
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<Course> searchCoursesByCategory(String category) {
        try {
            URL url = new URL(BASE_URL + "courses/searchByCategory?category=" + category);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return convertJsonToCourses(response.toString());
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<Course> sortCoursesByRating() {
        try {
            URL url = new URL(BASE_URL + "courses/sortByRating");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return convertJsonToCourses(response.toString());
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String addEnrollment(Long courseID, Long studentID) {
        try {
            URL url = new URL(BASE_URL + "courses/enrollments/add?courseID=" + courseID + "&studentID=" + studentID);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            } else {
                return "Error: HTTP " + responseCode;
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String updateEnrollment(Long id, String accepted) {
        try {
            URL url = new URL(BASE_URL + "courses/enrollments/update/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Convert accepted field to JSON string
            String jsonInputString = "{\"accepted\":\"" + accepted + "\"}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            } else {
                return "Error: HTTP " + responseCode;
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String deleteEnrollment(Long id) {
        try {
            URL url = new URL(BASE_URL + "courses/enrollments/delete/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return "Enrollment deleted successfully";
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                return "Enrollment not found";
            } else {
                return "Error: HTTP " + responseCode;
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String addReview(Long courseID, Long studentID, String reviewText) {
        try {
            URL url = new URL(BASE_URL + "courses/reviews/add?courseID=" + courseID + "&studentID=" + studentID);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Convert review text to JSON string
            String jsonInputString = "{\"review\":\"" + reviewText + "\"}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            } else {
                return "Error: HTTP " + responseCode;
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public List<Review> getReviewsByCourseID(Long courseID) {
        try {
            URL url = new URL(BASE_URL + "reviews?courseID=" + courseID);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return convertJsonToReviews(response.toString());
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private List<Review> convertJsonToReviews(String json) {
        return new Gson().fromJson(json, new TypeToken<List<Review>>() {}.getType());
    }
}