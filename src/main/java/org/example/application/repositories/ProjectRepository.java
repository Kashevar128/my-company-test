package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.api.CreateProjectRequest;
import org.example.application.exeptions.ResultException;
import org.example.application.interfaces.MyCallback;
import org.example.application.mappers.ProjectsMapper;
import org.example.application.model.Position;
import org.example.application.model.Project;
import org.example.application.services.ConnectionService;
import org.example.application.services.HibernateService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private final ConnectionService connectionService;
    private final ProjectsMapper projectsMapper;
    private final HibernateService hibernateService;

    public List<Project> getProjectsEmployeeById(int id) throws ResultException {
        String query = "SELECT pr.id, pr.project_name\n" +
                "FROM employees e\n" +
                "LEFT JOIN employees_to_projects t ON t.id_employee = e.id\n" +
                "LEFT JOIN projects pr ON t.id_project = pr.id\n" +
                "WHERE e.id = ?";

        List<Project> projectList = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet == null) throw new ResultException("Такого проекта не существует.");

            while (resultSet.next()) {
                Project project = projectsMapper.mapToProject(resultSet);
                projectList.add(project);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return projectList;
    }

    public List<Project> getAllProjects() throws ResultException {
        EntityManager entityManager = hibernateService.getEntityManager();
        MyCallback<Optional<List<Project>>> positionsCallback = () ->
                Optional
                        .ofNullable(entityManager
                                .createQuery("SELECT e FROM Project e", Project.class)
                                .getResultList());
        Optional<List<Project>> projects = hibernateService.executeQuery(positionsCallback);
        if (projects.isPresent()) return projects.get();
        else throw new ResultException("База данных пуста.");
    }

    public Project getProjectById(int id) throws ResultException {
        EntityManager entityManager = hibernateService.getEntityManager();
        MyCallback<Optional<Project>> myCallback = () -> Optional.ofNullable(entityManager.find(Project.class, id));
        Optional<Project> project = hibernateService.executeQuery(myCallback);
        if (!project.isPresent()) throw new ResultException("Такого проекта не существует.");
        return project.get();
    }

    public boolean saveProject(CreateProjectRequest createProjectRequest) {
        EntityManager entityManager = hibernateService.getEntityManager();
        MyCallback<Boolean> createPositionCallback = () -> {
            try {
                Project project = new Project();
                project.setProjectName(createProjectRequest.getProjectName());
                entityManager.persist(project);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };
        return hibernateService.executeQuery(createPositionCallback);
    }

    public boolean updateProject(CreateProjectRequest createProjectRequest, int id) {
        String projectNameSQL = "UPDATE projects SET project_name = ? WHERE id = ?";

        try (Connection connection = connectionService.getConnection()) {
            Consumer<String> projectNameConsumer = (projectName) -> {
                PreparedStatement statement;
                try {
                    statement = connection.prepareStatement(projectNameSQL);
                    statement.setString(1, projectName);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };

            String projectName = createProjectRequest.getProjectName();
            Optional.ofNullable(projectName).ifPresent(projectNameConsumer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteProject(int id) {
        String query = "DELETE FROM projects WHERE id = ?";
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
