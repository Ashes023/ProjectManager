package com.ashahar.projectmanagementsystem.repo;

import com.ashahar.projectmanagementsystem.model.Project;
import com.ashahar.projectmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {

    List<Project> findByOwner(User user);

    List<Project> findByNameContainingAndTeamMembers(String partialName, User user);

    @Query("SELECT p FROM Project p join p.team t where t = :user")
    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);
}
