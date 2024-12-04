package dh.scheduler.repository;

import dh.scheduler.dto.ScheduleResponseDto;
import dh.scheduler.entity.Author;
import dh.scheduler.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Schedule saveSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("authorId", schedule.getAuthorId());
        parameters.put("title", schedule.getTitle());
        parameters.put("password", schedule.getPassword());
        parameters.put("contents", schedule.getContents());
        parameters.put("createdAt", schedule.getCreatedAt());
        parameters.put("updatedAt", schedule.getUpdatedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new Schedule(
                key.longValue(),
                schedule.getAuthorId(),
                schedule.getTitle(),
                schedule.getPassword(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }

    @Override
    public Author saveAuthor(Author author) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("author").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userName", author.getUserName());
        parameters.put("createdAt", author.getCreatedAt());
        parameters.put("updatedAt", author.getUpdatedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new Author(
                key.longValue(),
                author.getUserName(),
                author.getCreatedAt(),
                author.getUpdatedAt());
    }

    @Override
    public Schedule findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from Schedule where id = ?", scheduleRowMapper(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id를 찾을 수 없습니다."));
    }

    @Override
    public Author findAuthorById(Long id) {
        List<Author> result = jdbcTemplate.query("select a.* from author a join Schedule s on a.id=s.author_id where s.id = ? ", authorRowMapper(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id를 찾을 수 없습니다."));
    }




    @Override
    public List<ScheduleResponseDto> findScheduleListByDate(String date) {
        return jdbcTemplate.query("select * from Schedule s join author a on s.author_id=a.id where date(s.updatedAt) = ?", scheduleRowMappers(), date);
    }

    @Override
    public List<ScheduleResponseDto> findScheduleListByName(String name) {
        return jdbcTemplate.query("select * from Schedule s join author a on s.author_id=a.id where a.user_name = ?", scheduleRowMappers(), name);
    }

    @Override
    public List<ScheduleResponseDto> findScheduleListByNameWithDate(String date, String name) {
        return jdbcTemplate.query("select * from Schedule s join author a on s.author_id=a.id where a.user_name = ? and date(s.updatedAt) = ? ", scheduleRowMappers(), name, date);
    }

    @Override
    public List<ScheduleResponseDto> findAllScheduleLists() {
        return jdbcTemplate.query("select * from Schedule s join author a on s.author_id = a.id ", scheduleRowMappers());
    }

    @Override
    public int updateSchedule(Long id, String contents, LocalDateTime now) {
        return jdbcTemplate.update("update Schedule set contents = ?, updatedAt = ? where id = ?", contents, now, id);
    }

    @Override
    public int updateAuthor(Long id, String name) {
        return jdbcTemplate.update("update author set user_name = ? where id = ?", name, id);
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("delete from Schedule where id = ?", id);
    }


    private RowMapper<Schedule> scheduleRowMapper() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getLong("author_id"),
                        rs.getString("title"),
                        rs.getString("password"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime()
                );
            }
        };
    }
    

    private RowMapper<ScheduleResponseDto> scheduleRowMappers() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getLong("author_id"),
                        rs.getString("user_name"),
                        rs.getString("title"),
                        rs.getString("password"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Author> authorRowMapper() {
        return new RowMapper<Author>() {
            @Override
            public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Author(
                        rs.getLong("id"),
                        rs.getString("user_name"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime()
                );
            }
        };
    }
    
    
}
