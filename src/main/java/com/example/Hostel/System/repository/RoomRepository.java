package com.example.Hostel.System.repository;





import com.example.Hostel.System.models.Room;
import com.example.Hostel.System.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByRoomNumber(String roomNumber);

    Room findByStudent(Student student);
    long countByStatus(String status);

}