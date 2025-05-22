package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.Chat;
import com.ashahar.projectmanagementsystem.model.Project;
import com.ashahar.projectmanagementsystem.model.User;
import com.ashahar.projectmanagementsystem.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();

        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        Project saveproject = projectRepo.save(createdProject);

        Chat chat = new Chat();
        chat.setProject(saveproject);

        Chat projectChat = chatService.createChat(chat);
        saveproject.setChat(projectChat);

        return saveproject;
    }

    @Override
    public List<Project> getProjectsByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepo.findByTeamContainingOrOwner(user, user);

        if(category != null){
            projects = projects.stream().filter(project -> project.getCategory().equals(category)).toList();
        }

        if(tag != null){
            projects = projects.stream().filter(project -> project.getTags().contains(tag)).toList();
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> project = projectRepo.findById(projectId);

        if(project.isEmpty()){
            throw new Exception("Project not found");
        }

        return project.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {

        //To check Project exists or not
        getProjectById(projectId);

        projectRepo.deleteById(projectId);

    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return projectRepo.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(!project.getTeam().contains(userId)){
            project.getChat().getUser().add(user);
            project.getTeam().add(user);
        }
        projectRepo.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(project.getTeam().contains(userId)){
            project.getChat().getUser().remove(user);
            project.getTeam().remove(user);
        }
        projectRepo.save(project);
    }

    @Override
    public Chat getchatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {

        return projectRepo.findByNameContainingAndTeamContains(keyword, user);
    }
}
