package com.example.eea_part2.API;

import com.example.eea_part2.Model.Batch;
import com.example.eea_part2.Model.Classroom;
import com.example.eea_part2.Model.ClassroomDTO;
import com.example.eea_part2.Model.JwtRequest;
import com.example.eea_part2.Model.JwtResponse;
import com.example.eea_part2.Model.Module;
import com.example.eea_part2.Model.ModuleDTO;
import com.example.eea_part2.Model.Timetable;
import com.example.eea_part2.Model.TimetableDTO;
import com.example.eea_part2.Model.User;
import com.example.eea_part2.Model.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CallAPI {

    @POST("visitor/authorize")
    Call<JwtResponse> authenticateUser(@Body JwtRequest users);

    @GET("student/todayStudentLectures")
    Call<List<Timetable>> getTodayStudentTimetable(@Header("Authorization") String Authorization);

    @GET("student/ViewAllStudentLectures")
    Call<List<Timetable>> getAllLecturesStudent(@Header("Authorization")String Authorization);

    @GET("student/viewStudentMyModules")
    Call<List<ModuleDTO>> getMyModulesStudent(@Header("Authorization")String Authorization);

    @GET("lecturer/getAllLecturerTimetable")
    Call<List<Timetable>> getAllLecturesLecturer(@Header("Authorization")String Authorization);

    @GET("lecturer/getTodayTimetableLecturer")
    Call<List<Timetable>> getTodayLecturerTimetable(@Header("Authorization")String Authorization);

    @GET("lecturer/viewMyModulesLecturer")
    Call<List<ModuleDTO>> getMyModulesLecturer(@Header("Authorization")String Authorization);

    @GET("admin/viewBatchesMobile")
    Call<List<Batch>> getAllBatches(@Header("Authorization") String Authorization);

    @GET("admin/viewClassroomMobile")
    Call<List<Classroom>> getAllClassrooms(@Header("Authorization")String Authorization);

    @GET("admin/viewModulesMobile")
    Call<List<ModuleDTO>> getAllModules(@Header("Authorization")String Authorization);

    @GET("admin/ViewStudentsMobile")
    Call<List<User>> getAllStudents(@Header("Authorization")String Authorization);

    @GET("admin/ViewLecturersMobile")
    Call<List<User>> getAllLecturers(@Header("Authorization")String Authorization);

    @GET("admin/viewAllLecturesAdmin")
    Call<List<Timetable>> getAllLecturesAdmin(@Header("Authorization")String Authorization);

    @GET("admin/viewTodayLecturesAdmin")
    Call<List<Timetable>> getTodayLecturesAdmin(@Header("Authorization")String Authorization);

    @POST("admin/addClassroom")
    Call<Classroom> addClassroom(@Header("Authorization")String Authorization, @Body Classroom classroom);

    @POST("admin/addBatch")
    Call<Batch> addBatch(@Header("Authorization")String Authorization, @Body Batch batch);

    @GET("admin/getBatchList")
    Call<List<Batch>> getBatchesForStudent(@Header("Authorization")String Authorization);

    @POST("admin/addStudent")
    Call<User> addStudent(@Header("Authorization")String Authorization, @Body User user);

    @POST("admin/addLecturer")
    Call<User> addLecturer(@Header("Authorization")String Authorization, @Body User user);

    @GET("admin/getLecturerList")
    Call<List<UserDTO>> getLecturersForModules(@Header("Authorization")String Authorization);

    @POST("admin/addModule")
    Call<Module> addModule(@Header("Authorization")String Authorization, @Body Module module);

    @GET("admin/getClassroomList")
    Call<List<ClassroomDTO>> getClassroomsForTimetable(@Header("Authorization")String Authorization);

    @POST("admin/addTimetable")
    Call<TimetableDTO> addTimetable(@Header("Authorization")String Authorization, @Body TimetableDTO timetable);

    @GET("student/myAccount")
    Call<UserDTO> getMyAccount(@Header("Authorization")String Authorization);

    @DELETE("admin/deleteClassroom/{ClassroomID}")
    Call<Void> deleteClassroom(@Path("ClassroomID") String ClassroomID, @Header("Authorization")String Authorization);

    @DELETE("admin/deleteBatch/{BatchID}")
    Call<Void> deleteBatch(@Path("BatchID") Integer BatchID, @Header("Authorization")String Authorization);

    @DELETE("admin/deleteModule/{ModuleID}")
    Call<Void> deleteModule(@Path("ModuleID") String ModuleID, @Header("Authorization")String Authorization);

    @DELETE("admin/deleteUser/{UserID}")
    Call<Void> deleteUser(@Path("UserID") String UserID, @Header("Authorization")String Authorization);

    @DELETE("admin/deleteTimetable/{timetableID}")
    Call<Void> deleteTimetable(@Path("timetableID") Integer timetableID, @Header("Authorization")String Authorization);

    @GET("admin/searchBatch/{keyword}")
    Call<List<Batch>> searchBatches(@Header("Authorization")String Authorization, @Path("keyword") String keyword);

    @GET("admin/searchStudent/{keyword}")
    Call<List<User>> searchStudents(@Header("Authorization")String Authorization, @Path("keyword") String keyword);

    @GET("admin/searchLecturer/{keyword}")
    Call<List<User>> searchLecturers(@Header("Authorization")String Authorization, @Path("keyword") String keyword);



}
