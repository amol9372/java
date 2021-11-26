package com.expensetracker.trackerapp.services;

import com.expensetracker.trackerapp.entities.app.Group;
import com.expensetracker.trackerapp.request.CreateGroupRequest;
import org.springframework.stereotype.Service;

@Service
public interface GroupService {

  void createGroup(CreateGroupRequest request);

  Group getGroupDetail(String groupId);

}
