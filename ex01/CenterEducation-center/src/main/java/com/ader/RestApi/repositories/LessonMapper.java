// package com.ader.RestApi.repositories;

// import java.sql.ResultSet;
// import java.sql.SQLException;

// import org.springframework.jdbc.core.RowMapper;

// import com.ader.RestApi.pojo.Lesson;
// import com.ader.RestApi.pojo.Role;
// import com.ader.RestApi.pojo.User;

// public class LessonMapper implements RowMapper<Lesson> {

//     @Override
//     public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {
//         Lesson lesson = new Lesson();
//         lesson.setlessonid(rs.getLong("lessonid"));
//         lesson.setstarttime(rs.getTime("starttime").toLocalTime());
//         lesson.setEndTime(rs.getTime("endTime").toLocalTime());
//         lesson.setDayOfWeek(rs.getString("dayOfWeek"));

//         User user = new User();
//         user.setId(rs.getLong("teacherId"));
//         user.setFirstName(rs.getString("firstName"));
//         user.setLastName(rs.getString("lastName"));
//         user.setLogin(rs.getString("login"));
//         user.setPassword(rs.getString("password"));
//         user.setRole(Role.values()[rs.getInt("role")]);
//         lesson.setTeacher(user);
//         return lesson;
//     }
// }
