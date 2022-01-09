package com.expensetracker.trackerapp.controller;

import com.expensetracker.trackerapp.entities.app.Group;
import com.expensetracker.trackerapp.request.CreateGroupRequest;
import com.expensetracker.trackerapp.services.GroupService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("group")
public class GroupController {

  private final GroupService groupService;

  GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @GetMapping("{groupId}")
  public Group getGroupDetail(@PathVariable String groupId) {
    return groupService.getGroupDetail(groupId);
  }

  @PostMapping("create")
  public void createGroup(@RequestBody CreateGroupRequest request) {
    groupService.createGroup(request);
  }

  @DeleteMapping("{groupId}")
  public void deleteGroup(@PathVariable String groupId) {

  }

}
