package org.example.application.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.api.ProjectRequest;
import org.example.application.exeptions.ResultException;
import org.example.application.mappers.ProjectsMapper;
import org.example.application.model.Project;
import org.example.application.services.ConnectionService;
import org.springframework.stereotype.Repository;

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
        String query = "SELECT * FROM projects";

        List<Project> positionList = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet == null) throw new ResultException("База данных пуста.");

            while (resultSet.next()) {
                Project project = projectsMapper.mapToProject(resultSet);
                positionList.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return positionList;
    }

    public Project getProjectById(int id) throws ResultException {
        String query = "SELECT * FROM projects p WHERE p.id = ?";
        Project project = null;
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet == null) throw new ResultException("Такого проекта не существует.");

            while (resultSet.next()) {
                project = projectsMapper.mapToProject(resultSet);
            }
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean savePosition(ProjectRequest projectRequest) {
        String query = "INSERT INTO projects (project_name) VALUES (?)";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectRequest.getProjectName());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProject(ProjectRequest projectRequest, int id) {
        String projectNameSQL = "UPDATE projects SET project_name = ? WHERE id = ?";

        try (Connection connection = connectionService.getConnection()){
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

            String projectName = projectRequest.getProjectName();
            Optional.ofNullable(projectName).ifPresent(projectNameConsumer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteProject(int id) {
        String query  = "DELETE FROM projects WHERE id = ?";
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
