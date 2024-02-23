package com.cafe.bbs.app.reply.service;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cafe.bbs.app.reply.dao.ReplyDAO;
import com.cafe.bbs.app.reply.dao.ReplyDAOImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import({ReplyServiceImpl.class, ReplyDAOImpl.class})
public class ReplyServiceImplTest {
	@Autowired
	private ReplyService replyService;
	@MockBean
	private ReplyDAO replyDAO;
	
	@Test
	@DisplayName("___체크")
	public void checkAvailable___test() {
		// given
		
		// when
		
		// then
	}

}
