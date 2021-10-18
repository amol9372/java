package com.expensetracker.trackerapp.controller;

import com.expensetracker.trackerapp.request.CreateGroupRequest;
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

  @GetMapping("{groupId}")
  public void getDetail(@PathVariable String groupId){

  }

  @PostMapping
  public void createGroup(@RequestBody CreateGroupRequest request){

  }

  @DeleteMapping("{groupId}")
  public void deleteGroup(@PathVariable String groupId){

  }

}
