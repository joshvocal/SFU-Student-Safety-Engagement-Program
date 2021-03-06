package controllers;

import models.databaseModel.helpers.DbOneTimeAvailabilityHelper;
import models.databaseModel.scheduling.DbOneTimeAvailability;
import models.databaseModel.scheduling.Status;
import models.queries.ScheduleUtil;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class OneTimeAvailabilityController extends Controller {

    private static final String TIME_START = "start";
    private static final String TIME_END = "end";
    private final FormFactory formFactory;

    @Inject
    OneTimeAvailabilityController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    private DbOneTimeAvailability getDbOneTimeAvailabilityFromForm() {
        Form<DbOneTimeAvailability> form = formFactory.form(DbOneTimeAvailability.class).bindFromRequest();
        return form.get();
    }

    public Result listOneTimeAvailabilities() {
        List<DbOneTimeAvailability> dbOneTimeAvailabilityList = DbOneTimeAvailabilityHelper
                .readAllDbOneTimeAvailability();
        return ok(Json.toJson(dbOneTimeAvailabilityList));
    }

    public Result createOneTimeAvailability() {
        DbOneTimeAvailability dbOneTimeAvailability = getDbOneTimeAvailabilityFromForm();
        DbOneTimeAvailabilityHelper.createDbOneTimeAvailability(dbOneTimeAvailability);
        return ok();
    }

    public Result readOneTimeAvailabilityById(Integer id) {
        DbOneTimeAvailability dbOneTimeAvailability = DbOneTimeAvailabilityHelper.readDbOneTimeAvailabilityById(id);
        return ok(Json.toJson(dbOneTimeAvailability));
    }

    public Result deleteOneTimeAvailability(Integer id) {
        DbOneTimeAvailability dbOneTimeAvailability = DbOneTimeAvailabilityHelper.readDbOneTimeAvailabilityById(id);
        DbOneTimeAvailabilityHelper.deleteDbOneTimeAvailability(dbOneTimeAvailability);
        return ok();
    }

    public Result readOneTimeAvailabilitiesByTimeRange(Long timeStart, Long timeEnd) {
        List<DbOneTimeAvailability> dbOneTimeAvailabilityList = DbOneTimeAvailabilityHelper
                .readDbOneTimeAvailabilityByTimeRange(timeStart, timeEnd);

        return ok(Json.toJson(dbOneTimeAvailabilityList));
    }


    public Result readOneTimeAvailabilityByUserId(Integer userId, Long timeStart, Long timeEnd) {
        List<DbOneTimeAvailability> dbOneTimeAvailabilityList = ScheduleUtil.getAllOneTimeAvailByUserIdAndTimeRange(userId, timeStart, timeEnd);
        return ok(Json.toJson(dbOneTimeAvailabilityList));
    }

    public Result readAllAvailabilitiesByUserId(Integer userId){
        List<DbOneTimeAvailability> dbOneTimeAvailabilityList = DbOneTimeAvailabilityHelper.readAllDbOneTimeAvailabilityByUserId(userId);
        return ok(Json.toJson(dbOneTimeAvailabilityList));
    }

    public Result readOneTimeAvailabilityStatus(Integer userId, Integer teamId, Long timeStart, Long timeEnd) {
        Status status = ScheduleUtil.getOneTimeAvailStatus(userId, teamId, timeStart, timeEnd);
        return ok(Json.toJson(status));
    }

}
