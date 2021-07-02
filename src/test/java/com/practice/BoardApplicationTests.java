package com.practice;

import com.practice.entity.Address;
import com.practice.entity.Member;
import com.practice.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardApplicationTests {

	@Autowired
	MemberRepository memberRepository;

	@Test
	void firstTest() {
		Address address = new Address("180-14","소하우림필유","14070");
		Member member = Member.builder()
						.userId("rudwns7552")
						.name("윤경준")
						.address(address)
						.build();
		memberRepository.save(member);
	}
}
