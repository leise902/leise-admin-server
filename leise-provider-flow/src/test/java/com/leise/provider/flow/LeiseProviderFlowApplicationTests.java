package com.leise.provider.flow;

import com.leise.provider.flow.service.FlowCenterDesignerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeiseProviderFlowApplicationTests {

	@Autowired
	private FlowCenterDesignerService fcAdminService;

	@Test
	public void testFlowDiagramService() {


	}

}
