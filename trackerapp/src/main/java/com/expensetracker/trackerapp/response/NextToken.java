package com.expensetracker.trackerapp.response;

import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class NextToken {

  private String pk;
  private String sk;

  public NextToken getNextToken(Map<String, AttributeValue> lastEvaluatedKey) {
    if (CollectionUtils.isEmpty(lastEvaluatedKey)) {
      return null;
    }
    this.setPk(lastEvaluatedKey.get("pk").s());
    this.setSk(lastEvaluatedKey.get("sk").s());
    return this;
  }

  public Map<String, AttributeValue> createNextToken(Map<String, String> startKey) {
    if (ObjectUtils.isEmpty(startKey.get("startPk"))) {
      return null;
    }
    return Map.of("pk", AttributeValue.builder().s(startKey.get("startPk")).build(), "sk",
        AttributeValue.builder().s(startKey.get("startSk")).build());
  }

  public String getPk() {
    return pk;
  }

  public void setPk(String pk) {
    this.pk = pk;
  }

  public String getSk() {
    return sk;
  }

  public void setSk(String sk) {
    this.sk = sk;
  }
}
