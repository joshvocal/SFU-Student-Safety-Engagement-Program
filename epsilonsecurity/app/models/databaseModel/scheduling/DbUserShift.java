package models.databaseModel.scheduling;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Java Object for DbUserShift Table with DbUserShift event id, user team id, shift id
 */
@Entity
public class DbUserShift extends Model {

    public static final String COLUMN_USER_TEAM_ID = "user_team_id";
    public static final String COLUMN_SHIFT_ID = "shift_id";

    @Id
    private Integer id;

    @Column(nullable = false)
    private Integer userTeamId;

    @Column(nullable = false)
    private Integer shiftId;

    public DbUserShift(Integer userTeamId, Integer shiftId) {
        this.userTeamId = userTeamId;
        this.shiftId = shiftId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserTeamId() {
        return userTeamId;
    }

    public void setUserTeamId(Integer userTeamId) {
        this.userTeamId = userTeamId;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public static Finder<Integer, DbUserShift> find = new Finder<>(DbUserShift.class);

    @Override
    public String toString() {
        return "DbUserShift{" +
                "id=" + id +
                ", userTeamId=" + userTeamId +
                ", shiftId=" + shiftId +
                '}';
    }
}
