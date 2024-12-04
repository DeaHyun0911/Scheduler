package dh.scheduler.repository;

import dh.scheduler.dto.ScheduleResponseDto;
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
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userName", schedule.getUserName());
        parameters.put("title", schedule.getTitle());
        parameters.put("password", schedule.getPassword());
        parameters.put("contents", schedule.getContents());
        parameters.put("createDate", schedule.getCreateDate());
        parameters.put("updateDate", schedule.getUpdateDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(
                key.longValue(),
                schedule.getUserName(),
                schedule.getTitle(),
                schedule.getPassword(),
                schedule.getContents(),
                schedule.getCreateDate(),
                schedule.getUpdateDate());
    }

    @Override
    public Schedule findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from Schedule where id = ?", scheduleRowMapper(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id를 찾을 수 없습니다."));
    }

    @Override
    public List<ScheduleResponseDto> findScheduleListByDate(String date) {
        return jdbcTemplate.query("select * from Schedule where date(update_date) = ?", scheduleRowMappers(), date);
    }

    @Override
    public List<ScheduleResponseDto> findScheduleListByName(String name) {
        return jdbcTemplate.query("select * from Schedule where user_name = ?", scheduleRowMappers(), name);
    }

    @Override
    public List<ScheduleResponseDto> findScheduleListByNameWithDate(String date, String name) {
        return jdbcTemplate.query("select * from Schedule where user_name = ? and date(update_date) = ?", scheduleRowMappers(), name, date);
    }

    @Override
    public List<ScheduleResponseDto> findAllScheduleLists() {
        return jdbcTemplate.query("select * from Schedule", scheduleRowMappers());
    }

    @Override
    public int updateSchedule(Long id, String name, String contents, LocalDateTime now) {
        return jdbcTemplate.update("update Schedule set user_name = ?, contents = ?, update_date = ? where id = ?", name, contents, now, id);
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
                        rs.getString("user_name"),
                        rs.getString("title"),
                        rs.getString("password"),
                        rs.getString("contents"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("update_date").toLocalDateTime()
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
                        rs.getString("user_name"),
                        rs.getString("title"),
                        rs.getString("password"),
                        rs.getString("contents"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("update_date").toLocalDateTime()
                );
            }
        };
    }
}
