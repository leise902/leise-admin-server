package com.leise.flow;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.leise.flow.action.commu.HttpCommuAction;
import com.leise.flow.action.database.ListQueryAction;
import com.leise.flow.action.database.PageQueryAction;
import com.leise.flow.action.database.SingleOperAction;
import com.leise.flow.action.database.SingleQueryAction;
import com.leise.flow.context.FlowContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeiseFlowApplicationTests {

	@Autowired
	private SingleQueryAction singleQueryAction;

	@Autowired
	private SingleOperAction singleOperAction;

	@Autowired
	private ListQueryAction listQueryAction;

	@Autowired
	private PageQueryAction pageQueryAction;

	@Autowired
	private HttpCommuAction httpCommuAction;


	@Test
	public void testSingleQueryAction() {
		FlowContext context = new FlowContext("leise-provider_provider", "queryFlowList",  "查询流程列表", "1.0.0");
		context.put("userId", 100000);
//		initContext(context);
		singleQueryAction.setQuerySql("select user_name as userName, card_no as cardNo from user_info where user_id = :userId and user_id =:userId");
		singleQueryAction.setOutParamNames("cardNo");
		long startTime = System.currentTimeMillis();
		singleQueryAction.execute(context);
		long endTime = System.currentTimeMillis();
		System.out.println("cost:"  +  new BigDecimal(endTime-startTime).divide(new BigDecimal(1000), 4 , RoundingMode.HALF_UP)  + " seconds" );
	}

	@Test
	public void testSingleOperAction() {
		FlowContext context = new FlowContext("leise-provider_provider", "queryFlowList",  "查询流程列表", "1.0.0");
		context.put("userId", 100000);
		context.put("cardNo", String.valueOf(System.currentTimeMillis()));
		singleOperAction.setOperSql("update user_info set card_no = :cardNo  where user_id = :userId");
		long startTime = System.currentTimeMillis();
		singleOperAction.execute(context);
		long endTime = System.currentTimeMillis();
		System.out.println("cost:"  +  new BigDecimal(endTime-startTime).divide(new BigDecimal(1000), 4 , RoundingMode.HALF_UP)  + " seconds" );
	}

	@Test
	public void testListQueryAction() {
		long startTime = System.currentTimeMillis();
		FlowContext context = new FlowContext("leise-provider_provider", "queryFlowList",  "查询流程列表", "1.0.0");
		listQueryAction.setQuerySql("select user_id as userId, user_name as userName, card_no as cardNo from user_info");
		listQueryAction.setOutParamNames("userName");
		listQueryAction.setListName("userList");
		listQueryAction.execute(context);

		System.out.println(JSON.toJSONString(context));
		long endTime = System.currentTimeMillis();
		System.out.println("cost:"  +  new BigDecimal(endTime-startTime).divide(new BigDecimal(1000), 4 , RoundingMode.HALF_UP)  + " seconds" );
	}

	@Test
	public void testPageQueryAction() {
		long startTime = System.currentTimeMillis();
		FlowContext context = new FlowContext("leise-provider_provider", "queryFlowList",  "查询流程列表", "1.0.0");
		context.put("corpName", "立透公司");
		pageQueryAction.setCountSql("select count(*) from corp_info");
		pageQueryAction.setPageSql("select corp_id as corpId, corp_name as corpName,regist_no as registNo from corp_info where corp_name = :corpName limit :limit, :offset");
		pageQueryAction.setPageNum(9000);
		pageQueryAction.setPageSize(30);
		pageQueryAction.setOutParamNames("corpName||corpId||registNo");
		pageQueryAction.setListName("corpList");
		pageQueryAction.setQueryCount("true");
		pageQueryAction.execute(context);

		System.out.println(JSON.toJSONString(context));
		long endTime = System.currentTimeMillis();
		System.out.println("cost:"  +  new BigDecimal(endTime-startTime).divide(new BigDecimal(1000), 4 , RoundingMode.HALF_UP)  + " seconds" );
	}

	public void initContext(FlowContext context){

		for(int i=0; i<100000; i++){
			Map<String, Object> map = Maps.newHashMap();
			map.put(String.valueOf(i), UUID.randomUUID());
			context.putAll(map);
		}
	}

}
