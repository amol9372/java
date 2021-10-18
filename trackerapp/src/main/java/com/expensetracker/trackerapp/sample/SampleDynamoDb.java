package com.expensetracker.trackerapp.sample;

import org.springframework.stereotype.Component;

@Component
public class SampleDynamoDb {



//    public static void main(String[] args) {
//
//        /**
//         *  Get Item API
//         */
//        // getItem();
//
//        /**
//         *  Query API
//         */
//        // queryItems();
//
//        /**
//         * Delete Item
//         */
//        //deleteItem();
//
//        /**
//         *  Put Item
//         */
//        //putItem();
//
//
//    }

//    private ExpenseTrackerBean getItem() {
//        ExpenseTrackerBean bean = new ExpenseTrackerBean();
//        bean.setPk("user#1");
//        bean.setSk("amol9372");
//        return dynamoDbEnhancedClient.getItem(bean);
//    }
//
//    private void queryItems() {
//        Map<String, AttributeValue> expressionValues = new HashMap<>();
//        expressionValues.put(":value", AttributeValue.builder().n("100").build());
//
//        Expression expression = Expression.builder()
//                .expression("cost = :value")
//                .expressionValues(expressionValues)
//                .build();
//
//        QueryConditional queryConditional = QueryConditional
//                .keyEqualTo(Key.builder().partitionValue("expense#5").build());
//
//        var queryEnhancedRequest = QueryEnhancedRequest.builder().queryConditional(queryConditional).filterExpression(expression).build();
//        var queryResult = dynamoDbEnhancedClient.query(queryEnhancedRequest);
//
//        queryResult.stream().forEach(r -> {
//            System.out.println(r.items());
//        });
//    }
//
//    private void deleteItem() {
//        var bean = new ExpenseTrackerBean();
//        bean.setPk("category#1");
//        bean.setSk("general");
//        dynamoDbEnhancedClient.deleteItem(bean);
//    }
//
//    private void putItem() {
//        var bean = new ExpenseTrackerBean();
//        bean.setPk("category#1");
//        bean.setSk("general");
//        bean.setCreated_date(LocalDateTime.now());
//        bean.setCreatedBy("amol9372");
//        dynamoDbEnhancedClient.putItem(bean);
//    }

}
