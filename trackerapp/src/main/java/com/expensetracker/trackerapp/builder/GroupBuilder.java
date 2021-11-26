package com.expensetracker.trackerapp.builder;

import com.expensetracker.trackerapp.entities.db.ExpenseTrackerBean;
import com.expensetracker.trackerapp.entities.db.ItemType;
import com.expensetracker.trackerapp.request.CreateGroupRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GroupBuilder extends BaseBuilder {

  public static List<ExpenseTrackerBean> with(CreateGroupRequest request) {

    var groupId = ItemType.GROUP.getValue().concat(UUID.randomUUID().toString());
    var groupDetails = new ExpenseTrackerBean.ExpenseTrackerBeanBuilder().withPk(groupId)
        .withSk(request.getName()).withCategory(request.getCategory())
        .withCreatedBy(request.getCreatedBy()).withMaxUsers(request.getMaxUsers()).withCreatedDate(
            LocalDateTime.now()).build();

    var groupData = stream(request.getStakeholders()).map(
        stakeholder -> new ExpenseTrackerBean.ExpenseTrackerBeanBuilder().withPk(groupId)
            .withSk(stakeholder.getUserId())
            .withCreatedDate(groupDetails.getCreatedDate())
            .withCategory(request.getCategory())
            .withEmail(stakeholder.getEmail())
            .withName(stakeholder.getName())
            .build()).collect(Collectors.toCollection(ArrayList::new));
    groupData.add(groupDetails);

    return groupData;
  }

}
